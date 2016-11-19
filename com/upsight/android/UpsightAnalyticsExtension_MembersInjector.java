package com.upsight.android;

import android.app.Application.ActivityLifecycleCallbacks;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.analytics.internal.association.AssociationManager;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.internal.util.Opt;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UpsightAnalyticsExtension_MembersInjector
  implements MembersInjector<UpsightAnalyticsExtension>
{
  private final Provider<UpsightAnalyticsApi> mAnalyticsProvider;
  private final Provider<AssociationManager> mAssociationManagerProvider;
  private final Provider<Clock> mClockProvider;
  private final Provider<Opt<Thread.UncaughtExceptionHandler>> mUncaughtExceptionHandlerProvider;
  private final Provider<Application.ActivityLifecycleCallbacks> mUpsightLifeCycleCallbacksProvider;
  
  static
  {
    if (!UpsightAnalyticsExtension_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UpsightAnalyticsExtension_MembersInjector(Provider<Opt<Thread.UncaughtExceptionHandler>> paramProvider, Provider<UpsightAnalyticsApi> paramProvider1, Provider<Clock> paramProvider2, Provider<Application.ActivityLifecycleCallbacks> paramProvider3, Provider<AssociationManager> paramProvider4)
  {
    assert (paramProvider != null);
    this.mUncaughtExceptionHandlerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mAnalyticsProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.mClockProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.mUpsightLifeCycleCallbacksProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.mAssociationManagerProvider = paramProvider4;
  }
  
  public static MembersInjector<UpsightAnalyticsExtension> create(Provider<Opt<Thread.UncaughtExceptionHandler>> paramProvider, Provider<UpsightAnalyticsApi> paramProvider1, Provider<Clock> paramProvider2, Provider<Application.ActivityLifecycleCallbacks> paramProvider3, Provider<AssociationManager> paramProvider4)
  {
    return new UpsightAnalyticsExtension_MembersInjector(paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4);
  }
  
  public static void injectMAnalytics(UpsightAnalyticsExtension paramUpsightAnalyticsExtension, Provider<UpsightAnalyticsApi> paramProvider)
  {
    paramUpsightAnalyticsExtension.mAnalytics = ((UpsightAnalyticsApi)paramProvider.get());
  }
  
  public static void injectMAssociationManager(UpsightAnalyticsExtension paramUpsightAnalyticsExtension, Provider<AssociationManager> paramProvider)
  {
    paramUpsightAnalyticsExtension.mAssociationManager = ((AssociationManager)paramProvider.get());
  }
  
  public static void injectMClock(UpsightAnalyticsExtension paramUpsightAnalyticsExtension, Provider<Clock> paramProvider)
  {
    paramUpsightAnalyticsExtension.mClock = ((Clock)paramProvider.get());
  }
  
  public static void injectMUncaughtExceptionHandler(UpsightAnalyticsExtension paramUpsightAnalyticsExtension, Provider<Opt<Thread.UncaughtExceptionHandler>> paramProvider)
  {
    paramUpsightAnalyticsExtension.mUncaughtExceptionHandler = ((Opt)paramProvider.get());
  }
  
  public static void injectMUpsightLifeCycleCallbacks(UpsightAnalyticsExtension paramUpsightAnalyticsExtension, Provider<Application.ActivityLifecycleCallbacks> paramProvider)
  {
    paramUpsightAnalyticsExtension.mUpsightLifeCycleCallbacks = ((Application.ActivityLifecycleCallbacks)paramProvider.get());
  }
  
  public void injectMembers(UpsightAnalyticsExtension paramUpsightAnalyticsExtension)
  {
    if (paramUpsightAnalyticsExtension == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramUpsightAnalyticsExtension.mUncaughtExceptionHandler = ((Opt)this.mUncaughtExceptionHandlerProvider.get());
    paramUpsightAnalyticsExtension.mAnalytics = ((UpsightAnalyticsApi)this.mAnalyticsProvider.get());
    paramUpsightAnalyticsExtension.mClock = ((Clock)this.mClockProvider.get());
    paramUpsightAnalyticsExtension.mUpsightLifeCycleCallbacks = ((Application.ActivityLifecycleCallbacks)this.mUpsightLifeCycleCallbacksProvider.get());
    paramUpsightAnalyticsExtension.mAssociationManager = ((AssociationManager)this.mAssociationManagerProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightAnalyticsExtension_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */