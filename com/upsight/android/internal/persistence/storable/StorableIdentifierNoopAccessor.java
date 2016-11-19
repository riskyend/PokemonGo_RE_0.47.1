package com.upsight.android.internal.persistence.storable;

import com.upsight.android.UpsightException;

class StorableIdentifierNoopAccessor
  implements StorableIdentifierAccessor
{
  public String getId(Object paramObject)
    throws UpsightException
  {
    return null;
  }
  
  public void setId(Object paramObject, String paramString)
    throws UpsightException
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableIdentifierNoopAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */