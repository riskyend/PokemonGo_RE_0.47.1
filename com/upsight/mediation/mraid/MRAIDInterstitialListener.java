package com.upsight.mediation.mraid;

public abstract interface MRAIDInterstitialListener
{
  public abstract void mraidInterstitialAcceptPressed(MRAIDInterstitial paramMRAIDInterstitial);
  
  public abstract void mraidInterstitialFailedToLoad(MRAIDInterstitial paramMRAIDInterstitial);
  
  public abstract void mraidInterstitialHide(MRAIDInterstitial paramMRAIDInterstitial);
  
  public abstract void mraidInterstitialLoaded(MRAIDInterstitial paramMRAIDInterstitial);
  
  public abstract void mraidInterstitialRejectPressed(MRAIDInterstitial paramMRAIDInterstitial);
  
  public abstract void mraidInterstitialReplayVideoPressed(MRAIDInterstitial paramMRAIDInterstitial);
  
  public abstract void mraidInterstitialShow(MRAIDInterstitial paramMRAIDInterstitial);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/MRAIDInterstitialListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */