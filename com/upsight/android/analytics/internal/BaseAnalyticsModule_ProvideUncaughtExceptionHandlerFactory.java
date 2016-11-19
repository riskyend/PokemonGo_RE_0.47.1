package com.upsight.android.analytics.internal;

import com.upsight.android.internal.util.Opt;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class BaseAnalyticsModule_ProvideUncaughtExceptionHandlerFactory
  implements Factory<Opt<Thread.UncaughtExceptionHandler>>
{
  private final BaseAnalyticsModule module;
  
  static
  {
    if (!BaseAnalyticsModule_ProvideUncaughtExceptionHandlerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BaseAnalyticsModule_ProvideUncaughtExceptionHandlerFactory(BaseAnalyticsModule paramBaseAnalyticsModule)
  {
    assert (paramBaseAnalyticsModule != null);
    this.module = paramBaseAnalyticsModule;
  }
  
  public static Factory<Opt<Thread.UncaughtExceptionHandler>> create(BaseAnalyticsModule paramBaseAnalyticsModule)
  {
    return new BaseAnalyticsModule_ProvideUncaughtExceptionHandlerFactory(paramBaseAnalyticsModule);
  }
  
  public Opt<Thread.UncaughtExceptionHandler> get()
  {
    return (Opt)Preconditions.checkNotNull(this.module.provideUncaughtExceptionHandler(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/BaseAnalyticsModule_ProvideUncaughtExceptionHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */