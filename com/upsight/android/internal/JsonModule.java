package com.upsight.android.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class JsonModule
{
  @Provides
  @Singleton
  Gson provideGson()
  {
    return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  }
  
  @Provides
  @Singleton
  JsonParser provideJsonParser()
  {
    return new JsonParser();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/JsonModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */