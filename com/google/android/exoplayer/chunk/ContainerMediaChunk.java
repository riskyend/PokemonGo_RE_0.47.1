package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.DefaultTrackOutput;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;

public class ContainerMediaChunk
  extends BaseMediaChunk
  implements ChunkExtractorWrapper.SingleTrackOutput
{
  private final int adaptiveMaxHeight;
  private final int adaptiveMaxWidth;
  private volatile int bytesLoaded;
  private DrmInitData drmInitData;
  private final ChunkExtractorWrapper extractorWrapper;
  private volatile boolean loadCanceled;
  private MediaFormat mediaFormat;
  private final long sampleOffsetUs;
  
  public ContainerMediaChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, Format paramFormat, long paramLong1, long paramLong2, int paramInt2, long paramLong3, ChunkExtractorWrapper paramChunkExtractorWrapper, MediaFormat paramMediaFormat, int paramInt3, int paramInt4, DrmInitData paramDrmInitData, boolean paramBoolean, int paramInt5)
  {
    super(paramDataSource, paramDataSpec, paramInt1, paramFormat, paramLong1, paramLong2, paramInt2, paramBoolean, paramInt5);
    this.extractorWrapper = paramChunkExtractorWrapper;
    this.sampleOffsetUs = paramLong3;
    this.adaptiveMaxWidth = paramInt3;
    this.adaptiveMaxHeight = paramInt4;
    this.mediaFormat = getAdjustedMediaFormat(paramMediaFormat, paramLong3, paramInt3, paramInt4);
    this.drmInitData = paramDrmInitData;
  }
  
  private static MediaFormat getAdjustedMediaFormat(MediaFormat paramMediaFormat, long paramLong, int paramInt1, int paramInt2)
  {
    if (paramMediaFormat == null) {
      paramMediaFormat = null;
    }
    MediaFormat localMediaFormat;
    do
    {
      return paramMediaFormat;
      localMediaFormat = paramMediaFormat;
      if (paramLong != 0L)
      {
        localMediaFormat = paramMediaFormat;
        if (paramMediaFormat.subsampleOffsetUs != Long.MAX_VALUE) {
          localMediaFormat = paramMediaFormat.copyWithSubsampleOffsetUs(paramMediaFormat.subsampleOffsetUs + paramLong);
        }
      }
      if (paramInt1 != -1) {
        break;
      }
      paramMediaFormat = localMediaFormat;
    } while (paramInt2 == -1);
    return localMediaFormat.copyWithMaxVideoDimensions(paramInt1, paramInt2);
  }
  
  public final long bytesLoaded()
  {
    return this.bytesLoaded;
  }
  
  public final void cancelLoad()
  {
    this.loadCanceled = true;
  }
  
  public final void drmInitData(DrmInitData paramDrmInitData)
  {
    this.drmInitData = paramDrmInitData;
  }
  
  public final void format(MediaFormat paramMediaFormat)
  {
    this.mediaFormat = getAdjustedMediaFormat(paramMediaFormat, this.sampleOffsetUs, this.adaptiveMaxWidth, this.adaptiveMaxHeight);
  }
  
  public final DrmInitData getDrmInitData()
  {
    return this.drmInitData;
  }
  
  public final MediaFormat getMediaFormat()
  {
    return this.mediaFormat;
  }
  
  public final boolean isLoadCanceled()
  {
    return this.loadCanceled;
  }
  
  /* Error */
  public final void load()
    throws IOException, InterruptedException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 82	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   4: aload_0
    //   5: getfield 60	com/google/android/exoplayer/chunk/ContainerMediaChunk:bytesLoaded	I
    //   8: invokestatic 88	com/google/android/exoplayer/util/Util:getRemainderDataSpec	(Lcom/google/android/exoplayer/upstream/DataSpec;I)Lcom/google/android/exoplayer/upstream/DataSpec;
    //   11: astore_2
    //   12: new 90	com/google/android/exoplayer/extractor/DefaultExtractorInput
    //   15: dup
    //   16: aload_0
    //   17: getfield 94	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   20: aload_2
    //   21: getfield 99	com/google/android/exoplayer/upstream/DataSpec:absoluteStreamPosition	J
    //   24: aload_0
    //   25: getfield 94	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   28: aload_2
    //   29: invokeinterface 105 2 0
    //   34: invokespecial 108	com/google/android/exoplayer/extractor/DefaultExtractorInput:<init>	(Lcom/google/android/exoplayer/upstream/DataSource;JJ)V
    //   37: astore_2
    //   38: aload_0
    //   39: getfield 60	com/google/android/exoplayer/chunk/ContainerMediaChunk:bytesLoaded	I
    //   42: ifne +11 -> 53
    //   45: aload_0
    //   46: getfield 27	com/google/android/exoplayer/chunk/ContainerMediaChunk:extractorWrapper	Lcom/google/android/exoplayer/chunk/ChunkExtractorWrapper;
    //   49: aload_0
    //   50: invokevirtual 114	com/google/android/exoplayer/chunk/ChunkExtractorWrapper:init	(Lcom/google/android/exoplayer/chunk/ChunkExtractorWrapper$SingleTrackOutput;)V
    //   53: iconst_0
    //   54: istore_1
    //   55: iload_1
    //   56: ifne +22 -> 78
    //   59: aload_0
    //   60: getfield 64	com/google/android/exoplayer/chunk/ContainerMediaChunk:loadCanceled	Z
    //   63: ifne +15 -> 78
    //   66: aload_0
    //   67: getfield 27	com/google/android/exoplayer/chunk/ContainerMediaChunk:extractorWrapper	Lcom/google/android/exoplayer/chunk/ChunkExtractorWrapper;
    //   70: aload_2
    //   71: invokevirtual 118	com/google/android/exoplayer/chunk/ChunkExtractorWrapper:read	(Lcom/google/android/exoplayer/extractor/ExtractorInput;)I
    //   74: istore_1
    //   75: goto -20 -> 55
    //   78: aload_0
    //   79: aload_2
    //   80: invokeinterface 123 1 0
    //   85: aload_0
    //   86: getfield 82	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   89: getfield 99	com/google/android/exoplayer/upstream/DataSpec:absoluteStreamPosition	J
    //   92: lsub
    //   93: l2i
    //   94: putfield 60	com/google/android/exoplayer/chunk/ContainerMediaChunk:bytesLoaded	I
    //   97: aload_0
    //   98: getfield 94	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   101: invokeinterface 126 1 0
    //   106: return
    //   107: astore_3
    //   108: aload_0
    //   109: aload_2
    //   110: invokeinterface 123 1 0
    //   115: aload_0
    //   116: getfield 82	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   119: getfield 99	com/google/android/exoplayer/upstream/DataSpec:absoluteStreamPosition	J
    //   122: lsub
    //   123: l2i
    //   124: putfield 60	com/google/android/exoplayer/chunk/ContainerMediaChunk:bytesLoaded	I
    //   127: aload_3
    //   128: athrow
    //   129: astore_2
    //   130: aload_0
    //   131: getfield 94	com/google/android/exoplayer/chunk/ContainerMediaChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   134: invokeinterface 126 1 0
    //   139: aload_2
    //   140: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	this	ContainerMediaChunk
    //   54	21	1	i	int
    //   11	99	2	localObject1	Object
    //   129	11	2	localObject2	Object
    //   107	21	3	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   59	75	107	finally
    //   12	53	129	finally
    //   78	97	129	finally
    //   108	129	129	finally
  }
  
  public final int sampleData(ExtractorInput paramExtractorInput, int paramInt, boolean paramBoolean)
    throws IOException, InterruptedException
  {
    return getOutput().sampleData(paramExtractorInput, paramInt, paramBoolean);
  }
  
  public final void sampleData(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    getOutput().sampleData(paramParsableByteArray, paramInt);
  }
  
  public final void sampleMetadata(long paramLong, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    getOutput().sampleMetadata(this.sampleOffsetUs + paramLong, paramInt1, paramInt2, paramInt3, paramArrayOfByte);
  }
  
  public final void seekMap(SeekMap paramSeekMap) {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/ContainerMediaChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */