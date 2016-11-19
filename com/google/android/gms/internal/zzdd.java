package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd.OnCustomClickListener;

@zzgr
public class zzdd
  extends zzcy.zza
{
  private final NativeCustomTemplateAd.OnCustomClickListener zzxl;
  
  public zzdd(NativeCustomTemplateAd.OnCustomClickListener paramOnCustomClickListener)
  {
    this.zzxl = paramOnCustomClickListener;
  }
  
  public void zza(zzcu paramzzcu, String paramString)
  {
    this.zzxl.onCustomClick(new zzcv(paramzzcu), paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzdd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */