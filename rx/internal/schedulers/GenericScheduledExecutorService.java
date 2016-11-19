package rx.internal.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;
import rx.internal.util.RxThreadFactory;

public final class GenericScheduledExecutorService
  implements SchedulerLifecycle
{
  public static final GenericScheduledExecutorService INSTANCE = new GenericScheduledExecutorService();
  private static final ScheduledExecutorService[] NONE;
  private static final ScheduledExecutorService SHUTDOWN;
  private static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory("RxScheduledExecutorPool-");
  private static final String THREAD_NAME_PREFIX = "RxScheduledExecutorPool-";
  private static int roundRobin;
  private final AtomicReference<ScheduledExecutorService[]> executor = new AtomicReference(NONE);
  
  static
  {
    NONE = new ScheduledExecutorService[0];
    SHUTDOWN = Executors.newScheduledThreadPool(0);
    SHUTDOWN.shutdown();
  }
  
  private GenericScheduledExecutorService()
  {
    start();
  }
  
  public static ScheduledExecutorService getInstance()
  {
    ScheduledExecutorService[] arrayOfScheduledExecutorService = (ScheduledExecutorService[])INSTANCE.executor.get();
    if (arrayOfScheduledExecutorService == NONE) {
      return SHUTDOWN;
    }
    int j = roundRobin + 1;
    int i = j;
    if (j >= arrayOfScheduledExecutorService.length) {
      i = 0;
    }
    roundRobin = i;
    return arrayOfScheduledExecutorService[i];
  }
  
  public void shutdown()
  {
    ScheduledExecutorService[] arrayOfScheduledExecutorService = (ScheduledExecutorService[])this.executor.get();
    if (arrayOfScheduledExecutorService == NONE) {}
    for (;;)
    {
      return;
      if (!this.executor.compareAndSet(arrayOfScheduledExecutorService, NONE)) {
        break;
      }
      int j = arrayOfScheduledExecutorService.length;
      int i = 0;
      while (i < j)
      {
        ScheduledExecutorService localScheduledExecutorService = arrayOfScheduledExecutorService[i];
        NewThreadWorker.deregisterExecutor(localScheduledExecutorService);
        localScheduledExecutorService.shutdownNow();
        i += 1;
      }
    }
  }
  
  public void start()
  {
    int j = Runtime.getRuntime().availableProcessors();
    int i = j;
    if (j > 4) {
      i = j / 2;
    }
    j = i;
    if (i > 8) {
      j = 8;
    }
    ScheduledExecutorService[] arrayOfScheduledExecutorService = new ScheduledExecutorService[j];
    i = 0;
    while (i < j)
    {
      arrayOfScheduledExecutorService[i] = Executors.newScheduledThreadPool(1, THREAD_FACTORY);
      i += 1;
    }
    if (this.executor.compareAndSet(NONE, arrayOfScheduledExecutorService))
    {
      j = arrayOfScheduledExecutorService.length;
      i = 0;
      while (i < j)
      {
        ScheduledExecutorService localScheduledExecutorService = arrayOfScheduledExecutorService[i];
        if ((!NewThreadWorker.tryEnableCancelPolicy(localScheduledExecutorService)) && ((localScheduledExecutorService instanceof ScheduledThreadPoolExecutor))) {
          NewThreadWorker.registerExecutor((ScheduledThreadPoolExecutor)localScheduledExecutorService);
        }
        i += 1;
      }
    }
    j = arrayOfScheduledExecutorService.length;
    i = 0;
    while (i < j)
    {
      arrayOfScheduledExecutorService[i].shutdownNow();
      i += 1;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/schedulers/GenericScheduledExecutorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */