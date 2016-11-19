package com.upsight.android.managedvariables.internal;

import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import com.upsight.android.managedvariables.internal.type.ManagedVariableManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ManagedVariables_Factory
  implements Factory<ManagedVariables>
{
  private final Provider<ManagedVariableManager> managedVariableManagerProvider;
  private final Provider<UpsightUserExperience> userExperienceProvider;
  
  static
  {
    if (!ManagedVariables_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ManagedVariables_Factory(Provider<ManagedVariableManager> paramProvider, Provider<UpsightUserExperience> paramProvider1)
  {
    assert (paramProvider != null);
    this.managedVariableManagerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.userExperienceProvider = paramProvider1;
  }
  
  public static Factory<ManagedVariables> create(Provider<ManagedVariableManager> paramProvider, Provider<UpsightUserExperience> paramProvider1)
  {
    return new ManagedVariables_Factory(paramProvider, paramProvider1);
  }
  
  public ManagedVariables get()
  {
    return new ManagedVariables((ManagedVariableManager)this.managedVariableManagerProvider.get(), (UpsightUserExperience)this.userExperienceProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/ManagedVariables_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */