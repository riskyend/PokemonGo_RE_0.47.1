package rx.internal.util;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.producers.SingleProducer;
import rx.internal.schedulers.EventLoopsScheduler;
import rx.observers.Subscribers;

public final class ScalarSynchronousObservable<T>
  extends Observable<T>
{
  static final boolean STRONG_MODE = Boolean.valueOf(System.getProperty("rx.just.strong-mode", "false")).booleanValue();
  final T t;
  
  protected ScalarSynchronousObservable(T paramT)
  {
    super(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        paramAnonymousSubscriber.setProducer(ScalarSynchronousObservable.createProducer(paramAnonymousSubscriber, ScalarSynchronousObservable.this));
      }
    });
    this.t = paramT;
  }
  
  public static <T> ScalarSynchronousObservable<T> create(T paramT)
  {
    return new ScalarSynchronousObservable(paramT);
  }
  
  static <T> Producer createProducer(Subscriber<? super T> paramSubscriber, T paramT)
  {
    if (STRONG_MODE) {
      return new SingleProducer(paramSubscriber, paramT);
    }
    return new WeakSingleProducer(paramSubscriber, paramT);
  }
  
  public T get()
  {
    return (T)this.t;
  }
  
  public <R> Observable<R> scalarFlatMap(final Func1<? super T, ? extends Observable<? extends R>> paramFunc1)
  {
    create(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super R> paramAnonymousSubscriber)
      {
        Observable localObservable = (Observable)paramFunc1.call(ScalarSynchronousObservable.this.t);
        if ((localObservable instanceof ScalarSynchronousObservable))
        {
          paramAnonymousSubscriber.setProducer(ScalarSynchronousObservable.createProducer(paramAnonymousSubscriber, ((ScalarSynchronousObservable)localObservable).t));
          return;
        }
        localObservable.unsafeSubscribe(Subscribers.wrap(paramAnonymousSubscriber));
      }
    });
  }
  
  public Observable<T> scalarScheduleOn(final Scheduler paramScheduler)
  {
    if ((paramScheduler instanceof EventLoopsScheduler)) {}
    for (paramScheduler = new Func1()
        {
          public Subscription call(Action0 paramAnonymousAction0)
          {
            return paramScheduler.scheduleDirect(paramAnonymousAction0);
          }
        };; paramScheduler = new Func1()
        {
          public Subscription call(final Action0 paramAnonymousAction0)
          {
            final Scheduler.Worker localWorker = paramScheduler.createWorker();
            localWorker.schedule(new Action0()
            {
              public void call()
              {
                try
                {
                  paramAnonymousAction0.call();
                  return;
                }
                finally
                {
                  localWorker.unsubscribe();
                }
              }
            });
            return localWorker;
          }
        }) {
      return create(new ScalarAsyncOnSubscribe(this.t, paramScheduler));
    }
  }
  
  static final class ScalarAsyncOnSubscribe<T>
    implements Observable.OnSubscribe<T>
  {
    final Func1<Action0, Subscription> onSchedule;
    final T value;
    
    ScalarAsyncOnSubscribe(T paramT, Func1<Action0, Subscription> paramFunc1)
    {
      this.value = paramT;
      this.onSchedule = paramFunc1;
    }
    
    public void call(Subscriber<? super T> paramSubscriber)
    {
      paramSubscriber.setProducer(new ScalarSynchronousObservable.ScalarAsyncProducer(paramSubscriber, this.value, this.onSchedule));
    }
  }
  
  static final class ScalarAsyncProducer<T>
    extends AtomicBoolean
    implements Producer, Action0
  {
    private static final long serialVersionUID = -2466317989629281651L;
    final Subscriber<? super T> actual;
    final Func1<Action0, Subscription> onSchedule;
    final T value;
    
    public ScalarAsyncProducer(Subscriber<? super T> paramSubscriber, T paramT, Func1<Action0, Subscription> paramFunc1)
    {
      this.actual = paramSubscriber;
      this.value = paramT;
      this.onSchedule = paramFunc1;
    }
    
    public void call()
    {
      Subscriber localSubscriber = this.actual;
      if (localSubscriber.isUnsubscribed()) {}
      for (;;)
      {
        return;
        Object localObject = this.value;
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
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= 0 required but it was " + paramLong);
      }
      if ((paramLong != 0L) && (compareAndSet(false, true))) {
        this.actual.add((Subscription)this.onSchedule.call(this));
      }
    }
    
    public String toString()
    {
      return "ScalarAsyncProducer[" + this.value + ", " + get() + "]";
    }
  }
  
  static final class WeakSingleProducer<T>
    implements Producer
  {
    final Subscriber<? super T> actual;
    boolean once;
    final T value;
    
    public WeakSingleProducer(Subscriber<? super T> paramSubscriber, T paramT)
    {
      this.actual = paramSubscriber;
      this.value = paramT;
    }
    
    public void request(long paramLong)
    {
      if (this.once) {}
      for (;;)
      {
        return;
        if (paramLong < 0L) {
          throw new IllegalStateException("n >= required but it was " + paramLong);
        }
        if (paramLong != 0L)
        {
          this.once = true;
          Subscriber localSubscriber = this.actual;
          if (!localSubscriber.isUnsubscribed())
          {
            Object localObject = this.value;
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
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/ScalarSynchronousObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */