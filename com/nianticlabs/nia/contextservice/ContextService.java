package com.nianticlabs.nia.contextservice;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public abstract class ContextService
{
  private static final Handler handler = new Handler(handlerThread.getLooper());
  private static final HandlerThread handlerThread = new HandlerThread("ContextService");
  private static final Handler mainHandler = new Handler(Looper.getMainLooper());
  protected final long NULL_POINTER = 0L;
  protected final Object callbackLock = new Object();
  protected final Context context;
  protected long nativeClassPointer;
  private Runnable runOnPause = new Runnable()
  {
    public void run()
    {
      ContextService.this.onPause();
    }
  };
  private Runnable runOnResume = new Runnable()
  {
    public void run()
    {
      ContextService.this.onResume();
    }
  };
  private Runnable runOnStart = new Runnable()
  {
    public void run()
    {
      ContextService.this.onStart();
    }
  };
  private Runnable runOnStop = new Runnable()
  {
    public void run()
    {
      ContextService.this.onStop();
    }
  };
  
  static
  {
    handlerThread.start();
  }
  
  public ContextService(Context paramContext, long paramLong)
  {
    this.context = paramContext;
    this.nativeClassPointer = paramLong;
  }
  
  public static void assertOnServiceThread()
  {
    if (!onServiceThread()) {
      throw new RuntimeException("Must be on the service thread");
    }
  }
  
  public static Handler getServiceHandler()
  {
    return handler;
  }
  
  public static Looper getServiceLooper()
  {
    return handlerThread.getLooper();
  }
  
  private void invokeOnPause()
  {
    runOnServiceHandler(this.runOnPause);
  }
  
  private void invokeOnResume()
  {
    runOnServiceHandler(this.runOnResume);
  }
  
  private void invokeOnStart()
  {
    runOnServiceHandler(this.runOnStart);
  }
  
  private void invokeOnStop()
  {
    runOnServiceHandler(this.runOnStop);
  }
  
  public static boolean onServiceThread()
  {
    return Looper.myLooper() == handlerThread.getLooper();
  }
  
  public static boolean onUiThread()
  {
    return Looper.myLooper() == Looper.getMainLooper();
  }
  
  public static void runOnServiceHandler(Runnable paramRunnable)
  {
    handler.post(paramRunnable);
  }
  
  public static void runOnUiThread(Runnable paramRunnable)
  {
    mainHandler.post(paramRunnable);
  }
  
  public static native void setActivityProviderClass(String paramString);
  
  public Context getContext()
  {
    return this.context;
  }
  
  public void onPause() {}
  
  public void onResume() {}
  
  public void onStart() {}
  
  public void onStop() {}
  
  public final void resetNativeClassPointer()
  {
    synchronized (this.callbackLock)
    {
      this.nativeClassPointer = 0L;
      return;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/contextservice/ContextService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */