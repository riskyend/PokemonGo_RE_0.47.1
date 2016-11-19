package com.upsight.android.googleadvertisingid.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.logger.UpsightLogger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class GooglePlayAdvertisingProvider
  extends UpsightDataProvider
{
  public static final String AID_KEY = "ids.aid";
  public static final String LIMITED_AD_TRACKING_KEY = "device.limit_ad_tracking";
  public static final String LOG_TAG = GooglePlayAdvertisingProvider.class.getSimpleName();
  private final Context mContext;
  private final UpsightLogger mLogger;
  
  public GooglePlayAdvertisingProvider(Context paramContext, UpsightLogger paramUpsightLogger)
  {
    this.mContext = paramContext;
    this.mLogger = paramUpsightLogger;
  }
  
  public Set<String> availableKeys()
  {
    return new HashSet(Arrays.asList(new String[] { "ids.aid", "device.limit_ad_tracking" }));
  }
  
  public Object get(String paramString)
  {
    Object localObject = null;
    int i = 0;
    for (;;)
    {
      try
      {
        switch (paramString.hashCode())
        {
        case 1669192966: 
          paramString = super.get(paramString);
          return paramString;
        }
      }
      finally {}
      if (paramString.equals("ids.aid"))
      {
        break label198;
        boolean bool = paramString.equals("device.limit_ad_tracking");
        if (bool)
        {
          i = 1;
          break label198;
          String str = null;
          try
          {
            paramString = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
            str = paramString;
          }
          catch (Exception paramString)
          {
            this.mLogger.w(LOG_TAG, "Unable to resolve Google Advertising ID", new Object[] { paramString });
            continue;
          }
          paramString = (String)localObject;
          if (str == null) {
            continue;
          }
          paramString = str.getId();
          continue;
          str = null;
          try
          {
            paramString = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
            str = paramString;
          }
          catch (Exception paramString)
          {
            for (;;)
            {
              this.mLogger.w(LOG_TAG, "Unable to resolve Google limited ad tracking status", new Object[] { paramString });
            }
          }
          paramString = (String)localObject;
          if (str == null) {
            continue;
          }
          paramString = Boolean.valueOf(str.isLimitAdTrackingEnabled());
          continue;
        }
      }
      i = -1;
      label198:
      switch (i)
      {
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googleadvertisingid/internal/GooglePlayAdvertisingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */