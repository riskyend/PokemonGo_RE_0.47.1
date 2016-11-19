package com.upsight.android.internal;

import android.content.Context;
import com.upsight.android.UpsightContext;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UpsightContextModule_ProvideUpsightContextFactory
  implements Factory<UpsightContext>
{
  private final Provider<String> appTokenProvider;
  private final Provider<Context> baseContextProvider;
  private final Provider<UpsightDataStore> dataStoreProvider;
  private final Provider<UpsightLogger> loggerProvider;
  private final UpsightContextModule module;
  private final Provider<String> publicKeyProvider;
  private final Provider<String> sdkPluginProvider;
  
  static
  {
    if (!UpsightContextModule_ProvideUpsightContextFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UpsightContextModule_ProvideUpsightContextFactory(UpsightContextModule paramUpsightContextModule, Provider<Context> paramProvider, Provider<String> paramProvider1, Provider<String> paramProvider2, Provider<String> paramProvider3, Provider<UpsightDataStore> paramProvider4, Provider<UpsightLogger> paramProvider5)
  {
    assert (paramUpsightContextModule != null);
    this.module = paramUpsightContextModule;
    assert (paramProvider != null);
    this.baseContextProvider = paramProvider;
    assert (paramProvider1 != null);
    this.sdkPluginProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.appTokenProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.publicKeyProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.dataStoreProvider = paramProvider4;
    assert (paramProvider5 != null);
    this.loggerProvider = paramProvider5;
  }
  
  public static Factory<UpsightContext> create(UpsightContextModule paramUpsightContextModule, Provider<Context> paramProvider, Provider<String> paramProvider1, Provider<String> paramProvider2, Provider<String> paramProvider3, Provider<UpsightDataStore> paramProvider4, Provider<UpsightLogger> paramProvider5)
  {
    return new UpsightContextModule_ProvideUpsightContextFactory(paramUpsightContextModule, paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4, paramProvider5);
  }
  
  public UpsightContext get()
  {
    return (UpsightContext)Preconditions.checkNotNull(this.module.provideUpsightContext((Context)this.baseContextProvider.get(), (String)this.sdkPluginProvider.get(), (String)this.appTokenProvider.get(), (String)this.publicKeyProvider.get(), (UpsightDataStore)this.dataStoreProvider.get(), (UpsightLogger)this.loggerProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/UpsightContextModule_ProvideUpsightContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */