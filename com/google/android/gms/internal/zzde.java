package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener;

@zzgr
public class zzde
  extends zzcz.zza
{
  private final NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener zzxm;
  
  public zzde(NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener paramOnCustomTemplateAdLoadedListener)
  {
    this.zzxm = paramOnCustomTemplateAdLoadedListener;
  }
  
  public void zza(zzcu paramzzcu)
  {
    this.zzxm.onCustomTemplateAdLoaded(new zzcv(paramzzcu));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzde.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */