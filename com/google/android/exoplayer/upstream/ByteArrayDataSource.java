package com.google.android.exoplayer.upstream;

import com.google.android.exoplayer.util.Assertions;
import java.io.IOException;

public final class ByteArrayDataSource
  implements DataSource
{
  private final byte[] data;
  private int readPosition;
  private int remainingBytes;
  
  public ByteArrayDataSource(byte[] paramArrayOfByte)
  {
    Assertions.checkNotNull(paramArrayOfByte);
    if (paramArrayOfByte.length > 0) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkArgument(bool);
      this.data = paramArrayOfByte;
      return;
    }
  }
  
  public void close()
    throws IOException
  {}
  
  public long open(DataSpec paramDataSpec)
    throws IOException
  {
    this.readPosition = ((int)paramDataSpec.position);
    if (paramDataSpec.length == -1L) {}
    for (long l = this.data.length - paramDataSpec.position;; l = paramDataSpec.length)
    {
      this.remainingBytes = ((int)l);
      if ((this.remainingBytes > 0) && (this.readPosition + this.remainingBytes <= this.data.length)) {
        break;
      }
      throw new IOException("Unsatisfiable range: [" + this.readPosition + ", " + paramDataSpec.length + "], length: " + this.data.length);
    }
    return this.remainingBytes;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.remainingBytes == 0) {
      return -1;
    }
    paramInt2 = Math.min(paramInt2, this.remainingBytes);
    System.arraycopy(this.data, this.readPosition, paramArrayOfByte, paramInt1, paramInt2);
    this.readPosition += paramInt2;
    this.remainingBytes -= paramInt2;
    return paramInt2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/ByteArrayDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */