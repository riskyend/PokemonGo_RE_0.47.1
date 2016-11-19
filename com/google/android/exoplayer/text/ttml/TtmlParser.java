package com.google.android.exoplayer.text.ttml;

import android.text.Layout.Alignment;
import android.util.Log;
import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.text.SubtitleParser;
import com.google.android.exoplayer.util.ParserUtil;
import com.google.android.exoplayer.util.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class TtmlParser
  implements SubtitleParser
{
  private static final String ATTR_BEGIN = "begin";
  private static final String ATTR_DURATION = "dur";
  private static final String ATTR_END = "end";
  private static final String ATTR_STYLE = "style";
  private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
  private static final int DEFAULT_FRAMERATE = 30;
  private static final int DEFAULT_SUBFRAMERATE = 1;
  private static final int DEFAULT_TICKRATE = 1;
  private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
  private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
  private static final String TAG = "TtmlParser";
  private final XmlPullParserFactory xmlParserFactory;
  
  public TtmlParser()
  {
    try
    {
      this.xmlParserFactory = XmlPullParserFactory.newInstance();
      return;
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      throw new RuntimeException("Couldn't create XmlPullParserFactory instance", localXmlPullParserException);
    }
  }
  
  private TtmlStyle createIfNull(TtmlStyle paramTtmlStyle)
  {
    TtmlStyle localTtmlStyle = paramTtmlStyle;
    if (paramTtmlStyle == null) {
      localTtmlStyle = new TtmlStyle();
    }
    return localTtmlStyle;
  }
  
  private static boolean isSupportedTag(String paramString)
  {
    return (paramString.equals("tt")) || (paramString.equals("head")) || (paramString.equals("body")) || (paramString.equals("div")) || (paramString.equals("p")) || (paramString.equals("span")) || (paramString.equals("br")) || (paramString.equals("style")) || (paramString.equals("styling")) || (paramString.equals("layout")) || (paramString.equals("region")) || (paramString.equals("metadata")) || (paramString.equals("smpte:image")) || (paramString.equals("smpte:data")) || (paramString.equals("smpte:information"));
  }
  
  private static void parseFontSize(String paramString, TtmlStyle paramTtmlStyle)
    throws ParserException
  {
    Object localObject = paramString.split("\\s+");
    label21:
    int i;
    if (localObject.length == 1)
    {
      paramString = FONT_SIZE.matcher(paramString);
      if (!paramString.matches()) {
        break label230;
      }
      localObject = paramString.group(3);
      i = -1;
      switch (((String)localObject).hashCode())
      {
      }
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        throw new ParserException();
        if (localObject.length == 2)
        {
          paramString = FONT_SIZE.matcher(localObject[1]);
          Log.w("TtmlParser", "multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
          break label21;
        }
        throw new ParserException();
        if (((String)localObject).equals("px"))
        {
          i = 0;
          continue;
          if (((String)localObject).equals("em"))
          {
            i = 1;
            continue;
            if (((String)localObject).equals("%")) {
              i = 2;
            }
          }
        }
        break;
      }
    }
    paramTtmlStyle.setFontSizeUnit((short)1);
    for (;;)
    {
      paramTtmlStyle.setFontSize(Float.valueOf(paramString.group(1)).floatValue());
      return;
      paramTtmlStyle.setFontSizeUnit((short)2);
      continue;
      paramTtmlStyle.setFontSizeUnit((short)3);
    }
    label230:
    throw new ParserException();
  }
  
  private Map<String, TtmlStyle> parseHeader(XmlPullParser paramXmlPullParser, Map<String, TtmlStyle> paramMap)
    throws IOException, XmlPullParserException
  {
    do
    {
      paramXmlPullParser.next();
      if (ParserUtil.isStartTag(paramXmlPullParser, "style"))
      {
        Object localObject = paramXmlPullParser.getAttributeValue(null, "style");
        TtmlStyle localTtmlStyle = parseStyleAttributes(paramXmlPullParser, new TtmlStyle());
        if (localObject != null)
        {
          localObject = parseStyleIds((String)localObject);
          int i = 0;
          while (i < localObject.length)
          {
            localTtmlStyle.chain((TtmlStyle)paramMap.get(localObject[i]));
            i += 1;
          }
        }
        if (localTtmlStyle.getId() != null) {
          paramMap.put(localTtmlStyle.getId(), localTtmlStyle);
        }
      }
    } while (!ParserUtil.isEndTag(paramXmlPullParser, "head"));
    return paramMap;
  }
  
  private TtmlNode parseNode(XmlPullParser paramXmlPullParser, TtmlNode paramTtmlNode)
    throws ParserException
  {
    long l5 = 0L;
    long l1 = -1L;
    long l2 = -1L;
    Object localObject1 = null;
    int j = paramXmlPullParser.getAttributeCount();
    TtmlStyle localTtmlStyle = parseStyleAttributes(paramXmlPullParser, null);
    int i = 0;
    long l3;
    if (i < j)
    {
      Object localObject3 = ParserUtil.removeNamespacePrefix(paramXmlPullParser.getAttributeName(i));
      String str = paramXmlPullParser.getAttributeValue(i);
      Object localObject2;
      if (((String)localObject3).equals("begin"))
      {
        l3 = parseTimeExpression(str, 30, 1, 1);
        l6 = l5;
        localObject2 = localObject1;
        l4 = l2;
      }
      for (;;)
      {
        i += 1;
        l1 = l3;
        l2 = l4;
        localObject1 = localObject2;
        l5 = l6;
        break;
        if (((String)localObject3).equals("end"))
        {
          l4 = parseTimeExpression(str, 30, 1, 1);
          l3 = l1;
          localObject2 = localObject1;
          l6 = l5;
        }
        else if (((String)localObject3).equals("dur"))
        {
          l6 = parseTimeExpression(str, 30, 1, 1);
          l3 = l1;
          l4 = l2;
          localObject2 = localObject1;
        }
        else
        {
          l3 = l1;
          l4 = l2;
          localObject2 = localObject1;
          l6 = l5;
          if (((String)localObject3).equals("style"))
          {
            localObject3 = parseStyleIds(str);
            l3 = l1;
            l4 = l2;
            localObject2 = localObject1;
            l6 = l5;
            if (localObject3.length > 0)
            {
              localObject2 = localObject3;
              l3 = l1;
              l4 = l2;
              l6 = l5;
            }
          }
        }
      }
    }
    long l6 = l1;
    long l4 = l2;
    if (paramTtmlNode != null)
    {
      l6 = l1;
      l4 = l2;
      if (paramTtmlNode.startTimeUs != -1L)
      {
        l3 = l1;
        if (l1 != -1L) {
          l3 = l1 + paramTtmlNode.startTimeUs;
        }
        l6 = l3;
        l4 = l2;
        if (l2 != -1L)
        {
          l4 = l2 + paramTtmlNode.startTimeUs;
          l6 = l3;
        }
      }
    }
    l1 = l4;
    if (l4 == -1L)
    {
      if (l5 <= 0L) {
        break label392;
      }
      l1 = l6 + l5;
    }
    for (;;)
    {
      return TtmlNode.buildNode(paramXmlPullParser.getName(), l6, l1, localTtmlStyle, (String[])localObject1);
      label392:
      l1 = l4;
      if (paramTtmlNode != null)
      {
        l1 = l4;
        if (paramTtmlNode.endTimeUs != -1L) {
          l1 = paramTtmlNode.endTimeUs;
        }
      }
    }
  }
  
  private TtmlStyle parseStyleAttributes(XmlPullParser paramXmlPullParser, TtmlStyle paramTtmlStyle)
  {
    int k = paramXmlPullParser.getAttributeCount();
    int j = 0;
    TtmlStyle localTtmlStyle1 = paramTtmlStyle;
    if (j < k)
    {
      paramTtmlStyle = paramXmlPullParser.getAttributeName(j);
      String str = paramXmlPullParser.getAttributeValue(j);
      paramTtmlStyle = ParserUtil.removeNamespacePrefix(paramTtmlStyle);
      label132:
      int i;
      switch (paramTtmlStyle.hashCode())
      {
      default: 
        i = -1;
        switch (i)
        {
        default: 
          label134:
          paramTtmlStyle = localTtmlStyle1;
        }
        break;
      }
      for (;;)
      {
        j += 1;
        localTtmlStyle1 = paramTtmlStyle;
        break;
        if (!paramTtmlStyle.equals("id")) {
          break label132;
        }
        i = 0;
        break label134;
        if (!paramTtmlStyle.equals("backgroundColor")) {
          break label132;
        }
        i = 1;
        break label134;
        if (!paramTtmlStyle.equals("color")) {
          break label132;
        }
        i = 2;
        break label134;
        if (!paramTtmlStyle.equals("fontFamily")) {
          break label132;
        }
        i = 3;
        break label134;
        if (!paramTtmlStyle.equals("fontSize")) {
          break label132;
        }
        i = 4;
        break label134;
        if (!paramTtmlStyle.equals("fontWeight")) {
          break label132;
        }
        i = 5;
        break label134;
        if (!paramTtmlStyle.equals("fontStyle")) {
          break label132;
        }
        i = 6;
        break label134;
        if (!paramTtmlStyle.equals("textAlign")) {
          break label132;
        }
        i = 7;
        break label134;
        if (!paramTtmlStyle.equals("textDecoration")) {
          break label132;
        }
        i = 8;
        break label134;
        paramTtmlStyle = localTtmlStyle1;
        if ("style".equals(paramXmlPullParser.getName()))
        {
          paramTtmlStyle = createIfNull(localTtmlStyle1).setId(str);
          continue;
          paramTtmlStyle = createIfNull(localTtmlStyle1);
          try
          {
            paramTtmlStyle.setBackgroundColor(TtmlColorParser.parseColor(str));
          }
          catch (IllegalArgumentException localIllegalArgumentException1)
          {
            Log.w("TtmlParser", "failed parsing background value: '" + str + "'");
          }
          continue;
          paramTtmlStyle = createIfNull(localIllegalArgumentException1);
          try
          {
            paramTtmlStyle.setColor(TtmlColorParser.parseColor(str));
          }
          catch (IllegalArgumentException localIllegalArgumentException2)
          {
            Log.w("TtmlParser", "failed parsing color value: '" + str + "'");
          }
          continue;
          paramTtmlStyle = createIfNull(localIllegalArgumentException2).setFontFamily(str);
          continue;
          paramTtmlStyle = localIllegalArgumentException2;
          try
          {
            TtmlStyle localTtmlStyle2 = createIfNull(localIllegalArgumentException2);
            paramTtmlStyle = localTtmlStyle2;
            parseFontSize(str, localTtmlStyle2);
            paramTtmlStyle = localTtmlStyle2;
          }
          catch (ParserException localParserException)
          {
            Log.w("TtmlParser", "failed parsing fontSize value: '" + str + "'");
          }
          continue;
          paramTtmlStyle = createIfNull(localParserException).setBold("bold".equalsIgnoreCase(str));
          continue;
          paramTtmlStyle = createIfNull(localParserException).setItalic("italic".equalsIgnoreCase(str));
          continue;
          paramTtmlStyle = Util.toLowerInvariant(str);
          switch (paramTtmlStyle.hashCode())
          {
          default: 
            label668:
            i = -1;
          }
          for (;;)
          {
            switch (i)
            {
            default: 
              paramTtmlStyle = localParserException;
              break;
            case 0: 
              paramTtmlStyle = createIfNull(localParserException).setTextAlign(Layout.Alignment.ALIGN_NORMAL);
              break;
              if (!paramTtmlStyle.equals("left")) {
                break label668;
              }
              i = 0;
              continue;
              if (!paramTtmlStyle.equals("start")) {
                break label668;
              }
              i = 1;
              continue;
              if (!paramTtmlStyle.equals("right")) {
                break label668;
              }
              i = 2;
              continue;
              if (!paramTtmlStyle.equals("end")) {
                break label668;
              }
              i = 3;
              continue;
              if (!paramTtmlStyle.equals("center")) {
                break label668;
              }
              i = 4;
            }
          }
          paramTtmlStyle = createIfNull(localParserException).setTextAlign(Layout.Alignment.ALIGN_NORMAL);
          continue;
          paramTtmlStyle = createIfNull(localParserException).setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
          continue;
          paramTtmlStyle = createIfNull(localParserException).setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
          continue;
          paramTtmlStyle = createIfNull(localParserException).setTextAlign(Layout.Alignment.ALIGN_CENTER);
          continue;
          paramTtmlStyle = Util.toLowerInvariant(str);
          switch (paramTtmlStyle.hashCode())
          {
          default: 
            label916:
            i = -1;
          }
          for (;;)
          {
            switch (i)
            {
            default: 
              paramTtmlStyle = localParserException;
              break;
            case 0: 
              paramTtmlStyle = createIfNull(localParserException).setLinethrough(true);
              break;
              if (!paramTtmlStyle.equals("linethrough")) {
                break label916;
              }
              i = 0;
              continue;
              if (!paramTtmlStyle.equals("nolinethrough")) {
                break label916;
              }
              i = 1;
              continue;
              if (!paramTtmlStyle.equals("underline")) {
                break label916;
              }
              i = 2;
              continue;
              if (!paramTtmlStyle.equals("nounderline")) {
                break label916;
              }
              i = 3;
            }
          }
          paramTtmlStyle = createIfNull(localParserException).setLinethrough(false);
          continue;
          paramTtmlStyle = createIfNull(localParserException).setUnderline(true);
          continue;
          paramTtmlStyle = createIfNull(localParserException).setUnderline(false);
        }
      }
    }
    return localParserException;
  }
  
  private String[] parseStyleIds(String paramString)
  {
    return paramString.split("\\s+");
  }
  
  private static long parseTimeExpression(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws ParserException
  {
    Matcher localMatcher = CLOCK_TIME.matcher(paramString);
    double d1;
    double d2;
    if (localMatcher.matches())
    {
      double d4 = Long.parseLong(localMatcher.group(1)) * 3600L;
      double d5 = Long.parseLong(localMatcher.group(2)) * 60L;
      double d6 = Long.parseLong(localMatcher.group(3));
      paramString = localMatcher.group(4);
      if (paramString != null)
      {
        d1 = Double.parseDouble(paramString);
        paramString = localMatcher.group(5);
        if (paramString == null) {
          break label153;
        }
        d2 = Long.parseLong(paramString) / paramInt1;
        label99:
        paramString = localMatcher.group(6);
        if (paramString == null) {
          break label159;
        }
      }
      label153:
      label159:
      for (double d3 = Long.parseLong(paramString) / paramInt2 / paramInt1;; d3 = 0.0D)
      {
        return (1000000.0D * (d4 + d5 + d6 + d1 + d2 + d3));
        d1 = 0.0D;
        break;
        d2 = 0.0D;
        break label99;
      }
    }
    localMatcher = OFFSET_TIME.matcher(paramString);
    if (localMatcher.matches())
    {
      d2 = Double.parseDouble(localMatcher.group(1));
      paramString = localMatcher.group(2);
      if (paramString.equals("h")) {
        d1 = d2 * 3600.0D;
      }
      for (;;)
      {
        return (1000000.0D * d1);
        if (paramString.equals("m"))
        {
          d1 = d2 * 60.0D;
        }
        else
        {
          d1 = d2;
          if (!paramString.equals("s")) {
            if (paramString.equals("ms"))
            {
              d1 = d2 / 1000.0D;
            }
            else if (paramString.equals("f"))
            {
              d1 = d2 / paramInt1;
            }
            else
            {
              d1 = d2;
              if (paramString.equals("t")) {
                d1 = d2 / paramInt3;
              }
            }
          }
        }
      }
    }
    throw new ParserException("Malformed time expression: " + paramString);
  }
  
  public boolean canParse(String paramString)
  {
    return "application/ttml+xml".equals(paramString);
  }
  
  public TtmlSubtitle parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ParserException
  {
    for (;;)
    {
      XmlPullParser localXmlPullParser;
      HashMap localHashMap;
      LinkedList localLinkedList;
      try
      {
        localXmlPullParser = this.xmlParserFactory.newPullParser();
        localHashMap = new HashMap();
        localXmlPullParser.setInput(new ByteArrayInputStream(paramArrayOfByte, paramInt1, paramInt2), null);
        paramArrayOfByte = null;
        localLinkedList = new LinkedList();
        paramInt2 = 0;
        i = localXmlPullParser.getEventType();
        if (i == 1) {
          break;
        }
        localTtmlNode1 = (TtmlNode)localLinkedList.peekLast();
        if (paramInt2 != 0) {
          break label370;
        }
        localObject = localXmlPullParser.getName();
        if (i != 2) {
          continue;
        }
        if (isSupportedTag((String)localObject)) {
          continue;
        }
        Log.i("TtmlParser", "Ignoring unsupported tag: " + localXmlPullParser.getName());
        paramInt1 = paramInt2 + 1;
        localObject = paramArrayOfByte;
      }
      catch (XmlPullParserException paramArrayOfByte)
      {
        TtmlNode localTtmlNode1;
        Object localObject;
        throw new ParserException("Unable to parse source", paramArrayOfByte);
        try
        {
          TtmlNode localTtmlNode2 = parseNode(localXmlPullParser, localTtmlNode1);
          localLinkedList.addLast(localTtmlNode2);
          localObject = paramArrayOfByte;
          paramInt1 = paramInt2;
          if (localTtmlNode1 == null) {
            continue;
          }
          localTtmlNode1.addChild(localTtmlNode2);
          localObject = paramArrayOfByte;
          paramInt1 = paramInt2;
        }
        catch (ParserException localParserException)
        {
          Log.w("TtmlParser", "Suppressing parser error", localParserException);
          paramInt1 = paramInt2 + 1;
          arrayOfByte = paramArrayOfByte;
        }
        continue;
        if (i != 4) {
          break label312;
        }
        localTtmlNode1.addChild(TtmlNode.buildTextNode(localXmlPullParser.getText()));
        arrayOfByte = paramArrayOfByte;
        paramInt1 = paramInt2;
        continue;
      }
      catch (IOException paramArrayOfByte)
      {
        throw new IllegalStateException("Unexpected error when reading input.", paramArrayOfByte);
      }
      localXmlPullParser.next();
      int i = localXmlPullParser.getEventType();
      paramArrayOfByte = (byte[])localObject;
      paramInt2 = paramInt1;
      continue;
      if ("head".equals(localObject))
      {
        parseHeader(localXmlPullParser, localHashMap);
        localObject = paramArrayOfByte;
        paramInt1 = paramInt2;
      }
      else
      {
        label312:
        byte[] arrayOfByte = paramArrayOfByte;
        paramInt1 = paramInt2;
        if (i == 3)
        {
          if (localXmlPullParser.getName().equals("tt")) {
            paramArrayOfByte = new TtmlSubtitle((TtmlNode)localLinkedList.getLast(), localHashMap);
          }
          localLinkedList.removeLast();
          arrayOfByte = paramArrayOfByte;
          paramInt1 = paramInt2;
          continue;
          label370:
          if (i == 2)
          {
            paramInt1 = paramInt2 + 1;
            arrayOfByte = paramArrayOfByte;
          }
          else
          {
            arrayOfByte = paramArrayOfByte;
            paramInt1 = paramInt2;
            if (i == 3)
            {
              paramInt1 = paramInt2 - 1;
              arrayOfByte = paramArrayOfByte;
            }
          }
        }
      }
    }
    return paramArrayOfByte;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/ttml/TtmlParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */