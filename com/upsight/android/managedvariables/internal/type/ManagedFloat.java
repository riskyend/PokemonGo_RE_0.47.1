package com.upsight.android.managedvariables.internal.type;

import com.upsight.android.managedvariables.type.UpsightManagedFloat;
import com.upsight.android.persistence.annotation.UpsightStorableType;

class ManagedFloat
  extends UpsightManagedFloat
{
  static final String MODEL_TYPE = "com.upsight.uxm.float";
  
  ManagedFloat(String paramString, Float paramFloat1, Float paramFloat2)
  {
    super(paramString, paramFloat1, paramFloat2);
  }
  
  @UpsightStorableType("com.upsight.uxm.float")
  static class Model
    extends ManagedVariableModel<Float>
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/ManagedFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */