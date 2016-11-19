package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.upsight.mediation.ads.model.AdapterLoadError;
import com.upsight.mediation.data.Offer;
import com.upsight.mediation.util.InAppBillingConnection;
import java.util.HashMap;

public class IAPOfferAdAdapter
  extends OfferAdAdapter
  implements LocalizedPrice
{
  @Nullable
  private InAppBillingConnection mInAppBillingConnection;
  
  public void injectIAPBillingConnection(@NonNull InAppBillingConnection paramInAppBillingConnection)
  {
    this.mInAppBillingConnection = paramInAppBillingConnection;
  }
  
  public boolean isAdAvailable(@Nullable Offer paramOffer1, @Nullable Offer paramOffer2)
  {
    return super.isAdAvailable(paramOffer1);
  }
  
  public void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap, @Nullable Offer paramOffer1, @Nullable Offer paramOffer2)
  {
    if ((this.mInAppBillingConnection == null) || (!this.mInAppBillingConnection.isConnected()))
    {
      onAdFailedToLoad(AdapterLoadError.PROVIDER_IAP_BILLING_FAILURE);
      return;
    }
    if (paramOffer1 == null)
    {
      onAdFailedToLoad(AdapterLoadError.PROVIDER_NO_FILL);
      return;
    }
    paramOffer2 = paramOffer1.itemId;
    paramOffer2 = this.mInAppBillingConnection.getLocalPriceForProductId(paramOffer2);
    if (paramOffer2 == null)
    {
      onAdFailedToLoad(AdapterLoadError.PROVIER_IAP_BILLING_NOT_FOUND);
      return;
    }
    this.javascriptInjection = ("(function(){document.getElementById(\"bonus_price_or_quantity\").innerHTML=\"" + paramOffer2 + "\"})()");
    super.loadAd(paramActivity, paramHashMap, paramOffer1);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/IAPOfferAdAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */