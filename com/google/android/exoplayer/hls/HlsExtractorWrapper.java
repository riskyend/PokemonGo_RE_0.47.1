package com.google.android.exoplayer.hls;

import android.util.SparseArray;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.SampleHolder;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.extractor.DefaultTrackOutput;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.MimeTypes;
import java.io.IOException;

public final class HlsExtractorWrapper
  implements ExtractorOutput
{
  private final int adaptiveMaxHeight;
  private final int adaptiveMaxWidth;
  private Allocator allocator;
  private final Extractor extractor;
  public final Format format;
  private boolean prepared;
  private MediaFormat[] sampleQueueFormats;
  private final SparseArray<DefaultTrackOutput> sampleQueues;
  private final boolean shouldSpliceIn;
  private boolean spliceConfigured;
  public final long startTimeUs;
  private volatile boolean tracksBuilt;
  public final int trigger;
  
  public HlsExtractorWrapper(int paramInt1, Format paramFormat, long paramLong, Extractor paramExtractor, boolean paramBoolean, int paramInt2, int paramInt3)
  {
    this.trigger = paramInt1;
    this.format = paramFormat;
    this.startTimeUs = paramLong;
    this.extractor = paramExtractor;
    this.shouldSpliceIn = paramBoolean;
    this.adaptiveMaxWidth = paramInt2;
    this.adaptiveMaxHeight = paramInt3;
    this.sampleQueues = new SparseArray();
  }
  
  public void clear()
  {
    int i = 0;
    while (i < this.sampleQueues.size())
    {
      ((DefaultTrackOutput)this.sampleQueues.valueAt(i)).clear();
      i += 1;
    }
  }
  
  public final void configureSpliceTo(HlsExtractorWrapper paramHlsExtractorWrapper)
  {
    Assertions.checkState(isPrepared());
    if ((this.spliceConfigured) || (!paramHlsExtractorWrapper.shouldSpliceIn) || (!paramHlsExtractorWrapper.isPrepared())) {
      return;
    }
    boolean bool = true;
    int j = getTrackCount();
    int i = 0;
    while (i < j)
    {
      bool &= ((DefaultTrackOutput)this.sampleQueues.valueAt(i)).configureSpliceTo((DefaultTrackOutput)paramHlsExtractorWrapper.sampleQueues.valueAt(i));
      i += 1;
    }
    this.spliceConfigured = bool;
  }
  
  public void discardUntil(int paramInt, long paramLong)
  {
    Assertions.checkState(isPrepared());
    ((DefaultTrackOutput)this.sampleQueues.valueAt(paramInt)).discardUntil(paramLong);
  }
  
  public void drmInitData(DrmInitData paramDrmInitData) {}
  
  public void endTracks()
  {
    this.tracksBuilt = true;
  }
  
  public long getLargestParsedTimestampUs()
  {
    long l = Long.MIN_VALUE;
    int i = 0;
    while (i < this.sampleQueues.size())
    {
      l = Math.max(l, ((DefaultTrackOutput)this.sampleQueues.valueAt(i)).getLargestParsedTimestampUs());
      i += 1;
    }
    return l;
  }
  
  public MediaFormat getMediaFormat(int paramInt)
  {
    Assertions.checkState(isPrepared());
    return this.sampleQueueFormats[paramInt];
  }
  
  public boolean getSample(int paramInt, SampleHolder paramSampleHolder)
  {
    Assertions.checkState(isPrepared());
    return ((DefaultTrackOutput)this.sampleQueues.valueAt(paramInt)).getSample(paramSampleHolder);
  }
  
  public int getTrackCount()
  {
    Assertions.checkState(isPrepared());
    return this.sampleQueues.size();
  }
  
  public boolean hasSamples(int paramInt)
  {
    Assertions.checkState(isPrepared());
    return !((DefaultTrackOutput)this.sampleQueues.valueAt(paramInt)).isEmpty();
  }
  
  public void init(Allocator paramAllocator)
  {
    this.allocator = paramAllocator;
    this.extractor.init(this);
  }
  
  public boolean isPrepared()
  {
    if ((!this.prepared) && (this.tracksBuilt))
    {
      int i = 0;
      while (i < this.sampleQueues.size())
      {
        if (!((DefaultTrackOutput)this.sampleQueues.valueAt(i)).hasFormat()) {
          return false;
        }
        i += 1;
      }
      this.prepared = true;
      this.sampleQueueFormats = new MediaFormat[this.sampleQueues.size()];
      i = 0;
      while (i < this.sampleQueueFormats.length)
      {
        MediaFormat localMediaFormat2 = ((DefaultTrackOutput)this.sampleQueues.valueAt(i)).getFormat();
        MediaFormat localMediaFormat1 = localMediaFormat2;
        if (MimeTypes.isVideo(localMediaFormat2.mimeType)) {
          if (this.adaptiveMaxWidth == -1)
          {
            localMediaFormat1 = localMediaFormat2;
            if (this.adaptiveMaxHeight == -1) {}
          }
          else
          {
            localMediaFormat1 = localMediaFormat2.copyWithMaxVideoDimensions(this.adaptiveMaxWidth, this.adaptiveMaxHeight);
          }
        }
        this.sampleQueueFormats[i] = localMediaFormat1;
        i += 1;
      }
    }
    return this.prepared;
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
  
  public void seekMap(SeekMap paramSeekMap) {}
  
  public TrackOutput track(int paramInt)
  {
    DefaultTrackOutput localDefaultTrackOutput = new DefaultTrackOutput(this.allocator);
    this.sampleQueues.put(paramInt, localDefaultTrackOutput);
    return localDefaultTrackOutput;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsExtractorWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */