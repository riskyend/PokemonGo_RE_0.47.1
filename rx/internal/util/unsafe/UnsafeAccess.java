package rx.internal.util.unsafe;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public final class UnsafeAccess
{
  private static final boolean DISABLED_BY_USER;
  public static final Unsafe UNSAFE;
  
  static
  {
    boolean bool = true;
    if (System.getProperty("rx.unsafe-disable") != null) {}
    for (;;)
    {
      DISABLED_BY_USER = bool;
      Object localObject1 = null;
      try
      {
        Object localObject2 = Unsafe.class.getDeclaredField("theUnsafe");
        ((Field)localObject2).setAccessible(true);
        localObject2 = (Unsafe)((Field)localObject2).get(null);
        localObject1 = localObject2;
      }
      catch (Throwable localThrowable)
      {
        for (;;) {}
      }
      UNSAFE = (Unsafe)localObject1;
      return;
      bool = false;
    }
  }
  
  private UnsafeAccess()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static long addressOf(Class<?> paramClass, String paramString)
  {
    try
    {
      paramClass = paramClass.getDeclaredField(paramString);
      long l = UNSAFE.objectFieldOffset(paramClass);
      return l;
    }
    catch (NoSuchFieldException paramClass)
    {
      paramString = new InternalError();
      paramString.initCause(paramClass);
      throw paramString;
    }
  }
  
  public static boolean compareAndSwapInt(Object paramObject, long paramLong, int paramInt1, int paramInt2)
  {
    return UNSAFE.compareAndSwapInt(paramObject, paramLong, paramInt1, paramInt2);
  }
  
  public static int getAndAddInt(Object paramObject, long paramLong, int paramInt)
  {
    int i;
    do
    {
      i = UNSAFE.getIntVolatile(paramObject, paramLong);
    } while (!UNSAFE.compareAndSwapInt(paramObject, paramLong, i, i + paramInt));
    return i;
  }
  
  public static int getAndIncrementInt(Object paramObject, long paramLong)
  {
    int i;
    do
    {
      i = UNSAFE.getIntVolatile(paramObject, paramLong);
    } while (!UNSAFE.compareAndSwapInt(paramObject, paramLong, i, i + 1));
    return i;
  }
  
  public static int getAndSetInt(Object paramObject, long paramLong, int paramInt)
  {
    int i;
    do
    {
      i = UNSAFE.getIntVolatile(paramObject, paramLong);
    } while (!UNSAFE.compareAndSwapInt(paramObject, paramLong, i, paramInt));
    return i;
  }
  
  public static boolean isUnsafeAvailable()
  {
    return (UNSAFE != null) && (!DISABLED_BY_USER);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/UnsafeAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */