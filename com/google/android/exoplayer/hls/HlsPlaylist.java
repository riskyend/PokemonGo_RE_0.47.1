package com.google.android.exoplayer.hls;

public abstract class HlsPlaylist
{
  public static final int TYPE_MASTER = 0;
  public static final int TYPE_MEDIA = 1;
  public final String baseUri;
  public final int type;
  
  protected HlsPlaylist(String paramString, int paramInt)
  {
    this.baseUri = paramString;
    this.type = paramInt;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsPlaylist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */