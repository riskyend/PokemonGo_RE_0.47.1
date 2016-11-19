package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T>
  implements Iterator<T>
{
  protected final DataBuffer<T> zzadi;
  protected int zzadj;
  
  public zzb(DataBuffer<T> paramDataBuffer)
  {
    this.zzadi = ((DataBuffer)zzx.zzw(paramDataBuffer));
    this.zzadj = -1;
  }
  
  public boolean hasNext()
  {
    return this.zzadj < this.zzadi.getCount() - 1;
  }
  
  public T next()
  {
    if (!hasNext()) {
      throw new NoSuchElementException("Cannot advance the iterator beyond " + this.zzadj);
    }
    DataBuffer localDataBuffer = this.zzadi;
    int i = this.zzadj + 1;
    this.zzadj = i;
    return (T)localDataBuffer.get(i);
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/common/data/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */