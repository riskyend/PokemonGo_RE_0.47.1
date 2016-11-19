package com.upsight.android.googlepushservices;

import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.UpsightBillboard;
import com.upsight.android.marketing.UpsightBillboard.Handler;

public abstract interface UpsightGooglePushServicesApi
{
  public abstract UpsightBillboard createPushBillboard(UpsightContext paramUpsightContext, UpsightBillboard.Handler paramHandler)
    throws IllegalArgumentException, IllegalStateException;
  
  public abstract void register(UpsightGooglePushServices.OnRegisterListener paramOnRegisterListener);
  
  public abstract void unregister(UpsightGooglePushServices.OnUnregisterListener paramOnUnregisterListener);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/UpsightGooglePushServicesApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */