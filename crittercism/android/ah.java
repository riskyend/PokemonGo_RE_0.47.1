package crittercism.android;

import org.apache.http.util.CharArrayBuffer;

public final class ah
  extends af
{
  private ai d;
  private int e;
  private int f = 0;
  
  public ah(ai paramai, int paramInt)
  {
    super(paramai);
    this.d = paramai;
    this.e = paramInt;
  }
  
  public final boolean a(int paramInt)
  {
    if (this.f >= this.e + 2) {}
    do
    {
      do
      {
        return false;
        if (paramInt == -1)
        {
          this.a.b(a());
          this.a.a(as.d);
          return true;
        }
        this.c += 1;
        paramInt = (char)paramInt;
        this.f += 1;
      } while (this.f <= this.e);
      if (paramInt == 10)
      {
        this.d.b(a());
        this.a.a(this.d);
        return true;
      }
    } while ((this.f != this.e + 2) || (paramInt == 10));
    this.a.a(as.d);
    return true;
  }
  
  public final boolean a(CharArrayBuffer paramCharArrayBuffer)
  {
    return true;
  }
  
  public final af b()
  {
    return this.d;
  }
  
  public final af c()
  {
    return null;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ah.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */