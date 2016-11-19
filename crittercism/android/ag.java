package crittercism.android;

import org.apache.http.util.CharArrayBuffer;

public final class ag
  extends af
{
  private int d;
  private int e = 0;
  
  public ag(af paramaf, int paramInt)
  {
    super(paramaf);
    this.d = paramInt;
  }
  
  public final boolean a(int paramInt)
  {
    if (paramInt == -1)
    {
      this.a.a(as.d);
      return true;
    }
    this.e += 1;
    this.c += 1;
    if (this.e == this.d)
    {
      this.a.b(a());
      af localaf = this.a.b();
      this.a.a(localaf);
      return true;
    }
    return false;
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    return true;
  }
  
  public final int b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 == -1)
    {
      this.a.a(as.d);
      return -1;
    }
    if (this.e + paramInt2 < this.d)
    {
      this.e += paramInt2;
      this.c += paramInt2;
      return paramInt2;
    }
    paramInt1 = this.d - this.e;
    this.c += paramInt1;
    this.a.b(a());
    this.a.a(this.a.b());
    return paramInt1;
  }
  
  public final af b()
  {
    return as.d;
  }
  
  public final af c()
  {
    return as.d;
  }
  
  protected final int d()
  {
    return 0;
  }
  
  protected final int e()
  {
    return 0;
  }
  
  public final void f()
  {
    this.a.b(a());
    this.a.a(as.d);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */