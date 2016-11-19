package crittercism.android;

import android.util.Log;
import java.util.concurrent.ExecutorService;

public final class dx
{
  public static a a = a.a;
  private static ec b;
  
  public static void a() {}
  
  public static void a(ec paramec)
  {
    b = paramec;
  }
  
  public static void a(String paramString)
  {
    Log.i("Crittercism", paramString);
  }
  
  public static void a(String paramString, Throwable paramThrowable)
  {
    Log.e("Crittercism", paramString, paramThrowable);
  }
  
  public static void a(Throwable paramThrowable)
  {
    if (!(paramThrowable instanceof cp)) {}
    try
    {
      ec localec = b;
      if ((b != null) && (a == a.b))
      {
        localec = b;
        paramThrowable = new ec.1(localec, paramThrowable, Thread.currentThread().getId());
        if (!localec.c.a(paramThrowable)) {
          localec.b.execute(paramThrowable);
        }
      }
      return;
    }
    catch (ThreadDeath paramThrowable)
    {
      throw paramThrowable;
    }
    catch (Throwable paramThrowable) {}
  }
  
  public static void b() {}
  
  public static void b(String paramString)
  {
    Log.e("Crittercism", paramString);
  }
  
  public static void b(String paramString, Throwable paramThrowable)
  {
    Log.w("Crittercism", paramString, paramThrowable);
  }
  
  public static void c() {}
  
  public static void c(String paramString)
  {
    Log.w("Crittercism", paramString);
  }
  
  public static enum a {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */