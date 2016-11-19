package com.upsight.android.internal.util;

import android.util.Log;
import com.upsight.android.UpsightException;
import com.upsight.android.persistence.UpsightDataStoreListener;

public final class LoggingListener<T>
  implements UpsightDataStoreListener<T>
{
  public void onFailure(UpsightException paramUpsightException)
  {
    Log.e("Upsight", "Uncaught Exception within Upsight SDK.", paramUpsightException);
  }
  
  public void onSuccess(T paramT) {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/util/LoggingListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */