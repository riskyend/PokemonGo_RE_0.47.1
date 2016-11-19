package crittercism.android;

import org.apache.http.util.CharArrayBuffer;

public final class ar
  extends af
{
  private af d;
  
  public ar(af paramaf)
  {
    super(paramaf);
    this.d = paramaf;
  }
  
  public final boolean a(int paramInt)
  {
    if (paramInt == -1)
    {
      this.a.a(as.d);
      return true;
    }
    this.c += 1;
    if ((char)paramInt == '\n')
    {
      this.d.b(a());
      this.a.a(this.d);
      return true;
    }
    return false;
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    return true;
  }
  
  public final af b()
  {
    return this;
  }
  
  public final af c()
  {
    return this;
  }
  
  protected final int d()
  {
    return 0;
  }
  
  protected final int e()
  {
    return 0;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */