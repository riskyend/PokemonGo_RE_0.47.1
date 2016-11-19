package com.upsight.android.internal.persistence.storable;

import com.upsight.android.UpsightException;

public abstract interface StorableTypeAccessor<T>
{
  public abstract String getType()
    throws UpsightException;
  
  public abstract String getType(T paramT)
    throws UpsightException;
  
  public abstract boolean isDynamic();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableTypeAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */