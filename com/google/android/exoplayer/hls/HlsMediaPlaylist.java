package com.google.android.exoplayer.hls;

import java.util.List;

public final class HlsMediaPlaylist
  extends HlsPlaylist
{
  public static final String ENCRYPTION_METHOD_AES_128 = "AES-128";
  public static final String ENCRYPTION_METHOD_NONE = "NONE";
  public final long durationUs;
  public final boolean live;
  public final int mediaSequence;
  public final List<Segment> segments;
  public final int targetDurationSecs;
  public final int version;
  
  public HlsMediaPlaylist(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, List<Segment> paramList)
  {
    super(paramString, 1);
    this.mediaSequence = paramInt1;
    this.targetDurationSecs = paramInt2;
    this.version = paramInt3;
    this.live = paramBoolean;
    this.segments = paramList;
    if (!paramList.isEmpty())
    {
      paramString = (Segment)paramList.get(paramList.size() - 1);
      this.durationUs = (paramString.startTimeUs + (paramString.durationSecs * 1000000.0D));
      return;
    }
    this.durationUs = 0L;
  }
  
  public static final class Segment
    implements Comparable<Long>
  {
    public final int byterangeLength;
    public final int byterangeOffset;
    public final int discontinuitySequenceNumber;
    public final double durationSecs;
    public final String encryptionIV;
    public final String encryptionKeyUri;
    public final boolean isEncrypted;
    public final long startTimeUs;
    public final String url;
    
    public Segment(String paramString1, double paramDouble, int paramInt1, long paramLong, boolean paramBoolean, String paramString2, String paramString3, int paramInt2, int paramInt3)
    {
      this.url = paramString1;
      this.durationSecs = paramDouble;
      this.discontinuitySequenceNumber = paramInt1;
      this.startTimeUs = paramLong;
      this.isEncrypted = paramBoolean;
      this.encryptionKeyUri = paramString2;
      this.encryptionIV = paramString3;
      this.byterangeOffset = paramInt2;
      this.byterangeLength = paramInt3;
    }
    
    public int compareTo(Long paramLong)
    {
      if (this.startTimeUs > paramLong.longValue()) {
        return 1;
      }
      if (this.startTimeUs < paramLong.longValue()) {
        return -1;
      }
      return 0;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsMediaPlaylist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */