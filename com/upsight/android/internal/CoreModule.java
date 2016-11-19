package com.upsight.android.internal;

import com.upsight.android.internal.logger.LoggerModule;
import com.upsight.android.internal.persistence.PersistenceModule;
import com.upsight.android.internal.persistence.storable.StorableModule;
import dagger.Module;

@Module(includes={UpsightContextModule.class, ContextModule.class, PropertiesModule.class, JsonModule.class, SchedulersModule.class, StorableModule.class, PersistenceModule.class, LoggerModule.class})
public final class CoreModule {}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/CoreModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */