package com.google.android.exoplayer.extractor.mp4;

import android.util.Pair;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.extractor.GaplessInfo;
import com.google.android.exoplayer.util.Ac3Util;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.CodecSpecificDataUtil;
import com.google.android.exoplayer.util.CodecSpecificDataUtil.SpsData;
import com.google.android.exoplayer.util.NalUnitUtil;
import com.google.android.exoplayer.util.ParsableBitArray;
import com.google.android.exoplayer.util.ParsableByteArray;
import com.google.android.exoplayer.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class AtomParsers
{
  private static int findEsdsPosition(ParsableByteArray paramParsableByteArray, int paramInt1, int paramInt2)
  {
    int i = paramParsableByteArray.getPosition();
    while (i - paramInt1 < paramInt2)
    {
      paramParsableByteArray.setPosition(i);
      int j = paramParsableByteArray.readInt();
      if (j > 0) {}
      for (boolean bool = true;; bool = false)
      {
        Assertions.checkArgument(bool, "childAtomSize should be positive");
        if (paramParsableByteArray.readInt() != Atom.TYPE_esds) {
          break;
        }
        return i;
      }
      i += j;
    }
    return -1;
  }
  
  private static void parseAudioSampleEntry(ParsableByteArray paramParsableByteArray, int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, String paramString, boolean paramBoolean, StsdData paramStsdData, int paramInt5)
  {
    paramParsableByteArray.setPosition(paramInt2 + 8);
    int i = 0;
    int m;
    int i1;
    int k;
    Object localObject1;
    label94:
    Object localObject2;
    if (paramBoolean)
    {
      paramParsableByteArray.skipBytes(8);
      i = paramParsableByteArray.readUnsignedShort();
      paramParsableByteArray.skipBytes(6);
      m = paramParsableByteArray.readUnsignedShort();
      i1 = paramParsableByteArray.readUnsignedShort();
      paramParsableByteArray.skipBytes(4);
      k = paramParsableByteArray.readUnsignedFixedPoint1616();
      if (i > 0)
      {
        paramParsableByteArray.skipBytes(16);
        if (i == 2) {
          paramParsableByteArray.skipBytes(20);
        }
      }
      localObject1 = null;
      if (paramInt1 != Atom.TYPE_ac_3) {
        break label309;
      }
      localObject1 = "audio/ac3";
      localObject2 = null;
      i = paramParsableByteArray.getPosition();
      label103:
      if (i - paramInt2 >= paramInt3) {
        break label680;
      }
      paramParsableByteArray.setPosition(i);
      i2 = paramParsableByteArray.readInt();
      if (i2 <= 0) {
        break label400;
      }
      bool = true;
      label131:
      Assertions.checkArgument(bool, "childAtomSize should be positive");
      i3 = paramParsableByteArray.readInt();
      if ((paramInt1 != Atom.TYPE_mp4a) && (paramInt1 != Atom.TYPE_enca)) {
        break label499;
      }
      n = -1;
      if (i3 != Atom.TYPE_esds) {
        break label406;
      }
      j = i;
      label173:
      if (j == -1) {
        break label440;
      }
      localObject2 = parseEsdsFromParent(paramParsableByteArray, j);
      localObject1 = (String)((Pair)localObject2).first;
      localObject2 = (byte[])((Pair)localObject2).second;
      localObject3 = localObject1;
      n = m;
      j = k;
      localObject4 = localObject2;
      if ("audio/mp4a-latm".equals(localObject1))
      {
        localObject3 = CodecSpecificDataUtil.parseAacAudioSpecificConfig((byte[])localObject2);
        j = ((Integer)((Pair)localObject3).first).intValue();
        n = ((Integer)((Pair)localObject3).second).intValue();
        localObject4 = localObject2;
        localObject3 = localObject1;
      }
    }
    for (;;)
    {
      i += i2;
      localObject1 = localObject3;
      m = n;
      k = j;
      localObject2 = localObject4;
      break label103;
      paramParsableByteArray.skipBytes(16);
      break;
      label309:
      if (paramInt1 == Atom.TYPE_ec_3)
      {
        localObject1 = "audio/eac3";
        break label94;
      }
      if (paramInt1 == Atom.TYPE_dtsc)
      {
        localObject1 = "audio/vnd.dts";
        break label94;
      }
      if ((paramInt1 == Atom.TYPE_dtsh) || (paramInt1 == Atom.TYPE_dtsl))
      {
        localObject1 = "audio/vnd.dts.hd";
        break label94;
      }
      if (paramInt1 == Atom.TYPE_dtse)
      {
        localObject1 = "audio/vnd.dts.hd;profile=lbr";
        break label94;
      }
      if (paramInt1 == Atom.TYPE_samr)
      {
        localObject1 = "audio/3gpp";
        break label94;
      }
      if (paramInt1 != Atom.TYPE_sawb) {
        break label94;
      }
      localObject1 = "audio/amr-wb";
      break label94;
      label400:
      bool = false;
      break label131;
      label406:
      j = n;
      if (!paramBoolean) {
        break label173;
      }
      j = n;
      if (i3 != Atom.TYPE_wave) {
        break label173;
      }
      j = findEsdsPosition(paramParsableByteArray, i, i2);
      break label173;
      label440:
      localObject3 = localObject1;
      n = m;
      j = k;
      localObject4 = localObject2;
      if (i3 == Atom.TYPE_sinf)
      {
        paramStsdData.trackEncryptionBoxes[paramInt5] = parseSinfFromParent(paramParsableByteArray, i, i2);
        localObject3 = localObject1;
        n = m;
        j = k;
        localObject4 = localObject2;
      }
    }
    label499:
    if ((paramInt1 == Atom.TYPE_ac_3) && (i3 == Atom.TYPE_dac3))
    {
      paramParsableByteArray.setPosition(i + 8);
      paramStsdData.mediaFormat = Ac3Util.parseAc3AnnexFFormat(paramParsableByteArray, Integer.toString(paramInt4), paramLong, paramString);
    }
    label680:
    while (localObject1 == null)
    {
      int i3;
      do
      {
        do
        {
          int i2;
          boolean bool;
          return;
          if ((paramInt1 == Atom.TYPE_ec_3) && (i3 == Atom.TYPE_dec3))
          {
            paramParsableByteArray.setPosition(i + 8);
            paramStsdData.mediaFormat = Ac3Util.parseEAc3AnnexFFormat(paramParsableByteArray, Integer.toString(paramInt4), paramLong, paramString);
            return;
          }
          if ((paramInt1 == Atom.TYPE_dtsc) || (paramInt1 == Atom.TYPE_dtse) || (paramInt1 == Atom.TYPE_dtsh)) {
            break;
          }
          localObject3 = localObject1;
          n = m;
          j = k;
          localObject4 = localObject2;
        } while (paramInt1 != Atom.TYPE_dtsl);
        localObject3 = localObject1;
        int n = m;
        int j = k;
        Object localObject4 = localObject2;
      } while (i3 != Atom.TYPE_ddts);
      paramStsdData.mediaFormat = MediaFormat.createAudioFormat(Integer.toString(paramInt4), (String)localObject1, -1, -1, paramLong, m, k, null, paramString);
      return;
    }
    Object localObject3 = Integer.toString(paramInt4);
    if (localObject2 == null) {}
    for (paramParsableByteArray = null;; paramParsableByteArray = Collections.singletonList(localObject2))
    {
      paramStsdData.mediaFormat = MediaFormat.createAudioFormat((String)localObject3, (String)localObject1, -1, i1, paramLong, m, k, paramParsableByteArray, paramString);
      return;
    }
  }
  
  private static AvcCData parseAvcCFromParent(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    paramParsableByteArray.setPosition(paramInt + 8 + 4);
    int i = (paramParsableByteArray.readUnsignedByte() & 0x3) + 1;
    if (i == 3) {
      throw new IllegalStateException();
    }
    ArrayList localArrayList = new ArrayList();
    float f = 1.0F;
    int j = paramParsableByteArray.readUnsignedByte() & 0x1F;
    paramInt = 0;
    while (paramInt < j)
    {
      localArrayList.add(NalUnitUtil.parseChildNalUnit(paramParsableByteArray));
      paramInt += 1;
    }
    int k = paramParsableByteArray.readUnsignedByte();
    paramInt = 0;
    while (paramInt < k)
    {
      localArrayList.add(NalUnitUtil.parseChildNalUnit(paramParsableByteArray));
      paramInt += 1;
    }
    if (j > 0)
    {
      paramParsableByteArray = new ParsableBitArray((byte[])localArrayList.get(0));
      paramParsableByteArray.setPosition((i + 1) * 8);
      f = CodecSpecificDataUtil.parseSpsNalUnit(paramParsableByteArray).pixelWidthAspectRatio;
    }
    return new AvcCData(localArrayList, i, f);
  }
  
  private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom paramContainerAtom)
  {
    if (paramContainerAtom != null)
    {
      paramContainerAtom = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_elst);
      if (paramContainerAtom != null) {}
    }
    else
    {
      return Pair.create(null, null);
    }
    paramContainerAtom = paramContainerAtom.data;
    paramContainerAtom.setPosition(8);
    int j = Atom.parseFullAtomVersion(paramContainerAtom.readInt());
    int k = paramContainerAtom.readUnsignedIntToInt();
    long[] arrayOfLong1 = new long[k];
    long[] arrayOfLong2 = new long[k];
    int i = 0;
    while (i < k)
    {
      if (j == 1)
      {
        l = paramContainerAtom.readUnsignedLongToLong();
        arrayOfLong1[i] = l;
        if (j != 1) {
          break label125;
        }
      }
      label125:
      for (long l = paramContainerAtom.readLong();; l = paramContainerAtom.readInt())
      {
        arrayOfLong2[i] = l;
        if (paramContainerAtom.readShort() == 1) {
          break label135;
        }
        throw new IllegalArgumentException("Unsupported media rate.");
        l = paramContainerAtom.readUnsignedInt();
        break;
      }
      label135:
      paramContainerAtom.skipBytes(2);
      i += 1;
    }
    return Pair.create(arrayOfLong1, arrayOfLong2);
  }
  
  private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    paramParsableByteArray.setPosition(paramInt + 8 + 4);
    paramParsableByteArray.skipBytes(1);
    for (paramInt = paramParsableByteArray.readUnsignedByte(); paramInt > 127; paramInt = paramParsableByteArray.readUnsignedByte()) {}
    paramParsableByteArray.skipBytes(2);
    paramInt = paramParsableByteArray.readUnsignedByte();
    if ((paramInt & 0x80) != 0) {
      paramParsableByteArray.skipBytes(2);
    }
    if ((paramInt & 0x40) != 0) {
      paramParsableByteArray.skipBytes(paramParsableByteArray.readUnsignedShort());
    }
    if ((paramInt & 0x20) != 0) {
      paramParsableByteArray.skipBytes(2);
    }
    paramParsableByteArray.skipBytes(1);
    for (paramInt = paramParsableByteArray.readUnsignedByte(); paramInt > 127; paramInt = paramParsableByteArray.readUnsignedByte()) {}
    String str;
    switch (paramParsableByteArray.readUnsignedByte())
    {
    default: 
      str = null;
    case 107: 
    case 32: 
    case 33: 
    case 35: 
    case 64: 
    case 102: 
    case 103: 
    case 104: 
    case 165: 
    case 166: 
      for (;;)
      {
        paramParsableByteArray.skipBytes(12);
        paramParsableByteArray.skipBytes(1);
        int i = paramParsableByteArray.readUnsignedByte();
        for (paramInt = i & 0x7F; i > 127; paramInt = paramInt << 8 | i & 0x7F) {
          i = paramParsableByteArray.readUnsignedByte();
        }
        return Pair.create("audio/mpeg", null);
        str = "video/mp4v-es";
        continue;
        str = "video/avc";
        continue;
        str = "video/hevc";
        continue;
        str = "audio/mp4a-latm";
        continue;
        str = "audio/ac3";
        continue;
        str = "audio/eac3";
      }
    case 169: 
    case 172: 
      return Pair.create("audio/vnd.dts", null);
    }
    return Pair.create("audio/vnd.dts.hd", null);
    byte[] arrayOfByte = new byte[paramInt];
    paramParsableByteArray.readBytes(arrayOfByte, 0, paramInt);
    return Pair.create(str, arrayOfByte);
  }
  
  private static int parseHdlr(ParsableByteArray paramParsableByteArray)
  {
    paramParsableByteArray.setPosition(16);
    return paramParsableByteArray.readInt();
  }
  
  private static Pair<List<byte[]>, Integer> parseHvcCFromParent(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    paramParsableByteArray.setPosition(paramInt + 8 + 21);
    int m = paramParsableByteArray.readUnsignedByte();
    int n = paramParsableByteArray.readUnsignedByte();
    paramInt = 0;
    int k = paramParsableByteArray.getPosition();
    int i = 0;
    int i1;
    int j;
    int i2;
    while (i < n)
    {
      paramParsableByteArray.skipBytes(1);
      i1 = paramParsableByteArray.readUnsignedShort();
      j = 0;
      while (j < i1)
      {
        i2 = paramParsableByteArray.readUnsignedShort();
        paramInt += i2 + 4;
        paramParsableByteArray.skipBytes(i2);
        j += 1;
      }
      i += 1;
    }
    paramParsableByteArray.setPosition(k);
    byte[] arrayOfByte = new byte[paramInt];
    k = 0;
    i = 0;
    while (i < n)
    {
      paramParsableByteArray.skipBytes(1);
      i1 = paramParsableByteArray.readUnsignedShort();
      j = 0;
      while (j < i1)
      {
        i2 = paramParsableByteArray.readUnsignedShort();
        System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, arrayOfByte, k, NalUnitUtil.NAL_START_CODE.length);
        k += NalUnitUtil.NAL_START_CODE.length;
        System.arraycopy(paramParsableByteArray.data, paramParsableByteArray.getPosition(), arrayOfByte, k, i2);
        k += i2;
        paramParsableByteArray.skipBytes(i2);
        j += 1;
      }
      i += 1;
    }
    if (paramInt == 0) {}
    for (paramParsableByteArray = null;; paramParsableByteArray = Collections.singletonList(arrayOfByte)) {
      return Pair.create(paramParsableByteArray, Integer.valueOf((m & 0x3) + 1));
    }
  }
  
  private static GaplessInfo parseIlst(ParsableByteArray paramParsableByteArray)
  {
    while (paramParsableByteArray.bytesLeft() > 0)
    {
      int i = paramParsableByteArray.getPosition() + paramParsableByteArray.readInt();
      if (paramParsableByteArray.readInt() == Atom.TYPE_DASHES)
      {
        String str2 = null;
        String str1 = null;
        String str3 = null;
        while (paramParsableByteArray.getPosition() < i)
        {
          int j = paramParsableByteArray.readInt() - 12;
          int k = paramParsableByteArray.readInt();
          paramParsableByteArray.skipBytes(4);
          if (k == Atom.TYPE_mean)
          {
            str2 = paramParsableByteArray.readString(j);
          }
          else if (k == Atom.TYPE_name)
          {
            str1 = paramParsableByteArray.readString(j);
          }
          else if (k == Atom.TYPE_data)
          {
            paramParsableByteArray.skipBytes(4);
            str3 = paramParsableByteArray.readString(j - 4);
          }
          else
          {
            paramParsableByteArray.skipBytes(j);
          }
        }
        if ((str1 != null) && (str3 != null) && ("com.apple.iTunes".equals(str2))) {
          return GaplessInfo.createFromComment(str1, str3);
        }
      }
      else
      {
        paramParsableByteArray.setPosition(i);
      }
    }
    return null;
  }
  
  private static Pair<Long, String> parseMdhd(ParsableByteArray paramParsableByteArray)
  {
    int j = 8;
    paramParsableByteArray.setPosition(8);
    int k = Atom.parseFullAtomVersion(paramParsableByteArray.readInt());
    if (k == 0) {}
    for (int i = 8;; i = 16)
    {
      paramParsableByteArray.skipBytes(i);
      long l = paramParsableByteArray.readUnsignedInt();
      i = j;
      if (k == 0) {
        i = 4;
      }
      paramParsableByteArray.skipBytes(i);
      i = paramParsableByteArray.readUnsignedShort();
      return Pair.create(Long.valueOf(l), "" + (char)((i >> 10 & 0x1F) + 96) + (char)((i >> 5 & 0x1F) + 96) + (char)((i & 0x1F) + 96));
    }
  }
  
  private static long parseMvhd(ParsableByteArray paramParsableByteArray)
  {
    int i = 8;
    paramParsableByteArray.setPosition(8);
    if (Atom.parseFullAtomVersion(paramParsableByteArray.readInt()) == 0) {}
    for (;;)
    {
      paramParsableByteArray.skipBytes(i);
      return paramParsableByteArray.readUnsignedInt();
      i = 16;
    }
  }
  
  private static float parsePaspFromParent(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    paramParsableByteArray.setPosition(paramInt + 8);
    paramInt = paramParsableByteArray.readUnsignedIntToInt();
    int i = paramParsableByteArray.readUnsignedIntToInt();
    return paramInt / i;
  }
  
  private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray paramParsableByteArray, int paramInt1, int paramInt2)
  {
    boolean bool = true;
    int i = paramInt1 + 8;
    while (i - paramInt1 < paramInt2)
    {
      paramParsableByteArray.setPosition(i);
      int j = paramParsableByteArray.readInt();
      if (paramParsableByteArray.readInt() == Atom.TYPE_tenc)
      {
        paramParsableByteArray.skipBytes(4);
        paramInt1 = paramParsableByteArray.readInt();
        if (paramInt1 >> 8 == 1) {}
        for (;;)
        {
          byte[] arrayOfByte = new byte[16];
          paramParsableByteArray.readBytes(arrayOfByte, 0, arrayOfByte.length);
          return new TrackEncryptionBox(bool, paramInt1 & 0xFF, arrayOfByte);
          bool = false;
        }
      }
      i += j;
    }
    return null;
  }
  
  private static TrackEncryptionBox parseSinfFromParent(ParsableByteArray paramParsableByteArray, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + 8;
    TrackEncryptionBox localTrackEncryptionBox = null;
    if (i - paramInt1 < paramInt2)
    {
      paramParsableByteArray.setPosition(i);
      int j = paramParsableByteArray.readInt();
      int k = paramParsableByteArray.readInt();
      if (k == Atom.TYPE_frma) {
        paramParsableByteArray.readInt();
      }
      for (;;)
      {
        i += j;
        break;
        if (k == Atom.TYPE_schm)
        {
          paramParsableByteArray.skipBytes(4);
          paramParsableByteArray.readInt();
          paramParsableByteArray.readInt();
        }
        else if (k == Atom.TYPE_schi)
        {
          localTrackEncryptionBox = parseSchiFromParent(paramParsableByteArray, i, j);
        }
      }
    }
    return localTrackEncryptionBox;
  }
  
  public static TrackSampleTable parseStbl(Track paramTrack, Atom.ContainerAtom paramContainerAtom)
  {
    Object localObject3 = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_stsz).data;
    Object localObject2 = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_stco);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_co64);
    }
    ParsableByteArray localParsableByteArray1 = ((Atom.LeafAtom)localObject1).data;
    ParsableByteArray localParsableByteArray2 = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_stsc).data;
    ParsableByteArray localParsableByteArray3 = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_stts).data;
    localObject2 = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_stss);
    if (localObject2 != null)
    {
      localObject2 = ((Atom.LeafAtom)localObject2).data;
      paramContainerAtom = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_ctts);
      if (paramContainerAtom == null) {
        break label184;
      }
    }
    int i19;
    int i20;
    long[] arrayOfLong1;
    int[] arrayOfInt1;
    int i5;
    long[] arrayOfLong2;
    int[] arrayOfInt2;
    label184:
    for (paramContainerAtom = paramContainerAtom.data;; paramContainerAtom = null)
    {
      ((ParsableByteArray)localObject3).setPosition(12);
      i19 = ((ParsableByteArray)localObject3).readUnsignedIntToInt();
      i20 = ((ParsableByteArray)localObject3).readUnsignedIntToInt();
      arrayOfLong1 = new long[i20];
      arrayOfInt1 = new int[i20];
      i5 = 0;
      arrayOfLong2 = new long[i20];
      arrayOfInt2 = new int[i20];
      if (i20 != 0) {
        break label189;
      }
      return new TrackSampleTable(arrayOfLong1, arrayOfInt1, 0, arrayOfLong2, arrayOfInt2);
      localObject2 = null;
      break;
    }
    label189:
    localParsableByteArray1.setPosition(12);
    int i21 = localParsableByteArray1.readUnsignedIntToInt();
    localParsableByteArray2.setPosition(12);
    int i4 = localParsableByteArray2.readUnsignedIntToInt() - 1;
    int i2;
    int i6;
    int i11;
    int i16;
    int i13;
    int j;
    int i1;
    long l1;
    label393:
    long l2;
    int i7;
    int i3;
    int i10;
    int i17;
    label427:
    label453:
    label497:
    int i9;
    long l3;
    int i14;
    int i12;
    int i15;
    label728:
    int i18;
    if (localParsableByteArray2.readInt() == 1)
    {
      bool = true;
      Assertions.checkState(bool, "stsc first chunk must be 1");
      i2 = localParsableByteArray2.readUnsignedIntToInt();
      localParsableByteArray2.skipBytes(4);
      m = -1;
      if (i4 > 0) {
        m = localParsableByteArray2.readUnsignedIntToInt() - 1;
      }
      int i8 = 0;
      i6 = i2;
      localParsableByteArray3.setPosition(12);
      i11 = localParsableByteArray3.readUnsignedIntToInt() - 1;
      i16 = localParsableByteArray3.readUnsignedIntToInt();
      i13 = localParsableByteArray3.readUnsignedIntToInt();
      j = 0;
      k = 0;
      i = 0;
      if (paramContainerAtom != null)
      {
        paramContainerAtom.setPosition(12);
        k = paramContainerAtom.readUnsignedIntToInt() - 1;
        j = paramContainerAtom.readUnsignedIntToInt();
        i = paramContainerAtom.readInt();
      }
      n = -1;
      i1 = 0;
      if (localObject2 != null)
      {
        ((ParsableByteArray)localObject2).setPosition(12);
        i1 = ((ParsableByteArray)localObject2).readUnsignedIntToInt();
        n = ((ParsableByteArray)localObject2).readUnsignedIntToInt() - 1;
      }
      if (((Atom.LeafAtom)localObject1).type != Atom.TYPE_stco) {
        break label902;
      }
      l1 = localParsableByteArray1.readUnsignedInt();
      l2 = 0L;
      i7 = 0;
      i3 = i;
      i = i2;
      i2 = k;
      k = i4;
      i10 = j;
      i17 = n;
      i4 = i7;
      j = i5;
      if (i4 >= i20) {
        break label951;
      }
      arrayOfLong1[i4] = l1;
      if (i19 != 0) {
        break label912;
      }
      n = ((ParsableByteArray)localObject3).readUnsignedIntToInt();
      arrayOfInt1[i4] = n;
      i5 = j;
      if (arrayOfInt1[i4] > j) {
        i5 = arrayOfInt1[i4];
      }
      arrayOfLong2[i4] = (i3 + l2);
      if (localObject2 != null) {
        break label919;
      }
      j = 1;
      arrayOfInt2[i4] = j;
      i9 = i17;
      i7 = i1;
      if (i4 == i17)
      {
        arrayOfInt2[i4] = 1;
        j = i1 - 1;
        i9 = i17;
        i7 = j;
        if (j > 0)
        {
          i9 = ((ParsableByteArray)localObject2).readUnsignedIntToInt() - 1;
          i7 = j;
        }
      }
      l3 = l2 + i13;
      j = i16 - 1;
      n = j;
      i14 = i11;
      i12 = i13;
      if (j == 0)
      {
        n = j;
        i14 = i11;
        i12 = i13;
        if (i11 > 0)
        {
          n = localParsableByteArray3.readUnsignedIntToInt();
          i12 = localParsableByteArray3.readUnsignedIntToInt();
          i14 = i11 - 1;
        }
      }
      i1 = i10;
      i13 = i2;
      i15 = i3;
      if (paramContainerAtom != null)
      {
        j = i10 - 1;
        i1 = j;
        i13 = i2;
        i15 = i3;
        if (j == 0)
        {
          i1 = j;
          i13 = i2;
          i15 = i3;
          if (i2 > 0)
          {
            i1 = paramContainerAtom.readUnsignedIntToInt();
            i15 = paramContainerAtom.readInt();
            i13 = i2 - 1;
          }
        }
      }
      i2 = i6 - 1;
      if (i2 != 0) {
        break label934;
      }
      i6 = i8 + 1;
      if (i6 < i21)
      {
        if (((Atom.LeafAtom)localObject1).type != Atom.TYPE_stco) {
          break label924;
        }
        l1 = localParsableByteArray1.readUnsignedInt();
      }
      i3 = m;
      j = k;
      if (i6 == m)
      {
        i8 = localParsableByteArray2.readUnsignedIntToInt();
        localParsableByteArray2.skipBytes(4);
        k -= 1;
        i3 = m;
        j = k;
        i = i8;
        if (k > 0)
        {
          i3 = localParsableByteArray2.readUnsignedIntToInt() - 1;
          i = i8;
          j = k;
        }
      }
      i8 = i6;
      m = i3;
      l2 = l1;
      k = j;
      i18 = i;
      if (i6 < i21)
      {
        i2 = i;
        i18 = i;
        k = j;
        l2 = l1;
        m = i3;
        i8 = i6;
      }
    }
    for (;;)
    {
      i4 += 1;
      j = i5;
      i17 = i9;
      i16 = n;
      l1 = l2;
      i10 = i1;
      i6 = i2;
      i1 = i7;
      i11 = i14;
      i2 = i13;
      i = i18;
      i13 = i12;
      i3 = i15;
      l2 = l3;
      break label427;
      bool = false;
      break;
      label902:
      l1 = localParsableByteArray1.readUnsignedLongToLong();
      break label393;
      label912:
      n = i19;
      break label453;
      label919:
      j = 0;
      break label497;
      label924:
      l1 = localParsableByteArray1.readUnsignedLongToLong();
      break label728;
      label934:
      l2 = l1 + arrayOfInt1[i4];
      i18 = i;
    }
    label951:
    if (i1 == 0)
    {
      bool = true;
      Assertions.checkArgument(bool);
      if (i16 != 0) {
        break label1058;
      }
      bool = true;
      label972:
      Assertions.checkArgument(bool);
      if (i6 != 0) {
        break label1064;
      }
      bool = true;
      label985:
      Assertions.checkArgument(bool);
      if (i11 != 0) {
        break label1070;
      }
      bool = true;
      label998:
      Assertions.checkArgument(bool);
      if (i2 != 0) {
        break label1076;
      }
    }
    label1058:
    label1064:
    label1070:
    label1076:
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkArgument(bool);
      if (paramTrack.editListDurations != null) {
        break label1082;
      }
      Util.scaleLargeTimestampsInPlace(arrayOfLong2, 1000000L, paramTrack.timescale);
      return new TrackSampleTable(arrayOfLong1, arrayOfInt1, j, arrayOfLong2, arrayOfInt2);
      bool = false;
      break;
      bool = false;
      break label972;
      bool = false;
      break label985;
      bool = false;
      break label998;
    }
    label1082:
    if ((paramTrack.editListDurations.length == 1) && (paramTrack.editListDurations[0] == 0L))
    {
      i = 0;
      while (i < arrayOfLong2.length)
      {
        arrayOfLong2[i] = Util.scaleLargeTimestamp(arrayOfLong2[i] - paramTrack.editListMediaTimes[0], 1000000L, paramTrack.timescale);
        i += 1;
      }
      return new TrackSampleTable(arrayOfLong1, arrayOfInt1, j, arrayOfLong2, arrayOfInt2);
    }
    int k = 0;
    int n = 0;
    int i = 0;
    int m = 0;
    if (m < paramTrack.editListDurations.length)
    {
      l1 = paramTrack.editListMediaTimes[m];
      i3 = i;
      i2 = k;
      i1 = n;
      if (l1 != -1L)
      {
        l2 = Util.scaleLargeTimestamp(paramTrack.editListDurations[m], paramTrack.timescale, paramTrack.movieTimescale);
        i3 = Util.binarySearchCeil(arrayOfLong2, l1, true, true);
        i1 = Util.binarySearchCeil(arrayOfLong2, l1 + l2, true, false);
        i2 = k + (i1 - i3);
        if (n == i3) {
          break label1301;
        }
      }
      label1301:
      for (k = 1;; k = 0)
      {
        i3 = i | k;
        m += 1;
        i = i3;
        k = i2;
        n = i1;
        break;
      }
    }
    if (k != i20)
    {
      m = 1;
      i2 = i | m;
      if (i2 == 0) {
        break label1602;
      }
      paramContainerAtom = new long[k];
      label1333:
      if (i2 == 0) {
        break label1608;
      }
      localObject1 = new int[k];
      label1344:
      if (i2 == 0) {
        break label1615;
      }
      i = 0;
      label1351:
      if (i2 == 0) {
        break label1620;
      }
      localObject2 = new int[k];
      label1362:
      localObject3 = new long[k];
      l1 = 0L;
      j = 0;
      k = 0;
    }
    for (;;)
    {
      if (k >= paramTrack.editListDurations.length) {
        break label1649;
      }
      l2 = paramTrack.editListMediaTimes[k];
      l3 = paramTrack.editListDurations[k];
      n = i;
      i1 = j;
      if (l2 != -1L)
      {
        long l4 = Util.scaleLargeTimestamp(l3, paramTrack.timescale, paramTrack.movieTimescale);
        m = Util.binarySearchCeil(arrayOfLong2, l2, true, true);
        i3 = Util.binarySearchCeil(arrayOfLong2, l2 + l4, true, false);
        if (i2 != 0)
        {
          n = i3 - m;
          System.arraycopy(arrayOfLong1, m, paramContainerAtom, j, n);
          System.arraycopy(arrayOfInt1, m, localObject1, j, n);
          System.arraycopy(arrayOfInt2, m, localObject2, j, n);
        }
        for (;;)
        {
          n = i;
          i1 = j;
          if (m >= i3) {
            break;
          }
          localObject3[j] = (Util.scaleLargeTimestamp(l1, 1000000L, paramTrack.movieTimescale) + Util.scaleLargeTimestamp(arrayOfLong2[m] - l2, 1000000L, paramTrack.timescale));
          n = i;
          if (i2 != 0)
          {
            n = i;
            if (localObject1[j] > i) {
              n = arrayOfInt1[m];
            }
          }
          j += 1;
          m += 1;
          i = n;
        }
        m = 0;
        break;
        label1602:
        paramContainerAtom = arrayOfLong1;
        break label1333;
        label1608:
        localObject1 = arrayOfInt1;
        break label1344;
        label1615:
        i = j;
        break label1351;
        label1620:
        localObject2 = arrayOfInt2;
        break label1362;
      }
      l1 += l3;
      k += 1;
      i = n;
      j = i1;
    }
    label1649:
    return new TrackSampleTable(paramContainerAtom, (int[])localObject1, i, (long[])localObject3, (int[])localObject2);
  }
  
  private static StsdData parseStsd(ParsableByteArray paramParsableByteArray, int paramInt1, long paramLong, int paramInt2, String paramString, boolean paramBoolean)
  {
    paramParsableByteArray.setPosition(12);
    int j = paramParsableByteArray.readInt();
    StsdData localStsdData = new StsdData(j);
    int i = 0;
    if (i < j)
    {
      int k = paramParsableByteArray.getPosition();
      int m = paramParsableByteArray.readInt();
      boolean bool;
      label53:
      int n;
      if (m > 0)
      {
        bool = true;
        Assertions.checkArgument(bool, "childAtomSize should be positive");
        n = paramParsableByteArray.readInt();
        if ((n != Atom.TYPE_avc1) && (n != Atom.TYPE_avc3) && (n != Atom.TYPE_encv) && (n != Atom.TYPE_mp4v) && (n != Atom.TYPE_hvc1) && (n != Atom.TYPE_hev1) && (n != Atom.TYPE_s263)) {
          break label162;
        }
        parseVideoSampleEntry(paramParsableByteArray, k, m, paramInt1, paramLong, paramInt2, localStsdData, i);
      }
      for (;;)
      {
        paramParsableByteArray.setPosition(k + m);
        i += 1;
        break;
        bool = false;
        break label53;
        label162:
        if ((n == Atom.TYPE_mp4a) || (n == Atom.TYPE_enca) || (n == Atom.TYPE_ac_3) || (n == Atom.TYPE_ec_3) || (n == Atom.TYPE_dtsc) || (n == Atom.TYPE_dtse) || (n == Atom.TYPE_dtsh) || (n == Atom.TYPE_dtsl) || (n == Atom.TYPE_samr) || (n == Atom.TYPE_sawb)) {
          parseAudioSampleEntry(paramParsableByteArray, n, k, m, paramInt1, paramLong, paramString, paramBoolean, localStsdData, i);
        } else if (n == Atom.TYPE_TTML) {
          localStsdData.mediaFormat = MediaFormat.createTextFormat(Integer.toString(paramInt1), "application/ttml+xml", -1, paramLong, paramString);
        } else if (n == Atom.TYPE_tx3g) {
          localStsdData.mediaFormat = MediaFormat.createTextFormat(Integer.toString(paramInt1), "application/x-quicktime-tx3g", -1, paramLong, paramString);
        } else if (n == Atom.TYPE_wvtt) {
          localStsdData.mediaFormat = MediaFormat.createTextFormat(Integer.toString(paramInt1), "application/x-mp4vtt", -1, paramLong, paramString);
        } else if (n == Atom.TYPE_stpp) {
          localStsdData.mediaFormat = MediaFormat.createTextFormat(Integer.toString(paramInt1), "application/ttml+xml", -1, paramLong, paramString, 0L);
        }
      }
    }
    return localStsdData;
  }
  
  private static TkhdData parseTkhd(ParsableByteArray paramParsableByteArray)
  {
    paramParsableByteArray.setPosition(8);
    int i1 = Atom.parseFullAtomVersion(paramParsableByteArray.readInt());
    int i;
    int n;
    int m;
    label55:
    int j;
    label57:
    int k;
    long l;
    if (i1 == 0)
    {
      i = 8;
      paramParsableByteArray.skipBytes(i);
      n = paramParsableByteArray.readInt();
      paramParsableByteArray.skipBytes(4);
      m = 1;
      int i2 = paramParsableByteArray.getPosition();
      if (i1 != 0) {
        break label172;
      }
      i = 4;
      j = 0;
      k = m;
      if (j < i)
      {
        if (paramParsableByteArray.data[(i2 + j)] == -1) {
          break label178;
        }
        k = 0;
      }
      if (k == 0) {
        break label185;
      }
      paramParsableByteArray.skipBytes(i);
      l = -1L;
      paramParsableByteArray.skipBytes(16);
      i = paramParsableByteArray.readInt();
      j = paramParsableByteArray.readInt();
      paramParsableByteArray.skipBytes(4);
      k = paramParsableByteArray.readInt();
      m = paramParsableByteArray.readInt();
      if ((i != 0) || (j != 65536) || (k != -65536) || (m != 0)) {
        break label208;
      }
      i = 90;
    }
    for (;;)
    {
      return new TkhdData(n, l, i);
      i = 16;
      break;
      label172:
      i = 8;
      break label55;
      label178:
      j += 1;
      break label57;
      label185:
      if (i1 == 0) {}
      for (l = paramParsableByteArray.readUnsignedInt();; l = paramParsableByteArray.readUnsignedLongToLong()) {
        break;
      }
      label208:
      if ((i == 0) && (j == -65536) && (k == 65536) && (m == 0)) {
        i = 270;
      } else if ((i == -65536) && (j == 0) && (k == 0) && (m == -65536)) {
        i = 180;
      } else {
        i = 0;
      }
    }
  }
  
  public static Track parseTrak(Atom.ContainerAtom paramContainerAtom, Atom.LeafAtom paramLeafAtom, boolean paramBoolean)
  {
    Object localObject = paramContainerAtom.getContainerAtomOfType(Atom.TYPE_mdia);
    int i = parseHdlr(((Atom.ContainerAtom)localObject).getLeafAtomOfType(Atom.TYPE_hdlr).data);
    if ((i != Track.TYPE_soun) && (i != Track.TYPE_vide) && (i != Track.TYPE_text) && (i != Track.TYPE_sbtl) && (i != Track.TYPE_subt)) {
      return null;
    }
    TkhdData localTkhdData = parseTkhd(paramContainerAtom.getLeafAtomOfType(Atom.TYPE_tkhd).data);
    long l1 = localTkhdData.duration;
    long l2 = parseMvhd(paramLeafAtom.data);
    if (l1 == -1L) {}
    for (l1 = -1L;; l1 = Util.scaleLargeTimestamp(l1, 1000000L, l2))
    {
      Atom.ContainerAtom localContainerAtom = ((Atom.ContainerAtom)localObject).getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
      paramLeafAtom = parseMdhd(((Atom.ContainerAtom)localObject).getLeafAtomOfType(Atom.TYPE_mdhd).data);
      localObject = parseStsd(localContainerAtom.getLeafAtomOfType(Atom.TYPE_stsd).data, localTkhdData.id, l1, localTkhdData.rotationDegrees, (String)paramLeafAtom.second, paramBoolean);
      paramContainerAtom = parseEdts(paramContainerAtom.getContainerAtomOfType(Atom.TYPE_edts));
      if (((StsdData)localObject).mediaFormat != null) {
        break;
      }
      return null;
    }
    return new Track(localTkhdData.id, i, ((Long)paramLeafAtom.first).longValue(), l2, l1, ((StsdData)localObject).mediaFormat, ((StsdData)localObject).trackEncryptionBoxes, ((StsdData)localObject).nalUnitLengthFieldLength, (long[])paramContainerAtom.first, (long[])paramContainerAtom.second);
  }
  
  public static GaplessInfo parseUdta(Atom.ContainerAtom paramContainerAtom)
  {
    paramContainerAtom = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_meta);
    if (paramContainerAtom == null)
    {
      paramContainerAtom = null;
      return paramContainerAtom;
    }
    ParsableByteArray localParsableByteArray1 = paramContainerAtom.data;
    localParsableByteArray1.setPosition(12);
    ParsableByteArray localParsableByteArray2 = new ParsableByteArray();
    for (;;)
    {
      if (localParsableByteArray1.bytesLeft() <= 0) {
        break label105;
      }
      int i = localParsableByteArray1.readInt() - 8;
      if (localParsableByteArray1.readInt() == Atom.TYPE_ilst)
      {
        localParsableByteArray2.reset(localParsableByteArray1.data, localParsableByteArray1.getPosition() + i);
        localParsableByteArray2.setPosition(localParsableByteArray1.getPosition());
        GaplessInfo localGaplessInfo = parseIlst(localParsableByteArray2);
        paramContainerAtom = localGaplessInfo;
        if (localGaplessInfo != null) {
          break;
        }
      }
      localParsableByteArray1.skipBytes(i);
    }
    label105:
    return null;
  }
  
  private static void parseVideoSampleEntry(ParsableByteArray paramParsableByteArray, int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4, StsdData paramStsdData, int paramInt5)
  {
    paramParsableByteArray.setPosition(paramInt1 + 8);
    paramParsableByteArray.skipBytes(24);
    int m = paramParsableByteArray.readUnsignedShort();
    int n = paramParsableByteArray.readUnsignedShort();
    int j = 0;
    float f = 1.0F;
    paramParsableByteArray.skipBytes(50);
    Object localObject3 = null;
    int i = paramParsableByteArray.getPosition();
    Object localObject4 = null;
    int i2;
    int i1;
    if (i - paramInt1 < paramInt2)
    {
      paramParsableByteArray.setPosition(i);
      i2 = paramParsableByteArray.getPosition();
      i1 = paramParsableByteArray.readInt();
      if ((i1 != 0) || (paramParsableByteArray.getPosition() - paramInt1 != paramInt2)) {}
    }
    else
    {
      if (localObject4 != null) {
        break label498;
      }
      return;
    }
    boolean bool;
    label105:
    int i3;
    label134:
    Object localObject1;
    Object localObject2;
    int k;
    if (i1 > 0)
    {
      bool = true;
      Assertions.checkArgument(bool, "childAtomSize should be positive");
      i3 = paramParsableByteArray.readInt();
      if (i3 != Atom.TYPE_avcC) {
        break label239;
      }
      if (localObject4 != null) {
        break label233;
      }
      bool = true;
      Assertions.checkState(bool);
      localObject3 = "video/avc";
      AvcCData localAvcCData = parseAvcCFromParent(paramParsableByteArray, i2);
      localObject4 = localAvcCData.initializationData;
      paramStsdData.nalUnitLengthFieldLength = localAvcCData.nalUnitLengthFieldLength;
      localObject1 = localObject3;
      localObject2 = localObject4;
      k = j;
      if (j == 0)
      {
        f = localAvcCData.pixelWidthAspectRatio;
        k = j;
        localObject2 = localObject4;
        localObject1 = localObject3;
      }
    }
    for (;;)
    {
      i += i1;
      localObject4 = localObject1;
      localObject3 = localObject2;
      j = k;
      break;
      bool = false;
      break label105;
      label233:
      bool = false;
      break label134;
      label239:
      if (i3 == Atom.TYPE_hvcC)
      {
        if (localObject4 == null) {}
        for (bool = true;; bool = false)
        {
          Assertions.checkState(bool);
          localObject1 = "video/hevc";
          localObject3 = parseHvcCFromParent(paramParsableByteArray, i2);
          localObject2 = (List)((Pair)localObject3).first;
          paramStsdData.nalUnitLengthFieldLength = ((Integer)((Pair)localObject3).second).intValue();
          k = j;
          break;
        }
      }
      if (i3 == Atom.TYPE_d263)
      {
        if (localObject4 == null) {}
        for (bool = true;; bool = false)
        {
          Assertions.checkState(bool);
          localObject1 = "video/3gpp";
          localObject2 = localObject3;
          k = j;
          break;
        }
      }
      if (i3 == Atom.TYPE_esds)
      {
        if (localObject4 == null) {}
        for (bool = true;; bool = false)
        {
          Assertions.checkState(bool);
          localObject2 = parseEsdsFromParent(paramParsableByteArray, i2);
          localObject1 = (String)((Pair)localObject2).first;
          localObject2 = Collections.singletonList(((Pair)localObject2).second);
          k = j;
          break;
        }
      }
      if (i3 == Atom.TYPE_sinf)
      {
        paramStsdData.trackEncryptionBoxes[paramInt5] = parseSinfFromParent(paramParsableByteArray, i2, i1);
        localObject1 = localObject4;
        localObject2 = localObject3;
        k = j;
      }
      else
      {
        localObject1 = localObject4;
        localObject2 = localObject3;
        k = j;
        if (i3 == Atom.TYPE_pasp)
        {
          f = parsePaspFromParent(paramParsableByteArray, i2);
          k = 1;
          localObject1 = localObject4;
          localObject2 = localObject3;
        }
      }
    }
    label498:
    paramStsdData.mediaFormat = MediaFormat.createVideoFormat(Integer.toString(paramInt3), (String)localObject4, -1, -1, paramLong, m, n, (List)localObject3, paramInt4, f);
  }
  
  private static final class AvcCData
  {
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthAspectRatio;
    
    public AvcCData(List<byte[]> paramList, int paramInt, float paramFloat)
    {
      this.initializationData = paramList;
      this.nalUnitLengthFieldLength = paramInt;
      this.pixelWidthAspectRatio = paramFloat;
    }
  }
  
  private static final class StsdData
  {
    public MediaFormat mediaFormat;
    public int nalUnitLengthFieldLength;
    public final TrackEncryptionBox[] trackEncryptionBoxes;
    
    public StsdData(int paramInt)
    {
      this.trackEncryptionBoxes = new TrackEncryptionBox[paramInt];
      this.nalUnitLengthFieldLength = -1;
    }
  }
  
  private static final class TkhdData
  {
    private final long duration;
    private final int id;
    private final int rotationDegrees;
    
    public TkhdData(int paramInt1, long paramLong, int paramInt2)
    {
      this.id = paramInt1;
      this.duration = paramLong;
      this.rotationDegrees = paramInt2;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp4/AtomParsers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */