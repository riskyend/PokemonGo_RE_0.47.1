package com.upsight.android.analytics.internal.dispatcher.schema;

class NetworkChangeEvent
{
  public final String activeNetworkType;
  public final String networkOperatorName;
  
  public NetworkChangeEvent(String paramString1, String paramString2)
  {
    this.activeNetworkType = paramString1;
    this.networkOperatorName = paramString2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/NetworkChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */