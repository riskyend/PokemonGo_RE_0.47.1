package com.upsight.android.googlepushservices.internal;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GooglePushServices_Factory
  implements Factory<GooglePushServices>
{
  private final Provider<PushConfigManager> pushConfigManagerProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!GooglePushServices_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public GooglePushServices_Factory(Provider<UpsightContext> paramProvider, Provider<PushConfigManager> paramProvider1)
  {
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.pushConfigManagerProvider = paramProvider1;
  }
  
  public static Factory<GooglePushServices> create(Provider<UpsightContext> paramProvider, Provider<PushConfigManager> paramProvider1)
  {
    return new GooglePushServices_Factory(paramProvider, paramProvider1);
  }
  
  public GooglePushServices get()
  {
    return new GooglePushServices((UpsightContext)this.upsightProvider.get(), (PushConfigManager)this.pushConfigManagerProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/GooglePushServices_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */