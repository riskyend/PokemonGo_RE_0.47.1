package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ResponseParser_Factory
  implements Factory<ResponseParser>
{
  private final Provider<Gson> gsonProvider;
  
  static
  {
    if (!ResponseParser_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ResponseParser_Factory(Provider<Gson> paramProvider)
  {
    assert (paramProvider != null);
    this.gsonProvider = paramProvider;
  }
  
  public static Factory<ResponseParser> create(Provider<Gson> paramProvider)
  {
    return new ResponseParser_Factory(paramProvider);
  }
  
  public ResponseParser get()
  {
    return new ResponseParser((Gson)this.gsonProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */