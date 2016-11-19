package rx.android.schedulers;

import android.os.Handler;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Action0;
import rx.internal.schedulers.ScheduledAction;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public final class HandlerScheduler
  extends Scheduler
{
  private final Handler handler;
  
  HandlerScheduler(Handler paramHandler)
  {
    this.handler = paramHandler;
  }
  
  public static HandlerScheduler from(Handler paramHandler)
  {
    if (paramHandler == null) {
      throw new NullPointerException("handler == null");
    }
    return new HandlerScheduler(paramHandler);
  }
  
  public Scheduler.Worker createWorker()
  {
    return new HandlerWorker(this.handler);
  }
  
  static class HandlerWorker
    extends Scheduler.Worker
  {
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final Handler handler;
    
    HandlerWorker(Handler paramHandler)
    {
      this.handler = paramHandler;
    }
    
    public boolean isUnsubscribed()
    {
      return this.compositeSubscription.isUnsubscribed();
    }
    
    public Subscription schedule(Action0 paramAction0)
    {
      return schedule(paramAction0, 0L, TimeUnit.MILLISECONDS);
    }
    
    public Subscription schedule(final Action0 paramAction0, long paramLong, TimeUnit paramTimeUnit)
    {
      if (this.compositeSubscription.isUnsubscribed()) {
        return Subscriptions.unsubscribed();
      }
      paramAction0 = new ScheduledAction(RxAndroidPlugins.getInstance().getSchedulersHook().onSchedule(paramAction0));
      paramAction0.addParent(this.compositeSubscription);
      this.compositeSubscription.add(paramAction0);
      this.handler.postDelayed(paramAction0, paramTimeUnit.toMillis(paramLong));
      paramAction0.add(Subscriptions.create(new Action0()
      {
        public void call()
        {
          HandlerScheduler.HandlerWorker.this.handler.removeCallbacks(paramAction0);
        }
      }));
      return paramAction0;
    }
    
    public void unsubscribe()
    {
      this.compositeSubscription.unsubscribe();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/android/schedulers/HandlerScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */