package com.google.android.exoplayer.audio;

import android.annotation.TargetApi;
import android.media.AudioTimestamp;
import android.media.MediaFormat;
import android.media.PlaybackParams;
import android.os.ConditionVariable;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.exoplayer.C;
import com.google.android.exoplayer.util.Ac3Util;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.DtsUtil;
import com.google.android.exoplayer.util.Util;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

@TargetApi(16)
public final class AudioTrack
{
  private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
  public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
  private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000L;
  private static final long MAX_BUFFER_DURATION_US = 750000L;
  private static final long MAX_LATENCY_US = 5000000L;
  private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
  private static final long MIN_BUFFER_DURATION_US = 250000L;
  private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
  private static final int MIN_TIMESTAMP_SAMPLE_INTERVAL_US = 500000;
  private static final long PASSTHROUGH_BUFFER_DURATION_US = 250000L;
  public static final int RESULT_BUFFER_CONSUMED = 2;
  public static final int RESULT_POSITION_DISCONTINUITY = 1;
  public static final int SESSION_ID_NOT_SET = 0;
  private static final int START_IN_SYNC = 1;
  private static final int START_NEED_SYNC = 2;
  private static final int START_NOT_SET = 0;
  private static final String TAG = "AudioTrack";
  public static boolean enablePreV21AudioSessionWorkaround = false;
  public static boolean failOnSpuriousAudioTimestamp = false;
  private final AudioCapabilities audioCapabilities;
  private boolean audioTimestampSet;
  private android.media.AudioTrack audioTrack;
  private final AudioTrackUtil audioTrackUtil;
  private int bufferBytesRemaining;
  private int bufferSize;
  private long bufferSizeUs;
  private int channelConfig;
  private int encoding;
  private int framesPerEncodedSample;
  private Method getLatencyMethod;
  private android.media.AudioTrack keepSessionIdAudioTrack;
  private long lastPlayheadSampleTimeUs;
  private long lastTimestampSampleTimeUs;
  private long latencyUs;
  private int nextPlayheadOffsetIndex;
  private boolean passthrough;
  private int pcmFrameSize;
  private int playheadOffsetCount;
  private final long[] playheadOffsets;
  private final ConditionVariable releasingConditionVariable;
  private long resumeSystemTimeUs;
  private int sampleRate;
  private long smoothedPlayheadOffsetUs;
  private int startMediaTimeState;
  private long startMediaTimeUs;
  private final int streamType;
  private long submittedEncodedFrames;
  private long submittedPcmBytes;
  private byte[] temporaryBuffer;
  private int temporaryBufferOffset;
  private float volume;
  
  public AudioTrack()
  {
    this(null, 3);
  }
  
  public AudioTrack(AudioCapabilities paramAudioCapabilities, int paramInt)
  {
    this.audioCapabilities = paramAudioCapabilities;
    this.streamType = paramInt;
    this.releasingConditionVariable = new ConditionVariable(true);
    if (Util.SDK_INT >= 18) {}
    try
    {
      this.getLatencyMethod = android.media.AudioTrack.class.getMethod("getLatency", (Class[])null);
      if (Util.SDK_INT >= 23) {
        this.audioTrackUtil = new AudioTrackUtilV23();
      }
      for (;;)
      {
        this.playheadOffsets = new long[10];
        this.volume = 1.0F;
        this.startMediaTimeState = 0;
        return;
        if (Util.SDK_INT >= 19) {
          this.audioTrackUtil = new AudioTrackUtilV19();
        } else {
          this.audioTrackUtil = new AudioTrackUtil(null);
        }
      }
    }
    catch (NoSuchMethodException paramAudioCapabilities)
    {
      for (;;) {}
    }
  }
  
  private void checkAudioTrackInitialized()
    throws AudioTrack.InitializationException
  {
    int i = this.audioTrack.getState();
    if (i == 1) {
      return;
    }
    try
    {
      this.audioTrack.release();
      this.audioTrack = null;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException = localException;
        this.audioTrack = null;
      }
    }
    finally
    {
      localObject = finally;
      this.audioTrack = null;
      throw ((Throwable)localObject);
    }
    throw new InitializationException(i, this.sampleRate, this.channelConfig, this.bufferSize);
  }
  
  private long durationUsToFrames(long paramLong)
  {
    return this.sampleRate * paramLong / 1000000L;
  }
  
  private long framesToDurationUs(long paramLong)
  {
    return 1000000L * paramLong / this.sampleRate;
  }
  
  private static int getEncodingForMimeType(String paramString)
  {
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        return 0;
        if (paramString.equals("audio/ac3"))
        {
          i = 0;
          continue;
          if (paramString.equals("audio/eac3"))
          {
            i = 1;
            continue;
            if (paramString.equals("audio/vnd.dts"))
            {
              i = 2;
              continue;
              if (paramString.equals("audio/vnd.dts.hd")) {
                i = 3;
              }
            }
          }
        }
        break;
      }
    }
    return 5;
    return 6;
    return 7;
    return 8;
  }
  
  private static int getFramesPerEncodedSample(int paramInt, ByteBuffer paramByteBuffer)
  {
    if ((paramInt == 7) || (paramInt == 8)) {
      return DtsUtil.parseDtsAudioSampleCount(paramByteBuffer);
    }
    if (paramInt == 5) {
      return Ac3Util.getAc3SyncframeAudioSampleCount();
    }
    if (paramInt == 6) {
      return Ac3Util.parseEAc3SyncframeAudioSampleCount(paramByteBuffer);
    }
    throw new IllegalStateException("Unexpected audio encoding: " + paramInt);
  }
  
  private long getSubmittedFrames()
  {
    if (this.passthrough) {
      return this.submittedEncodedFrames;
    }
    return pcmBytesToFrames(this.submittedPcmBytes);
  }
  
  private boolean hasCurrentPositionUs()
  {
    return (isInitialized()) && (this.startMediaTimeState != 0);
  }
  
  private void maybeSampleSyncParams()
  {
    long l1 = this.audioTrackUtil.getPlaybackHeadPositionUs();
    if (l1 == 0L) {}
    long l2;
    do
    {
      return;
      l2 = System.nanoTime() / 1000L;
      if (l2 - this.lastPlayheadSampleTimeUs >= 30000L)
      {
        this.playheadOffsets[this.nextPlayheadOffsetIndex] = (l1 - l2);
        this.nextPlayheadOffsetIndex = ((this.nextPlayheadOffsetIndex + 1) % 10);
        if (this.playheadOffsetCount < 10) {
          this.playheadOffsetCount += 1;
        }
        this.lastPlayheadSampleTimeUs = l2;
        this.smoothedPlayheadOffsetUs = 0L;
        int i = 0;
        while (i < this.playheadOffsetCount)
        {
          this.smoothedPlayheadOffsetUs += this.playheadOffsets[i] / this.playheadOffsetCount;
          i += 1;
        }
      }
    } while ((needsPassthroughWorkarounds()) || (l2 - this.lastTimestampSampleTimeUs < 500000L));
    this.audioTimestampSet = this.audioTrackUtil.updateTimestamp();
    long l3;
    long l4;
    if (this.audioTimestampSet)
    {
      l3 = this.audioTrackUtil.getTimestampNanoTime() / 1000L;
      l4 = this.audioTrackUtil.getTimestampFramePosition();
      if (l3 >= this.resumeSystemTimeUs) {
        break label321;
      }
      this.audioTimestampSet = false;
    }
    for (;;)
    {
      if ((this.getLatencyMethod != null) && (!this.passthrough)) {}
      try
      {
        this.latencyUs = (((Integer)this.getLatencyMethod.invoke(this.audioTrack, (Object[])null)).intValue() * 1000L - this.bufferSizeUs);
        this.latencyUs = Math.max(this.latencyUs, 0L);
        if (this.latencyUs > 5000000L)
        {
          Log.w("AudioTrack", "Ignoring impossibly large audio latency: " + this.latencyUs);
          this.latencyUs = 0L;
        }
        this.lastTimestampSampleTimeUs = l2;
        return;
        label321:
        if (Math.abs(l3 - l2) > 5000000L)
        {
          str = "Spurious audio timestamp (system clock mismatch): " + l4 + ", " + l3 + ", " + l2 + ", " + l1;
          if (failOnSpuriousAudioTimestamp) {
            throw new InvalidAudioTrackTimestampException(str);
          }
          Log.w("AudioTrack", str);
          this.audioTimestampSet = false;
          continue;
        }
        if (Math.abs(framesToDurationUs(l4) - l1) <= 5000000L) {
          continue;
        }
        String str = "Spurious audio timestamp (frame position mismatch): " + l4 + ", " + l3 + ", " + l2 + ", " + l1;
        if (failOnSpuriousAudioTimestamp) {
          throw new InvalidAudioTrackTimestampException(str);
        }
        Log.w("AudioTrack", str);
        this.audioTimestampSet = false;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          this.getLatencyMethod = null;
        }
      }
    }
  }
  
  private boolean needsPassthroughWorkarounds()
  {
    return (Util.SDK_INT < 23) && ((this.encoding == 5) || (this.encoding == 6));
  }
  
  private boolean overrideHasPendingData()
  {
    return (needsPassthroughWorkarounds()) && (this.audioTrack.getPlayState() == 2) && (this.audioTrack.getPlaybackHeadPosition() == 0);
  }
  
  private long pcmBytesToFrames(long paramLong)
  {
    return paramLong / this.pcmFrameSize;
  }
  
  private void releaseKeepSessionIdAudioTrack()
  {
    if (this.keepSessionIdAudioTrack == null) {
      return;
    }
    final android.media.AudioTrack localAudioTrack = this.keepSessionIdAudioTrack;
    this.keepSessionIdAudioTrack = null;
    new Thread()
    {
      public void run()
      {
        localAudioTrack.release();
      }
    }.start();
  }
  
  private void resetSyncParams()
  {
    this.smoothedPlayheadOffsetUs = 0L;
    this.playheadOffsetCount = 0;
    this.nextPlayheadOffsetIndex = 0;
    this.lastPlayheadSampleTimeUs = 0L;
    this.audioTimestampSet = false;
    this.lastTimestampSampleTimeUs = 0L;
  }
  
  private void setAudioTrackVolume()
  {
    if (!isInitialized()) {
      return;
    }
    if (Util.SDK_INT >= 21)
    {
      setAudioTrackVolumeV21(this.audioTrack, this.volume);
      return;
    }
    setAudioTrackVolumeV3(this.audioTrack, this.volume);
  }
  
  @TargetApi(21)
  private static void setAudioTrackVolumeV21(android.media.AudioTrack paramAudioTrack, float paramFloat)
  {
    paramAudioTrack.setVolume(paramFloat);
  }
  
  private static void setAudioTrackVolumeV3(android.media.AudioTrack paramAudioTrack, float paramFloat)
  {
    paramAudioTrack.setStereoVolume(paramFloat, paramFloat);
  }
  
  @TargetApi(21)
  private static int writeNonBlockingV21(android.media.AudioTrack paramAudioTrack, ByteBuffer paramByteBuffer, int paramInt)
  {
    return paramAudioTrack.write(paramByteBuffer, paramInt, 1);
  }
  
  public void configure(MediaFormat paramMediaFormat, boolean paramBoolean)
  {
    configure(paramMediaFormat, paramBoolean, 0);
  }
  
  public void configure(MediaFormat paramMediaFormat, boolean paramBoolean, int paramInt)
  {
    int k = paramMediaFormat.getInteger("channel-count");
    int i;
    int m;
    switch (k)
    {
    default: 
      throw new IllegalArgumentException("Unsupported channel count: " + k);
    case 1: 
      i = 4;
      m = paramMediaFormat.getInteger("sample-rate");
      paramMediaFormat = paramMediaFormat.getString("mime");
      if (!paramBoolean) {
        break;
      }
    }
    for (int j = getEncodingForMimeType(paramMediaFormat);; j = 2)
    {
      if ((!isInitialized()) || (this.sampleRate != m) || (this.channelConfig != i) || (this.encoding != j)) {
        break label210;
      }
      return;
      i = 12;
      break;
      i = 28;
      break;
      i = 204;
      break;
      i = 220;
      break;
      i = 252;
      break;
      i = 1276;
      break;
      i = C.CHANNEL_OUT_7POINT1_SURROUND;
      break;
    }
    label210:
    reset();
    this.encoding = j;
    this.passthrough = paramBoolean;
    this.sampleRate = m;
    this.channelConfig = i;
    this.pcmFrameSize = (k * 2);
    if (paramInt != 0)
    {
      this.bufferSize = paramInt;
      if (!paramBoolean) {
        break label413;
      }
    }
    label328:
    label398:
    label413:
    for (long l = -1L;; l = framesToDurationUs(pcmBytesToFrames(this.bufferSize)))
    {
      this.bufferSizeUs = l;
      return;
      if (paramBoolean)
      {
        if ((j == 5) || (j == 6))
        {
          this.bufferSize = 20480;
          break;
        }
        this.bufferSize = 49152;
        break;
      }
      j = android.media.AudioTrack.getMinBufferSize(m, i, j);
      boolean bool;
      if (j != -2)
      {
        bool = true;
        Assertions.checkState(bool);
        paramInt = j * 4;
        i = (int)durationUsToFrames(250000L) * this.pcmFrameSize;
        j = (int)Math.max(j, durationUsToFrames(750000L) * this.pcmFrameSize);
        if (paramInt >= i) {
          break label398;
        }
        paramInt = i;
      }
      for (;;)
      {
        this.bufferSize = paramInt;
        break;
        bool = false;
        break label328;
        if (paramInt > j) {
          paramInt = j;
        }
      }
    }
  }
  
  public int getBufferSize()
  {
    return this.bufferSize;
  }
  
  public long getBufferSizeUs()
  {
    return this.bufferSizeUs;
  }
  
  public long getCurrentPositionUs(boolean paramBoolean)
  {
    long l2;
    if (!hasCurrentPositionUs())
    {
      l2 = Long.MIN_VALUE;
      return l2;
    }
    if (this.audioTrack.getPlayState() == 3) {
      maybeSampleSyncParams();
    }
    long l1 = System.nanoTime() / 1000L;
    if (this.audioTimestampSet)
    {
      l1 = durationUsToFrames(((float)(l1 - this.audioTrackUtil.getTimestampNanoTime() / 1000L) * this.audioTrackUtil.getPlaybackSpeed()));
      return framesToDurationUs(this.audioTrackUtil.getTimestampFramePosition() + l1) + this.startMediaTimeUs;
    }
    if (this.playheadOffsetCount == 0) {}
    for (l1 = this.audioTrackUtil.getPlaybackHeadPositionUs() + this.startMediaTimeUs;; l1 = this.smoothedPlayheadOffsetUs + l1 + this.startMediaTimeUs)
    {
      l2 = l1;
      if (paramBoolean) {
        break;
      }
      return l1 - this.latencyUs;
    }
  }
  
  public int handleBuffer(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, long paramLong)
    throws AudioTrack.WriteException
  {
    if (paramInt2 == 0) {
      paramInt1 = 2;
    }
    int i;
    label153:
    label304:
    label315:
    label455:
    do
    {
      return paramInt1;
      if (needsPassthroughWorkarounds())
      {
        if (this.audioTrack.getPlayState() == 2) {
          return 0;
        }
        if ((this.audioTrack.getPlayState() == 1) && (this.audioTrackUtil.getPlaybackHeadPosition() != 0L)) {
          return 0;
        }
      }
      i = 0;
      int j = 0;
      long l;
      if (this.bufferBytesRemaining == 0)
      {
        this.bufferBytesRemaining = paramInt2;
        paramByteBuffer.position(paramInt1);
        if ((this.passthrough) && (this.framesPerEncodedSample == 0)) {
          this.framesPerEncodedSample = getFramesPerEncodedSample(this.encoding, paramByteBuffer);
        }
        if (!this.passthrough) {
          break label304;
        }
        l = this.framesPerEncodedSample;
        paramLong -= framesToDurationUs(l);
        if (this.startMediaTimeState != 0) {
          break label315;
        }
        this.startMediaTimeUs = Math.max(0L, paramLong);
        this.startMediaTimeState = 1;
        paramInt1 = j;
        i = paramInt1;
        if (Util.SDK_INT < 21)
        {
          if ((this.temporaryBuffer == null) || (this.temporaryBuffer.length < paramInt2)) {
            this.temporaryBuffer = new byte[paramInt2];
          }
          paramByteBuffer.get(this.temporaryBuffer, 0, paramInt2);
          this.temporaryBufferOffset = 0;
          i = paramInt1;
        }
      }
      paramInt1 = 0;
      if (Util.SDK_INT < 21)
      {
        paramInt2 = (int)(this.submittedPcmBytes - this.audioTrackUtil.getPlaybackHeadPosition() * this.pcmFrameSize);
        paramInt2 = this.bufferSize - paramInt2;
        if (paramInt2 > 0)
        {
          paramInt1 = Math.min(this.bufferBytesRemaining, paramInt2);
          paramInt2 = this.audioTrack.write(this.temporaryBuffer, this.temporaryBufferOffset, paramInt1);
          paramInt1 = paramInt2;
          if (paramInt2 >= 0) {
            this.temporaryBufferOffset += paramInt2;
          }
        }
      }
      for (paramInt1 = paramInt2;; paramInt1 = writeNonBlockingV21(this.audioTrack, paramByteBuffer, this.bufferBytesRemaining))
      {
        if (paramInt1 >= 0) {
          break label455;
        }
        throw new WriteException(paramInt1);
        l = pcmBytesToFrames(paramInt2);
        break;
        l = this.startMediaTimeUs + framesToDurationUs(getSubmittedFrames());
        if ((this.startMediaTimeState == 1) && (Math.abs(l - paramLong) > 200000L))
        {
          Log.e("AudioTrack", "Discontinuity detected [expected " + l + ", got " + paramLong + "]");
          this.startMediaTimeState = 2;
        }
        paramInt1 = j;
        if (this.startMediaTimeState != 2) {
          break label153;
        }
        this.startMediaTimeUs += paramLong - l;
        this.startMediaTimeState = 1;
        paramInt1 = 0x0 | 0x1;
        break label153;
      }
      this.bufferBytesRemaining -= paramInt1;
      if (!this.passthrough) {
        this.submittedPcmBytes += paramInt1;
      }
      paramInt1 = i;
    } while (this.bufferBytesRemaining != 0);
    if (this.passthrough) {
      this.submittedEncodedFrames += this.framesPerEncodedSample;
    }
    return i | 0x2;
  }
  
  public void handleDiscontinuity()
  {
    if (this.startMediaTimeState == 1) {
      this.startMediaTimeState = 2;
    }
  }
  
  public void handleEndOfStream()
  {
    if (isInitialized()) {
      this.audioTrackUtil.handleEndOfStream(getSubmittedFrames());
    }
  }
  
  public boolean hasPendingData()
  {
    return (isInitialized()) && ((getSubmittedFrames() > this.audioTrackUtil.getPlaybackHeadPosition()) || (overrideHasPendingData()));
  }
  
  public int initialize()
    throws AudioTrack.InitializationException
  {
    return initialize(0);
  }
  
  public int initialize(int paramInt)
    throws AudioTrack.InitializationException
  {
    this.releasingConditionVariable.block();
    if (paramInt == 0) {}
    for (this.audioTrack = new android.media.AudioTrack(this.streamType, this.sampleRate, this.channelConfig, this.encoding, this.bufferSize, 1);; this.audioTrack = new android.media.AudioTrack(this.streamType, this.sampleRate, this.channelConfig, this.encoding, this.bufferSize, 1, paramInt))
    {
      checkAudioTrackInitialized();
      paramInt = this.audioTrack.getAudioSessionId();
      if ((enablePreV21AudioSessionWorkaround) && (Util.SDK_INT < 21))
      {
        if ((this.keepSessionIdAudioTrack != null) && (paramInt != this.keepSessionIdAudioTrack.getAudioSessionId())) {
          releaseKeepSessionIdAudioTrack();
        }
        if (this.keepSessionIdAudioTrack == null) {
          this.keepSessionIdAudioTrack = new android.media.AudioTrack(this.streamType, 4000, 4, 2, 2, 0, paramInt);
        }
      }
      this.audioTrackUtil.reconfigure(this.audioTrack, needsPassthroughWorkarounds());
      setAudioTrackVolume();
      return paramInt;
    }
  }
  
  public boolean isInitialized()
  {
    return this.audioTrack != null;
  }
  
  public boolean isPassthroughSupported(String paramString)
  {
    return (this.audioCapabilities != null) && (this.audioCapabilities.supportsEncoding(getEncodingForMimeType(paramString)));
  }
  
  public void pause()
  {
    if (isInitialized())
    {
      resetSyncParams();
      this.audioTrackUtil.pause();
    }
  }
  
  public void play()
  {
    if (isInitialized())
    {
      this.resumeSystemTimeUs = (System.nanoTime() / 1000L);
      this.audioTrack.play();
    }
  }
  
  public void release()
  {
    reset();
    releaseKeepSessionIdAudioTrack();
  }
  
  public void reset()
  {
    if (isInitialized())
    {
      this.submittedPcmBytes = 0L;
      this.submittedEncodedFrames = 0L;
      this.framesPerEncodedSample = 0;
      this.bufferBytesRemaining = 0;
      this.startMediaTimeState = 0;
      this.latencyUs = 0L;
      resetSyncParams();
      if (this.audioTrack.getPlayState() == 3) {
        this.audioTrack.pause();
      }
      final android.media.AudioTrack localAudioTrack = this.audioTrack;
      this.audioTrack = null;
      this.audioTrackUtil.reconfigure(null, false);
      this.releasingConditionVariable.close();
      new Thread()
      {
        public void run()
        {
          try
          {
            localAudioTrack.flush();
            localAudioTrack.release();
            return;
          }
          finally
          {
            AudioTrack.this.releasingConditionVariable.open();
          }
        }
      }.start();
    }
  }
  
  public void setPlaybackParams(PlaybackParams paramPlaybackParams)
  {
    this.audioTrackUtil.setPlaybackParameters(paramPlaybackParams);
  }
  
  public void setVolume(float paramFloat)
  {
    if (this.volume != paramFloat)
    {
      this.volume = paramFloat;
      setAudioTrackVolume();
    }
  }
  
  private static class AudioTrackUtil
  {
    protected android.media.AudioTrack audioTrack;
    private long endPlaybackHeadPosition;
    private long lastRawPlaybackHeadPosition;
    private boolean needsPassthroughWorkaround;
    private long passthroughWorkaroundPauseOffset;
    private long rawPlaybackHeadWrapCount;
    private int sampleRate;
    private long stopPlaybackHeadPosition;
    private long stopTimestampUs;
    
    public long getPlaybackHeadPosition()
    {
      long l1 = 0L;
      if (this.stopTimestampUs != -1L)
      {
        l1 = SystemClock.elapsedRealtime();
        l2 = this.stopTimestampUs;
        l1 = this.sampleRate * (l1 * 1000L - l2) / 1000000L;
        l1 = Math.min(this.endPlaybackHeadPosition, this.stopPlaybackHeadPosition + l1);
      }
      int i;
      do
      {
        return l1;
        i = this.audioTrack.getPlayState();
      } while (i == 1);
      long l2 = 0xFFFFFFFF & this.audioTrack.getPlaybackHeadPosition();
      l1 = l2;
      if (this.needsPassthroughWorkaround)
      {
        if ((i == 2) && (l2 == 0L)) {
          this.passthroughWorkaroundPauseOffset = this.lastRawPlaybackHeadPosition;
        }
        l1 = l2 + this.passthroughWorkaroundPauseOffset;
      }
      if (this.lastRawPlaybackHeadPosition > l1) {
        this.rawPlaybackHeadWrapCount += 1L;
      }
      this.lastRawPlaybackHeadPosition = l1;
      return (this.rawPlaybackHeadWrapCount << 32) + l1;
    }
    
    public long getPlaybackHeadPositionUs()
    {
      return getPlaybackHeadPosition() * 1000000L / this.sampleRate;
    }
    
    public float getPlaybackSpeed()
    {
      return 1.0F;
    }
    
    public long getTimestampFramePosition()
    {
      throw new UnsupportedOperationException();
    }
    
    public long getTimestampNanoTime()
    {
      throw new UnsupportedOperationException();
    }
    
    public void handleEndOfStream(long paramLong)
    {
      this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
      this.stopTimestampUs = (SystemClock.elapsedRealtime() * 1000L);
      this.endPlaybackHeadPosition = paramLong;
      this.audioTrack.stop();
    }
    
    public void pause()
    {
      if (this.stopTimestampUs != -1L) {
        return;
      }
      this.audioTrack.pause();
    }
    
    public void reconfigure(android.media.AudioTrack paramAudioTrack, boolean paramBoolean)
    {
      this.audioTrack = paramAudioTrack;
      this.needsPassthroughWorkaround = paramBoolean;
      this.stopTimestampUs = -1L;
      this.lastRawPlaybackHeadPosition = 0L;
      this.rawPlaybackHeadWrapCount = 0L;
      this.passthroughWorkaroundPauseOffset = 0L;
      if (paramAudioTrack != null) {
        this.sampleRate = paramAudioTrack.getSampleRate();
      }
    }
    
    public void setPlaybackParameters(PlaybackParams paramPlaybackParams)
    {
      throw new UnsupportedOperationException();
    }
    
    public boolean updateTimestamp()
    {
      return false;
    }
  }
  
  @TargetApi(19)
  private static class AudioTrackUtilV19
    extends AudioTrack.AudioTrackUtil
  {
    private final AudioTimestamp audioTimestamp = new AudioTimestamp();
    private long lastRawTimestampFramePosition;
    private long lastTimestampFramePosition;
    private long rawTimestampFramePositionWrapCount;
    
    public AudioTrackUtilV19()
    {
      super();
    }
    
    public long getTimestampFramePosition()
    {
      return this.lastTimestampFramePosition;
    }
    
    public long getTimestampNanoTime()
    {
      return this.audioTimestamp.nanoTime;
    }
    
    public void reconfigure(android.media.AudioTrack paramAudioTrack, boolean paramBoolean)
    {
      super.reconfigure(paramAudioTrack, paramBoolean);
      this.rawTimestampFramePositionWrapCount = 0L;
      this.lastRawTimestampFramePosition = 0L;
      this.lastTimestampFramePosition = 0L;
    }
    
    public boolean updateTimestamp()
    {
      boolean bool = this.audioTrack.getTimestamp(this.audioTimestamp);
      if (bool)
      {
        long l = this.audioTimestamp.framePosition;
        if (this.lastRawTimestampFramePosition > l) {
          this.rawTimestampFramePositionWrapCount += 1L;
        }
        this.lastRawTimestampFramePosition = l;
        this.lastTimestampFramePosition = ((this.rawTimestampFramePositionWrapCount << 32) + l);
      }
      return bool;
    }
  }
  
  @TargetApi(23)
  private static class AudioTrackUtilV23
    extends AudioTrack.AudioTrackUtilV19
  {
    private PlaybackParams playbackParams;
    private float playbackSpeed = 1.0F;
    
    private void maybeApplyPlaybackParams()
    {
      if ((this.audioTrack != null) && (this.playbackParams != null)) {
        this.audioTrack.setPlaybackParams(this.playbackParams);
      }
    }
    
    public float getPlaybackSpeed()
    {
      return this.playbackSpeed;
    }
    
    public void reconfigure(android.media.AudioTrack paramAudioTrack, boolean paramBoolean)
    {
      super.reconfigure(paramAudioTrack, paramBoolean);
      maybeApplyPlaybackParams();
    }
    
    public void setPlaybackParameters(PlaybackParams paramPlaybackParams)
    {
      if (paramPlaybackParams != null) {}
      for (;;)
      {
        paramPlaybackParams = paramPlaybackParams.allowDefaults();
        this.playbackParams = paramPlaybackParams;
        this.playbackSpeed = paramPlaybackParams.getSpeed();
        maybeApplyPlaybackParams();
        return;
        paramPlaybackParams = new PlaybackParams();
      }
    }
  }
  
  public static final class InitializationException
    extends Exception
  {
    public final int audioTrackState;
    
    public InitializationException(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super();
      this.audioTrackState = paramInt1;
    }
  }
  
  public static final class InvalidAudioTrackTimestampException
    extends RuntimeException
  {
    public InvalidAudioTrackTimestampException(String paramString)
    {
      super();
    }
  }
  
  public static final class WriteException
    extends Exception
  {
    public final int errorCode;
    
    public WriteException(int paramInt)
    {
      super();
      this.errorCode = paramInt;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/audio/AudioTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */