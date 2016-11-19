package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzgr
public final class zzdf
  implements zzdk
{
  private final zzdg zzxn;
  
  public zzdf(zzdg paramzzdg)
  {
    this.zzxn = paramzzdg;
  }
  
  public void zza(zziz paramzziz, Map<String, String> paramMap)
  {
    paramzziz = (String)paramMap.get("name");
    if (paramzziz == null)
    {
      zzb.zzaH("App event with no name parameter.");
      return;
    }
    this.zzxn.onAppEvent(paramzziz, (String)paramMap.get("info"));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzdf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */