package com.upsight.mediation.vast.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkTools
{
  private static final String TAG = HttpTools.class.getName();
  
  public static boolean connectedToInternet(Context paramContext)
  {
    paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    NetworkInfo localNetworkInfo = paramContext.getNetworkInfo(1);
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {}
    do
    {
      do
      {
        return true;
        localNetworkInfo = paramContext.getNetworkInfo(0);
      } while ((localNetworkInfo != null) && (localNetworkInfo.isConnected()));
      paramContext = paramContext.getActiveNetworkInfo();
    } while ((paramContext != null) && (paramContext.isConnected()));
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/util/NetworkTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */