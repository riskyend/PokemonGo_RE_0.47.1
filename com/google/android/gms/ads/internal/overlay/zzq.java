package com.google.android.gms.ads.internal.overlay;

import android.os.Handler;
import com.google.android.gms.internal.zzid;

class zzq
  implements Runnable
{
  private boolean mCancelled = false;
  private zzk zzCo;
  
  zzq(zzk paramzzk)
  {
    this.zzCo = paramzzk;
  }
  
  public void cancel()
  {
    this.mCancelled = true;
    zzid.zzIE.removeCallbacks(this);
  }
  
  public void run()
  {
    if (!this.mCancelled)
    {
      this.zzCo.zzeX();
      zzfg();
    }
  }
  
  public void zzfg()
  {
    zzid.zzIE.postDelayed(this, 250L);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/overlay/zzq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */