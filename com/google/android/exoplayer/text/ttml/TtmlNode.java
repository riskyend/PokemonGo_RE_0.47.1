package com.google.android.exoplayer.text.ttml;

import android.text.SpannableStringBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

final class TtmlNode
{
  public static final String ATTR_ID = "id";
  public static final String ATTR_TTS_BACKGROUND_COLOR = "backgroundColor";
  public static final String ATTR_TTS_COLOR = "color";
  public static final String ATTR_TTS_FONT_FAMILY = "fontFamily";
  public static final String ATTR_TTS_FONT_SIZE = "fontSize";
  public static final String ATTR_TTS_FONT_STYLE = "fontStyle";
  public static final String ATTR_TTS_FONT_WEIGHT = "fontWeight";
  public static final String ATTR_TTS_TEXT_ALIGN = "textAlign";
  public static final String ATTR_TTS_TEXT_DECORATION = "textDecoration";
  public static final String BOLD = "bold";
  public static final String CENTER = "center";
  public static final String END = "end";
  public static final String ITALIC = "italic";
  public static final String LEFT = "left";
  public static final String LINETHROUGH = "linethrough";
  public static final String NO_LINETHROUGH = "nolinethrough";
  public static final String NO_UNDERLINE = "nounderline";
  public static final String RIGHT = "right";
  public static final String START = "start";
  public static final String TAG_BODY = "body";
  public static final String TAG_BR = "br";
  public static final String TAG_DIV = "div";
  public static final String TAG_HEAD = "head";
  public static final String TAG_LAYOUT = "layout";
  public static final String TAG_METADATA = "metadata";
  public static final String TAG_P = "p";
  public static final String TAG_REGION = "region";
  public static final String TAG_SMPTE_DATA = "smpte:data";
  public static final String TAG_SMPTE_IMAGE = "smpte:image";
  public static final String TAG_SMPTE_INFORMATION = "smpte:information";
  public static final String TAG_SPAN = "span";
  public static final String TAG_STYLE = "style";
  public static final String TAG_STYLING = "styling";
  public static final String TAG_TT = "tt";
  public static final long UNDEFINED_TIME = -1L;
  public static final String UNDERLINE = "underline";
  private List<TtmlNode> children;
  private int end;
  public final long endTimeUs;
  public final boolean isTextNode;
  private int start;
  public final long startTimeUs;
  public final TtmlStyle style;
  private String[] styleIds;
  public final String tag;
  public final String text;
  
  private TtmlNode(String paramString1, String paramString2, long paramLong1, long paramLong2, TtmlStyle paramTtmlStyle, String[] paramArrayOfString)
  {
    this.tag = paramString1;
    this.text = paramString2;
    this.style = paramTtmlStyle;
    this.styleIds = paramArrayOfString;
    if (paramString2 != null) {}
    for (boolean bool = true;; bool = false)
    {
      this.isTextNode = bool;
      this.startTimeUs = paramLong1;
      this.endTimeUs = paramLong2;
      return;
    }
  }
  
  public static TtmlNode buildNode(String paramString, long paramLong1, long paramLong2, TtmlStyle paramTtmlStyle, String[] paramArrayOfString)
  {
    return new TtmlNode(paramString, null, paramLong1, paramLong2, paramTtmlStyle, paramArrayOfString);
  }
  
  public static TtmlNode buildTextNode(String paramString)
  {
    return new TtmlNode(null, TtmlRenderUtil.applyTextElementSpacePolicy(paramString), -1L, -1L, null, null);
  }
  
  private void getEventTimes(TreeSet<Long> paramTreeSet, boolean paramBoolean)
  {
    boolean bool2 = "p".equals(this.tag);
    if ((paramBoolean) || (bool2))
    {
      if (this.startTimeUs != -1L) {
        paramTreeSet.add(Long.valueOf(this.startTimeUs));
      }
      if (this.endTimeUs != -1L) {
        paramTreeSet.add(Long.valueOf(this.endTimeUs));
      }
    }
    if (this.children == null) {
      return;
    }
    int i = 0;
    label76:
    TtmlNode localTtmlNode;
    if (i < this.children.size())
    {
      localTtmlNode = (TtmlNode)this.children.get(i);
      if ((!paramBoolean) && (!bool2)) {
        break label131;
      }
    }
    label131:
    for (boolean bool1 = true;; bool1 = false)
    {
      localTtmlNode.getEventTimes(paramTreeSet, bool1);
      i += 1;
      break label76;
      break;
    }
  }
  
  private void traverseForStyle(SpannableStringBuilder paramSpannableStringBuilder, Map<String, TtmlStyle> paramMap)
  {
    if (this.start != this.end)
    {
      TtmlStyle localTtmlStyle = TtmlRenderUtil.resolveStyle(this.style, this.styleIds, paramMap);
      if (localTtmlStyle != null) {
        TtmlRenderUtil.applyStylesToSpan(paramSpannableStringBuilder, this.start, this.end, localTtmlStyle);
      }
      int i = 0;
      while (i < getChildCount())
      {
        getChild(i).traverseForStyle(paramSpannableStringBuilder, paramMap);
        i += 1;
      }
    }
  }
  
  private SpannableStringBuilder traverseForText(long paramLong, SpannableStringBuilder paramSpannableStringBuilder, boolean paramBoolean)
  {
    this.start = paramSpannableStringBuilder.length();
    this.end = this.start;
    if ((this.isTextNode) && (paramBoolean)) {
      paramSpannableStringBuilder.append(this.text);
    }
    do
    {
      return paramSpannableStringBuilder;
      if (("br".equals(this.tag)) && (paramBoolean))
      {
        paramSpannableStringBuilder.append('\n');
        return paramSpannableStringBuilder;
      }
    } while (("metadata".equals(this.tag)) || (!isActive(paramLong)));
    boolean bool2 = "p".equals(this.tag);
    int i = 0;
    if (i < getChildCount())
    {
      TtmlNode localTtmlNode = getChild(i);
      if ((paramBoolean) || (bool2)) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        localTtmlNode.traverseForText(paramLong, paramSpannableStringBuilder, bool1);
        i += 1;
        break;
      }
    }
    if (bool2) {
      TtmlRenderUtil.endParagraph(paramSpannableStringBuilder);
    }
    this.end = paramSpannableStringBuilder.length();
    return paramSpannableStringBuilder;
  }
  
  public void addChild(TtmlNode paramTtmlNode)
  {
    if (this.children == null) {
      this.children = new ArrayList();
    }
    this.children.add(paramTtmlNode);
  }
  
  public TtmlNode getChild(int paramInt)
  {
    if (this.children == null) {
      throw new IndexOutOfBoundsException();
    }
    return (TtmlNode)this.children.get(paramInt);
  }
  
  public int getChildCount()
  {
    if (this.children == null) {
      return 0;
    }
    return this.children.size();
  }
  
  public long[] getEventTimesUs()
  {
    Object localObject = new TreeSet();
    getEventTimes((TreeSet)localObject, false);
    long[] arrayOfLong = new long[((TreeSet)localObject).size()];
    localObject = ((TreeSet)localObject).iterator();
    int i = 0;
    while (((Iterator)localObject).hasNext())
    {
      arrayOfLong[i] = ((Long)((Iterator)localObject).next()).longValue();
      i += 1;
    }
    return arrayOfLong;
  }
  
  public String[] getStyleIds()
  {
    return this.styleIds;
  }
  
  public CharSequence getText(long paramLong, Map<String, TtmlStyle> paramMap)
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    traverseForText(paramLong, localSpannableStringBuilder, false);
    traverseForStyle(localSpannableStringBuilder, paramMap);
    int i = localSpannableStringBuilder.length();
    int j = 0;
    while (j < i)
    {
      k = i;
      if (localSpannableStringBuilder.charAt(j) == ' ')
      {
        k = j + 1;
        while ((k < localSpannableStringBuilder.length()) && (localSpannableStringBuilder.charAt(k) == ' ')) {
          k += 1;
        }
        int m = k - (j + 1);
        k = i;
        if (m > 0)
        {
          localSpannableStringBuilder.delete(j, j + m);
          k = i - m;
        }
      }
      j += 1;
      i = k;
    }
    j = i;
    if (i > 0)
    {
      j = i;
      if (localSpannableStringBuilder.charAt(0) == ' ')
      {
        localSpannableStringBuilder.delete(0, 1);
        j = i - 1;
      }
    }
    int k = 0;
    for (i = j; k < i - 1; i = j)
    {
      j = i;
      if (localSpannableStringBuilder.charAt(k) == '\n')
      {
        j = i;
        if (localSpannableStringBuilder.charAt(k + 1) == ' ')
        {
          localSpannableStringBuilder.delete(k + 1, k + 2);
          j = i - 1;
        }
      }
      k += 1;
    }
    j = i;
    if (i > 0)
    {
      j = i;
      if (localSpannableStringBuilder.charAt(i - 1) == ' ')
      {
        localSpannableStringBuilder.delete(i - 1, i);
        j = i - 1;
      }
    }
    i = 0;
    while (i < j - 1)
    {
      k = j;
      if (localSpannableStringBuilder.charAt(i) == ' ')
      {
        k = j;
        if (localSpannableStringBuilder.charAt(i + 1) == '\n')
        {
          localSpannableStringBuilder.delete(i, i + 1);
          k = j - 1;
        }
      }
      i += 1;
      j = k;
    }
    if ((j > 0) && (localSpannableStringBuilder.charAt(j - 1) == '\n')) {
      localSpannableStringBuilder.delete(j - 1, j);
    }
    return localSpannableStringBuilder;
  }
  
  public boolean isActive(long paramLong)
  {
    return ((this.startTimeUs == -1L) && (this.endTimeUs == -1L)) || ((this.startTimeUs <= paramLong) && (this.endTimeUs == -1L)) || ((this.startTimeUs == -1L) && (paramLong < this.endTimeUs)) || ((this.startTimeUs <= paramLong) && (paramLong < this.endTimeUs));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/ttml/TtmlNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */