package com.upsight.android.internal.persistence;

import android.content.Context;
import android.text.TextUtils;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightException;
import com.upsight.android.internal.persistence.storable.StorableIdFactory;
import com.upsight.android.internal.persistence.storable.StorableIdentifierAccessor;
import com.upsight.android.internal.persistence.storable.StorableInfo;
import com.upsight.android.internal.persistence.storable.StorableInfoCache;
import com.upsight.android.internal.persistence.storable.StorableTypeAccessor;
import com.upsight.android.internal.persistence.storable.Storables;
import com.upsight.android.internal.persistence.subscription.Subscriptions;
import com.upsight.android.internal.util.LoggingListener;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightDataStoreListener;
import com.upsight.android.persistence.UpsightStorableSerializer;
import com.upsight.android.persistence.UpsightSubscription;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

class DataStore
  implements UpsightDataStore
{
  private final Bus mBus;
  private final Context mContext;
  private final StorableIdFactory mIdFactory;
  private final StorableInfoCache mInfoCache;
  private final Scheduler mObserveOnScheduler;
  private final Scheduler mSubscribeOnScheduler;
  
  DataStore(Context paramContext, StorableInfoCache paramStorableInfoCache, StorableIdFactory paramStorableIdFactory, Scheduler paramScheduler1, Scheduler paramScheduler2, Bus paramBus)
  {
    this.mContext = paramContext;
    this.mInfoCache = paramStorableInfoCache;
    this.mIdFactory = paramStorableIdFactory;
    this.mSubscribeOnScheduler = paramScheduler1;
    this.mObserveOnScheduler = paramScheduler2;
    this.mBus = paramBus;
  }
  
  public <T> UpsightSubscription fetch(Class<T> paramClass, final UpsightDataStoreListener<Set<T>> paramUpsightDataStoreListener)
  {
    if (paramClass == null) {
      throw new IllegalArgumentException("A non null class must be specified for fetch(..)");
    }
    if (paramUpsightDataStoreListener == null) {
      throw new IllegalArgumentException("A non null listener must be specified for fetch(..)");
    }
    Subscriptions.from(fetchObservable(paramClass).toList().subscribeOn(this.mSubscribeOnScheduler).observeOn(this.mObserveOnScheduler).subscribe(new Action1()new Action1
    {
      public void call(List<T> paramAnonymousList)
      {
        paramUpsightDataStoreListener.onSuccess(new HashSet(paramAnonymousList));
      }
    }, new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        paramUpsightDataStoreListener.onFailure(new UpsightException(paramAnonymousThrowable));
      }
    }));
  }
  
  public <T> UpsightSubscription fetch(Class<T> paramClass, Set<String> paramSet, final UpsightDataStoreListener<Set<T>> paramUpsightDataStoreListener)
  {
    Subscriptions.from(fetchObservable(paramClass, (String[])paramSet.toArray(new String[paramSet.size()])).toList().subscribeOn(this.mSubscribeOnScheduler).observeOn(this.mObserveOnScheduler).subscribe(new Action1()new Action1
    {
      public void call(List<T> paramAnonymousList)
      {
        paramUpsightDataStoreListener.onSuccess(new HashSet(paramAnonymousList));
      }
    }, new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        paramUpsightDataStoreListener.onFailure(new UpsightException(paramAnonymousThrowable));
      }
    }));
  }
  
  public <T> Observable<T> fetchObservable(Class<T> paramClass)
  {
    StorableInfo localStorableInfo;
    try
    {
      localStorableInfo = this.mInfoCache.get(paramClass);
      if (localStorableInfo == null) {
        throw new IllegalArgumentException(String.format("Class %s must be annotated with @%s", new Object[] { paramClass.getName(), Storable.class.getSimpleName() }));
      }
    }
    catch (UpsightException paramClass)
    {
      return Observable.error(paramClass);
    }
    String str = localStorableInfo.getStorableTypeAccessor().getType();
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException(String.format("Class %s must be annotated with @%s", new Object[] { paramClass.getSimpleName(), UpsightStorableType.class.getSimpleName() }));
    }
    paramClass = ContentObservables.fetch(this.mContext, str).lift(Storables.deserialize(localStorableInfo));
    return paramClass;
  }
  
  public <T> Observable<T> fetchObservable(Class<T> paramClass, String... paramVarArgs)
  {
    StorableInfo localStorableInfo;
    try
    {
      localStorableInfo = this.mInfoCache.get(paramClass);
      if (localStorableInfo == null) {
        throw new IllegalArgumentException(String.format("Class %s must be annotated with @%s", new Object[] { paramClass.getName(), Storable.class.getSimpleName() }));
      }
    }
    catch (UpsightException paramClass)
    {
      return Observable.error(paramClass);
    }
    paramClass = localStorableInfo.getStorableTypeAccessor().getType();
    paramClass = ContentObservables.fetch(this.mContext, paramClass, paramVarArgs).lift(Storables.deserialize(localStorableInfo));
    return paramClass;
  }
  
  public <T> UpsightSubscription remove(Class<T> paramClass, Set<String> paramSet)
  {
    return remove(paramClass, paramSet, new LoggingListener());
  }
  
  public <T> UpsightSubscription remove(Class<T> paramClass, Set<String> paramSet, final UpsightDataStoreListener<Set<T>> paramUpsightDataStoreListener)
  {
    Subscriptions.from(removeObservable(paramClass, (String[])paramSet.toArray(new String[paramSet.size()])).toList().subscribeOn(this.mSubscribeOnScheduler).observeOn(this.mObserveOnScheduler).subscribe(new Action1()new Action1
    {
      public void call(List<T> paramAnonymousList)
      {
        paramUpsightDataStoreListener.onSuccess(new HashSet(paramAnonymousList));
      }
    }, new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        paramUpsightDataStoreListener.onFailure(new UpsightException(paramAnonymousThrowable));
      }
    }));
  }
  
  public <T> UpsightSubscription remove(T paramT)
  {
    return remove(paramT, new LoggingListener());
  }
  
  public <T> UpsightSubscription remove(T paramT, final UpsightDataStoreListener<T> paramUpsightDataStoreListener)
  {
    if (paramUpsightDataStoreListener == null) {
      throw new IllegalArgumentException("Listener can not be null.");
    }
    Subscriptions.from(removeObservable(paramT).subscribeOn(this.mSubscribeOnScheduler).observeOn(this.mObserveOnScheduler).subscribe(new Action1()new Action1
    {
      public void call(T paramAnonymousT)
      {
        paramUpsightDataStoreListener.onSuccess(paramAnonymousT);
      }
    }, new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        paramUpsightDataStoreListener.onFailure(new UpsightException(paramAnonymousThrowable));
      }
    }));
  }
  
  public <T> Observable<T> removeObservable(Class<T> paramClass, String... paramVarArgs)
  {
    StorableInfo localStorableInfo;
    try
    {
      localStorableInfo = this.mInfoCache.get(paramClass);
      if (localStorableInfo == null) {
        throw new IllegalArgumentException(String.format("Class %s must be annotated with @%s", new Object[] { paramClass.getName(), Storable.class.getSimpleName() }));
      }
    }
    catch (UpsightException paramClass)
    {
      return Observable.error(paramClass);
    }
    paramClass = localStorableInfo.getStorableTypeAccessor().getType();
    paramClass = ContentObservables.fetch(this.mContext, paramClass, paramVarArgs).lift(Storables.deserialize(localStorableInfo)).flatMap(new Func1()
    {
      public Observable<T> call(T paramAnonymousT)
      {
        return DataStore.this.removeObservable(paramAnonymousT);
      }
    });
    return paramClass;
  }
  
  public <T> Observable<T> removeObservable(T paramT)
  {
    int i = 1;
    if (paramT == null) {
      throw new IllegalArgumentException("Object can not be null.");
    }
    Object localObject = paramT.getClass();
    for (;;)
    {
      try
      {
        StorableInfo localStorableInfo = this.mInfoCache.get((Class)localObject);
        if (localStorableInfo != null)
        {
          if (!TextUtils.isEmpty(localStorableInfo.getIdentifierAccessor().getId(paramT)))
          {
            if (i != 0)
            {
              localObject = localStorableInfo.getStorableTypeAccessor().getType(paramT);
              paramT = Observable.just(paramT);
              paramT.lift(Storables.serialize(localStorableInfo, this.mIdFactory)).flatMap(new Func1()
              {
                public Observable<Storable> call(Storable paramAnonymousStorable)
                {
                  return ContentObservables.remove(DataStore.this.mContext, paramAnonymousStorable);
                }
              }).zipWith(paramT, new Func2()
              {
                public T call(Storable paramAnonymousStorable, T paramAnonymousT)
                {
                  return paramAnonymousT;
                }
              }).doOnNext(Subscriptions.publishRemoved(this.mBus, (String)localObject));
            }
            return Observable.error(new UpsightException("Object must be stored before removal.", new Object[0]));
          }
        }
        else
        {
          paramT = Observable.error(new UpsightException("Class %s must be annotated with @%s", new Object[] { ((Class)localObject).getName(), Storable.class.getSimpleName() }));
          return paramT;
        }
      }
      catch (UpsightException paramT)
      {
        return Observable.error(paramT);
      }
      i = 0;
    }
  }
  
  public <T> void setSerializer(Class<T> paramClass, UpsightStorableSerializer<T> paramUpsightStorableSerializer)
  {
    if (paramClass == null) {
      throw new IllegalArgumentException("Class can not be null.");
    }
    if (paramUpsightStorableSerializer == null) {
      throw new IllegalArgumentException("UpsightStorableSerializer can not be null.");
    }
    this.mInfoCache.setSerializer(paramClass, paramUpsightStorableSerializer);
  }
  
  public <T> UpsightSubscription store(T paramT)
  {
    return store(paramT, new LoggingListener());
  }
  
  public <T> UpsightSubscription store(T paramT, final UpsightDataStoreListener<T> paramUpsightDataStoreListener)
  {
    Subscriptions.from(storeObservable(paramT).subscribeOn(this.mSubscribeOnScheduler).observeOn(this.mObserveOnScheduler).subscribe(new Action1()new Action1
    {
      public void call(T paramAnonymousT)
      {
        paramUpsightDataStoreListener.onSuccess(paramAnonymousT);
      }
    }, new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        paramUpsightDataStoreListener.onFailure(new UpsightException(paramAnonymousThrowable));
      }
    }));
  }
  
  public <T> Observable<T> storeObservable(T paramT)
  {
    final boolean bool = true;
    if (paramT == null) {
      throw new IllegalArgumentException("Attempting to store null object.");
    }
    Object localObject = paramT.getClass();
    StorableInfo localStorableInfo;
    try
    {
      localStorableInfo = this.mInfoCache.get((Class)localObject);
      if (localStorableInfo == null) {
        throw new IllegalArgumentException(String.format("Class %s must be annotated with @%s", new Object[] { ((Class)localObject).getName(), Storable.class.getSimpleName() }));
      }
    }
    catch (UpsightException paramT)
    {
      return Observable.error(paramT);
    }
    if (!TextUtils.isEmpty(localStorableInfo.getIdentifierAccessor().getId(paramT))) {}
    for (;;)
    {
      if (!bool) {
        localStorableInfo.getIdentifierAccessor().setId(paramT, this.mIdFactory.createObjectID());
      }
      localObject = Observable.just(paramT);
      localObject = ((Observable)localObject).lift(Storables.serialize(localStorableInfo, this.mIdFactory)).flatMap(new Func1()
      {
        public Observable<Storable> call(Storable paramAnonymousStorable)
        {
          if (bool) {
            return ContentObservables.update(DataStore.this.mContext, paramAnonymousStorable);
          }
          return ContentObservables.insert(DataStore.this.mContext, paramAnonymousStorable);
        }
      }).zipWith((Observable)localObject, new Func2()
      {
        public T call(Storable paramAnonymousStorable, T paramAnonymousT)
        {
          return paramAnonymousT;
        }
      });
      paramT = localStorableInfo.getStorableTypeAccessor().getType(paramT);
      if (bool) {
        return ((Observable)localObject).doOnNext(Subscriptions.publishUpdated(this.mBus, paramT));
      }
      paramT = ((Observable)localObject).doOnNext(Subscriptions.publishCreated(this.mBus, paramT));
      return paramT;
      bool = false;
    }
  }
  
  public UpsightSubscription subscribe(Object paramObject)
  {
    return Subscriptions.from(Subscriptions.toObservable(this.mBus).observeOn(this.mObserveOnScheduler).subscribe(Subscriptions.create(paramObject)));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/DataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */