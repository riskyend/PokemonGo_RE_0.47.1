package com.upsight.mediation.ads.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.mraid.MRAIDInterstitial;

public class MRaidActivity
  extends Activity
{
  private static final String TAG = "MRaidActivity";
  private boolean firstResume = true;
  private MRAIDInterstitial interstitial;
  public boolean isVisible;
  private MRaidAdAdapter provider;
  private int registryId;
  private int rotateMode;
  
  private int getOrientationValue(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return -1;
    case 2: 
      return 14;
    case 3: 
      return 6;
    }
    return 7;
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1)
    {
      if (paramInt2 != -1) {
        break label29;
      }
      this.provider.onAdCompleted();
    }
    for (;;)
    {
      this.provider.mraidInterstitialHide(this.interstitial);
      return;
      label29:
      if (paramInt2 == 0) {
        this.provider.onAdSkipped();
      }
    }
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    this.provider.mraidInterstitialHide(this.interstitial);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.registryId = getIntent().getIntExtra("registryId", -1);
    this.provider = MRaidAdAdapter.mRaidRegistry.getProvider(this.registryId);
    this.provider.setMRaidActivity(this);
    this.rotateMode = getIntent().getIntExtra("rotate", 1);
    setRequestedOrientation(getOrientationValue(this.rotateMode));
    this.interstitial = this.provider.getInterstitial();
    this.interstitial.updateContext(this);
    FuseLog.e("MRaidActivity", getRequestedOrientation() + "");
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    this.isVisible = false;
  }
  
  protected void onResume()
  {
    super.onResume();
    this.isVisible = true;
    if (this.firstResume)
    {
      this.firstResume = false;
      if (this.interstitial != null) {
        break label33;
      }
      finish();
    }
    label33:
    while (this.interstitial.show()) {
      return;
    }
    this.provider.mraidInterstitialFailedToShow();
    finish();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/MRaidActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */