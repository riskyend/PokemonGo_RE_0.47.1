package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;

public class zzcb
{
  public zzca zza(zzbz paramzzbz)
  {
    if (paramzzbz == null) {
      throw new IllegalArgumentException("CSI configuration can't be null. ");
    }
    if (!paramzzbz.zzdg())
    {
      zzb.v("CsiReporterFactory: CSI is not enabled. No CSI reporter created.");
      return null;
    }
    if (paramzzbz.getContext() == null) {
      throw new IllegalArgumentException("Context can't be null. Please set up context in CsiConfiguration.");
    }
    if (TextUtils.isEmpty(paramzzbz.zzbV())) {
      throw new IllegalArgumentException("AfmaVersion can't be null or empty. Please set up afmaVersion in CsiConfiguration.");
    }
    return new zzca(paramzzbz.getContext(), paramzzbz.zzbV(), paramzzbz.zzdh(), paramzzbz.zzdi());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzcb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */