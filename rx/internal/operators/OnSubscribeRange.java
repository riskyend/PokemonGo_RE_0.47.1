package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Subscriber;

public final class OnSubscribeRange
  implements Observable.OnSubscribe<Integer>
{
  private final int endIndex;
  private final int startIndex;
  
  public OnSubscribeRange(int paramInt1, int paramInt2)
  {
    this.startIndex = paramInt1;
    this.endIndex = paramInt2;
  }
  
  public void call(Subscriber<? super Integer> paramSubscriber)
  {
    paramSubscriber.setProducer(new RangeProducer(paramSubscriber, this.startIndex, this.endIndex));
  }
  
  private static final class RangeProducer
    extends AtomicLong
    implements Producer
  {
    private static final long serialVersionUID = 4114392207069098388L;
    private final Subscriber<? super Integer> childSubscriber;
    private long currentIndex;
    private final int endOfRange;
    
    RangeProducer(Subscriber<? super Integer> paramSubscriber, int paramInt1, int paramInt2)
    {
      this.childSubscriber = paramSubscriber;
      this.currentIndex = paramInt1;
      this.endOfRange = paramInt2;
    }
    
    void fastpath()
    {
      long l2 = this.endOfRange;
      Subscriber localSubscriber = this.childSubscriber;
      long l1 = this.currentIndex;
      if (l1 != l2 + 1L) {
        if (!localSubscriber.isUnsubscribed()) {}
      }
      while (localSubscriber.isUnsubscribed())
      {
        return;
        localSubscriber.onNext(Integer.valueOf((int)l1));
        l1 += 1L;
        break;
      }
      localSubscriber.onCompleted();
    }
    
    public void request(long paramLong)
    {
      if (get() == Long.MAX_VALUE) {}
      do
      {
        return;
        if ((paramLong == Long.MAX_VALUE) && (compareAndSet(0L, Long.MAX_VALUE)))
        {
          fastpath();
          return;
        }
      } while ((paramLong <= 0L) || (BackpressureUtils.getAndAddRequest(this, paramLong) != 0L));
      slowpath(paramLong);
    }
    
    void slowpath(long paramLong)
    {
      long l1 = 0L;
      long l4 = this.endOfRange + 1L;
      long l2 = this.currentIndex;
      Subscriber localSubscriber = this.childSubscriber;
      for (;;)
      {
        if ((l1 != paramLong) && (l2 != l4)) {
          if (!localSubscriber.isUnsubscribed()) {}
        }
        do
        {
          do
          {
            return;
            localSubscriber.onNext(Integer.valueOf((int)l2));
            l2 += 1L;
            l1 += 1L;
            break;
          } while (localSubscriber.isUnsubscribed());
          if (l2 == l4)
          {
            localSubscriber.onCompleted();
            return;
          }
          long l3 = get();
          paramLong = l3;
          if (l3 != l1) {
            break;
          }
          this.currentIndex = l2;
          paramLong = addAndGet(-l1);
        } while (paramLong == 0L);
        l1 = 0L;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */