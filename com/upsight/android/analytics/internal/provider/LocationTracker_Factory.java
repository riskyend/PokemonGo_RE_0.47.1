package com.upsight.android.analytics.internal.provider;

import com.upsight.android.UpsightContext;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class LocationTracker_Factory
  implements Factory<LocationTracker>
{
  private final MembersInjector<LocationTracker> locationTrackerMembersInjector;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!LocationTracker_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public LocationTracker_Factory(MembersInjector<LocationTracker> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    assert (paramMembersInjector != null);
    this.locationTrackerMembersInjector = paramMembersInjector;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<LocationTracker> create(MembersInjector<LocationTracker> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    return new LocationTracker_Factory(paramMembersInjector, paramProvider);
  }
  
  public LocationTracker get()
  {
    return (LocationTracker)MembersInjectors.injectMembers(this.locationTrackerMembersInjector, new LocationTracker((UpsightContext)this.upsightProvider.get()));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/LocationTracker_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */