package com.google.android.exoplayer.smoothstreaming;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Base64;
import android.util.SparseArray;
import com.google.android.exoplayer.BehindLiveWindowException;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.chunk.Chunk;
import com.google.android.exoplayer.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer.chunk.ChunkOperationHolder;
import com.google.android.exoplayer.chunk.ChunkSource;
import com.google.android.exoplayer.chunk.ContainerMediaChunk;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.chunk.Format.DecreasingBandwidthComparator;
import com.google.android.exoplayer.chunk.FormatEvaluator;
import com.google.android.exoplayer.chunk.FormatEvaluator.Evaluation;
import com.google.android.exoplayer.chunk.MediaChunk;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.drm.DrmInitData.Mapped;
import com.google.android.exoplayer.drm.DrmInitData.SchemeInitData;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer.extractor.mp4.Track;
import com.google.android.exoplayer.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.CodecSpecificDataUtil;
import com.google.android.exoplayer.util.ManifestFetcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SmoothStreamingChunkSource
  implements ChunkSource, SmoothStreamingTrackSelector.Output
{
  private static final int INITIALIZATION_VECTOR_SIZE = 8;
  private static final int MINIMUM_MANIFEST_REFRESH_PERIOD_MS = 5000;
  private final FormatEvaluator adaptiveFormatEvaluator;
  private SmoothStreamingManifest currentManifest;
  private int currentManifestChunkOffset;
  private final DataSource dataSource;
  private final DrmInitData.Mapped drmInitData;
  private ExposedTrack enabledTrack;
  private final FormatEvaluator.Evaluation evaluation;
  private final SparseArray<ChunkExtractorWrapper> extractorWrappers;
  private IOException fatalError;
  private final boolean live;
  private final long liveEdgeLatencyUs;
  private final ManifestFetcher<SmoothStreamingManifest> manifestFetcher;
  private final SparseArray<MediaFormat> mediaFormats;
  private boolean needManifestRefresh;
  private boolean prepareCalled;
  private final TrackEncryptionBox[] trackEncryptionBoxes;
  private final SmoothStreamingTrackSelector trackSelector;
  private final ArrayList<ExposedTrack> tracks;
  
  public SmoothStreamingChunkSource(SmoothStreamingManifest paramSmoothStreamingManifest, SmoothStreamingTrackSelector paramSmoothStreamingTrackSelector, DataSource paramDataSource, FormatEvaluator paramFormatEvaluator)
  {
    this(null, paramSmoothStreamingManifest, paramSmoothStreamingTrackSelector, paramDataSource, paramFormatEvaluator, 0L);
  }
  
  private SmoothStreamingChunkSource(ManifestFetcher<SmoothStreamingManifest> paramManifestFetcher, SmoothStreamingManifest paramSmoothStreamingManifest, SmoothStreamingTrackSelector paramSmoothStreamingTrackSelector, DataSource paramDataSource, FormatEvaluator paramFormatEvaluator, long paramLong)
  {
    this.manifestFetcher = paramManifestFetcher;
    this.currentManifest = paramSmoothStreamingManifest;
    this.trackSelector = paramSmoothStreamingTrackSelector;
    this.dataSource = paramDataSource;
    this.adaptiveFormatEvaluator = paramFormatEvaluator;
    this.liveEdgeLatencyUs = (1000L * paramLong);
    this.evaluation = new FormatEvaluator.Evaluation();
    this.tracks = new ArrayList();
    this.extractorWrappers = new SparseArray();
    this.mediaFormats = new SparseArray();
    this.live = paramSmoothStreamingManifest.isLive;
    paramManifestFetcher = paramSmoothStreamingManifest.protectionElement;
    if (paramManifestFetcher != null)
    {
      paramSmoothStreamingManifest = getProtectionElementKeyId(paramManifestFetcher.data);
      this.trackEncryptionBoxes = new TrackEncryptionBox[1];
      this.trackEncryptionBoxes[0] = new TrackEncryptionBox(true, 8, paramSmoothStreamingManifest);
      this.drmInitData = new DrmInitData.Mapped();
      this.drmInitData.put(paramManifestFetcher.uuid, new DrmInitData.SchemeInitData("video/mp4", paramManifestFetcher.data));
      return;
    }
    this.trackEncryptionBoxes = null;
    this.drmInitData = null;
  }
  
  public SmoothStreamingChunkSource(ManifestFetcher<SmoothStreamingManifest> paramManifestFetcher, SmoothStreamingTrackSelector paramSmoothStreamingTrackSelector, DataSource paramDataSource, FormatEvaluator paramFormatEvaluator, long paramLong)
  {
    this(paramManifestFetcher, (SmoothStreamingManifest)paramManifestFetcher.getManifest(), paramSmoothStreamingTrackSelector, paramDataSource, paramFormatEvaluator, paramLong);
  }
  
  private static long getLiveSeekPosition(SmoothStreamingManifest paramSmoothStreamingManifest, long paramLong)
  {
    long l1 = Long.MIN_VALUE;
    int i = 0;
    while (i < paramSmoothStreamingManifest.streamElements.length)
    {
      SmoothStreamingManifest.StreamElement localStreamElement = paramSmoothStreamingManifest.streamElements[i];
      long l2 = l1;
      if (localStreamElement.chunkCount > 0) {
        l2 = Math.max(l1, localStreamElement.getStartTimeUs(localStreamElement.chunkCount - 1) + localStreamElement.getChunkDurationUs(localStreamElement.chunkCount - 1));
      }
      i += 1;
      l1 = l2;
    }
    return l1 - paramLong;
  }
  
  private static int getManifestTrackIndex(SmoothStreamingManifest.StreamElement paramStreamElement, Format paramFormat)
  {
    paramStreamElement = paramStreamElement.tracks;
    int i = 0;
    while (i < paramStreamElement.length)
    {
      if (paramStreamElement[i].format.equals(paramFormat)) {
        return i;
      }
      i += 1;
    }
    throw new IllegalStateException("Invalid format: " + paramFormat);
  }
  
  private static int getManifestTrackKey(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 65536) && (paramInt2 <= 65536)) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      return paramInt1 << 16 | paramInt2;
    }
  }
  
  private static byte[] getProtectionElementKeyId(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      localStringBuilder.append((char)paramArrayOfByte[i]);
      i += 2;
    }
    paramArrayOfByte = localStringBuilder.toString();
    paramArrayOfByte = Base64.decode(paramArrayOfByte.substring(paramArrayOfByte.indexOf("<KID>") + 5, paramArrayOfByte.indexOf("</KID>")), 0);
    swap(paramArrayOfByte, 0, 3);
    swap(paramArrayOfByte, 1, 2);
    swap(paramArrayOfByte, 4, 5);
    swap(paramArrayOfByte, 6, 7);
    return paramArrayOfByte;
  }
  
  private MediaFormat initManifestTrack(SmoothStreamingManifest paramSmoothStreamingManifest, int paramInt1, int paramInt2)
  {
    int j = getManifestTrackKey(paramInt1, paramInt2);
    Object localObject1 = (MediaFormat)this.mediaFormats.get(j);
    if (localObject1 != null) {
      return (MediaFormat)localObject1;
    }
    if (this.live) {}
    for (long l1 = -1L;; l1 = paramSmoothStreamingManifest.durationUs)
    {
      localObject1 = paramSmoothStreamingManifest.streamElements[paramInt1];
      localObject2 = localObject1.tracks[paramInt2].format;
      paramSmoothStreamingManifest = localObject1.tracks[paramInt2].csd;
      switch (((SmoothStreamingManifest.StreamElement)localObject1).type)
      {
      default: 
        throw new IllegalStateException("Invalid type: " + ((SmoothStreamingManifest.StreamElement)localObject1).type);
      }
    }
    paramSmoothStreamingManifest = MediaFormat.createVideoFormat(((Format)localObject2).id, ((Format)localObject2).mimeType, ((Format)localObject2).bitrate, -1, l1, ((Format)localObject2).width, ((Format)localObject2).height, Arrays.asList(paramSmoothStreamingManifest));
    paramInt1 = Track.TYPE_vide;
    Object localObject2 = new FragmentedMp4Extractor(3);
    long l2 = ((SmoothStreamingManifest.StreamElement)localObject1).timescale;
    localObject1 = this.trackEncryptionBoxes;
    if (paramInt1 == Track.TYPE_vide) {}
    for (int i = 4;; i = -1)
    {
      ((FragmentedMp4Extractor)localObject2).setTrack(new Track(paramInt2, paramInt1, l2, -1L, l1, paramSmoothStreamingManifest, (TrackEncryptionBox[])localObject1, i, null, null));
      this.mediaFormats.put(j, paramSmoothStreamingManifest);
      this.extractorWrappers.put(j, new ChunkExtractorWrapper((Extractor)localObject2));
      return paramSmoothStreamingManifest;
      if (paramSmoothStreamingManifest != null) {}
      for (paramSmoothStreamingManifest = Arrays.asList(paramSmoothStreamingManifest);; paramSmoothStreamingManifest = Collections.singletonList(CodecSpecificDataUtil.buildAacAudioSpecificConfig(((Format)localObject2).audioSamplingRate, ((Format)localObject2).audioChannels)))
      {
        paramSmoothStreamingManifest = MediaFormat.createAudioFormat(((Format)localObject2).id, ((Format)localObject2).mimeType, ((Format)localObject2).bitrate, -1, l1, ((Format)localObject2).audioChannels, ((Format)localObject2).audioSamplingRate, paramSmoothStreamingManifest, ((Format)localObject2).language);
        paramInt1 = Track.TYPE_soun;
        break;
      }
      paramSmoothStreamingManifest = MediaFormat.createTextFormat(((Format)localObject2).id, ((Format)localObject2).mimeType, ((Format)localObject2).bitrate, l1, ((Format)localObject2).language);
      paramInt1 = Track.TYPE_text;
      break;
    }
  }
  
  private static MediaChunk newMediaChunk(Format paramFormat, Uri paramUri, String paramString, ChunkExtractorWrapper paramChunkExtractorWrapper, DrmInitData paramDrmInitData, DataSource paramDataSource, int paramInt1, long paramLong1, long paramLong2, int paramInt2, MediaFormat paramMediaFormat, int paramInt3, int paramInt4)
  {
    return new ContainerMediaChunk(paramDataSource, new DataSpec(paramUri, 0L, -1L, paramString), paramInt2, paramFormat, paramLong1, paramLong2, paramInt1, paramLong1, paramChunkExtractorWrapper, paramMediaFormat, paramInt3, paramInt4, paramDrmInitData, true, -1);
  }
  
  private static void swap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte[paramInt1];
    paramArrayOfByte[paramInt1] = paramArrayOfByte[paramInt2];
    paramArrayOfByte[paramInt2] = i;
  }
  
  public void adaptiveTrack(SmoothStreamingManifest paramSmoothStreamingManifest, int paramInt, int[] paramArrayOfInt)
  {
    if (this.adaptiveFormatEvaluator == null) {
      return;
    }
    Object localObject = null;
    SmoothStreamingManifest.StreamElement localStreamElement = paramSmoothStreamingManifest.streamElements[paramInt];
    int k = -1;
    int j = -1;
    Format[] arrayOfFormat = new Format[paramArrayOfInt.length];
    int i = 0;
    while (i < arrayOfFormat.length)
    {
      int m = paramArrayOfInt[i];
      arrayOfFormat[i] = localStreamElement.tracks[m].format;
      MediaFormat localMediaFormat = initManifestTrack(paramSmoothStreamingManifest, paramInt, m);
      if ((localObject == null) || (localMediaFormat.height > j)) {
        localObject = localMediaFormat;
      }
      k = Math.max(k, localMediaFormat.width);
      j = Math.max(j, localMediaFormat.height);
      i += 1;
    }
    Arrays.sort(arrayOfFormat, new Format.DecreasingBandwidthComparator());
    paramSmoothStreamingManifest = ((MediaFormat)localObject).copyAsAdaptive(null);
    this.tracks.add(new ExposedTrack(paramSmoothStreamingManifest, paramInt, arrayOfFormat, k, j));
  }
  
  public void continueBuffering(long paramLong)
  {
    if ((this.manifestFetcher == null) || (!this.currentManifest.isLive) || (this.fatalError != null)) {
      return;
    }
    SmoothStreamingManifest localSmoothStreamingManifest = (SmoothStreamingManifest)this.manifestFetcher.getManifest();
    SmoothStreamingManifest.StreamElement localStreamElement1;
    int i;
    SmoothStreamingManifest.StreamElement localStreamElement2;
    if ((this.currentManifest != localSmoothStreamingManifest) && (localSmoothStreamingManifest != null))
    {
      localStreamElement1 = this.currentManifest.streamElements[this.enabledTrack.elementIndex];
      i = localStreamElement1.chunkCount;
      localStreamElement2 = localSmoothStreamingManifest.streamElements[this.enabledTrack.elementIndex];
      if ((i != 0) && (localStreamElement2.chunkCount != 0)) {
        break label155;
      }
      this.currentManifestChunkOffset += i;
    }
    for (;;)
    {
      this.currentManifest = localSmoothStreamingManifest;
      this.needManifestRefresh = false;
      if ((!this.needManifestRefresh) || (SystemClock.elapsedRealtime() <= this.manifestFetcher.getManifestLoadStartTimestamp() + 5000L)) {
        break;
      }
      this.manifestFetcher.requestRefresh();
      return;
      label155:
      paramLong = localStreamElement1.getStartTimeUs(i - 1);
      long l1 = localStreamElement1.getChunkDurationUs(i - 1);
      long l2 = localStreamElement2.getStartTimeUs(0);
      if (paramLong + l1 <= l2) {
        this.currentManifestChunkOffset += i;
      } else {
        this.currentManifestChunkOffset += localStreamElement1.getChunkIndex(l2);
      }
    }
  }
  
  public void disable(List<? extends MediaChunk> paramList)
  {
    if (this.enabledTrack.isAdaptive()) {
      this.adaptiveFormatEvaluator.disable();
    }
    if (this.manifestFetcher != null) {
      this.manifestFetcher.disable();
    }
    this.evaluation.format = null;
    this.fatalError = null;
  }
  
  public void enable(int paramInt)
  {
    this.enabledTrack = ((ExposedTrack)this.tracks.get(paramInt));
    if (this.enabledTrack.isAdaptive()) {
      this.adaptiveFormatEvaluator.enable();
    }
    if (this.manifestFetcher != null) {
      this.manifestFetcher.enable();
    }
  }
  
  public void fixedTrack(SmoothStreamingManifest paramSmoothStreamingManifest, int paramInt1, int paramInt2)
  {
    MediaFormat localMediaFormat = initManifestTrack(paramSmoothStreamingManifest, paramInt1, paramInt2);
    paramSmoothStreamingManifest = paramSmoothStreamingManifest.streamElements[paramInt1].tracks[paramInt2].format;
    this.tracks.add(new ExposedTrack(localMediaFormat, paramInt1, paramSmoothStreamingManifest));
  }
  
  public final void getChunkOperation(List<? extends MediaChunk> paramList, long paramLong, ChunkOperationHolder paramChunkOperationHolder)
  {
    if (this.fatalError != null) {
      paramChunkOperationHolder.chunk = null;
    }
    Format localFormat;
    do
    {
      return;
      this.evaluation.queueSize = paramList.size();
      if (this.enabledTrack.isAdaptive()) {
        this.adaptiveFormatEvaluator.evaluate(paramList, paramLong, this.enabledTrack.adaptiveFormats, this.evaluation);
      }
      for (;;)
      {
        localFormat = this.evaluation.format;
        paramChunkOperationHolder.queueSize = this.evaluation.queueSize;
        if (localFormat != null) {
          break;
        }
        paramChunkOperationHolder.chunk = null;
        return;
        this.evaluation.format = this.enabledTrack.fixedFormat;
        this.evaluation.trigger = 2;
      }
    } while ((paramChunkOperationHolder.queueSize == paramList.size()) && (paramChunkOperationHolder.chunk != null) && (paramChunkOperationHolder.chunk.format.equals(localFormat)));
    paramChunkOperationHolder.chunk = null;
    SmoothStreamingManifest.StreamElement localStreamElement = this.currentManifest.streamElements[this.enabledTrack.elementIndex];
    if (localStreamElement.chunkCount == 0)
    {
      if (this.currentManifest.isLive)
      {
        this.needManifestRefresh = true;
        return;
      }
      paramChunkOperationHolder.endOfStream = true;
      return;
    }
    if (paramList.isEmpty()) {
      if (this.live) {
        paramLong = getLiveSeekPosition(this.currentManifest, this.liveEdgeLatencyUs);
      }
    }
    for (int i = localStreamElement.getChunkIndex(paramLong); (this.live) && (i < 0); i = ((MediaChunk)paramList.get(paramChunkOperationHolder.queueSize - 1)).chunkIndex + 1 - this.currentManifestChunkOffset)
    {
      this.fatalError = new BehindLiveWindowException();
      return;
    }
    int j;
    label368:
    long l;
    if (this.currentManifest.isLive)
    {
      if (i >= localStreamElement.chunkCount)
      {
        this.needManifestRefresh = true;
        return;
      }
      if (i == localStreamElement.chunkCount - 1) {
        this.needManifestRefresh = true;
      }
      if ((this.currentManifest.isLive) || (i != localStreamElement.chunkCount - 1)) {
        break label514;
      }
      j = 1;
      l = localStreamElement.getStartTimeUs(i);
      if (j == 0) {
        break label520;
      }
    }
    label514:
    label520:
    for (paramLong = -1L;; paramLong = l + localStreamElement.getChunkDurationUs(i))
    {
      j = this.currentManifestChunkOffset;
      int k = getManifestTrackIndex(localStreamElement, localFormat);
      int m = getManifestTrackKey(this.enabledTrack.elementIndex, k);
      paramChunkOperationHolder.chunk = newMediaChunk(localFormat, localStreamElement.buildRequestUri(k, i), null, (ChunkExtractorWrapper)this.extractorWrappers.get(m), this.drmInitData, this.dataSource, i + j, l, paramLong, this.evaluation.trigger, (MediaFormat)this.mediaFormats.get(m), this.enabledTrack.adaptiveMaxWidth, this.enabledTrack.adaptiveMaxHeight);
      return;
      if (i < localStreamElement.chunkCount) {
        break;
      }
      paramChunkOperationHolder.endOfStream = true;
      return;
      j = 0;
      break label368;
    }
  }
  
  public final MediaFormat getFormat(int paramInt)
  {
    return ((ExposedTrack)this.tracks.get(paramInt)).trackFormat;
  }
  
  public int getTrackCount()
  {
    return this.tracks.size();
  }
  
  public void maybeThrowError()
    throws IOException
  {
    if (this.fatalError != null) {
      throw this.fatalError;
    }
    this.manifestFetcher.maybeThrowError();
  }
  
  public void onChunkLoadCompleted(Chunk paramChunk) {}
  
  public void onChunkLoadError(Chunk paramChunk, Exception paramException) {}
  
  public boolean prepare()
  {
    if (!this.prepareCalled) {
      this.prepareCalled = true;
    }
    try
    {
      this.trackSelector.selectTracks(this.currentManifest, this);
      if (this.fatalError == null) {
        return true;
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        this.fatalError = localIOException;
      }
    }
    return false;
  }
  
  private static final class ExposedTrack
  {
    private final Format[] adaptiveFormats;
    private final int adaptiveMaxHeight;
    private final int adaptiveMaxWidth;
    private final int elementIndex;
    private final Format fixedFormat;
    public final MediaFormat trackFormat;
    
    public ExposedTrack(MediaFormat paramMediaFormat, int paramInt, Format paramFormat)
    {
      this.trackFormat = paramMediaFormat;
      this.elementIndex = paramInt;
      this.fixedFormat = paramFormat;
      this.adaptiveFormats = null;
      this.adaptiveMaxWidth = -1;
      this.adaptiveMaxHeight = -1;
    }
    
    public ExposedTrack(MediaFormat paramMediaFormat, int paramInt1, Format[] paramArrayOfFormat, int paramInt2, int paramInt3)
    {
      this.trackFormat = paramMediaFormat;
      this.elementIndex = paramInt1;
      this.adaptiveFormats = paramArrayOfFormat;
      this.adaptiveMaxWidth = paramInt2;
      this.adaptiveMaxHeight = paramInt3;
      this.fixedFormat = null;
    }
    
    public boolean isAdaptive()
    {
      return this.adaptiveFormats != null;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/smoothstreaming/SmoothStreamingChunkSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */