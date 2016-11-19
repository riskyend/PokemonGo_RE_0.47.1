package com.upsight.android.managedvariables.internal.type;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import rx.Scheduler;

public final class UxmModule_ProvideManagedVariableManagerFactory
  implements Factory<ManagedVariableManager>
{
  private final UxmModule module;
  private final Provider<Scheduler> schedulerProvider;
  private final Provider<UpsightContext> upsightProvider;
  private final Provider<UxmSchema> uxmSchemaProvider;
  
  static
  {
    if (!UxmModule_ProvideManagedVariableManagerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UxmModule_ProvideManagedVariableManagerFactory(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider, Provider<Scheduler> paramProvider1, Provider<UxmSchema> paramProvider2)
  {
    assert (paramUxmModule != null);
    this.module = paramUxmModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.schedulerProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.uxmSchemaProvider = paramProvider2;
  }
  
  public static Factory<ManagedVariableManager> create(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider, Provider<Scheduler> paramProvider1, Provider<UxmSchema> paramProvider2)
  {
    return new UxmModule_ProvideManagedVariableManagerFactory(paramUxmModule, paramProvider, paramProvider1, paramProvider2);
  }
  
  public ManagedVariableManager get()
  {
    return (ManagedVariableManager)Preconditions.checkNotNull(this.module.provideManagedVariableManager((UpsightContext)this.upsightProvider.get(), (Scheduler)this.schedulerProvider.get(), (UxmSchema)this.uxmSchemaProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmModule_ProvideManagedVariableManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */