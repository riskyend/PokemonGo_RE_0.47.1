package com.upsight.android.internal.persistence.storable;

import com.upsight.android.UpsightException;

public abstract interface StorableIdentifierAccessor
{
  public abstract String getId(Object paramObject)
    throws UpsightException;
  
  public abstract void setId(Object paramObject, String paramString)
    throws UpsightException;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableIdentifierAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */