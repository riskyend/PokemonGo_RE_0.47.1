package rx.android;

import android.os.Looper;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public abstract class MainThreadSubscription
  implements Subscription
{
  private final AtomicBoolean unsubscribed = new AtomicBoolean();
  
  public static void verifyMainThread()
  {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      throw new IllegalStateException("Expected to be called on the main thread but was " + Thread.currentThread().getName());
    }
  }
  
  public final boolean isUnsubscribed()
  {
    return this.unsubscribed.get();
  }
  
  protected abstract void onUnsubscribe();
  
  public final void unsubscribe()
  {
    if (this.unsubscribed.compareAndSet(false, true))
    {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        onUnsubscribe();
      }
    }
    else {
      return;
    }
    AndroidSchedulers.mainThread().createWorker().schedule(new Action0()
    {
      public void call()
      {
        MainThreadSubscription.this.onUnsubscribe();
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/android/MainThreadSubscription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */