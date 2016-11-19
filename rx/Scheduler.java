package rx;

import java.util.concurrent.TimeUnit;
import rx.functions.Action0;
import rx.subscriptions.MultipleAssignmentSubscription;

public abstract class Scheduler
{
  static final long CLOCK_DRIFT_TOLERANCE_NANOS = TimeUnit.MINUTES.toNanos(Long.getLong("rx.scheduler.drift-tolerance", 15L).longValue());
  
  public abstract Worker createWorker();
  
  public long now()
  {
    return System.currentTimeMillis();
  }
  
  public static abstract class Worker
    implements Subscription
  {
    public long now()
    {
      return System.currentTimeMillis();
    }
    
    public abstract Subscription schedule(Action0 paramAction0);
    
    public abstract Subscription schedule(Action0 paramAction0, long paramLong, TimeUnit paramTimeUnit);
    
    public Subscription schedulePeriodically(Action0 paramAction0, long paramLong1, final long paramLong2, TimeUnit paramTimeUnit)
    {
      paramLong2 = paramTimeUnit.toNanos(paramLong2);
      final long l1 = TimeUnit.MILLISECONDS.toNanos(now());
      long l2 = paramTimeUnit.toNanos(paramLong1);
      final MultipleAssignmentSubscription localMultipleAssignmentSubscription1 = new MultipleAssignmentSubscription();
      paramAction0 = new Action0()
      {
        long count;
        long lastNowNanos = l1;
        long startInNanos = localMultipleAssignmentSubscription1;
        
        public void call()
        {
          long l2;
          long l1;
          long l3;
          if (!paramLong2.isUnsubscribed())
          {
            this.val$action.call();
            l2 = TimeUnit.MILLISECONDS.toNanos(Scheduler.Worker.this.now());
            if ((Scheduler.CLOCK_DRIFT_TOLERANCE_NANOS + l2 >= this.lastNowNanos) && (l2 < this.lastNowNanos + this.val$periodInNanos + Scheduler.CLOCK_DRIFT_TOLERANCE_NANOS)) {
              break label129;
            }
            l1 = l2 + this.val$periodInNanos;
            l3 = this.val$periodInNanos;
            long l4 = this.count + 1L;
            this.count = l4;
            this.startInNanos = (l1 - l3 * l4);
          }
          for (;;)
          {
            this.lastNowNanos = l2;
            paramLong2.set(Scheduler.Worker.this.schedule(this, l1 - l2, TimeUnit.NANOSECONDS));
            return;
            label129:
            l1 = this.startInNanos;
            l3 = this.count + 1L;
            this.count = l3;
            l1 += l3 * this.val$periodInNanos;
          }
        }
      };
      MultipleAssignmentSubscription localMultipleAssignmentSubscription2 = new MultipleAssignmentSubscription();
      localMultipleAssignmentSubscription1.set(localMultipleAssignmentSubscription2);
      localMultipleAssignmentSubscription2.set(schedule(paramAction0, paramLong1, paramTimeUnit));
      return localMultipleAssignmentSubscription1;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/Scheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */