package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

public class zzg<T>
  extends zzb<T>
{
  private T zzadF;
  
  public zzg(DataBuffer<T> paramDataBuffer)
  {
    super(paramDataBuffer);
  }
  
  public T next()
  {
    if (!hasNext()) {
      throw new NoSuchElementException("Cannot advance the iterator beyond " + this.zzadj);
    }
    this.zzadj += 1;
    if (this.zzadj == 0)
    {
      this.zzadF = this.zzadi.get(0);
      if (!(this.zzadF instanceof zzc)) {
        throw new IllegalStateException("DataBuffer reference of type " + this.zzadF.getClass() + " is not movable");
      }
    }
    else
    {
      ((zzc)this.zzadF).zzbr(this.zzadj);
    }
    return (T)this.zzadF;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/common/data/zzg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */