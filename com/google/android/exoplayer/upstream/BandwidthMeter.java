package com.google.android.exoplayer.upstream;

public abstract interface BandwidthMeter
  extends TransferListener
{
  public static final long NO_ESTIMATE = -1L;
  
  public abstract long getBitrateEstimate();
  
  public static abstract interface EventListener
  {
    public abstract void onBandwidthSample(int paramInt, long paramLong1, long paramLong2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/BandwidthMeter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */