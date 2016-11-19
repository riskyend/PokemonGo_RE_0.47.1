package com.google.android.gms.ads.internal.request;

import java.lang.ref.WeakReference;

public final class zzg
  extends zzk.zza
{
  private final WeakReference<zzc.zza> zzEI;
  
  public zzg(zzc.zza paramzza)
  {
    this.zzEI = new WeakReference(paramzza);
  }
  
  public void zzb(AdResponseParcel paramAdResponseParcel)
  {
    zzc.zza localzza = (zzc.zza)this.zzEI.get();
    if (localzza != null) {
      localzza.zzb(paramAdResponseParcel);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/request/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */