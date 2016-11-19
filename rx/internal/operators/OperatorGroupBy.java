package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Observable.OnSubscribe;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.producers.ProducerArbiter;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.UtilityFunctions;
import rx.observables.GroupedObservable;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.Subscriptions;

public final class OperatorGroupBy<T, K, V>
  implements Observable.Operator<GroupedObservable<K, V>, T>
{
  final int bufferSize;
  final boolean delayError;
  final Func1<? super T, ? extends K> keySelector;
  final Func1<? super T, ? extends V> valueSelector;
  
  public OperatorGroupBy(Func1<? super T, ? extends K> paramFunc1)
  {
    this(paramFunc1, UtilityFunctions.identity(), RxRingBuffer.SIZE, false);
  }
  
  public OperatorGroupBy(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11)
  {
    this(paramFunc1, paramFunc11, RxRingBuffer.SIZE, false);
  }
  
  public OperatorGroupBy(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11, int paramInt, boolean paramBoolean)
  {
    this.keySelector = paramFunc1;
    this.valueSelector = paramFunc11;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public Subscriber<? super T> call(Subscriber<? super GroupedObservable<K, V>> paramSubscriber)
  {
    final GroupBySubscriber localGroupBySubscriber = new GroupBySubscriber(paramSubscriber, this.keySelector, this.valueSelector, this.bufferSize, this.delayError);
    paramSubscriber.add(Subscriptions.create(new Action0()
    {
      public void call()
      {
        localGroupBySubscriber.cancel();
      }
    }));
    paramSubscriber.setProducer(localGroupBySubscriber.producer);
    return localGroupBySubscriber;
  }
  
  public static final class GroupByProducer
    implements Producer
  {
    final OperatorGroupBy.GroupBySubscriber<?, ?, ?> parent;
    
    public GroupByProducer(OperatorGroupBy.GroupBySubscriber<?, ?, ?> paramGroupBySubscriber)
    {
      this.parent = paramGroupBySubscriber;
    }
    
    public void request(long paramLong)
    {
      this.parent.requestMore(paramLong);
    }
  }
  
  public static final class GroupBySubscriber<T, K, V>
    extends Subscriber<T>
  {
    static final AtomicIntegerFieldUpdater<GroupBySubscriber> CANCELLED;
    static final AtomicIntegerFieldUpdater<GroupBySubscriber> GROUP_COUNT = AtomicIntegerFieldUpdater.newUpdater(GroupBySubscriber.class, "groupCount");
    static final Object NULL_KEY = new Object();
    static final AtomicLongFieldUpdater<GroupBySubscriber> REQUESTED;
    static final AtomicIntegerFieldUpdater<GroupBySubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(GroupBySubscriber.class, "wip");
    final Subscriber<? super GroupedObservable<K, V>> actual;
    final int bufferSize;
    volatile int cancelled;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    volatile int groupCount;
    final Map<Object, OperatorGroupBy.GroupedUnicast<K, V>> groups;
    final Func1<? super T, ? extends K> keySelector;
    final OperatorGroupBy.GroupByProducer producer;
    final Queue<GroupedObservable<K, V>> queue;
    volatile long requested;
    final ProducerArbiter s;
    final Func1<? super T, ? extends V> valueSelector;
    volatile int wip;
    
    static
    {
      CANCELLED = AtomicIntegerFieldUpdater.newUpdater(GroupBySubscriber.class, "cancelled");
      REQUESTED = AtomicLongFieldUpdater.newUpdater(GroupBySubscriber.class, "requested");
    }
    
    public GroupBySubscriber(Subscriber<? super GroupedObservable<K, V>> paramSubscriber, Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11, int paramInt, boolean paramBoolean)
    {
      this.actual = paramSubscriber;
      this.keySelector = paramFunc1;
      this.valueSelector = paramFunc11;
      this.bufferSize = paramInt;
      this.delayError = paramBoolean;
      this.groups = new ConcurrentHashMap();
      this.queue = new ConcurrentLinkedQueue();
      GROUP_COUNT.lazySet(this, 1);
      this.s = new ProducerArbiter();
      this.s.request(paramInt);
      this.producer = new OperatorGroupBy.GroupByProducer(this);
    }
    
    public void cancel()
    {
      if ((CANCELLED.compareAndSet(this, 0, 1)) && (GROUP_COUNT.decrementAndGet(this) == 0)) {
        unsubscribe();
      }
    }
    
    public void cancel(K paramK)
    {
      if (paramK != null) {}
      for (;;)
      {
        if ((this.groups.remove(paramK) != null) && (GROUP_COUNT.decrementAndGet(this) == 0)) {
          unsubscribe();
        }
        return;
        paramK = NULL_KEY;
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super GroupedObservable<K, V>> paramSubscriber, Queue<?> paramQueue)
    {
      if (paramBoolean1)
      {
        Throwable localThrowable = this.error;
        if (localThrowable != null)
        {
          errorAll(paramSubscriber, paramQueue, localThrowable);
          return true;
        }
        if (paramBoolean2)
        {
          this.actual.onCompleted();
          return true;
        }
      }
      return false;
    }
    
    void drain()
    {
      if (WIP.getAndIncrement(this) != 0) {
        label10:
        return;
      }
      int i;
      Queue localQueue;
      Subscriber localSubscriber;
      while (checkTerminated(this.done, localQueue.isEmpty(), localSubscriber, localQueue))
      {
        i = 1;
        localQueue = this.queue;
        localSubscriber = this.actual;
      }
      long l2 = this.requested;
      int j;
      if (l2 == Long.MAX_VALUE) {
        j = 1;
      }
      label64:
      for (long l1 = 0L;; l1 -= 1L)
      {
        boolean bool2;
        GroupedObservable localGroupedObservable;
        if (l2 != 0L)
        {
          bool2 = this.done;
          localGroupedObservable = (GroupedObservable)localQueue.poll();
          if (localGroupedObservable != null) {
            break label169;
          }
        }
        label169:
        for (boolean bool1 = true;; bool1 = false)
        {
          if (checkTerminated(bool2, bool1, localSubscriber, localQueue)) {
            break label173;
          }
          if (!bool1) {
            break label175;
          }
          if (l1 != 0L)
          {
            if (j == 0) {
              REQUESTED.addAndGet(this, l1);
            }
            this.s.request(-l1);
          }
          j = WIP.addAndGet(this, -i);
          i = j;
          if (j != 0) {
            break;
          }
          return;
          j = 0;
          break label64;
        }
        label173:
        break label10;
        label175:
        localSubscriber.onNext(localGroupedObservable);
        l2 -= 1L;
      }
    }
    
    void errorAll(Subscriber<? super GroupedObservable<K, V>> paramSubscriber, Queue<?> paramQueue, Throwable paramThrowable)
    {
      paramQueue.clear();
      paramQueue = new ArrayList(this.groups.values());
      this.groups.clear();
      paramQueue = paramQueue.iterator();
      while (paramQueue.hasNext()) {
        ((OperatorGroupBy.GroupedUnicast)paramQueue.next()).onError(paramThrowable);
      }
      paramSubscriber.onError(paramThrowable);
    }
    
    public void onCompleted()
    {
      if (this.done) {
        return;
      }
      Iterator localIterator = this.groups.values().iterator();
      while (localIterator.hasNext()) {
        ((OperatorGroupBy.GroupedUnicast)localIterator.next()).onComplete();
      }
      this.groups.clear();
      this.done = true;
      GROUP_COUNT.decrementAndGet(this);
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      GROUP_COUNT.decrementAndGet(this);
      drain();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      Queue localQueue = this.queue;
      Subscriber localSubscriber = this.actual;
      for (;;)
      {
        try
        {
          Object localObject2 = this.keySelector.call(paramT);
          i = 1;
          if (localObject2 != null)
          {
            localObject1 = localObject2;
            OperatorGroupBy.GroupedUnicast localGroupedUnicast2 = (OperatorGroupBy.GroupedUnicast)this.groups.get(localObject1);
            localGroupedUnicast1 = localGroupedUnicast2;
            if (localGroupedUnicast2 == null)
            {
              if (this.cancelled != 0) {
                break;
              }
              localGroupedUnicast1 = OperatorGroupBy.GroupedUnicast.createWith(localObject2, this.bufferSize, this, this.delayError);
              this.groups.put(localObject1, localGroupedUnicast1);
              GROUP_COUNT.getAndIncrement(this);
              i = 0;
              localQueue.offer(localGroupedUnicast1);
              drain();
            }
          }
        }
        catch (Throwable paramT)
        {
          try
          {
            int i;
            OperatorGroupBy.GroupedUnicast localGroupedUnicast1;
            paramT = this.valueSelector.call(paramT);
            localGroupedUnicast1.onNext(paramT);
            if (i == 0) {
              break;
            }
            this.s.request(1L);
            return;
          }
          catch (Throwable paramT)
          {
            Object localObject1;
            unsubscribe();
            errorAll(localSubscriber, localQueue, paramT);
          }
          paramT = paramT;
          unsubscribe();
          errorAll(localSubscriber, localQueue, paramT);
          return;
        }
        localObject1 = NULL_KEY;
      }
    }
    
    public void requestMore(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= 0 required but it was " + paramLong);
      }
      BackpressureUtils.getAndAddRequest(REQUESTED, this, paramLong);
      drain();
    }
    
    public void setProducer(Producer paramProducer)
    {
      this.s.setProducer(paramProducer);
    }
  }
  
  static final class GroupedUnicast<K, T>
    extends GroupedObservable<K, T>
  {
    final OperatorGroupBy.State<T, K> state;
    
    protected GroupedUnicast(K paramK, OperatorGroupBy.State<T, K> paramState)
    {
      super(paramState);
      this.state = paramState;
    }
    
    public static <T, K> GroupedUnicast<K, T> createWith(K paramK, int paramInt, OperatorGroupBy.GroupBySubscriber<?, K, T> paramGroupBySubscriber, boolean paramBoolean)
    {
      return new GroupedUnicast(paramK, new OperatorGroupBy.State(paramInt, paramGroupBySubscriber, paramK, paramBoolean));
    }
    
    public void onComplete()
    {
      this.state.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.state.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.state.onNext(paramT);
    }
  }
  
  static final class State<T, K>
    extends AtomicInteger
    implements Producer, Subscription, Observable.OnSubscribe<T>
  {
    static final AtomicReferenceFieldUpdater<State, Subscriber> ACTUAL = AtomicReferenceFieldUpdater.newUpdater(State.class, Subscriber.class, "actual");
    static final AtomicIntegerFieldUpdater<State> CANCELLED;
    static final AtomicIntegerFieldUpdater<State> ONCE = AtomicIntegerFieldUpdater.newUpdater(State.class, "once");
    static final AtomicLongFieldUpdater<State> REQUESTED = AtomicLongFieldUpdater.newUpdater(State.class, "requested");
    private static final long serialVersionUID = -3852313036005250360L;
    volatile Subscriber<? super T> actual;
    volatile int cancelled;
    final boolean delayError;
    volatile boolean done;
    Throwable error;
    final K key;
    volatile int once;
    final OperatorGroupBy.GroupBySubscriber<?, K, T> parent;
    final Queue<Object> queue = new ConcurrentLinkedQueue();
    volatile long requested;
    
    static
    {
      CANCELLED = AtomicIntegerFieldUpdater.newUpdater(State.class, "cancelled");
    }
    
    public State(int paramInt, OperatorGroupBy.GroupBySubscriber<?, K, T> paramGroupBySubscriber, K paramK, boolean paramBoolean)
    {
      this.parent = paramGroupBySubscriber;
      this.key = paramK;
      this.delayError = paramBoolean;
    }
    
    public void call(Subscriber<? super T> paramSubscriber)
    {
      if (ONCE.compareAndSet(this, 0, 1))
      {
        paramSubscriber.add(this);
        paramSubscriber.setProducer(this);
        ACTUAL.lazySet(this, paramSubscriber);
        drain();
        return;
      }
      paramSubscriber.onError(new IllegalStateException("Only one Subscriber allowed!"));
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super T> paramSubscriber, boolean paramBoolean3)
    {
      if (this.cancelled != 0)
      {
        this.queue.clear();
        this.parent.cancel(this.key);
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable;
        if (paramBoolean3)
        {
          if (paramBoolean2)
          {
            localThrowable = this.error;
            if (localThrowable != null)
            {
              paramSubscriber.onError(localThrowable);
              return true;
            }
            paramSubscriber.onCompleted();
            return true;
          }
        }
        else
        {
          localThrowable = this.error;
          if (localThrowable != null)
          {
            this.queue.clear();
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
    
    void drain()
    {
      if (getAndIncrement() != 0) {}
      int i;
      Queue localQueue;
      boolean bool2;
      Subscriber localSubscriber;
      NotificationLite localNotificationLite;
      label33:
      do
      {
        return;
        i = 1;
        localQueue = this.queue;
        bool2 = this.delayError;
        localSubscriber = this.actual;
        localNotificationLite = NotificationLite.instance();
        if (localSubscriber == null) {
          break;
        }
      } while (checkTerminated(this.done, localQueue.isEmpty(), localSubscriber, bool2));
      long l2 = this.requested;
      int j;
      if (l2 == Long.MAX_VALUE) {
        j = 1;
      }
      label77:
      for (long l1 = 0L;; l1 -= 1L)
      {
        boolean bool3;
        Object localObject;
        if (l2 != 0L)
        {
          bool3 = this.done;
          localObject = localQueue.poll();
          if (localObject != null) {
            break label195;
          }
        }
        label195:
        for (boolean bool1 = true;; bool1 = false)
        {
          if (checkTerminated(bool3, bool1, localSubscriber, bool2)) {
            break label198;
          }
          if (!bool1) {
            break label200;
          }
          if (l1 != 0L)
          {
            if (j == 0) {
              REQUESTED.addAndGet(this, l1);
            }
            this.parent.s.request(-l1);
          }
          j = addAndGet(-i);
          if (j == 0) {
            break;
          }
          i = j;
          if (localSubscriber != null) {
            break label33;
          }
          localSubscriber = this.actual;
          i = j;
          break label33;
          j = 0;
          break label77;
        }
        label198:
        break;
        label200:
        localSubscriber.onNext(localNotificationLite.getValue(localObject));
        l2 -= 1L;
      }
    }
    
    public boolean isUnsubscribed()
    {
      return this.cancelled != 0;
    }
    
    public void onComplete()
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
    
    public void onNext(T paramT)
    {
      if (paramT == null)
      {
        this.error = new NullPointerException();
        this.done = true;
      }
      for (;;)
      {
        drain();
        return;
        this.queue.offer(NotificationLite.instance().next(paramT));
      }
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= required but it was " + paramLong);
      }
      if (paramLong != 0L)
      {
        BackpressureUtils.getAndAddRequest(REQUESTED, this, paramLong);
        drain();
      }
    }
    
    public void unsubscribe()
    {
      if ((CANCELLED.compareAndSet(this, 0, 1)) && (getAndIncrement() == 0)) {
        this.parent.cancel(this.key);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorGroupBy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */