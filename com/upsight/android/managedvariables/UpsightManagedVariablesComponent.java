package com.upsight.android.managedvariables;

import com.upsight.android.UpsightExtension.BaseComponent;
import com.upsight.android.UpsightManagedVariablesExtension;
import com.upsight.android.managedvariables.internal.type.UxmSchema;

public abstract interface UpsightManagedVariablesComponent
  extends UpsightExtension.BaseComponent<UpsightManagedVariablesExtension>
{
  public abstract UxmSchema uxmSchema();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/UpsightManagedVariablesComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */