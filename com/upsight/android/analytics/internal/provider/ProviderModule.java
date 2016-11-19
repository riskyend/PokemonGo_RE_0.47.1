package com.upsight.android.analytics.internal.provider;

import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightOptOutStatus;
import com.upsight.android.analytics.provider.UpsightUserAttributes;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class ProviderModule
{
  @Provides
  @Singleton
  public UpsightOptOutStatus providesOptOutStatus(OptOutStatus paramOptOutStatus)
  {
    return paramOptOutStatus;
  }
  
  @Provides
  @Singleton
  public UpsightLocationTracker providesUpsightLocationTracker(LocationTracker paramLocationTracker)
  {
    return paramLocationTracker;
  }
  
  @Provides
  @Singleton
  public UpsightUserAttributes providesUpsightUserAttributes(UserAttributes paramUserAttributes)
  {
    return paramUserAttributes;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/ProviderModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */