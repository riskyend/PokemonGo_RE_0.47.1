package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;

public final class InitializationChunk
  extends Chunk
  implements ChunkExtractorWrapper.SingleTrackOutput
{
  private volatile int bytesLoaded;
  private DrmInitData drmInitData;
  private final ChunkExtractorWrapper extractorWrapper;
  private volatile boolean loadCanceled;
  private MediaFormat mediaFormat;
  private SeekMap seekMap;
  
  public InitializationChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt, Format paramFormat, ChunkExtractorWrapper paramChunkExtractorWrapper)
  {
    this(paramDataSource, paramDataSpec, paramInt, paramFormat, paramChunkExtractorWrapper, -1);
  }
  
  public InitializationChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, Format paramFormat, ChunkExtractorWrapper paramChunkExtractorWrapper, int paramInt2)
  {
    super(paramDataSource, paramDataSpec, 2, paramInt1, paramFormat, paramInt2);
    this.extractorWrapper = paramChunkExtractorWrapper;
  }
  
  public long bytesLoaded()
  {
    return this.bytesLoaded;
  }
  
  public void cancelLoad()
  {
    this.loadCanceled = true;
  }
  
  public void drmInitData(DrmInitData paramDrmInitData)
  {
    this.drmInitData = paramDrmInitData;
  }
  
  public void format(MediaFormat paramMediaFormat)
  {
    this.mediaFormat = paramMediaFormat;
  }
  
  public DrmInitData getDrmInitData()
  {
    return this.drmInitData;
  }
  
  public MediaFormat getFormat()
  {
    return this.mediaFormat;
  }
  
  public SeekMap getSeekMap()
  {
    return this.seekMap;
  }
  
  public boolean hasDrmInitData()
  {
    return this.drmInitData != null;
  }
  
  public boolean hasFormat()
  {
    return this.mediaFormat != null;
  }
  
  public boolean hasSeekMap()
  {
    return this.seekMap != null;
  }
  
  public boolean isLoadCanceled()
  {
    return this.loadCanceled;
  }
  
  /* Error */
  public void load()
    throws IOException, InterruptedException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 65	com/google/android/exoplayer/chunk/InitializationChunk:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   4: aload_0
    //   5: getfield 32	com/google/android/exoplayer/chunk/InitializationChunk:bytesLoaded	I
    //   8: invokestatic 71	com/google/android/exoplayer/util/Util:getRemainderDataSpec	(Lcom/google/android/exoplayer/upstream/DataSpec;I)Lcom/google/android/exoplayer/upstream/DataSpec;
    //   11: astore_2
    //   12: new 73	com/google/android/exoplayer/extractor/DefaultExtractorInput
    //   15: dup
    //   16: aload_0
    //   17: getfield 77	com/google/android/exoplayer/chunk/InitializationChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   20: aload_2
    //   21: getfield 83	com/google/android/exoplayer/upstream/DataSpec:absoluteStreamPosition	J
    //   24: aload_0
    //   25: getfield 77	com/google/android/exoplayer/chunk/InitializationChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   28: aload_2
    //   29: invokeinterface 89 2 0
    //   34: invokespecial 92	com/google/android/exoplayer/extractor/DefaultExtractorInput:<init>	(Lcom/google/android/exoplayer/upstream/DataSource;JJ)V
    //   37: astore_2
    //   38: aload_0
    //   39: getfield 32	com/google/android/exoplayer/chunk/InitializationChunk:bytesLoaded	I
    //   42: ifne +11 -> 53
    //   45: aload_0
    //   46: getfield 29	com/google/android/exoplayer/chunk/InitializationChunk:extractorWrapper	Lcom/google/android/exoplayer/chunk/ChunkExtractorWrapper;
    //   49: aload_0
    //   50: invokevirtual 98	com/google/android/exoplayer/chunk/ChunkExtractorWrapper:init	(Lcom/google/android/exoplayer/chunk/ChunkExtractorWrapper$SingleTrackOutput;)V
    //   53: iconst_0
    //   54: istore_1
    //   55: iload_1
    //   56: ifne +22 -> 78
    //   59: aload_0
    //   60: getfield 36	com/google/android/exoplayer/chunk/InitializationChunk:loadCanceled	Z
    //   63: ifne +15 -> 78
    //   66: aload_0
    //   67: getfield 29	com/google/android/exoplayer/chunk/InitializationChunk:extractorWrapper	Lcom/google/android/exoplayer/chunk/ChunkExtractorWrapper;
    //   70: aload_2
    //   71: invokevirtual 102	com/google/android/exoplayer/chunk/ChunkExtractorWrapper:read	(Lcom/google/android/exoplayer/extractor/ExtractorInput;)I
    //   74: istore_1
    //   75: goto -20 -> 55
    //   78: aload_0
    //   79: aload_2
    //   80: invokeinterface 107 1 0
    //   85: aload_0
    //   86: getfield 65	com/google/android/exoplayer/chunk/InitializationChunk:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   89: getfield 83	com/google/android/exoplayer/upstream/DataSpec:absoluteStreamPosition	J
    //   92: lsub
    //   93: l2i
    //   94: putfield 32	com/google/android/exoplayer/chunk/InitializationChunk:bytesLoaded	I
    //   97: aload_0
    //   98: getfield 77	com/google/android/exoplayer/chunk/InitializationChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   101: invokeinterface 110 1 0
    //   106: return
    //   107: astore_3
    //   108: aload_0
    //   109: aload_2
    //   110: invokeinterface 107 1 0
    //   115: aload_0
    //   116: getfield 65	com/google/android/exoplayer/chunk/InitializationChunk:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   119: getfield 83	com/google/android/exoplayer/upstream/DataSpec:absoluteStreamPosition	J
    //   122: lsub
    //   123: l2i
    //   124: putfield 32	com/google/android/exoplayer/chunk/InitializationChunk:bytesLoaded	I
    //   127: aload_3
    //   128: athrow
    //   129: astore_2
    //   130: aload_0
    //   131: getfield 77	com/google/android/exoplayer/chunk/InitializationChunk:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
    //   134: invokeinterface 110 1 0
    //   139: aload_2
    //   140: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	this	InitializationChunk
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
  
  public int sampleData(ExtractorInput paramExtractorInput, int paramInt, boolean paramBoolean)
    throws IOException, InterruptedException
  {
    throw new IllegalStateException("Unexpected sample data in initialization chunk");
  }
  
  public void sampleData(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    throw new IllegalStateException("Unexpected sample data in initialization chunk");
  }
  
  public void sampleMetadata(long paramLong, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    throw new IllegalStateException("Unexpected sample data in initialization chunk");
  }
  
  public void seekMap(SeekMap paramSeekMap)
  {
    this.seekMap = paramSeekMap;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/InitializationChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */