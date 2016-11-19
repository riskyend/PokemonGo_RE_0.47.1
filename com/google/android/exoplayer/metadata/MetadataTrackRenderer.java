package com.google.android.exoplayer.metadata;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.MediaFormatHolder;
import com.google.android.exoplayer.SampleHolder;
import com.google.android.exoplayer.SampleSource;
import com.google.android.exoplayer.SampleSourceTrackRenderer;
import com.google.android.exoplayer.util.Assertions;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class MetadataTrackRenderer<T>
  extends SampleSourceTrackRenderer
  implements Handler.Callback
{
  private static final int MSG_INVOKE_RENDERER = 0;
  private final MediaFormatHolder formatHolder;
  private boolean inputStreamEnded;
  private final Handler metadataHandler;
  private final MetadataParser<T> metadataParser;
  private final MetadataRenderer<T> metadataRenderer;
  private T pendingMetadata;
  private long pendingMetadataTimestamp;
  private final SampleHolder sampleHolder;
  
  public MetadataTrackRenderer(SampleSource paramSampleSource, MetadataParser<T> paramMetadataParser, MetadataRenderer<T> paramMetadataRenderer, Looper paramLooper)
  {
    super(new SampleSource[] { paramSampleSource });
    this.metadataParser = ((MetadataParser)Assertions.checkNotNull(paramMetadataParser));
    this.metadataRenderer = ((MetadataRenderer)Assertions.checkNotNull(paramMetadataRenderer));
    if (paramLooper == null) {}
    for (paramSampleSource = null;; paramSampleSource = new Handler(paramLooper, this))
    {
      this.metadataHandler = paramSampleSource;
      this.formatHolder = new MediaFormatHolder();
      this.sampleHolder = new SampleHolder(1);
      return;
    }
  }
  
  private void invokeRenderer(T paramT)
  {
    if (this.metadataHandler != null)
    {
      this.metadataHandler.obtainMessage(0, paramT).sendToTarget();
      return;
    }
    invokeRendererInternal(paramT);
  }
  
  private void invokeRendererInternal(T paramT)
  {
    this.metadataRenderer.onMetadata(paramT);
  }
  
  protected void doSomeWork(long paramLong1, long paramLong2, boolean paramBoolean)
    throws ExoPlaybackException
  {
    int i;
    if ((!this.inputStreamEnded) && (this.pendingMetadata == null))
    {
      this.sampleHolder.clearData();
      i = readSource(paramLong1, this.formatHolder, this.sampleHolder);
      if (i != -3) {
        break label126;
      }
      this.pendingMetadataTimestamp = this.sampleHolder.timeUs;
    }
    for (;;)
    {
      try
      {
        this.pendingMetadata = this.metadataParser.parse(this.sampleHolder.data.array(), this.sampleHolder.size);
        if ((this.pendingMetadata != null) && (this.pendingMetadataTimestamp <= paramLong1))
        {
          invokeRenderer(this.pendingMetadata);
          this.pendingMetadata = null;
        }
        return;
      }
      catch (IOException localIOException)
      {
        throw new ExoPlaybackException(localIOException);
      }
      label126:
      if (i == -1) {
        this.inputStreamEnded = true;
      }
    }
  }
  
  protected long getBufferedPositionUs()
  {
    return -3L;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default: 
      return false;
    }
    invokeRendererInternal(paramMessage.obj);
    return true;
  }
  
  protected boolean handlesTrack(MediaFormat paramMediaFormat)
  {
    return this.metadataParser.canParse(paramMediaFormat.mimeType);
  }
  
  protected boolean isEnded()
  {
    return this.inputStreamEnded;
  }
  
  protected boolean isReady()
  {
    return true;
  }
  
  protected void onDisabled()
    throws ExoPlaybackException
  {
    this.pendingMetadata = null;
    super.onDisabled();
  }
  
  protected void onDiscontinuity(long paramLong)
  {
    this.pendingMetadata = null;
    this.inputStreamEnded = false;
  }
  
  public static abstract interface MetadataRenderer<T>
  {
    public abstract void onMetadata(T paramT);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/metadata/MetadataTrackRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */