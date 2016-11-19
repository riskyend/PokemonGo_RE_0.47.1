package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;

@zzgr
public class zzdc
  extends zzcx.zza
{
  private final NativeContentAd.OnContentAdLoadedListener zzxk;
  
  public zzdc(NativeContentAd.OnContentAdLoadedListener paramOnContentAdLoadedListener)
  {
    this.zzxk = paramOnContentAdLoadedListener;
  }
  
  public void zza(zzcs paramzzcs)
  {
    this.zzxk.onContentAdLoaded(zzb(paramzzcs));
  }
  
  zzct zzb(zzcs paramzzcs)
  {
    return new zzct(paramzzcs);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzdc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */