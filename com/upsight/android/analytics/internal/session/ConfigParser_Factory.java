package com.upsight.android.analytics.internal.session;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ConfigParser_Factory
  implements Factory<ConfigParser>
{
  private final Provider<Gson> gsonProvider;
  
  static
  {
    if (!ConfigParser_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ConfigParser_Factory(Provider<Gson> paramProvider)
  {
    assert (paramProvider != null);
    this.gsonProvider = paramProvider;
  }
  
  public static Factory<ConfigParser> create(Provider<Gson> paramProvider)
  {
    return new ConfigParser_Factory(paramProvider);
  }
  
  public ConfigParser get()
  {
    return new ConfigParser((Gson)this.gsonProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/ConfigParser_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */