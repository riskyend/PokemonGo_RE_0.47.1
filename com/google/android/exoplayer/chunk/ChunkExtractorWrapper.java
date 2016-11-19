package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;

public final class ChunkExtractorWrapper
  implements ExtractorOutput, TrackOutput
{
  private final Extractor extractor;
  private boolean extractorInitialized;
  private SingleTrackOutput output;
  private boolean seenTrack;
  
  public ChunkExtractorWrapper(Extractor paramExtractor)
  {
    this.extractor = paramExtractor;
  }
  
  public void drmInitData(DrmInitData paramDrmInitData)
  {
    this.output.drmInitData(paramDrmInitData);
  }
  
  public void endTracks()
  {
    Assertions.checkState(this.seenTrack);
  }
  
  public void format(MediaFormat paramMediaFormat)
  {
    this.output.format(paramMediaFormat);
  }
  
  public void init(SingleTrackOutput paramSingleTrackOutput)
  {
    this.output = paramSingleTrackOutput;
    if (!this.extractorInitialized)
    {
      this.extractor.init(this);
      this.extractorInitialized = true;
      return;
    }
    this.extractor.seek();
  }
  
  public int read(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    boolean bool = true;
    int i = this.extractor.read(paramExtractorInput, null);
    if (i != 1) {}
    for (;;)
    {
      Assertions.checkState(bool);
      return i;
      bool = false;
    }
  }
  
  public int sampleData(ExtractorInput paramExtractorInput, int paramInt, boolean paramBoolean)
    throws IOException, InterruptedException
  {
    return this.output.sampleData(paramExtractorInput, paramInt, paramBoolean);
  }
  
  public void sampleData(ParsableByteArray paramParsableByteArray, int paramInt)
  {
    this.output.sampleData(paramParsableByteArray, paramInt);
  }
  
  public void sampleMetadata(long paramLong, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
  {
    this.output.sampleMetadata(paramLong, paramInt1, paramInt2, paramInt3, paramArrayOfByte);
  }
  
  public void seekMap(SeekMap paramSeekMap)
  {
    this.output.seekMap(paramSeekMap);
  }
  
  public TrackOutput track(int paramInt)
  {
    if (!this.seenTrack) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      this.seenTrack = true;
      return this;
    }
  }
  
  public static abstract interface SingleTrackOutput
    extends TrackOutput
  {
    public abstract void drmInitData(DrmInitData paramDrmInitData);
    
    public abstract void seekMap(SeekMap paramSeekMap);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/ChunkExtractorWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */