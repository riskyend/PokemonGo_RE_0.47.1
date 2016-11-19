package com.upsight.android.managedvariables.internal;

import com.upsight.android.managedvariables.UpsightManagedVariablesApi;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import com.upsight.android.managedvariables.experience.UpsightUserExperience.Handler;
import com.upsight.android.managedvariables.internal.type.ManagedVariableManager;
import com.upsight.android.managedvariables.type.UpsightManagedVariable;
import com.upsight.android.managedvariables.type.UpsightManagedVariable.Listener;
import com.upsight.android.persistence.UpsightSubscription;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class ManagedVariables
  implements UpsightManagedVariablesApi
{
  private ManagedVariableManager mManagedVariableManager;
  private UpsightUserExperience mUserExperience;
  
  @Inject
  public ManagedVariables(ManagedVariableManager paramManagedVariableManager, UpsightUserExperience paramUpsightUserExperience)
  {
    this.mManagedVariableManager = paramManagedVariableManager;
    this.mUserExperience = paramUpsightUserExperience;
  }
  
  public <T extends UpsightManagedVariable> T fetch(Class<T> paramClass, String paramString)
  {
    return (UpsightManagedVariable)this.mManagedVariableManager.fetch(paramClass, paramString);
  }
  
  public <T extends UpsightManagedVariable> UpsightSubscription fetch(Class<T> paramClass, String paramString, UpsightManagedVariable.Listener<T> paramListener)
  {
    return this.mManagedVariableManager.fetch(paramClass, paramString, paramListener);
  }
  
  public void registerUserExperienceHandler(UpsightUserExperience.Handler paramHandler)
  {
    this.mUserExperience.registerHandler(paramHandler);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/ManagedVariables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */