package crittercism.android;

import java.util.Locale;

public final class cg
{
  public static final cg a = new cg();
  private volatile int b = 1;
  private final long c = System.currentTimeMillis();
  
  private int b()
  {
    try
    {
      int i = this.b;
      this.b = (i + 1);
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String a()
  {
    return String.format(Locale.US, "%d.%d.%09d", new Object[] { Integer.valueOf(1), Long.valueOf(this.c), Integer.valueOf(b()) });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/cg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */