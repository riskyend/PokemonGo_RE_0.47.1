package com.upsight.android.internal.persistence;

import android.content.Context;
import rx.Observable;

final class ContentObservables
{
  public static Observable<Storable> fetch(Context paramContext, String paramString)
  {
    return Observable.create(new OnSubscribeFetchByType(paramContext, paramString)).onBackpressureBuffer();
  }
  
  public static Observable<Storable> fetch(Context paramContext, String paramString, String[] paramArrayOfString)
  {
    return Observable.create(new OnSubscribeFetchById(paramContext, paramString, paramArrayOfString)).onBackpressureBuffer();
  }
  
  public static Observable<Storable> insert(Context paramContext, Storable paramStorable)
  {
    return Observable.create(new OnSubscribeInsert(paramContext, paramStorable));
  }
  
  public static Observable<Storable> remove(Context paramContext, Storable paramStorable)
  {
    return Observable.create(new OnSubscribeRemove(paramContext, paramStorable));
  }
  
  public static Observable<Storable> update(Context paramContext, Storable paramStorable)
  {
    return Observable.create(new OnSubscribeUpdate(paramContext, paramStorable));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/ContentObservables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */