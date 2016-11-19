package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

public class zzcf
{
  private final zzcg zzoo;
  private final Map<String, zzce> zzvQ;
  
  public zzcf(zzcg paramzzcg)
  {
    this.zzoo = paramzzcg;
    this.zzvQ = new HashMap();
  }
  
  public void zza(String paramString, zzce paramzzce)
  {
    this.zzvQ.put(paramString, paramzzce);
  }
  
  public void zza(String paramString1, String paramString2, long paramLong)
  {
    zzcc.zza(this.zzoo, (zzce)this.zzvQ.get(paramString2), paramLong, new String[] { paramString1 });
    this.zzvQ.put(paramString1, zzcc.zza(this.zzoo, paramLong));
  }
  
  public zzcg zzdm()
  {
    return this.zzoo;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzcf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */