package rx.internal.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.internal.schedulers.SchedulerLifecycle;
import rx.internal.util.unsafe.MpmcArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.schedulers.Schedulers;

public abstract class ObjectPool<T>
  implements SchedulerLifecycle
{
  final int maxSize;
  final int minSize;
  Queue<T> pool;
  private final AtomicReference<Scheduler.Worker> schedulerWorker;
  private final long validationInterval;
  
  public ObjectPool()
  {
    this(0, 0, 67L);
  }
  
  private ObjectPool(int paramInt1, int paramInt2, long paramLong)
  {
    this.minSize = paramInt1;
    this.maxSize = paramInt2;
    this.validationInterval = paramLong;
    this.schedulerWorker = new AtomicReference();
    initialize(paramInt1);
    start();
  }
  
  private void initialize(int paramInt)
  {
    if (UnsafeAccess.isUnsafeAvailable()) {}
    for (this.pool = new MpmcArrayQueue(Math.max(this.maxSize, 1024));; this.pool = new ConcurrentLinkedQueue())
    {
      int i = 0;
      while (i < paramInt)
      {
        this.pool.add(createObject());
        i += 1;
      }
    }
  }
  
  public T borrowObject()
  {
    Object localObject2 = this.pool.poll();
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = createObject();
    }
    return (T)localObject1;
  }
  
  protected abstract T createObject();
  
  public void returnObject(T paramT)
  {
    if (paramT == null) {
      return;
    }
    this.pool.offer(paramT);
  }
  
  public void shutdown()
  {
    Scheduler.Worker localWorker = (Scheduler.Worker)this.schedulerWorker.getAndSet(null);
    if (localWorker != null) {
      localWorker.unsubscribe();
    }
  }
  
  public void start()
  {
    Scheduler.Worker localWorker = Schedulers.computation().createWorker();
    if (this.schedulerWorker.compareAndSet(null, localWorker))
    {
      localWorker.schedulePeriodically(new Action0()
      {
        public void call()
        {
          int j = ObjectPool.this.pool.size();
          int k;
          int i;
          if (j < ObjectPool.this.minSize)
          {
            k = ObjectPool.this.maxSize;
            i = 0;
            while (i < k - j)
            {
              ObjectPool.this.pool.add(ObjectPool.this.createObject());
              i += 1;
            }
          }
          if (j > ObjectPool.this.maxSize)
          {
            k = ObjectPool.this.maxSize;
            i = 0;
            while (i < j - k)
            {
              ObjectPool.this.pool.poll();
              i += 1;
            }
          }
        }
      }, this.validationInterval, this.validationInterval, TimeUnit.SECONDS);
      return;
    }
    localWorker.unsubscribe();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/ObjectPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */