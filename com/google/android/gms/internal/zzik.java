package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;

public class zzik
{
  private long zzJk;
  private long zzJl = Long.MIN_VALUE;
  private Object zzpd = new Object();
  
  public zzik(long paramLong)
  {
    this.zzJk = paramLong;
  }
  
  public boolean tryAcquire()
  {
    synchronized (this.zzpd)
    {
      long l = zzp.zzbz().elapsedRealtime();
      if (this.zzJl + this.zzJk > l) {
        return false;
      }
      this.zzJl = l;
      return true;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzik.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */