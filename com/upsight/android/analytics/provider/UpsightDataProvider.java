package com.upsight.android.analytics.provider;

import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.logger.UpsightLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class UpsightDataProvider
{
  private final Map<String, Object> mCachedValues = new HashMap();
  
  public static void register(UpsightContext paramUpsightContext, UpsightDataProvider paramUpsightDataProvider)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().registerDataProvider(paramUpsightDataProvider);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void register(UpsightSessionContext paramUpsightSessionContext, UpsightDataProvider paramUpsightDataProvider)
  {
    register(paramUpsightSessionContext.getUpsightContext(), paramUpsightDataProvider);
  }
  
  public abstract Set<String> availableKeys();
  
  public Object get(String paramString)
  {
    try
    {
      paramString = this.mCachedValues.get(paramString);
      return paramString;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  protected void put(String paramString, Object paramObject)
  {
    try
    {
      this.mCachedValues.put(paramString, paramObject);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/provider/UpsightDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */