package rx.schedulers;

import rx.Scheduler.Worker;
import rx.functions.Action0;

class SleepingAction
  implements Action0
{
  private final long execTime;
  private final Scheduler.Worker innerScheduler;
  private final Action0 underlying;
  
  public SleepingAction(Action0 paramAction0, Scheduler.Worker paramWorker, long paramLong)
  {
    this.underlying = paramAction0;
    this.innerScheduler = paramWorker;
    this.execTime = paramLong;
  }
  
  public void call()
  {
    if (this.innerScheduler.isUnsubscribed()) {}
    for (;;)
    {
      return;
      long l;
      if (this.execTime > this.innerScheduler.now())
      {
        l = this.execTime - this.innerScheduler.now();
        if (l <= 0L) {}
      }
      try
      {
        Thread.sleep(l);
        if (this.innerScheduler.isUnsubscribed()) {
          continue;
        }
        this.underlying.call();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        Thread.currentThread().interrupt();
        throw new RuntimeException(localInterruptedException);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/schedulers/SleepingAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */