package com.google.android.exoplayer.extractor.mp4;

import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;

final class TrackFragment
{
  public long auxiliaryDataPosition;
  public long dataPosition;
  public boolean definesEncryptionData;
  public DefaultSampleValues header;
  public int length;
  public int[] sampleCompositionTimeOffsetTable;
  public long[] sampleDecodingTimeTable;
  public ParsableByteArray sampleEncryptionData;
  public int sampleEncryptionDataLength;
  public boolean sampleEncryptionDataNeedsFill;
  public boolean[] sampleHasSubsampleEncryptionTable;
  public boolean[] sampleIsSyncFrameTable;
  public int[] sampleSizeTable;
  
  public void fillEncryptionData(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    paramExtractorInput.readFully(this.sampleEncryptionData.data, 0, this.sampleEncryptionDataLength);
    this.sampleEncryptionData.setPosition(0);
    this.sampleEncryptionDataNeedsFill = false;
  }
  
  public void fillEncryptionData(ParsableByteArray paramParsableByteArray)
  {
    paramParsableByteArray.readBytes(this.sampleEncryptionData.data, 0, this.sampleEncryptionDataLength);
    this.sampleEncryptionData.setPosition(0);
    this.sampleEncryptionDataNeedsFill = false;
  }
  
  public long getSamplePresentationTime(int paramInt)
  {
    return this.sampleDecodingTimeTable[paramInt] + this.sampleCompositionTimeOffsetTable[paramInt];
  }
  
  public void initEncryptionData(int paramInt)
  {
    if ((this.sampleEncryptionData == null) || (this.sampleEncryptionData.limit() < paramInt)) {
      this.sampleEncryptionData = new ParsableByteArray(paramInt);
    }
    this.sampleEncryptionDataLength = paramInt;
    this.definesEncryptionData = true;
    this.sampleEncryptionDataNeedsFill = true;
  }
  
  public void initTables(int paramInt)
  {
    this.length = paramInt;
    if ((this.sampleSizeTable == null) || (this.sampleSizeTable.length < this.length))
    {
      paramInt = paramInt * 125 / 100;
      this.sampleSizeTable = new int[paramInt];
      this.sampleCompositionTimeOffsetTable = new int[paramInt];
      this.sampleDecodingTimeTable = new long[paramInt];
      this.sampleIsSyncFrameTable = new boolean[paramInt];
      this.sampleHasSubsampleEncryptionTable = new boolean[paramInt];
    }
  }
  
  public void reset()
  {
    this.length = 0;
    this.definesEncryptionData = false;
    this.sampleEncryptionDataNeedsFill = false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp4/TrackFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */