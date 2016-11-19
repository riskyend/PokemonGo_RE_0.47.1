package com.google.android.exoplayer.extractor.mp4;

import android.util.Log;
import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.drm.DrmInitData.Mapped;
import com.google.android.exoplayer.drm.DrmInitData.SchemeInitData;
import com.google.android.exoplayer.extractor.ChunkIndex;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.PositionHolder;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.util.NalUnitUtil;
import com.google.android.exoplayer.util.ParsableByteArray;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public final class FragmentedMp4Extractor
  implements Extractor
{
  private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = { -94, 57, 79, 82, 90, -101, 79, 20, -94, 68, 108, 66, 124, 100, -115, -12 };
  private static final int STATE_READING_ATOM_HEADER = 0;
  private static final int STATE_READING_ATOM_PAYLOAD = 1;
  private static final int STATE_READING_ENCRYPTION_DATA = 2;
  private static final int STATE_READING_SAMPLE_CONTINUE = 4;
  private static final int STATE_READING_SAMPLE_START = 3;
  private static final String TAG = "FragmentedMp4Extractor";
  public static final int WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
  public static final int WORKAROUND_IGNORE_TFDT_BOX = 2;
  private ParsableByteArray atomData;
  private final ParsableByteArray atomHeader;
  private int atomHeaderBytesRead;
  private long atomSize;
  private int atomType;
  private final Stack<Atom.ContainerAtom> containerAtoms;
  private final ParsableByteArray encryptionSignalByte;
  private long endOfMdatPosition;
  private final byte[] extendedTypeScratch;
  private DefaultSampleValues extendsDefaults;
  private ExtractorOutput extractorOutput;
  private final TrackFragment fragmentRun;
  private boolean haveOutputSeekMap;
  private final ParsableByteArray nalLength;
  private final ParsableByteArray nalStartCode;
  private int parserState;
  private int sampleBytesWritten;
  private int sampleCurrentNalBytesRemaining;
  private int sampleIndex;
  private int sampleSize;
  private Track track;
  private TrackOutput trackOutput;
  private final int workaroundFlags;
  
  public FragmentedMp4Extractor()
  {
    this(0);
  }
  
  public FragmentedMp4Extractor(int paramInt)
  {
    this.workaroundFlags = paramInt;
    this.atomHeader = new ParsableByteArray(16);
    this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    this.nalLength = new ParsableByteArray(4);
    this.encryptionSignalByte = new ParsableByteArray(1);
    this.extendedTypeScratch = new byte[16];
    this.containerAtoms = new Stack();
    this.fragmentRun = new TrackFragment();
    enterReadingAtomHeaderState();
  }
  
  private int appendSampleEncryptionData(ParsableByteArray paramParsableByteArray)
  {
    int i = this.fragmentRun.header.sampleDescriptionIndex;
    int j = this.track.sampleDescriptionEncryptionBoxes[i].initializationVectorSize;
    int k = this.fragmentRun.sampleHasSubsampleEncryptionTable[this.sampleIndex];
    byte[] arrayOfByte = this.encryptionSignalByte.data;
    if (k != 0) {}
    for (i = 128;; i = 0)
    {
      arrayOfByte[0] = ((byte)(i | j));
      this.encryptionSignalByte.setPosition(0);
      this.trackOutput.sampleData(this.encryptionSignalByte, 1);
      this.trackOutput.sampleData(paramParsableByteArray, j);
      if (k != 0) {
        break;
      }
      return j + 1;
    }
    i = paramParsableByteArray.readUnsignedShort();
    paramParsableByteArray.skipBytes(-2);
    i = i * 6 + 2;
    this.trackOutput.sampleData(paramParsableByteArray, i);
    return j + 1 + i;
  }
  
  private void enterReadingAtomHeaderState()
  {
    this.parserState = 0;
    this.atomHeaderBytesRead = 0;
  }
  
  private void onContainerAtomRead(Atom.ContainerAtom paramContainerAtom)
    throws ParserException
  {
    if (paramContainerAtom.type == Atom.TYPE_moov) {
      onMoovContainerAtomRead(paramContainerAtom);
    }
    do
    {
      return;
      if (paramContainerAtom.type == Atom.TYPE_moof)
      {
        onMoofContainerAtomRead(paramContainerAtom);
        return;
      }
    } while (this.containerAtoms.isEmpty());
    ((Atom.ContainerAtom)this.containerAtoms.peek()).add(paramContainerAtom);
  }
  
  private void onLeafAtomRead(Atom.LeafAtom paramLeafAtom, long paramLong)
    throws ParserException
  {
    if (!this.containerAtoms.isEmpty()) {
      ((Atom.ContainerAtom)this.containerAtoms.peek()).add(paramLeafAtom);
    }
    while (paramLeafAtom.type != Atom.TYPE_sidx) {
      return;
    }
    paramLeafAtom = parseSidx(paramLeafAtom.data, paramLong);
    this.extractorOutput.seekMap(paramLeafAtom);
    this.haveOutputSeekMap = true;
  }
  
  private void onMoofContainerAtomRead(Atom.ContainerAtom paramContainerAtom)
    throws ParserException
  {
    this.fragmentRun.reset();
    parseMoof(this.track, this.extendsDefaults, paramContainerAtom, this.fragmentRun, this.workaroundFlags, this.extendedTypeScratch);
    this.sampleIndex = 0;
  }
  
  private void onMoovContainerAtomRead(Atom.ContainerAtom paramContainerAtom)
    throws ParserException
  {
    List localList = paramContainerAtom.leafChildren;
    int j = localList.size();
    Object localObject1 = null;
    int i = 0;
    if (i < j)
    {
      Atom.LeafAtom localLeafAtom = (Atom.LeafAtom)localList.get(i);
      Object localObject2 = localObject1;
      if (localLeafAtom.type == Atom.TYPE_pssh)
      {
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = new DrmInitData.Mapped();
        }
        localObject1 = localLeafAtom.data.data;
        if (PsshAtomUtil.parseUuid((byte[])localObject1) != null) {
          break label108;
        }
        Log.w("FragmentedMp4Extractor", "Skipped pssh atom (failed to extract uuid)");
      }
      for (;;)
      {
        i += 1;
        localObject1 = localObject2;
        break;
        label108:
        ((DrmInitData.Mapped)localObject2).put(PsshAtomUtil.parseUuid((byte[])localObject1), new DrmInitData.SchemeInitData("video/mp4", (byte[])localObject1));
      }
    }
    if (localObject1 != null) {
      this.extractorOutput.drmInitData((DrmInitData)localObject1);
    }
    this.extendsDefaults = parseTrex(paramContainerAtom.getContainerAtomOfType(Atom.TYPE_mvex).getLeafAtomOfType(Atom.TYPE_trex).data);
    this.track = AtomParsers.parseTrak(paramContainerAtom.getContainerAtomOfType(Atom.TYPE_trak), paramContainerAtom.getLeafAtomOfType(Atom.TYPE_mvhd), false);
    if (this.track == null) {
      throw new ParserException("Track type not supported.");
    }
    this.trackOutput.format(this.track.mediaFormat);
  }
  
  private static void parseMoof(Track paramTrack, DefaultSampleValues paramDefaultSampleValues, Atom.ContainerAtom paramContainerAtom, TrackFragment paramTrackFragment, int paramInt, byte[] paramArrayOfByte)
    throws ParserException
  {
    if (paramContainerAtom.getChildAtomOfTypeCount(Atom.TYPE_traf) != 1) {
      throw new ParserException("Traf count in moof != 1 (unsupported).");
    }
    parseTraf(paramTrack, paramDefaultSampleValues, paramContainerAtom.getContainerAtomOfType(Atom.TYPE_traf), paramTrackFragment, paramInt, paramArrayOfByte);
  }
  
  private static void parseSaio(ParsableByteArray paramParsableByteArray, TrackFragment paramTrackFragment)
    throws ParserException
  {
    paramParsableByteArray.setPosition(8);
    int i = paramParsableByteArray.readInt();
    if ((Atom.parseFullAtomFlags(i) & 0x1) == 1) {
      paramParsableByteArray.skipBytes(8);
    }
    int j = paramParsableByteArray.readUnsignedIntToInt();
    if (j != 1) {
      throw new ParserException("Unexpected saio entry count: " + j);
    }
    i = Atom.parseFullAtomVersion(i);
    long l2 = paramTrackFragment.auxiliaryDataPosition;
    if (i == 0) {}
    for (long l1 = paramParsableByteArray.readUnsignedInt();; l1 = paramParsableByteArray.readUnsignedLongToLong())
    {
      paramTrackFragment.auxiliaryDataPosition = (l1 + l2);
      return;
    }
  }
  
  private static void parseSaiz(TrackEncryptionBox paramTrackEncryptionBox, ParsableByteArray paramParsableByteArray, TrackFragment paramTrackFragment)
    throws ParserException
  {
    int n = paramTrackEncryptionBox.initializationVectorSize;
    paramParsableByteArray.setPosition(8);
    if ((Atom.parseFullAtomFlags(paramParsableByteArray.readInt()) & 0x1) == 1) {
      paramParsableByteArray.skipBytes(8);
    }
    int j = paramParsableByteArray.readUnsignedByte();
    int m = paramParsableByteArray.readUnsignedIntToInt();
    if (m != paramTrackFragment.length) {
      throw new ParserException("Length mismatch: " + m + ", " + paramTrackFragment.length);
    }
    int i = 0;
    int k;
    if (j == 0)
    {
      paramTrackEncryptionBox = paramTrackFragment.sampleHasSubsampleEncryptionTable;
      j = 0;
      k = i;
      if (j < m)
      {
        k = paramParsableByteArray.readUnsignedByte();
        i += k;
        if (k > n) {}
        for (bool = true;; bool = false)
        {
          paramTrackEncryptionBox[j] = bool;
          j += 1;
          break;
        }
      }
    }
    else
    {
      if (j <= n) {
        break label199;
      }
    }
    label199:
    for (boolean bool = true;; bool = false)
    {
      k = 0 + j * m;
      Arrays.fill(paramTrackFragment.sampleHasSubsampleEncryptionTable, 0, m, bool);
      paramTrackFragment.initEncryptionData(k);
      return;
    }
  }
  
  private static void parseSenc(ParsableByteArray paramParsableByteArray, int paramInt, TrackFragment paramTrackFragment)
    throws ParserException
  {
    paramParsableByteArray.setPosition(paramInt + 8);
    paramInt = Atom.parseFullAtomFlags(paramParsableByteArray.readInt());
    if ((paramInt & 0x1) != 0) {
      throw new ParserException("Overriding TrackEncryptionBox parameters is unsupported.");
    }
    if ((paramInt & 0x2) != 0) {}
    for (boolean bool = true;; bool = false)
    {
      paramInt = paramParsableByteArray.readUnsignedIntToInt();
      if (paramInt == paramTrackFragment.length) {
        break;
      }
      throw new ParserException("Length mismatch: " + paramInt + ", " + paramTrackFragment.length);
    }
    Arrays.fill(paramTrackFragment.sampleHasSubsampleEncryptionTable, 0, paramInt, bool);
    paramTrackFragment.initEncryptionData(paramParsableByteArray.bytesLeft());
    paramTrackFragment.fillEncryptionData(paramParsableByteArray);
  }
  
  private static void parseSenc(ParsableByteArray paramParsableByteArray, TrackFragment paramTrackFragment)
    throws ParserException
  {
    parseSenc(paramParsableByteArray, 0, paramTrackFragment);
  }
  
  private static ChunkIndex parseSidx(ParsableByteArray paramParsableByteArray, long paramLong)
    throws ParserException
  {
    paramParsableByteArray.setPosition(8);
    int i = Atom.parseFullAtomVersion(paramParsableByteArray.readInt());
    paramParsableByteArray.skipBytes(4);
    long l3 = paramParsableByteArray.readUnsignedInt();
    long l1;
    int j;
    int[] arrayOfInt;
    long[] arrayOfLong1;
    long[] arrayOfLong2;
    long[] arrayOfLong3;
    long l2;
    if (i == 0)
    {
      l1 = paramParsableByteArray.readUnsignedInt();
      paramLong += paramParsableByteArray.readUnsignedInt();
      paramParsableByteArray.skipBytes(2);
      j = paramParsableByteArray.readUnsignedShort();
      arrayOfInt = new int[j];
      arrayOfLong1 = new long[j];
      arrayOfLong2 = new long[j];
      arrayOfLong3 = new long[j];
      l2 = Util.scaleLargeTimestamp(l1, 1000000L, l3);
      i = 0;
    }
    for (;;)
    {
      if (i >= j) {
        break label216;
      }
      int k = paramParsableByteArray.readInt();
      if ((0x80000000 & k) != 0)
      {
        throw new ParserException("Unhandled indirect reference");
        l1 = paramParsableByteArray.readUnsignedLongToLong();
        paramLong += paramParsableByteArray.readUnsignedLongToLong();
        break;
      }
      long l4 = paramParsableByteArray.readUnsignedInt();
      arrayOfInt[i] = (0x7FFFFFFF & k);
      arrayOfLong1[i] = paramLong;
      arrayOfLong3[i] = l2;
      l1 += l4;
      l2 = Util.scaleLargeTimestamp(l1, 1000000L, l3);
      arrayOfLong2[i] = (l2 - arrayOfLong3[i]);
      paramParsableByteArray.skipBytes(4);
      paramLong += arrayOfInt[i];
      i += 1;
    }
    label216:
    return new ChunkIndex(arrayOfInt, arrayOfLong1, arrayOfLong2, arrayOfLong3);
  }
  
  private static long parseTfdt(ParsableByteArray paramParsableByteArray)
  {
    paramParsableByteArray.setPosition(8);
    if (Atom.parseFullAtomVersion(paramParsableByteArray.readInt()) == 1) {
      return paramParsableByteArray.readUnsignedLongToLong();
    }
    return paramParsableByteArray.readUnsignedInt();
  }
  
  private static void parseTfhd(DefaultSampleValues paramDefaultSampleValues, ParsableByteArray paramParsableByteArray, TrackFragment paramTrackFragment)
  {
    paramParsableByteArray.setPosition(8);
    int m = Atom.parseFullAtomFlags(paramParsableByteArray.readInt());
    paramParsableByteArray.skipBytes(4);
    if ((m & 0x1) != 0)
    {
      long l = paramParsableByteArray.readUnsignedLongToLong();
      paramTrackFragment.dataPosition = l;
      paramTrackFragment.auxiliaryDataPosition = l;
    }
    int i;
    int j;
    label73:
    int k;
    if ((m & 0x2) != 0)
    {
      i = paramParsableByteArray.readUnsignedIntToInt() - 1;
      if ((m & 0x8) == 0) {
        break label128;
      }
      j = paramParsableByteArray.readUnsignedIntToInt();
      if ((m & 0x10) == 0) {
        break label137;
      }
      k = paramParsableByteArray.readUnsignedIntToInt();
      label87:
      if ((m & 0x20) == 0) {
        break label146;
      }
    }
    label128:
    label137:
    label146:
    for (m = paramParsableByteArray.readUnsignedIntToInt();; m = paramDefaultSampleValues.flags)
    {
      paramTrackFragment.header = new DefaultSampleValues(i, j, k, m);
      return;
      i = paramDefaultSampleValues.sampleDescriptionIndex;
      break;
      j = paramDefaultSampleValues.duration;
      break label73;
      k = paramDefaultSampleValues.size;
      break label87;
    }
  }
  
  private static void parseTraf(Track paramTrack, DefaultSampleValues paramDefaultSampleValues, Atom.ContainerAtom paramContainerAtom, TrackFragment paramTrackFragment, int paramInt, byte[] paramArrayOfByte)
    throws ParserException
  {
    if (paramContainerAtom.getChildAtomOfTypeCount(Atom.TYPE_trun) != 1) {
      throw new ParserException("Trun count in traf != 1 (unsupported).");
    }
    if ((paramContainerAtom.getLeafAtomOfType(Atom.TYPE_tfdt) == null) || ((paramInt & 0x2) != 0)) {}
    for (long l = 0L;; l = parseTfdt(paramContainerAtom.getLeafAtomOfType(Atom.TYPE_tfdt).data))
    {
      parseTfhd(paramDefaultSampleValues, paramContainerAtom.getLeafAtomOfType(Atom.TYPE_tfhd).data, paramTrackFragment);
      paramDefaultSampleValues = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_trun);
      parseTrun(paramTrack, paramTrackFragment.header, l, paramInt, paramDefaultSampleValues.data, paramTrackFragment);
      paramDefaultSampleValues = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_saiz);
      if (paramDefaultSampleValues != null) {
        parseSaiz(paramTrack.sampleDescriptionEncryptionBoxes[paramTrackFragment.header.sampleDescriptionIndex], paramDefaultSampleValues.data, paramTrackFragment);
      }
      paramTrack = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_saio);
      if (paramTrack != null) {
        parseSaio(paramTrack.data, paramTrackFragment);
      }
      paramTrack = paramContainerAtom.getLeafAtomOfType(Atom.TYPE_senc);
      if (paramTrack != null) {
        parseSenc(paramTrack.data, paramTrackFragment);
      }
      int i = paramContainerAtom.leafChildren.size();
      paramInt = 0;
      while (paramInt < i)
      {
        paramTrack = (Atom.LeafAtom)paramContainerAtom.leafChildren.get(paramInt);
        if (paramTrack.type == Atom.TYPE_uuid) {
          parseUuid(paramTrack.data, paramTrackFragment, paramArrayOfByte);
        }
        paramInt += 1;
      }
    }
  }
  
  private static DefaultSampleValues parseTrex(ParsableByteArray paramParsableByteArray)
  {
    paramParsableByteArray.setPosition(16);
    return new DefaultSampleValues(paramParsableByteArray.readUnsignedIntToInt() - 1, paramParsableByteArray.readUnsignedIntToInt(), paramParsableByteArray.readUnsignedIntToInt(), paramParsableByteArray.readInt());
  }
  
  private static void parseTrun(Track paramTrack, DefaultSampleValues paramDefaultSampleValues, long paramLong, int paramInt, ParsableByteArray paramParsableByteArray, TrackFragment paramTrackFragment)
  {
    paramParsableByteArray.setPosition(8);
    int i1 = Atom.parseFullAtomFlags(paramParsableByteArray.readInt());
    int i6 = paramParsableByteArray.readUnsignedIntToInt();
    if ((i1 & 0x1) != 0) {
      paramTrackFragment.dataPosition += paramParsableByteArray.readInt();
    }
    int j;
    int k;
    label88:
    int m;
    label100:
    int n;
    label112:
    label124:
    int[] arrayOfInt2;
    int i2;
    label245:
    int i3;
    label248:
    int i4;
    label267:
    int i5;
    if ((i1 & 0x4) != 0)
    {
      j = 1;
      int i = paramDefaultSampleValues.flags;
      if (j != 0) {
        i = paramParsableByteArray.readUnsignedIntToInt();
      }
      if ((i1 & 0x100) == 0) {
        break label392;
      }
      k = 1;
      if ((i1 & 0x200) == 0) {
        break label398;
      }
      m = 1;
      if ((i1 & 0x400) == 0) {
        break label404;
      }
      n = 1;
      if ((i1 & 0x800) == 0) {
        break label410;
      }
      i1 = 1;
      long l2 = 0L;
      long l1 = l2;
      if (paramTrack.editListDurations != null)
      {
        l1 = l2;
        if (paramTrack.editListDurations.length == 1)
        {
          l1 = l2;
          if (paramTrack.editListDurations[0] == 0L) {
            l1 = Util.scaleLargeTimestamp(paramTrack.editListMediaTimes[0], 1000L, paramTrack.timescale);
          }
        }
      }
      paramTrackFragment.initTables(i6);
      int[] arrayOfInt1 = paramTrackFragment.sampleSizeTable;
      arrayOfInt2 = paramTrackFragment.sampleCompositionTimeOffsetTable;
      long[] arrayOfLong = paramTrackFragment.sampleDecodingTimeTable;
      paramTrackFragment = paramTrackFragment.sampleIsSyncFrameTable;
      l2 = paramTrack.timescale;
      if ((paramTrack.type != Track.TYPE_vide) || ((paramInt & 0x1) == 0)) {
        break label416;
      }
      i2 = 1;
      i3 = 0;
      if (i3 >= i6) {
        return;
      }
      if (k == 0) {
        break label422;
      }
      i4 = paramParsableByteArray.readUnsignedIntToInt();
      if (m == 0) {
        break label431;
      }
      i5 = paramParsableByteArray.readUnsignedIntToInt();
      label279:
      if ((i3 != 0) || (j == 0)) {
        break label440;
      }
      paramInt = i;
      label293:
      if (i1 == 0) {
        break label464;
      }
      arrayOfInt2[i3] = ((int)(paramParsableByteArray.readInt() * 1000 / l2));
      label317:
      arrayOfLong[i3] = (Util.scaleLargeTimestamp(paramLong, 1000L, l2) - l1);
      arrayOfInt1[i3] = i5;
      if (((paramInt >> 16 & 0x1) != 0) || ((i2 != 0) && (i3 != 0))) {
        break label473;
      }
    }
    label392:
    label398:
    label404:
    label410:
    label416:
    label422:
    label431:
    label440:
    label464:
    label473:
    for (int i7 = 1;; i7 = 0)
    {
      paramTrackFragment[i3] = i7;
      paramLong += i4;
      i3 += 1;
      break label248;
      j = 0;
      break;
      k = 0;
      break label88;
      m = 0;
      break label100;
      n = 0;
      break label112;
      i1 = 0;
      break label124;
      i2 = 0;
      break label245;
      i4 = paramDefaultSampleValues.duration;
      break label267;
      i5 = paramDefaultSampleValues.size;
      break label279;
      if (n != 0)
      {
        paramInt = paramParsableByteArray.readInt();
        break label293;
      }
      paramInt = paramDefaultSampleValues.flags;
      break label293;
      arrayOfInt2[i3] = 0;
      break label317;
    }
  }
  
  private static void parseUuid(ParsableByteArray paramParsableByteArray, TrackFragment paramTrackFragment, byte[] paramArrayOfByte)
    throws ParserException
  {
    paramParsableByteArray.setPosition(8);
    paramParsableByteArray.readBytes(paramArrayOfByte, 0, 16);
    if (!Arrays.equals(paramArrayOfByte, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
      return;
    }
    parseSenc(paramParsableByteArray, 16, paramTrackFragment);
  }
  
  private boolean readAtomHeader(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    if (this.atomHeaderBytesRead == 0)
    {
      if (!paramExtractorInput.readFully(this.atomHeader.data, 0, 8, true)) {
        return false;
      }
      this.atomHeaderBytesRead = 8;
      this.atomHeader.setPosition(0);
      this.atomSize = this.atomHeader.readUnsignedInt();
      this.atomType = this.atomHeader.readInt();
    }
    if (this.atomSize == 1L)
    {
      paramExtractorInput.readFully(this.atomHeader.data, 8, 8);
      this.atomHeaderBytesRead += 8;
      this.atomSize = this.atomHeader.readUnsignedLongToLong();
    }
    long l1 = paramExtractorInput.getPosition() - this.atomHeaderBytesRead;
    if (this.atomType == Atom.TYPE_moof)
    {
      this.fragmentRun.auxiliaryDataPosition = l1;
      this.fragmentRun.dataPosition = l1;
    }
    if (this.atomType == Atom.TYPE_mdat)
    {
      this.endOfMdatPosition = (this.atomSize + l1);
      if (!this.haveOutputSeekMap)
      {
        this.extractorOutput.seekMap(SeekMap.UNSEEKABLE);
        this.haveOutputSeekMap = true;
      }
      if (this.fragmentRun.sampleEncryptionDataNeedsFill) {}
      for (this.parserState = 2;; this.parserState = 3) {
        return true;
      }
    }
    if (shouldParseContainerAtom(this.atomType))
    {
      l1 = paramExtractorInput.getPosition();
      long l2 = this.atomSize;
      this.containerAtoms.add(new Atom.ContainerAtom(this.atomType, l1 + l2 - 8L));
      enterReadingAtomHeaderState();
    }
    for (;;)
    {
      return true;
      if (shouldParseLeafAtom(this.atomType))
      {
        if (this.atomHeaderBytesRead != 8) {
          throw new ParserException("Leaf atom defines extended atom size (unsupported).");
        }
        if (this.atomSize > 2147483647L) {
          throw new ParserException("Leaf atom with length > 2147483647 (unsupported).");
        }
        this.atomData = new ParsableByteArray((int)this.atomSize);
        System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
        this.parserState = 1;
      }
      else
      {
        if (this.atomSize > 2147483647L) {
          throw new ParserException("Skipping atom with length > 2147483647 (unsupported).");
        }
        this.atomData = null;
        this.parserState = 1;
      }
    }
  }
  
  private void readAtomPayload(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    int i = (int)this.atomSize - this.atomHeaderBytesRead;
    if (this.atomData != null)
    {
      paramExtractorInput.readFully(this.atomData.data, 8, i);
      onLeafAtomRead(new Atom.LeafAtom(this.atomType, this.atomData), paramExtractorInput.getPosition());
    }
    for (;;)
    {
      long l = paramExtractorInput.getPosition();
      while ((!this.containerAtoms.isEmpty()) && (((Atom.ContainerAtom)this.containerAtoms.peek()).endPosition == l)) {
        onContainerAtomRead((Atom.ContainerAtom)this.containerAtoms.pop());
      }
      paramExtractorInput.skipFully(i);
    }
    enterReadingAtomHeaderState();
  }
  
  private void readEncryptionData(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    int i = (int)(this.fragmentRun.auxiliaryDataPosition - paramExtractorInput.getPosition());
    if (i < 0) {
      throw new ParserException("Offset to encryption data was negative.");
    }
    paramExtractorInput.skipFully(i);
    this.fragmentRun.fillEncryptionData(paramExtractorInput);
    this.parserState = 3;
  }
  
  private boolean readSample(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    int i;
    int j;
    if (this.parserState == 3)
    {
      if (this.sampleIndex == this.fragmentRun.length)
      {
        i = (int)(this.endOfMdatPosition - paramExtractorInput.getPosition());
        if (i < 0) {
          throw new ParserException("Offset to end of mdat was negative.");
        }
        paramExtractorInput.skipFully(i);
        enterReadingAtomHeaderState();
        return false;
      }
      if (this.sampleIndex == 0)
      {
        i = (int)(this.fragmentRun.dataPosition - paramExtractorInput.getPosition());
        if (i < 0) {
          throw new ParserException("Offset to sample data was negative.");
        }
        paramExtractorInput.skipFully(i);
      }
      this.sampleSize = this.fragmentRun.sampleSizeTable[this.sampleIndex];
      if (this.fragmentRun.definesEncryptionData)
      {
        this.sampleBytesWritten = appendSampleEncryptionData(this.fragmentRun.sampleEncryptionData);
        this.sampleSize += this.sampleBytesWritten;
        this.sampleCurrentNalBytesRemaining = 0;
        this.parserState = 4;
      }
    }
    else
    {
      if (this.track.nalUnitLengthFieldLength == -1) {
        break label372;
      }
      byte[] arrayOfByte = this.nalLength.data;
      arrayOfByte[0] = 0;
      arrayOfByte[1] = 0;
      arrayOfByte[2] = 0;
      i = this.track.nalUnitLengthFieldLength;
      j = 4 - this.track.nalUnitLengthFieldLength;
    }
    int k;
    for (;;)
    {
      if (this.sampleBytesWritten >= this.sampleSize) {
        break label417;
      }
      if (this.sampleCurrentNalBytesRemaining == 0)
      {
        paramExtractorInput.readFully(this.nalLength.data, j, i);
        this.nalLength.setPosition(0);
        this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
        this.nalStartCode.setPosition(0);
        this.trackOutput.sampleData(this.nalStartCode, 4);
        this.sampleBytesWritten += 4;
        this.sampleSize += j;
        continue;
        this.sampleBytesWritten = 0;
        break;
      }
      k = this.trackOutput.sampleData(paramExtractorInput, this.sampleCurrentNalBytesRemaining, false);
      this.sampleBytesWritten += k;
      this.sampleCurrentNalBytesRemaining -= k;
    }
    label372:
    while (this.sampleBytesWritten < this.sampleSize)
    {
      i = this.trackOutput.sampleData(paramExtractorInput, this.sampleSize - this.sampleBytesWritten, false);
      this.sampleBytesWritten += i;
    }
    label417:
    long l = this.fragmentRun.getSamplePresentationTime(this.sampleIndex);
    if (this.fragmentRun.definesEncryptionData)
    {
      i = 2;
      if (this.fragmentRun.sampleIsSyncFrameTable[this.sampleIndex] == 0) {
        break label541;
      }
      j = 1;
      label459:
      k = this.fragmentRun.header.sampleDescriptionIndex;
      if (!this.fragmentRun.definesEncryptionData) {
        break label546;
      }
    }
    label541:
    label546:
    for (paramExtractorInput = this.track.sampleDescriptionEncryptionBoxes[k].keyId;; paramExtractorInput = null)
    {
      this.trackOutput.sampleMetadata(l * 1000L, i | j, this.sampleSize, 0, paramExtractorInput);
      this.sampleIndex += 1;
      this.parserState = 3;
      return true;
      i = 0;
      break;
      j = 0;
      break label459;
    }
  }
  
  private static boolean shouldParseContainerAtom(int paramInt)
  {
    return (paramInt == Atom.TYPE_moov) || (paramInt == Atom.TYPE_trak) || (paramInt == Atom.TYPE_mdia) || (paramInt == Atom.TYPE_minf) || (paramInt == Atom.TYPE_stbl) || (paramInt == Atom.TYPE_moof) || (paramInt == Atom.TYPE_traf) || (paramInt == Atom.TYPE_mvex) || (paramInt == Atom.TYPE_edts);
  }
  
  private static boolean shouldParseLeafAtom(int paramInt)
  {
    return (paramInt == Atom.TYPE_hdlr) || (paramInt == Atom.TYPE_mdhd) || (paramInt == Atom.TYPE_mvhd) || (paramInt == Atom.TYPE_sidx) || (paramInt == Atom.TYPE_stsd) || (paramInt == Atom.TYPE_tfdt) || (paramInt == Atom.TYPE_tfhd) || (paramInt == Atom.TYPE_tkhd) || (paramInt == Atom.TYPE_trex) || (paramInt == Atom.TYPE_trun) || (paramInt == Atom.TYPE_pssh) || (paramInt == Atom.TYPE_saiz) || (paramInt == Atom.TYPE_saio) || (paramInt == Atom.TYPE_senc) || (paramInt == Atom.TYPE_uuid) || (paramInt == Atom.TYPE_elst);
  }
  
  public void init(ExtractorOutput paramExtractorOutput)
  {
    this.extractorOutput = paramExtractorOutput;
    this.trackOutput = paramExtractorOutput.track(0);
    this.extractorOutput.endTracks();
  }
  
  public int read(ExtractorInput paramExtractorInput, PositionHolder paramPositionHolder)
    throws IOException, InterruptedException
  {
    for (;;)
    {
      switch (this.parserState)
      {
      default: 
        if (readSample(paramExtractorInput)) {
          return 0;
        }
        break;
      case 0: 
        if (!readAtomHeader(paramExtractorInput)) {
          return -1;
        }
        break;
      case 1: 
        readAtomPayload(paramExtractorInput);
        break;
      case 2: 
        readEncryptionData(paramExtractorInput);
      }
    }
  }
  
  public void seek()
  {
    this.containerAtoms.clear();
    enterReadingAtomHeaderState();
  }
  
  public void setTrack(Track paramTrack)
  {
    this.extendsDefaults = new DefaultSampleValues(0, 0, 0, 0);
    this.track = paramTrack;
  }
  
  public boolean sniff(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    return Sniffer.sniffFragmented(paramExtractorInput);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp4/FragmentedMp4Extractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */