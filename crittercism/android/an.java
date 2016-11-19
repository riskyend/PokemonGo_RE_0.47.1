package crittercism.android;

import org.apache.http.ParseException;
import org.apache.http.RequestLine;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public final class an
  extends af
{
  public an(al paramal)
  {
    super(paramal);
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    ParserCursor localParserCursor = new ParserCursor(0, paramCharArrayBuffer.length());
    try
    {
      paramCharArrayBuffer = BasicLineParser.DEFAULT.parseRequestLine(paramCharArrayBuffer, localParserCursor);
      this.a.a(paramCharArrayBuffer.getMethod(), paramCharArrayBuffer.getUri());
      return true;
    }
    catch (ParseException paramCharArrayBuffer) {}
    return false;
  }
  
  public final af b()
  {
    return new am(this);
  }
  
  public final af c()
  {
    return as.d;
  }
  
  protected final int d()
  {
    return 64;
  }
  
  protected final int e()
  {
    return 2048;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/an.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */