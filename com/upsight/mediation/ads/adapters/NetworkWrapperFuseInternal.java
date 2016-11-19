package com.upsight.mediation.ads.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.upsight.mediation.data.Offer;
import java.util.HashMap;

public abstract class NetworkWrapperFuseInternal
  extends NetworkWrapper
{
  public final void onOfferAccepted(Offer paramOffer)
  {
    this.listener.onOfferAccepted();
  }
  
  public final void onOfferDisplayed(Offer paramOffer)
  {
    this.listener.onOfferDisplayed(paramOffer);
  }
  
  public final void onOfferRejected(Offer paramOffer)
  {
    this.listener.onOfferRejected();
  }
  
  protected void onOpenMRaidUrl(@NonNull String paramString)
  {
    this.listener.onOpenMRaidUrl(paramString);
  }
  
  protected final void onVastError(int paramInt)
  {
    this.listener.onVastError(paramInt);
  }
  
  protected final void onVastProgress(int paramInt)
  {
    this.listener.onVastProgress(paramInt);
  }
  
  protected final void onVastReplay()
  {
    this.listener.onVastReplay();
  }
  
  protected final void onVastSkip()
  {
    this.listener.onVastSkip();
  }
  
  public final void sendRequestToBeacon(@Nullable String paramString)
  {
    this.listener.sendRequestToBeacon(paramString);
  }
  
  public abstract boolean verifyParameters(HashMap<String, String> paramHashMap);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/NetworkWrapperFuseInternal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */