package com.google.android.exoplayer.extractor.ts;

import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.PositionHolder;
import com.google.android.exoplayer.extractor.SeekMap;
import com.google.android.exoplayer.util.ParsableBitArray;
import com.google.android.exoplayer.util.ParsableByteArray;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;

public final class AdtsExtractor
  implements Extractor
{
  private static final int ID3_TAG = Util.getIntegerCodeForString("ID3");
  private static final int MAX_PACKET_SIZE = 200;
  private static final int MAX_SNIFF_BYTES = 8192;
  private AdtsReader adtsReader;
  private final long firstSampleTimestampUs;
  private final ParsableByteArray packetBuffer;
  private boolean startedPacket;
  
  public AdtsExtractor()
  {
    this(0L);
  }
  
  public AdtsExtractor(long paramLong)
  {
    this.firstSampleTimestampUs = paramLong;
    this.packetBuffer = new ParsableByteArray(200);
  }
  
  public void init(ExtractorOutput paramExtractorOutput)
  {
    this.adtsReader = new AdtsReader(paramExtractorOutput.track(0), paramExtractorOutput.track(1));
    paramExtractorOutput.endTracks();
    paramExtractorOutput.seekMap(SeekMap.UNSEEKABLE);
  }
  
  public int read(ExtractorInput paramExtractorInput, PositionHolder paramPositionHolder)
    throws IOException, InterruptedException
  {
    int i = paramExtractorInput.read(this.packetBuffer.data, 0, 200);
    if (i == -1) {
      return -1;
    }
    this.packetBuffer.setPosition(0);
    this.packetBuffer.setLimit(i);
    if (!this.startedPacket)
    {
      this.adtsReader.packetStarted(this.firstSampleTimestampUs, true);
      this.startedPacket = true;
    }
    this.adtsReader.consume(this.packetBuffer);
    return 0;
  }
  
  public void seek()
  {
    this.startedPacket = false;
    this.adtsReader.seek();
  }
  
  public boolean sniff(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    ParsableByteArray localParsableByteArray = new ParsableByteArray(10);
    ParsableBitArray localParsableBitArray = new ParsableBitArray(localParsableByteArray.data);
    int i = 0;
    paramExtractorInput.peekFully(localParsableByteArray.data, 0, 10);
    localParsableByteArray.setPosition(0);
    int m;
    int j;
    int k;
    if (localParsableByteArray.readUnsignedInt24() != ID3_TAG)
    {
      paramExtractorInput.resetPeekPosition();
      paramExtractorInput.advancePeekPosition(i);
      m = i;
      j = 0;
      k = 0;
    }
    for (;;)
    {
      paramExtractorInput.peekFully(localParsableByteArray.data, 0, 2);
      localParsableByteArray.setPosition(0);
      if ((0xFFF6 & localParsableByteArray.readUnsignedShort()) != 65520)
      {
        k = 0;
        j = 0;
        paramExtractorInput.resetPeekPosition();
        m += 1;
        if (m - i >= 8192)
        {
          return false;
          j = (localParsableByteArray.data[6] & 0x7F) << 21 | (localParsableByteArray.data[7] & 0x7F) << 14 | (localParsableByteArray.data[8] & 0x7F) << 7 | localParsableByteArray.data[9] & 0x7F;
          i += j + 10;
          paramExtractorInput.advancePeekPosition(j);
          break;
        }
        paramExtractorInput.advancePeekPosition(m);
        continue;
      }
      k += 1;
      if ((k >= 4) && (j > 188)) {
        return true;
      }
      paramExtractorInput.peekFully(localParsableByteArray.data, 0, 4);
      localParsableBitArray.setPosition(14);
      int n = localParsableBitArray.readBits(13);
      paramExtractorInput.advancePeekPosition(n - 6);
      j += n;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/ts/AdtsExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */