package com.upsight.android.analytics.internal;

import com.upsight.android.analytics.UpsightAnalyticsApi;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AnalyticsApiModule_ProvideUpsightAnalyticsApiFactory
  implements Factory<UpsightAnalyticsApi>
{
  private final Provider<Analytics> analyticsProvider;
  private final AnalyticsApiModule module;
  
  static
  {
    if (!AnalyticsApiModule_ProvideUpsightAnalyticsApiFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public AnalyticsApiModule_ProvideUpsightAnalyticsApiFactory(AnalyticsApiModule paramAnalyticsApiModule, Provider<Analytics> paramProvider)
  {
    assert (paramAnalyticsApiModule != null);
    this.module = paramAnalyticsApiModule;
    assert (paramProvider != null);
    this.analyticsProvider = paramProvider;
  }
  
  public static Factory<UpsightAnalyticsApi> create(AnalyticsApiModule paramAnalyticsApiModule, Provider<Analytics> paramProvider)
  {
    return new AnalyticsApiModule_ProvideUpsightAnalyticsApiFactory(paramAnalyticsApiModule, paramProvider);
  }
  
  public UpsightAnalyticsApi get()
  {
    return (UpsightAnalyticsApi)Preconditions.checkNotNull(this.module.provideUpsightAnalyticsApi((Analytics)this.analyticsProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/AnalyticsApiModule_ProvideUpsightAnalyticsApiFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */