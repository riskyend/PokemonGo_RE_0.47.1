package rx.internal.producers;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public final class SingleProducer<T>
  extends AtomicBoolean
  implements Producer
{
  private static final long serialVersionUID = -3353584923995471404L;
  final Subscriber<? super T> child;
  final T value;
  
  public SingleProducer(Subscriber<? super T> paramSubscriber, T paramT)
  {
    this.child = paramSubscriber;
    this.value = paramT;
  }
  
  public void request(long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("n >= 0 required");
    }
    if (paramLong == 0L) {
      break label22;
    }
    for (;;)
    {
      label22:
      return;
      if (compareAndSet(false, true))
      {
        Subscriber localSubscriber = this.child;
        Object localObject = this.value;
        if (localSubscriber.isUnsubscribed()) {
          break;
        }
        try
        {
          localSubscriber.onNext(localObject);
          if (!localSubscriber.isUnsubscribed())
          {
            localSubscriber.onCompleted();
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwOrReport(localThrowable, localSubscriber, localObject);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/producers/SingleProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */