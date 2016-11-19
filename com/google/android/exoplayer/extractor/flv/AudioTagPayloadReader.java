package com.google.android.exoplayer.extractor.flv;

import android.util.Pair;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.util.CodecSpecificDataUtil;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.util.Collections;

final class AudioTagPayloadReader
  extends TagPayloadReader
{
  private static final int AAC_PACKET_TYPE_AAC_RAW = 1;
  private static final int AAC_PACKET_TYPE_SEQUENCE_HEADER = 0;
  private static final int AUDIO_FORMAT_AAC = 10;
  private static final int[] AUDIO_SAMPLING_RATE_TABLE = { 5500, 11000, 22000, 44000 };
  private boolean hasOutputFormat;
  private boolean hasParsedAudioDataHeader;
  
  public AudioTagPayloadReader(TrackOutput paramTrackOutput)
  {
    super(paramTrackOutput);
  }
  
  protected boolean parseHeader(ParsableByteArray paramParsableByteArray)
    throws TagPayloadReader.UnsupportedFormatException
  {
    if (!this.hasParsedAudioDataHeader)
    {
      int j = paramParsableByteArray.readUnsignedByte();
      int i = j >> 4 & 0xF;
      j = j >> 2 & 0x3;
      if ((j < 0) || (j >= AUDIO_SAMPLING_RATE_TABLE.length)) {
        throw new TagPayloadReader.UnsupportedFormatException("Invalid sample rate index: " + j);
      }
      if (i != 10) {
        throw new TagPayloadReader.UnsupportedFormatException("Audio format not supported: " + i);
      }
      this.hasParsedAudioDataHeader = true;
      return true;
    }
    paramParsableByteArray.skipBytes(1);
    return true;
  }
  
  protected void parsePayload(ParsableByteArray paramParsableByteArray, long paramLong)
  {
    int i = paramParsableByteArray.readUnsignedByte();
    if ((i == 0) && (!this.hasOutputFormat))
    {
      arrayOfByte = new byte[paramParsableByteArray.bytesLeft()];
      paramParsableByteArray.readBytes(arrayOfByte, 0, arrayOfByte.length);
      paramParsableByteArray = CodecSpecificDataUtil.parseAacAudioSpecificConfig(arrayOfByte);
      paramParsableByteArray = MediaFormat.createAudioFormat(null, "audio/mp4a-latm", -1, -1, getDurationUs(), ((Integer)paramParsableByteArray.second).intValue(), ((Integer)paramParsableByteArray.first).intValue(), Collections.singletonList(arrayOfByte), null);
      this.output.format(paramParsableByteArray);
      this.hasOutputFormat = true;
    }
    while (i != 1)
    {
      byte[] arrayOfByte;
      return;
    }
    i = paramParsableByteArray.bytesLeft();
    this.output.sampleData(paramParsableByteArray, i);
    this.output.sampleMetadata(paramLong, 1, i, 0, null);
  }
  
  public void seek() {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/flv/AudioTagPayloadReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */