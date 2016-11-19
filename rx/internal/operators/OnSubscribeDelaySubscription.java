package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.functions.Action0;
import rx.observers.Subscribers;

public final class OnSubscribeDelaySubscription<T>
  implements Observable.OnSubscribe<T>
{
  final Scheduler scheduler;
  final Observable<? extends T> source;
  final long time;
  final TimeUnit unit;
  
  public OnSubscribeDelaySubscription(Observable<? extends T> paramObservable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.source = paramObservable;
    this.time = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  public void call(final Subscriber<? super T> paramSubscriber)
  {
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    paramSubscriber.add(localWorker);
    localWorker.schedule(new Action0()
    {
      public void call()
      {
        if (!paramSubscriber.isUnsubscribed()) {
          OnSubscribeDelaySubscription.this.source.unsafeSubscribe(Subscribers.wrap(paramSubscriber));
        }
      }
    }, this.time, this.unit);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeDelaySubscription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */