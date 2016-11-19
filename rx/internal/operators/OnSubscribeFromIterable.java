package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Subscriber;

public final class OnSubscribeFromIterable<T>
  implements Observable.OnSubscribe<T>
{
  final Iterable<? extends T> is;
  
  public OnSubscribeFromIterable(Iterable<? extends T> paramIterable)
  {
    if (paramIterable == null) {
      throw new NullPointerException("iterable must not be null");
    }
    this.is = paramIterable;
  }
  
  public void call(Subscriber<? super T> paramSubscriber)
  {
    Iterator localIterator = this.is.iterator();
    if ((!localIterator.hasNext()) && (!paramSubscriber.isUnsubscribed()))
    {
      paramSubscriber.onCompleted();
      return;
    }
    paramSubscriber.setProducer(new IterableProducer(paramSubscriber, localIterator));
  }
  
  private static final class IterableProducer<T>
    extends AtomicLong
    implements Producer
  {
    private static final long serialVersionUID = -8730475647105475802L;
    private final Iterator<? extends T> it;
    private final Subscriber<? super T> o;
    
    IterableProducer(Subscriber<? super T> paramSubscriber, Iterator<? extends T> paramIterator)
    {
      this.o = paramSubscriber;
      this.it = paramIterator;
    }
    
    void fastpath()
    {
      Subscriber localSubscriber = this.o;
      Iterator localIterator = this.it;
      if (localSubscriber.isUnsubscribed()) {}
      do
      {
        return;
        if (localIterator.hasNext())
        {
          localSubscriber.onNext(localIterator.next());
          break;
        }
      } while (localSubscriber.isUnsubscribed());
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
      Subscriber localSubscriber = this.o;
      Iterator localIterator = this.it;
      long l;
      label72:
      do
      {
        l = paramLong;
        if (localSubscriber.isUnsubscribed()) {}
        do
        {
          return;
          if (localIterator.hasNext())
          {
            l -= 1L;
            if (l < 0L) {
              break label72;
            }
            localSubscriber.onNext(localIterator.next());
            break;
          }
        } while (localSubscriber.isUnsubscribed());
        localSubscriber.onCompleted();
        return;
        l = addAndGet(-paramLong);
        paramLong = l;
      } while (l != 0L);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeFromIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */