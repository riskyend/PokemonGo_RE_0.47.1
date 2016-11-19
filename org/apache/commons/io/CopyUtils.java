package org.apache.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

@Deprecated
public class CopyUtils
{
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  
  public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte['က'];
    int i = 0;
    for (;;)
    {
      int j = paramInputStream.read(arrayOfByte);
      if (-1 == j) {
        break;
      }
      paramOutputStream.write(arrayOfByte, 0, j);
      i += j;
    }
    return i;
  }
  
  public static int copy(Reader paramReader, Writer paramWriter)
    throws IOException
  {
    char[] arrayOfChar = new char['က'];
    int i = 0;
    for (;;)
    {
      int j = paramReader.read(arrayOfChar);
      if (-1 == j) {
        break;
      }
      paramWriter.write(arrayOfChar, 0, j);
      i += j;
    }
    return i;
  }
  
  public static void copy(InputStream paramInputStream, Writer paramWriter)
    throws IOException
  {
    copy(new InputStreamReader(paramInputStream), paramWriter);
  }
  
  public static void copy(InputStream paramInputStream, Writer paramWriter, String paramString)
    throws IOException
  {
    copy(new InputStreamReader(paramInputStream, paramString), paramWriter);
  }
  
  public static void copy(Reader paramReader, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream = new OutputStreamWriter(paramOutputStream);
    copy(paramReader, paramOutputStream);
    paramOutputStream.flush();
  }
  
  public static void copy(String paramString, OutputStream paramOutputStream)
    throws IOException
  {
    paramString = new StringReader(paramString);
    paramOutputStream = new OutputStreamWriter(paramOutputStream);
    copy(paramString, paramOutputStream);
    paramOutputStream.flush();
  }
  
  public static void copy(String paramString, Writer paramWriter)
    throws IOException
  {
    paramWriter.write(paramString);
  }
  
  public static void copy(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(paramArrayOfByte);
  }
  
  public static void copy(byte[] paramArrayOfByte, Writer paramWriter)
    throws IOException
  {
    copy(new ByteArrayInputStream(paramArrayOfByte), paramWriter);
  }
  
  public static void copy(byte[] paramArrayOfByte, Writer paramWriter, String paramString)
    throws IOException
  {
    copy(new ByteArrayInputStream(paramArrayOfByte), paramWriter, paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/CopyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */