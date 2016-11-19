package com.google.android.exoplayer;

import android.net.Uri;
import android.os.SystemClock;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.upstream.Loader;
import com.google.android.exoplayer.upstream.Loader.Callback;
import com.google.android.exoplayer.upstream.Loader.Loadable;
import com.google.android.exoplayer.util.Assertions;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class SingleSampleSource
  implements SampleSource, SampleSource.SampleSourceReader, Loader.Callback, Loader.Loadable
{
  public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT = 3;
  private static final int INITIAL_SAMPLE_SIZE = 1;
  private static final int STATE_END_OF_STREAM = 2;
  private static final int STATE_SEND_FORMAT = 0;
  private static final int STATE_SEND_SAMPLE = 1;
  private IOException currentLoadableException;
  private int currentLoadableExceptionCount;
  private long currentLoadableExceptionTimestamp;
  private final DataSource dataSource;
  private final MediaFormat format;
  private Loader loader;
  private boolean loadingFinished;
  private final int minLoadableRetryCount;
  private long pendingDiscontinuityPositionUs;
  private byte[] sampleData;
  private int sampleSize;
  private int state;
  private final Uri uri;
  
  public SingleSampleSource(Uri paramUri, DataSource paramDataSource, MediaFormat paramMediaFormat)
  {
    this(paramUri, paramDataSource, paramMediaFormat, 3);
  }
  
  public SingleSampleSource(Uri paramUri, DataSource paramDataSource, MediaFormat paramMediaFormat, int paramInt)
  {
    this.uri = paramUri;
    this.dataSource = paramDataSource;
    this.format = paramMediaFormat;
    this.minLoadableRetryCount = paramInt;
    this.sampleData = new byte[1];
  }
  
  private void clearCurrentLoadableException()
  {
    this.currentLoadableException = null;
    this.currentLoadableExceptionCount = 0;
  }
  
  private long getRetryDelayMillis(long paramLong)
  {
    return Math.min((paramLong - 1L) * 1000L, 5000L);
  }
  
  private void maybeStartLoading()
  {
    if ((this.loadingFinished) || (this.state == 2) || (this.loader.isLoading())) {}
    do
    {
      return;
      if (this.currentLoadableException == null) {
        break;
      }
    } while (SystemClock.elapsedRealtime() - this.currentLoadableExceptionTimestamp < getRetryDelayMillis(this.currentLoadableExceptionCount));
    this.currentLoadableException = null;
    this.loader.startLoading(this, this);
  }
  
  public void cancelLoad() {}
  
  public boolean continueBuffering(int paramInt, long paramLong)
  {
    maybeStartLoading();
    return this.loadingFinished;
  }
  
  public void disable(int paramInt)
  {
    this.state = 2;
  }
  
  public void enable(int paramInt, long paramLong)
  {
    this.state = 0;
    this.pendingDiscontinuityPositionUs = Long.MIN_VALUE;
    clearCurrentLoadableException();
    maybeStartLoading();
  }
  
  public long getBufferedPositionUs()
  {
    if (this.loadingFinished) {
      return -3L;
    }
    return 0L;
  }
  
  public MediaFormat getFormat(int paramInt)
  {
    return this.format;
  }
  
  public int getTrackCount()
  {
    return 1;
  }
  
  public boolean isLoadCanceled()
  {
    return false;
  }
  
  public void load()
    throws IOException, InterruptedException
  {
    this.sampleSize = 0;
    try
    {
      this.dataSource.open(new DataSpec(this.uri));
      for (int i = 0; i != -1; i = this.dataSource.read(this.sampleData, this.sampleSize, this.sampleData.length - this.sampleSize))
      {
        this.sampleSize += i;
        if (this.sampleSize == this.sampleData.length) {
          this.sampleData = Arrays.copyOf(this.sampleData, this.sampleData.length * 2);
        }
      }
      return;
    }
    finally
    {
      this.dataSource.close();
    }
  }
  
  public void maybeThrowError()
    throws IOException
  {
    if ((this.currentLoadableException != null) && (this.currentLoadableExceptionCount > this.minLoadableRetryCount)) {
      throw this.currentLoadableException;
    }
  }
  
  public void onLoadCanceled(Loader.Loadable paramLoadable) {}
  
  public void onLoadCompleted(Loader.Loadable paramLoadable)
  {
    this.loadingFinished = true;
    clearCurrentLoadableException();
  }
  
  public void onLoadError(Loader.Loadable paramLoadable, IOException paramIOException)
  {
    this.currentLoadableException = paramIOException;
    this.currentLoadableExceptionCount += 1;
    this.currentLoadableExceptionTimestamp = SystemClock.elapsedRealtime();
    maybeStartLoading();
  }
  
  public boolean prepare(long paramLong)
  {
    if (this.loader == null) {
      this.loader = new Loader("Loader:" + this.format.mimeType);
    }
    return true;
  }
  
  public int readData(int paramInt, long paramLong, MediaFormatHolder paramMediaFormatHolder, SampleHolder paramSampleHolder)
  {
    if (this.state == 2) {
      return -1;
    }
    if (this.state == 0)
    {
      paramMediaFormatHolder.format = this.format;
      this.state = 1;
      return -4;
    }
    if (this.state == 1) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      if (this.loadingFinished) {
        break;
      }
      return -2;
    }
    paramSampleHolder.timeUs = 0L;
    paramSampleHolder.size = this.sampleSize;
    paramSampleHolder.flags = 1;
    paramSampleHolder.ensureSpaceForWrite(paramSampleHolder.size);
    paramSampleHolder.data.put(this.sampleData, 0, this.sampleSize);
    this.state = 2;
    return -3;
  }
  
  public long readDiscontinuity(int paramInt)
  {
    long l = this.pendingDiscontinuityPositionUs;
    this.pendingDiscontinuityPositionUs = Long.MIN_VALUE;
    return l;
  }
  
  public SampleSource.SampleSourceReader register()
  {
    return this;
  }
  
  public void release()
  {
    if (this.loader != null)
    {
      this.loader.release();
      this.loader = null;
    }
  }
  
  public void seekToUs(long paramLong)
  {
    if (this.state == 2)
    {
      this.pendingDiscontinuityPositionUs = paramLong;
      this.state = 1;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/SingleSampleSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */