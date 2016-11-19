package com.google.android.gms.ads.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zzw.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzgr;

@zzgr
public class zzm
  extends zzw.zza
{
  private static final Object zzpy = new Object();
  private static zzm zzpz;
  private final Context mContext;
  private boolean zzpA;
  
  zzm(Context paramContext)
  {
    this.mContext = paramContext;
    this.zzpA = false;
  }
  
  public static zzm zzq(Context paramContext)
  {
    synchronized (zzpy)
    {
      if (zzpz == null) {
        zzpz = new zzm(paramContext.getApplicationContext());
      }
      paramContext = zzpz;
      return paramContext;
    }
  }
  
  public void zza()
  {
    synchronized (zzpy)
    {
      if (this.zzpA)
      {
        zzb.zzaH("Mobile ads is initialized already.");
        return;
      }
      this.zzpA = true;
      return;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/zzm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */