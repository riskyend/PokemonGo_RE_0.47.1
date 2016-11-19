package com.upsight.android.googlepushservices.internal;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoogleCloudMessagingModule_ProvideGoogleCloudMessagingFactory
  implements Factory<GoogleCloudMessaging>
{
  private final GoogleCloudMessagingModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!GoogleCloudMessagingModule_ProvideGoogleCloudMessagingFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public GoogleCloudMessagingModule_ProvideGoogleCloudMessagingFactory(GoogleCloudMessagingModule paramGoogleCloudMessagingModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramGoogleCloudMessagingModule != null);
    this.module = paramGoogleCloudMessagingModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<GoogleCloudMessaging> create(GoogleCloudMessagingModule paramGoogleCloudMessagingModule, Provider<UpsightContext> paramProvider)
  {
    return new GoogleCloudMessagingModule_ProvideGoogleCloudMessagingFactory(paramGoogleCloudMessagingModule, paramProvider);
  }
  
  public GoogleCloudMessaging get()
  {
    return (GoogleCloudMessaging)Preconditions.checkNotNull(this.module.provideGoogleCloudMessaging((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/GoogleCloudMessagingModule_ProvideGoogleCloudMessagingFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */