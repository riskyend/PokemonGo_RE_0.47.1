package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.upsight.mediation.ads.model.AdapterLoadError;
import java.util.HashMap;

public class UnknownNetwork
  extends NetworkWrapper
{
  private static final String UNKNOWN = "Unknown";
  
  public void displayAd()
  {
    onAdFailedToDisplay();
  }
  
  public String getName()
  {
    return "Unknown";
  }
  
  public void init() {}
  
  public boolean isAdAvailable()
  {
    return false;
  }
  
  public void loadAd(@NonNull Activity paramActivity, @NonNull HashMap<String, String> paramHashMap)
  {
    onAdFailedToLoad(AdapterLoadError.PROVIDER_UNRECOGNIZED);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/UnknownNetwork.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */