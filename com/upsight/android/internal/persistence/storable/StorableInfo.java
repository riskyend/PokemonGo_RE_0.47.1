package com.upsight.android.internal.persistence.storable;

import com.upsight.android.persistence.UpsightStorableSerializer;

public final class StorableInfo<T>
{
  private final StorableIdentifierAccessor mIdentifierAccessor;
  private final UpsightStorableSerializer<T> mSerializer;
  private final StorableTypeAccessor<T> mStorableType;
  
  StorableInfo(StorableTypeAccessor<T> paramStorableTypeAccessor, UpsightStorableSerializer<T> paramUpsightStorableSerializer, StorableIdentifierAccessor paramStorableIdentifierAccessor)
  {
    if (paramStorableTypeAccessor == null) {
      throw new IllegalArgumentException("StorableTypeAccessor type can not be null.");
    }
    if (paramUpsightStorableSerializer == null) {
      throw new IllegalArgumentException("UpsightStorableSerializer can not be null.");
    }
    if (paramStorableIdentifierAccessor == null) {
      throw new IllegalArgumentException("StorableIdentifierAccessor can not be null.");
    }
    this.mStorableType = paramStorableTypeAccessor;
    this.mSerializer = paramUpsightStorableSerializer;
    this.mIdentifierAccessor = paramStorableIdentifierAccessor;
  }
  
  public static final <T> StorableInfo<T> create(StorableTypeAccessor<T> paramStorableTypeAccessor, UpsightStorableSerializer<T> paramUpsightStorableSerializer, StorableIdentifierAccessor paramStorableIdentifierAccessor)
  {
    return new StorableInfo(paramStorableTypeAccessor, paramUpsightStorableSerializer, paramStorableIdentifierAccessor);
  }
  
  public UpsightStorableSerializer<T> getDeserializer()
  {
    return this.mSerializer;
  }
  
  public StorableIdentifierAccessor getIdentifierAccessor()
  {
    return this.mIdentifierAccessor;
  }
  
  public StorableTypeAccessor<T> getStorableTypeAccessor()
  {
    return this.mStorableType;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */