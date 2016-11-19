package com.google.android.exoplayer.upstream;

import com.google.android.exoplayer.util.Assertions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class ByteArrayDataSink
  implements DataSink
{
  private ByteArrayOutputStream stream;
  
  public void close()
    throws IOException
  {
    this.stream.close();
  }
  
  public byte[] getData()
  {
    if (this.stream == null) {
      return null;
    }
    return this.stream.toByteArray();
  }
  
  public DataSink open(DataSpec paramDataSpec)
    throws IOException
  {
    if (paramDataSpec.length == -1L)
    {
      this.stream = new ByteArrayOutputStream();
      return this;
    }
    if (paramDataSpec.length <= 2147483647L) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkArgument(bool);
      this.stream = new ByteArrayOutputStream((int)paramDataSpec.length);
      return this;
    }
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.stream.write(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/ByteArrayDataSink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */