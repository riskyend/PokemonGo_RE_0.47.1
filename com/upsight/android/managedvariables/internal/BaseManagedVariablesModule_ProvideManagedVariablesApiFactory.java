package com.upsight.android.managedvariables.internal;

import com.upsight.android.managedvariables.UpsightManagedVariablesApi;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import com.upsight.android.managedvariables.internal.type.ManagedVariableManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseManagedVariablesModule_ProvideManagedVariablesApiFactory
  implements Factory<UpsightManagedVariablesApi>
{
  private final Provider<ManagedVariableManager> managedVariableManagerProvider;
  private final BaseManagedVariablesModule module;
  private final Provider<UpsightUserExperience> userExperienceProvider;
  
  static
  {
    if (!BaseManagedVariablesModule_ProvideManagedVariablesApiFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BaseManagedVariablesModule_ProvideManagedVariablesApiFactory(BaseManagedVariablesModule paramBaseManagedVariablesModule, Provider<ManagedVariableManager> paramProvider, Provider<UpsightUserExperience> paramProvider1)
  {
    assert (paramBaseManagedVariablesModule != null);
    this.module = paramBaseManagedVariablesModule;
    assert (paramProvider != null);
    this.managedVariableManagerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.userExperienceProvider = paramProvider1;
  }
  
  public static Factory<UpsightManagedVariablesApi> create(BaseManagedVariablesModule paramBaseManagedVariablesModule, Provider<ManagedVariableManager> paramProvider, Provider<UpsightUserExperience> paramProvider1)
  {
    return new BaseManagedVariablesModule_ProvideManagedVariablesApiFactory(paramBaseManagedVariablesModule, paramProvider, paramProvider1);
  }
  
  public UpsightManagedVariablesApi get()
  {
    return (UpsightManagedVariablesApi)Preconditions.checkNotNull(this.module.provideManagedVariablesApi((ManagedVariableManager)this.managedVariableManagerProvider.get(), (UpsightUserExperience)this.userExperienceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/BaseManagedVariablesModule_ProvideManagedVariablesApiFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */