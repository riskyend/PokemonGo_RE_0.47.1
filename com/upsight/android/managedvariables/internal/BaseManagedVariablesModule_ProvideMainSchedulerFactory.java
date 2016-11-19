package com.upsight.android.managedvariables.internal;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import rx.Scheduler;

public final class BaseManagedVariablesModule_ProvideMainSchedulerFactory
  implements Factory<Scheduler>
{
  private final BaseManagedVariablesModule module;
  
  static
  {
    if (!BaseManagedVariablesModule_ProvideMainSchedulerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BaseManagedVariablesModule_ProvideMainSchedulerFactory(BaseManagedVariablesModule paramBaseManagedVariablesModule)
  {
    assert (paramBaseManagedVariablesModule != null);
    this.module = paramBaseManagedVariablesModule;
  }
  
  public static Factory<Scheduler> create(BaseManagedVariablesModule paramBaseManagedVariablesModule)
  {
    return new BaseManagedVariablesModule_ProvideMainSchedulerFactory(paramBaseManagedVariablesModule);
  }
  
  public Scheduler get()
  {
    return (Scheduler)Preconditions.checkNotNull(this.module.provideMainScheduler(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/BaseManagedVariablesModule_ProvideMainSchedulerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */