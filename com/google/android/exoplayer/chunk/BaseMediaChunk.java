package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.DefaultTrackOutput;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;

public abstract class BaseMediaChunk
  extends MediaChunk
{
  private int firstSampleIndex;
  public final boolean isMediaFormatFinal;
  private DefaultTrackOutput output;
  
  public BaseMediaChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, Format paramFormat, long paramLong1, long paramLong2, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    super(paramDataSource, paramDataSpec, paramInt1, paramFormat, paramLong1, paramLong2, paramInt2, paramInt3);
    this.isMediaFormatFinal = paramBoolean;
  }
  
  public abstract DrmInitData getDrmInitData();
  
  public final int getFirstSampleIndex()
  {
    return this.firstSampleIndex;
  }
  
  public abstract MediaFormat getMediaFormat();
  
  protected final DefaultTrackOutput getOutput()
  {
    return this.output;
  }
  
  public void init(DefaultTrackOutput paramDefaultTrackOutput)
  {
    this.output = paramDefaultTrackOutput;
    this.firstSampleIndex = paramDefaultTrackOutput.getWriteIndex();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/BaseMediaChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */