package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.DefaultTrackOutput;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;

public final class SingleSampleMediaChunk
  extends BaseMediaChunk
{
  private volatile int bytesLoaded;
  private volatile boolean loadCanceled;
  private final DrmInitData sampleDrmInitData;
  private final MediaFormat sampleFormat;
  
  public SingleSampleMediaChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, Format paramFormat, long paramLong1, long paramLong2, int paramInt2, MediaFormat paramMediaFormat, DrmInitData paramDrmInitData, int paramInt3)
  {
    super(paramDataSource, paramDataSpec, paramInt1, paramFormat, paramLong1, paramLong2, paramInt2, true, paramInt3);
    this.sampleFormat = paramMediaFormat;
    this.sampleDrmInitData = paramDrmInitData;
  }
  
  public long bytesLoaded()
  {
    return this.bytesLoaded;
  }
  
  public void cancelLoad()
  {
    this.loadCanceled = true;
  }
  
  public DrmInitData getDrmInitData()
  {
    return this.sampleDrmInitData;
  }
  
  public MediaFormat getMediaFormat()
  {
    return this.sampleFormat;
  }
  
  public boolean isLoadCanceled()
  {
    return this.loadCanceled;
  }
  
  public void load()
    throws IOException, InterruptedException
  {
    DataSpec localDataSpec = Util.getRemainderDataSpec(this.dataSpec, this.bytesLoaded);
    try
    {
      this.dataSource.open(localDataSpec);
      for (int i = 0; i != -1; i = getOutput().sampleData(this.dataSource, Integer.MAX_VALUE, true)) {
        this.bytesLoaded += i;
      }
      i = this.bytesLoaded;
      getOutput().sampleMetadata(this.startTimeUs, 1, i, 0, null);
      return;
    }
    finally
    {
      this.dataSource.close();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/SingleSampleMediaChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */