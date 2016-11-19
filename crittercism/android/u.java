package crittercism.android;

import java.io.OutputStream;

public final class u
  extends OutputStream
{
  private final OutputStream a;
  private final c b;
  
  public u(OutputStream paramOutputStream, c paramc)
  {
    if (paramOutputStream == null) {
      throw new NullPointerException("delegate was null");
    }
    if (paramc == null) {
      throw new NullPointerException("stats were null");
    }
    this.a = paramOutputStream;
    this.b = paramc;
  }
  
  public final void close()
  {
    this.a.close();
  }
  
  public final void flush()
  {
    this.a.flush();
  }
  
  public final void write(int paramInt)
  {
    try
    {
      if (this.b != null)
      {
        this.b.b();
        this.b.c(1L);
      }
      this.a.write(paramInt);
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
  
  public final void write(byte[] paramArrayOfByte)
  {
    if (this.b != null)
    {
      this.b.b();
      if (paramArrayOfByte != null) {
        this.b.c(paramArrayOfByte.length);
      }
    }
    this.a.write(paramArrayOfByte);
  }
  
  public final void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.b != null)
    {
      this.b.b();
      if (paramArrayOfByte != null) {
        this.b.c(paramInt2);
      }
    }
    this.a.write(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/u.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */