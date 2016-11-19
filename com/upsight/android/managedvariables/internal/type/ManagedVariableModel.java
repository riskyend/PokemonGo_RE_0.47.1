package com.upsight.android.managedvariables.internal.type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;

abstract class ManagedVariableModel<T>
{
  @UpsightStorableIdentifier
  String id;
  @Expose
  @SerializedName("tag")
  String tag;
  @Expose
  @SerializedName("value")
  T value;
  
  public String getTag()
  {
    return this.tag;
  }
  
  public T getValue()
  {
    return (T)this.value;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/ManagedVariableModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */