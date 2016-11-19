package com.voxelbusters.nativeplugins.features.reachability;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityListener
  extends BroadcastReceiver
{
  boolean isConnected;
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    updateConnectionStatus(paramContext);
  }
  
  public void updateConnectionStatus(Context paramContext)
  {
    paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((paramContext != null) && (paramContext.isConnected())) {}
    for (boolean bool = true;; bool = false)
    {
      NetworkReachabilityHandler.sendWifiReachabilityStatus(bool);
      return;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/nativeplugins/features/reachability/ConnectivityListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */