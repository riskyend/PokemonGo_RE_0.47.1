package com.google.android.exoplayer;

import android.os.SystemClock;

final class StandaloneMediaClock
  implements MediaClock
{
  private long deltaUs;
  private long positionUs;
  private boolean started;
  
  private long elapsedRealtimeMinus(long paramLong)
  {
    return SystemClock.elapsedRealtime() * 1000L - paramLong;
  }
  
  public long getPositionUs()
  {
    if (this.started) {
      return elapsedRealtimeMinus(this.deltaUs);
    }
    return this.positionUs;
  }
  
  public void setPositionUs(long paramLong)
  {
    this.positionUs = paramLong;
    this.deltaUs = elapsedRealtimeMinus(paramLong);
  }
  
  public void start()
  {
    if (!this.started)
    {
      this.started = true;
      this.deltaUs = elapsedRealtimeMinus(this.positionUs);
    }
  }
  
  public void stop()
  {
    if (this.started)
    {
      this.positionUs = elapsedRealtimeMinus(this.deltaUs);
      this.started = false;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/StandaloneMediaClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */