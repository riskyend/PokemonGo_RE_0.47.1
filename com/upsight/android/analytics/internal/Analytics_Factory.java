package com.upsight.android.analytics.internal;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightGooglePlayHelper;
import com.upsight.android.analytics.UpsightLifeCycleTracker;
import com.upsight.android.analytics.internal.association.AssociationManager;
import com.upsight.android.analytics.internal.dispatcher.schema.SchemaSelectorBuilder;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightOptOutStatus;
import com.upsight.android.analytics.provider.UpsightUserAttributes;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class Analytics_Factory
  implements Factory<Analytics>
{
  private final Provider<AssociationManager> associationManagerProvider;
  private final Provider<UpsightGooglePlayHelper> googlePlayHelperProvider;
  private final Provider<UpsightLifeCycleTracker> lifeCycleTrackerProvider;
  private final Provider<UpsightLocationTracker> locationTrackerProvider;
  private final Provider<UpsightOptOutStatus> optOutStatusProvider;
  private final Provider<SchemaSelectorBuilder> schemaSelectorProvider;
  private final Provider<SessionManager> sessionManagerProvider;
  private final Provider<UpsightContext> upsightProvider;
  private final Provider<UpsightUserAttributes> userAttributesProvider;
  
  static
  {
    if (!Analytics_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public Analytics_Factory(Provider<UpsightContext> paramProvider, Provider<UpsightLifeCycleTracker> paramProvider1, Provider<SessionManager> paramProvider2, Provider<SchemaSelectorBuilder> paramProvider3, Provider<AssociationManager> paramProvider4, Provider<UpsightOptOutStatus> paramProvider5, Provider<UpsightLocationTracker> paramProvider6, Provider<UpsightUserAttributes> paramProvider7, Provider<UpsightGooglePlayHelper> paramProvider8)
  {
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.lifeCycleTrackerProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.sessionManagerProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.schemaSelectorProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.associationManagerProvider = paramProvider4;
    assert (paramProvider5 != null);
    this.optOutStatusProvider = paramProvider5;
    assert (paramProvider6 != null);
    this.locationTrackerProvider = paramProvider6;
    assert (paramProvider7 != null);
    this.userAttributesProvider = paramProvider7;
    assert (paramProvider8 != null);
    this.googlePlayHelperProvider = paramProvider8;
  }
  
  public static Factory<Analytics> create(Provider<UpsightContext> paramProvider, Provider<UpsightLifeCycleTracker> paramProvider1, Provider<SessionManager> paramProvider2, Provider<SchemaSelectorBuilder> paramProvider3, Provider<AssociationManager> paramProvider4, Provider<UpsightOptOutStatus> paramProvider5, Provider<UpsightLocationTracker> paramProvider6, Provider<UpsightUserAttributes> paramProvider7, Provider<UpsightGooglePlayHelper> paramProvider8)
  {
    return new Analytics_Factory(paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4, paramProvider5, paramProvider6, paramProvider7, paramProvider8);
  }
  
  public Analytics get()
  {
    return new Analytics((UpsightContext)this.upsightProvider.get(), (UpsightLifeCycleTracker)this.lifeCycleTrackerProvider.get(), (SessionManager)this.sessionManagerProvider.get(), (SchemaSelectorBuilder)this.schemaSelectorProvider.get(), (AssociationManager)this.associationManagerProvider.get(), (UpsightOptOutStatus)this.optOutStatusProvider.get(), (UpsightLocationTracker)this.locationTrackerProvider.get(), (UpsightUserAttributes)this.userAttributesProvider.get(), (UpsightGooglePlayHelper)this.googlePlayHelperProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/Analytics_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */