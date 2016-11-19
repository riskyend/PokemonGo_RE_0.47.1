package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.upsight.mediation.ads.model.AdapterLoadError;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.mraid.MRAIDInterstitial;
import com.upsight.mediation.mraid.MRAIDInterstitialListener;
import com.upsight.mediation.mraid.MRAIDNativeFeatureListener;
import com.upsight.mediation.util.StringUtil;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;

public class MRaidAdAdapter
  extends NetworkWrapperFuseInternal
  implements MRAIDNativeFeatureListener, MRAIDInterstitialListener
{
  private static final int DISPLAY_MRAID_ACTIVITY_REQUEST_CODE = 0;
  public static final String NAME = "MRAID";
  private static final String TAG = "MRaidAdAdapter";
  static MRaidRegistry mRaidRegistry = new MRaidRegistry();
  private WeakReference<MRaidActivity> MRaidActivity;
  protected Activity activity;
  protected int backgroundColor;
  String baseUrl;
  protected String clickBeacon;
  private int closeButtonDelay;
  protected boolean hasReportedClose = false;
  protected String htmlBody;
  protected String impressionBeacon;
  protected MRAIDInterstitial interstitial;
  private boolean isRewarded;
  protected boolean loaded = false;
  private String name;
  protected int registryId;
  private boolean returnToInterstitial;
  private int rewardTimer;
  protected int rotateMode;
  protected boolean shouldPreload;
  private Date startDisplayTime;
  
  private void preloadInterstitial()
  {
    this.loaded = false;
    this.interstitial = new MRAIDInterstitial(this.activity, this.baseUrl, this.htmlBody, this.backgroundColor, new String[] { "calendar", "inlineVideo", "storePicture", "sms", "tel" }, this, this);
    this.interstitial.setOrientationConfig(this.rotateMode);
    this.htmlBody = null;
  }
  
  public void displayAd()
  {
    if (!isAdAvailable())
    {
      onAdFailedToDisplay();
      return;
    }
    if (this.shouldPreload)
    {
      displayInterstitial();
      return;
    }
    preloadInterstitial();
  }
  
  void displayInterstitial()
  {
    Intent localIntent = new Intent(this.activity, MRaidActivity.class);
    localIntent.putExtra("registryId", this.registryId);
    localIntent.putExtra("rotate", this.rotateMode);
    localIntent.setFlags(65536);
    this.activity.startActivity(localIntent);
    this.hasReportedClose = false;
    this.loaded = false;
  }
  
  public MRAIDInterstitial getInterstitial()
  {
    return this.interstitial;
  }
  
  public MRaidActivity getMRaidActivity()
  {
    if (this.MRaidActivity != null) {
      return (MRaidActivity)this.MRaidActivity.get();
    }
    return null;
  }
  
  public String getName()
  {
    if (this.name == null) {
      this.name = ("MRAID" + ": " + getID());
    }
    return this.name;
  }
  
  public void init()
  {
    this.registryId = mRaidRegistry.register(this);
    this.baseUrl = null;
  }
  
  public boolean isAdAvailable()
  {
    boolean bool2 = this.loaded;
    boolean bool1 = bool2;
    if (this.shouldPreload)
    {
      if ((bool2) && (this.interstitial != null) && (this.interstitial.isReady)) {
        bool1 = true;
      }
    }
    else {
      return bool1;
    }
    return false;
  }
  
  public boolean isDisplaying()
  {
    MRaidActivity localMRaidActivity = getMRaidActivity();
    if (localMRaidActivity != null) {
      return localMRaidActivity.isVisible;
    }
    return false;
  }
  
  public void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap)
  {
    this.isRewarded = Boolean.parseBoolean((String)paramHashMap.get("isRewarded"));
    this.activity = paramActivity;
    this.htmlBody = ((String)paramHashMap.get("script"));
    if (StringUtil.isNullOrEmpty(this.htmlBody))
    {
      onAdFailedToLoad(AdapterLoadError.INVALID_PARAMETERS);
      return;
    }
    this.clickBeacon = ((String)paramHashMap.get("beacon-click"));
    this.impressionBeacon = ((String)paramHashMap.get("beacon-impression"));
    this.baseUrl = ((String)paramHashMap.get("baseUrl"));
    this.shouldPreload = Boolean.parseBoolean((String)paramHashMap.get("shouldPreload"));
    this.returnToInterstitial = Boolean.parseBoolean((String)paramHashMap.get("rewardTimer"));
    try
    {
      this.backgroundColor = Integer.parseInt((String)paramHashMap.get("backgroundColor"));
      this.rotateMode = Integer.parseInt((String)paramHashMap.get("rotateMode"));
      this.rewardTimer = Integer.parseInt((String)paramHashMap.get("rewardTimer"));
      this.closeButtonDelay = Integer.parseInt((String)paramHashMap.get("rewardTimer"));
      if (this.shouldPreload)
      {
        preloadInterstitial();
        return;
      }
    }
    catch (NumberFormatException paramActivity)
    {
      onAdFailedToLoad(AdapterLoadError.INVALID_PARAMETERS);
      return;
    }
    this.loaded = true;
    onAdLoaded();
  }
  
  public void mraidInterstitialAcceptPressed(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidInterstitialFailedToLoad(MRAIDInterstitial paramMRAIDInterstitial)
  {
    FuseLog.d("MRaidAdAdapter", "MRAID Ad Failed to Load");
    onAdFailedToLoad(AdapterLoadError.PROVIDER_ADAPTER_ERROR);
  }
  
  public void mraidInterstitialFailedToShow()
  {
    onAdFailedToDisplay();
  }
  
  public void mraidInterstitialHide(MRAIDInterstitial paramMRAIDInterstitial)
  {
    int i = 1;
    FuseLog.v("MRaidAdAdapter", "MRAID Ad Hidden");
    if (!this.hasReportedClose)
    {
      this.hasReportedClose = true;
      if ((this.isRewarded) && (this.startDisplayTime != null)) {
        if ((new Date().getTime() - this.startDisplayTime.getTime() <= this.rewardTimer) || (this.rewardTimer <= 0)) {
          break label96;
        }
      }
    }
    for (;;)
    {
      if (i != 0) {
        onRewardedVideoCompleted();
      }
      onAdClosed();
      paramMRAIDInterstitial = getMRaidActivity();
      if (paramMRAIDInterstitial != null) {
        paramMRAIDInterstitial.finish();
      }
      return;
      label96:
      i = 0;
    }
  }
  
  public void mraidInterstitialLoaded(MRAIDInterstitial paramMRAIDInterstitial)
  {
    if (this.shouldPreload)
    {
      FuseLog.v("MRaidAdAdapter", "MRAID Ad Loaded");
      this.loaded = true;
      onAdLoaded();
      return;
    }
    displayInterstitial();
  }
  
  public void mraidInterstitialRejectPressed(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidInterstitialReplayVideoPressed(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidInterstitialShow(MRAIDInterstitial paramMRAIDInterstitial)
  {
    FuseLog.v("MRaidAdAdapter", "MRAID Ad Displayed");
    onAdDisplayed();
    if (this.impressionBeacon != null) {
      sendRequestToBeacon(this.impressionBeacon);
    }
    if (this.isRewarded) {
      this.startDisplayTime = new Date();
    }
  }
  
  public void mraidNativeFeatureCallTel(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "MRAID Ad Wants to make phone call " + paramString);
  }
  
  public void mraidNativeFeatureCreateCalendarEvent(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "MRAID Ad Wants to create calendar event: " + paramString);
  }
  
  public void mraidNativeFeatureOpenBrowser(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "Ad Wants to display browser: " + paramString);
    if (this.clickBeacon != null) {
      sendRequestToBeacon(this.clickBeacon);
    }
    onAdClicked();
    onOpenMRaidUrl(paramString);
    mraidInterstitialHide(this.interstitial);
  }
  
  public void mraidNativeFeatureOpenMarket(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "Ad Wants to display market: " + paramString);
    if (this.clickBeacon != null) {
      sendRequestToBeacon(this.clickBeacon);
    }
    onAdClicked();
    onOpenMRaidUrl(paramString);
    mraidInterstitialHide(this.interstitial);
  }
  
  public void mraidNativeFeaturePlayVideo(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "MRAID Ad Wants to play video: " + paramString);
    MRaidActivity localMRaidActivity = getMRaidActivity();
    if (localMRaidActivity != null)
    {
      Intent localIntent = new Intent(localMRaidActivity, MRaidVideoActivity.class);
      localIntent.putExtra("url", paramString);
      localIntent.putExtra("cb_ms", this.closeButtonDelay);
      localIntent.putExtra("rti", this.returnToInterstitial);
      localMRaidActivity.startActivityForResult(localIntent, 1);
    }
  }
  
  public void mraidNativeFeatureSendSms(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "Ad Wants to send SMS: " + paramString);
  }
  
  public void mraidNativeFeatureStorePicture(String paramString)
  {
    FuseLog.v("MRaidAdAdapter", "Ad Wants to store a picture: " + paramString);
  }
  
  public void mraidRewardComplete()
  {
    onRewardedVideoCompleted();
  }
  
  public void setMRaidActivity(MRaidActivity paramMRaidActivity)
  {
    this.MRaidActivity = new WeakReference(paramMRaidActivity);
  }
  
  public boolean verifyParameters(HashMap<String, String> paramHashMap)
  {
    return !StringUtil.isNullOrEmpty((String)paramHashMap.get("script"));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/MRaidAdAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */