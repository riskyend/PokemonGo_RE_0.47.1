package com.google.android.exoplayer.hls;

import java.util.Collections;
import java.util.List;

public final class HlsMasterPlaylist
  extends HlsPlaylist
{
  public final List<Variant> subtitles;
  public final List<Variant> variants;
  
  public HlsMasterPlaylist(String paramString, List<Variant> paramList1, List<Variant> paramList2)
  {
    super(paramString, 0);
    this.variants = Collections.unmodifiableList(paramList1);
    this.subtitles = Collections.unmodifiableList(paramList2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsMasterPlaylist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */