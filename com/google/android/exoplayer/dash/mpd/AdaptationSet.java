package com.google.android.exoplayer.dash.mpd;

import java.util.Collections;
import java.util.List;

public class AdaptationSet
{
  public static final int TYPE_AUDIO = 1;
  public static final int TYPE_TEXT = 2;
  public static final int TYPE_UNKNOWN = -1;
  public static final int TYPE_VIDEO = 0;
  public final List<ContentProtection> contentProtections;
  public final int id;
  public final List<Representation> representations;
  public final int type;
  
  public AdaptationSet(int paramInt1, int paramInt2, List<Representation> paramList)
  {
    this(paramInt1, paramInt2, paramList, null);
  }
  
  public AdaptationSet(int paramInt1, int paramInt2, List<Representation> paramList, List<ContentProtection> paramList1)
  {
    this.id = paramInt1;
    this.type = paramInt2;
    this.representations = Collections.unmodifiableList(paramList);
    if (paramList1 == null)
    {
      this.contentProtections = Collections.emptyList();
      return;
    }
    this.contentProtections = Collections.unmodifiableList(paramList1);
  }
  
  public boolean hasContentProtection()
  {
    return !this.contentProtections.isEmpty();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/dash/mpd/AdaptationSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */