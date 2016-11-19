package com.google.android.exoplayer.hls;

import java.io.IOException;

public abstract interface HlsTrackSelector
{
  public abstract void selectTracks(HlsMasterPlaylist paramHlsMasterPlaylist, Output paramOutput)
    throws IOException;
  
  public static abstract interface Output
  {
    public abstract void adaptiveTrack(HlsMasterPlaylist paramHlsMasterPlaylist, Variant[] paramArrayOfVariant);
    
    public abstract void fixedTrack(HlsMasterPlaylist paramHlsMasterPlaylist, Variant paramVariant);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsTrackSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */