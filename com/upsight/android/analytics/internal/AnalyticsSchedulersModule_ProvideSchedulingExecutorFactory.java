package com.upsight.android.analytics.internal;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import rx.Scheduler;

public final class AnalyticsSchedulersModule_ProvideSchedulingExecutorFactory
  implements Factory<Scheduler>
{
  private final AnalyticsSchedulersModule module;
  
  static
  {
    if (!AnalyticsSchedulersModule_ProvideSchedulingExecutorFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public AnalyticsSchedulersModule_ProvideSchedulingExecutorFactory(AnalyticsSchedulersModule paramAnalyticsSchedulersModule)
  {
    assert (paramAnalyticsSchedulersModule != null);
    this.module = paramAnalyticsSchedulersModule;
  }
  
  public static Factory<Scheduler> create(AnalyticsSchedulersModule paramAnalyticsSchedulersModule)
  {
    return new AnalyticsSchedulersModule_ProvideSchedulingExecutorFactory(paramAnalyticsSchedulersModule);
  }
  
  public Scheduler get()
  {
    return (Scheduler)Preconditions.checkNotNull(this.module.provideSchedulingExecutor(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/AnalyticsSchedulersModule_ProvideSchedulingExecutorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */