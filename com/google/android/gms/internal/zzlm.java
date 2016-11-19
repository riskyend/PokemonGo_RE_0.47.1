package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzx;

public final class zzlm<L>
{
  private volatile L mListener;
  private final zzlm<L>.zza zzacG;
  
  public zzlm(Looper paramLooper, L paramL)
  {
    this.zzacG = new zza(paramLooper);
    this.mListener = zzx.zzb(paramL, "Listener must not be null");
  }
  
  public void clear()
  {
    this.mListener = null;
  }
  
  public void zza(zzb<? super L> paramzzb)
  {
    zzx.zzb(paramzzb, "Notifier must not be null");
    paramzzb = this.zzacG.obtainMessage(1, paramzzb);
    this.zzacG.sendMessage(paramzzb);
  }
  
  void zzb(zzb<? super L> paramzzb)
  {
    Object localObject = this.mListener;
    if (localObject == null)
    {
      paramzzb.zznN();
      return;
    }
    try
    {
      paramzzb.zzq(localObject);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      paramzzb.zznN();
      throw localRuntimeException;
    }
  }
  
  private final class zza
    extends Handler
  {
    public zza(Looper paramLooper)
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      boolean bool = true;
      if (paramMessage.what == 1) {}
      for (;;)
      {
        zzx.zzaa(bool);
        zzlm.this.zzb((zzlm.zzb)paramMessage.obj);
        return;
        bool = false;
      }
    }
  }
  
  public static abstract interface zzb<L>
  {
    public abstract void zznN();
    
    public abstract void zzq(L paramL);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzlm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */