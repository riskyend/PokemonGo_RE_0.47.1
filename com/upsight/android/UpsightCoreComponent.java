package com.upsight.android;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.squareup.otto.Bus;
import com.upsight.android.persistence.UpsightDataStore;
import javax.inject.Named;
import rx.Scheduler;

public abstract interface UpsightCoreComponent
{
  public abstract Context applicationContext();
  
  @Named("background")
  public abstract UpsightDataStore backgroundDataStore();
  
  public abstract Bus bus();
  
  public abstract Gson gson();
  
  public abstract JsonParser jsonParser();
  
  @Named("callback")
  public abstract Scheduler observeOnScheduler();
  
  @Named("execution")
  public abstract Scheduler subscribeOnScheduler();
  
  public abstract UpsightContext upsightContext();
  
  public static abstract interface Factory
  {
    public abstract UpsightCoreComponent create(Context paramContext);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightCoreComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */