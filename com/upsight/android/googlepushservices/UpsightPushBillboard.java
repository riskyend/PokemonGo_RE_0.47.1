package com.upsight.android.googlepushservices;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightGooglePushServicesExtension;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightBillboard;
import com.upsight.android.marketing.UpsightBillboard.Handler;

public class UpsightPushBillboard
{
  public static UpsightBillboard create(UpsightContext paramUpsightContext, UpsightBillboard.Handler paramHandler)
    throws IllegalArgumentException, IllegalStateException
  {
    UpsightGooglePushServicesExtension localUpsightGooglePushServicesExtension = (UpsightGooglePushServicesExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.googlepushservices");
    if (localUpsightGooglePushServicesExtension != null) {
      return localUpsightGooglePushServicesExtension.getApi().createPushBillboard(paramUpsightContext, paramHandler);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.googlepushservices must be registered in your Android Manifest", new Object[0]);
    return new NoOpBillboard(null);
  }
  
  private static class NoOpBillboard
    extends UpsightBillboard
  {
    public void destroy() {}
    
    protected UpsightBillboard setUp(UpsightContext paramUpsightContext)
      throws IllegalStateException
    {
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/UpsightPushBillboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */