package rx.internal.operators;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.observers.Subscribers;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

public final class OnSubscribeDelaySubscriptionOther<T, U>
  implements Observable.OnSubscribe<T>
{
  final Observable<? extends T> main;
  final Observable<U> other;
  
  public OnSubscribeDelaySubscriptionOther(Observable<? extends T> paramObservable, Observable<U> paramObservable1)
  {
    this.main = paramObservable;
    this.other = paramObservable1;
  }
  
  public void call(Subscriber<? super T> paramSubscriber)
  {
    final SerialSubscription localSerialSubscription = new SerialSubscription();
    paramSubscriber.add(localSerialSubscription);
    paramSubscriber = new Subscriber()
    {
      boolean done;
      
      public void onCompleted()
      {
        if (this.done) {
          return;
        }
        this.done = true;
        localSerialSubscription.set(Subscriptions.unsubscribed());
        OnSubscribeDelaySubscriptionOther.this.main.unsafeSubscribe(this.val$child);
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        if (this.done)
        {
          RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymousThrowable);
          return;
        }
        this.done = true;
        this.val$child.onError(paramAnonymousThrowable);
      }
      
      public void onNext(U paramAnonymousU)
      {
        onCompleted();
      }
    };
    localSerialSubscription.set(paramSubscriber);
    this.other.unsafeSubscribe(paramSubscriber);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeDelaySubscriptionOther.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */