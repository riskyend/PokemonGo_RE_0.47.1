package com.upsight.mediation.log;

import android.os.Build.VERSION;
import android.util.Log;
import android.webkit.WebView;

public class FuseLog
{
  private static boolean INTERNAL;
  public static boolean LOG = true;
  private static LogBuffer buffer = new LogBuffer(100, 2000);
  public static boolean debug = false;
  private static boolean testingMode;
  public static boolean veryDebug = false;
  
  static
  {
    INTERNAL = false;
    testingMode = false;
  }
  
  public static void TOAST(String paramString)
  {
    if (testingMode) {}
  }
  
  public static void clearBuffer()
  {
    if (INTERNAL) {
      buffer = new LogBuffer(200, 2000);
    }
  }
  
  public static void d(String paramString1, String paramString2)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("d", paramString1, paramString2);
    } while (!veryDebug);
    Log.d(paramString1, paramString2);
  }
  
  public static void disableForTests()
  {
    testingMode = true;
  }
  
  public static void e(String paramString1, String paramString2)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("e", paramString1, paramString2);
    } while (!debug);
    Log.e(paramString1, paramString2);
  }
  
  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("e", paramString1, paramString2);
    } while (!debug);
    Log.e(paramString1, paramString2, paramThrowable);
  }
  
  public static void enableInternalLogging()
  {
    buffer = new LogBuffer(200, 2000);
    debug = true;
    veryDebug = true;
    INTERNAL = true;
    if (Build.VERSION.SDK_INT >= 19) {
      WebView.setWebContentsDebuggingEnabled(true);
    }
  }
  
  public static String[] getLogHistory()
  {
    return buffer.getLog();
  }
  
  public static void i(String paramString1, String paramString2)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("i", paramString1, paramString2);
    } while (!debug);
    Log.i(paramString1, paramString2);
  }
  
  public static void internal(String paramString1, String paramString2)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("INTERNAL", paramString1, paramString2);
    } while (!INTERNAL);
    Log.i(paramString1, "INTERNAL | " + paramString2);
  }
  
  public static void public_e(String paramString1, String paramString2)
  {
    if (testingMode) {
      return;
    }
    buffer.append("e", paramString1, paramString2);
    Log.e(paramString1, paramString2);
  }
  
  public static void public_e(String paramString1, String paramString2, Exception paramException)
  {
    if (testingMode) {
      return;
    }
    buffer.append("e", paramString1, paramString2);
    Log.e(paramString1, paramString2);
  }
  
  public static void public_w(String paramString1, String paramString2)
  {
    if (testingMode) {
      return;
    }
    buffer.append("w", paramString1, paramString2);
    Log.w(paramString1, paramString2);
  }
  
  public static void public_w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (testingMode) {
      return;
    }
    buffer.append("w", paramString1, paramString2);
    Log.w(paramString1, paramString2, paramThrowable);
  }
  
  public static void v(String paramString1, String paramString2)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("v", paramString1, paramString2);
    } while (!veryDebug);
    Log.v(paramString1, paramString2);
  }
  
  public static void w(String paramString1, String paramString2)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("w", paramString1, paramString2);
    } while (!debug);
    Log.w(paramString1, paramString2);
  }
  
  public static void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (testingMode) {}
    do
    {
      return;
      buffer.append("w", paramString1, paramString2);
    } while (!debug);
    Log.w(paramString1, paramString2, paramThrowable);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/log/FuseLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */