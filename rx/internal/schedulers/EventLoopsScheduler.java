package rx.internal.schedulers;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.util.RxThreadFactory;
import rx.internal.util.SubscriptionList;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public class EventLoopsScheduler
  extends Scheduler
  implements SchedulerLifecycle
{
  static final String KEY_MAX_THREADS = "rx.scheduler.max-computation-threads";
  static final int MAX_THREADS;
  static final FixedSchedulerPool NONE;
  static final PoolWorker SHUTDOWN_WORKER;
  static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory("RxComputationThreadPool-");
  private static final String THREAD_NAME_PREFIX = "RxComputationThreadPool-";
  final AtomicReference<FixedSchedulerPool> pool = new AtomicReference(NONE);
  
  static
  {
    int i = Integer.getInteger("rx.scheduler.max-computation-threads", 0).intValue();
    int j = Runtime.getRuntime().availableProcessors();
    if ((i <= 0) || (i > j)) {
      i = j;
    }
    for (;;)
    {
      MAX_THREADS = i;
      SHUTDOWN_WORKER = new PoolWorker(new RxThreadFactory("RxComputationShutdown-"));
      SHUTDOWN_WORKER.unsubscribe();
      NONE = new FixedSchedulerPool(0);
      return;
    }
  }
  
  public EventLoopsScheduler()
  {
    start();
  }
  
  public Scheduler.Worker createWorker()
  {
    return new EventLoopWorker(((FixedSchedulerPool)this.pool.get()).getEventLoop());
  }
  
  public Subscription scheduleDirect(Action0 paramAction0)
  {
    return ((FixedSchedulerPool)this.pool.get()).getEventLoop().scheduleActual(paramAction0, -1L, TimeUnit.NANOSECONDS);
  }
  
  public void shutdown()
  {
    FixedSchedulerPool localFixedSchedulerPool;
    do
    {
      localFixedSchedulerPool = (FixedSchedulerPool)this.pool.get();
      if (localFixedSchedulerPool == NONE) {
        return;
      }
    } while (!this.pool.compareAndSet(localFixedSchedulerPool, NONE));
    localFixedSchedulerPool.shutdown();
  }
  
  public void start()
  {
    FixedSchedulerPool localFixedSchedulerPool = new FixedSchedulerPool(MAX_THREADS);
    if (!this.pool.compareAndSet(NONE, localFixedSchedulerPool)) {
      localFixedSchedulerPool.shutdown();
    }
  }
  
  private static class EventLoopWorker
    extends Scheduler.Worker
  {
    private final SubscriptionList both = new SubscriptionList(new Subscription[] { this.serial, this.timed });
    private final EventLoopsScheduler.PoolWorker poolWorker;
    private final SubscriptionList serial = new SubscriptionList();
    private final CompositeSubscription timed = new CompositeSubscription();
    
    EventLoopWorker(EventLoopsScheduler.PoolWorker paramPoolWorker)
    {
      this.poolWorker = paramPoolWorker;
    }
    
    public boolean isUnsubscribed()
    {
      return this.both.isUnsubscribed();
    }
    
    public Subscription schedule(Action0 paramAction0)
    {
      if (isUnsubscribed()) {
        return Subscriptions.unsubscribed();
      }
      return this.poolWorker.scheduleActual(paramAction0, 0L, null, this.serial);
    }
    
    public Subscription schedule(Action0 paramAction0, long paramLong, TimeUnit paramTimeUnit)
    {
      if (isUnsubscribed()) {
        return Subscriptions.unsubscribed();
      }
      return this.poolWorker.scheduleActual(paramAction0, paramLong, paramTimeUnit, this.timed);
    }
    
    public void unsubscribe()
    {
      this.both.unsubscribe();
    }
  }
  
  static final class FixedSchedulerPool
  {
    final int cores;
    final EventLoopsScheduler.PoolWorker[] eventLoops;
    long n;
    
    FixedSchedulerPool(int paramInt)
    {
      this.cores = paramInt;
      this.eventLoops = new EventLoopsScheduler.PoolWorker[paramInt];
      int i = 0;
      while (i < paramInt)
      {
        this.eventLoops[i] = new EventLoopsScheduler.PoolWorker(EventLoopsScheduler.THREAD_FACTORY);
        i += 1;
      }
    }
    
    public EventLoopsScheduler.PoolWorker getEventLoop()
    {
      int i = this.cores;
      if (i == 0) {
        return EventLoopsScheduler.SHUTDOWN_WORKER;
      }
      EventLoopsScheduler.PoolWorker[] arrayOfPoolWorker = this.eventLoops;
      long l = this.n;
      this.n = (1L + l);
      return arrayOfPoolWorker[((int)(l % i))];
    }
    
    public void shutdown()
    {
      EventLoopsScheduler.PoolWorker[] arrayOfPoolWorker = this.eventLoops;
      int j = arrayOfPoolWorker.length;
      int i = 0;
      while (i < j)
      {
        arrayOfPoolWorker[i].unsubscribe();
        i += 1;
      }
    }
  }
  
  static final class PoolWorker
    extends NewThreadWorker
  {
    PoolWorker(ThreadFactory paramThreadFactory)
    {
      super();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/schedulers/EventLoopsScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */