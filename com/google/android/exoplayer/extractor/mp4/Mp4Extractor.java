package com.google.android.exoplayer.extractor.mp4;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.GaplessInfo;
import com.google.android.exoplayer.extractor.PositionHolder;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.NalUnitUtil;
import com.google.android.exoplayer.util.ParsableByteArray;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class Mp4Extractor
  implements Extractor, SeekMap
{
  private static final int BRAND_QUICKTIME = Util.getIntegerCodeForString("qt  ");
  private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144L;
  private static final int STATE_AFTER_SEEK = 0;
  private static final int STATE_READING_ATOM_HEADER = 1;
  private static final int STATE_READING_ATOM_PAYLOAD = 2;
  private static final int STATE_READING_SAMPLE = 3;
  private ParsableByteArray atomData;
  private final ParsableByteArray atomHeader = new ParsableByteArray(16);
  private int atomHeaderBytesRead;
  private long atomSize;
  private int atomType;
  private final Stack<Atom.ContainerAtom> containerAtoms = new Stack();
  private ExtractorOutput extractorOutput;
  private boolean isQuickTime;
  private final ParsableByteArray nalLength = new ParsableByteArray(4);
  private final ParsableByteArray nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
  private int parserState;
  private int sampleBytesWritten;
  private int sampleCurrentNalBytesRemaining;
  private int sampleSize;
  private Mp4Track[] tracks;
  
  public Mp4Extractor()
  {
    enterReadingAtomHeaderState();
  }
  
  private void enterReadingAtomHeaderState()
  {
    this.parserState = 1;
    this.atomHeaderBytesRead = 0;
  }
  
  private int getTrackIndexOfEarliestCurrentSample()
  {
    int j = -1;
    long l1 = Long.MAX_VALUE;
    int i = 0;
    if (i < this.tracks.length)
    {
      Mp4Track localMp4Track = this.tracks[i];
      int k = localMp4Track.sampleIndex;
      long l2;
      if (k == localMp4Track.sampleTable.sampleCount) {
        l2 = l1;
      }
      for (;;)
      {
        i += 1;
        l1 = l2;
        break;
        long l3 = localMp4Track.sampleTable.offsets[k];
        l2 = l1;
        if (l3 < l1)
        {
          l2 = l3;
          j = i;
        }
      }
    }
    return j;
  }
  
  private static boolean processFtypAtom(ParsableByteArray paramParsableByteArray)
  {
    paramParsableByteArray.setPosition(8);
    if (paramParsableByteArray.readInt() == BRAND_QUICKTIME) {
      return true;
    }
    paramParsableByteArray.skipBytes(4);
    while (paramParsableByteArray.bytesLeft() > 0) {
      if (paramParsableByteArray.readInt() == BRAND_QUICKTIME) {
        return true;
      }
    }
    return false;
  }
  
  private void processMoovAtom(Atom.ContainerAtom paramContainerAtom)
  {
    ArrayList localArrayList = new ArrayList();
    long l1 = Long.MAX_VALUE;
    GaplessInfo localGaplessInfo = null;
    Object localObject1 = paramContainerAtom.getContainerAtomOfType(Atom.TYPE_udta);
    if (localObject1 != null) {
      localGaplessInfo = AtomParsers.parseUdta((Atom.ContainerAtom)localObject1);
    }
    int i = 0;
    if (i < paramContainerAtom.containerChildren.size())
    {
      Object localObject2 = (Atom.ContainerAtom)paramContainerAtom.containerChildren.get(i);
      long l2;
      if (((Atom.ContainerAtom)localObject2).type != Atom.TYPE_trak) {
        l2 = l1;
      }
      for (;;)
      {
        i += 1;
        l1 = l2;
        break;
        localObject1 = AtomParsers.parseTrak((Atom.ContainerAtom)localObject2, paramContainerAtom.getLeafAtomOfType(Atom.TYPE_mvhd), this.isQuickTime);
        l2 = l1;
        if (localObject1 != null)
        {
          TrackSampleTable localTrackSampleTable = AtomParsers.parseStbl((Track)localObject1, ((Atom.ContainerAtom)localObject2).getContainerAtomOfType(Atom.TYPE_mdia).getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl));
          l2 = l1;
          if (localTrackSampleTable.sampleCount != 0)
          {
            Mp4Track localMp4Track = new Mp4Track((Track)localObject1, localTrackSampleTable, this.extractorOutput.track(i));
            int j = localTrackSampleTable.maximumSize;
            localObject2 = ((Track)localObject1).mediaFormat.copyWithMaxInputSize(j + 30);
            localObject1 = localObject2;
            if (localGaplessInfo != null) {
              localObject1 = ((MediaFormat)localObject2).copyWithGaplessInfo(localGaplessInfo.encoderDelay, localGaplessInfo.encoderPadding);
            }
            localMp4Track.trackOutput.format((MediaFormat)localObject1);
            localArrayList.add(localMp4Track);
            long l3 = localTrackSampleTable.offsets[0];
            l2 = l1;
            if (l3 < l1) {
              l2 = l3;
            }
          }
        }
      }
    }
    this.tracks = ((Mp4Track[])localArrayList.toArray(new Mp4Track[0]));
    this.extractorOutput.endTracks();
    this.extractorOutput.seekMap(this);
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
    if (shouldParseContainerAtom(this.atomType))
    {
      long l1 = paramExtractorInput.getPosition();
      long l2 = this.atomSize;
      long l3 = this.atomHeaderBytesRead;
      this.containerAtoms.add(new Atom.ContainerAtom(this.atomType, l1 + l2 - l3));
      enterReadingAtomHeaderState();
    }
    for (;;)
    {
      return true;
      if (shouldParseLeafAtom(this.atomType))
      {
        if (this.atomHeaderBytesRead == 8)
        {
          bool = true;
          label197:
          Assertions.checkState(bool);
          if (this.atomSize > 2147483647L) {
            break label272;
          }
        }
        label272:
        for (boolean bool = true;; bool = false)
        {
          Assertions.checkState(bool);
          this.atomData = new ParsableByteArray((int)this.atomSize);
          System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
          this.parserState = 2;
          break;
          bool = false;
          break label197;
        }
      }
      this.atomData = null;
      this.parserState = 2;
    }
  }
  
  private boolean readAtomPayload(ExtractorInput paramExtractorInput, PositionHolder paramPositionHolder)
    throws IOException, InterruptedException
  {
    long l1 = this.atomSize - this.atomHeaderBytesRead;
    long l2 = paramExtractorInput.getPosition();
    boolean bool2 = false;
    boolean bool1;
    if (this.atomData != null)
    {
      paramExtractorInput.readFully(this.atomData.data, this.atomHeaderBytesRead, (int)l1);
      if (this.atomType == Atom.TYPE_ftyp)
      {
        this.isQuickTime = processFtypAtom(this.atomData);
        bool1 = bool2;
      }
    }
    while ((!this.containerAtoms.isEmpty()) && (((Atom.ContainerAtom)this.containerAtoms.peek()).endPosition == l2 + l1))
    {
      paramExtractorInput = (Atom.ContainerAtom)this.containerAtoms.pop();
      if (paramExtractorInput.type == Atom.TYPE_moov)
      {
        processMoovAtom(paramExtractorInput);
        this.containerAtoms.clear();
        this.parserState = 3;
        return false;
        bool1 = bool2;
        if (!this.containerAtoms.isEmpty())
        {
          ((Atom.ContainerAtom)this.containerAtoms.peek()).add(new Atom.LeafAtom(this.atomType, this.atomData));
          bool1 = bool2;
          continue;
          if (l1 < 262144L)
          {
            paramExtractorInput.skipFully((int)l1);
            bool1 = bool2;
          }
          else
          {
            paramPositionHolder.position = (paramExtractorInput.getPosition() + l1);
            bool1 = true;
          }
        }
      }
      else if (!this.containerAtoms.isEmpty())
      {
        ((Atom.ContainerAtom)this.containerAtoms.peek()).add(paramExtractorInput);
      }
    }
    enterReadingAtomHeaderState();
    return bool1;
  }
  
  private int readSample(ExtractorInput paramExtractorInput, PositionHolder paramPositionHolder)
    throws IOException, InterruptedException
  {
    int i = getTrackIndexOfEarliestCurrentSample();
    if (i == -1) {
      return -1;
    }
    Mp4Track localMp4Track = this.tracks[i];
    TrackOutput localTrackOutput = localMp4Track.trackOutput;
    i = localMp4Track.sampleIndex;
    long l1 = localMp4Track.sampleTable.offsets[i];
    long l2 = l1 - paramExtractorInput.getPosition() + this.sampleBytesWritten;
    if ((l2 < 0L) || (l2 >= 262144L))
    {
      paramPositionHolder.position = l1;
      return 1;
    }
    paramExtractorInput.skipFully((int)l2);
    this.sampleSize = localMp4Track.sampleTable.sizes[i];
    int j;
    if (localMp4Track.track.nalUnitLengthFieldLength != -1)
    {
      paramPositionHolder = this.nalLength.data;
      paramPositionHolder[0] = 0;
      paramPositionHolder[1] = 0;
      paramPositionHolder[2] = 0;
      j = localMp4Track.track.nalUnitLengthFieldLength;
      int k = 4 - localMp4Track.track.nalUnitLengthFieldLength;
      while (this.sampleBytesWritten < this.sampleSize) {
        if (this.sampleCurrentNalBytesRemaining == 0)
        {
          paramExtractorInput.readFully(this.nalLength.data, k, j);
          this.nalLength.setPosition(0);
          this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
          this.nalStartCode.setPosition(0);
          localTrackOutput.sampleData(this.nalStartCode, 4);
          this.sampleBytesWritten += 4;
          this.sampleSize += k;
        }
        else
        {
          int m = localTrackOutput.sampleData(paramExtractorInput, this.sampleCurrentNalBytesRemaining, false);
          this.sampleBytesWritten += m;
          this.sampleCurrentNalBytesRemaining -= m;
        }
      }
    }
    while (this.sampleBytesWritten < this.sampleSize)
    {
      j = localTrackOutput.sampleData(paramExtractorInput, this.sampleSize - this.sampleBytesWritten, false);
      this.sampleBytesWritten += j;
      this.sampleCurrentNalBytesRemaining -= j;
    }
    localTrackOutput.sampleMetadata(localMp4Track.sampleTable.timestampsUs[i], localMp4Track.sampleTable.flags[i], this.sampleSize, 0, null);
    localMp4Track.sampleIndex += 1;
    this.sampleBytesWritten = 0;
    this.sampleCurrentNalBytesRemaining = 0;
    return 0;
  }
  
  private static boolean shouldParseContainerAtom(int paramInt)
  {
    return (paramInt == Atom.TYPE_moov) || (paramInt == Atom.TYPE_trak) || (paramInt == Atom.TYPE_mdia) || (paramInt == Atom.TYPE_minf) || (paramInt == Atom.TYPE_stbl) || (paramInt == Atom.TYPE_edts) || (paramInt == Atom.TYPE_udta);
  }
  
  private static boolean shouldParseLeafAtom(int paramInt)
  {
    return (paramInt == Atom.TYPE_mdhd) || (paramInt == Atom.TYPE_mvhd) || (paramInt == Atom.TYPE_hdlr) || (paramInt == Atom.TYPE_stsd) || (paramInt == Atom.TYPE_stts) || (paramInt == Atom.TYPE_stss) || (paramInt == Atom.TYPE_ctts) || (paramInt == Atom.TYPE_elst) || (paramInt == Atom.TYPE_stsc) || (paramInt == Atom.TYPE_stsz) || (paramInt == Atom.TYPE_stco) || (paramInt == Atom.TYPE_co64) || (paramInt == Atom.TYPE_tkhd) || (paramInt == Atom.TYPE_ftyp) || (paramInt == Atom.TYPE_meta);
  }
  
  public long getPosition(long paramLong)
  {
    long l1 = Long.MAX_VALUE;
    int i = 0;
    while (i < this.tracks.length)
    {
      TrackSampleTable localTrackSampleTable = this.tracks[i].sampleTable;
      int k = localTrackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(paramLong);
      int j = k;
      if (k == -1) {
        j = localTrackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(paramLong);
      }
      this.tracks[i].sampleIndex = j;
      long l3 = localTrackSampleTable.offsets[j];
      long l2 = l1;
      if (l3 < l1) {
        l2 = l3;
      }
      i += 1;
      l1 = l2;
    }
    return l1;
  }
  
  public void init(ExtractorOutput paramExtractorOutput)
  {
    this.extractorOutput = paramExtractorOutput;
  }
  
  public boolean isSeekable()
  {
    return true;
  }
  
  public int read(ExtractorInput paramExtractorInput, PositionHolder paramPositionHolder)
    throws IOException, InterruptedException
  {
    do
    {
      do
      {
        for (;;)
        {
          switch (this.parserState)
          {
          default: 
            return readSample(paramExtractorInput, paramPositionHolder);
          case 0: 
            if (paramExtractorInput.getPosition() == 0L) {
              enterReadingAtomHeaderState();
            } else {
              this.parserState = 3;
            }
            break;
          }
        }
      } while (readAtomHeader(paramExtractorInput));
      return -1;
    } while (!readAtomPayload(paramExtractorInput, paramPositionHolder));
    return 1;
  }
  
  public void seek()
  {
    this.containerAtoms.clear();
    this.atomHeaderBytesRead = 0;
    this.sampleBytesWritten = 0;
    this.sampleCurrentNalBytesRemaining = 0;
    this.parserState = 0;
  }
  
  public boolean sniff(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    return Sniffer.sniffUnfragmented(paramExtractorInput);
  }
  
  private static final class Mp4Track
  {
    public int sampleIndex;
    public final TrackSampleTable sampleTable;
    public final Track track;
    public final TrackOutput trackOutput;
    
    public Mp4Track(Track paramTrack, TrackSampleTable paramTrackSampleTable, TrackOutput paramTrackOutput)
    {
      this.track = paramTrack;
      this.sampleTable = paramTrackSampleTable;
      this.trackOutput = paramTrackOutput;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp4/Mp4Extractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */