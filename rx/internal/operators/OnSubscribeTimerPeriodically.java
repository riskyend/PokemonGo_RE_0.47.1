package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable.OnSubscribe;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;

public final class OnSubscribeTimerPeriodically
  implements Observable.OnSubscribe<Long>
{
  final long initialDelay;
  final long period;
  final Scheduler scheduler;
  final TimeUnit unit;
  
  public OnSubscribeTimerPeriodically(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.initialDelay = paramLong1;
    this.period = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void call(final Subscriber<? super Long> paramSubscriber)
  {
    final Scheduler.Worker localWorker = this.scheduler.createWorker();
    paramSubscriber.add(localWorker);
    localWorker.schedulePeriodically(new Action0()
    {
      long counter;
      
      public void call()
      {
        try
        {
          Subscriber localSubscriber = paramSubscriber;
          long l = this.counter;
          this.counter = (1L + l);
          localSubscriber.onNext(Long.valueOf(l));
          return;
        }
        catch (Throwable localThrowable)
        {
          try
          {
            localWorker.unsubscribe();
            return;
          }
          finally
          {
            Exceptions.throwOrReport(localThrowable, paramSubscriber);
          }
        }
      }
    }, this.initialDelay, this.period, this.unit);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeTimerPeriodically.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */