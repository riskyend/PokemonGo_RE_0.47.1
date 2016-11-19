package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class DemuxOutputStream
  extends OutputStream
{
  private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal();
  
  public OutputStream bindStream(OutputStream paramOutputStream)
  {
    OutputStream localOutputStream = (OutputStream)this.m_streams.get();
    this.m_streams.set(paramOutputStream);
    return localOutputStream;
  }
  
  public void close()
    throws IOException
  {
    OutputStream localOutputStream = (OutputStream)this.m_streams.get();
    if (localOutputStream != null) {
      localOutputStream.close();
    }
  }
  
  public void flush()
    throws IOException
  {
    OutputStream localOutputStream = (OutputStream)this.m_streams.get();
    if (localOutputStream != null) {
      localOutputStream.flush();
    }
  }
  
  public void write(int paramInt)
    throws IOException
  {
    OutputStream localOutputStream = (OutputStream)this.m_streams.get();
    if (localOutputStream != null) {
      localOutputStream.write(paramInt);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/output/DemuxOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */