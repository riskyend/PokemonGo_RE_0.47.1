package com.upsight.android.managedvariables;

import com.upsight.android.managedvariables.experience.UpsightUserExperience.Handler;
import com.upsight.android.managedvariables.type.UpsightManagedVariable;
import com.upsight.android.managedvariables.type.UpsightManagedVariable.Listener;
import com.upsight.android.persistence.UpsightSubscription;

public abstract interface UpsightManagedVariablesApi
{
  public abstract <T extends UpsightManagedVariable> T fetch(Class<T> paramClass, String paramString);
  
  public abstract <T extends UpsightManagedVariable> UpsightSubscription fetch(Class<T> paramClass, String paramString, UpsightManagedVariable.Listener<T> paramListener);
  
  public abstract void registerUserExperienceHandler(UpsightUserExperience.Handler paramHandler);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/UpsightManagedVariablesApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */