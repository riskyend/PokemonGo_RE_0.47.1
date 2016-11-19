package com.google.android.exoplayer.extractor.mp3;

final class ConstantBitrateSeeker
  implements Mp3Extractor.Seeker
{
  private static final int BITS_PER_BYTE = 8;
  private final int bitrate;
  private final long durationUs;
  private final long firstFramePosition;
  
  public ConstantBitrateSeeker(long paramLong1, int paramInt, long paramLong2)
  {
    this.firstFramePosition = paramLong1;
    this.bitrate = paramInt;
    if (paramLong2 == -1L) {}
    for (paramLong1 = l;; paramLong1 = getTimeUs(paramLong2))
    {
      this.durationUs = paramLong1;
      return;
    }
  }
  
  public long getDurationUs()
  {
    return this.durationUs;
  }
  
  public long getPosition(long paramLong)
  {
    if (this.durationUs == -1L) {
      return 0L;
    }
    return this.firstFramePosition + this.bitrate * paramLong / 8000000L;
  }
  
  public long getTimeUs(long paramLong)
  {
    return Math.max(0L, paramLong - this.firstFramePosition) * 1000000L * 8L / this.bitrate;
  }
  
  public boolean isSeekable()
  {
    return this.durationUs != -1L;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp3/ConstantBitrateSeeker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */