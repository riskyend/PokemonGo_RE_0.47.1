package com.upsight.android;

import com.upsight.android.googlepushservices.UpsightGooglePushServicesApi;
import com.upsight.android.googlepushservices.internal.PushConfigManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UpsightGooglePushServicesExtension_MembersInjector
  implements MembersInjector<UpsightGooglePushServicesExtension>
{
  private final Provider<PushConfigManager> mPushConfigManagerProvider;
  private final Provider<UpsightGooglePushServicesApi> mUpsightPushProvider;
  
  static
  {
    if (!UpsightGooglePushServicesExtension_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UpsightGooglePushServicesExtension_MembersInjector(Provider<UpsightGooglePushServicesApi> paramProvider, Provider<PushConfigManager> paramProvider1)
  {
    assert (paramProvider != null);
    this.mUpsightPushProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mPushConfigManagerProvider = paramProvider1;
  }
  
  public static MembersInjector<UpsightGooglePushServicesExtension> create(Provider<UpsightGooglePushServicesApi> paramProvider, Provider<PushConfigManager> paramProvider1)
  {
    return new UpsightGooglePushServicesExtension_MembersInjector(paramProvider, paramProvider1);
  }
  
  public static void injectMPushConfigManager(UpsightGooglePushServicesExtension paramUpsightGooglePushServicesExtension, Provider<PushConfigManager> paramProvider)
  {
    paramUpsightGooglePushServicesExtension.mPushConfigManager = ((PushConfigManager)paramProvider.get());
  }
  
  public static void injectMUpsightPush(UpsightGooglePushServicesExtension paramUpsightGooglePushServicesExtension, Provider<UpsightGooglePushServicesApi> paramProvider)
  {
    paramUpsightGooglePushServicesExtension.mUpsightPush = ((UpsightGooglePushServicesApi)paramProvider.get());
  }
  
  public void injectMembers(UpsightGooglePushServicesExtension paramUpsightGooglePushServicesExtension)
  {
    if (paramUpsightGooglePushServicesExtension == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramUpsightGooglePushServicesExtension.mUpsightPush = ((UpsightGooglePushServicesApi)this.mUpsightPushProvider.get());
    paramUpsightGooglePushServicesExtension.mPushConfigManager = ((PushConfigManager)this.mPushConfigManagerProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightGooglePushServicesExtension_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */