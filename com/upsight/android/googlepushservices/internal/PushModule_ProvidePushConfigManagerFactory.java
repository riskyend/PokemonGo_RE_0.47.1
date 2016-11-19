package com.upsight.android.googlepushservices.internal;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class PushModule_ProvidePushConfigManagerFactory
  implements Factory<PushConfigManager>
{
  private final PushModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!PushModule_ProvidePushConfigManagerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public PushModule_ProvidePushConfigManagerFactory(PushModule paramPushModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramPushModule != null);
    this.module = paramPushModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<PushConfigManager> create(PushModule paramPushModule, Provider<UpsightContext> paramProvider)
  {
    return new PushModule_ProvidePushConfigManagerFactory(paramPushModule, paramProvider);
  }
  
  public PushConfigManager get()
  {
    return (PushConfigManager)Preconditions.checkNotNull(this.module.providePushConfigManager((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/PushModule_ProvidePushConfigManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */