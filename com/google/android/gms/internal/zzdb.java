package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;

@zzgr
public class zzdb
  extends zzcw.zza
{
  private final NativeAppInstallAd.OnAppInstallAdLoadedListener zzxj;
  
  public zzdb(NativeAppInstallAd.OnAppInstallAdLoadedListener paramOnAppInstallAdLoadedListener)
  {
    this.zzxj = paramOnAppInstallAdLoadedListener;
  }
  
  public void zza(zzcq paramzzcq)
  {
    this.zzxj.onAppInstallAdLoaded(zzb(paramzzcq));
  }
  
  zzcr zzb(zzcq paramzzcq)
  {
    return new zzcr(paramzzcq);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzdb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */