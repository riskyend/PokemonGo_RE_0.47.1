package com.google.android.exoplayer.dash;

import com.google.android.exoplayer.dash.mpd.MediaPresentationDescription;
import java.io.IOException;

public abstract interface DashTrackSelector
{
  public abstract void selectTracks(MediaPresentationDescription paramMediaPresentationDescription, int paramInt, Output paramOutput)
    throws IOException;
  
  public static abstract interface Output
  {
    public abstract void adaptiveTrack(MediaPresentationDescription paramMediaPresentationDescription, int paramInt1, int paramInt2, int[] paramArrayOfInt);
    
    public abstract void fixedTrack(MediaPresentationDescription paramMediaPresentationDescription, int paramInt1, int paramInt2, int paramInt3);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/dash/DashTrackSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */