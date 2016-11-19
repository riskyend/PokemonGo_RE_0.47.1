package com.upsight.mediation.mraid;

public abstract interface MRAIDNativeFeatureListener
{
  public abstract void mraidNativeFeatureCallTel(String paramString);
  
  public abstract void mraidNativeFeatureCreateCalendarEvent(String paramString);
  
  public abstract void mraidNativeFeatureOpenBrowser(String paramString);
  
  public abstract void mraidNativeFeatureOpenMarket(String paramString);
  
  public abstract void mraidNativeFeaturePlayVideo(String paramString);
  
  public abstract void mraidNativeFeatureSendSms(String paramString);
  
  public abstract void mraidNativeFeatureStorePicture(String paramString);
  
  public abstract void mraidRewardComplete();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/MRAIDNativeFeatureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */