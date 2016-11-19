package com.upsight.android.internal.persistence.storable;

import com.upsight.android.UpsightException;

class StorableStaticTypeAccessor<T>
  implements StorableTypeAccessor<T>
{
  private final String mType;
  
  public StorableStaticTypeAccessor(String paramString)
  {
    this.mType = paramString;
  }
  
  public String getType()
    throws UpsightException
  {
    return this.mType;
  }
  
  public String getType(T paramT)
    throws UpsightException
  {
    return this.mType;
  }
  
  public boolean isDynamic()
  {
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableStaticTypeAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */