package com.google.android.exoplayer.util;

import java.util.Arrays;

public final class LongArray
{
  private static final int DEFAULT_INITIAL_CAPACITY = 32;
  private int size;
  private long[] values;
  
  public LongArray()
  {
    this(32);
  }
  
  public LongArray(int paramInt)
  {
    this.values = new long[paramInt];
  }
  
  public void add(long paramLong)
  {
    if (this.size == this.values.length) {
      this.values = Arrays.copyOf(this.values, this.size * 2);
    }
    long[] arrayOfLong = this.values;
    int i = this.size;
    this.size = (i + 1);
    arrayOfLong[i] = paramLong;
  }
  
  public long get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size)) {
      throw new IndexOutOfBoundsException("Invalid size " + paramInt + ", size is " + this.size);
    }
    return this.values[paramInt];
  }
  
  public int size()
  {
    return this.size;
  }
  
  public long[] toArray()
  {
    return Arrays.copyOf(this.values, this.size);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/util/LongArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */