package crittercism.android;

import org.apache.http.util.CharArrayBuffer;

public abstract class af
{
  al a;
  CharArrayBuffer b;
  protected int c;
  private int d;
  
  public af(af paramaf)
  {
    a(paramaf.a, paramaf.c);
  }
  
  public af(al paramal)
  {
    a(paramal, 0);
  }
  
  private void a(al paramal, int paramInt)
  {
    this.a = paramal;
    this.d = e();
    this.b = new CharArrayBuffer(d());
    this.c = paramInt;
  }
  
  private void g()
  {
    this.a.a(as.d);
  }
  
  public final int a()
  {
    return this.c;
  }
  
  public final void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = b(paramArrayOfByte, paramInt1, paramInt2);
    while ((i > 0) && (i < paramInt2))
    {
      int j = this.a.a().b(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      if (j <= 0) {
        break;
      }
      i += j;
    }
  }
  
  public boolean a(int paramInt)
  {
    if (paramInt == -1)
    {
      g();
      return true;
    }
    this.c += 1;
    char c1 = (char)paramInt;
    Object localObject;
    if (c1 == '\n') {
      if (a(this.b)) {
        localObject = b();
      }
    }
    for (;;)
    {
      if (localObject != this) {
        this.a.a((af)localObject);
      }
      if (localObject != this) {
        break;
      }
      return false;
      localObject = as.d;
      continue;
      if (this.b.length() < this.d)
      {
        this.b.append(c1);
        localObject = this;
      }
      else
      {
        localObject = c();
      }
    }
  }
  
  public abstract boolean a(CharArrayBuffer paramCharArrayBuffer);
  
  protected int b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    boolean bool = false;
    int i = -1;
    int j;
    if (paramInt2 == -1)
    {
      g();
      j = i;
    }
    do
    {
      do
      {
        return j;
        j = i;
      } while (paramArrayOfByte == null);
      j = i;
    } while (paramInt2 == 0);
    i = 0;
    for (;;)
    {
      j = i;
      if (bool) {
        break;
      }
      j = i;
      if (i >= paramInt2) {
        break;
      }
      bool = a((char)paramArrayOfByte[(paramInt1 + i)]);
      i += 1;
    }
  }
  
  public abstract af b();
  
  public final void b(int paramInt)
  {
    this.c = paramInt;
  }
  
  public abstract af c();
  
  protected abstract int d();
  
  protected abstract int e();
  
  public void f()
  {
    if (this.a != null) {
      this.a.a(as.d);
    }
  }
  
  public final String toString()
  {
    return this.b.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/af.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */