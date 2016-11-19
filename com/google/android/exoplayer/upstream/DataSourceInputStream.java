package com.google.android.exoplayer.upstream;

import com.google.android.exoplayer.util.Assertions;
import java.io.IOException;
import java.io.InputStream;

public final class DataSourceInputStream
  extends InputStream
{
  private boolean closed = false;
  private final DataSource dataSource;
  private final DataSpec dataSpec;
  private boolean opened = false;
  private final byte[] singleByteArray;
  
  public DataSourceInputStream(DataSource paramDataSource, DataSpec paramDataSpec)
  {
    this.dataSource = paramDataSource;
    this.dataSpec = paramDataSpec;
    this.singleByteArray = new byte[1];
  }
  
  private void checkOpened()
    throws IOException
  {
    if (!this.opened)
    {
      this.dataSource.open(this.dataSpec);
      this.opened = true;
    }
  }
  
  public void close()
    throws IOException
  {
    if (!this.closed)
    {
      this.dataSource.close();
      this.closed = true;
    }
  }
  
  public void open()
    throws IOException
  {
    checkOpened();
  }
  
  public int read()
    throws IOException
  {
    if (read(this.singleByteArray) == -1) {
      return -1;
    }
    return this.singleByteArray[0] & 0xFF;
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (!this.closed) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      checkOpened();
      return this.dataSource.read(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    if (!this.closed) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      checkOpened();
      return super.skip(paramLong);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/DataSourceInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */