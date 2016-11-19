package com.nianticlabs.pokemongoplus;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;

class HandlerExecutor
  implements Executor
{
  private static final boolean reallyAssertOnThread = false;
  private final Handler handler;
  private final HandlerThread handlerThread;
  
  HandlerExecutor(String paramString)
  {
    this.handlerThread = new HandlerThread(paramString);
    this.handlerThread.start();
    this.handler = new Handler(this.handlerThread.getLooper());
  }
  
  public void execute(Runnable paramRunnable)
  {
    this.handler.post(paramRunnable);
  }
  
  public void maybeAssertOnThread()
  {
    if (!onThread()) {}
  }
  
  public boolean onThread()
  {
    return Looper.myLooper() == this.handlerThread.getLooper();
  }
  
  public void stop()
  {
    this.handlerThread.quitSafely();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/HandlerExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */