package com.upsight.android.internal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public final class NetworkHelper
{
  public static final String NETWORK_OPERATOR_NONE = "none";
  public static final String NETWORK_TYPE_NONE = "no_network";
  
  public static String getActiveNetworkType(Context paramContext)
  {
    String str = "no_network";
    try
    {
      Object localObject = (ConnectivityManager)paramContext.getSystemService("connectivity");
      paramContext = str;
      if (localObject != null)
      {
        localObject = ((ConnectivityManager)localObject).getActiveNetworkInfo();
        paramContext = str;
        if (localObject != null)
        {
          paramContext = str;
          if (((NetworkInfo)localObject).isConnected())
          {
            localObject = ((NetworkInfo)localObject).getTypeName();
            boolean bool = TextUtils.isEmpty((CharSequence)localObject);
            paramContext = str;
            if (!bool) {
              paramContext = (Context)localObject;
            }
          }
        }
      }
      return paramContext;
    }
    catch (SecurityException paramContext) {}
    return "no_network";
  }
  
  public static String getNetworkOperatorName(Context paramContext)
  {
    String str = "none";
    try
    {
      Object localObject = (TelephonyManager)paramContext.getSystemService("phone");
      paramContext = str;
      if (localObject != null)
      {
        localObject = ((TelephonyManager)localObject).getNetworkOperatorName();
        boolean bool = TextUtils.isEmpty((CharSequence)localObject);
        paramContext = str;
        if (!bool) {
          paramContext = (Context)localObject;
        }
      }
      return paramContext;
    }
    catch (SecurityException paramContext) {}
    return "none";
  }
  
  public static boolean isConnected(Context paramContext)
  {
    boolean bool = false;
    try
    {
      paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (paramContext != null)
      {
        paramContext = paramContext.getActiveNetworkInfo();
        if (paramContext != null)
        {
          bool = paramContext.isConnected();
          if (bool) {
            bool = true;
          }
        }
      }
      else
      {
        return bool;
      }
      return false;
    }
    catch (SecurityException paramContext) {}
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/util/NetworkHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */