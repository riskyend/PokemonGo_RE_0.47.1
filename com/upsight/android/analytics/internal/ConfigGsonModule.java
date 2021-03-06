package com.upsight.android.analytics.internal;

import com.google.gson.Gson;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public final class ConfigGsonModule
{
  public static final String GSON_CONFIG = "config-gson";
  
  @Provides
  @Named("config-gson")
  @Singleton
  public Gson provideConfigGson(UpsightContext paramUpsightContext)
  {
    return paramUpsightContext.getCoreComponent().gson();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/ConfigGsonModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */