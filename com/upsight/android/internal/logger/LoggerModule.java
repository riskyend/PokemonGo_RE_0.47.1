package com.upsight.android.internal.logger;

import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class LoggerModule
{
  @Provides
  @Singleton
  UpsightLogger provideUpsightLogger(UpsightDataStore paramUpsightDataStore, LogWriter paramLogWriter)
  {
    return Logger.create(paramUpsightDataStore, paramLogWriter);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/logger/LoggerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */