package org.apache.commons.io;

import java.io.IOException;
import java.io.OutputStream;

public class HexDump
{
  public static final String EOL = System.getProperty("line.separator");
  private static final char[] _hexcodes = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  private static final int[] _shifts = { 28, 24, 20, 16, 12, 8, 4, 0 };
  
  private static StringBuilder dump(StringBuilder paramStringBuilder, byte paramByte)
  {
    int i = 0;
    while (i < 2)
    {
      paramStringBuilder.append(_hexcodes[(paramByte >> _shifts[(i + 6)] & 0xF)]);
      i += 1;
    }
    return paramStringBuilder;
  }
  
  private static StringBuilder dump(StringBuilder paramStringBuilder, long paramLong)
  {
    int i = 0;
    while (i < 8)
    {
      paramStringBuilder.append(_hexcodes[((int)(paramLong >> _shifts[i]) & 0xF)]);
      i += 1;
    }
    return paramStringBuilder;
  }
  
  public static void dump(byte[] paramArrayOfByte, long paramLong, OutputStream paramOutputStream, int paramInt)
    throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException
  {
    if ((paramInt < 0) || (paramInt >= paramArrayOfByte.length)) {
      throw new ArrayIndexOutOfBoundsException("illegal index: " + paramInt + " into array of length " + paramArrayOfByte.length);
    }
    if (paramOutputStream == null) {
      throw new IllegalArgumentException("cannot write to nullstream");
    }
    paramLong += paramInt;
    StringBuilder localStringBuilder = new StringBuilder(74);
    while (paramInt < paramArrayOfByte.length)
    {
      int j = paramArrayOfByte.length - paramInt;
      int i = j;
      if (j > 16) {
        i = 16;
      }
      dump(localStringBuilder, paramLong).append(' ');
      j = 0;
      if (j < 16)
      {
        if (j < i) {
          dump(localStringBuilder, paramArrayOfByte[(j + paramInt)]);
        }
        for (;;)
        {
          localStringBuilder.append(' ');
          j += 1;
          break;
          localStringBuilder.append("  ");
        }
      }
      j = 0;
      if (j < i)
      {
        if ((paramArrayOfByte[(j + paramInt)] >= 32) && (paramArrayOfByte[(j + paramInt)] < Byte.MAX_VALUE)) {
          localStringBuilder.append((char)paramArrayOfByte[(j + paramInt)]);
        }
        for (;;)
        {
          j += 1;
          break;
          localStringBuilder.append('.');
        }
      }
      localStringBuilder.append(EOL);
      paramOutputStream.write(localStringBuilder.toString().getBytes());
      paramOutputStream.flush();
      localStringBuilder.setLength(0);
      paramLong += i;
      paramInt += 16;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/HexDump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */