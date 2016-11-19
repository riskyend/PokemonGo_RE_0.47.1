package com.upsight.android.analytics.internal.dispatcher;

import com.google.gson.Gson;
import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ConfigParser_Factory
  implements Factory<ConfigParser>
{
  private final Provider<Gson> gsonProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!ConfigParser_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ConfigParser_Factory(Provider<UpsightContext> paramProvider, Provider<Gson> paramProvider1)
  {
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.gsonProvider = paramProvider1;
  }
  
  public static Factory<ConfigParser> create(Provider<UpsightContext> paramProvider, Provider<Gson> paramProvider1)
  {
    return new ConfigParser_Factory(paramProvider, paramProvider1);
  }
  
  public ConfigParser get()
  {
    return new ConfigParser((UpsightContext)this.upsightProvider.get(), (Gson)this.gsonProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/ConfigParser_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */