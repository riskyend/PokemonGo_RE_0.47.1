package com.upsight.android.internal;

import android.content.Context;
import com.upsight.android.UpsightContext;
import com.upsight.android.internal.util.SidHelper;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public final class UpsightContextModule
{
  @Provides
  @Singleton
  UpsightContext provideUpsightContext(Context paramContext, @Named("com.upsight.sdk_plugin") String paramString1, @Named("com.upsight.app_token") String paramString2, @Named("com.upsight.public_key") String paramString3, UpsightDataStore paramUpsightDataStore, UpsightLogger paramUpsightLogger)
  {
    return new UpsightContext(paramContext, paramString1, paramString2, paramString3, SidHelper.getSid(paramContext), paramUpsightDataStore, paramUpsightLogger);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/UpsightContextModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */