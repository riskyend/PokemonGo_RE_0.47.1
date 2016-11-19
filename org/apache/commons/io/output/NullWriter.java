package org.apache.commons.io.output;

import java.io.Writer;

public class NullWriter
  extends Writer
{
  public static final NullWriter NULL_WRITER = new NullWriter();
  
  public Writer append(char paramChar)
  {
    return this;
  }
  
  public Writer append(CharSequence paramCharSequence)
  {
    return this;
  }
  
  public Writer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    return this;
  }
  
  public void close() {}
  
  public void flush() {}
  
  public void write(int paramInt) {}
  
  public void write(String paramString) {}
  
  public void write(String paramString, int paramInt1, int paramInt2) {}
  
  public void write(char[] paramArrayOfChar) {}
  
  public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2) {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/output/NullWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */