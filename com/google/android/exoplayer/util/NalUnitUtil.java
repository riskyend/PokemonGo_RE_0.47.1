package com.google.android.exoplayer.util;

import java.nio.ByteBuffer;

public final class NalUnitUtil
{
  public static final float[] ASPECT_RATIO_IDC_VALUES;
  public static final int EXTENDED_SAR = 255;
  public static final byte[] NAL_START_CODE = { 0, 0, 0, 1 };
  private static final int NAL_UNIT_TYPE_SPS = 7;
  private static int[] scratchEscapePositions = new int[10];
  private static final Object scratchEscapePositionsLock;
  
  static
  {
    ASPECT_RATIO_IDC_VALUES = new float[] { 1.0F, 1.0F, 1.0909091F, 0.90909094F, 1.4545455F, 1.2121212F, 2.1818182F, 1.8181819F, 2.909091F, 2.4242425F, 1.6363636F, 1.3636364F, 1.939394F, 1.6161616F, 1.3333334F, 1.5F, 2.0F };
    scratchEscapePositionsLock = new Object();
  }
  
  public static void clearPrefixFlags(boolean[] paramArrayOfBoolean)
  {
    paramArrayOfBoolean[0] = false;
    paramArrayOfBoolean[1] = false;
    paramArrayOfBoolean[2] = false;
  }
  
  public static void discardToSps(ByteBuffer paramByteBuffer)
  {
    int m = paramByteBuffer.position();
    int i = 0;
    int k = 0;
    while (k + 1 < m)
    {
      int n = paramByteBuffer.get(k) & 0xFF;
      int j;
      if (i == 3)
      {
        j = i;
        if (n == 1)
        {
          j = i;
          if ((paramByteBuffer.get(k + 1) & 0x1F) == 7)
          {
            ByteBuffer localByteBuffer = paramByteBuffer.duplicate();
            localByteBuffer.position(k - 3);
            localByteBuffer.limit(m);
            paramByteBuffer.position(0);
            paramByteBuffer.put(localByteBuffer);
          }
        }
      }
      else
      {
        j = i;
        if (n == 0) {
          j = i + 1;
        }
      }
      i = j;
      if (n != 0) {
        i = 0;
      }
      k += 1;
    }
    paramByteBuffer.clear();
  }
  
  public static int findNalUnit(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean[] paramArrayOfBoolean)
  {
    boolean bool2 = true;
    int i = paramInt2 - paramInt1;
    if (i >= 0)
    {
      bool1 = true;
      Assertions.checkState(bool1);
      if (i != 0) {
        break label34;
      }
    }
    label34:
    do
    {
      return paramInt2;
      bool1 = false;
      break;
      if (paramArrayOfBoolean != null)
      {
        if (paramArrayOfBoolean[0] != 0)
        {
          clearPrefixFlags(paramArrayOfBoolean);
          return paramInt1 - 3;
        }
        if ((i > 1) && (paramArrayOfBoolean[1] != 0) && (paramArrayOfByte[paramInt1] == 1))
        {
          clearPrefixFlags(paramArrayOfBoolean);
          return paramInt1 - 2;
        }
        if ((i > 2) && (paramArrayOfBoolean[2] != 0) && (paramArrayOfByte[paramInt1] == 0) && (paramArrayOfByte[(paramInt1 + 1)] == 1))
        {
          clearPrefixFlags(paramArrayOfBoolean);
          return paramInt1 - 1;
        }
      }
      paramInt1 += 2;
      if (paramInt1 < paramInt2 - 1)
      {
        if ((paramArrayOfByte[paramInt1] & 0xFE) != 0) {}
        for (;;)
        {
          paramInt1 += 3;
          break;
          if ((paramArrayOfByte[(paramInt1 - 2)] == 0) && (paramArrayOfByte[(paramInt1 - 1)] == 0) && (paramArrayOfByte[paramInt1] == 1))
          {
            if (paramArrayOfBoolean != null) {
              clearPrefixFlags(paramArrayOfBoolean);
            }
            return paramInt1 - 2;
          }
          paramInt1 -= 2;
        }
      }
    } while (paramArrayOfBoolean == null);
    if (i > 2) {
      if ((paramArrayOfByte[(paramInt2 - 3)] == 0) && (paramArrayOfByte[(paramInt2 - 2)] == 0) && (paramArrayOfByte[(paramInt2 - 1)] == 1))
      {
        bool1 = true;
        paramArrayOfBoolean[0] = bool1;
        if (i <= 1) {
          break label356;
        }
        if ((paramArrayOfByte[(paramInt2 - 2)] != 0) || (paramArrayOfByte[(paramInt2 - 1)] != 0)) {
          break label350;
        }
        bool1 = true;
        label252:
        paramArrayOfBoolean[1] = bool1;
        if (paramArrayOfByte[(paramInt2 - 1)] != 0) {
          break label382;
        }
      }
    }
    label350:
    label356:
    label382:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      paramArrayOfBoolean[2] = bool1;
      return paramInt2;
      bool1 = false;
      break;
      if (i == 2)
      {
        if ((paramArrayOfBoolean[2] != 0) && (paramArrayOfByte[(paramInt2 - 2)] == 0) && (paramArrayOfByte[(paramInt2 - 1)] == 1))
        {
          bool1 = true;
          break;
        }
        bool1 = false;
        break;
      }
      if ((paramArrayOfBoolean[1] != 0) && (paramArrayOfByte[(paramInt2 - 1)] == 1))
      {
        bool1 = true;
        break;
      }
      bool1 = false;
      break;
      bool1 = false;
      break label252;
      if ((paramArrayOfBoolean[2] != 0) && (paramArrayOfByte[(paramInt2 - 1)] == 0))
      {
        bool1 = true;
        break label252;
      }
      bool1 = false;
      break label252;
    }
  }
  
  private static int findNextUnescapeIndex(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2 - 2)
    {
      if ((paramArrayOfByte[paramInt1] == 0) && (paramArrayOfByte[(paramInt1 + 1)] == 0) && (paramArrayOfByte[(paramInt1 + 2)] == 3)) {
        return paramInt1;
      }
      paramInt1 += 1;
    }
    return paramInt2;
  }
  
  public static int getH265NalUnitType(byte[] paramArrayOfByte, int paramInt)
  {
    return (paramArrayOfByte[(paramInt + 3)] & 0x7E) >> 1;
  }
  
  public static int getNalUnitType(byte[] paramArrayOfByte, int paramInt)
  {
    return paramArrayOfByte[(paramInt + 3)] & 0x1F;
  }
  
  public static byte[] parseChildNalUnit(ParsableByteArray paramParsableByteArray)
  {
    int i = paramParsableByteArray.readUnsignedShort();
    int j = paramParsableByteArray.getPosition();
    paramParsableByteArray.skipBytes(i);
    return CodecSpecificDataUtil.buildNalUnit(paramParsableByteArray.data, j, i);
  }
  
  /* Error */
  public static int unescapeStream(byte[] paramArrayOfByte, int paramInt)
  {
    // Byte code:
    //   0: getstatic 44	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositionsLock	Ljava/lang/Object;
    //   3: astore 8
    //   5: aload 8
    //   7: monitorenter
    //   8: iconst_0
    //   9: istore_2
    //   10: iconst_0
    //   11: istore_3
    //   12: iload_2
    //   13: iload_1
    //   14: if_icmpge +66 -> 80
    //   17: aload_0
    //   18: iload_2
    //   19: iload_1
    //   20: invokestatic 120	com/google/android/exoplayer/util/NalUnitUtil:findNextUnescapeIndex	([BII)I
    //   23: istore 4
    //   25: iload 4
    //   27: istore_2
    //   28: iload 4
    //   30: iload_1
    //   31: if_icmpge -19 -> 12
    //   34: getstatic 46	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositions	[I
    //   37: arraylength
    //   38: iload_3
    //   39: if_icmpgt +18 -> 57
    //   42: getstatic 46	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositions	[I
    //   45: getstatic 46	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositions	[I
    //   48: arraylength
    //   49: iconst_2
    //   50: imul
    //   51: invokestatic 126	java/util/Arrays:copyOf	([II)[I
    //   54: putstatic 46	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositions	[I
    //   57: getstatic 46	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositions	[I
    //   60: astore 9
    //   62: aload 9
    //   64: iload_3
    //   65: iload 4
    //   67: iastore
    //   68: iload 4
    //   70: iconst_3
    //   71: iadd
    //   72: istore_2
    //   73: iload_3
    //   74: iconst_1
    //   75: iadd
    //   76: istore_3
    //   77: goto -65 -> 12
    //   80: iload_1
    //   81: iload_3
    //   82: isub
    //   83: istore 5
    //   85: iconst_0
    //   86: istore_2
    //   87: iconst_0
    //   88: istore 4
    //   90: iconst_0
    //   91: istore_1
    //   92: iload_1
    //   93: iload_3
    //   94: if_icmpge +65 -> 159
    //   97: getstatic 46	com/google/android/exoplayer/util/NalUnitUtil:scratchEscapePositions	[I
    //   100: iload_1
    //   101: iaload
    //   102: iload_2
    //   103: isub
    //   104: istore 6
    //   106: aload_0
    //   107: iload_2
    //   108: aload_0
    //   109: iload 4
    //   111: iload 6
    //   113: invokestatic 132	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   116: iload 4
    //   118: iload 6
    //   120: iadd
    //   121: istore 4
    //   123: iload 4
    //   125: iconst_1
    //   126: iadd
    //   127: istore 7
    //   129: aload_0
    //   130: iload 4
    //   132: iconst_0
    //   133: bastore
    //   134: iload 7
    //   136: iconst_1
    //   137: iadd
    //   138: istore 4
    //   140: aload_0
    //   141: iload 7
    //   143: iconst_0
    //   144: bastore
    //   145: iload_2
    //   146: iload 6
    //   148: iconst_3
    //   149: iadd
    //   150: iadd
    //   151: istore_2
    //   152: iload_1
    //   153: iconst_1
    //   154: iadd
    //   155: istore_1
    //   156: goto -64 -> 92
    //   159: aload_0
    //   160: iload_2
    //   161: aload_0
    //   162: iload 4
    //   164: iload 5
    //   166: iload 4
    //   168: isub
    //   169: invokestatic 132	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   172: aload 8
    //   174: monitorexit
    //   175: iload 5
    //   177: ireturn
    //   178: astore_0
    //   179: aload 8
    //   181: monitorexit
    //   182: aload_0
    //   183: athrow
    //   184: astore_0
    //   185: goto -6 -> 179
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	paramArrayOfByte	byte[]
    //   0	188	1	paramInt	int
    //   9	152	2	i	int
    //   11	84	3	j	int
    //   23	146	4	k	int
    //   83	93	5	m	int
    //   104	46	6	n	int
    //   127	15	7	i1	int
    //   3	177	8	localObject	Object
    //   60	3	9	arrayOfInt	int[]
    // Exception table:
    //   from	to	target	type
    //   17	25	178	finally
    //   34	57	178	finally
    //   57	62	178	finally
    //   97	116	178	finally
    //   159	175	178	finally
    //   179	182	184	finally
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/util/NalUnitUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */