package com.google.android.exoplayer.text.tx3g;

import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.Subtitle;
import com.google.android.exoplayer.text.SubtitleParser;

public final class Tx3gParser
  implements SubtitleParser
{
  public boolean canParse(String paramString)
  {
    return "application/x-quicktime-tx3g".equals(paramString);
  }
  
  public Subtitle parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new Tx3gSubtitle(new Cue(new String(paramArrayOfByte, paramInt1, paramInt2)));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/tx3g/Tx3gParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */