package com.google.android.exoplayer.dash.mpd;

import com.google.android.exoplayer.dash.DashSegmentIndex;

final class DashSingleSegmentIndex
  implements DashSegmentIndex
{
  private final RangedUri uri;
  
  public DashSingleSegmentIndex(RangedUri paramRangedUri)
  {
    this.uri = paramRangedUri;
  }
  
  public long getDurationUs(int paramInt, long paramLong)
  {
    return paramLong;
  }
  
  public int getFirstSegmentNum()
  {
    return 0;
  }
  
  public int getLastSegmentNum(long paramLong)
  {
    return 0;
  }
  
  public int getSegmentNum(long paramLong1, long paramLong2)
  {
    return 0;
  }
  
  public RangedUri getSegmentUrl(int paramInt)
  {
    return this.uri;
  }
  
  public long getTimeUs(int paramInt)
  {
    return 0L;
  }
  
  public boolean isExplicit()
  {
    return true;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/dash/mpd/DashSingleSegmentIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */