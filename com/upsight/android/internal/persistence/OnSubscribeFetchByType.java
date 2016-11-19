package com.upsight.android.internal.persistence;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.upsight.android.UpsightException;
import java.lang.ref.WeakReference;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

class OnSubscribeFetchByType
  implements Observable.OnSubscribe<Storable>
{
  private final WeakReference<Context> reference;
  private final String type;
  
  OnSubscribeFetchByType(Context paramContext, String paramString)
  {
    if (paramContext == null) {
      throw new IllegalArgumentException("Provided Context can not be null.");
    }
    if (TextUtils.isEmpty(paramString)) {
      throw new IllegalArgumentException("Provided type can not be empty or null.");
    }
    this.reference = new WeakReference(paramContext);
    this.type = paramString;
  }
  
  public void call(Subscriber<? super Storable> paramSubscriber)
  {
    Context localContext = (Context)this.reference.get();
    if (localContext == null) {
      paramSubscriber.onError(new IllegalStateException("Context has been reclaimed by Android."));
    }
    for (;;)
    {
      return;
      Object localObject3 = null;
      Cursor localCursor = null;
      Object localObject2 = localCursor;
      Object localObject1 = localObject3;
      try
      {
        Uri localUri = Content.getContentTypeUri(localContext, this.type);
        localObject2 = localCursor;
        localObject1 = localObject3;
        localCursor = localContext.getContentResolver().query(localUri, null, null, null, null);
        if (localCursor == null)
        {
          localObject2 = localCursor;
          localObject1 = localCursor;
          paramSubscriber.onError(new UpsightException("Unable to retrieve stored objects.", new Object[0]));
          if (localCursor == null) {
            continue;
          }
          localCursor.close();
          return;
        }
        for (;;)
        {
          localObject2 = localCursor;
          localObject1 = localCursor;
          if (!localCursor.moveToNext()) {
            break;
          }
          localObject2 = localCursor;
          localObject1 = localCursor;
          paramSubscriber.onNext(Storable.create(localCursor.getString(localCursor.getColumnIndex("_id")), localCursor.getString(localCursor.getColumnIndex("type")), localCursor.getString(localCursor.getColumnIndex("data"))));
        }
      }
      catch (Throwable localThrowable)
      {
        localObject1 = localObject2;
        paramSubscriber.onError(localThrowable);
        return;
        localObject2 = localThrowable;
        localObject1 = localThrowable;
        paramSubscriber.onCompleted();
        if (localThrowable == null) {
          continue;
        }
        localThrowable.close();
        return;
      }
      finally
      {
        if (localObject1 != null) {
          ((Cursor)localObject1).close();
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/OnSubscribeFetchByType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */