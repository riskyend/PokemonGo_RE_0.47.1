package crittercism.android;

import java.io.IOException;
import java.io.InputStream;

public final class t
  extends InputStream
{
  private final InputStream a;
  private final e b;
  private final c c;
  
  public t(InputStream paramInputStream, e parame, c paramc)
  {
    if (paramInputStream == null) {
      throw new NullPointerException("delegate was null");
    }
    if (parame == null) {
      throw new NullPointerException("dispatch was null");
    }
    if (paramc == null) {
      throw new NullPointerException("stats were null");
    }
    this.a = paramInputStream;
    this.b = parame;
    this.c = paramc;
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    try
    {
      if (this.c != null)
      {
        if (paramInt1 == -1)
        {
          this.b.a(this.c);
          return;
        }
        this.c.a(paramInt2);
        return;
      }
    }
    catch (ThreadDeath localThreadDeath)
    {
      throw localThreadDeath;
    }
    catch (Throwable localThrowable)
    {
      dx.a(localThrowable);
    }
  }
  
  private void a(Exception paramException)
  {
    try
    {
      this.c.a(paramException);
      this.b.a(this.c);
      return;
    }
    catch (ThreadDeath paramException)
    {
      throw paramException;
    }
    catch (Throwable paramException)
    {
      dx.a(paramException);
    }
  }
  
  public final int available()
  {
    return this.a.available();
  }
  
  public final void close()
  {
    this.a.close();
  }
  
  public final void mark(int paramInt)
  {
    this.a.mark(paramInt);
  }
  
  public final boolean markSupported()
  {
    return this.a.markSupported();
  }
  
  public final int read()
  {
    try
    {
      int i = this.a.read();
      a(i, 1);
      return i;
    }
    catch (IOException localIOException)
    {
      a(localIOException);
      throw localIOException;
    }
  }
  
  public final int read(byte[] paramArrayOfByte)
  {
    try
    {
      int i = this.a.read(paramArrayOfByte);
      a(i, i);
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
      paramInt1 = this.a.read(paramArrayOfByte, paramInt1, paramInt2);
      a(paramInt1, paramInt1);
      return paramInt1;
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
      this.a.reset();
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
    paramLong = this.a.skip(paramLong);
    try
    {
      if (this.c != null) {
        this.c.a(paramLong);
      }
      return paramLong;
    }
    catch (ThreadDeath localThreadDeath)
    {
      throw localThreadDeath;
    }
    catch (Throwable localThrowable)
    {
      dx.a(localThrowable);
    }
    return paramLong;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */