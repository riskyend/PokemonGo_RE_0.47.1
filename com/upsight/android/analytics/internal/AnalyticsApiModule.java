package com.upsight.android.analytics.internal;

import com.upsight.android.analytics.UpsightAnalyticsApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class AnalyticsApiModule
{
  @Provides
  @Singleton
  public UpsightAnalyticsApi provideUpsightAnalyticsApi(Analytics paramAnalytics)
  {
    return paramAnalytics;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/AnalyticsApiModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */