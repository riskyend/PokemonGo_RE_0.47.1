package com.upsight.android;

import com.upsight.android.googleadvertisingid.internal.GooglePlayAdvertisingProvider;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UpsightGoogleAdvertisingIdExtension_MembersInjector
  implements MembersInjector<UpsightGoogleAdvertisingIdExtension>
{
  private final Provider<GooglePlayAdvertisingProvider> mAdvertisingIdProvider;
  
  static
  {
    if (!UpsightGoogleAdvertisingIdExtension_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UpsightGoogleAdvertisingIdExtension_MembersInjector(Provider<GooglePlayAdvertisingProvider> paramProvider)
  {
    assert (paramProvider != null);
    this.mAdvertisingIdProvider = paramProvider;
  }
  
  public static MembersInjector<UpsightGoogleAdvertisingIdExtension> create(Provider<GooglePlayAdvertisingProvider> paramProvider)
  {
    return new UpsightGoogleAdvertisingIdExtension_MembersInjector(paramProvider);
  }
  
  public static void injectMAdvertisingIdProvider(UpsightGoogleAdvertisingIdExtension paramUpsightGoogleAdvertisingIdExtension, Provider<GooglePlayAdvertisingProvider> paramProvider)
  {
    paramUpsightGoogleAdvertisingIdExtension.mAdvertisingIdProvider = ((GooglePlayAdvertisingProvider)paramProvider.get());
  }
  
  public void injectMembers(UpsightGoogleAdvertisingIdExtension paramUpsightGoogleAdvertisingIdExtension)
  {
    if (paramUpsightGoogleAdvertisingIdExtension == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramUpsightGoogleAdvertisingIdExtension.mAdvertisingIdProvider = ((GooglePlayAdvertisingProvider)this.mAdvertisingIdProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightGoogleAdvertisingIdExtension_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */