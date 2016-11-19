package com.upsight.android.analytics.session;

import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.logger.UpsightLogger;

public class UpsightSessionInfo
{
  public static final UpsightSessionInfo NONE = new UpsightSessionInfo(0, 0L);
  public static final int SESSION_NUMBER_INVALID = 0;
  public static final long START_TIMESTAMP_INVALID = 0L;
  public final int sessionNumber;
  public final long startTimestamp;
  
  public UpsightSessionInfo(int paramInt, long paramLong)
  {
    this.sessionNumber = paramInt;
    this.startTimestamp = paramLong;
  }
  
  public static UpsightSessionInfo getLatest(UpsightContext paramUpsightContext)
    throws IllegalArgumentException
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getLatestSessionInfo();
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return NONE;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/session/UpsightSessionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */