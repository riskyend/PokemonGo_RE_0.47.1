package com.google.android.exoplayer.hls;

import android.text.TextUtils;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.PositionHolder;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.extractor.ts.PtsTimestampAdjuster;
import com.google.android.exoplayer.text.webvtt.WebvttCueParser;
import com.google.android.exoplayer.text.webvtt.WebvttParserUtil;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class WebvttExtractor
  implements Extractor
{
  private static final Pattern LOCAL_TIMESTAMP = Pattern.compile("LOCAL:([^,]+)");
  private static final Pattern MEDIA_TIMESTAMP = Pattern.compile("MPEGTS:(\\d+)");
  private ExtractorOutput output;
  private final PtsTimestampAdjuster ptsTimestampAdjuster;
  private byte[] sampleData;
  private final ParsableByteArray sampleDataWrapper;
  private int sampleSize;
  
  public WebvttExtractor(PtsTimestampAdjuster paramPtsTimestampAdjuster)
  {
    this.ptsTimestampAdjuster = paramPtsTimestampAdjuster;
    this.sampleDataWrapper = new ParsableByteArray();
    this.sampleData = new byte['Ѐ'];
  }
  
  private TrackOutput buildTrackOutput(long paramLong)
  {
    TrackOutput localTrackOutput = this.output.track(0);
    localTrackOutput.format(MediaFormat.createTextFormat("id", "text/vtt", -1, -1L, "en", paramLong));
    this.output.endTracks();
    return localTrackOutput;
  }
  
  private void processSample()
    throws ParserException
  {
    Object localObject = new ParsableByteArray(this.sampleData);
    WebvttParserUtil.validateWebvttHeaderLine((ParsableByteArray)localObject);
    long l2 = 0L;
    long l1 = 0L;
    for (;;)
    {
      String str = ((ParsableByteArray)localObject).readLine();
      if (TextUtils.isEmpty(str)) {
        break;
      }
      if (str.startsWith("X-TIMESTAMP-MAP"))
      {
        Matcher localMatcher1 = LOCAL_TIMESTAMP.matcher(str);
        if (!localMatcher1.find()) {
          throw new ParserException("X-TIMESTAMP-MAP doesn't contain local timestamp: " + str);
        }
        Matcher localMatcher2 = MEDIA_TIMESTAMP.matcher(str);
        if (!localMatcher2.find()) {
          throw new ParserException("X-TIMESTAMP-MAP doesn't contain media timestamp: " + str);
        }
        l2 = WebvttParserUtil.parseTimestampUs(localMatcher1.group(1));
        l1 = PtsTimestampAdjuster.ptsToUs(Long.parseLong(localMatcher2.group(1)));
      }
    }
    localObject = WebvttCueParser.findNextCueHeader((ParsableByteArray)localObject);
    if (localObject == null)
    {
      buildTrackOutput(0L);
      return;
    }
    long l3 = WebvttParserUtil.parseTimestampUs(((Matcher)localObject).group(1));
    l1 = this.ptsTimestampAdjuster.adjustTimestamp(PtsTimestampAdjuster.usToPts(l3 + l1 - l2));
    localObject = buildTrackOutput(l1 - l3);
    this.sampleDataWrapper.reset(this.sampleData, this.sampleSize);
    ((TrackOutput)localObject).sampleData(this.sampleDataWrapper, this.sampleSize);
    ((TrackOutput)localObject).sampleMetadata(l1, 1, this.sampleSize, 0, null);
  }
  
  public void init(ExtractorOutput paramExtractorOutput)
  {
    this.output = paramExtractorOutput;
    paramExtractorOutput.seekMap(SeekMap.UNSEEKABLE);
  }
  
  public int read(ExtractorInput paramExtractorInput, PositionHolder paramPositionHolder)
    throws IOException, InterruptedException
  {
    int j = (int)paramExtractorInput.getLength();
    if (this.sampleSize == this.sampleData.length)
    {
      paramPositionHolder = this.sampleData;
      if (j == -1) {
        break label105;
      }
    }
    label105:
    for (int i = j;; i = this.sampleData.length)
    {
      this.sampleData = Arrays.copyOf(paramPositionHolder, i * 3 / 2);
      i = paramExtractorInput.read(this.sampleData, this.sampleSize, this.sampleData.length - this.sampleSize);
      if (i == -1) {
        break;
      }
      this.sampleSize += i;
      if ((j != -1) && (this.sampleSize == j)) {
        break;
      }
      return 0;
    }
    processSample();
    return -1;
  }
  
  public void seek()
  {
    throw new IllegalStateException();
  }
  
  public boolean sniff(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    throw new IllegalStateException();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/WebvttExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */