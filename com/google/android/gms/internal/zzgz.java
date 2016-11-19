package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzp;
import java.util.WeakHashMap;

@zzgr
public final class zzgz
{
  private WeakHashMap<Context, zza> zzGO = new WeakHashMap();
  
  public zzgy zzC(Context paramContext)
  {
    Object localObject = (zza)this.zzGO.get(paramContext);
    if ((localObject != null) && (!((zza)localObject).hasExpired()) && (((Boolean)zzby.zzvm.get()).booleanValue())) {}
    for (localObject = new zzgy.zza(paramContext, ((zza)localObject).zzGQ).zzfX();; localObject = new zzgy.zza(paramContext).zzfX())
    {
      this.zzGO.put(paramContext, new zza((zzgy)localObject));
      return (zzgy)localObject;
    }
  }
  
  private class zza
  {
    public final long zzGP = zzp.zzbz().currentTimeMillis();
    public final zzgy zzGQ;
    
    public zza(zzgy paramzzgy)
    {
      this.zzGQ = paramzzgy;
    }
    
    public boolean hasExpired()
    {
      long l = this.zzGP;
      return ((Long)zzby.zzvn.get()).longValue() + l < zzp.zzbz().currentTimeMillis();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzgz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */