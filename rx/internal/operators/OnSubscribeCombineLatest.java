package rx.internal.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.functions.FuncN;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.atomic.SpscLinkedArrayQueue;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

public final class OnSubscribeCombineLatest<T, R>
  implements Observable.OnSubscribe<R>
{
  final int bufferSize;
  final FuncN<? extends R> combiner;
  final boolean delayError;
  final Observable<? extends T>[] sources;
  final Iterable<? extends Observable<? extends T>> sourcesIterable;
  
  public OnSubscribeCombineLatest(Iterable<? extends Observable<? extends T>> paramIterable, FuncN<? extends R> paramFuncN)
  {
    this(null, paramIterable, paramFuncN, RxRingBuffer.SIZE, false);
  }
  
  public OnSubscribeCombineLatest(Observable<? extends T>[] paramArrayOfObservable, Iterable<? extends Observable<? extends T>> paramIterable, FuncN<? extends R> paramFuncN, int paramInt, boolean paramBoolean)
  {
    this.sources = paramArrayOfObservable;
    this.sourcesIterable = paramIterable;
    this.combiner = paramFuncN;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public void call(Subscriber<? super R> paramSubscriber)
  {
    Object localObject2 = this.sources;
    int j = 0;
    Object localObject1;
    int i;
    if (localObject2 == null) {
      if ((this.sourcesIterable instanceof List))
      {
        localObject1 = (List)this.sourcesIterable;
        localObject2 = (Observable[])((List)localObject1).toArray(new Observable[((List)localObject1).size()]);
        i = localObject2.length;
      }
    }
    while (i == 0)
    {
      paramSubscriber.onCompleted();
      return;
      localObject1 = new Observable[8];
      Iterator localIterator = this.sourcesIterable.iterator();
      for (;;)
      {
        i = j;
        localObject2 = localObject1;
        if (!localIterator.hasNext()) {
          break;
        }
        Observable localObservable = (Observable)localIterator.next();
        localObject2 = localObject1;
        if (j == localObject1.length)
        {
          localObject2 = new Observable[(j >> 2) + j];
          System.arraycopy(localObject1, 0, localObject2, 0, j);
        }
        localObject2[j] = localObservable;
        j += 1;
        localObject1 = localObject2;
      }
      i = localObject2.length;
    }
    new LatestCoordinator(paramSubscriber, this.combiner, i, this.bufferSize, this.delayError).subscribe((Observable[])localObject2);
  }
  
  static final class CombinerSubscriber<T, R>
    extends Subscriber<T>
  {
    boolean done;
    final int index;
    final NotificationLite<T> nl;
    final OnSubscribeCombineLatest.LatestCoordinator<T, R> parent;
    
    public CombinerSubscriber(OnSubscribeCombineLatest.LatestCoordinator<T, R> paramLatestCoordinator, int paramInt)
    {
      this.parent = paramLatestCoordinator;
      this.index = paramInt;
      this.nl = NotificationLite.instance();
      request(paramLatestCoordinator.bufferSize);
    }
    
    public void onCompleted()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.parent.combine(null, this.index);
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
        return;
      }
      this.parent.onError(paramThrowable);
      this.done = true;
      this.parent.combine(null, this.index);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      this.parent.combine(this.nl.next(paramT), this.index);
    }
    
    public void requestMore(long paramLong)
    {
      request(paramLong);
    }
  }
  
  static final class LatestCoordinator<T, R>
    extends AtomicInteger
    implements Producer, Subscription
  {
    static final Object MISSING = new Object();
    private static final long serialVersionUID = 8567835998786448817L;
    int active;
    final Subscriber<? super R> actual;
    final int bufferSize;
    volatile boolean cancelled;
    final FuncN<? extends R> combiner;
    int complete;
    final int count;
    final boolean delayError;
    volatile boolean done;
    final AtomicReference<Throwable> error;
    final Object[] latest;
    final SpscLinkedArrayQueue<Object> queue;
    final AtomicLong requested;
    final OnSubscribeCombineLatest.CombinerSubscriber<T, R>[] subscribers;
    
    public LatestCoordinator(Subscriber<? super R> paramSubscriber, FuncN<? extends R> paramFuncN, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.actual = paramSubscriber;
      this.combiner = paramFuncN;
      this.count = paramInt1;
      this.bufferSize = paramInt2;
      this.delayError = paramBoolean;
      this.latest = new Object[paramInt1];
      Arrays.fill(this.latest, MISSING);
      this.subscribers = new OnSubscribeCombineLatest.CombinerSubscriber[paramInt1];
      this.queue = new SpscLinkedArrayQueue(paramInt2);
      this.requested = new AtomicLong();
      this.error = new AtomicReference();
    }
    
    void cancel(Queue<?> paramQueue)
    {
      paramQueue.clear();
      paramQueue = this.subscribers;
      int j = paramQueue.length;
      int i = 0;
      while (i < j)
      {
        paramQueue[i].unsubscribe();
        i += 1;
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<?> paramSubscriber, Queue<?> paramQueue, boolean paramBoolean3)
    {
      if (this.cancelled)
      {
        cancel(paramQueue);
        return true;
      }
      if (paramBoolean1) {
        if (paramBoolean3)
        {
          if (paramBoolean2)
          {
            paramQueue = (Throwable)this.error.get();
            if (paramQueue != null)
            {
              paramSubscriber.onError(paramQueue);
              return true;
            }
            paramSubscriber.onCompleted();
            return true;
          }
        }
        else
        {
          Throwable localThrowable = (Throwable)this.error.get();
          if (localThrowable != null)
          {
            cancel(paramQueue);
            paramSubscriber.onError(localThrowable);
            return true;
          }
          if (paramBoolean2)
          {
            paramSubscriber.onCompleted();
            return true;
          }
        }
      }
      return false;
    }
    
    void combine(Object paramObject, int paramInt)
    {
      int j = 0;
      OnSubscribeCombineLatest.CombinerSubscriber localCombinerSubscriber = this.subscribers[paramInt];
      for (;;)
      {
        int m;
        Object localObject;
        try
        {
          m = this.latest.length;
          localObject = this.latest[paramInt];
          int k = this.active;
          i = k;
          if (localObject == MISSING)
          {
            i = k + 1;
            this.active = i;
          }
          k = this.complete;
          if (paramObject == null)
          {
            paramInt = k + 1;
            this.complete = paramInt;
            break label232;
            if (paramInt == m) {
              break label243;
            }
            paramInt = j;
            if (paramObject == null)
            {
              paramInt = j;
              if (localObject == MISSING) {
                break label243;
              }
            }
            if (paramInt != 0) {
              break label219;
            }
            if ((paramObject != null) && (i != 0))
            {
              this.queue.offer(localCombinerSubscriber, this.latest.clone());
              label134:
              if ((i != 0) || (paramObject == null)) {
                break label227;
              }
              localCombinerSubscriber.requestMore(1L);
            }
          }
          else
          {
            this.latest[paramInt] = localCombinerSubscriber.nl.getValue(paramObject);
            paramInt = k;
          }
        }
        finally {}
        label219:
        label227:
        label232:
        do
        {
          i = 0;
          break;
          if ((paramObject != null) || (this.error.get() == null) || ((localObject != MISSING) && (this.delayError))) {
            break label134;
          }
          this.done = true;
          break label134;
          this.done = true;
          break label134;
          drain();
          return;
        } while (i != m);
        int i = 1;
        continue;
        label243:
        paramInt = 1;
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        label7:
        return;
      }
      SpscLinkedArrayQueue localSpscLinkedArrayQueue;
      Subscriber localSubscriber;
      boolean bool2;
      AtomicLong localAtomicLong;
      int i;
      while (checkTerminated(this.done, localSpscLinkedArrayQueue.isEmpty(), localSubscriber, localSpscLinkedArrayQueue, bool2))
      {
        localSpscLinkedArrayQueue = this.queue;
        localSubscriber = this.actual;
        bool2 = this.delayError;
        localAtomicLong = this.requested;
        i = 1;
      }
      long l2 = localAtomicLong.get();
      int j;
      label76:
      long l1;
      if (l2 == Long.MAX_VALUE)
      {
        j = 1;
        l1 = 0L;
      }
      for (;;)
      {
        boolean bool3;
        OnSubscribeCombineLatest.CombinerSubscriber localCombinerSubscriber;
        if (l2 != 0L)
        {
          bool3 = this.done;
          localCombinerSubscriber = (OnSubscribeCombineLatest.CombinerSubscriber)localSpscLinkedArrayQueue.peek();
          if (localCombinerSubscriber != null) {
            break label169;
          }
        }
        label169:
        for (boolean bool1 = true;; bool1 = false)
        {
          if (checkTerminated(bool3, bool1, localSubscriber, localSpscLinkedArrayQueue, bool2)) {
            break label172;
          }
          if (!bool1) {
            break label174;
          }
          if ((l1 != 0L) && (j == 0)) {
            localAtomicLong.addAndGet(l1);
          }
          j = addAndGet(-i);
          i = j;
          if (j != 0) {
            break;
          }
          return;
          j = 0;
          break label76;
        }
        label172:
        break label7;
        label174:
        localSpscLinkedArrayQueue.poll();
        Object localObject = (Object[])localSpscLinkedArrayQueue.poll();
        if (localObject == null)
        {
          this.cancelled = true;
          cancel(localSpscLinkedArrayQueue);
          localSubscriber.onError(new IllegalStateException("Broken queue?! Sender received but not the array."));
          return;
        }
        try
        {
          localObject = this.combiner.call((Object[])localObject);
          localSubscriber.onNext(localObject);
          localCombinerSubscriber.requestMore(1L);
          l2 -= 1L;
          l1 -= 1L;
        }
        catch (Throwable localThrowable)
        {
          this.cancelled = true;
          cancel(localSpscLinkedArrayQueue);
          localSubscriber.onError(localThrowable);
        }
      }
    }
    
    public boolean isUnsubscribed()
    {
      return this.cancelled;
    }
    
    void onError(Throwable paramThrowable)
    {
      AtomicReference localAtomicReference = this.error;
      for (;;)
      {
        Throwable localThrowable = (Throwable)localAtomicReference.get();
        Object localObject;
        if (localThrowable != null) {
          if ((localThrowable instanceof CompositeException))
          {
            localObject = new ArrayList(((CompositeException)localThrowable).getExceptions());
            ((List)localObject).add(paramThrowable);
            localObject = new CompositeException((Collection)localObject);
          }
        }
        while (localAtomicReference.compareAndSet(localThrowable, localObject))
        {
          return;
          localObject = new CompositeException(Arrays.asList(new Throwable[] { localThrowable, paramThrowable }));
          continue;
          localObject = paramThrowable;
        }
      }
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= required but it was " + paramLong);
      }
      if (paramLong != 0L)
      {
        BackpressureUtils.getAndAddRequest(this.requested, paramLong);
        drain();
      }
    }
    
    public void subscribe(Observable<? extends T>[] paramArrayOfObservable)
    {
      OnSubscribeCombineLatest.CombinerSubscriber[] arrayOfCombinerSubscriber = this.subscribers;
      int j = arrayOfCombinerSubscriber.length;
      int i = 0;
      while (i < j)
      {
        arrayOfCombinerSubscriber[i] = new OnSubscribeCombineLatest.CombinerSubscriber(this, i);
        i += 1;
      }
      lazySet(0);
      this.actual.add(this);
      this.actual.setProducer(this);
      i = 0;
      for (;;)
      {
        if ((i >= j) || (this.cancelled)) {
          return;
        }
        paramArrayOfObservable[i].subscribe(arrayOfCombinerSubscriber[i]);
        i += 1;
      }
    }
    
    public void unsubscribe()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        if (getAndIncrement() == 0) {
          cancel(this.queue);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeCombineLatest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */