package com.upsight.android.googleadvertisingid.internal;

import com.upsight.android.UpsightGoogleAdvertisingIdExtension;
import com.upsight.android.UpsightGoogleAdvertisingIdExtension_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerGoogleAdvertisingProviderComponent
  implements GoogleAdvertisingProviderComponent
{
  private Provider<GooglePlayAdvertisingProvider> provideGooglePlayAdvertisingProvider;
  private MembersInjector<UpsightGoogleAdvertisingIdExtension> upsightGoogleAdvertisingIdExtensionMembersInjector;
  
  static
  {
    if (!DaggerGoogleAdvertisingProviderComponent.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private DaggerGoogleAdvertisingProviderComponent(Builder paramBuilder)
  {
    assert (paramBuilder != null);
    initialize(paramBuilder);
  }
  
  public static Builder builder()
  {
    return new Builder(null);
  }
  
  private void initialize(Builder paramBuilder)
  {
    this.provideGooglePlayAdvertisingProvider = DoubleCheck.provider(GoogleAdvertisingProviderModule_ProvideGooglePlayAdvertisingProviderFactory.create(paramBuilder.googleAdvertisingProviderModule));
    this.upsightGoogleAdvertisingIdExtensionMembersInjector = UpsightGoogleAdvertisingIdExtension_MembersInjector.create(this.provideGooglePlayAdvertisingProvider);
  }
  
  public void inject(UpsightGoogleAdvertisingIdExtension paramUpsightGoogleAdvertisingIdExtension)
  {
    this.upsightGoogleAdvertisingIdExtensionMembersInjector.injectMembers(paramUpsightGoogleAdvertisingIdExtension);
  }
  
  public static final class Builder
  {
    private GoogleAdvertisingProviderModule googleAdvertisingProviderModule;
    
    public GoogleAdvertisingProviderComponent build()
    {
      if (this.googleAdvertisingProviderModule == null) {
        throw new IllegalStateException(GoogleAdvertisingProviderModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerGoogleAdvertisingProviderComponent(this, null);
    }
    
    public Builder googleAdvertisingProviderModule(GoogleAdvertisingProviderModule paramGoogleAdvertisingProviderModule)
    {
      this.googleAdvertisingProviderModule = ((GoogleAdvertisingProviderModule)Preconditions.checkNotNull(paramGoogleAdvertisingProviderModule));
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googleadvertisingid/internal/DaggerGoogleAdvertisingProviderComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */