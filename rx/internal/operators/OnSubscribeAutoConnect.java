package rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.observers.Subscribers;

public final class OnSubscribeAutoConnect<T>
  implements Observable.OnSubscribe<T>
{
  final AtomicInteger clients;
  final Action1<? super Subscription> connection;
  final int numberOfSubscribers;
  final ConnectableObservable<? extends T> source;
  
  public OnSubscribeAutoConnect(ConnectableObservable<? extends T> paramConnectableObservable, int paramInt, Action1<? super Subscription> paramAction1)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("numberOfSubscribers > 0 required");
    }
    this.source = paramConnectableObservable;
    this.numberOfSubscribers = paramInt;
    this.connection = paramAction1;
    this.clients = new AtomicInteger();
  }
  
  public void call(Subscriber<? super T> paramSubscriber)
  {
    this.source.unsafeSubscribe(Subscribers.wrap(paramSubscriber));
    if (this.clients.incrementAndGet() == this.numberOfSubscribers) {
      this.source.connect(this.connection);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeAutoConnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */