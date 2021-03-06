package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.upsight.mediation.data.Offer;
import java.util.HashMap;

public abstract interface OfferBasedNetworkWrapper
{
  public abstract boolean isAdAvailable(@Nullable Offer paramOffer1, @Nullable Offer paramOffer2);
  
  public abstract void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap, @Nullable Offer paramOffer1, @Nullable Offer paramOffer2);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/OfferBasedNetworkWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */