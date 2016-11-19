package com.upsight.android.analytics.internal.provider;

import com.upsight.android.UpsightContext;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class OptOutStatus_Factory
  implements Factory<OptOutStatus>
{
  private final MembersInjector<OptOutStatus> optOutStatusMembersInjector;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!OptOutStatus_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public OptOutStatus_Factory(MembersInjector<OptOutStatus> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    assert (paramMembersInjector != null);
    this.optOutStatusMembersInjector = paramMembersInjector;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<OptOutStatus> create(MembersInjector<OptOutStatus> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    return new OptOutStatus_Factory(paramMembersInjector, paramProvider);
  }
  
  public OptOutStatus get()
  {
    return (OptOutStatus)MembersInjectors.injectMembers(this.optOutStatusMembersInjector, new OptOutStatus((UpsightContext)this.upsightProvider.get()));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/OptOutStatus_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */