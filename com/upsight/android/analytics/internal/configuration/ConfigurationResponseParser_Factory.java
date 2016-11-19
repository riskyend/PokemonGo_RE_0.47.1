package com.upsight.android.analytics.internal.configuration;

import com.google.gson.Gson;
import com.upsight.android.analytics.internal.session.SessionManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ConfigurationResponseParser_Factory
  implements Factory<ConfigurationResponseParser>
{
  private final Provider<Gson> gsonProvider;
  private final Provider<SessionManager> sessionManagerProvider;
  
  static
  {
    if (!ConfigurationResponseParser_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ConfigurationResponseParser_Factory(Provider<Gson> paramProvider, Provider<SessionManager> paramProvider1)
  {
    assert (paramProvider != null);
    this.gsonProvider = paramProvider;
    assert (paramProvider1 != null);
    this.sessionManagerProvider = paramProvider1;
  }
  
  public static Factory<ConfigurationResponseParser> create(Provider<Gson> paramProvider, Provider<SessionManager> paramProvider1)
  {
    return new ConfigurationResponseParser_Factory(paramProvider, paramProvider1);
  }
  
  public ConfigurationResponseParser get()
  {
    return new ConfigurationResponseParser((Gson)this.gsonProvider.get(), (SessionManager)this.sessionManagerProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/configuration/ConfigurationResponseParser_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */