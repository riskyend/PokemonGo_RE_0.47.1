package com.upsight.android.analytics.internal.dispatcher.schema;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.squareup.otto.Bus;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.internal.util.NetworkHelper;

public class NetworkChangeBroadcastReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!Upsight.isEnabled(paramContext)) {}
    do
    {
      return;
      paramIntent = Upsight.createContext(paramContext).getCoreComponent();
    } while (paramIntent == null);
    paramIntent.bus().post(new NetworkChangeEvent(NetworkHelper.getActiveNetworkType(paramContext), NetworkHelper.getNetworkOperatorName(paramContext)));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/NetworkChangeBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */