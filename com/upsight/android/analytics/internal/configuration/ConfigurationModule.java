package com.upsight.android.analytics.internal.configuration;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class ConfigurationModule
{
  @Provides
  @Singleton
  public ConfigurationManager provideConfigurationManager(UpsightContext paramUpsightContext, ConfigurationResponseParser paramConfigurationResponseParser, ManagerConfigParser paramManagerConfigParser)
  {
    return new ConfigurationManager(paramUpsightContext, paramUpsightContext.getDataStore(), paramConfigurationResponseParser, paramManagerConfigParser, paramUpsightContext.getCoreComponent().subscribeOnScheduler(), paramUpsightContext.getCoreComponent().bus(), paramUpsightContext.getLogger());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/configuration/ConfigurationModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */