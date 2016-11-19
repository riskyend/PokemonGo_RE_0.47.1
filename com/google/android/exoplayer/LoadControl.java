package com.google.android.exoplayer;

import com.google.android.exoplayer.upstream.Allocator;

public abstract interface LoadControl
{
  public abstract Allocator getAllocator();
  
  public abstract void register(Object paramObject, int paramInt);
  
  public abstract void trimAllocator();
  
  public abstract void unregister(Object paramObject);
  
  public abstract boolean update(Object paramObject, long paramLong1, long paramLong2, boolean paramBoolean);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/LoadControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */