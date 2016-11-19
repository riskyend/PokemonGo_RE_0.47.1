package com.upsight.android.analytics.internal;

import com.upsight.android.UpsightContext;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class AnalyticsContext_Factory
  implements Factory<AnalyticsContext>
{
  private final MembersInjector<AnalyticsContext> analyticsContextMembersInjector;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!AnalyticsContext_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public AnalyticsContext_Factory(MembersInjector<AnalyticsContext> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    assert (paramMembersInjector != null);
    this.analyticsContextMembersInjector = paramMembersInjector;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<AnalyticsContext> create(MembersInjector<AnalyticsContext> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    return new AnalyticsContext_Factory(paramMembersInjector, paramProvider);
  }
  
  public AnalyticsContext get()
  {
    return (AnalyticsContext)MembersInjectors.injectMembers(this.analyticsContextMembersInjector, new AnalyticsContext((UpsightContext)this.upsightProvider.get()));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/AnalyticsContext_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */