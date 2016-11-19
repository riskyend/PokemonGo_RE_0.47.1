package crittercism.android;

import org.apache.http.util.CharArrayBuffer;

public final class ai
  extends af
{
  private int d = -1;
  
  public ai(af paramaf)
  {
    super(paramaf);
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    int i = paramCharArrayBuffer.indexOf(59);
    int j = paramCharArrayBuffer.length();
    if (i > 0) {}
    for (;;)
    {
      try
      {
        this.d = Integer.parseInt(paramCharArrayBuffer.substringTrimmed(0, i), 16);
        return true;
      }
      catch (NumberFormatException paramCharArrayBuffer)
      {
        return false;
      }
      i = j;
    }
  }
  
  public final af b()
  {
    int i = this.d;
    if (this.d == 0) {
      return new aq(this);
    }
    this.b.clear();
    return new ah(this, this.d);
  }
  
  public final af c()
  {
    return as.d;
  }
  
  protected final int d()
  {
    return 16;
  }
  
  protected final int e()
  {
    return 256;
  }
  
  public final void f()
  {
    this.a.b(a());
    this.a.a(as.d);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ai.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */