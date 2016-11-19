package com.upsight.android.managedvariables.internal.type;

import com.upsight.android.managedvariables.type.UpsightManagedBoolean;
import com.upsight.android.persistence.annotation.UpsightStorableType;

class ManagedBoolean
  extends UpsightManagedBoolean
{
  static final String MODEL_TYPE = "com.upsight.uxm.boolean";
  
  ManagedBoolean(String paramString, Boolean paramBoolean1, Boolean paramBoolean2)
  {
    super(paramString, paramBoolean1, paramBoolean2);
  }
  
  @UpsightStorableType("com.upsight.uxm.boolean")
  static class Model
    extends ManagedVariableModel<Boolean>
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/ManagedBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */