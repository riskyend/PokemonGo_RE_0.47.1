package crittercism.android;

import org.apache.http.util.CharArrayBuffer;

public final class aq
  extends af
{
  private boolean d = false;
  
  public aq(af paramaf)
  {
    super(paramaf);
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    boolean bool = false;
    if (paramCharArrayBuffer.substringTrimmed(0, paramCharArrayBuffer.length()).length() == 0) {
      bool = true;
    }
    this.d = bool;
    return true;
  }
  
  public final af b()
  {
    if (this.d)
    {
      this.a.b(a());
      return this.a.b();
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
    return 8;
  }
  
  protected final int e()
  {
    return 128;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/aq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */