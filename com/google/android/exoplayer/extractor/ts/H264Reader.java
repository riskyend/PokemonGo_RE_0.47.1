package com.google.android.exoplayer.extractor.ts;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.util.CodecSpecificDataUtil;
import com.google.android.exoplayer.util.CodecSpecificDataUtil.SpsData;
import com.google.android.exoplayer.util.NalUnitUtil;
import com.google.android.exoplayer.util.ParsableBitArray;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class H264Reader
  extends ElementaryStreamReader
{
  private static final int FRAME_TYPE_ALL_I = 7;
  private static final int FRAME_TYPE_I = 2;
  private static final int NAL_UNIT_TYPE_AUD = 9;
  private static final int NAL_UNIT_TYPE_IDR = 5;
  private static final int NAL_UNIT_TYPE_IFR = 1;
  private static final int NAL_UNIT_TYPE_PPS = 8;
  private static final int NAL_UNIT_TYPE_SEI = 6;
  private static final int NAL_UNIT_TYPE_SPS = 7;
  private boolean foundFirstSample;
  private boolean hasOutputFormat;
  private final IfrParserBuffer ifrParserBuffer;
  private boolean isKeyframe;
  private long pesTimeUs;
  private final NalUnitTargetBuffer pps;
  private final boolean[] prefixFlags;
  private long samplePosition;
  private long sampleTimeUs;
  private final NalUnitTargetBuffer sei;
  private final SeiReader seiReader;
  private final ParsableByteArray seiWrapper;
  private final NalUnitTargetBuffer sps;
  private long totalBytesWritten;
  
  public H264Reader(TrackOutput paramTrackOutput, SeiReader paramSeiReader, boolean paramBoolean)
  {
    super(paramTrackOutput);
    this.seiReader = paramSeiReader;
    this.prefixFlags = new boolean[3];
    if (paramBoolean) {}
    for (paramTrackOutput = new IfrParserBuffer();; paramTrackOutput = null)
    {
      this.ifrParserBuffer = paramTrackOutput;
      this.sps = new NalUnitTargetBuffer(7, 128);
      this.pps = new NalUnitTargetBuffer(8, 128);
      this.sei = new NalUnitTargetBuffer(6, 128);
      this.seiWrapper = new ParsableByteArray();
      return;
    }
  }
  
  private void feedNalUnitTargetBuffersData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.ifrParserBuffer != null) {
      this.ifrParserBuffer.appendToNalUnit(paramArrayOfByte, paramInt1, paramInt2);
    }
    if (!this.hasOutputFormat)
    {
      this.sps.appendToNalUnit(paramArrayOfByte, paramInt1, paramInt2);
      this.pps.appendToNalUnit(paramArrayOfByte, paramInt1, paramInt2);
    }
    this.sei.appendToNalUnit(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  private void feedNalUnitTargetBuffersStart(int paramInt)
  {
    if (this.ifrParserBuffer != null) {
      this.ifrParserBuffer.startNalUnit(paramInt);
    }
    if (!this.hasOutputFormat)
    {
      this.sps.startNalUnit(paramInt);
      this.pps.startNalUnit(paramInt);
    }
    this.sei.startNalUnit(paramInt);
  }
  
  private void feedNalUnitTargetEnd(long paramLong, int paramInt)
  {
    this.sps.endNalUnit(paramInt);
    this.pps.endNalUnit(paramInt);
    if (this.sei.endNalUnit(paramInt))
    {
      paramInt = NalUnitUtil.unescapeStream(this.sei.nalData, this.sei.nalLength);
      this.seiWrapper.reset(this.sei.nalData, paramInt);
      this.seiWrapper.setPosition(4);
      this.seiReader.consume(paramLong, this.seiWrapper);
    }
  }
  
  private static MediaFormat parseMediaFormat(NalUnitTargetBuffer paramNalUnitTargetBuffer1, NalUnitTargetBuffer paramNalUnitTargetBuffer2)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Arrays.copyOf(paramNalUnitTargetBuffer1.nalData, paramNalUnitTargetBuffer1.nalLength));
    localArrayList.add(Arrays.copyOf(paramNalUnitTargetBuffer2.nalData, paramNalUnitTargetBuffer2.nalLength));
    NalUnitUtil.unescapeStream(paramNalUnitTargetBuffer1.nalData, paramNalUnitTargetBuffer1.nalLength);
    paramNalUnitTargetBuffer1 = new ParsableBitArray(paramNalUnitTargetBuffer1.nalData);
    paramNalUnitTargetBuffer1.skipBits(32);
    paramNalUnitTargetBuffer1 = CodecSpecificDataUtil.parseSpsNalUnit(paramNalUnitTargetBuffer1);
    return MediaFormat.createVideoFormat(null, "video/avc", -1, -1, -1L, paramNalUnitTargetBuffer1.width, paramNalUnitTargetBuffer1.height, localArrayList, -1, paramNalUnitTargetBuffer1.pixelWidthAspectRatio);
  }
  
  public void consume(ParsableByteArray paramParsableByteArray)
  {
    int j;
    byte[] arrayOfByte;
    int k;
    if (paramParsableByteArray.bytesLeft() > 0)
    {
      i = paramParsableByteArray.getPosition();
      j = paramParsableByteArray.limit();
      arrayOfByte = paramParsableByteArray.data;
      this.totalBytesWritten += paramParsableByteArray.bytesLeft();
      this.output.sampleData(paramParsableByteArray, paramParsableByteArray.bytesLeft());
      k = NalUnitUtil.findNalUnit(arrayOfByte, i, j, this.prefixFlags);
      if (k == j) {
        feedNalUnitTargetBuffersData(arrayOfByte, i, j);
      }
    }
    else
    {
      return;
    }
    int m = NalUnitUtil.getNalUnitType(arrayOfByte, k);
    int n = k - i;
    if (n > 0) {
      feedNalUnitTargetBuffersData(arrayOfByte, i, k);
    }
    label136:
    long l;
    switch (m)
    {
    default: 
      l = this.pesTimeUs;
      if (n >= 0) {
        break;
      }
    }
    for (int i = -n;; i = 0)
    {
      feedNalUnitTargetEnd(l, i);
      feedNalUnitTargetBuffersStart(m);
      i = k + 3;
      break;
      this.isKeyframe = true;
      break label136;
      int i1 = j - k;
      if (this.foundFirstSample)
      {
        if ((this.ifrParserBuffer != null) && (this.ifrParserBuffer.isCompleted()))
        {
          i = this.ifrParserBuffer.getSliceType();
          int i3 = this.isKeyframe;
          if ((i != 2) && (i != 7)) {
            break label387;
          }
          i = 1;
          label237:
          this.isKeyframe = (i | i3);
          this.ifrParserBuffer.reset();
        }
        if ((this.isKeyframe) && (!this.hasOutputFormat) && (this.sps.isCompleted()) && (this.pps.isCompleted()))
        {
          this.output.format(parseMediaFormat(this.sps, this.pps));
          this.hasOutputFormat = true;
        }
        if (!this.isKeyframe) {
          break label392;
        }
      }
      label387:
      label392:
      for (i = 1;; i = 0)
      {
        int i2 = (int)(this.totalBytesWritten - this.samplePosition);
        this.output.sampleMetadata(this.sampleTimeUs, i, i2 - i1, i1, null);
        this.foundFirstSample = true;
        this.samplePosition = (this.totalBytesWritten - i1);
        this.sampleTimeUs = this.pesTimeUs;
        this.isKeyframe = false;
        break;
        i = 0;
        break label237;
      }
    }
  }
  
  public void packetFinished() {}
  
  public void packetStarted(long paramLong, boolean paramBoolean)
  {
    this.pesTimeUs = paramLong;
  }
  
  public void seek()
  {
    NalUnitUtil.clearPrefixFlags(this.prefixFlags);
    this.sps.reset();
    this.pps.reset();
    this.sei.reset();
    if (this.ifrParserBuffer != null) {
      this.ifrParserBuffer.reset();
    }
    this.foundFirstSample = false;
    this.totalBytesWritten = 0L;
  }
  
  private static final class IfrParserBuffer
  {
    private static final int DEFAULT_BUFFER_SIZE = 128;
    private static final int NOT_SET = -1;
    private byte[] ifrData = new byte[''];
    private int ifrLength;
    private boolean isFilling;
    private final ParsableBitArray scratchSliceType = new ParsableBitArray(this.ifrData);
    private int sliceType;
    
    public IfrParserBuffer()
    {
      reset();
    }
    
    public void appendToNalUnit(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      if (!this.isFilling) {}
      do
      {
        do
        {
          return;
          paramInt2 -= paramInt1;
          if (this.ifrData.length < this.ifrLength + paramInt2) {
            this.ifrData = Arrays.copyOf(this.ifrData, (this.ifrLength + paramInt2) * 2);
          }
          System.arraycopy(paramArrayOfByte, paramInt1, this.ifrData, this.ifrLength, paramInt2);
          this.ifrLength += paramInt2;
          this.scratchSliceType.reset(this.ifrData, this.ifrLength);
          this.scratchSliceType.skipBits(8);
          paramInt1 = this.scratchSliceType.peekExpGolombCodedNumLength();
        } while ((paramInt1 == -1) || (paramInt1 > this.scratchSliceType.bitsLeft()));
        this.scratchSliceType.skipBits(paramInt1);
        paramInt1 = this.scratchSliceType.peekExpGolombCodedNumLength();
      } while ((paramInt1 == -1) || (paramInt1 > this.scratchSliceType.bitsLeft()));
      this.sliceType = this.scratchSliceType.readUnsignedExpGolombCodedInt();
      this.isFilling = false;
    }
    
    public int getSliceType()
    {
      return this.sliceType;
    }
    
    public boolean isCompleted()
    {
      return this.sliceType != -1;
    }
    
    public void reset()
    {
      this.isFilling = false;
      this.ifrLength = 0;
      this.sliceType = -1;
    }
    
    public void startNalUnit(int paramInt)
    {
      if (paramInt == 1)
      {
        reset();
        this.isFilling = true;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/ts/H264Reader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */