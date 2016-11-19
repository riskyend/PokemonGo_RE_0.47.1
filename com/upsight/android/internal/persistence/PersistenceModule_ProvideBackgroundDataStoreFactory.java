package com.upsight.android.internal.persistence;

import android.content.Context;
import com.squareup.otto.Bus;
import com.upsight.android.internal.persistence.storable.StorableIdFactory;
import com.upsight.android.internal.persistence.storable.StorableInfoCache;
import com.upsight.android.persistence.UpsightDataStore;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import rx.Scheduler;

public final class PersistenceModule_ProvideBackgroundDataStoreFactory
  implements Factory<UpsightDataStore>
{
  private final Provider<Bus> busProvider;
  private final Provider<Context> contextProvider;
  private final Provider<StorableIdFactory> idFactoryProvider;
  private final Provider<StorableInfoCache> infoCacheProvider;
  private final PersistenceModule module;
  private final Provider<Scheduler> subscribeOnSchedulerProvider;
  
  static
  {
    if (!PersistenceModule_ProvideBackgroundDataStoreFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public PersistenceModule_ProvideBackgroundDataStoreFactory(PersistenceModule paramPersistenceModule, Provider<Context> paramProvider, Provider<Scheduler> paramProvider1, Provider<StorableIdFactory> paramProvider2, Provider<StorableInfoCache> paramProvider3, Provider<Bus> paramProvider4)
  {
    assert (paramPersistenceModule != null);
    this.module = paramPersistenceModule;
    assert (paramProvider != null);
    this.contextProvider = paramProvider;
    assert (paramProvider1 != null);
    this.subscribeOnSchedulerProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.idFactoryProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.infoCacheProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.busProvider = paramProvider4;
  }
  
  public static Factory<UpsightDataStore> create(PersistenceModule paramPersistenceModule, Provider<Context> paramProvider, Provider<Scheduler> paramProvider1, Provider<StorableIdFactory> paramProvider2, Provider<StorableInfoCache> paramProvider3, Provider<Bus> paramProvider4)
  {
    return new PersistenceModule_ProvideBackgroundDataStoreFactory(paramPersistenceModule, paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4);
  }
  
  public UpsightDataStore get()
  {
    return (UpsightDataStore)Preconditions.checkNotNull(this.module.provideBackgroundDataStore((Context)this.contextProvider.get(), (Scheduler)this.subscribeOnSchedulerProvider.get(), (StorableIdFactory)this.idFactoryProvider.get(), (StorableInfoCache)this.infoCacheProvider.get(), (Bus)this.busProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/PersistenceModule_ProvideBackgroundDataStoreFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */