package com.google.android.exoplayer.metadata.id3;

public final class TxxxFrame
  extends Id3Frame
{
  public static final String ID = "TXXX";
  public final String description;
  public final String value;
  
  public TxxxFrame(String paramString1, String paramString2)
  {
    super("TXXX");
    this.description = paramString1;
    this.value = paramString2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/metadata/id3/TxxxFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */