package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.upsight.mediation.ads.model.AdapterLoadError;
import com.upsight.mediation.data.Offer;
import com.upsight.mediation.mraid.MRAIDInterstitial;
import java.util.Date;
import java.util.HashMap;

public abstract class OfferAdAdapter
  extends MRaidAdAdapter
  implements OfferBasedNetworkWrapper
{
  @Nullable
  protected String javascriptInjection;
  @Nullable
  Offer offer;
  boolean offerAccepted;
  
  public boolean isAdAvailable(@Nullable Offer paramOffer)
  {
    return (this.offer == paramOffer) && (paramOffer != null) && (!paramOffer.isExpired(new Date())) && (super.isAdAvailable());
  }
  
  public abstract boolean isAdAvailable(@Nullable Offer paramOffer1, @Nullable Offer paramOffer2);
  
  void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap, @Nullable Offer paramOffer)
  {
    this.activity = paramActivity;
    this.shouldPreload = true;
    if ((paramOffer != null) && (paramOffer.isExpired(new Date())))
    {
      onAdFailedToLoad(AdapterLoadError.PROVIDER_NO_FILL);
      return;
    }
    if ((paramOffer == this.offer) && (this.loaded))
    {
      onAdLoaded();
      return;
    }
    this.offer = paramOffer;
    this.loaded = false;
    if ((paramOffer == null) || (paramOffer.consumed))
    {
      onAdFailedToLoad(AdapterLoadError.PROVIDER_NO_FILL);
      return;
    }
    try
    {
      this.backgroundColor = Integer.parseInt((String)paramHashMap.get("backgroundColor"));
      this.rotateMode = Integer.parseInt((String)paramHashMap.get("rotateMode"));
      this.interstitial = new MRAIDInterstitial(paramActivity, "", this.offer.offerHtml, this.backgroundColor, new String[0], this, null);
      this.interstitial.setOrientationConfig(this.rotateMode);
      this.offerAccepted = false;
      return;
    }
    catch (NumberFormatException paramActivity)
    {
      onAdFailedToLoad(AdapterLoadError.INVALID_PARAMETERS);
    }
  }
  
  public abstract void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap, @Nullable Offer paramOffer1, @Nullable Offer paramOffer2);
  
  public void mraidInterstitialAcceptPressed(MRAIDInterstitial paramMRAIDInterstitial)
  {
    super.mraidInterstitialAcceptPressed(paramMRAIDInterstitial);
    onOfferAccepted(this.offer);
    this.offerAccepted = true;
  }
  
  public void mraidInterstitialHide(MRAIDInterstitial paramMRAIDInterstitial)
  {
    if (!this.offerAccepted) {
      onOfferRejected(this.offer);
    }
    super.mraidInterstitialHide(paramMRAIDInterstitial);
  }
  
  public void mraidInterstitialShow(MRAIDInterstitial paramMRAIDInterstitial)
  {
    super.mraidInterstitialShow(paramMRAIDInterstitial);
    onOfferDisplayed(this.offer);
    if (this.javascriptInjection != null) {
      paramMRAIDInterstitial.injectJavaScript(this.javascriptInjection);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/OfferAdAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */