package rx.internal.operators;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.functions.Action0;

public final class OperatorSubscribeOn<T>
  implements Observable.OnSubscribe<T>
{
  final Scheduler scheduler;
  final Observable<T> source;
  
  public OperatorSubscribeOn(Observable<T> paramObservable, Scheduler paramScheduler)
  {
    this.scheduler = paramScheduler;
    this.source = paramObservable;
  }
  
  public void call(final Subscriber<? super T> paramSubscriber)
  {
    final Scheduler.Worker localWorker = this.scheduler.createWorker();
    paramSubscriber.add(localWorker);
    localWorker.schedule(new Action0()
    {
      public void call()
      {
        final Object localObject = Thread.currentThread();
        localObject = new Subscriber(paramSubscriber)
        {
          public void onCompleted()
          {
            try
            {
              OperatorSubscribeOn.1.this.val$subscriber.onCompleted();
              return;
            }
            finally
            {
              OperatorSubscribeOn.1.this.val$inner.unsubscribe();
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              OperatorSubscribeOn.1.this.val$subscriber.onError(paramAnonymous2Throwable);
              return;
            }
            finally
            {
              OperatorSubscribeOn.1.this.val$inner.unsubscribe();
            }
          }
          
          public void onNext(T paramAnonymous2T)
          {
            OperatorSubscribeOn.1.this.val$subscriber.onNext(paramAnonymous2T);
          }
          
          public void setProducer(final Producer paramAnonymous2Producer)
          {
            OperatorSubscribeOn.1.this.val$subscriber.setProducer(new Producer()
            {
              public void request(final long paramAnonymous3Long)
              {
                if (OperatorSubscribeOn.1.1.this.val$t == Thread.currentThread())
                {
                  paramAnonymous2Producer.request(paramAnonymous3Long);
                  return;
                }
                OperatorSubscribeOn.1.this.val$inner.schedule(new Action0()
                {
                  public void call()
                  {
                    OperatorSubscribeOn.1.1.1.this.val$p.request(paramAnonymous3Long);
                  }
                });
              }
            });
          }
        };
        OperatorSubscribeOn.this.source.unsafeSubscribe((Subscriber)localObject);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSubscribeOn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */