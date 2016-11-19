package com.google.android.gms.ads.mediation;

import android.os.Bundle;
import android.view.View;

public abstract class NativeAdMapper
{
  protected Bundle mExtras = new Bundle();
  protected boolean mOverrideClickHandling;
  protected boolean mOverrideImpressionRecording;
  
  public final Bundle getExtras()
  {
    return this.mExtras;
  }
  
  public final boolean getOverrideClickHandling()
  {
    return this.mOverrideClickHandling;
  }
  
  public final boolean getOverrideImpressionRecording()
  {
    return this.mOverrideImpressionRecording;
  }
  
  public void handleClick(View paramView) {}
  
  public void recordImpression() {}
  
  public final void setExtras(Bundle paramBundle)
  {
    this.mExtras = paramBundle;
  }
  
  public final void setOverrideClickHandling(boolean paramBoolean)
  {
    this.mOverrideClickHandling = paramBoolean;
  }
  
  public final void setOverrideImpressionRecording(boolean paramBoolean)
  {
    this.mOverrideImpressionRecording = paramBoolean;
  }
  
  public void trackView(View paramView) {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/mediation/NativeAdMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */