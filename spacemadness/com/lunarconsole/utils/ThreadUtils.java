package spacemadness.com.lunarconsole.utils;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils
{
  private final Handler handler = new Handler(Looper.getMainLooper());
  
  public static void cancel(Runnable paramRunnable)
  {
    Holder.INSTANCE.cancelRunnable(paramRunnable);
  }
  
  public static void cancelAll()
  {
    Holder.INSTANCE.cancelRunnables();
  }
  
  private void cancelRunnable(Runnable paramRunnable)
  {
    this.handler.removeCallbacks(paramRunnable);
  }
  
  private void cancelRunnables()
  {
    this.handler.removeCallbacks(null);
  }
  
  public static boolean isRunningOnMainThread()
  {
    return Thread.currentThread() == Looper.getMainLooper().getThread();
  }
  
  private void postRunnable(Runnable paramRunnable)
  {
    this.handler.post(paramRunnable);
  }
  
  private void postRunnable(Runnable paramRunnable, long paramLong)
  {
    this.handler.postDelayed(paramRunnable, paramLong);
  }
  
  public static void runOnUIThread(Runnable paramRunnable)
  {
    Holder.INSTANCE.postRunnable(paramRunnable);
  }
  
  public static void runOnUIThread(Runnable paramRunnable, long paramLong)
  {
    Holder.INSTANCE.postRunnable(paramRunnable, paramLong);
  }
  
  private static class Holder
  {
    private static final ThreadUtils INSTANCE = new ThreadUtils(null);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/utils/ThreadUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */