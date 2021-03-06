package com.google.android.gms.ads.internal.purchase;

import android.content.Intent;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzid;

@zzgr
public class zzk
{
  private final String zztG;
  
  public zzk(String paramString)
  {
    this.zztG = paramString;
  }
  
  public boolean zza(String paramString, int paramInt, Intent paramIntent)
  {
    if ((paramString == null) || (paramIntent == null)) {}
    String str;
    do
    {
      return false;
      str = zzp.zzbF().zze(paramIntent);
      paramIntent = zzp.zzbF().zzf(paramIntent);
    } while ((str == null) || (paramIntent == null));
    if (!paramString.equals(zzp.zzbF().zzao(str)))
    {
      zzb.zzaH("Developer payload not match.");
      return false;
    }
    if ((this.zztG != null) && (!zzl.zzc(this.zztG, str, paramIntent)))
    {
      zzb.zzaH("Fail to verify signature.");
      return false;
    }
    return true;
  }
  
  public String zzfq()
  {
    return zzp.zzbv().zzgD();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/purchase/zzk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */