package crittercism.android;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.message.BasicLineParser;
import org.apache.http.util.CharArrayBuffer;

public abstract class ak
  extends af
{
  boolean d = false;
  int e;
  boolean f = false;
  private boolean g = false;
  private boolean h = false;
  
  public ak(af paramaf)
  {
    super(paramaf);
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    int i = this.b.length();
    if ((i == 0) || ((i == 1) && (this.b.charAt(0) == '\r')))
    {
      i = 1;
      if (i == 0) {
        break label48;
      }
      this.h = true;
    }
    for (;;)
    {
      return true;
      i = 0;
      break;
      try
      {
        label48:
        paramCharArrayBuffer = BasicLineParser.DEFAULT.parseHeader(paramCharArrayBuffer);
        if ((!this.d) && (paramCharArrayBuffer.getName().equalsIgnoreCase("content-length")))
        {
          i = Integer.parseInt(paramCharArrayBuffer.getValue());
          if (i < 0) {
            return false;
          }
          this.d = true;
          this.e = i;
          return true;
        }
        if (paramCharArrayBuffer.getName().equalsIgnoreCase("transfer-encoding"))
        {
          this.f = paramCharArrayBuffer.getValue().equalsIgnoreCase("chunked");
          return true;
        }
        if ((!this.g) && (paramCharArrayBuffer.getName().equalsIgnoreCase("host")))
        {
          paramCharArrayBuffer = paramCharArrayBuffer.getValue();
          if (paramCharArrayBuffer != null)
          {
            this.g = true;
            this.a.a(paramCharArrayBuffer);
            return true;
          }
        }
      }
      catch (ParseException paramCharArrayBuffer)
      {
        return false;
      }
      catch (NumberFormatException paramCharArrayBuffer) {}
    }
    return false;
  }
  
  public final af b()
  {
    if (this.h) {
      return g();
    }
    this.b.clear();
    return this;
  }
  
  public final af c()
  {
    this.b.clear();
    return new ar(this);
  }
  
  protected final int d()
  {
    return 32;
  }
  
  protected final int e()
  {
    return 128;
  }
  
  protected abstract af g();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ak.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */