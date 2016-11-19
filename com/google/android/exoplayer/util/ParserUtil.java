package com.google.android.exoplayer.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ParserUtil
{
  public static boolean isEndTag(XmlPullParser paramXmlPullParser, String paramString)
    throws XmlPullParserException
  {
    return (paramXmlPullParser.getEventType() == 3) && (paramString.equals(paramXmlPullParser.getName()));
  }
  
  public static boolean isStartTag(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException
  {
    return paramXmlPullParser.getEventType() == 2;
  }
  
  public static boolean isStartTag(XmlPullParser paramXmlPullParser, String paramString)
    throws XmlPullParserException
  {
    return (paramXmlPullParser.getEventType() == 2) && (paramString.equals(paramXmlPullParser.getName()));
  }
  
  public static String removeNamespacePrefix(String paramString)
  {
    return paramString.replaceFirst("^.*:", "");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/util/ParserUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */