package com.upsight.android.internal;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import rx.Scheduler;

public final class SchedulersModule_ProvideObserveOnSchedulerFactory
  implements Factory<Scheduler>
{
  private final SchedulersModule module;
  
  static
  {
    if (!SchedulersModule_ProvideObserveOnSchedulerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public SchedulersModule_ProvideObserveOnSchedulerFactory(SchedulersModule paramSchedulersModule)
  {
    assert (paramSchedulersModule != null);
    this.module = paramSchedulersModule;
  }
  
  public static Factory<Scheduler> create(SchedulersModule paramSchedulersModule)
  {
    return new SchedulersModule_ProvideObserveOnSchedulerFactory(paramSchedulersModule);
  }
  
  public Scheduler get()
  {
    return (Scheduler)Preconditions.checkNotNull(this.module.provideObserveOnScheduler(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/SchedulersModule_ProvideObserveOnSchedulerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */