package com.upsight.android.internal.persistence.storable;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class StorableModule
{
  @Provides
  @Singleton
  public StorableInfoCache provideStorableInfoCache(Gson paramGson)
  {
    return new StorableInfoCache(paramGson);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */