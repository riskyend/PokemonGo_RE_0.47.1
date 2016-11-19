package com.upsight.android.analytics.internal.session;

import android.app.Application.ActivityLifecycleCallbacks;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LifecycleTrackerModule_ProvideUpsightLifeCycleCallbacksFactory
  implements Factory<Application.ActivityLifecycleCallbacks>
{
  private final Provider<ActivityLifecycleTracker> handlerProvider;
  private final LifecycleTrackerModule module;
  
  static
  {
    if (!LifecycleTrackerModule_ProvideUpsightLifeCycleCallbacksFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public LifecycleTrackerModule_ProvideUpsightLifeCycleCallbacksFactory(LifecycleTrackerModule paramLifecycleTrackerModule, Provider<ActivityLifecycleTracker> paramProvider)
  {
    assert (paramLifecycleTrackerModule != null);
    this.module = paramLifecycleTrackerModule;
    assert (paramProvider != null);
    this.handlerProvider = paramProvider;
  }
  
  public static Factory<Application.ActivityLifecycleCallbacks> create(LifecycleTrackerModule paramLifecycleTrackerModule, Provider<ActivityLifecycleTracker> paramProvider)
  {
    return new LifecycleTrackerModule_ProvideUpsightLifeCycleCallbacksFactory(paramLifecycleTrackerModule, paramProvider);
  }
  
  public Application.ActivityLifecycleCallbacks get()
  {
    return (Application.ActivityLifecycleCallbacks)Preconditions.checkNotNull(this.module.provideUpsightLifeCycleCallbacks((ActivityLifecycleTracker)this.handlerProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/LifecycleTrackerModule_ProvideUpsightLifeCycleCallbacksFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */