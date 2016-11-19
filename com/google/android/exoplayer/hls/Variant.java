package com.google.android.exoplayer.hls;

import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.chunk.FormatWrapper;

public final class Variant
  implements FormatWrapper
{
  public final Format format;
  public final String url;
  
  public Variant(String paramString, Format paramFormat)
  {
    this.url = paramString;
    this.format = paramFormat;
  }
  
  public Format getFormat()
  {
    return this.format;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/Variant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */