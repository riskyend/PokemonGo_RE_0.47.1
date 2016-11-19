package rx.internal.util;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

public final class PlatformDependent
{
  private static final int ANDROID_API_VERSION = ;
  public static final int ANDROID_API_VERSION_IS_NOT_ANDROID = 0;
  private static final boolean IS_ANDROID;
  
  static
  {
    if (ANDROID_API_VERSION != 0) {}
    for (boolean bool = true;; bool = false)
    {
      IS_ANDROID = bool;
      return;
    }
  }
  
  public static int getAndroidApiVersion()
  {
    return ANDROID_API_VERSION;
  }
  
  static ClassLoader getSystemClassLoader()
  {
    if (System.getSecurityManager() == null) {
      return ClassLoader.getSystemClassLoader();
    }
    (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
    {
      public ClassLoader run()
      {
        return ClassLoader.getSystemClassLoader();
      }
    });
  }
  
  public static boolean isAndroid()
  {
    return IS_ANDROID;
  }
  
  private static int resolveAndroidApiVersion()
  {
    try
    {
      int i = ((Integer)Class.forName("android.os.Build$VERSION", true, getSystemClassLoader()).getField("SDK_INT").get(null)).intValue();
      return i;
    }
    catch (Exception localException) {}
    return 0;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/PlatformDependent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */