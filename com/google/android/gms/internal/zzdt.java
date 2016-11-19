package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.ads.internal.zzp;

@zzgr
public class zzdt
  extends zzhz
{
  final zziz zzoM;
  final zzdv zzxY;
  private final String zzxZ;
  
  zzdt(zziz paramzziz, zzdv paramzzdv, String paramString)
  {
    this.zzoM = paramzziz;
    this.zzxY = paramzzdv;
    this.zzxZ = paramString;
    zzp.zzbI().zza(this);
  }
  
  public void onStop()
  {
    this.zzxY.abort();
  }
  
  public void zzbn()
  {
    try
    {
      this.zzxY.zzab(this.zzxZ);
      return;
    }
    finally
    {
      zzid.zzIE.post(new Runnable()
      {
        public void run()
        {
          zzp.zzbI().zzb(zzdt.this);
        }
      });
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzdt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */