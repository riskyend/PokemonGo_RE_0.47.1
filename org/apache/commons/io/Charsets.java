package org.apache.commons.io;

import java.nio.charset.Charset;

public class Charsets
{
  public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
  public static final Charset US_ASCII = Charset.forName("US-ASCII");
  public static final Charset UTF_16 = Charset.forName("UTF-16");
  public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
  public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
  public static final Charset UTF_8 = Charset.forName("UTF-8");
  
  public static Charset toCharset(String paramString)
  {
    if (paramString == null) {
      return Charset.defaultCharset();
    }
    return Charset.forName(paramString);
  }
  
  public static Charset toCharset(Charset paramCharset)
  {
    Charset localCharset = paramCharset;
    if (paramCharset == null) {
      localCharset = Charset.defaultCharset();
    }
    return localCharset;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/Charsets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */