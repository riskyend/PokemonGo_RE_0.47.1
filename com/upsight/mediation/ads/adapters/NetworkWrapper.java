package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import com.upsight.mediation.ads.model.AdapterLoadError;
import com.upsight.mediation.data.Offer;
import com.upsight.mediation.log.FuseLog;
import java.util.HashMap;

public abstract class NetworkWrapper
{
  public static final String BACKGROUND_COLOR = "backgroundColor";
  public static final String BASE_URL = "baseUrl";
  public static final String CLOSE_BUTTON_DELAY = "closeButtonDelay";
  public static final String FUSE_SDK_VERSION = "fuse_sdk_version";
  public static final String IS_REWARDED = "isRewarded";
  public static final String IS_TABLET = "is_tablet";
  public static final String IS_VIDEO = "isVideo";
  public static final String MAX_FILE_SIZE = "maxFileSize";
  public static final String REWARD_TIMER = "rewardTimer";
  public static final String ROTATE_MODE = "rotateMode";
  public static final String SHOULD_PRELOAD = "shouldPreload";
  public static final String SHOULD_VALIDATE_SCHEMA = "shouldValidateSchema";
  public static final String TIMEOUT = "timeout";
  public static final String VAST_CACHE_TO = "vast_cache_to";
  Listener listener;
  
  public abstract void displayAd();
  
  public int getID()
  {
    return this.listener.getID();
  }
  
  public abstract String getName();
  
  public abstract void init();
  
  public abstract boolean isAdAvailable();
  
  protected boolean isLandscape(Context paramContext)
  {
    return !isPortrait(paramContext);
  }
  
  protected boolean isPortrait(Context paramContext)
  {
    paramContext = paramContext.getApplicationContext().getResources().getDisplayMetrics();
    return paramContext.heightPixels >= paramContext.widthPixels;
  }
  
  public abstract void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap);
  
  protected void log(String paramString)
  {
    FuseLog.d(getName(), paramString);
  }
  
  protected void logError(String paramString)
  {
    FuseLog.w(getName(), paramString);
  }
  
  protected void logError(String paramString, Throwable paramThrowable)
  {
    FuseLog.w(getName(), paramString, paramThrowable);
  }
  
  protected final void onAdClicked()
  {
    if (this.listener != null) {
      this.listener.onAdClicked();
    }
  }
  
  protected final void onAdClosed()
  {
    if (this.listener != null) {
      this.listener.onAdClosed();
    }
  }
  
  protected final void onAdCompleted()
  {
    if (this.listener != null) {
      this.listener.onAdCompleted();
    }
  }
  
  protected final void onAdDisplayed()
  {
    if (this.listener != null) {
      this.listener.onAdDisplayed();
    }
  }
  
  protected final void onAdFailedToDisplay()
  {
    if (this.listener != null) {
      this.listener.onAdFailedToDisplay();
    }
  }
  
  protected final void onAdFailedToLoad(AdapterLoadError paramAdapterLoadError)
  {
    if (this.listener != null) {
      this.listener.onAdFailedToLoad(paramAdapterLoadError);
    }
  }
  
  protected final void onAdLoaded()
  {
    if (this.listener != null) {
      this.listener.onAdLoaded();
    }
  }
  
  protected final void onAdSkipped()
  {
    if (this.listener != null) {
      this.listener.onAdSkipped();
    }
  }
  
  protected final void onRewardedVideoCompleted()
  {
    if (this.listener != null) {
      this.listener.onRewardedVideoCompleted();
    }
  }
  
  public void resumedAfterAdDisplay() {}
  
  public void setListener(Listener paramListener)
  {
    this.listener = paramListener;
  }
  
  public static abstract interface Listener
  {
    public abstract int getID();
    
    public abstract void onAdClicked();
    
    public abstract void onAdClosed();
    
    public abstract void onAdCompleted();
    
    public abstract void onAdDisplayed();
    
    public abstract void onAdFailedToDisplay();
    
    public abstract void onAdFailedToLoad(AdapterLoadError paramAdapterLoadError);
    
    public abstract void onAdLoaded();
    
    public abstract void onAdSkipped();
    
    public abstract void onOfferAccepted();
    
    public abstract void onOfferDisplayed(Offer paramOffer);
    
    public abstract void onOfferRejected();
    
    public abstract void onOpenMRaidUrl(@NonNull String paramString);
    
    public abstract void onRewardedVideoCompleted();
    
    public abstract void onVastError(int paramInt);
    
    public abstract void onVastProgress(int paramInt);
    
    public abstract void onVastReplay();
    
    public abstract void onVastSkip();
    
    public abstract void sendRequestToBeacon(String paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/NetworkWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */