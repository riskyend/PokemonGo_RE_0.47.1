package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzgr
public class zzdn
  implements zzdk
{
  private final zzdo zzxO;
  
  public zzdn(zzdo paramzzdo)
  {
    this.zzxO = paramzzdo;
  }
  
  public void zza(zziz paramzziz, Map<String, String> paramMap)
  {
    boolean bool1 = "1".equals(paramMap.get("transparentBackground"));
    boolean bool2 = "1".equals(paramMap.get("blur"));
    try
    {
      if (paramMap.get("blurRadius") != null)
      {
        f = Float.parseFloat((String)paramMap.get("blurRadius"));
        this.zzxO.zzd(bool1);
        this.zzxO.zza(bool2, f);
        return;
      }
    }
    catch (NumberFormatException paramzziz)
    {
      for (;;)
      {
        zzb.zzb("Fail to parse float", paramzziz);
        float f = 0.0F;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzdn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */