package rx.internal.operators;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeTimeout
  implements Completable.CompletableOnSubscribe
{
  final Completable other;
  final Scheduler scheduler;
  final Completable source;
  final long timeout;
  final TimeUnit unit;
  
  public CompletableOnSubscribeTimeout(Completable paramCompletable1, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, Completable paramCompletable2)
  {
    this.source = paramCompletable1;
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.other = paramCompletable2;
  }
  
  public void call(final Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
    paramCompletableSubscriber.onSubscribe(localCompositeSubscription);
    final AtomicBoolean localAtomicBoolean = new AtomicBoolean();
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    localCompositeSubscription.add(localWorker);
    localWorker.schedule(new Action0()
    {
      public void call()
      {
        if (localAtomicBoolean.compareAndSet(false, true))
        {
          localCompositeSubscription.clear();
          if (CompletableOnSubscribeTimeout.this.other == null) {
            paramCompletableSubscriber.onError(new TimeoutException());
          }
        }
        else
        {
          return;
        }
        CompletableOnSubscribeTimeout.this.other.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            CompletableOnSubscribeTimeout.1.this.val$set.unsubscribe();
            CompletableOnSubscribeTimeout.1.this.val$s.onCompleted();
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            CompletableOnSubscribeTimeout.1.this.val$set.unsubscribe();
            CompletableOnSubscribeTimeout.1.this.val$s.onError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            CompletableOnSubscribeTimeout.1.this.val$set.add(paramAnonymous2Subscription);
          }
        });
      }
    }, this.timeout, this.unit);
    this.source.subscribe(new Completable.CompletableSubscriber()
    {
      public void onCompleted()
      {
        if (localAtomicBoolean.compareAndSet(false, true))
        {
          localCompositeSubscription.unsubscribe();
          paramCompletableSubscriber.onCompleted();
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        if (localAtomicBoolean.compareAndSet(false, true))
        {
          localCompositeSubscription.unsubscribe();
          paramCompletableSubscriber.onError(paramAnonymousThrowable);
          return;
        }
        RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymousThrowable);
      }
      
      public void onSubscribe(Subscription paramAnonymousSubscription)
      {
        localCompositeSubscription.add(paramAnonymousSubscription);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */