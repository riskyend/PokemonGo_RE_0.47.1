package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public final class OnSubscribeRefCount<T>
  implements Observable.OnSubscribe<T>
{
  volatile CompositeSubscription baseSubscription = new CompositeSubscription();
  final ReentrantLock lock = new ReentrantLock();
  private final ConnectableObservable<? extends T> source;
  final AtomicInteger subscriptionCount = new AtomicInteger(0);
  
  public OnSubscribeRefCount(ConnectableObservable<? extends T> paramConnectableObservable)
  {
    this.source = paramConnectableObservable;
  }
  
  private Subscription disconnect(final CompositeSubscription paramCompositeSubscription)
  {
    Subscriptions.create(new Action0()
    {
      public void call()
      {
        OnSubscribeRefCount.this.lock.lock();
        try
        {
          if ((OnSubscribeRefCount.this.baseSubscription == paramCompositeSubscription) && (OnSubscribeRefCount.this.subscriptionCount.decrementAndGet() == 0))
          {
            OnSubscribeRefCount.this.baseSubscription.unsubscribe();
            OnSubscribeRefCount.this.baseSubscription = new CompositeSubscription();
          }
          return;
        }
        finally
        {
          OnSubscribeRefCount.this.lock.unlock();
        }
      }
    });
  }
  
  private Action1<Subscription> onSubscribe(final Subscriber<? super T> paramSubscriber, final AtomicBoolean paramAtomicBoolean)
  {
    new Action1()
    {
      public void call(Subscription paramAnonymousSubscription)
      {
        try
        {
          OnSubscribeRefCount.this.baseSubscription.add(paramAnonymousSubscription);
          OnSubscribeRefCount.this.doSubscribe(paramSubscriber, OnSubscribeRefCount.this.baseSubscription);
          return;
        }
        finally
        {
          OnSubscribeRefCount.this.lock.unlock();
          paramAtomicBoolean.set(false);
        }
      }
    };
  }
  
  public void call(Subscriber<? super T> paramSubscriber)
  {
    this.lock.lock();
    if (this.subscriptionCount.incrementAndGet() == 1)
    {
      AtomicBoolean localAtomicBoolean = new AtomicBoolean(true);
      try
      {
        this.source.connect(onSubscribe(paramSubscriber, localAtomicBoolean));
        return;
      }
      finally
      {
        if (localAtomicBoolean.get()) {
          this.lock.unlock();
        }
      }
    }
    try
    {
      doSubscribe(paramSubscriber, this.baseSubscription);
      return;
    }
    finally
    {
      this.lock.unlock();
    }
  }
  
  void doSubscribe(final Subscriber<? super T> paramSubscriber, final CompositeSubscription paramCompositeSubscription)
  {
    paramSubscriber.add(disconnect(paramCompositeSubscription));
    this.source.unsafeSubscribe(new Subscriber(paramSubscriber)
    {
      void cleanup()
      {
        OnSubscribeRefCount.this.lock.lock();
        try
        {
          if (OnSubscribeRefCount.this.baseSubscription == paramCompositeSubscription)
          {
            OnSubscribeRefCount.this.baseSubscription.unsubscribe();
            OnSubscribeRefCount.this.baseSubscription = new CompositeSubscription();
            OnSubscribeRefCount.this.subscriptionCount.set(0);
          }
          return;
        }
        finally
        {
          OnSubscribeRefCount.this.lock.unlock();
        }
      }
      
      public void onCompleted()
      {
        cleanup();
        paramSubscriber.onCompleted();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        cleanup();
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        paramSubscriber.onNext(paramAnonymousT);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeRefCount.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */