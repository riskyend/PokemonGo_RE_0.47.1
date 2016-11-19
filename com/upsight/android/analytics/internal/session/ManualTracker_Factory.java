package com.upsight.android.analytics.internal.session;

import com.upsight.android.UpsightContext;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class ManualTracker_Factory
  implements Factory<ManualTracker>
{
  private final MembersInjector<ManualTracker> manualTrackerMembersInjector;
  private final Provider<SessionManager> sessionManagerProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!ManualTracker_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ManualTracker_Factory(MembersInjector<ManualTracker> paramMembersInjector, Provider<SessionManager> paramProvider, Provider<UpsightContext> paramProvider1)
  {
    assert (paramMembersInjector != null);
    this.manualTrackerMembersInjector = paramMembersInjector;
    assert (paramProvider != null);
    this.sessionManagerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.upsightProvider = paramProvider1;
  }
  
  public static Factory<ManualTracker> create(MembersInjector<ManualTracker> paramMembersInjector, Provider<SessionManager> paramProvider, Provider<UpsightContext> paramProvider1)
  {
    return new ManualTracker_Factory(paramMembersInjector, paramProvider, paramProvider1);
  }
  
  public ManualTracker get()
  {
    return (ManualTracker)MembersInjectors.injectMembers(this.manualTrackerMembersInjector, new ManualTracker((SessionManager)this.sessionManagerProvider.get(), (UpsightContext)this.upsightProvider.get()));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/ManualTracker_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */