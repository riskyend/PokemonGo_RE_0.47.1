package rx.internal.operators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable.Operator;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.subscriptions.Subscriptions;

public final class OperatorEagerConcatMap<T, R>
  implements Observable.Operator<R, T>
{
  final int bufferSize;
  final Func1<? super T, ? extends Observable<? extends R>> mapper;
  private final int maxConcurrent;
  
  public OperatorEagerConcatMap(Func1<? super T, ? extends Observable<? extends R>> paramFunc1, int paramInt1, int paramInt2)
  {
    this.mapper = paramFunc1;
    this.bufferSize = paramInt1;
    this.maxConcurrent = paramInt2;
  }
  
  public Subscriber<? super T> call(Subscriber<? super R> paramSubscriber)
  {
    paramSubscriber = new EagerOuterSubscriber(this.mapper, this.bufferSize, this.maxConcurrent, paramSubscriber);
    paramSubscriber.init();
    return paramSubscriber;
  }
  
  static final class EagerInnerSubscriber<T>
    extends Subscriber<T>
  {
    volatile boolean done;
    Throwable error;
    final NotificationLite<T> nl;
    final OperatorEagerConcatMap.EagerOuterSubscriber<?, T> parent;
    final Queue<Object> queue;
    
    public EagerInnerSubscriber(OperatorEagerConcatMap.EagerOuterSubscriber<?, T> paramEagerOuterSubscriber, int paramInt)
    {
      this.parent = paramEagerOuterSubscriber;
      if (UnsafeAccess.isUnsafeAvailable()) {}
      for (paramEagerOuterSubscriber = new SpscArrayQueue(paramInt);; paramEagerOuterSubscriber = new SpscAtomicArrayQueue(paramInt))
      {
        this.queue = paramEagerOuterSubscriber;
        this.nl = NotificationLite.instance();
        request(paramInt);
        return;
      }
    }
    
    public void onCompleted()
    {
      this.done = true;
      this.parent.drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      this.parent.drain();
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(this.nl.next(paramT));
      this.parent.drain();
    }
    
    void requestMore(long paramLong)
    {
      request(paramLong);
    }
  }
  
  static final class EagerOuterProducer
    extends AtomicLong
    implements Producer
  {
    private static final long serialVersionUID = -657299606803478389L;
    final OperatorEagerConcatMap.EagerOuterSubscriber<?, ?> parent;
    
    public EagerOuterProducer(OperatorEagerConcatMap.EagerOuterSubscriber<?, ?> paramEagerOuterSubscriber)
    {
      this.parent = paramEagerOuterSubscriber;
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalStateException("n >= 0 required but it was " + paramLong);
      }
      if (paramLong > 0L)
      {
        BackpressureUtils.getAndAddRequest(this, paramLong);
        this.parent.drain();
      }
    }
  }
  
  static final class EagerOuterSubscriber<T, R>
    extends Subscriber<T>
  {
    final Subscriber<? super R> actual;
    final int bufferSize;
    volatile boolean cancelled;
    volatile boolean done;
    Throwable error;
    final Func1<? super T, ? extends Observable<? extends R>> mapper;
    private OperatorEagerConcatMap.EagerOuterProducer sharedProducer;
    final LinkedList<OperatorEagerConcatMap.EagerInnerSubscriber<R>> subscribers;
    final AtomicInteger wip;
    
    public EagerOuterSubscriber(Func1<? super T, ? extends Observable<? extends R>> paramFunc1, int paramInt1, int paramInt2, Subscriber<? super R> paramSubscriber)
    {
      this.mapper = paramFunc1;
      this.bufferSize = paramInt1;
      this.actual = paramSubscriber;
      this.subscribers = new LinkedList();
      this.wip = new AtomicInteger();
      if (paramInt2 == Integer.MAX_VALUE) {}
      for (long l = Long.MAX_VALUE;; l = paramInt2)
      {
        request(l);
        return;
      }
    }
    
    void cleanup()
    {
      synchronized (this.subscribers)
      {
        ArrayList localArrayList = new ArrayList(this.subscribers);
        this.subscribers.clear();
        ??? = localArrayList.iterator();
        if (((Iterator)???).hasNext()) {
          ((Subscription)((Iterator)???).next()).unsubscribe();
        }
      }
    }
    
    void drain()
    {
      if (this.wip.getAndIncrement() != 0) {
        return;
      }
      int i = 1;
      OperatorEagerConcatMap.EagerOuterProducer localEagerOuterProducer = this.sharedProducer;
      Subscriber localSubscriber = this.actual;
      NotificationLite localNotificationLite = NotificationLite.instance();
      if (this.cancelled)
      {
        cleanup();
        return;
      }
      boolean bool = this.done;
      OperatorEagerConcatMap.EagerInnerSubscriber localEagerInnerSubscriber;
      int j;
      for (;;)
      {
        synchronized (this.subscribers)
        {
          localEagerInnerSubscriber = (OperatorEagerConcatMap.EagerInnerSubscriber)this.subscribers.peek();
          if (localEagerInnerSubscriber == null)
          {
            j = 1;
            if (!bool) {
              break label130;
            }
            ??? = this.error;
            if (??? == null) {
              break;
            }
            cleanup();
            localSubscriber.onError((Throwable)???);
            return;
          }
        }
        j = 0;
      }
      if (j != 0)
      {
        ((Subscriber)localObject1).onCompleted();
        return;
      }
      label130:
      long l2;
      long l1;
      Queue localQueue;
      int n;
      if (j == 0)
      {
        l2 = localEagerOuterProducer.get();
        l1 = 0L;
        if (l2 == Long.MAX_VALUE)
        {
          j = 1;
          localQueue = localEagerInnerSubscriber.queue;
          n = 0;
        }
      }
      for (;;)
      {
        bool = localEagerInnerSubscriber.done;
        ??? = localQueue.peek();
        int k;
        if (??? == null) {
          k = 1;
        }
        for (;;)
        {
          if (bool)
          {
            Throwable localThrowable2 = localEagerInnerSubscriber.error;
            if (localThrowable2 != null)
            {
              cleanup();
              ((Subscriber)localObject1).onError(localThrowable2);
              return;
              j = 0;
              break;
              k = 0;
              continue;
            }
            if (k == 0) {}
          }
        }
        do
        {
          do
          {
            synchronized (this.subscribers)
            {
              this.subscribers.poll();
              localEagerInnerSubscriber.unsubscribe();
              m = 1;
              request(1L);
              if (l1 != 0L)
              {
                if (j == 0) {
                  localEagerOuterProducer.addAndGet(l1);
                }
                if (m == 0) {
                  localEagerInnerSubscriber.requestMore(-l1);
                }
              }
              if (m != 0) {
                break;
              }
              j = this.wip.addAndGet(-i);
              i = j;
              if (j != 0) {
                break;
              }
              return;
            }
            m = n;
          } while (k != 0);
          int m = n;
        } while (l2 == 0L);
        localQueue.poll();
        try
        {
          localObserver.onNext(localNotificationLite.getValue(???));
          l2 -= 1L;
          l1 -= 1L;
        }
        catch (Throwable localThrowable1)
        {
          Exceptions.throwOrReport(localThrowable1, localObserver, ???);
        }
      }
    }
    
    void init()
    {
      this.sharedProducer = new OperatorEagerConcatMap.EagerOuterProducer(this);
      add(Subscriptions.create(new Action0()
      {
        public void call()
        {
          OperatorEagerConcatMap.EagerOuterSubscriber.this.cancelled = true;
          if (OperatorEagerConcatMap.EagerOuterSubscriber.this.wip.getAndIncrement() == 0) {
            OperatorEagerConcatMap.EagerOuterSubscriber.this.cleanup();
          }
        }
      }));
      this.actual.add(this);
      this.actual.setProducer(this.sharedProducer);
    }
    
    public void onCompleted()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T arg1)
    {
      OperatorEagerConcatMap.EagerInnerSubscriber localEagerInnerSubscriber;
      do
      {
        try
        {
          Observable localObservable = (Observable)this.mapper.call(???);
          localEagerInnerSubscriber = new OperatorEagerConcatMap.EagerInnerSubscriber(this, this.bufferSize);
          if (this.cancelled) {
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwOrReport(localThrowable, this.actual, ???);
          return;
        }
        synchronized (this.subscribers)
        {
          if (this.cancelled) {
            return;
          }
        }
        this.subscribers.add(localEagerInnerSubscriber);
      } while (this.cancelled);
      ((Observable)localObject).unsafeSubscribe(localEagerInnerSubscriber);
      drain();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorEagerConcatMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */