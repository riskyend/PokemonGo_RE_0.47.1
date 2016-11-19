package com.google.android.exoplayer.dash.mpd;

public final class UtcTimingElement
{
  public final String schemeIdUri;
  public final String value;
  
  public UtcTimingElement(String paramString1, String paramString2)
  {
    this.schemeIdUri = paramString1;
    this.value = paramString2;
  }
  
  public String toString()
  {
    return this.schemeIdUri + ", " + this.value;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/dash/mpd/UtcTimingElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */