package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import java.io.IOException;
import java.util.Arrays;

public abstract class DataChunk
  extends Chunk
{
  private static final int READ_GRANULARITY = 16384;
  private byte[] data;
  private int limit;
  private volatile boolean loadCanceled;
  
  public DataChunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, int paramInt2, Format paramFormat, int paramInt3, byte[] paramArrayOfByte)
  {
    super(paramDataSource, paramDataSpec, paramInt1, paramInt2, paramFormat, paramInt3);
    this.data = paramArrayOfByte;
  }
  
  private void maybeExpandData()
  {
    if (this.data == null) {
      this.data = new byte['䀀'];
    }
    while (this.data.length >= this.limit + 16384) {
      return;
    }
    this.data = Arrays.copyOf(this.data, this.data.length + 16384);
  }
  
  public long bytesLoaded()
  {
    return this.limit;
  }
  
  public final void cancelLoad()
  {
    this.loadCanceled = true;
  }
  
  protected abstract void consume(byte[] paramArrayOfByte, int paramInt)
    throws IOException;
  
  public byte[] getDataHolder()
  {
    return this.data;
  }
  
  public final boolean isLoadCanceled()
  {
    return this.loadCanceled;
  }
  
  public final void load()
    throws IOException, InterruptedException
  {
    try
    {
      this.dataSource.open(this.dataSpec);
      this.limit = 0;
      int i = 0;
      while ((i != -1) && (!this.loadCanceled))
      {
        maybeExpandData();
        int j = this.dataSource.read(this.data, this.limit, 16384);
        i = j;
        if (j != -1)
        {
          this.limit += j;
          i = j;
        }
      }
      if (this.loadCanceled) {
        break label111;
      }
    }
    finally
    {
      this.dataSource.close();
    }
    consume(this.data, this.limit);
    label111:
    this.dataSource.close();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/DataChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */