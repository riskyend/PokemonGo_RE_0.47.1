package com.upsight.android;

import com.upsight.android.googlepushservices.UpsightGooglePushServices.OnRegisterListener;
import com.upsight.android.googlepushservices.UpsightGooglePushServicesApi;
import com.upsight.android.googlepushservices.UpsightGooglePushServicesComponent;
import com.upsight.android.googlepushservices.internal.DaggerGooglePushServicesComponent;
import com.upsight.android.googlepushservices.internal.DaggerGooglePushServicesComponent.Builder;
import com.upsight.android.googlepushservices.internal.PushConfigManager;
import com.upsight.android.googlepushservices.internal.PushConfigManager.Config;
import com.upsight.android.googlepushservices.internal.PushModule;
import com.upsight.android.logger.UpsightLogger;
import java.io.IOException;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;

public class UpsightGooglePushServicesExtension
  extends UpsightExtension<UpsightGooglePushServicesComponent, UpsightGooglePushServicesApi>
{
  public static final String EXTENSION_NAME = "com.upsight.extension.googlepushservices";
  private static final String LOG_TAG = UpsightGooglePushServicesExtension.class.getSimpleName();
  @Inject
  PushConfigManager mPushConfigManager;
  @Inject
  UpsightGooglePushServicesApi mUpsightPush;
  
  public UpsightGooglePushServicesApi getApi()
  {
    return this.mUpsightPush;
  }
  
  protected void onPostCreate(final UpsightContext paramUpsightContext)
  {
    try
    {
      this.mPushConfigManager.fetchCurrentConfigObservable().subscribeOn(paramUpsightContext.getCoreComponent().subscribeOnScheduler()).observeOn(paramUpsightContext.getCoreComponent().observeOnScheduler()).subscribe(new Action1()
      {
        public void call(PushConfigManager.Config paramAnonymousConfig)
        {
          if (paramAnonymousConfig.autoRegister)
          {
            UpsightGooglePushServicesExtension.this.mUpsightPush.register(new UpsightGooglePushServices.OnRegisterListener()
            {
              public void onFailure(UpsightException paramAnonymous2UpsightException)
              {
                UpsightGooglePushServicesExtension.1.this.val$upsight.getLogger().e(UpsightGooglePushServicesExtension.LOG_TAG, "Failed to auto-register for push notifications", new Object[] { paramAnonymous2UpsightException });
              }
              
              public void onSuccess(String paramAnonymous2String)
              {
                UpsightGooglePushServicesExtension.1.this.val$upsight.getLogger().d(UpsightGooglePushServicesExtension.LOG_TAG, "Auto-registered for push notifications with registrationId=" + paramAnonymous2String, new Object[0]);
              }
            });
            return;
          }
          paramUpsightContext.getLogger().d(UpsightGooglePushServicesExtension.LOG_TAG, "Skipping auto-registration of push notifications", new Object[0]);
        }
      });
      return;
    }
    catch (IOException localIOException)
    {
      paramUpsightContext.getLogger().e(LOG_TAG, "Failed to fetch push configurations", new Object[] { localIOException });
    }
  }
  
  protected UpsightGooglePushServicesComponent onResolve(UpsightContext paramUpsightContext)
  {
    return DaggerGooglePushServicesComponent.builder().pushModule(new PushModule(paramUpsightContext)).build();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightGooglePushServicesExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */