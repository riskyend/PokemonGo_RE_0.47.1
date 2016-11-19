package com.google.android.exoplayer.hls;

import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer.BehindLiveWindowException;
import com.google.android.exoplayer.chunk.BaseChunkSampleSourceEventListener;
import com.google.android.exoplayer.chunk.Chunk;
import com.google.android.exoplayer.chunk.ChunkOperationHolder;
import com.google.android.exoplayer.chunk.DataChunk;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.chunk.Format.DecreasingBandwidthComparator;
import com.google.android.exoplayer.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer.extractor.ts.TsExtractor;
import com.google.android.exoplayer.upstream.BandwidthMeter;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.exoplayer.util.UriUtil;
import com.google.android.exoplayer.util.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class HlsChunkSource
  implements HlsTrackSelector.Output
{
  private static final String AAC_FILE_EXTENSION = ".aac";
  public static final int ADAPTIVE_MODE_ABRUPT = 3;
  public static final int ADAPTIVE_MODE_NONE = 0;
  public static final int ADAPTIVE_MODE_SPLICE = 1;
  private static final float BANDWIDTH_FRACTION = 0.8F;
  public static final long DEFAULT_MAX_BUFFER_TO_SWITCH_DOWN_MS = 20000L;
  public static final long DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS = 5000L;
  public static final long DEFAULT_PLAYLIST_BLACKLIST_MS = 60000L;
  private static final String MP3_FILE_EXTENSION = ".mp3";
  private static final String TAG = "HlsChunkSource";
  private static final String VTT_FILE_EXTENSION = ".vtt";
  private static final String WEBVTT_FILE_EXTENSION = ".webvtt";
  private final int adaptiveMode;
  private final BandwidthMeter bandwidthMeter;
  private final String baseUri;
  private final DataSource dataSource;
  private long durationUs;
  private byte[] encryptionIv;
  private String encryptionIvString;
  private byte[] encryptionKey;
  private Uri encryptionKeyUri;
  private IOException fatalError;
  private final boolean isMaster;
  private boolean live;
  private final HlsMasterPlaylist masterPlaylist;
  private final long maxBufferDurationToSwitchDownUs;
  private final long minBufferDurationToSwitchUpUs;
  private final HlsPlaylistParser playlistParser;
  private boolean prepareCalled;
  private byte[] scratchSpace;
  private int selectedTrackIndex;
  private int selectedVariantIndex;
  private final PtsTimestampAdjusterProvider timestampAdjusterProvider;
  private final HlsTrackSelector trackSelector;
  private final ArrayList<ExposedTrack> tracks;
  private long[] variantBlacklistTimes;
  private long[] variantLastPlaylistLoadTimesMs;
  private HlsMediaPlaylist[] variantPlaylists;
  private Variant[] variants;
  
  public HlsChunkSource(boolean paramBoolean, DataSource paramDataSource, String paramString, HlsPlaylist paramHlsPlaylist, HlsTrackSelector paramHlsTrackSelector, BandwidthMeter paramBandwidthMeter, PtsTimestampAdjusterProvider paramPtsTimestampAdjusterProvider, int paramInt)
  {
    this(paramBoolean, paramDataSource, paramString, paramHlsPlaylist, paramHlsTrackSelector, paramBandwidthMeter, paramPtsTimestampAdjusterProvider, paramInt, 5000L, 20000L);
  }
  
  public HlsChunkSource(boolean paramBoolean, DataSource paramDataSource, String paramString, HlsPlaylist paramHlsPlaylist, HlsTrackSelector paramHlsTrackSelector, BandwidthMeter paramBandwidthMeter, PtsTimestampAdjusterProvider paramPtsTimestampAdjusterProvider, int paramInt, long paramLong1, long paramLong2)
  {
    this.isMaster = paramBoolean;
    this.dataSource = paramDataSource;
    this.trackSelector = paramHlsTrackSelector;
    this.bandwidthMeter = paramBandwidthMeter;
    this.timestampAdjusterProvider = paramPtsTimestampAdjusterProvider;
    this.adaptiveMode = paramInt;
    this.minBufferDurationToSwitchUpUs = (1000L * paramLong1);
    this.maxBufferDurationToSwitchDownUs = (1000L * paramLong2);
    this.baseUri = paramHlsPlaylist.baseUri;
    this.playlistParser = new HlsPlaylistParser();
    this.tracks = new ArrayList();
    if (paramHlsPlaylist.type == 0)
    {
      this.masterPlaylist = ((HlsMasterPlaylist)paramHlsPlaylist);
      return;
    }
    paramDataSource = new Format("0", "application/x-mpegURL", -1, -1, -1.0F, -1, -1, -1, null, null);
    paramHlsPlaylist = new ArrayList();
    paramHlsPlaylist.add(new Variant(paramString, paramDataSource));
    this.masterPlaylist = new HlsMasterPlaylist(paramString, paramHlsPlaylist, Collections.emptyList());
  }
  
  private boolean allVariantsBlacklisted()
  {
    int i = 0;
    while (i < this.variantBlacklistTimes.length)
    {
      if (this.variantBlacklistTimes[i] == 0L) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private void clearEncryptionData()
  {
    this.encryptionKeyUri = null;
    this.encryptionKey = null;
    this.encryptionIvString = null;
    this.encryptionIv = null;
  }
  
  private void clearStaleBlacklistedVariants()
  {
    long l = SystemClock.elapsedRealtime();
    int i = 0;
    while (i < this.variantBlacklistTimes.length)
    {
      if ((this.variantBlacklistTimes[i] != 0L) && (l - this.variantBlacklistTimes[i] > 60000L)) {
        this.variantBlacklistTimes[i] = 0L;
      }
      i += 1;
    }
  }
  
  private int getLiveStartChunkMediaSequence(int paramInt)
  {
    HlsMediaPlaylist localHlsMediaPlaylist = this.variantPlaylists[paramInt];
    if (localHlsMediaPlaylist.segments.size() > 3) {}
    for (paramInt = localHlsMediaPlaylist.segments.size() - 3;; paramInt = 0) {
      return localHlsMediaPlaylist.mediaSequence + paramInt;
    }
  }
  
  private int getNextVariantIndex(TsChunk paramTsChunk, long paramLong)
  {
    clearStaleBlacklistedVariants();
    long l = this.bandwidthMeter.getBitrateEstimate();
    int i;
    if (this.variantBlacklistTimes[this.selectedVariantIndex] != 0L)
    {
      i = getVariantIndexForBandwidth(l);
      return i;
    }
    if (paramTsChunk == null) {
      return this.selectedVariantIndex;
    }
    if (l == -1L) {
      return this.selectedVariantIndex;
    }
    int j = getVariantIndexForBandwidth(l);
    if (j == this.selectedVariantIndex) {
      return this.selectedVariantIndex;
    }
    if (this.adaptiveMode == 1) {}
    for (l = paramTsChunk.startTimeUs;; l = paramTsChunk.endTimeUs)
    {
      paramLong = l - paramLong;
      i = j;
      if (this.variantBlacklistTimes[this.selectedVariantIndex] != 0L) {
        break;
      }
      if (j > this.selectedVariantIndex)
      {
        i = j;
        if (paramLong < this.maxBufferDurationToSwitchDownUs) {
          break;
        }
      }
      if (j < this.selectedVariantIndex)
      {
        i = j;
        if (paramLong > this.minBufferDurationToSwitchUpUs) {
          break;
        }
      }
      return this.selectedVariantIndex;
    }
  }
  
  private int getVariantIndex(Format paramFormat)
  {
    int i = 0;
    while (i < this.variants.length)
    {
      if (this.variants[i].format.equals(paramFormat)) {
        return i;
      }
      i += 1;
    }
    throw new IllegalStateException("Invalid format: " + paramFormat);
  }
  
  private int getVariantIndexForBandwidth(long paramLong)
  {
    long l = paramLong;
    if (paramLong == -1L) {
      l = 0L;
    }
    int k = (int)((float)l * 0.8F);
    int j = -1;
    int i = 0;
    while (i < this.variants.length)
    {
      if (this.variantBlacklistTimes[i] == 0L)
      {
        if (this.variants[i].format.bitrate <= k) {
          return i;
        }
        j = i;
      }
      i += 1;
    }
    if (j != -1) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      return j;
    }
  }
  
  private EncryptionKeyChunk newEncryptionKeyChunk(Uri paramUri, String paramString, int paramInt)
  {
    paramUri = new DataSpec(paramUri, 0L, -1L, null, 1);
    return new EncryptionKeyChunk(this.dataSource, paramUri, this.scratchSpace, paramString, paramInt);
  }
  
  private MediaPlaylistChunk newMediaPlaylistChunk(int paramInt)
  {
    Uri localUri = UriUtil.resolveToUri(this.baseUri, this.variants[paramInt].url);
    DataSpec localDataSpec = new DataSpec(localUri, 0L, -1L, null, 1);
    return new MediaPlaylistChunk(this.dataSource, localDataSpec, this.scratchSpace, this.playlistParser, paramInt, localUri.toString());
  }
  
  private void setEncryptionData(Uri paramUri, String paramString, byte[] paramArrayOfByte)
  {
    Object localObject;
    byte[] arrayOfByte;
    if (paramString.toLowerCase(Locale.getDefault()).startsWith("0x"))
    {
      localObject = paramString.substring(2);
      localObject = new BigInteger((String)localObject, 16).toByteArray();
      arrayOfByte = new byte[16];
      if (localObject.length <= 16) {
        break label114;
      }
    }
    label114:
    for (int i = localObject.length - 16;; i = 0)
    {
      System.arraycopy(localObject, i, arrayOfByte, arrayOfByte.length - localObject.length + i, localObject.length - i);
      this.encryptionKeyUri = paramUri;
      this.encryptionKey = paramArrayOfByte;
      this.encryptionIvString = paramString;
      this.encryptionIv = arrayOfByte;
      return;
      localObject = paramString;
      break;
    }
  }
  
  private void setMediaPlaylist(int paramInt, HlsMediaPlaylist paramHlsMediaPlaylist)
  {
    this.variantLastPlaylistLoadTimesMs[paramInt] = SystemClock.elapsedRealtime();
    this.variantPlaylists[paramInt] = paramHlsMediaPlaylist;
    this.live |= paramHlsMediaPlaylist.live;
    if (this.live) {}
    for (long l = -1L;; l = paramHlsMediaPlaylist.durationUs)
    {
      this.durationUs = l;
      return;
    }
  }
  
  private boolean shouldRerequestLiveMediaPlaylist(int paramInt)
  {
    HlsMediaPlaylist localHlsMediaPlaylist = this.variantPlaylists[paramInt];
    return SystemClock.elapsedRealtime() - this.variantLastPlaylistLoadTimesMs[paramInt] >= localHlsMediaPlaylist.targetDurationSecs * 1000 / 2;
  }
  
  public void adaptiveTrack(HlsMasterPlaylist paramHlsMasterPlaylist, Variant[] paramArrayOfVariant)
  {
    Arrays.sort(paramArrayOfVariant, new Comparator()
    {
      private final Comparator<Format> formatComparator = new Format.DecreasingBandwidthComparator();
      
      public int compare(Variant paramAnonymousVariant1, Variant paramAnonymousVariant2)
      {
        return this.formatComparator.compare(paramAnonymousVariant1.format, paramAnonymousVariant2.format);
      }
    });
    int m = 0;
    int k = -1;
    int i = -1;
    int n = Integer.MAX_VALUE;
    int j = 0;
    while (j < paramArrayOfVariant.length)
    {
      int i2 = paramHlsMasterPlaylist.variants.indexOf(paramArrayOfVariant[j]);
      int i1 = n;
      if (i2 < n)
      {
        i1 = i2;
        m = j;
      }
      Format localFormat = paramArrayOfVariant[j].format;
      k = Math.max(localFormat.width, k);
      i = Math.max(localFormat.height, i);
      j += 1;
      n = i1;
    }
    if (k > 0) {
      if (i <= 0) {
        break label152;
      }
    }
    for (;;)
    {
      this.tracks.add(new ExposedTrack(paramArrayOfVariant, m, k, i));
      return;
      k = 1920;
      break;
      label152:
      i = 1080;
    }
  }
  
  public void fixedTrack(HlsMasterPlaylist paramHlsMasterPlaylist, Variant paramVariant)
  {
    this.tracks.add(new ExposedTrack(paramVariant));
  }
  
  public void getChunkOperation(TsChunk paramTsChunk, long paramLong, ChunkOperationHolder paramChunkOperationHolder)
  {
    int k;
    boolean bool;
    Object localObject1;
    if (this.adaptiveMode == 0)
    {
      k = this.selectedVariantIndex;
      bool = false;
      localObject1 = this.variantPlaylists[k];
      if (localObject1 != null) {
        break label94;
      }
      paramChunkOperationHolder.chunk = newMediaPlaylistChunk(k);
    }
    label94:
    int i;
    int j;
    label261:
    do
    {
      return;
      k = getNextVariantIndex(paramTsChunk, paramLong);
      if ((paramTsChunk != null) && (!this.variants[k].format.equals(paramTsChunk.format)) && (this.adaptiveMode == 1)) {}
      for (bool = true;; bool = false) {
        break;
      }
      this.selectedVariantIndex = k;
      if (this.live) {
        if (paramTsChunk == null) {
          i = getLiveStartChunkMediaSequence(k);
        }
      }
      for (;;)
      {
        j = i - ((HlsMediaPlaylist)localObject1).mediaSequence;
        if (j < ((HlsMediaPlaylist)localObject1).segments.size()) {
          break label282;
        }
        if (((HlsMediaPlaylist)localObject1).live) {
          break label261;
        }
        paramChunkOperationHolder.endOfStream = true;
        return;
        if (bool) {}
        for (j = paramTsChunk.chunkIndex;; j = paramTsChunk.chunkIndex + 1)
        {
          i = j;
          if (j >= ((HlsMediaPlaylist)localObject1).mediaSequence) {
            break;
          }
          this.fatalError = new BehindLiveWindowException();
          return;
        }
        if (paramTsChunk != null) {
          break;
        }
        i = Util.binarySearchFloor(((HlsMediaPlaylist)localObject1).segments, Long.valueOf(paramLong), true, true) + ((HlsMediaPlaylist)localObject1).mediaSequence;
      }
      if (bool) {}
      for (i = paramTsChunk.chunkIndex;; i = paramTsChunk.chunkIndex + 1) {
        break;
      }
    } while (!shouldRerequestLiveMediaPlaylist(k));
    paramChunkOperationHolder.chunk = newMediaPlaylistChunk(k);
    return;
    label282:
    HlsMediaPlaylist.Segment localSegment = (HlsMediaPlaylist.Segment)((HlsMediaPlaylist)localObject1).segments.get(j);
    Object localObject2 = UriUtil.resolveToUri(((HlsMediaPlaylist)localObject1).baseUri, localSegment.url);
    label400:
    label437:
    long l;
    Format localFormat;
    if (localSegment.isEncrypted)
    {
      localObject1 = UriUtil.resolveToUri(((HlsMediaPlaylist)localObject1).baseUri, localSegment.encryptionKeyUri);
      if (!((Uri)localObject1).equals(this.encryptionKeyUri))
      {
        paramChunkOperationHolder.chunk = newEncryptionKeyChunk((Uri)localObject1, localSegment.encryptionIV, this.selectedVariantIndex);
        return;
      }
      if (!Util.areEqual(localSegment.encryptionIV, this.encryptionIvString)) {
        setEncryptionData((Uri)localObject1, localSegment.encryptionIV, this.encryptionKey);
      }
      localObject1 = new DataSpec((Uri)localObject2, localSegment.byterangeOffset, localSegment.byterangeLength, null);
      if (!this.live) {
        break label575;
      }
      if (paramTsChunk != null) {
        break label554;
      }
      paramLong = 0L;
      l = (localSegment.durationSecs * 1000000.0D);
      localFormat = this.variants[this.selectedVariantIndex].format;
      localObject2 = ((Uri)localObject2).getLastPathSegment();
      if (!((String)localObject2).endsWith(".aac")) {
        break label584;
      }
      paramTsChunk = new HlsExtractorWrapper(0, localFormat, paramLong, new AdtsExtractor(paramLong), bool, -1, -1);
    }
    for (;;)
    {
      paramChunkOperationHolder.chunk = new TsChunk(this.dataSource, (DataSpec)localObject1, 0, localFormat, paramLong, paramLong + l, i, localSegment.discontinuitySequenceNumber, paramTsChunk, this.encryptionKey, this.encryptionIv);
      return;
      clearEncryptionData();
      break label400;
      label554:
      if (bool)
      {
        paramLong = paramTsChunk.startTimeUs;
        break label437;
      }
      paramLong = paramTsChunk.endTimeUs;
      break label437;
      label575:
      paramLong = localSegment.startTimeUs;
      break label437;
      label584:
      if (((String)localObject2).endsWith(".mp3"))
      {
        paramTsChunk = new HlsExtractorWrapper(0, localFormat, paramLong, new Mp3Extractor(paramLong), bool, -1, -1);
      }
      else
      {
        if ((((String)localObject2).endsWith(".webvtt")) || (((String)localObject2).endsWith(".vtt")))
        {
          paramTsChunk = this.timestampAdjusterProvider.getAdjuster(this.isMaster, localSegment.discontinuitySequenceNumber, paramLong);
          if (paramTsChunk == null) {
            break;
          }
          paramTsChunk = new HlsExtractorWrapper(0, localFormat, paramLong, new WebvttExtractor(paramTsChunk), bool, -1, -1);
          continue;
        }
        if ((paramTsChunk == null) || (paramTsChunk.discontinuitySequenceNumber != localSegment.discontinuitySequenceNumber) || (!localFormat.equals(paramTsChunk.format)))
        {
          paramTsChunk = this.timestampAdjusterProvider.getAdjuster(this.isMaster, localSegment.discontinuitySequenceNumber, paramLong);
          if (paramTsChunk == null) {
            break;
          }
          j = 0;
          k = 0;
          localObject2 = localFormat.codecs;
          if (!TextUtils.isEmpty((CharSequence)localObject2))
          {
            if (MimeTypes.getAudioMediaMimeType((String)localObject2) != "audio/mp4a-latm") {
              k = 0x0 | 0x2;
            }
            j = k;
            if (MimeTypes.getVideoMediaMimeType((String)localObject2) != "video/avc") {
              j = k | 0x4;
            }
          }
          paramTsChunk = new TsExtractor(paramTsChunk, j);
          localObject2 = (ExposedTrack)this.tracks.get(this.selectedTrackIndex);
          paramTsChunk = new HlsExtractorWrapper(0, localFormat, paramLong, paramTsChunk, bool, ((ExposedTrack)localObject2).adaptiveMaxWidth, ((ExposedTrack)localObject2).adaptiveMaxHeight);
          continue;
        }
        paramTsChunk = paramTsChunk.extractorWrapper;
      }
    }
  }
  
  public long getDurationUs()
  {
    return this.durationUs;
  }
  
  public Variant getFixedTrackVariant(int paramInt)
  {
    Variant[] arrayOfVariant = ((ExposedTrack)this.tracks.get(paramInt)).variants;
    if (arrayOfVariant.length == 1) {
      return arrayOfVariant[0];
    }
    return null;
  }
  
  public int getSelectedTrackIndex()
  {
    return this.selectedTrackIndex;
  }
  
  public int getTrackCount()
  {
    return this.tracks.size();
  }
  
  public boolean isLive()
  {
    return this.live;
  }
  
  public void maybeThrowError()
    throws IOException
  {
    if (this.fatalError != null) {
      throw this.fatalError;
    }
  }
  
  public void onChunkLoadCompleted(Chunk paramChunk)
  {
    if ((paramChunk instanceof MediaPlaylistChunk))
    {
      paramChunk = (MediaPlaylistChunk)paramChunk;
      this.scratchSpace = paramChunk.getDataHolder();
      setMediaPlaylist(paramChunk.variantIndex, paramChunk.getResult());
    }
    while (!(paramChunk instanceof EncryptionKeyChunk)) {
      return;
    }
    paramChunk = (EncryptionKeyChunk)paramChunk;
    this.scratchSpace = paramChunk.getDataHolder();
    setEncryptionData(paramChunk.dataSpec.uri, paramChunk.iv, paramChunk.getResult());
  }
  
  public boolean onChunkLoadError(Chunk paramChunk, IOException paramIOException)
  {
    if ((paramChunk.bytesLoaded() == 0L) && (((paramChunk instanceof TsChunk)) || ((paramChunk instanceof MediaPlaylistChunk)) || ((paramChunk instanceof EncryptionKeyChunk))) && ((paramIOException instanceof HttpDataSource.InvalidResponseCodeException)))
    {
      int k = ((HttpDataSource.InvalidResponseCodeException)paramIOException).responseCode;
      if ((k == 404) || (k == 410))
      {
        int i;
        if ((paramChunk instanceof TsChunk))
        {
          i = getVariantIndex(((TsChunk)paramChunk).format);
          if (this.variantBlacklistTimes[i] == 0L) {
            break label183;
          }
        }
        label183:
        for (int j = 1;; j = 0)
        {
          this.variantBlacklistTimes[i] = SystemClock.elapsedRealtime();
          if (j == 0) {
            break label189;
          }
          Log.w("HlsChunkSource", "Already blacklisted variant (" + k + "): " + paramChunk.dataSpec.uri);
          return false;
          if ((paramChunk instanceof MediaPlaylistChunk))
          {
            i = ((MediaPlaylistChunk)paramChunk).variantIndex;
            break;
          }
          i = ((EncryptionKeyChunk)paramChunk).variantIndex;
          break;
        }
        label189:
        if (!allVariantsBlacklisted())
        {
          Log.w("HlsChunkSource", "Blacklisted variant (" + k + "): " + paramChunk.dataSpec.uri);
          return true;
        }
        Log.w("HlsChunkSource", "Final variant not blacklisted (" + k + "): " + paramChunk.dataSpec.uri);
        this.variantBlacklistTimes[i] = 0L;
        return false;
      }
    }
    return false;
  }
  
  public boolean prepare()
  {
    if (!this.prepareCalled) {
      this.prepareCalled = true;
    }
    try
    {
      this.trackSelector.selectTracks(this.masterPlaylist, this);
      selectTrack(0);
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
  
  public void reset()
  {
    this.fatalError = null;
  }
  
  public void seek()
  {
    if (this.isMaster) {
      this.timestampAdjusterProvider.reset();
    }
  }
  
  public void selectTrack(int paramInt)
  {
    this.selectedTrackIndex = paramInt;
    ExposedTrack localExposedTrack = (ExposedTrack)this.tracks.get(this.selectedTrackIndex);
    this.selectedVariantIndex = localExposedTrack.defaultVariantIndex;
    this.variants = localExposedTrack.variants;
    this.variantPlaylists = new HlsMediaPlaylist[this.variants.length];
    this.variantLastPlaylistLoadTimesMs = new long[this.variants.length];
    this.variantBlacklistTimes = new long[this.variants.length];
  }
  
  private static final class EncryptionKeyChunk
    extends DataChunk
  {
    public final String iv;
    private byte[] result;
    public final int variantIndex;
    
    public EncryptionKeyChunk(DataSource paramDataSource, DataSpec paramDataSpec, byte[] paramArrayOfByte, String paramString, int paramInt)
    {
      super(paramDataSpec, 3, 0, null, -1, paramArrayOfByte);
      this.iv = paramString;
      this.variantIndex = paramInt;
    }
    
    protected void consume(byte[] paramArrayOfByte, int paramInt)
      throws IOException
    {
      this.result = Arrays.copyOf(paramArrayOfByte, paramInt);
    }
    
    public byte[] getResult()
    {
      return this.result;
    }
  }
  
  public static abstract interface EventListener
    extends BaseChunkSampleSourceEventListener
  {}
  
  private static final class ExposedTrack
  {
    private final int adaptiveMaxHeight;
    private final int adaptiveMaxWidth;
    private final int defaultVariantIndex;
    private final Variant[] variants;
    
    public ExposedTrack(Variant paramVariant)
    {
      this.variants = new Variant[] { paramVariant };
      this.defaultVariantIndex = 0;
      this.adaptiveMaxWidth = -1;
      this.adaptiveMaxHeight = -1;
    }
    
    public ExposedTrack(Variant[] paramArrayOfVariant, int paramInt1, int paramInt2, int paramInt3)
    {
      this.variants = paramArrayOfVariant;
      this.defaultVariantIndex = paramInt1;
      this.adaptiveMaxWidth = paramInt2;
      this.adaptiveMaxHeight = paramInt3;
    }
  }
  
  private static final class MediaPlaylistChunk
    extends DataChunk
  {
    private final HlsPlaylistParser playlistParser;
    private final String playlistUrl;
    private HlsMediaPlaylist result;
    public final int variantIndex;
    
    public MediaPlaylistChunk(DataSource paramDataSource, DataSpec paramDataSpec, byte[] paramArrayOfByte, HlsPlaylistParser paramHlsPlaylistParser, int paramInt, String paramString)
    {
      super(paramDataSpec, 4, 0, null, -1, paramArrayOfByte);
      this.variantIndex = paramInt;
      this.playlistParser = paramHlsPlaylistParser;
      this.playlistUrl = paramString;
    }
    
    protected void consume(byte[] paramArrayOfByte, int paramInt)
      throws IOException
    {
      this.result = ((HlsMediaPlaylist)this.playlistParser.parse(this.playlistUrl, new ByteArrayInputStream(paramArrayOfByte, 0, paramInt)));
    }
    
    public HlsMediaPlaylist getResult()
    {
      return this.result;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsChunkSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */