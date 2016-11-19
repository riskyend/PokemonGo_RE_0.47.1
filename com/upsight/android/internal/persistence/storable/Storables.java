package com.upsight.android.internal.persistence.storable;

import com.upsight.android.internal.persistence.Storable;
import rx.Observable.Operator;

public final class Storables
{
  public static <T> Observable.Operator<T, Storable> deserialize(StorableInfo<T> paramStorableInfo)
  {
    return new OperatorDeserialize(paramStorableInfo);
  }
  
  public static <T> Observable.Operator<Storable, T> serialize(StorableInfo<T> paramStorableInfo, StorableIdFactory paramStorableIdFactory)
  {
    return new OperatorSerialize(paramStorableInfo, paramStorableIdFactory);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/Storables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */