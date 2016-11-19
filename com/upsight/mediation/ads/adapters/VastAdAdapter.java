package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;
import com.upsight.mediation.ads.model.AdapterLoadError;
import com.upsight.mediation.util.StringUtil;
import com.upsight.mediation.vast.VASTPlayer;
import com.upsight.mediation.vast.VASTPlayer.VASTPlayerListener;
import java.util.HashMap;

public class VastAdAdapter
  extends NetworkWrapperFuseInternal
  implements VASTPlayer.VASTPlayerListener
{
  public static final String NAME = "VAST";
  private static final String TAG = "VastAdAdapter";
  private Activity activity;
  private VASTPlayer interstitial;
  private boolean isRewarded = false;
  
  public void displayAd()
  {
    if (!isAdAvailable())
    {
      onAdFailedToDisplay();
      return;
    }
    this.interstitial.play();
  }
  
  public String getName()
  {
    return "VAST" + ": " + getID();
  }
  
  @CallSuper
  public void init() {}
  
  public boolean isAdAvailable()
  {
    return (this.interstitial != null) && (this.interstitial.isLoaded());
  }
  
  public void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap)
  {
    this.isRewarded = Boolean.parseBoolean((String)paramHashMap.get("isRewarded"));
    String str2 = (String)paramHashMap.get("cta");
    String str1;
    if (str2 != null)
    {
      str1 = str2;
      if (str2.length() != 0) {}
    }
    else
    {
      str1 = "Learn More";
    }
    str2 = (String)paramHashMap.get("maxFileSize");
    if (StringUtil.isNullOrEmpty(str2))
    {
      onAdFailedToLoad(AdapterLoadError.INVALID_PARAMETERS);
      return;
    }
    String str3 = (String)paramHashMap.get("closeButtonDelay");
    try
    {
      i = Integer.parseInt(str3);
      str3 = (String)paramHashMap.get("postroll");
      int j;
      if ((str3 != null) && (str3.equals("1")))
      {
        bool1 = true;
        str3 = (String)paramHashMap.get("shouldValidateSchema");
        if ((str3 == null) || (!Boolean.parseBoolean(str3))) {
          break label276;
        }
        bool2 = true;
        str3 = (String)paramHashMap.get("endcard_script");
        j = -1;
      }
      try
      {
        int k = Integer.parseInt((String)paramHashMap.get("vast_cache_to"));
        j = k;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        for (;;) {}
      }
      paramHashMap = (String)paramHashMap.get("script");
      if (paramHashMap != null)
      {
        try
        {
          this.interstitial = new VASTPlayer(paramActivity, this, bool1, str3, i, this.isRewarded, str2, bool2, str1, j);
          if (!URLUtil.isValidUrl(paramHashMap)) {
            break label282;
          }
          this.interstitial.loadVastResponseViaURL(paramHashMap);
          return;
        }
        catch (Exception paramActivity)
        {
          logError("Vast failed to load to to unexpected error", paramActivity);
        }
      }
      else
      {
        onAdFailedToLoad(AdapterLoadError.PROVIDER_ADAPTER_ERROR);
        return;
      }
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      for (;;)
      {
        int i = -1;
        log("Could not parse close button delay");
        continue;
        boolean bool1 = false;
        continue;
        boolean bool2 = false;
      }
      this.interstitial.loadVastResponseViaXML(paramHashMap);
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      label276:
      label282:
      for (;;) {}
    }
  }
  
  public void vastClick()
  {
    onAdClicked();
  }
  
  public void vastComplete()
  {
    onAdCompleted();
  }
  
  public void vastDismiss()
  {
    onAdClosed();
  }
  
  public void vastDisplay()
  {
    onAdDisplayed();
  }
  
  public void vastError(int paramInt)
  {
    log("Error: " + paramInt);
    onVastError(paramInt);
    switch (paramInt)
    {
    default: 
      onAdFailedToLoad(AdapterLoadError.PROVIDER_UNRECOGNIZED);
      return;
    case 405: 
      onAdFailedToDisplay();
      return;
    case 303: 
      onAdFailedToLoad(AdapterLoadError.PROVIDER_UNDEFINED);
      return;
    case 301: 
    case 402: 
      onAdFailedToLoad(AdapterLoadError.PROVIDER_TIMED_OUT);
      return;
    case 1: 
    case 401: 
    case 403: 
      onAdFailedToLoad(AdapterLoadError.PROVIDER_LOAD_NOT_STARTED);
      return;
    }
    onAdFailedToLoad(AdapterLoadError.PROVIDER_ADAPTER_ERROR);
  }
  
  public void vastProgress(int paramInt)
  {
    onVastProgress(paramInt);
  }
  
  public void vastReady()
  {
    log("Ad loaded");
    onAdLoaded();
  }
  
  public void vastReplay()
  {
    onVastReplay();
  }
  
  public void vastRewardedVideoComplete()
  {
    onRewardedVideoCompleted();
  }
  
  public void vastSkip()
  {
    onAdSkipped();
    onVastSkip();
  }
  
  public boolean verifyParameters(HashMap<String, String> paramHashMap)
  {
    if ((StringUtil.isNullOrEmpty((String)paramHashMap.get("maxFileSize"))) || (StringUtil.isNullOrEmpty((String)paramHashMap.get("script")))) {}
    for (int i = 1; i == 0; i = 0) {
      return true;
    }
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/VastAdAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */