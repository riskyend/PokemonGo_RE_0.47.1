package rx.schedulers;

import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;

public final class ImmediateScheduler
  extends Scheduler
{
  private static final ImmediateScheduler INSTANCE = new ImmediateScheduler();
  
  static ImmediateScheduler instance()
  {
    return INSTANCE;
  }
  
  public Scheduler.Worker createWorker()
  {
    return new InnerImmediateScheduler();
  }
  
  private class InnerImmediateScheduler
    extends Scheduler.Worker
    implements Subscription
  {
    final BooleanSubscription innerSubscription = new BooleanSubscription();
    
    InnerImmediateScheduler() {}
    
    public boolean isUnsubscribed()
    {
      return this.innerSubscription.isUnsubscribed();
    }
    
    public Subscription schedule(Action0 paramAction0)
    {
      paramAction0.call();
      return Subscriptions.unsubscribed();
    }
    
    public Subscription schedule(Action0 paramAction0, long paramLong, TimeUnit paramTimeUnit)
    {
      return schedule(new SleepingAction(paramAction0, this, ImmediateScheduler.this.now() + paramTimeUnit.toMillis(paramLong)));
    }
    
    public void unsubscribe()
    {
      this.innerSubscription.unsubscribe();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/schedulers/ImmediateScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */