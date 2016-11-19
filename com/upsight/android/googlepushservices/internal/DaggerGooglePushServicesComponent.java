package com.upsight.android.googlepushservices.internal;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightGooglePushServicesExtension;
import com.upsight.android.UpsightGooglePushServicesExtension_MembersInjector;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.googlepushservices.UpsightGooglePushServicesApi;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerGooglePushServicesComponent
  implements GooglePushServicesComponent
{
  private Provider<GooglePushServices> googlePushServicesProvider;
  private Provider<GoogleCloudMessaging> provideGoogleCloudMessagingProvider;
  private Provider<UpsightGooglePushServicesApi> provideGooglePushServicesApiProvider;
  private Provider<PushConfigManager> providePushConfigManagerProvider;
  private Provider<SessionManager> provideSessionManagerProvider;
  private Provider<UpsightContext> provideUpsightContextProvider;
  private MembersInjector<PushClickIntentService> pushClickIntentServiceMembersInjector;
  private MembersInjector<PushIntentService> pushIntentServiceMembersInjector;
  private MembersInjector<UpsightGooglePushServicesExtension> upsightGooglePushServicesExtensionMembersInjector;
  
  static
  {
    if (!DaggerGooglePushServicesComponent.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private DaggerGooglePushServicesComponent(Builder paramBuilder)
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
    this.provideUpsightContextProvider = DoubleCheck.provider(PushModule_ProvideUpsightContextFactory.create(paramBuilder.pushModule));
    this.providePushConfigManagerProvider = DoubleCheck.provider(PushModule_ProvidePushConfigManagerFactory.create(paramBuilder.pushModule, this.provideUpsightContextProvider));
    this.googlePushServicesProvider = DoubleCheck.provider(GooglePushServices_Factory.create(this.provideUpsightContextProvider, this.providePushConfigManagerProvider));
    this.provideGooglePushServicesApiProvider = DoubleCheck.provider(PushModule_ProvideGooglePushServicesApiFactory.create(paramBuilder.pushModule, this.googlePushServicesProvider));
    this.upsightGooglePushServicesExtensionMembersInjector = UpsightGooglePushServicesExtension_MembersInjector.create(this.provideGooglePushServicesApiProvider, this.providePushConfigManagerProvider);
    this.provideGoogleCloudMessagingProvider = DoubleCheck.provider(GoogleCloudMessagingModule_ProvideGoogleCloudMessagingFactory.create(paramBuilder.googleCloudMessagingModule, this.provideUpsightContextProvider));
    this.pushIntentServiceMembersInjector = PushIntentService_MembersInjector.create(this.provideGoogleCloudMessagingProvider, this.provideUpsightContextProvider);
    this.provideSessionManagerProvider = DoubleCheck.provider(PushModule_ProvideSessionManagerFactory.create(paramBuilder.pushModule, this.provideUpsightContextProvider));
    this.pushClickIntentServiceMembersInjector = PushClickIntentService_MembersInjector.create(this.provideSessionManagerProvider);
  }
  
  public void inject(UpsightGooglePushServicesExtension paramUpsightGooglePushServicesExtension)
  {
    this.upsightGooglePushServicesExtensionMembersInjector.injectMembers(paramUpsightGooglePushServicesExtension);
  }
  
  public void inject(PushClickIntentService paramPushClickIntentService)
  {
    this.pushClickIntentServiceMembersInjector.injectMembers(paramPushClickIntentService);
  }
  
  public void inject(PushIntentService paramPushIntentService)
  {
    this.pushIntentServiceMembersInjector.injectMembers(paramPushIntentService);
  }
  
  public static final class Builder
  {
    private GoogleCloudMessagingModule googleCloudMessagingModule;
    private PushModule pushModule;
    
    public GooglePushServicesComponent build()
    {
      if (this.pushModule == null) {
        throw new IllegalStateException(PushModule.class.getCanonicalName() + " must be set");
      }
      if (this.googleCloudMessagingModule == null) {
        this.googleCloudMessagingModule = new GoogleCloudMessagingModule();
      }
      return new DaggerGooglePushServicesComponent(this, null);
    }
    
    public Builder googleCloudMessagingModule(GoogleCloudMessagingModule paramGoogleCloudMessagingModule)
    {
      this.googleCloudMessagingModule = ((GoogleCloudMessagingModule)Preconditions.checkNotNull(paramGoogleCloudMessagingModule));
      return this;
    }
    
    @Deprecated
    public Builder googlePushServicesModule(GooglePushServicesModule paramGooglePushServicesModule)
    {
      Preconditions.checkNotNull(paramGooglePushServicesModule);
      return this;
    }
    
    public Builder pushModule(PushModule paramPushModule)
    {
      this.pushModule = ((PushModule)Preconditions.checkNotNull(paramPushModule));
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/DaggerGooglePushServicesComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */