package com.google.android.exoplayer.text.ttml;

import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.text.Subtitle;
import com.google.android.exoplayer.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class TtmlSubtitle
  implements Subtitle
{
  private final long[] eventTimesUs;
  private final Map<String, TtmlStyle> globalStyles;
  private final TtmlNode root;
  
  public TtmlSubtitle(TtmlNode paramTtmlNode, Map<String, TtmlStyle> paramMap)
  {
    this.root = paramTtmlNode;
    if (paramMap != null) {}
    for (paramMap = Collections.unmodifiableMap(paramMap);; paramMap = Collections.emptyMap())
    {
      this.globalStyles = paramMap;
      this.eventTimesUs = paramTtmlNode.getEventTimesUs();
      return;
    }
  }
  
  public List<Cue> getCues(long paramLong)
  {
    CharSequence localCharSequence = this.root.getText(paramLong, this.globalStyles);
    if (localCharSequence == null) {
      return Collections.emptyList();
    }
    return Collections.singletonList(new Cue(localCharSequence));
  }
  
  public long getEventTime(int paramInt)
  {
    return this.eventTimesUs[paramInt];
  }
  
  public int getEventTimeCount()
  {
    return this.eventTimesUs.length;
  }
  
  Map<String, TtmlStyle> getGlobalStyles()
  {
    return this.globalStyles;
  }
  
  public long getLastEventTime()
  {
    if (this.eventTimesUs.length == 0) {
      return -1L;
    }
    return this.eventTimesUs[(this.eventTimesUs.length - 1)];
  }
  
  public int getNextEventTimeIndex(long paramLong)
  {
    int i = Util.binarySearchCeil(this.eventTimesUs, paramLong, false, false);
    if (i < this.eventTimesUs.length) {
      return i;
    }
    return -1;
  }
  
  TtmlNode getRoot()
  {
    return this.root;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/ttml/TtmlSubtitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */