package com.google.vr.cardboard;

import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;

public class DisplaySynchronizer
  implements Choreographer.FrameCallback
{
  private static final long FRAME_TIME_NS = 16666666L;
  private Choreographer choreographer = Choreographer.getInstance();
  private final long nativeDisplaySynchronizer = nativeInit(16666666L);
  
  public DisplaySynchronizer()
  {
    this.choreographer.postFrameCallback(this);
  }
  
  private native void nativeAddSyncTime(long paramLong1, long paramLong2);
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit(long paramLong);
  
  private native long nativeRetainNativeDisplaySynchronizer(long paramLong);
  
  private native long nativeSyncToNextVsync(long paramLong);
  
  public void doFrame(long paramLong)
  {
    nativeAddSyncTime(this.nativeDisplaySynchronizer, paramLong);
    this.choreographer.postFrameCallback(this);
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      nativeDestroy(this.nativeDisplaySynchronizer);
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  public long retainNativeDisplaySynchronizer()
  {
    return nativeRetainNativeDisplaySynchronizer(this.nativeDisplaySynchronizer);
  }
  
  public long syncToNextVsync()
  {
    return nativeSyncToNextVsync(this.nativeDisplaySynchronizer);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/vr/cardboard/DisplaySynchronizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */