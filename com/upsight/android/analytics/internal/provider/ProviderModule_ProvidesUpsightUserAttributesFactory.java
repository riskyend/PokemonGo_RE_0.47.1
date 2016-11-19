package com.upsight.android.analytics.internal.provider;

import com.upsight.android.analytics.provider.UpsightUserAttributes;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ProviderModule_ProvidesUpsightUserAttributesFactory
  implements Factory<UpsightUserAttributes>
{
  private final ProviderModule module;
  private final Provider<UserAttributes> userAttributesProvider;
  
  static
  {
    if (!ProviderModule_ProvidesUpsightUserAttributesFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ProviderModule_ProvidesUpsightUserAttributesFactory(ProviderModule paramProviderModule, Provider<UserAttributes> paramProvider)
  {
    assert (paramProviderModule != null);
    this.module = paramProviderModule;
    assert (paramProvider != null);
    this.userAttributesProvider = paramProvider;
  }
  
  public static Factory<UpsightUserAttributes> create(ProviderModule paramProviderModule, Provider<UserAttributes> paramProvider)
  {
    return new ProviderModule_ProvidesUpsightUserAttributesFactory(paramProviderModule, paramProvider);
  }
  
  public UpsightUserAttributes get()
  {
    return (UpsightUserAttributes)Preconditions.checkNotNull(this.module.providesUpsightUserAttributes((UserAttributes)this.userAttributesProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/ProviderModule_ProvidesUpsightUserAttributesFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */