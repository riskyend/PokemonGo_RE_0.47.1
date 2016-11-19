package com.google.android.gms.internal;

import java.util.concurrent.Future;

@zzgr
public abstract class zzhz
  implements zzgh<Future>
{
  private volatile Thread zzIl;
  private boolean zzIm;
  private final Runnable zzx = new Runnable()
  {
    public final void run()
    {
      zzhz.zza(zzhz.this, Thread.currentThread());
      zzhz.this.zzbn();
    }
  };
  
  public zzhz()
  {
    this.zzIm = false;
  }
  
  public zzhz(boolean paramBoolean)
  {
    this.zzIm = paramBoolean;
  }
  
  public final void cancel()
  {
    onStop();
    if (this.zzIl != null) {
      this.zzIl.interrupt();
    }
  }
  
  public abstract void onStop();
  
  public abstract void zzbn();
  
  public final Future zzgz()
  {
    if (this.zzIm) {
      return zzic.zza(1, this.zzx);
    }
    return zzic.zza(this.zzx);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzhz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */