package com.google.android.exoplayer.dash.mpd;

import java.util.Collections;
import java.util.List;

public class Period
{
  public final List<AdaptationSet> adaptationSets;
  public final String id;
  public final long startMs;
  
  public Period(String paramString, long paramLong, List<AdaptationSet> paramList)
  {
    this.id = paramString;
    this.startMs = paramLong;
    this.adaptationSets = Collections.unmodifiableList(paramList);
  }
  
  public int getAdaptationSetIndex(int paramInt)
  {
    int j = this.adaptationSets.size();
    int i = 0;
    while (i < j)
    {
      if (((AdaptationSet)this.adaptationSets.get(i)).type == paramInt) {
        return i;
      }
      i += 1;
    }
    return -1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/dash/mpd/Period.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */