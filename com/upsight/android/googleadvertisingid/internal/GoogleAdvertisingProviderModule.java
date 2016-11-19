package com.upsight.android.googleadvertisingid.internal;

import com.upsight.android.UpsightContext;
import com.upsight.android.logger.UpsightLogger;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class GoogleAdvertisingProviderModule
{
  private final UpsightContext mUpsight;
  
  public GoogleAdvertisingProviderModule(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  @Provides
  @Singleton
  public GooglePlayAdvertisingProvider provideGooglePlayAdvertisingProvider()
  {
    UpsightLogger localUpsightLogger = this.mUpsight.getLogger();
    return new GooglePlayAdvertisingProvider(this.mUpsight, localUpsightLogger);
  }
  
  @Provides
  @Singleton
  UpsightContext provideUpsightContext()
  {
    return this.mUpsight;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googleadvertisingid/internal/GoogleAdvertisingProviderModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */