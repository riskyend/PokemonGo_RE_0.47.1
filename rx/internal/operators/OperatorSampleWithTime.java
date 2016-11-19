package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable.Operator;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.observers.SerializedSubscriber;

public final class OperatorSampleWithTime<T>
  implements Observable.Operator<T, T>
{
  final Scheduler scheduler;
  final long time;
  final TimeUnit unit;
  
  public OperatorSampleWithTime(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.time = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    Object localObject = new SerializedSubscriber(paramSubscriber);
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    paramSubscriber.add(localWorker);
    localObject = new SamplerSubscriber((Subscriber)localObject);
    paramSubscriber.add((Subscription)localObject);
    localWorker.schedulePeriodically((Action0)localObject, this.time, this.time, this.unit);
    return (Subscriber<? super T>)localObject;
  }
  
  static final class SamplerSubscriber<T>
    extends Subscriber<T>
    implements Action0
  {
    private static final Object EMPTY_TOKEN = new Object();
    private final Subscriber<? super T> subscriber;
    final AtomicReference<Object> value = new AtomicReference(EMPTY_TOKEN);
    
    public SamplerSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.subscriber = paramSubscriber;
    }
    
    private void emitIfNonEmpty()
    {
      Object localObject = this.value.getAndSet(EMPTY_TOKEN);
      if (localObject != EMPTY_TOKEN) {}
      try
      {
        this.subscriber.onNext(localObject);
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwOrReport(localThrowable, this);
      }
    }
    
    public void call()
    {
      emitIfNonEmpty();
    }
    
    public void onCompleted()
    {
      emitIfNonEmpty();
      this.subscriber.onCompleted();
      unsubscribe();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.subscriber.onError(paramThrowable);
      unsubscribe();
    }
    
    public void onNext(T paramT)
    {
      this.value.set(paramT);
    }
    
    public void onStart()
    {
      request(Long.MAX_VALUE);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSampleWithTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */