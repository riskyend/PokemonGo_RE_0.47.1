package crittercism.android;

import java.io.IOException;
import java.io.InputStream;

public final class x
  extends InputStream
  implements al
{
  private ae a;
  private c b;
  private InputStream c;
  private e d;
  private af e;
  
  public x(ae paramae, InputStream paramInputStream, e parame)
  {
    if (paramae == null) {
      throw new NullPointerException("socket was null");
    }
    if (paramInputStream == null) {
      throw new NullPointerException("delegate was null");
    }
    if (parame == null) {
      throw new NullPointerException("dispatch was null");
    }
    this.a = paramae;
    this.c = paramInputStream;
    this.d = parame;
    this.e = b();
    if (this.e == null) {
      throw new NullPointerException("parser was null");
    }
  }
  
  private void a(Exception paramException)
  {
    try
    {
      c localc = e();
      localc.a(paramException);
      this.d.a(localc, c.a.h);
      return;
    }
    catch (ThreadDeath paramException)
    {
      throw paramException;
    }
    catch (Throwable paramException)
    {
      dx.a(paramException);
      return;
    }
    catch (IllegalStateException paramException) {}
  }
  
  private void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      this.e.a(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    catch (ThreadDeath paramArrayOfByte)
    {
      throw paramArrayOfByte;
    }
    catch (IllegalStateException paramArrayOfByte)
    {
      this.e = as.d;
      return;
    }
    catch (Throwable paramArrayOfByte)
    {
      this.e = as.d;
      dx.a(paramArrayOfByte);
    }
  }
  
  private c e()
  {
    if (this.b == null) {
      this.b = this.a.b();
    }
    if (this.b == null) {
      throw new IllegalStateException("No statistics were queued up.");
    }
    return this.b;
  }
  
  public final af a()
  {
    return this.e;
  }
  
  public final void a(int paramInt)
  {
    c localc = e();
    localc.c();
    localc.e = paramInt;
  }
  
  public final void a(af paramaf)
  {
    this.e = paramaf;
  }
  
  public final void a(String paramString) {}
  
  public final void a(String paramString1, String paramString2) {}
  
  public final boolean a(InputStream paramInputStream)
  {
    return this.c == paramInputStream;
  }
  
  public final int available()
  {
    return this.c.available();
  }
  
  public final af b()
  {
    return new ap(this);
  }
  
  public final void b(int paramInt)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    c localc = this.b;
    if (this.b != null)
    {
      int i = this.b.e;
      localObject1 = localObject2;
      if (i >= 100)
      {
        localObject1 = localObject2;
        if (i < 200)
        {
          localObject1 = new c(this.b.a());
          ((c)localObject1).e(this.b.a);
          ((c)localObject1).d(this.b.d);
          ((c)localObject1).f = this.b.f;
        }
      }
      this.b.b(paramInt);
      this.d.a(this.b, c.a.g);
    }
    this.b = ((c)localObject1);
  }
  
  public final String c()
  {
    return e().f;
  }
  
  public final void close()
  {
    try
    {
      this.e.f();
      this.c.close();
      return;
    }
    catch (ThreadDeath localThreadDeath)
    {
      throw localThreadDeath;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        dx.a(localThrowable);
      }
    }
  }
  
  public final void d()
  {
    if (this.b != null)
    {
      cn localcn = this.b.g;
      cm localcm = cm.a;
      if ((localcn.a != co.d.ordinal()) || (localcn.b != localcm.a())) {
        break label64;
      }
    }
    label64:
    for (int i = 1;; i = 0)
    {
      if ((i != 0) && (this.e != null)) {
        this.e.f();
      }
      return;
    }
  }
  
  public final void mark(int paramInt)
  {
    this.c.mark(paramInt);
  }
  
  public final boolean markSupported()
  {
    return this.c.markSupported();
  }
  
  public final int read()
  {
    try
    {
      int i = this.c.read();
      return i;
    }
    catch (IOException localIOException)
    {
      try
      {
        this.e.a(i);
        return i;
      }
      catch (ThreadDeath localThreadDeath)
      {
        throw localThreadDeath;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        this.e = as.d;
        return i;
      }
      catch (Throwable localThrowable)
      {
        this.e = as.d;
        dx.a(localThrowable);
      }
      localIOException = localIOException;
      a(localIOException);
      throw localIOException;
    }
  }
  
  public final int read(byte[] paramArrayOfByte)
  {
    try
    {
      int i = this.c.read(paramArrayOfByte);
      a(paramArrayOfByte, 0, i);
      return i;
    }
    catch (IOException paramArrayOfByte)
    {
      a(paramArrayOfByte);
      throw paramArrayOfByte;
    }
  }
  
  public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      paramInt2 = this.c.read(paramArrayOfByte, paramInt1, paramInt2);
      a(paramArrayOfByte, paramInt1, paramInt2);
      return paramInt2;
    }
    catch (IOException paramArrayOfByte)
    {
      a(paramArrayOfByte);
      throw paramArrayOfByte;
    }
  }
  
  public final void reset()
  {
    try
    {
      this.c.reset();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final long skip(long paramLong)
  {
    return this.c.skip(paramLong);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/x.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */