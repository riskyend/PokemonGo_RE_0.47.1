package com.upsight.android.analytics.internal.dispatcher.routing;

import com.upsight.android.analytics.internal.DataStoreRecord;
import com.upsight.android.analytics.internal.dispatcher.delivery.OnResponseListener;

public abstract interface RoutingListener
  extends OnResponseListener
{
  public abstract void onDelivery(DataStoreRecord paramDataStoreRecord, boolean paramBoolean1, boolean paramBoolean2, String paramString);
  
  public abstract void onRoutingFinished(Router paramRouter);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/routing/RoutingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */