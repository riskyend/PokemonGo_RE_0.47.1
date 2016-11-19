package com.google.android.gms.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public abstract class zzle
  implements Releasable, Result
{
  protected final Status zzSC;
  protected final DataHolder zzabq;
  
  protected zzle(DataHolder paramDataHolder, Status paramStatus)
  {
    this.zzSC = paramStatus;
    this.zzabq = paramDataHolder;
  }
  
  public Status getStatus()
  {
    return this.zzSC;
  }
  
  public void release()
  {
    if (this.zzabq != null) {
      this.zzabq.close();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */