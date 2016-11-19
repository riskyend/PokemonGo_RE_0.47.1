package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Subscriber;

public final class OnSubscribeFromArray<T>
  implements Observable.OnSubscribe<T>
{
  final T[] array;
  
  public OnSubscribeFromArray(T[] paramArrayOfT)
  {
    this.array = paramArrayOfT;
  }
  
  public void call(Subscriber<? super T> paramSubscriber)
  {
    paramSubscriber.setProducer(new FromArrayProducer(paramSubscriber, this.array));
  }
  
  static final class FromArrayProducer<T>
    extends AtomicLong
    implements Producer
  {
    private static final long serialVersionUID = 3534218984725836979L;
    final T[] array;
    final Subscriber<? super T> child;
    int index;
    
    public FromArrayProducer(Subscriber<? super T> paramSubscriber, T[] paramArrayOfT)
    {
      this.child = paramSubscriber;
      this.array = paramArrayOfT;
    }
    
    void fastPath()
    {
      Subscriber localSubscriber = this.child;
      Object[] arrayOfObject = this.array;
      int j = arrayOfObject.length;
      int i = 0;
      if (i < j)
      {
        localObject = arrayOfObject[i];
        if (!localSubscriber.isUnsubscribed()) {}
      }
      while (localSubscriber.isUnsubscribed())
      {
        Object localObject;
        return;
        localSubscriber.onNext(localObject);
        i += 1;
        break;
      }
      localSubscriber.onCompleted();
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= 0 required but it was " + paramLong);
      }
      if (paramLong == Long.MAX_VALUE) {
        if (BackpressureUtils.getAndAddRequest(this, paramLong) == 0L) {
          fastPath();
        }
      }
      while ((paramLong == 0L) || (BackpressureUtils.getAndAddRequest(this, paramLong) != 0L)) {
        return;
      }
      slowPath(paramLong);
    }
    
    void slowPath(long paramLong)
    {
      Subscriber localSubscriber = this.child;
      Object[] arrayOfObject = this.array;
      int j = arrayOfObject.length;
      long l1 = 0L;
      int i = this.index;
      for (;;)
      {
        if ((paramLong != 0L) && (i != j)) {
          if (!localSubscriber.isUnsubscribed()) {}
        }
        do
        {
          do
          {
            return;
            localSubscriber.onNext(arrayOfObject[i]);
            i += 1;
            if (i != j) {
              break;
            }
          } while (localSubscriber.isUnsubscribed());
          localSubscriber.onCompleted();
          return;
          paramLong -= 1L;
          l1 -= 1L;
          break;
          long l2 = get() + l1;
          paramLong = l2;
          if (l2 != 0L) {
            break;
          }
          this.index = i;
          paramLong = addAndGet(l1);
        } while (paramLong == 0L);
        l1 = 0L;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeFromArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */