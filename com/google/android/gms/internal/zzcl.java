package com.google.android.gms.internal;

import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;

@zzgr
public final class zzcl
  extends zzck.zza
{
  private final OnCustomRenderedAdLoadedListener zztK;
  
  public zzcl(OnCustomRenderedAdLoadedListener paramOnCustomRenderedAdLoadedListener)
  {
    this.zztK = paramOnCustomRenderedAdLoadedListener;
  }
  
  public void zza(zzcj paramzzcj)
  {
    this.zztK.onCustomRenderedAdLoaded(new zzci(paramzzcj));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzcl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */