package com.upsight.android.internal.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipHelper
{
  public static byte[] compress(String paramString)
    throws IOException
  {
    if (paramString == null) {
      return null;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(localByteArrayOutputStream));
    try
    {
      localGZIPOutputStream.write(paramString.getBytes());
      return localByteArrayOutputStream.toByteArray();
    }
    finally
    {
      localGZIPOutputStream.close();
    }
  }
  
  public static String decompress(byte[] paramArrayOfByte)
    throws IOException
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    paramArrayOfByte = new GZIPInputStream(new ByteArrayInputStream(paramArrayOfByte));
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramArrayOfByte));
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        localStringBuilder.append(str);
      }
    }
    finally
    {
      paramArrayOfByte.close();
    }
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/util/GzipHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */