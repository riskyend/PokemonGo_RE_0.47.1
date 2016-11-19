package rx.internal.util;

import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.schedulers.EventLoopsScheduler;

public final class ScalarSynchronousSingle<T>
  extends Single<T>
{
  final T value;
  
  protected ScalarSynchronousSingle(T paramT)
  {
    super(new Single.OnSubscribe()
    {
      public void call(SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        paramAnonymousSingleSubscriber.onSuccess(ScalarSynchronousSingle.this);
      }
    });
    this.value = paramT;
  }
  
  public static final <T> ScalarSynchronousSingle<T> create(T paramT)
  {
    return new ScalarSynchronousSingle(paramT);
  }
  
  public T get()
  {
    return (T)this.value;
  }
  
  public <R> Single<R> scalarFlatMap(final Func1<? super T, ? extends Single<? extends R>> paramFunc1)
  {
    create(new Single.OnSubscribe()
    {
      public void call(final SingleSubscriber<? super R> paramAnonymousSingleSubscriber)
      {
        Single localSingle = (Single)paramFunc1.call(ScalarSynchronousSingle.this.value);
        if ((localSingle instanceof ScalarSynchronousSingle))
        {
          paramAnonymousSingleSubscriber.onSuccess(((ScalarSynchronousSingle)localSingle).value);
          return;
        }
        Subscriber local1 = new Subscriber()
        {
          public void onCompleted() {}
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousSingleSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onNext(R paramAnonymous2R)
          {
            paramAnonymousSingleSubscriber.onSuccess(paramAnonymous2R);
          }
        };
        paramAnonymousSingleSubscriber.add(local1);
        localSingle.unsafeSubscribe(local1);
      }
    });
  }
  
  public Single<T> scalarScheduleOn(Scheduler paramScheduler)
  {
    if ((paramScheduler instanceof EventLoopsScheduler)) {
      return create(new DirectScheduledEmission((EventLoopsScheduler)paramScheduler, this.value));
    }
    return create(new NormalScheduledEmission(paramScheduler, this.value));
  }
  
  static final class DirectScheduledEmission<T>
    implements Single.OnSubscribe<T>
  {
    private final EventLoopsScheduler es;
    private final T value;
    
    DirectScheduledEmission(EventLoopsScheduler paramEventLoopsScheduler, T paramT)
    {
      this.es = paramEventLoopsScheduler;
      this.value = paramT;
    }
    
    public void call(SingleSubscriber<? super T> paramSingleSubscriber)
    {
      paramSingleSubscriber.add(this.es.scheduleDirect(new ScalarSynchronousSingle.ScalarSynchronousSingleAction(paramSingleSubscriber, this.value)));
    }
  }
  
  static final class NormalScheduledEmission<T>
    implements Single.OnSubscribe<T>
  {
    private final Scheduler scheduler;
    private final T value;
    
    NormalScheduledEmission(Scheduler paramScheduler, T paramT)
    {
      this.scheduler = paramScheduler;
      this.value = paramT;
    }
    
    public void call(SingleSubscriber<? super T> paramSingleSubscriber)
    {
      Scheduler.Worker localWorker = this.scheduler.createWorker();
      paramSingleSubscriber.add(localWorker);
      localWorker.schedule(new ScalarSynchronousSingle.ScalarSynchronousSingleAction(paramSingleSubscriber, this.value));
    }
  }
  
  static final class ScalarSynchronousSingleAction<T>
    implements Action0
  {
    private final SingleSubscriber<? super T> subscriber;
    private final T value;
    
    ScalarSynchronousSingleAction(SingleSubscriber<? super T> paramSingleSubscriber, T paramT)
    {
      this.subscriber = paramSingleSubscriber;
      this.value = paramT;
    }
    
    public void call()
    {
      try
      {
        this.subscriber.onSuccess(this.value);
        return;
      }
      catch (Throwable localThrowable)
      {
        this.subscriber.onError(localThrowable);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/ScalarSynchronousSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */