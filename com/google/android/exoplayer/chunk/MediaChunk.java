package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.util.Assertions;

public abstract class MediaChunk
  extends Chunk
{
  public final int chunkIndex;
  public final long endTimeUs;
  public final long startTimeUs;
  
  public MediaChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, Format paramFormat, long paramLong1, long paramLong2, int paramInt2)
  {
    this(paramDataSource, paramDataSpec, paramInt1, paramFormat, paramLong1, paramLong2, paramInt2, -1);
  }
  
  public MediaChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, Format paramFormat, long paramLong1, long paramLong2, int paramInt2, int paramInt3)
  {
    super(paramDataSource, paramDataSpec, 1, paramInt1, paramFormat, paramInt3);
    Assertions.checkNotNull(paramFormat);
    this.startTimeUs = paramLong1;
    this.endTimeUs = paramLong2;
    this.chunkIndex = paramInt2;
  }
  
  public int getNextChunkIndex()
  {
    return this.chunkIndex + 1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/MediaChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */