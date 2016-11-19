package com.upsight.mediation.mraid.internal;

import com.upsight.mediation.log.FuseLog;

public class MRAIDLog
{
  private static final String TAG = "MRAID";
  
  public static void d(String paramString)
  {
    FuseLog.d("MRAID", paramString);
  }
  
  public static void d(String paramString1, String paramString2)
  {
    FuseLog.d("MRAID", "[" + paramString1 + "] " + paramString2);
  }
  
  public static void e(String paramString)
  {
    FuseLog.e("MRAID", paramString);
  }
  
  public static void e(String paramString1, String paramString2)
  {
    FuseLog.e("MRAID", "[" + paramString1 + "] " + paramString2);
  }
  
  public static void i(String paramString)
  {
    FuseLog.i("MRAID", paramString);
  }
  
  public static void i(String paramString1, String paramString2)
  {
    FuseLog.i("MRAID", "[" + paramString1 + "] " + paramString2);
  }
  
  public static void v(String paramString)
  {
    FuseLog.v("MRAID", paramString);
  }
  
  public static void v(String paramString1, String paramString2)
  {
    FuseLog.v("MRAID", "[" + paramString1 + "] " + paramString2);
  }
  
  public static void w(String paramString)
  {
    FuseLog.w("MRAID", paramString);
  }
  
  public static void w(String paramString1, String paramString2)
  {
    FuseLog.w("MRAID", "[" + paramString1 + "] " + paramString2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/internal/MRAIDLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */