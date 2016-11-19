package com.upsight.android.analytics.internal;

import com.google.gson.Gson;
import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ConfigGsonModule_ProvideConfigGsonFactory
  implements Factory<Gson>
{
  private final ConfigGsonModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!ConfigGsonModule_ProvideConfigGsonFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ConfigGsonModule_ProvideConfigGsonFactory(ConfigGsonModule paramConfigGsonModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramConfigGsonModule != null);
    this.module = paramConfigGsonModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<Gson> create(ConfigGsonModule paramConfigGsonModule, Provider<UpsightContext> paramProvider)
  {
    return new ConfigGsonModule_ProvideConfigGsonFactory(paramConfigGsonModule, paramProvider);
  }
  
  public Gson get()
  {
    return (Gson)Preconditions.checkNotNull(this.module.provideConfigGson((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/ConfigGsonModule_ProvideConfigGsonFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */