package com.upsight.android.internal.persistence.storable;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorableModule_ProvideStorableInfoCacheFactory
  implements Factory<StorableInfoCache>
{
  private final Provider<Gson> gsonProvider;
  private final StorableModule module;
  
  static
  {
    if (!StorableModule_ProvideStorableInfoCacheFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public StorableModule_ProvideStorableInfoCacheFactory(StorableModule paramStorableModule, Provider<Gson> paramProvider)
  {
    assert (paramStorableModule != null);
    this.module = paramStorableModule;
    assert (paramProvider != null);
    this.gsonProvider = paramProvider;
  }
  
  public static Factory<StorableInfoCache> create(StorableModule paramStorableModule, Provider<Gson> paramProvider)
  {
    return new StorableModule_ProvideStorableInfoCacheFactory(paramStorableModule, paramProvider);
  }
  
  public StorableInfoCache get()
  {
    return (StorableInfoCache)Preconditions.checkNotNull(this.module.provideStorableInfoCache((Gson)this.gsonProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableModule_ProvideStorableInfoCacheFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */