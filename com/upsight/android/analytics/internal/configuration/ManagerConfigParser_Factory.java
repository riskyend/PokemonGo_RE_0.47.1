package com.upsight.android.analytics.internal.configuration;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ManagerConfigParser_Factory
  implements Factory<ManagerConfigParser>
{
  private final Provider<Gson> gsonProvider;
  
  static
  {
    if (!ManagerConfigParser_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ManagerConfigParser_Factory(Provider<Gson> paramProvider)
  {
    assert (paramProvider != null);
    this.gsonProvider = paramProvider;
  }
  
  public static Factory<ManagerConfigParser> create(Provider<Gson> paramProvider)
  {
    return new ManagerConfigParser_Factory(paramProvider);
  }
  
  public ManagerConfigParser get()
  {
    return new ManagerConfigParser((Gson)this.gsonProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/configuration/ManagerConfigParser_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */