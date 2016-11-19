package com.upsight.android.internal.persistence.subscription;

import com.squareup.otto.Bus;
import rx.functions.Action1;

class BusPublishAction
  implements Action1<DataStoreEvent>
{
  private final Bus bus;
  
  BusPublishAction(Bus paramBus)
  {
    this.bus = paramBus;
  }
  
  public void call(DataStoreEvent paramDataStoreEvent)
  {
    this.bus.post(paramDataStoreEvent);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/BusPublishAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */