package com.upsight.android.persistence;

import java.util.Set;
import rx.Observable;

public abstract interface UpsightDataStore
{
  public abstract <T> UpsightSubscription fetch(Class<T> paramClass, UpsightDataStoreListener<Set<T>> paramUpsightDataStoreListener);
  
  public abstract <T> UpsightSubscription fetch(Class<T> paramClass, Set<String> paramSet, UpsightDataStoreListener<Set<T>> paramUpsightDataStoreListener);
  
  public abstract <T> Observable<T> fetchObservable(Class<T> paramClass);
  
  public abstract <T> Observable<T> fetchObservable(Class<T> paramClass, String... paramVarArgs);
  
  public abstract <T> UpsightSubscription remove(Class<T> paramClass, Set<String> paramSet);
  
  public abstract <T> UpsightSubscription remove(Class<T> paramClass, Set<String> paramSet, UpsightDataStoreListener<Set<T>> paramUpsightDataStoreListener);
  
  public abstract <T> UpsightSubscription remove(T paramT);
  
  public abstract <T> UpsightSubscription remove(T paramT, UpsightDataStoreListener<T> paramUpsightDataStoreListener);
  
  public abstract <T> Observable<T> removeObservable(Class<T> paramClass, String... paramVarArgs);
  
  public abstract <T> Observable<T> removeObservable(T paramT);
  
  public abstract <T> void setSerializer(Class<T> paramClass, UpsightStorableSerializer<T> paramUpsightStorableSerializer);
  
  public abstract <T> UpsightSubscription store(T paramT);
  
  public abstract <T> UpsightSubscription store(T paramT, UpsightDataStoreListener<T> paramUpsightDataStoreListener);
  
  public abstract <T> Observable<T> storeObservable(T paramT);
  
  public abstract UpsightSubscription subscribe(Object paramObject);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/persistence/UpsightDataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */