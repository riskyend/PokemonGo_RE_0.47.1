package com.google.android.exoplayer.smoothstreaming;

import java.io.IOException;

public abstract interface SmoothStreamingTrackSelector
{
  public abstract void selectTracks(SmoothStreamingManifest paramSmoothStreamingManifest, Output paramOutput)
    throws IOException;
  
  public static abstract interface Output
  {
    public abstract void adaptiveTrack(SmoothStreamingManifest paramSmoothStreamingManifest, int paramInt, int[] paramArrayOfInt);
    
    public abstract void fixedTrack(SmoothStreamingManifest paramSmoothStreamingManifest, int paramInt1, int paramInt2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/smoothstreaming/SmoothStreamingTrackSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */