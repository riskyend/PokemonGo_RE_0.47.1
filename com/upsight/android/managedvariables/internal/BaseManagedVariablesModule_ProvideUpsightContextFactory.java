package com.upsight.android.managedvariables.internal;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class BaseManagedVariablesModule_ProvideUpsightContextFactory
  implements Factory<UpsightContext>
{
  private final BaseManagedVariablesModule module;
  
  static
  {
    if (!BaseManagedVariablesModule_ProvideUpsightContextFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BaseManagedVariablesModule_ProvideUpsightContextFactory(BaseManagedVariablesModule paramBaseManagedVariablesModule)
  {
    assert (paramBaseManagedVariablesModule != null);
    this.module = paramBaseManagedVariablesModule;
  }
  
  public static Factory<UpsightContext> create(BaseManagedVariablesModule paramBaseManagedVariablesModule)
  {
    return new BaseManagedVariablesModule_ProvideUpsightContextFactory(paramBaseManagedVariablesModule);
  }
  
  public UpsightContext get()
  {
    return (UpsightContext)Preconditions.checkNotNull(this.module.provideUpsightContext(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/BaseManagedVariablesModule_ProvideUpsightContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */