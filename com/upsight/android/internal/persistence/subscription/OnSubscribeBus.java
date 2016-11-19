package com.upsight.android.internal.persistence.subscription;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

class OnSubscribeBus
  implements Observable.OnSubscribe<DataStoreEvent>
{
  private final Bus mBus;
  
  OnSubscribeBus(Bus paramBus)
  {
    this.mBus = paramBus;
  }
  
  public void call(Subscriber<? super DataStoreEvent> paramSubscriber)
  {
    final BusAdapter localBusAdapter = new BusAdapter(paramSubscriber, null);
    this.mBus.register(localBusAdapter);
    paramSubscriber.add(Subscriptions.create(new Action0()
    {
      public void call()
      {
        OnSubscribeBus.this.mBus.unregister(localBusAdapter);
      }
    }));
  }
  
  private static class BusAdapter
  {
    private final Subscriber<? super DataStoreEvent> mChild;
    
    private BusAdapter(Subscriber<? super DataStoreEvent> paramSubscriber)
    {
      this.mChild = paramSubscriber;
    }
    
    @Subscribe
    public void onPersistenceEvent(DataStoreEvent paramDataStoreEvent)
    {
      if (!this.mChild.isUnsubscribed()) {
        this.mChild.onNext(paramDataStoreEvent);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/OnSubscribeBus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */