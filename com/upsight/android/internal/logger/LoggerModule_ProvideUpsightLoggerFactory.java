package com.upsight.android.internal.logger;

import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LoggerModule_ProvideUpsightLoggerFactory
  implements Factory<UpsightLogger>
{
  private final Provider<UpsightDataStore> dataStoreProvider;
  private final LoggerModule module;
  private final Provider<LogWriter> writerProvider;
  
  static
  {
    if (!LoggerModule_ProvideUpsightLoggerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public LoggerModule_ProvideUpsightLoggerFactory(LoggerModule paramLoggerModule, Provider<UpsightDataStore> paramProvider, Provider<LogWriter> paramProvider1)
  {
    assert (paramLoggerModule != null);
    this.module = paramLoggerModule;
    assert (paramProvider != null);
    this.dataStoreProvider = paramProvider;
    assert (paramProvider1 != null);
    this.writerProvider = paramProvider1;
  }
  
  public static Factory<UpsightLogger> create(LoggerModule paramLoggerModule, Provider<UpsightDataStore> paramProvider, Provider<LogWriter> paramProvider1)
  {
    return new LoggerModule_ProvideUpsightLoggerFactory(paramLoggerModule, paramProvider, paramProvider1);
  }
  
  public UpsightLogger get()
  {
    return (UpsightLogger)Preconditions.checkNotNull(this.module.provideUpsightLogger((UpsightDataStore)this.dataStoreProvider.get(), (LogWriter)this.writerProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/logger/LoggerModule_ProvideUpsightLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */