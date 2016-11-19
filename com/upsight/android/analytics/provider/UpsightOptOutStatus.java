package com.upsight.android.analytics.provider;

import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.logger.UpsightLogger;

public abstract class UpsightOptOutStatus
{
  public static boolean get(UpsightContext paramUpsightContext)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getOptOutStatus();
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return false;
  }
  
  public static boolean get(UpsightSessionContext paramUpsightSessionContext)
  {
    return get(paramUpsightSessionContext.getUpsightContext());
  }
  
  public static void set(UpsightContext paramUpsightContext, boolean paramBoolean)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().setOptOutStatus(paramBoolean);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void set(UpsightSessionContext paramUpsightSessionContext, boolean paramBoolean)
  {
    set(paramUpsightSessionContext.getUpsightContext(), paramBoolean);
  }
  
  public abstract boolean get();
  
  public abstract void set(boolean paramBoolean);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/provider/UpsightOptOutStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */