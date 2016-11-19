package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import com.google.android.gms.internal.zzce;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zziz;
import com.google.android.gms.internal.zzmx;

public class zzl
  implements zzj
{
  public zzi zza(Context paramContext, zziz paramzziz, int paramInt, zzcg paramzzcg, zzce paramzzce)
  {
    if (!zzmx.zzqx()) {
      return null;
    }
    return new zzc(paramContext, new zzp(paramContext, paramzziz.zzhh(), paramzziz.getRequestId(), paramzzcg, paramzzce));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/overlay/zzl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */