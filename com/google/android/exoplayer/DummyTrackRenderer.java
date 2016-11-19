package com.google.android.exoplayer;

public final class DummyTrackRenderer
  extends TrackRenderer
{
  protected boolean doPrepare(long paramLong)
    throws ExoPlaybackException
  {
    return true;
  }
  
  protected void doSomeWork(long paramLong1, long paramLong2)
  {
    throw new IllegalStateException();
  }
  
  protected long getBufferedPositionUs()
  {
    throw new IllegalStateException();
  }
  
  protected long getDurationUs()
  {
    throw new IllegalStateException();
  }
  
  protected MediaFormat getFormat(int paramInt)
  {
    throw new IllegalStateException();
  }
  
  protected int getTrackCount()
  {
    return 0;
  }
  
  protected boolean isEnded()
  {
    throw new IllegalStateException();
  }
  
  protected boolean isReady()
  {
    throw new IllegalStateException();
  }
  
  protected void maybeThrowError()
  {
    throw new IllegalStateException();
  }
  
  protected void seekTo(long paramLong)
  {
    throw new IllegalStateException();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/DummyTrackRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */