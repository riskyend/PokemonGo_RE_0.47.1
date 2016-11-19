package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.Operator;
import rx.Producer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Action0;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.ImmediateScheduler;
import rx.schedulers.TrampolineScheduler;

public final class OperatorObserveOn<T>
  implements Observable.Operator<T, T>
{
  private final int bufferSize;
  private final boolean delayError;
  private final Scheduler scheduler;
  
  public OperatorObserveOn(Scheduler paramScheduler, boolean paramBoolean)
  {
    this(paramScheduler, paramBoolean, RxRingBuffer.SIZE);
  }
  
  public OperatorObserveOn(Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
    if (paramInt > 0) {}
    for (;;)
    {
      this.bufferSize = paramInt;
      return;
      paramInt = RxRingBuffer.SIZE;
    }
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    if ((this.scheduler instanceof ImmediateScheduler)) {}
    while ((this.scheduler instanceof TrampolineScheduler)) {
      return paramSubscriber;
    }
    paramSubscriber = new ObserveOnSubscriber(this.scheduler, paramSubscriber, this.delayError, this.bufferSize);
    paramSubscriber.init();
    return paramSubscriber;
  }
  
  private static final class ObserveOnSubscriber<T>
    extends Subscriber<T>
    implements Action0
  {
    final Subscriber<? super T> child;
    final AtomicLong counter = new AtomicLong();
    final boolean delayError;
    long emitted;
    Throwable error;
    volatile boolean finished;
    final int limit;
    final NotificationLite<T> on;
    final Queue<Object> queue;
    final Scheduler.Worker recursiveScheduler;
    final AtomicLong requested = new AtomicLong();
    
    public ObserveOnSubscriber(Scheduler paramScheduler, Subscriber<? super T> paramSubscriber, boolean paramBoolean, int paramInt)
    {
      this.child = paramSubscriber;
      this.recursiveScheduler = paramScheduler.createWorker();
      this.delayError = paramBoolean;
      this.on = NotificationLite.instance();
      if (paramInt > 0)
      {
        this.limit = (paramInt - (paramInt >> 2));
        if (!UnsafeAccess.isUnsafeAvailable()) {
          break label102;
        }
      }
      label102:
      for (this.queue = new SpscArrayQueue(paramInt);; this.queue = new SpscAtomicArrayQueue(paramInt))
      {
        request(paramInt);
        return;
        paramInt = RxRingBuffer.SIZE;
        break;
      }
    }
    
    public void call()
    {
      long l2 = 1L;
      long l1 = this.emitted;
      Queue localQueue = this.queue;
      Subscriber localSubscriber = this.child;
      NotificationLite localNotificationLite = this.on;
      long l3 = this.requested.get();
      for (;;)
      {
        Object localObject;
        if (l3 != l1)
        {
          bool2 = this.finished;
          localObject = localQueue.poll();
          if (localObject == null)
          {
            bool1 = true;
            if (!checkTerminated(bool2, bool1, localSubscriber, localQueue)) {
              break label86;
            }
          }
        }
        label86:
        while ((l3 == l1) && (checkTerminated(this.finished, localQueue.isEmpty(), localSubscriber, localQueue)))
        {
          boolean bool1;
          for (;;)
          {
            boolean bool2;
            return;
            bool1 = false;
          }
          if (!bool1) {
            break label147;
          }
        }
        this.emitted = l1;
        l3 = this.counter.addAndGet(-l2);
        l2 = l3;
        if (l3 != 0L) {
          break;
        }
        return;
        label147:
        localSubscriber.onNext(localNotificationLite.getValue(localObject));
        long l4 = l1 + 1L;
        l1 = l4;
        if (l4 == this.limit)
        {
          l3 = BackpressureUtils.produced(this.requested, l4);
          request(l4);
          l1 = 0L;
        }
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super T> paramSubscriber, Queue<Object> paramQueue)
    {
      if (paramSubscriber.isUnsubscribed())
      {
        paramQueue.clear();
        return true;
      }
      if (paramBoolean1)
      {
        if (!this.delayError) {
          break label74;
        }
        if (paramBoolean2)
        {
          paramQueue = this.error;
          if (paramQueue == null) {
            break label57;
          }
        }
      }
      label57:
      label74:
      do
      {
        for (;;)
        {
          try
          {
            paramSubscriber.onError(paramQueue);
            return false;
          }
          finally
          {
            this.recursiveScheduler.unsubscribe();
          }
          paramSubscriber.onCompleted();
        }
        Throwable localThrowable = this.error;
        if (localThrowable != null)
        {
          paramQueue.clear();
          try
          {
            paramSubscriber.onError(localThrowable);
            return true;
          }
          finally
          {
            this.recursiveScheduler.unsubscribe();
          }
        }
      } while (!paramBoolean2);
      try
      {
        paramSubscriber.onCompleted();
        return true;
      }
      finally
      {
        this.recursiveScheduler.unsubscribe();
      }
    }
    
    void init()
    {
      Subscriber localSubscriber = this.child;
      localSubscriber.setProducer(new Producer()
      {
        public void request(long paramAnonymousLong)
        {
          if (paramAnonymousLong > 0L)
          {
            BackpressureUtils.getAndAddRequest(OperatorObserveOn.ObserveOnSubscriber.this.requested, paramAnonymousLong);
            OperatorObserveOn.ObserveOnSubscriber.this.schedule();
          }
        }
      });
      localSubscriber.add(this.recursiveScheduler);
      localSubscriber.add(this);
    }
    
    public void onCompleted()
    {
      if ((isUnsubscribed()) || (this.finished)) {
        return;
      }
      this.finished = true;
      schedule();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if ((isUnsubscribed()) || (this.finished))
      {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.finished = true;
      schedule();
    }
    
    public void onNext(T paramT)
    {
      if ((isUnsubscribed()) || (this.finished)) {
        return;
      }
      if (!this.queue.offer(this.on.next(paramT)))
      {
        onError(new MissingBackpressureException());
        return;
      }
      schedule();
    }
    
    protected void schedule()
    {
      if (this.counter.getAndIncrement() == 0L) {
        this.recursiveScheduler.schedule(this);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorObserveOn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */