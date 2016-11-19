package com.google.android.exoplayer.dash;

import com.google.android.exoplayer.dash.mpd.RangedUri;

public abstract interface DashSegmentIndex
{
  public static final int INDEX_UNBOUNDED = -1;
  
  public abstract long getDurationUs(int paramInt, long paramLong);
  
  public abstract int getFirstSegmentNum();
  
  public abstract int getLastSegmentNum(long paramLong);
  
  public abstract int getSegmentNum(long paramLong1, long paramLong2);
  
  public abstract RangedUri getSegmentUrl(int paramInt);
  
  public abstract long getTimeUs(int paramInt);
  
  public abstract boolean isExplicit();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/dash/DashSegmentIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */