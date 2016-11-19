package com.google.android.exoplayer.util;

import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

public final class CodecSpecificDataUtil
{
  private static final int AUDIO_OBJECT_TYPE_AAC_LC = 2;
  private static final int AUDIO_OBJECT_TYPE_ER_BSAC = 22;
  private static final int AUDIO_OBJECT_TYPE_PS = 29;
  private static final int AUDIO_OBJECT_TYPE_SBR = 5;
  private static final int AUDIO_SPECIFIC_CONFIG_CHANNEL_CONFIGURATION_INVALID = -1;
  private static final int[] AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE = { 0, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, 7, 8, -1, 8, -1 };
  private static final int AUDIO_SPECIFIC_CONFIG_FREQUENCY_INDEX_ARBITRARY = 15;
  private static final int[] AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE;
  private static final byte[] NAL_START_CODE = { 0, 0, 0, 1 };
  private static final String TAG = "CodecSpecificDataUtil";
  
  static
  {
    AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE = new int[] { 96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350 };
  }
  
  public static byte[] buildAacAudioSpecificConfig(int paramInt1, int paramInt2)
  {
    int j = -1;
    int i = 0;
    while (i < AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE.length)
    {
      if (paramInt1 == AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[i]) {
        j = i;
      }
      i += 1;
    }
    i = -1;
    paramInt1 = 0;
    while (paramInt1 < AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE.length)
    {
      if (paramInt2 == AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE[paramInt1]) {
        i = paramInt1;
      }
      paramInt1 += 1;
    }
    return new byte[] { (byte)(j >> 1 | 0x10), (byte)((j & 0x1) << 7 | i << 3) };
  }
  
  public static byte[] buildAacAudioSpecificConfig(int paramInt1, int paramInt2, int paramInt3)
  {
    return new byte[] { (byte)(paramInt1 << 3 & 0xF8 | paramInt2 >> 1 & 0x7), (byte)(paramInt2 << 7 & 0x80 | paramInt3 << 3 & 0x78) };
  }
  
  public static byte[] buildNalUnit(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[NAL_START_CODE.length + paramInt2];
    System.arraycopy(NAL_START_CODE, 0, arrayOfByte, 0, NAL_START_CODE.length);
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, NAL_START_CODE.length, paramInt2);
    return arrayOfByte;
  }
  
  private static int findNalStartCode(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length;
    int j = NAL_START_CODE.length;
    while (paramInt <= i - j)
    {
      if (isNalStartCode(paramArrayOfByte, paramInt)) {
        return paramInt;
      }
      paramInt += 1;
    }
    return -1;
  }
  
  private static boolean isNalStartCode(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length - paramInt <= NAL_START_CODE.length) {
      return false;
    }
    int i = 0;
    for (;;)
    {
      if (i >= NAL_START_CODE.length) {
        break label43;
      }
      if (paramArrayOfByte[(paramInt + i)] != NAL_START_CODE[i]) {
        break;
      }
      i += 1;
    }
    label43:
    return true;
  }
  
  public static Pair<Integer, Integer> parseAacAudioSpecificConfig(byte[] paramArrayOfByte)
  {
    boolean bool2 = true;
    paramArrayOfByte = new ParsableBitArray(paramArrayOfByte);
    int n = paramArrayOfByte.readBits(5);
    int i = paramArrayOfByte.readBits(4);
    int j;
    if (i == 15)
    {
      i = paramArrayOfByte.readBits(24);
      int m = paramArrayOfByte.readBits(4);
      int k;
      if (n != 5)
      {
        k = m;
        j = i;
        if (n != 29) {}
      }
      else
      {
        i = paramArrayOfByte.readBits(4);
        if (i != 15) {
          break label166;
        }
        i = paramArrayOfByte.readBits(24);
        k = m;
        j = i;
        if (paramArrayOfByte.readBits(5) == 22)
        {
          k = paramArrayOfByte.readBits(4);
          j = i;
        }
      }
      i = AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE[k];
      if (i == -1) {
        break label195;
      }
    }
    label166:
    label195:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      Assertions.checkArgument(bool1);
      return Pair.create(Integer.valueOf(j), Integer.valueOf(i));
      if (i < 13) {}
      for (bool1 = true;; bool1 = false)
      {
        Assertions.checkArgument(bool1);
        i = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[i];
        break;
      }
      if (i < 13) {}
      for (bool1 = true;; bool1 = false)
      {
        Assertions.checkArgument(bool1);
        i = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[i];
        break;
      }
    }
  }
  
  public static SpsData parseSpsNalUnit(ParsableBitArray paramParsableBitArray)
  {
    int i = paramParsableBitArray.readBits(8);
    paramParsableBitArray.skipBits(16);
    paramParsableBitArray.readUnsignedExpGolombCodedInt();
    int j = 1;
    int m;
    int k;
    if ((i == 100) || (i == 110) || (i == 122) || (i == 244) || (i == 44) || (i == 83) || (i == 86) || (i == 118) || (i == 128) || (i == 138))
    {
      m = paramParsableBitArray.readUnsignedExpGolombCodedInt();
      if (m == 3) {
        paramParsableBitArray.skipBits(1);
      }
      paramParsableBitArray.readUnsignedExpGolombCodedInt();
      paramParsableBitArray.readUnsignedExpGolombCodedInt();
      paramParsableBitArray.skipBits(1);
      j = m;
      if (paramParsableBitArray.readBit())
      {
        if (m != 3)
        {
          i = 8;
          k = 0;
          label139:
          j = m;
          if (k >= i) {
            break label195;
          }
          if (paramParsableBitArray.readBit()) {
            if (k >= 6) {
              break label188;
            }
          }
        }
        label188:
        for (j = 16;; j = 64)
        {
          skipScalingList(paramParsableBitArray, j);
          k += 1;
          break label139;
          i = 12;
          break;
        }
      }
    }
    label195:
    paramParsableBitArray.readUnsignedExpGolombCodedInt();
    long l = paramParsableBitArray.readUnsignedExpGolombCodedInt();
    boolean bool;
    label254:
    label344:
    float f2;
    float f1;
    if (l == 0L)
    {
      paramParsableBitArray.readUnsignedExpGolombCodedInt();
      paramParsableBitArray.readUnsignedExpGolombCodedInt();
      paramParsableBitArray.skipBits(1);
      m = paramParsableBitArray.readUnsignedExpGolombCodedInt();
      k = paramParsableBitArray.readUnsignedExpGolombCodedInt();
      bool = paramParsableBitArray.readBit();
      if (!bool) {
        break label513;
      }
      i = 1;
      if (!bool) {
        paramParsableBitArray.skipBits(1);
      }
      paramParsableBitArray.skipBits(1);
      m = (m + 1) * 16;
      int n = (2 - i) * (k + 1) * 16;
      k = n;
      i = m;
      if (paramParsableBitArray.readBit())
      {
        int i3 = paramParsableBitArray.readUnsignedExpGolombCodedInt();
        int i4 = paramParsableBitArray.readUnsignedExpGolombCodedInt();
        int i1 = paramParsableBitArray.readUnsignedExpGolombCodedInt();
        int i2 = paramParsableBitArray.readUnsignedExpGolombCodedInt();
        if (j != 0) {
          break label523;
        }
        j = 1;
        if (!bool) {
          break label518;
        }
        i = 1;
        i = 2 - i;
        j = m - (i3 + i4) * j;
        k = n - (i1 + i2) * i;
        i = j;
      }
      f2 = 1.0F;
      f1 = f2;
      if (paramParsableBitArray.readBit())
      {
        f1 = f2;
        if (paramParsableBitArray.readBit())
        {
          j = paramParsableBitArray.readBits(8);
          if (j != 255) {
            break label583;
          }
          j = paramParsableBitArray.readBits(16);
          m = paramParsableBitArray.readBits(16);
          f1 = f2;
          if (j != 0)
          {
            f1 = f2;
            if (m != 0) {
              f1 = j / m;
            }
          }
        }
      }
    }
    for (;;)
    {
      return new SpsData(i, k, f1);
      if (l != 1L) {
        break;
      }
      paramParsableBitArray.skipBits(1);
      paramParsableBitArray.readSignedExpGolombCodedInt();
      paramParsableBitArray.readSignedExpGolombCodedInt();
      l = paramParsableBitArray.readUnsignedExpGolombCodedInt();
      i = 0;
      while (i < l)
      {
        paramParsableBitArray.readUnsignedExpGolombCodedInt();
        i += 1;
      }
      break;
      label513:
      i = 0;
      break label254;
      label518:
      i = 0;
      break label344;
      label523:
      if (j == 3)
      {
        i = 1;
        label531:
        if (j != 1) {
          break label571;
        }
        j = 2;
        label540:
        if (!bool) {
          break label577;
        }
      }
      label571:
      label577:
      for (k = 1;; k = 0)
      {
        k = j * (2 - k);
        j = i;
        i = k;
        break;
        i = 2;
        break label531;
        j = 1;
        break label540;
      }
      label583:
      if (j < NalUnitUtil.ASPECT_RATIO_IDC_VALUES.length)
      {
        f1 = NalUnitUtil.ASPECT_RATIO_IDC_VALUES[j];
      }
      else
      {
        Log.w("CodecSpecificDataUtil", "Unexpected aspect_ratio_idc value: " + j);
        f1 = f2;
      }
    }
  }
  
  private static void skipScalingList(ParsableBitArray paramParsableBitArray, int paramInt)
  {
    int k = 8;
    int m = 8;
    int j = 0;
    if (j < paramInt)
    {
      int i = m;
      if (m != 0) {
        i = (k + paramParsableBitArray.readSignedExpGolombCodedInt() + 256) % 256;
      }
      if (i == 0) {}
      for (;;)
      {
        j += 1;
        m = i;
        break;
        k = i;
      }
    }
  }
  
  public static byte[][] splitNalUnits(byte[] paramArrayOfByte)
  {
    if (!isNalStartCode(paramArrayOfByte, 0)) {
      return (byte[][])null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j;
    do
    {
      localArrayList.add(Integer.valueOf(i));
      j = findNalStartCode(paramArrayOfByte, NAL_START_CODE.length + i);
      i = j;
    } while (j != -1);
    byte[][] arrayOfByte = new byte[localArrayList.size()][];
    i = 0;
    if (i < localArrayList.size())
    {
      int k = ((Integer)localArrayList.get(i)).intValue();
      if (i < localArrayList.size() - 1) {}
      for (j = ((Integer)localArrayList.get(i + 1)).intValue();; j = paramArrayOfByte.length)
      {
        byte[] arrayOfByte1 = new byte[j - k];
        System.arraycopy(paramArrayOfByte, k, arrayOfByte1, 0, arrayOfByte1.length);
        arrayOfByte[i] = arrayOfByte1;
        i += 1;
        break;
      }
    }
    return arrayOfByte;
  }
  
  public static final class SpsData
  {
    public final int height;
    public final float pixelWidthAspectRatio;
    public final int width;
    
    public SpsData(int paramInt1, int paramInt2, float paramFloat)
    {
      this.width = paramInt1;
      this.height = paramInt2;
      this.pixelWidthAspectRatio = paramFloat;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/util/CodecSpecificDataUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */