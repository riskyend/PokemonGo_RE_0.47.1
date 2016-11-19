package com.upsight.android.internal;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightContext;
import com.upsight.android.internal.logger.LogWriter;
import com.upsight.android.internal.logger.LoggerModule;
import com.upsight.android.internal.logger.LoggerModule_ProvideUpsightLoggerFactory;
import com.upsight.android.internal.persistence.PersistenceModule;
import com.upsight.android.internal.persistence.PersistenceModule_ProvideBackgroundDataStoreFactory;
import com.upsight.android.internal.persistence.PersistenceModule_ProvideDataStoreFactory;
import com.upsight.android.internal.persistence.storable.StorableIdFactory;
import com.upsight.android.internal.persistence.storable.StorableInfoCache;
import com.upsight.android.internal.persistence.storable.StorableModule;
import com.upsight.android.internal.persistence.storable.StorableModule_ProvideStorableInfoCacheFactory;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerCoreComponent
  implements CoreComponent
{
  private Provider<Context> provideApplicationContextProvider;
  private Provider<String> provideApplicationTokenProvider;
  private Provider<UpsightDataStore> provideBackgroundDataStoreProvider;
  private Provider<Bus> provideBusProvider;
  private Provider<UpsightDataStore> provideDataStoreProvider;
  private Provider<Gson> provideGsonProvider;
  private Provider<JsonParser> provideJsonParserProvider;
  private Provider<LogWriter> provideLogWriterProvider;
  private Provider<Scheduler> provideObserveOnSchedulerProvider;
  private Provider<String> providePublicKeyProvider;
  private Provider<String> provideSdkPluginProvider;
  private Provider<StorableInfoCache> provideStorableInfoCacheProvider;
  private Provider<Scheduler> provideSubscribeOnSchedulerProvider;
  private Provider<StorableIdFactory> provideTypeIdGeneratorProvider;
  private Provider<UpsightContext> provideUpsightContextProvider;
  private Provider<UpsightLogger> provideUpsightLoggerProvider;
  
  static
  {
    if (!DaggerCoreComponent.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private DaggerCoreComponent(Builder paramBuilder)
  {
    assert (paramBuilder != null);
    initialize(paramBuilder);
  }
  
  public static Builder builder()
  {
    return new Builder(null);
  }
  
  private void initialize(Builder paramBuilder)
  {
    this.provideApplicationContextProvider = DoubleCheck.provider(ContextModule_ProvideApplicationContextFactory.create(paramBuilder.contextModule));
    this.provideGsonProvider = DoubleCheck.provider(JsonModule_ProvideGsonFactory.create(paramBuilder.jsonModule));
    this.provideStorableInfoCacheProvider = DoubleCheck.provider(StorableModule_ProvideStorableInfoCacheFactory.create(paramBuilder.storableModule, this.provideGsonProvider));
    this.provideTypeIdGeneratorProvider = DoubleCheck.provider(ContextModule_ProvideTypeIdGeneratorFactory.create(paramBuilder.contextModule));
    this.provideSubscribeOnSchedulerProvider = DoubleCheck.provider(SchedulersModule_ProvideSubscribeOnSchedulerFactory.create(paramBuilder.schedulersModule));
    this.provideObserveOnSchedulerProvider = DoubleCheck.provider(SchedulersModule_ProvideObserveOnSchedulerFactory.create(paramBuilder.schedulersModule));
    this.provideBusProvider = DoubleCheck.provider(ContextModule_ProvideBusFactory.create(paramBuilder.contextModule));
    this.provideDataStoreProvider = DoubleCheck.provider(PersistenceModule_ProvideDataStoreFactory.create(paramBuilder.persistenceModule, this.provideApplicationContextProvider, this.provideStorableInfoCacheProvider, this.provideTypeIdGeneratorProvider, this.provideSubscribeOnSchedulerProvider, this.provideObserveOnSchedulerProvider, this.provideBusProvider));
    this.provideLogWriterProvider = DoubleCheck.provider(ContextModule_ProvideLogWriterFactory.create(paramBuilder.contextModule));
    this.provideUpsightLoggerProvider = DoubleCheck.provider(LoggerModule_ProvideUpsightLoggerFactory.create(paramBuilder.loggerModule, this.provideDataStoreProvider, this.provideLogWriterProvider));
    this.provideSdkPluginProvider = DoubleCheck.provider(PropertiesModule_ProvideSdkPluginFactory.create(paramBuilder.propertiesModule, this.provideApplicationContextProvider, this.provideUpsightLoggerProvider));
    this.provideApplicationTokenProvider = DoubleCheck.provider(PropertiesModule_ProvideApplicationTokenFactory.create(paramBuilder.propertiesModule, this.provideApplicationContextProvider, this.provideUpsightLoggerProvider));
    this.providePublicKeyProvider = DoubleCheck.provider(PropertiesModule_ProvidePublicKeyFactory.create(paramBuilder.propertiesModule, this.provideApplicationContextProvider, this.provideUpsightLoggerProvider));
    this.provideUpsightContextProvider = DoubleCheck.provider(UpsightContextModule_ProvideUpsightContextFactory.create(paramBuilder.upsightContextModule, this.provideApplicationContextProvider, this.provideSdkPluginProvider, this.provideApplicationTokenProvider, this.providePublicKeyProvider, this.provideDataStoreProvider, this.provideUpsightLoggerProvider));
    this.provideJsonParserProvider = DoubleCheck.provider(JsonModule_ProvideJsonParserFactory.create(paramBuilder.jsonModule));
    this.provideBackgroundDataStoreProvider = DoubleCheck.provider(PersistenceModule_ProvideBackgroundDataStoreFactory.create(paramBuilder.persistenceModule, this.provideApplicationContextProvider, this.provideSubscribeOnSchedulerProvider, this.provideTypeIdGeneratorProvider, this.provideStorableInfoCacheProvider, this.provideBusProvider));
  }
  
  public Context applicationContext()
  {
    return (Context)this.provideApplicationContextProvider.get();
  }
  
  public UpsightDataStore backgroundDataStore()
  {
    return (UpsightDataStore)this.provideBackgroundDataStoreProvider.get();
  }
  
  public Bus bus()
  {
    return (Bus)this.provideBusProvider.get();
  }
  
  public Gson gson()
  {
    return (Gson)this.provideGsonProvider.get();
  }
  
  public JsonParser jsonParser()
  {
    return (JsonParser)this.provideJsonParserProvider.get();
  }
  
  public Scheduler observeOnScheduler()
  {
    return (Scheduler)this.provideObserveOnSchedulerProvider.get();
  }
  
  public Scheduler subscribeOnScheduler()
  {
    return (Scheduler)this.provideSubscribeOnSchedulerProvider.get();
  }
  
  public UpsightContext upsightContext()
  {
    return (UpsightContext)this.provideUpsightContextProvider.get();
  }
  
  public static final class Builder
  {
    private ContextModule contextModule;
    private JsonModule jsonModule;
    private LoggerModule loggerModule;
    private PersistenceModule persistenceModule;
    private PropertiesModule propertiesModule;
    private SchedulersModule schedulersModule;
    private StorableModule storableModule;
    private UpsightContextModule upsightContextModule;
    
    public CoreComponent build()
    {
      if (this.contextModule == null) {
        throw new IllegalStateException(ContextModule.class.getCanonicalName() + " must be set");
      }
      if (this.jsonModule == null) {
        this.jsonModule = new JsonModule();
      }
      if (this.storableModule == null) {
        this.storableModule = new StorableModule();
      }
      if (this.schedulersModule == null) {
        this.schedulersModule = new SchedulersModule();
      }
      if (this.persistenceModule == null) {
        this.persistenceModule = new PersistenceModule();
      }
      if (this.loggerModule == null) {
        this.loggerModule = new LoggerModule();
      }
      if (this.propertiesModule == null) {
        this.propertiesModule = new PropertiesModule();
      }
      if (this.upsightContextModule == null) {
        this.upsightContextModule = new UpsightContextModule();
      }
      return new DaggerCoreComponent(this, null);
    }
    
    public Builder contextModule(ContextModule paramContextModule)
    {
      this.contextModule = ((ContextModule)Preconditions.checkNotNull(paramContextModule));
      return this;
    }
    
    @Deprecated
    public Builder coreModule(CoreModule paramCoreModule)
    {
      Preconditions.checkNotNull(paramCoreModule);
      return this;
    }
    
    public Builder jsonModule(JsonModule paramJsonModule)
    {
      this.jsonModule = ((JsonModule)Preconditions.checkNotNull(paramJsonModule));
      return this;
    }
    
    public Builder loggerModule(LoggerModule paramLoggerModule)
    {
      this.loggerModule = ((LoggerModule)Preconditions.checkNotNull(paramLoggerModule));
      return this;
    }
    
    public Builder persistenceModule(PersistenceModule paramPersistenceModule)
    {
      this.persistenceModule = ((PersistenceModule)Preconditions.checkNotNull(paramPersistenceModule));
      return this;
    }
    
    public Builder propertiesModule(PropertiesModule paramPropertiesModule)
    {
      this.propertiesModule = ((PropertiesModule)Preconditions.checkNotNull(paramPropertiesModule));
      return this;
    }
    
    public Builder schedulersModule(SchedulersModule paramSchedulersModule)
    {
      this.schedulersModule = ((SchedulersModule)Preconditions.checkNotNull(paramSchedulersModule));
      return this;
    }
    
    public Builder storableModule(StorableModule paramStorableModule)
    {
      this.storableModule = ((StorableModule)Preconditions.checkNotNull(paramStorableModule));
      return this;
    }
    
    public Builder upsightContextModule(UpsightContextModule paramUpsightContextModule)
    {
      this.upsightContextModule = ((UpsightContextModule)Preconditions.checkNotNull(paramUpsightContextModule));
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/DaggerCoreComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */