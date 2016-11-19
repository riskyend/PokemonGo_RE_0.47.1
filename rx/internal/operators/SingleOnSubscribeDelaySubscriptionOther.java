package rx.internal.operators;

import rx.Observable;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;

public final class SingleOnSubscribeDelaySubscriptionOther<T>
  implements Single.OnSubscribe<T>
{
  final Single<? extends T> main;
  final Observable<?> other;
  
  public SingleOnSubscribeDelaySubscriptionOther(Single<? extends T> paramSingle, Observable<?> paramObservable)
  {
    this.main = paramSingle;
    this.other = paramObservable;
  }
  
  public void call(final SingleSubscriber<? super T> paramSingleSubscriber)
  {
    final SingleSubscriber local1 = new SingleSubscriber()
    {
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSingleSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onSuccess(T paramAnonymousT)
      {
        paramSingleSubscriber.onSuccess(paramAnonymousT);
      }
    };
    final SerialSubscription localSerialSubscription = new SerialSubscription();
    paramSingleSubscriber.add(localSerialSubscription);
    paramSingleSubscriber = new Subscriber()
    {
      boolean done;
      
      public void onCompleted()
      {
        if (this.done) {
          return;
        }
        this.done = true;
        localSerialSubscription.set(local1);
        SingleOnSubscribeDelaySubscriptionOther.this.main.subscribe(local1);
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        if (this.done)
        {
          RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymousThrowable);
          return;
        }
        this.done = true;
        local1.onError(paramAnonymousThrowable);
      }
      
      public void onNext(Object paramAnonymousObject)
      {
        onCompleted();
      }
    };
    localSerialSubscription.set(paramSingleSubscriber);
    this.other.subscribe(paramSingleSubscriber);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/SingleOnSubscribeDelaySubscriptionOther.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */