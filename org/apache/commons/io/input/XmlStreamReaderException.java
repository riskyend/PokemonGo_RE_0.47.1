package org.apache.commons.io.input;

import java.io.IOException;

public class XmlStreamReaderException
  extends IOException
{
  private static final long serialVersionUID = 1L;
  private final String bomEncoding;
  private final String contentTypeEncoding;
  private final String contentTypeMime;
  private final String xmlEncoding;
  private final String xmlGuessEncoding;
  
  public XmlStreamReaderException(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this(paramString1, null, null, paramString2, paramString3, paramString4);
  }
  
  public XmlStreamReaderException(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    super(paramString1);
    this.contentTypeMime = paramString2;
    this.contentTypeEncoding = paramString3;
    this.bomEncoding = paramString4;
    this.xmlGuessEncoding = paramString5;
    this.xmlEncoding = paramString6;
  }
  
  public String getBomEncoding()
  {
    return this.bomEncoding;
  }
  
  public String getContentTypeEncoding()
  {
    return this.contentTypeEncoding;
  }
  
  public String getContentTypeMime()
  {
    return this.contentTypeMime;
  }
  
  public String getXmlEncoding()
  {
    return this.xmlEncoding;
  }
  
  public String getXmlGuessEncoding()
  {
    return this.xmlGuessEncoding;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/input/XmlStreamReaderException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */