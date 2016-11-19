package com.upsight.android.googlepushservices.internal;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.upsight.android.UpsightContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class GoogleCloudMessagingModule
{
  @Provides
  @Singleton
  public GoogleCloudMessaging provideGoogleCloudMessaging(UpsightContext paramUpsightContext)
  {
    return GoogleCloudMessaging.getInstance(paramUpsightContext);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/GoogleCloudMessagingModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */