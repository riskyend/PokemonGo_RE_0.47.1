package com.upsight.android.managedvariables.internal.type;

import com.upsight.android.managedvariables.type.UpsightManagedString;
import com.upsight.android.persistence.annotation.UpsightStorableType;

class ManagedString
  extends UpsightManagedString
{
  static final String MODEL_TYPE = "com.upsight.uxm.string";
  
  ManagedString(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, paramString2, paramString3);
  }
  
  @UpsightStorableType("com.upsight.uxm.string")
  static class Model
    extends ManagedVariableModel<String>
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/ManagedString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */