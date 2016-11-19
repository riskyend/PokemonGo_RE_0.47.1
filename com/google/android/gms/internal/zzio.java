package com.google.android.gms.internal;

import java.util.concurrent.TimeUnit;

@zzgr
public class zzio<T>
  implements zziq<T>
{
  private final zzir zzJA;
  private final T zzJy;
  
  public zzio(T paramT)
  {
    this.zzJy = paramT;
    this.zzJA = new zzir();
    this.zzJA.zzgV();
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    return false;
  }
  
  public T get()
  {
    return (T)this.zzJy;
  }
  
  public T get(long paramLong, TimeUnit paramTimeUnit)
  {
    return (T)this.zzJy;
  }
  
  public boolean isCancelled()
  {
    return false;
  }
  
  public boolean isDone()
  {
    return true;
  }
  
  public void zzc(Runnable paramRunnable)
  {
    this.zzJA.zzc(paramRunnable);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzio.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */