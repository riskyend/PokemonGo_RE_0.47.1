package com.google.android.exoplayer.extractor;

import android.net.Uri;
import android.os.SystemClock;
import android.util.SparseArray;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.MediaFormatHolder;
import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.SampleHolder;
import com.google.android.exoplayer.SampleSource;
import com.google.android.exoplayer.SampleSource.SampleSourceReader;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.Loader;
import com.google.android.exoplayer.upstream.Loader.Callback;
import com.google.android.exoplayer.upstream.Loader.Loadable;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ExtractorSampleSource
  implements SampleSource, SampleSource.SampleSourceReader, ExtractorOutput, Loader.Callback
{
  private static final List<Class<? extends Extractor>> DEFAULT_EXTRACTOR_CLASSES = new ArrayList();
  public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT_LIVE = 6;
  public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT_ON_DEMAND = 3;
  private static final int MIN_RETRY_COUNT_DEFAULT_FOR_MEDIA = -1;
  private static final long NO_RESET_PENDING = Long.MIN_VALUE;
  private final Allocator allocator;
  private IOException currentLoadableException;
  private int currentLoadableExceptionCount;
  private long currentLoadableExceptionTimestamp;
  private final DataSource dataSource;
  private long downstreamPositionUs;
  private volatile DrmInitData drmInitData;
  private int enabledTrackCount;
  private int extractedSampleCount;
  private int extractedSampleCountAtStartOfLoad;
  private final ExtractorHolder extractorHolder;
  private boolean havePendingNextSampleUs;
  private long lastSeekPositionUs;
  private ExtractingLoadable loadable;
  private Loader loader;
  private boolean loadingFinished;
  private long maxTrackDurationUs;
  private MediaFormat[] mediaFormats;
  private final int minLoadableRetryCount;
  private boolean[] pendingDiscontinuities;
  private boolean[] pendingMediaFormat;
  private long pendingNextSampleUs;
  private long pendingResetPositionUs;
  private boolean prepared;
  private int remainingReleaseCount;
  private final int requestedBufferSize;
  private final SparseArray<InternalTrackOutput> sampleQueues;
  private long sampleTimeOffsetUs;
  private volatile SeekMap seekMap;
  private boolean[] trackEnabledStates;
  private volatile boolean tracksBuilt;
  private final Uri uri;
  
  static
  {
    try
    {
      DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.webm.WebmExtractor").asSubclass(Extractor.class));
      try
      {
        DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.mp4.FragmentedMp4Extractor").asSubclass(Extractor.class));
        try
        {
          DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.mp4.Mp4Extractor").asSubclass(Extractor.class));
          try
          {
            DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.mp3.Mp3Extractor").asSubclass(Extractor.class));
            try
            {
              DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.ts.AdtsExtractor").asSubclass(Extractor.class));
              try
              {
                DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.ts.TsExtractor").asSubclass(Extractor.class));
                try
                {
                  DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.flv.FlvExtractor").asSubclass(Extractor.class));
                  try
                  {
                    DEFAULT_EXTRACTOR_CLASSES.add(Class.forName("com.google.android.exoplayer.extractor.ts.PsExtractor").asSubclass(Extractor.class));
                    return;
                  }
                  catch (ClassNotFoundException localClassNotFoundException1) {}
                }
                catch (ClassNotFoundException localClassNotFoundException2)
                {
                  for (;;) {}
                }
              }
              catch (ClassNotFoundException localClassNotFoundException3)
              {
                for (;;) {}
              }
            }
            catch (ClassNotFoundException localClassNotFoundException4)
            {
              for (;;) {}
            }
          }
          catch (ClassNotFoundException localClassNotFoundException5)
          {
            for (;;) {}
          }
        }
        catch (ClassNotFoundException localClassNotFoundException6)
        {
          for (;;) {}
        }
      }
      catch (ClassNotFoundException localClassNotFoundException7)
      {
        for (;;) {}
      }
    }
    catch (ClassNotFoundException localClassNotFoundException8)
    {
      for (;;) {}
    }
  }
  
  public ExtractorSampleSource(Uri paramUri, DataSource paramDataSource, Allocator paramAllocator, int paramInt1, int paramInt2, Extractor... paramVarArgs)
  {
    this.uri = paramUri;
    this.dataSource = paramDataSource;
    this.allocator = paramAllocator;
    this.requestedBufferSize = paramInt1;
    this.minLoadableRetryCount = paramInt2;
    if (paramVarArgs != null)
    {
      paramUri = paramVarArgs;
      if (paramVarArgs.length != 0) {}
    }
    else
    {
      paramDataSource = new Extractor[DEFAULT_EXTRACTOR_CLASSES.size()];
      paramInt1 = 0;
      for (;;)
      {
        paramUri = paramDataSource;
        if (paramInt1 < paramDataSource.length) {
          try
          {
            paramDataSource[paramInt1] = ((Extractor)((Class)DEFAULT_EXTRACTOR_CLASSES.get(paramInt1)).newInstance());
            paramInt1 += 1;
          }
          catch (InstantiationException paramUri)
          {
            throw new IllegalStateException("Unexpected error creating default extractor", paramUri);
          }
          catch (IllegalAccessException paramUri)
          {
            throw new IllegalStateException("Unexpected error creating default extractor", paramUri);
          }
        }
      }
    }
    this.extractorHolder = new ExtractorHolder(paramUri, this);
    this.sampleQueues = new SparseArray();
    this.pendingResetPositionUs = Long.MIN_VALUE;
  }
  
  public ExtractorSampleSource(Uri paramUri, DataSource paramDataSource, Allocator paramAllocator, int paramInt, Extractor... paramVarArgs)
  {
    this(paramUri, paramDataSource, paramAllocator, paramInt, -1, paramVarArgs);
  }
  
  private void clearState()
  {
    int i = 0;
    while (i < this.sampleQueues.size())
    {
      ((InternalTrackOutput)this.sampleQueues.valueAt(i)).clear();
      i += 1;
    }
    this.loadable = null;
    this.currentLoadableException = null;
    this.currentLoadableExceptionCount = 0;
  }
  
  private ExtractingLoadable createLoadableFromPositionUs(long paramLong)
  {
    return new ExtractingLoadable(this.uri, this.dataSource, this.extractorHolder, this.allocator, this.requestedBufferSize, this.seekMap.getPosition(paramLong));
  }
  
  private ExtractingLoadable createLoadableFromStart()
  {
    return new ExtractingLoadable(this.uri, this.dataSource, this.extractorHolder, this.allocator, this.requestedBufferSize, 0L);
  }
  
  private void discardSamplesForDisabledTracks(long paramLong)
  {
    int i = 0;
    while (i < this.trackEnabledStates.length)
    {
      if (this.trackEnabledStates[i] == 0) {
        ((InternalTrackOutput)this.sampleQueues.valueAt(i)).discardUntil(paramLong);
      }
      i += 1;
    }
  }
  
  private long getRetryDelayMillis(long paramLong)
  {
    return Math.min((paramLong - 1L) * 1000L, 5000L);
  }
  
  private boolean haveFormatsForAllTracks()
  {
    int i = 0;
    while (i < this.sampleQueues.size())
    {
      if (!((InternalTrackOutput)this.sampleQueues.valueAt(i)).hasFormat()) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private boolean isCurrentLoadableExceptionFatal()
  {
    return this.currentLoadableException instanceof UnrecognizedInputFormatException;
  }
  
  private boolean isPendingReset()
  {
    return this.pendingResetPositionUs != Long.MIN_VALUE;
  }
  
  private void maybeStartLoading()
  {
    boolean bool = false;
    if ((this.loadingFinished) || (this.loader.isLoading())) {}
    do
    {
      do
      {
        return;
        if (this.currentLoadableException == null) {
          break;
        }
      } while (isCurrentLoadableExceptionFatal());
      if (this.loadable != null) {
        bool = true;
      }
      Assertions.checkState(bool);
    } while (SystemClock.elapsedRealtime() - this.currentLoadableExceptionTimestamp < getRetryDelayMillis(this.currentLoadableExceptionCount));
    this.currentLoadableException = null;
    int i;
    if (!this.prepared)
    {
      i = 0;
      while (i < this.sampleQueues.size())
      {
        ((InternalTrackOutput)this.sampleQueues.valueAt(i)).clear();
        i += 1;
      }
      this.loadable = createLoadableFromStart();
    }
    for (;;)
    {
      this.extractedSampleCountAtStartOfLoad = this.extractedSampleCount;
      this.loader.startLoading(this.loadable, this);
      return;
      if ((!this.seekMap.isSeekable()) && (this.maxTrackDurationUs == -1L))
      {
        i = 0;
        while (i < this.sampleQueues.size())
        {
          ((InternalTrackOutput)this.sampleQueues.valueAt(i)).clear();
          i += 1;
        }
        this.loadable = createLoadableFromStart();
        this.pendingNextSampleUs = this.downstreamPositionUs;
        this.havePendingNextSampleUs = true;
      }
    }
    this.sampleTimeOffsetUs = 0L;
    this.havePendingNextSampleUs = false;
    if (!this.prepared) {
      this.loadable = createLoadableFromStart();
    }
    for (;;)
    {
      this.extractedSampleCountAtStartOfLoad = this.extractedSampleCount;
      this.loader.startLoading(this.loadable, this);
      return;
      Assertions.checkState(isPendingReset());
      if ((this.maxTrackDurationUs != -1L) && (this.pendingResetPositionUs >= this.maxTrackDurationUs))
      {
        this.loadingFinished = true;
        this.pendingResetPositionUs = Long.MIN_VALUE;
        return;
      }
      this.loadable = createLoadableFromPositionUs(this.pendingResetPositionUs);
      this.pendingResetPositionUs = Long.MIN_VALUE;
    }
  }
  
  private void restartFrom(long paramLong)
  {
    this.pendingResetPositionUs = paramLong;
    this.loadingFinished = false;
    if (this.loader.isLoading())
    {
      this.loader.cancelLoading();
      return;
    }
    clearState();
    maybeStartLoading();
  }
  
  public boolean continueBuffering(int paramInt, long paramLong)
  {
    Assertions.checkState(this.prepared);
    Assertions.checkState(this.trackEnabledStates[paramInt]);
    this.downstreamPositionUs = paramLong;
    discardSamplesForDisabledTracks(this.downstreamPositionUs);
    if (this.loadingFinished) {
      return true;
    }
    maybeStartLoading();
    if (isPendingReset()) {
      return false;
    }
    if (!((InternalTrackOutput)this.sampleQueues.valueAt(paramInt)).isEmpty()) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void disable(int paramInt)
  {
    Assertions.checkState(this.prepared);
    Assertions.checkState(this.trackEnabledStates[paramInt]);
    this.enabledTrackCount -= 1;
    this.trackEnabledStates[paramInt] = false;
    if (this.enabledTrackCount == 0)
    {
      this.downstreamPositionUs = Long.MIN_VALUE;
      if (this.loader.isLoading()) {
        this.loader.cancelLoading();
      }
    }
    else
    {
      return;
    }
    clearState();
    this.allocator.trim(0);
  }
  
  public void drmInitData(DrmInitData paramDrmInitData)
  {
    this.drmInitData = paramDrmInitData;
  }
  
  public void enable(int paramInt, long paramLong)
  {
    Assertions.checkState(this.prepared);
    if (this.trackEnabledStates[paramInt] == 0) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      this.enabledTrackCount += 1;
      this.trackEnabledStates[paramInt] = true;
      this.pendingMediaFormat[paramInt] = true;
      this.pendingDiscontinuities[paramInt] = false;
      if (this.enabledTrackCount == 1)
      {
        if (!this.seekMap.isSeekable()) {
          paramLong = 0L;
        }
        this.downstreamPositionUs = paramLong;
        this.lastSeekPositionUs = paramLong;
        restartFrom(paramLong);
      }
      return;
    }
  }
  
  public void endTracks()
  {
    this.tracksBuilt = true;
  }
  
  public long getBufferedPositionUs()
  {
    long l2;
    if (this.loadingFinished) {
      l2 = -3L;
    }
    long l1;
    do
    {
      return l2;
      if (isPendingReset()) {
        return this.pendingResetPositionUs;
      }
      l1 = Long.MIN_VALUE;
      int i = 0;
      while (i < this.sampleQueues.size())
      {
        l1 = Math.max(l1, ((InternalTrackOutput)this.sampleQueues.valueAt(i)).getLargestParsedTimestampUs());
        i += 1;
      }
      l2 = l1;
    } while (l1 != Long.MIN_VALUE);
    return this.downstreamPositionUs;
  }
  
  public MediaFormat getFormat(int paramInt)
  {
    Assertions.checkState(this.prepared);
    return this.mediaFormats[paramInt];
  }
  
  public int getTrackCount()
  {
    return this.sampleQueues.size();
  }
  
  public void maybeThrowError()
    throws IOException
  {
    if (this.currentLoadableException == null) {
      return;
    }
    if (isCurrentLoadableExceptionFatal()) {
      throw this.currentLoadableException;
    }
    if (this.minLoadableRetryCount != -1)
    {
      i = this.minLoadableRetryCount;
      label33:
      if (this.currentLoadableExceptionCount > i) {
        throw this.currentLoadableException;
      }
    }
    else
    {
      if ((this.seekMap == null) || (this.seekMap.isSeekable())) {
        break label71;
      }
    }
    label71:
    for (int i = 6;; i = 3)
    {
      break label33;
      break;
    }
  }
  
  public void onLoadCanceled(Loader.Loadable paramLoadable)
  {
    if (this.enabledTrackCount > 0)
    {
      restartFrom(this.pendingResetPositionUs);
      return;
    }
    clearState();
    this.allocator.trim(0);
  }
  
  public void onLoadCompleted(Loader.Loadable paramLoadable)
  {
    this.loadingFinished = true;
  }
  
  public void onLoadError(Loader.Loadable paramLoadable, IOException paramIOException)
  {
    this.currentLoadableException = paramIOException;
    if (this.extractedSampleCount > this.extractedSampleCountAtStartOfLoad) {}
    for (int i = 1;; i = this.currentLoadableExceptionCount + 1)
    {
      this.currentLoadableExceptionCount = i;
      this.currentLoadableExceptionTimestamp = SystemClock.elapsedRealtime();
      maybeStartLoading();
      return;
    }
  }
  
  public boolean prepare(long paramLong)
  {
    if (this.prepared) {
      return true;
    }
    if (this.loader == null) {
      this.loader = new Loader("Loader:ExtractorSampleSource");
    }
    maybeStartLoading();
    if ((this.seekMap != null) && (this.tracksBuilt) && (haveFormatsForAllTracks()))
    {
      int j = this.sampleQueues.size();
      this.trackEnabledStates = new boolean[j];
      this.pendingDiscontinuities = new boolean[j];
      this.pendingMediaFormat = new boolean[j];
      this.mediaFormats = new MediaFormat[j];
      this.maxTrackDurationUs = -1L;
      int i = 0;
      while (i < j)
      {
        MediaFormat localMediaFormat = ((InternalTrackOutput)this.sampleQueues.valueAt(i)).getFormat();
        this.mediaFormats[i] = localMediaFormat;
        if ((localMediaFormat.durationUs != -1L) && (localMediaFormat.durationUs > this.maxTrackDurationUs)) {
          this.maxTrackDurationUs = localMediaFormat.durationUs;
        }
        i += 1;
      }
      this.prepared = true;
      return true;
    }
    return false;
  }
  
  public int readData(int paramInt, long paramLong, MediaFormatHolder paramMediaFormatHolder, SampleHolder paramSampleHolder)
  {
    this.downstreamPositionUs = paramLong;
    if ((this.pendingDiscontinuities[paramInt] != 0) || (isPendingReset())) {
      return -2;
    }
    InternalTrackOutput localInternalTrackOutput = (InternalTrackOutput)this.sampleQueues.valueAt(paramInt);
    if (this.pendingMediaFormat[paramInt] != 0)
    {
      paramMediaFormatHolder.format = localInternalTrackOutput.getFormat();
      paramMediaFormatHolder.drmInitData = this.drmInitData;
      this.pendingMediaFormat[paramInt] = false;
      return -4;
    }
    if (localInternalTrackOutput.getSample(paramSampleHolder))
    {
      int i;
      if (paramSampleHolder.timeUs < this.lastSeekPositionUs)
      {
        paramInt = 1;
        i = paramSampleHolder.flags;
        if (paramInt == 0) {
          break label173;
        }
      }
      label173:
      for (paramInt = 134217728;; paramInt = 0)
      {
        paramSampleHolder.flags = (paramInt | i);
        if (this.havePendingNextSampleUs)
        {
          this.sampleTimeOffsetUs = (this.pendingNextSampleUs - paramSampleHolder.timeUs);
          this.havePendingNextSampleUs = false;
        }
        paramSampleHolder.timeUs += this.sampleTimeOffsetUs;
        return -3;
        paramInt = 0;
        break;
      }
    }
    if (this.loadingFinished) {
      return -1;
    }
    return -2;
  }
  
  public long readDiscontinuity(int paramInt)
  {
    if (this.pendingDiscontinuities[paramInt] != 0)
    {
      this.pendingDiscontinuities[paramInt] = false;
      return this.lastSeekPositionUs;
    }
    return Long.MIN_VALUE;
  }
  
  public SampleSource.SampleSourceReader register()
  {
    this.remainingReleaseCount += 1;
    return this;
  }
  
  public void release()
  {
    if (this.remainingReleaseCount > 0) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      int i = this.remainingReleaseCount - 1;
      this.remainingReleaseCount = i;
      if ((i == 0) && (this.loader != null))
      {
        this.loader.release();
        this.loader = null;
      }
      return;
    }
  }
  
  public void seekMap(SeekMap paramSeekMap)
  {
    this.seekMap = paramSeekMap;
  }
  
  public void seekToUs(long paramLong)
  {
    Assertions.checkState(this.prepared);
    boolean bool2;
    long l;
    if (this.enabledTrackCount > 0)
    {
      bool2 = true;
      Assertions.checkState(bool2);
      if (!this.seekMap.isSeekable()) {
        paramLong = 0L;
      }
      if (!isPendingReset()) {
        break label73;
      }
      l = this.pendingResetPositionUs;
      label49:
      this.downstreamPositionUs = paramLong;
      this.lastSeekPositionUs = paramLong;
      if (l != paramLong) {
        break label82;
      }
    }
    for (;;)
    {
      return;
      bool2 = false;
      break;
      label73:
      l = this.downstreamPositionUs;
      break label49;
      label82:
      if (!isPendingReset()) {}
      boolean bool1;
      for (int i = 1;; i = 0)
      {
        int k = 0;
        int j = i;
        i = k;
        while ((j != 0) && (i < this.sampleQueues.size()))
        {
          j &= ((InternalTrackOutput)this.sampleQueues.valueAt(i)).skipToKeyframeBefore(paramLong);
          i += 1;
        }
      }
      if (!bool1) {
        restartFrom(paramLong);
      }
      i = 0;
      while (i < this.pendingDiscontinuities.length)
      {
        this.pendingDiscontinuities[i] = true;
        i += 1;
      }
    }
  }
  
  public TrackOutput track(int paramInt)
  {
    InternalTrackOutput localInternalTrackOutput2 = (InternalTrackOutput)this.sampleQueues.get(paramInt);
    InternalTrackOutput localInternalTrackOutput1 = localInternalTrackOutput2;
    if (localInternalTrackOutput2 == null)
    {
      localInternalTrackOutput1 = new InternalTrackOutput(this.allocator);
      this.sampleQueues.put(paramInt, localInternalTrackOutput1);
    }
    return localInternalTrackOutput1;
  }
  
  private static class ExtractingLoadable
    implements Loader.Loadable
  {
    private final Allocator allocator;
    private final DataSource dataSource;
    private final ExtractorSampleSource.ExtractorHolder extractorHolder;
    private volatile boolean loadCanceled;
    private boolean pendingExtractorSeek;
    private final PositionHolder positionHolder;
    private final int requestedBufferSize;
    private final Uri uri;
    
    public ExtractingLoadable(Uri paramUri, DataSource paramDataSource, ExtractorSampleSource.ExtractorHolder paramExtractorHolder, Allocator paramAllocator, int paramInt, long paramLong)
    {
      this.uri = ((Uri)Assertions.checkNotNull(paramUri));
      this.dataSource = ((DataSource)Assertions.checkNotNull(paramDataSource));
      this.extractorHolder = ((ExtractorSampleSource.ExtractorHolder)Assertions.checkNotNull(paramExtractorHolder));
      this.allocator = ((Allocator)Assertions.checkNotNull(paramAllocator));
      this.requestedBufferSize = paramInt;
      this.positionHolder = new PositionHolder();
      this.positionHolder.position = paramLong;
      this.pendingExtractorSeek = true;
    }
    
    public void cancelLoad()
    {
      this.loadCanceled = true;
    }
    
    public boolean isLoadCanceled()
    {
      return this.loadCanceled;
    }
    
    /* Error */
    public void load()
      throws IOException, InterruptedException
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore_1
      //   2: iload_1
      //   3: ifne +262 -> 265
      //   6: aload_0
      //   7: getfield 68	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:loadCanceled	Z
      //   10: ifne +255 -> 265
      //   13: aload_0
      //   14: getfield 58	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:positionHolder	Lcom/google/android/exoplayer/extractor/PositionHolder;
      //   17: getfield 62	com/google/android/exoplayer/extractor/PositionHolder:position	J
      //   20: lstore 8
      //   22: aload_0
      //   23: getfield 43	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
      //   26: new 77	com/google/android/exoplayer/upstream/DataSpec
      //   29: dup
      //   30: aload_0
      //   31: getfield 39	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:uri	Landroid/net/Uri;
      //   34: lload 8
      //   36: ldc2_w 78
      //   39: aconst_null
      //   40: invokespecial 82	com/google/android/exoplayer/upstream/DataSpec:<init>	(Landroid/net/Uri;JJLjava/lang/String;)V
      //   43: invokeinterface 86 2 0
      //   48: lstore 6
      //   50: lload 6
      //   52: lstore 4
      //   54: lload 6
      //   56: ldc2_w 78
      //   59: lcmp
      //   60: ifeq +10 -> 70
      //   63: lload 6
      //   65: lload 8
      //   67: ladd
      //   68: lstore 4
      //   70: new 88	com/google/android/exoplayer/extractor/DefaultExtractorInput
      //   73: dup
      //   74: aload_0
      //   75: getfield 43	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
      //   78: lload 8
      //   80: lload 4
      //   82: invokespecial 91	com/google/android/exoplayer/extractor/DefaultExtractorInput:<init>	(Lcom/google/android/exoplayer/upstream/DataSource;JJ)V
      //   85: astore 10
      //   87: iload_1
      //   88: istore_3
      //   89: aload_0
      //   90: getfield 47	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:extractorHolder	Lcom/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractorHolder;
      //   93: aload 10
      //   95: invokevirtual 95	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractorHolder:selectExtractor	(Lcom/google/android/exoplayer/extractor/ExtractorInput;)Lcom/google/android/exoplayer/extractor/Extractor;
      //   98: astore 11
      //   100: iload_1
      //   101: istore_2
      //   102: iload_1
      //   103: istore_3
      //   104: aload_0
      //   105: getfield 64	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:pendingExtractorSeek	Z
      //   108: ifeq +21 -> 129
      //   111: iload_1
      //   112: istore_3
      //   113: aload 11
      //   115: invokeinterface 100 1 0
      //   120: iload_1
      //   121: istore_3
      //   122: aload_0
      //   123: iconst_0
      //   124: putfield 64	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:pendingExtractorSeek	Z
      //   127: iload_1
      //   128: istore_2
      //   129: iload_2
      //   130: ifne +46 -> 176
      //   133: iload_2
      //   134: istore_3
      //   135: aload_0
      //   136: getfield 68	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:loadCanceled	Z
      //   139: ifne +37 -> 176
      //   142: iload_2
      //   143: istore_3
      //   144: aload_0
      //   145: getfield 51	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:allocator	Lcom/google/android/exoplayer/upstream/Allocator;
      //   148: aload_0
      //   149: getfield 53	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:requestedBufferSize	I
      //   152: invokeinterface 104 2 0
      //   157: iload_2
      //   158: istore_3
      //   159: aload 11
      //   161: aload 10
      //   163: aload_0
      //   164: getfield 58	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:positionHolder	Lcom/google/android/exoplayer/extractor/PositionHolder;
      //   167: invokeinterface 108 3 0
      //   172: istore_2
      //   173: goto -44 -> 129
      //   176: iload_2
      //   177: iconst_1
      //   178: if_icmpne +17 -> 195
      //   181: iconst_0
      //   182: istore_1
      //   183: aload_0
      //   184: getfield 43	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
      //   187: invokeinterface 111 1 0
      //   192: goto -190 -> 2
      //   195: iload_2
      //   196: istore_1
      //   197: aload 10
      //   199: ifnull -16 -> 183
      //   202: aload_0
      //   203: getfield 58	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:positionHolder	Lcom/google/android/exoplayer/extractor/PositionHolder;
      //   206: aload 10
      //   208: invokeinterface 117 1 0
      //   213: putfield 62	com/google/android/exoplayer/extractor/PositionHolder:position	J
      //   216: iload_2
      //   217: istore_1
      //   218: goto -35 -> 183
      //   221: astore 11
      //   223: aconst_null
      //   224: astore 10
      //   226: iload_1
      //   227: iconst_1
      //   228: if_icmpne +15 -> 243
      //   231: aload_0
      //   232: getfield 43	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:dataSource	Lcom/google/android/exoplayer/upstream/DataSource;
      //   235: invokeinterface 111 1 0
      //   240: aload 11
      //   242: athrow
      //   243: aload 10
      //   245: ifnull -14 -> 231
      //   248: aload_0
      //   249: getfield 58	com/google/android/exoplayer/extractor/ExtractorSampleSource$ExtractingLoadable:positionHolder	Lcom/google/android/exoplayer/extractor/PositionHolder;
      //   252: aload 10
      //   254: invokeinterface 117 1 0
      //   259: putfield 62	com/google/android/exoplayer/extractor/PositionHolder:position	J
      //   262: goto -31 -> 231
      //   265: return
      //   266: astore 11
      //   268: iload_3
      //   269: istore_1
      //   270: goto -44 -> 226
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	273	0	this	ExtractingLoadable
      //   1	269	1	i	int
      //   101	116	2	j	int
      //   88	181	3	k	int
      //   52	29	4	l1	long
      //   48	16	6	l2	long
      //   20	59	8	l3	long
      //   85	168	10	localDefaultExtractorInput	DefaultExtractorInput
      //   98	62	11	localExtractor	Extractor
      //   221	20	11	localObject1	Object
      //   266	1	11	localObject2	Object
      // Exception table:
      //   from	to	target	type
      //   13	50	221	finally
      //   70	87	221	finally
      //   89	100	266	finally
      //   104	111	266	finally
      //   113	120	266	finally
      //   122	127	266	finally
      //   135	142	266	finally
      //   144	157	266	finally
      //   159	173	266	finally
    }
  }
  
  private static final class ExtractorHolder
  {
    private Extractor extractor;
    private final ExtractorOutput extractorOutput;
    private final Extractor[] extractors;
    
    public ExtractorHolder(Extractor[] paramArrayOfExtractor, ExtractorOutput paramExtractorOutput)
    {
      this.extractors = paramArrayOfExtractor;
      this.extractorOutput = paramExtractorOutput;
    }
    
    public Extractor selectExtractor(ExtractorInput paramExtractorInput)
      throws ExtractorSampleSource.UnrecognizedInputFormatException, IOException, InterruptedException
    {
      if (this.extractor != null) {
        return this.extractor;
      }
      Extractor[] arrayOfExtractor = this.extractors;
      int j = arrayOfExtractor.length;
      int i = 0;
      for (;;)
      {
        Extractor localExtractor;
        if (i < j) {
          localExtractor = arrayOfExtractor[i];
        }
        try
        {
          if (localExtractor.sniff(paramExtractorInput))
          {
            this.extractor = localExtractor;
            if (this.extractor != null) {
              break;
            }
            throw new ExtractorSampleSource.UnrecognizedInputFormatException(this.extractors);
          }
        }
        catch (EOFException localEOFException)
        {
          paramExtractorInput.resetPeekPosition();
          i += 1;
        }
      }
      this.extractor.init(this.extractorOutput);
      return this.extractor;
    }
  }
  
  private class InternalTrackOutput
    extends DefaultTrackOutput
  {
    public InternalTrackOutput(Allocator paramAllocator)
    {
      super();
    }
    
    public void sampleMetadata(long paramLong, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
    {
      super.sampleMetadata(paramLong, paramInt1, paramInt2, paramInt3, paramArrayOfByte);
      ExtractorSampleSource.access$008(ExtractorSampleSource.this);
    }
  }
  
  public static final class UnrecognizedInputFormatException
    extends ParserException
  {
    public UnrecognizedInputFormatException(Extractor[] paramArrayOfExtractor)
    {
      super();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/ExtractorSampleSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */