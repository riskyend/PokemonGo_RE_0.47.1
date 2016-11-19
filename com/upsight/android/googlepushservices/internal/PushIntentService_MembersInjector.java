package com.upsight.android.googlepushservices.internal;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.upsight.android.UpsightContext;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class PushIntentService_MembersInjector
  implements MembersInjector<PushIntentService>
{
  private final Provider<GoogleCloudMessaging> mGcmProvider;
  private final Provider<UpsightContext> mUpsightProvider;
  
  static
  {
    if (!PushIntentService_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public PushIntentService_MembersInjector(Provider<GoogleCloudMessaging> paramProvider, Provider<UpsightContext> paramProvider1)
  {
    assert (paramProvider != null);
    this.mGcmProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mUpsightProvider = paramProvider1;
  }
  
  public static MembersInjector<PushIntentService> create(Provider<GoogleCloudMessaging> paramProvider, Provider<UpsightContext> paramProvider1)
  {
    return new PushIntentService_MembersInjector(paramProvider, paramProvider1);
  }
  
  public static void injectMGcm(PushIntentService paramPushIntentService, Provider<GoogleCloudMessaging> paramProvider)
  {
    paramPushIntentService.mGcm = ((GoogleCloudMessaging)paramProvider.get());
  }
  
  public static void injectMUpsight(PushIntentService paramPushIntentService, Provider<UpsightContext> paramProvider)
  {
    paramPushIntentService.mUpsight = ((UpsightContext)paramProvider.get());
  }
  
  public void injectMembers(PushIntentService paramPushIntentService)
  {
    if (paramPushIntentService == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramPushIntentService.mGcm = ((GoogleCloudMessaging)this.mGcmProvider.get());
    paramPushIntentService.mUpsight = ((UpsightContext)this.mUpsightProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/PushIntentService_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */