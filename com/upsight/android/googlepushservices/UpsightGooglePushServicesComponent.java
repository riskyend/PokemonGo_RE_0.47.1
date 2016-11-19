package com.upsight.android.googlepushservices;

import com.upsight.android.UpsightExtension.BaseComponent;
import com.upsight.android.UpsightGooglePushServicesExtension;
import com.upsight.android.googlepushservices.internal.PushClickIntentService;
import com.upsight.android.googlepushservices.internal.PushIntentService;

public abstract interface UpsightGooglePushServicesComponent
  extends UpsightExtension.BaseComponent<UpsightGooglePushServicesExtension>
{
  public abstract void inject(PushClickIntentService paramPushClickIntentService);
  
  public abstract void inject(PushIntentService paramPushIntentService);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/UpsightGooglePushServicesComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */