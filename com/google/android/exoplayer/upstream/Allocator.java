package com.google.android.exoplayer.upstream;

public abstract interface Allocator
{
  public abstract Allocation allocate();
  
  public abstract void blockWhileTotalBytesAllocatedExceeds(int paramInt)
    throws InterruptedException;
  
  public abstract int getIndividualAllocationLength();
  
  public abstract int getTotalBytesAllocated();
  
  public abstract void release(Allocation paramAllocation);
  
  public abstract void trim(int paramInt);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/Allocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */