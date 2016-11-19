package rx.internal.util;

import java.util.concurrent.atomic.AtomicLong;
import rx.Producer;
import rx.annotations.Experimental;

@Experimental
public final class BackpressureDrainManager
  extends AtomicLong
  implements Producer
{
  protected final BackpressureQueueCallback actual;
  protected boolean emitting;
  protected Throwable exception;
  protected volatile boolean terminated;
  
  public BackpressureDrainManager(BackpressureQueueCallback paramBackpressureQueueCallback)
  {
    this.actual = paramBackpressureQueueCallback;
  }
  
  public final void drain()
  {
    boolean bool1;
    long l1;
    int n;
    int m;
    int i;
    BackpressureQueueCallback localBackpressureQueueCallback;
    try
    {
      if (this.emitting) {
        return;
      }
      this.emitting = true;
      bool1 = this.terminated;
      l1 = get();
      n = 0;
      m = 0;
      i = n;
    }
    finally {}
    try
    {
      localBackpressureQueueCallback = this.actual;
    }
    finally
    {
      label115:
      if (i != 0) {
        break label311;
      }
    }
    if (bool1)
    {
      i = n;
      if (localBackpressureQueueCallback.peek() == null)
      {
        i = 1;
        localBackpressureQueueCallback.complete(this.exception);
        if (1 != 0) {
          break label389;
        }
        try
        {
          this.emitting = false;
          return;
        }
        finally {}
      }
      if (l1 == 0L)
      {
        i = n;
        i = m;
      }
    }
    for (;;)
    {
      int k;
      int j;
      try
      {
        bool1 = this.terminated;
        i = m;
        if (((BackpressureQueueCallback)localObject2).peek() != null)
        {
          k = 1;
          i = m;
          if (get() != Long.MAX_VALUE) {
            break label314;
          }
          if ((k != 0) || (bool1)) {
            continue;
          }
          j = 1;
          i = j;
          this.emitting = false;
          i = j;
          if (1 != 0) {
            break label389;
          }
          try
          {
            this.emitting = false;
            return;
          }
          finally {}
          i = n;
          Object localObject9 = ((BackpressureQueueCallback)localObject3).poll();
          if (localObject9 == null) {
            break label115;
          }
          i = n;
          boolean bool2 = ((BackpressureQueueCallback)localObject3).accept(localObject9);
          if (bool2)
          {
            if (1 != 0) {
              break label389;
            }
            try
            {
              this.emitting = false;
              return;
            }
            finally {}
          }
          l1 -= 1L;
          j += 1;
          break label392;
        }
        k = 0;
        continue;
        l1 = Long.MAX_VALUE;
        i = m;
      }
      finally {}
      try
      {
        this.emitting = false;
        label311:
        throw ((Throwable)localObject6);
        label314:
        l1 = -j;
        i = m;
        l2 = addAndGet(l1);
        if (l2 != 0L)
        {
          l1 = l2;
          if (k != 0) {
            continue;
          }
          break label407;
          j = 1;
          i = j;
          this.emitting = false;
          i = j;
          if (1 == 0) {
            try
            {
              this.emitting = false;
              return;
            }
            finally {}
          }
        }
      }
      finally
      {
        long l2;
        label389:
        label392:
        label407:
        do
        {
          throw ((Throwable)localObject8);
          return;
          j = 0;
          if (l1 > 0L) {
            break;
          }
          if (!bool1) {
            break label115;
          }
          break;
        } while (!bool1);
        l1 = l2;
      }
    }
  }
  
  public final boolean isTerminated()
  {
    return this.terminated;
  }
  
  public final void request(long paramLong)
  {
    if (paramLong == 0L) {
      return;
    }
    label31:
    label70:
    label98:
    for (;;)
    {
      long l2 = get();
      if (l2 == 0L) {}
      for (int i = 1; l2 == Long.MAX_VALUE; i = 0)
      {
        if (i == 0) {
          break label70;
        }
        drain();
        return;
      }
      long l1;
      if (paramLong == Long.MAX_VALUE)
      {
        l1 = paramLong;
        i = 1;
      }
      for (;;)
      {
        if (!compareAndSet(l2, l1)) {
          break label98;
        }
        break label31;
        break;
        if (l2 > Long.MAX_VALUE - paramLong) {
          l1 = Long.MAX_VALUE;
        } else {
          l1 = l2 + paramLong;
        }
      }
    }
  }
  
  public final void terminate()
  {
    this.terminated = true;
  }
  
  public final void terminate(Throwable paramThrowable)
  {
    if (!this.terminated)
    {
      this.exception = paramThrowable;
      this.terminated = true;
    }
  }
  
  public final void terminateAndDrain()
  {
    this.terminated = true;
    drain();
  }
  
  public final void terminateAndDrain(Throwable paramThrowable)
  {
    if (!this.terminated)
    {
      this.exception = paramThrowable;
      this.terminated = true;
      drain();
    }
  }
  
  public static abstract interface BackpressureQueueCallback
  {
    public abstract boolean accept(Object paramObject);
    
    public abstract void complete(Throwable paramThrowable);
    
    public abstract Object peek();
    
    public abstract Object poll();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/BackpressureDrainManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */