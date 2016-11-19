package com.upsight.android.googleadvertisingid.internal;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GoogleAdvertisingProviderModule_ProvideGooglePlayAdvertisingProviderFactory
  implements Factory<GooglePlayAdvertisingProvider>
{
  private final GoogleAdvertisingProviderModule module;
  
  static
  {
    if (!GoogleAdvertisingProviderModule_ProvideGooglePlayAdvertisingProviderFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public GoogleAdvertisingProviderModule_ProvideGooglePlayAdvertisingProviderFactory(GoogleAdvertisingProviderModule paramGoogleAdvertisingProviderModule)
  {
    assert (paramGoogleAdvertisingProviderModule != null);
    this.module = paramGoogleAdvertisingProviderModule;
  }
  
  public static Factory<GooglePlayAdvertisingProvider> create(GoogleAdvertisingProviderModule paramGoogleAdvertisingProviderModule)
  {
    return new GoogleAdvertisingProviderModule_ProvideGooglePlayAdvertisingProviderFactory(paramGoogleAdvertisingProviderModule);
  }
  
  public GooglePlayAdvertisingProvider get()
  {
    return (GooglePlayAdvertisingProvider)Preconditions.checkNotNull(this.module.provideGooglePlayAdvertisingProvider(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googleadvertisingid/internal/GoogleAdvertisingProviderModule_ProvideGooglePlayAdvertisingProviderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */