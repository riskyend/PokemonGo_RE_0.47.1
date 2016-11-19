package com.google.android.exoplayer;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.CodecException;
import android.media.MediaCodec.CryptoException;
import android.media.MediaCodec.CryptoInfo;
import android.media.MediaCrypto;
import android.os.Handler;
import android.os.SystemClock;
import com.google.android.exoplayer.drm.DrmInitData;
import com.google.android.exoplayer.drm.DrmSessionManager;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.NalUnitUtil;
import com.google.android.exoplayer.util.TraceUtil;
import com.google.android.exoplayer.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@TargetApi(16)
public abstract class MediaCodecTrackRenderer
  extends SampleSourceTrackRenderer
{
  private static final long MAX_CODEC_HOTSWAP_TIME_MS = 1000L;
  private static final int RECONFIGURATION_STATE_NONE = 0;
  private static final int RECONFIGURATION_STATE_QUEUE_PENDING = 2;
  private static final int RECONFIGURATION_STATE_WRITE_PENDING = 1;
  private static final int REINITIALIZATION_STATE_NONE = 0;
  private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;
  private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;
  protected static final int SOURCE_STATE_NOT_READY = 0;
  protected static final int SOURCE_STATE_READY = 1;
  protected static final int SOURCE_STATE_READY_READ_MAY_FAIL = 2;
  private MediaCodec codec;
  public final CodecCounters codecCounters;
  private long codecHotswapTimeMs;
  private boolean codecIsAdaptive;
  private boolean codecNeedsDiscardToSpsWorkaround;
  private boolean codecNeedsEosFlushWorkaround;
  private boolean codecNeedsEosPropagationWorkaround;
  private boolean codecNeedsFlushWorkaround;
  private boolean codecNeedsMonoChannelCountWorkaround;
  private boolean codecReceivedBuffers;
  private boolean codecReceivedEos;
  private int codecReconfigurationState;
  private boolean codecReconfigured;
  private int codecReinitializationState;
  private final List<Long> decodeOnlyPresentationTimestamps;
  private final boolean deviceNeedsAutoFrcWorkaround;
  private DrmInitData drmInitData;
  private final DrmSessionManager drmSessionManager;
  protected final Handler eventHandler;
  private final EventListener eventListener;
  private MediaFormat format;
  private final MediaFormatHolder formatHolder;
  private ByteBuffer[] inputBuffers;
  private int inputIndex;
  private boolean inputStreamEnded;
  private final MediaCodecSelector mediaCodecSelector;
  private boolean openedDrmSession;
  private final MediaCodec.BufferInfo outputBufferInfo;
  private ByteBuffer[] outputBuffers;
  private int outputIndex;
  private boolean outputStreamEnded;
  private final boolean playClearSamplesWithoutKeys;
  private final SampleHolder sampleHolder;
  private int sourceState;
  private boolean waitingForFirstSyncFrame;
  private boolean waitingForKeys;
  
  public MediaCodecTrackRenderer(SampleSource paramSampleSource, MediaCodecSelector paramMediaCodecSelector, DrmSessionManager paramDrmSessionManager, boolean paramBoolean, Handler paramHandler, EventListener paramEventListener)
  {
    super(new SampleSource[] { paramSampleSource });
    if (Util.SDK_INT >= 16) {}
    for (;;)
    {
      Assertions.checkState(bool);
      this.mediaCodecSelector = ((MediaCodecSelector)Assertions.checkNotNull(paramMediaCodecSelector));
      this.drmSessionManager = paramDrmSessionManager;
      this.playClearSamplesWithoutKeys = paramBoolean;
      this.eventHandler = paramHandler;
      this.eventListener = paramEventListener;
      this.deviceNeedsAutoFrcWorkaround = deviceNeedsAutoFrcWorkaround();
      this.codecCounters = new CodecCounters();
      this.sampleHolder = new SampleHolder(0);
      this.formatHolder = new MediaFormatHolder();
      this.decodeOnlyPresentationTimestamps = new ArrayList();
      this.outputBufferInfo = new MediaCodec.BufferInfo();
      this.codecReconfigurationState = 0;
      this.codecReinitializationState = 0;
      return;
      bool = false;
    }
  }
  
  private static boolean codecNeedsDiscardToSpsWorkaround(String paramString, MediaFormat paramMediaFormat)
  {
    return (Util.SDK_INT < 21) && (paramMediaFormat.initializationData.isEmpty()) && ("OMX.MTK.VIDEO.DECODER.AVC".equals(paramString));
  }
  
  private static boolean codecNeedsEosFlushWorkaround(String paramString)
  {
    return (Util.SDK_INT <= 23) && ("OMX.google.vorbis.decoder".equals(paramString));
  }
  
  private static boolean codecNeedsEosPropagationWorkaround(String paramString)
  {
    return (Util.SDK_INT <= 17) && ("OMX.rk.video_decoder.avc".equals(paramString));
  }
  
  private static boolean codecNeedsFlushWorkaround(String paramString)
  {
    return (Util.SDK_INT < 18) || ((Util.SDK_INT == 18) && (("OMX.SEC.avc.dec".equals(paramString)) || ("OMX.SEC.avc.dec.secure".equals(paramString)))) || ((Util.SDK_INT == 19) && (Util.MODEL.startsWith("SM-G800")) && (("OMX.Exynos.avc.dec".equals(paramString)) || ("OMX.Exynos.avc.dec.secure".equals(paramString))));
  }
  
  private static boolean codecNeedsMonoChannelCountWorkaround(String paramString, MediaFormat paramMediaFormat)
  {
    return (Util.SDK_INT <= 18) && (paramMediaFormat.channelCount == 1) && ("OMX.MTK.AUDIO.DECODER.MP3".equals(paramString));
  }
  
  private static boolean deviceNeedsAutoFrcWorkaround()
  {
    return (Util.SDK_INT <= 22) && ("foster".equals(Util.DEVICE)) && ("NVIDIA".equals(Util.MANUFACTURER));
  }
  
  private boolean drainOutputBuffer(long paramLong1, long paramLong2)
    throws ExoPlaybackException
  {
    if (this.outputStreamEnded) {
      return false;
    }
    if (this.outputIndex < 0) {
      this.outputIndex = this.codec.dequeueOutputBuffer(this.outputBufferInfo, getDequeueOutputBufferTimeoutUs());
    }
    if (this.outputIndex == -2)
    {
      processOutputFormat();
      return true;
    }
    if (this.outputIndex == -3)
    {
      this.outputBuffers = this.codec.getOutputBuffers();
      localObject = this.codecCounters;
      ((CodecCounters)localObject).outputBuffersChangedCount += 1;
      return true;
    }
    if (this.outputIndex < 0)
    {
      if ((this.codecNeedsEosPropagationWorkaround) && ((this.inputStreamEnded) || (this.codecReinitializationState == 2)))
      {
        processEndOfStream();
        return true;
      }
      return false;
    }
    if ((this.outputBufferInfo.flags & 0x4) != 0)
    {
      processEndOfStream();
      return false;
    }
    int i = getDecodeOnlyIndex(this.outputBufferInfo.presentationTimeUs);
    Object localObject = this.codec;
    ByteBuffer localByteBuffer = this.outputBuffers[this.outputIndex];
    MediaCodec.BufferInfo localBufferInfo = this.outputBufferInfo;
    int j = this.outputIndex;
    if (i != -1) {}
    for (boolean bool = true; processOutputBuffer(paramLong1, paramLong2, (MediaCodec)localObject, localByteBuffer, localBufferInfo, j, bool); bool = false)
    {
      onProcessedOutputBuffer(this.outputBufferInfo.presentationTimeUs);
      if (i != -1) {
        this.decodeOnlyPresentationTimestamps.remove(i);
      }
      this.outputIndex = -1;
      return true;
    }
    return false;
  }
  
  private boolean feedInputBuffer(long paramLong, boolean paramBoolean)
    throws ExoPlaybackException
  {
    if ((this.inputStreamEnded) || (this.codecReinitializationState == 2)) {
      return false;
    }
    if (this.inputIndex < 0)
    {
      this.inputIndex = this.codec.dequeueInputBuffer(0L);
      if (this.inputIndex < 0) {
        return false;
      }
      this.sampleHolder.data = this.inputBuffers[this.inputIndex];
      this.sampleHolder.clearData();
    }
    if (this.codecReinitializationState == 1)
    {
      if (this.codecNeedsEosPropagationWorkaround) {}
      for (;;)
      {
        this.codecReinitializationState = 2;
        return false;
        this.codecReceivedEos = true;
        this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0L, 4);
        this.inputIndex = -1;
      }
    }
    int i;
    if (this.waitingForKeys) {
      i = -3;
    }
    int j;
    while (i == -2)
    {
      return false;
      if (this.codecReconfigurationState == 1)
      {
        i = 0;
        while (i < this.format.initializationData.size())
        {
          byte[] arrayOfByte = (byte[])this.format.initializationData.get(i);
          this.sampleHolder.data.put(arrayOfByte);
          i += 1;
        }
        this.codecReconfigurationState = 2;
      }
      j = readSource(paramLong, this.formatHolder, this.sampleHolder);
      i = j;
      if (paramBoolean)
      {
        i = j;
        if (this.sourceState == 1)
        {
          i = j;
          if (j == -2)
          {
            this.sourceState = 2;
            i = j;
          }
        }
      }
    }
    if (i == -4)
    {
      if (this.codecReconfigurationState == 2)
      {
        this.sampleHolder.clearData();
        this.codecReconfigurationState = 1;
      }
      onInputFormatChanged(this.formatHolder);
      return true;
    }
    if (i == -1)
    {
      if (this.codecReconfigurationState == 2)
      {
        this.sampleHolder.clearData();
        this.codecReconfigurationState = 1;
      }
      this.inputStreamEnded = true;
      if (!this.codecReceivedBuffers)
      {
        processEndOfStream();
        return false;
      }
      try
      {
        if (this.codecNeedsEosPropagationWorkaround) {
          break label664;
        }
        this.codecReceivedEos = true;
        this.codec.queueInputBuffer(this.inputIndex, 0, 0, 0L, 4);
        this.inputIndex = -1;
      }
      catch (MediaCodec.CryptoException localCryptoException1)
      {
        notifyCryptoError(localCryptoException1);
        throw new ExoPlaybackException(localCryptoException1);
      }
    }
    if (this.waitingForFirstSyncFrame)
    {
      if (!this.sampleHolder.isSyncFrame())
      {
        this.sampleHolder.clearData();
        if (this.codecReconfigurationState == 2) {
          this.codecReconfigurationState = 1;
        }
        return true;
      }
      this.waitingForFirstSyncFrame = false;
    }
    paramBoolean = this.sampleHolder.isEncrypted();
    this.waitingForKeys = shouldWaitForKeys(paramBoolean);
    if (this.waitingForKeys) {
      return false;
    }
    if ((this.codecNeedsDiscardToSpsWorkaround) && (!paramBoolean))
    {
      NalUnitUtil.discardToSps(this.sampleHolder.data);
      if (this.sampleHolder.data.position() == 0) {
        return true;
      }
      this.codecNeedsDiscardToSpsWorkaround = false;
    }
    try
    {
      i = this.sampleHolder.data.position();
      j = this.sampleHolder.size;
      paramLong = this.sampleHolder.timeUs;
      if (this.sampleHolder.isDecodeOnly()) {
        this.decodeOnlyPresentationTimestamps.add(Long.valueOf(paramLong));
      }
      if (paramBoolean)
      {
        MediaCodec.CryptoInfo localCryptoInfo = getFrameworkCryptoInfo(this.sampleHolder, i - j);
        this.codec.queueSecureInputBuffer(this.inputIndex, 0, localCryptoInfo, paramLong, 0);
      }
      for (;;)
      {
        this.inputIndex = -1;
        this.codecReceivedBuffers = true;
        this.codecReconfigurationState = 0;
        onQueuedInputBuffer(paramLong);
        return true;
        this.codec.queueInputBuffer(this.inputIndex, 0, i, paramLong, 0);
      }
      return false;
    }
    catch (MediaCodec.CryptoException localCryptoException2)
    {
      notifyCryptoError(localCryptoException2);
      throw new ExoPlaybackException(localCryptoException2);
    }
  }
  
  private void flushCodec()
    throws ExoPlaybackException
  {
    this.codecHotswapTimeMs = -1L;
    this.inputIndex = -1;
    this.outputIndex = -1;
    this.waitingForFirstSyncFrame = true;
    this.waitingForKeys = false;
    this.decodeOnlyPresentationTimestamps.clear();
    if ((this.codecNeedsFlushWorkaround) || ((this.codecNeedsEosFlushWorkaround) && (this.codecReceivedEos)))
    {
      releaseCodec();
      maybeInitCodec();
    }
    for (;;)
    {
      if ((this.codecReconfigured) && (this.format != null)) {
        this.codecReconfigurationState = 1;
      }
      return;
      if (this.codecReinitializationState != 0)
      {
        releaseCodec();
        maybeInitCodec();
      }
      else
      {
        this.codec.flush();
        this.codecReceivedBuffers = false;
      }
    }
  }
  
  private int getDecodeOnlyIndex(long paramLong)
  {
    int j = this.decodeOnlyPresentationTimestamps.size();
    int i = 0;
    while (i < j)
    {
      if (((Long)this.decodeOnlyPresentationTimestamps.get(i)).longValue() == paramLong) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
  
  private static MediaCodec.CryptoInfo getFrameworkCryptoInfo(SampleHolder paramSampleHolder, int paramInt)
  {
    paramSampleHolder = paramSampleHolder.cryptoInfo.getFrameworkCryptoInfoV16();
    if (paramInt == 0) {
      return paramSampleHolder;
    }
    if (paramSampleHolder.numBytesOfClearData == null) {
      paramSampleHolder.numBytesOfClearData = new int[1];
    }
    int[] arrayOfInt = paramSampleHolder.numBytesOfClearData;
    arrayOfInt[0] += paramInt;
    return paramSampleHolder;
  }
  
  private android.media.MediaFormat getFrameworkMediaFormat(MediaFormat paramMediaFormat)
  {
    paramMediaFormat = paramMediaFormat.getFrameworkMediaFormatV16();
    if (this.deviceNeedsAutoFrcWorkaround) {
      paramMediaFormat.setInteger("auto-frc", 0);
    }
    return paramMediaFormat;
  }
  
  private boolean isWithinHotswapPeriod()
  {
    return SystemClock.elapsedRealtime() < this.codecHotswapTimeMs + 1000L;
  }
  
  private void notifyAndThrowDecoderInitError(DecoderInitializationException paramDecoderInitializationException)
    throws ExoPlaybackException
  {
    notifyDecoderInitializationError(paramDecoderInitializationException);
    throw new ExoPlaybackException(paramDecoderInitializationException);
  }
  
  private void notifyCryptoError(final MediaCodec.CryptoException paramCryptoException)
  {
    if ((this.eventHandler != null) && (this.eventListener != null)) {
      this.eventHandler.post(new Runnable()
      {
        public void run()
        {
          MediaCodecTrackRenderer.this.eventListener.onCryptoError(paramCryptoException);
        }
      });
    }
  }
  
  private void notifyDecoderInitializationError(final DecoderInitializationException paramDecoderInitializationException)
  {
    if ((this.eventHandler != null) && (this.eventListener != null)) {
      this.eventHandler.post(new Runnable()
      {
        public void run()
        {
          MediaCodecTrackRenderer.this.eventListener.onDecoderInitializationError(paramDecoderInitializationException);
        }
      });
    }
  }
  
  private void notifyDecoderInitialized(final String paramString, final long paramLong1, long paramLong2)
  {
    if ((this.eventHandler != null) && (this.eventListener != null)) {
      this.eventHandler.post(new Runnable()
      {
        public void run()
        {
          MediaCodecTrackRenderer.this.eventListener.onDecoderInitialized(paramString, paramLong1, this.val$initializationDuration);
        }
      });
    }
  }
  
  private void processEndOfStream()
    throws ExoPlaybackException
  {
    if (this.codecReinitializationState == 2)
    {
      releaseCodec();
      maybeInitCodec();
      return;
    }
    this.outputStreamEnded = true;
    onOutputStreamEnded();
  }
  
  private void processOutputFormat()
    throws ExoPlaybackException
  {
    Object localObject = this.codec.getOutputFormat();
    if (this.codecNeedsMonoChannelCountWorkaround) {
      ((android.media.MediaFormat)localObject).setInteger("channel-count", 1);
    }
    onOutputFormatChanged((android.media.MediaFormat)localObject);
    localObject = this.codecCounters;
    ((CodecCounters)localObject).outputFormatChangedCount += 1;
  }
  
  private void readFormat(long paramLong)
    throws ExoPlaybackException
  {
    if (readSource(paramLong, this.formatHolder, null) == -4) {
      onInputFormatChanged(this.formatHolder);
    }
  }
  
  private boolean shouldWaitForKeys(boolean paramBoolean)
    throws ExoPlaybackException
  {
    if (!this.openedDrmSession) {}
    int i;
    do
    {
      return false;
      i = this.drmSessionManager.getState();
      if (i == 0) {
        throw new ExoPlaybackException(this.drmSessionManager.getError());
      }
    } while ((i == 4) || ((!paramBoolean) && (this.playClearSamplesWithoutKeys)));
    return true;
  }
  
  protected boolean canReconfigureCodec(MediaCodec paramMediaCodec, boolean paramBoolean, MediaFormat paramMediaFormat1, MediaFormat paramMediaFormat2)
  {
    return false;
  }
  
  protected final boolean codecInitialized()
  {
    return this.codec != null;
  }
  
  protected abstract void configureCodec(MediaCodec paramMediaCodec, boolean paramBoolean, android.media.MediaFormat paramMediaFormat, MediaCrypto paramMediaCrypto);
  
  protected void doSomeWork(long paramLong1, long paramLong2, boolean paramBoolean)
    throws ExoPlaybackException
  {
    int i;
    if (paramBoolean) {
      if (this.sourceState == 0) {
        i = 1;
      }
    }
    for (;;)
    {
      this.sourceState = i;
      if (this.format == null) {
        readFormat(paramLong1);
      }
      maybeInitCodec();
      if (this.codec != null)
      {
        TraceUtil.beginSection("drainAndFeed");
        while (drainOutputBuffer(paramLong1, paramLong2)) {}
        while ((feedInputBuffer(paramLong1, true)) && (feedInputBuffer(paramLong1, false))) {}
        TraceUtil.endSection();
      }
      this.codecCounters.ensureUpdated();
      return;
      i = this.sourceState;
      continue;
      i = 0;
    }
  }
  
  protected DecoderInfo getDecoderInfo(MediaCodecSelector paramMediaCodecSelector, String paramString, boolean paramBoolean)
    throws MediaCodecUtil.DecoderQueryException
  {
    return paramMediaCodecSelector.getDecoderInfo(paramString, paramBoolean);
  }
  
  protected long getDequeueOutputBufferTimeoutUs()
  {
    return 0L;
  }
  
  protected final int getSourceState()
  {
    return this.sourceState;
  }
  
  protected abstract boolean handlesTrack(MediaCodecSelector paramMediaCodecSelector, MediaFormat paramMediaFormat)
    throws MediaCodecUtil.DecoderQueryException;
  
  protected final boolean handlesTrack(MediaFormat paramMediaFormat)
    throws MediaCodecUtil.DecoderQueryException
  {
    return handlesTrack(this.mediaCodecSelector, paramMediaFormat);
  }
  
  protected final boolean haveFormat()
  {
    return this.format != null;
  }
  
  protected boolean isEnded()
  {
    return this.outputStreamEnded;
  }
  
  protected boolean isReady()
  {
    return (this.format != null) && (!this.waitingForKeys) && ((this.sourceState != 0) || (this.outputIndex >= 0) || (isWithinHotswapPeriod()));
  }
  
  protected final void maybeInitCodec()
    throws ExoPlaybackException
  {
    if (!shouldInitCodec()) {}
    int i;
    do
    {
      return;
      localObject3 = this.format.mimeType;
      localObject1 = null;
      bool = false;
      if (this.drmInitData == null) {
        break;
      }
      if (this.drmSessionManager == null) {
        throw new ExoPlaybackException("Media requires a DrmSessionManager");
      }
      if (!this.openedDrmSession)
      {
        this.drmSessionManager.open(this.drmInitData);
        this.openedDrmSession = true;
      }
      i = this.drmSessionManager.getState();
      if (i == 0) {
        throw new ExoPlaybackException(this.drmSessionManager.getError());
      }
    } while ((i != 3) && (i != 4));
    localObject1 = this.drmSessionManager.getMediaCrypto();
    bool = this.drmSessionManager.requiresSecureDecoderComponent((String)localObject3);
    localObject2 = null;
    try
    {
      localObject3 = getDecoderInfo(this.mediaCodecSelector, (String)localObject3, bool);
      localObject2 = localObject3;
    }
    catch (MediaCodecUtil.DecoderQueryException localDecoderQueryException)
    {
      try
      {
        l1 = SystemClock.elapsedRealtime();
        TraceUtil.beginSection("createByCodecName(" + (String)localObject3 + ")");
        this.codec = MediaCodec.createByCodecName((String)localObject3);
        TraceUtil.endSection();
        TraceUtil.beginSection("configureCodec");
        configureCodec(this.codec, ((DecoderInfo)localObject2).adaptive, getFrameworkMediaFormat(this.format), (MediaCrypto)localObject1);
        TraceUtil.endSection();
        TraceUtil.beginSection("codec.start()");
        this.codec.start();
        TraceUtil.endSection();
        long l2 = SystemClock.elapsedRealtime();
        notifyDecoderInitialized((String)localObject3, l2, l2 - l1);
        this.inputBuffers = this.codec.getInputBuffers();
        this.outputBuffers = this.codec.getOutputBuffers();
        if (getState() != 3) {
          break label485;
        }
        l1 = SystemClock.elapsedRealtime();
        this.codecHotswapTimeMs = l1;
        this.inputIndex = -1;
        this.outputIndex = -1;
        this.waitingForFirstSyncFrame = true;
        localObject1 = this.codecCounters;
        ((CodecCounters)localObject1).codecInitCount += 1;
        return;
        localDecoderQueryException = localDecoderQueryException;
        notifyAndThrowDecoderInitError(new DecoderInitializationException(this.format, localDecoderQueryException, bool, -49998));
      }
      catch (Exception localException)
      {
        for (;;)
        {
          notifyAndThrowDecoderInitError(new DecoderInitializationException(this.format, localException, bool, localDecoderQueryException));
          continue;
          long l1 = -1L;
        }
      }
    }
    if (localObject2 == null) {
      notifyAndThrowDecoderInitError(new DecoderInitializationException(this.format, null, bool, -49999));
    }
    localObject3 = ((DecoderInfo)localObject2).name;
    this.codecIsAdaptive = ((DecoderInfo)localObject2).adaptive;
    this.codecNeedsDiscardToSpsWorkaround = codecNeedsDiscardToSpsWorkaround((String)localObject3, this.format);
    this.codecNeedsFlushWorkaround = codecNeedsFlushWorkaround((String)localObject3);
    this.codecNeedsEosPropagationWorkaround = codecNeedsEosPropagationWorkaround((String)localObject3);
    this.codecNeedsEosFlushWorkaround = codecNeedsEosFlushWorkaround((String)localObject3);
    this.codecNeedsMonoChannelCountWorkaround = codecNeedsMonoChannelCountWorkaround((String)localObject3, this.format);
  }
  
  /* Error */
  protected void onDisabled()
    throws ExoPlaybackException
  {
    // Byte code:
    //   0: aload_0
    //   1: aconst_null
    //   2: putfield 308	com/google/android/exoplayer/MediaCodecTrackRenderer:format	Lcom/google/android/exoplayer/MediaFormat;
    //   5: aload_0
    //   6: aconst_null
    //   7: putfield 561	com/google/android/exoplayer/MediaCodecTrackRenderer:drmInitData	Lcom/google/android/exoplayer/drm/DrmInitData;
    //   10: aload_0
    //   11: invokevirtual 408	com/google/android/exoplayer/MediaCodecTrackRenderer:releaseCodec	()V
    //   14: aload_0
    //   15: getfield 497	com/google/android/exoplayer/MediaCodecTrackRenderer:openedDrmSession	Z
    //   18: ifeq +17 -> 35
    //   21: aload_0
    //   22: getfield 115	com/google/android/exoplayer/MediaCodecTrackRenderer:drmSessionManager	Lcom/google/android/exoplayer/drm/DrmSessionManager;
    //   25: invokeinterface 650 1 0
    //   30: aload_0
    //   31: iconst_0
    //   32: putfield 497	com/google/android/exoplayer/MediaCodecTrackRenderer:openedDrmSession	Z
    //   35: aload_0
    //   36: invokespecial 652	com/google/android/exoplayer/SampleSourceTrackRenderer:onDisabled	()V
    //   39: return
    //   40: astore_1
    //   41: aload_0
    //   42: invokespecial 652	com/google/android/exoplayer/SampleSourceTrackRenderer:onDisabled	()V
    //   45: aload_1
    //   46: athrow
    //   47: astore_1
    //   48: aload_0
    //   49: getfield 497	com/google/android/exoplayer/MediaCodecTrackRenderer:openedDrmSession	Z
    //   52: ifeq +17 -> 69
    //   55: aload_0
    //   56: getfield 115	com/google/android/exoplayer/MediaCodecTrackRenderer:drmSessionManager	Lcom/google/android/exoplayer/drm/DrmSessionManager;
    //   59: invokeinterface 650 1 0
    //   64: aload_0
    //   65: iconst_0
    //   66: putfield 497	com/google/android/exoplayer/MediaCodecTrackRenderer:openedDrmSession	Z
    //   69: aload_0
    //   70: invokespecial 652	com/google/android/exoplayer/SampleSourceTrackRenderer:onDisabled	()V
    //   73: aload_1
    //   74: athrow
    //   75: astore_1
    //   76: aload_0
    //   77: invokespecial 652	com/google/android/exoplayer/SampleSourceTrackRenderer:onDisabled	()V
    //   80: aload_1
    //   81: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	MediaCodecTrackRenderer
    //   40	6	1	localObject1	Object
    //   47	27	1	localObject2	Object
    //   75	6	1	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   14	35	40	finally
    //   10	14	47	finally
    //   48	69	75	finally
  }
  
  protected void onDiscontinuity(long paramLong)
    throws ExoPlaybackException
  {
    this.sourceState = 0;
    this.inputStreamEnded = false;
    this.outputStreamEnded = false;
    if (this.codec != null) {
      flushCodec();
    }
  }
  
  protected void onInputFormatChanged(MediaFormatHolder paramMediaFormatHolder)
    throws ExoPlaybackException
  {
    MediaFormat localMediaFormat = this.format;
    this.format = paramMediaFormatHolder.format;
    this.drmInitData = paramMediaFormatHolder.drmInitData;
    if ((this.codec != null) && (canReconfigureCodec(this.codec, this.codecIsAdaptive, localMediaFormat, this.format)))
    {
      this.codecReconfigured = true;
      this.codecReconfigurationState = 1;
      return;
    }
    if (this.codecReceivedBuffers)
    {
      this.codecReinitializationState = 1;
      return;
    }
    releaseCodec();
    maybeInitCodec();
  }
  
  protected void onOutputFormatChanged(android.media.MediaFormat paramMediaFormat)
    throws ExoPlaybackException
  {}
  
  protected void onOutputStreamEnded() {}
  
  protected void onProcessedOutputBuffer(long paramLong) {}
  
  protected void onQueuedInputBuffer(long paramLong) {}
  
  protected void onStarted() {}
  
  protected void onStopped() {}
  
  protected abstract boolean processOutputBuffer(long paramLong1, long paramLong2, MediaCodec paramMediaCodec, ByteBuffer paramByteBuffer, MediaCodec.BufferInfo paramBufferInfo, int paramInt, boolean paramBoolean)
    throws ExoPlaybackException;
  
  /* Error */
  protected void releaseCodec()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   4: ifnull +133 -> 137
    //   7: aload_0
    //   8: ldc2_w 395
    //   11: putfield 398	com/google/android/exoplayer/MediaCodecTrackRenderer:codecHotswapTimeMs	J
    //   14: aload_0
    //   15: iconst_m1
    //   16: putfield 286	com/google/android/exoplayer/MediaCodecTrackRenderer:inputIndex	I
    //   19: aload_0
    //   20: iconst_m1
    //   21: putfield 226	com/google/android/exoplayer/MediaCodecTrackRenderer:outputIndex	I
    //   24: aload_0
    //   25: iconst_0
    //   26: putfield 306	com/google/android/exoplayer/MediaCodecTrackRenderer:waitingForKeys	Z
    //   29: aload_0
    //   30: getfield 150	com/google/android/exoplayer/MediaCodecTrackRenderer:decodeOnlyPresentationTimestamps	Ljava/util/List;
    //   33: invokeinterface 401 1 0
    //   38: aload_0
    //   39: aconst_null
    //   40: putfield 291	com/google/android/exoplayer/MediaCodecTrackRenderer:inputBuffers	[Ljava/nio/ByteBuffer;
    //   43: aload_0
    //   44: aconst_null
    //   45: putfield 247	com/google/android/exoplayer/MediaCodecTrackRenderer:outputBuffers	[Ljava/nio/ByteBuffer;
    //   48: aload_0
    //   49: iconst_0
    //   50: putfield 413	com/google/android/exoplayer/MediaCodecTrackRenderer:codecReconfigured	Z
    //   53: aload_0
    //   54: iconst_0
    //   55: putfield 335	com/google/android/exoplayer/MediaCodecTrackRenderer:codecReceivedBuffers	Z
    //   58: aload_0
    //   59: iconst_0
    //   60: putfield 594	com/google/android/exoplayer/MediaCodecTrackRenderer:codecIsAdaptive	Z
    //   63: aload_0
    //   64: iconst_0
    //   65: putfield 356	com/google/android/exoplayer/MediaCodecTrackRenderer:codecNeedsDiscardToSpsWorkaround	Z
    //   68: aload_0
    //   69: iconst_0
    //   70: putfield 403	com/google/android/exoplayer/MediaCodecTrackRenderer:codecNeedsFlushWorkaround	Z
    //   73: aload_0
    //   74: iconst_0
    //   75: putfield 252	com/google/android/exoplayer/MediaCodecTrackRenderer:codecNeedsEosPropagationWorkaround	Z
    //   78: aload_0
    //   79: iconst_0
    //   80: putfield 405	com/google/android/exoplayer/MediaCodecTrackRenderer:codecNeedsEosFlushWorkaround	Z
    //   83: aload_0
    //   84: iconst_0
    //   85: putfield 485	com/google/android/exoplayer/MediaCodecTrackRenderer:codecNeedsMonoChannelCountWorkaround	Z
    //   88: aload_0
    //   89: iconst_0
    //   90: putfield 300	com/google/android/exoplayer/MediaCodecTrackRenderer:codecReceivedEos	Z
    //   93: aload_0
    //   94: iconst_0
    //   95: putfield 157	com/google/android/exoplayer/MediaCodecTrackRenderer:codecReconfigurationState	I
    //   98: aload_0
    //   99: iconst_0
    //   100: putfield 159	com/google/android/exoplayer/MediaCodecTrackRenderer:codecReinitializationState	I
    //   103: aload_0
    //   104: getfield 133	com/google/android/exoplayer/MediaCodecTrackRenderer:codecCounters	Lcom/google/android/exoplayer/CodecCounters;
    //   107: astore_1
    //   108: aload_1
    //   109: aload_1
    //   110: getfield 664	com/google/android/exoplayer/CodecCounters:codecReleaseCount	I
    //   113: iconst_1
    //   114: iadd
    //   115: putfield 664	com/google/android/exoplayer/CodecCounters:codecReleaseCount	I
    //   118: aload_0
    //   119: getfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   122: invokevirtual 667	android/media/MediaCodec:stop	()V
    //   125: aload_0
    //   126: getfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   129: invokevirtual 670	android/media/MediaCodec:release	()V
    //   132: aload_0
    //   133: aconst_null
    //   134: putfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   137: return
    //   138: astore_1
    //   139: aload_0
    //   140: aconst_null
    //   141: putfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   144: aload_1
    //   145: athrow
    //   146: astore_1
    //   147: aload_0
    //   148: getfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   151: invokevirtual 670	android/media/MediaCodec:release	()V
    //   154: aload_0
    //   155: aconst_null
    //   156: putfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   159: aload_1
    //   160: athrow
    //   161: astore_1
    //   162: aload_0
    //   163: aconst_null
    //   164: putfield 228	com/google/android/exoplayer/MediaCodecTrackRenderer:codec	Landroid/media/MediaCodec;
    //   167: aload_1
    //   168: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	169	0	this	MediaCodecTrackRenderer
    //   107	3	1	localCodecCounters	CodecCounters
    //   138	7	1	localObject1	Object
    //   146	14	1	localObject2	Object
    //   161	7	1	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   125	132	138	finally
    //   118	125	146	finally
    //   147	154	161	finally
  }
  
  protected boolean shouldInitCodec()
  {
    return (this.codec == null) && (this.format != null);
  }
  
  public static class DecoderInitializationException
    extends Exception
  {
    private static final int CUSTOM_ERROR_CODE_BASE = -50000;
    private static final int DECODER_QUERY_ERROR = -49998;
    private static final int NO_SUITABLE_DECODER_ERROR = -49999;
    public final String decoderName;
    public final String diagnosticInfo;
    public final String mimeType;
    public final boolean secureDecoderRequired;
    
    public DecoderInitializationException(MediaFormat paramMediaFormat, Throwable paramThrowable, boolean paramBoolean, int paramInt)
    {
      super(paramThrowable);
      this.mimeType = paramMediaFormat.mimeType;
      this.secureDecoderRequired = paramBoolean;
      this.decoderName = null;
      this.diagnosticInfo = buildCustomDiagnosticInfo(paramInt);
    }
    
    public DecoderInitializationException(MediaFormat paramMediaFormat, Throwable paramThrowable, boolean paramBoolean, String paramString)
    {
      super(paramThrowable);
      this.mimeType = paramMediaFormat.mimeType;
      this.secureDecoderRequired = paramBoolean;
      this.decoderName = paramString;
      if (Util.SDK_INT >= 21) {}
      for (paramMediaFormat = getDiagnosticInfoV21(paramThrowable);; paramMediaFormat = null)
      {
        this.diagnosticInfo = paramMediaFormat;
        return;
      }
    }
    
    private static String buildCustomDiagnosticInfo(int paramInt)
    {
      if (paramInt < 0) {}
      for (String str = "neg_";; str = "") {
        return "com.google.android.exoplayer.MediaCodecTrackRenderer_" + str + Math.abs(paramInt);
      }
    }
    
    @TargetApi(21)
    private static String getDiagnosticInfoV21(Throwable paramThrowable)
    {
      if ((paramThrowable instanceof MediaCodec.CodecException)) {
        return ((MediaCodec.CodecException)paramThrowable).getDiagnosticInfo();
      }
      return null;
    }
  }
  
  public static abstract interface EventListener
  {
    public abstract void onCryptoError(MediaCodec.CryptoException paramCryptoException);
    
    public abstract void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException paramDecoderInitializationException);
    
    public abstract void onDecoderInitialized(String paramString, long paramLong1, long paramLong2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/MediaCodecTrackRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */