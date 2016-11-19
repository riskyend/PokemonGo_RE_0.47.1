package com.google.android.exoplayer.extractor.ts;

import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.text.eia608.Eia608Parser;
import com.google.android.exoplayer.util.ParsableByteArray;

final class SeiReader
{
  private final TrackOutput output;
  
  public SeiReader(TrackOutput paramTrackOutput)
  {
    this.output = paramTrackOutput;
    paramTrackOutput.format(MediaFormat.createTextFormat(null, "application/eia-608", -1, -1L, null));
  }
  
  public void consume(long paramLong, ParsableByteArray paramParsableByteArray)
  {
    while (paramParsableByteArray.bytesLeft() > 1)
    {
      int i = 0;
      int k;
      int j;
      do
      {
        k = paramParsableByteArray.readUnsignedByte();
        j = i + k;
        i = j;
      } while (k == 255);
      i = 0;
      int m;
      do
      {
        m = paramParsableByteArray.readUnsignedByte();
        k = i + m;
        i = k;
      } while (m == 255);
      if (Eia608Parser.isSeiMessageEia608(j, k, paramParsableByteArray))
      {
        this.output.sampleData(paramParsableByteArray, k);
        this.output.sampleMetadata(paramLong, 1, k, 0, null);
      }
      else
      {
        paramParsableByteArray.skipBytes(k);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/ts/SeiReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */