package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream
  extends ProxyInputStream
{
  private final OutputStream branch;
  private final boolean closeBranch;
  
  public TeeInputStream(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    this(paramInputStream, paramOutputStream, false);
  }
  
  public TeeInputStream(InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean)
  {
    super(paramInputStream);
    this.branch = paramOutputStream;
    this.closeBranch = paramBoolean;
  }
  
  public void close()
    throws IOException
  {
    try
    {
      super.close();
      return;
    }
    finally
    {
      if (this.closeBranch) {
        this.branch.close();
      }
    }
  }
  
  public int read()
    throws IOException
  {
    int i = super.read();
    if (i != -1) {
      this.branch.write(i);
    }
    return i;
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = super.read(paramArrayOfByte);
    if (i != -1) {
      this.branch.write(paramArrayOfByte, 0, i);
    }
    return i;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    paramInt2 = super.read(paramArrayOfByte, paramInt1, paramInt2);
    if (paramInt2 != -1) {
      this.branch.write(paramArrayOfByte, paramInt1, paramInt2);
    }
    return paramInt2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/input/TeeInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */