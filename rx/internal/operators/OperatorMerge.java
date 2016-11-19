package rx.internal.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.exceptions.MissingBackpressureException;
import rx.exceptions.OnErrorThrowable;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.atomic.SpscExactAtomicArrayQueue;
import rx.internal.util.atomic.SpscUnboundedAtomicArrayQueue;
import rx.internal.util.unsafe.Pow2;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.subscriptions.CompositeSubscription;

public final class OperatorMerge<T>
  implements Observable.Operator<T, Observable<? extends T>>
{
  final boolean delayErrors;
  final int maxConcurrent;
  
  OperatorMerge(boolean paramBoolean, int paramInt)
  {
    this.delayErrors = paramBoolean;
    this.maxConcurrent = paramInt;
  }
  
  public static <T> OperatorMerge<T> instance(boolean paramBoolean)
  {
    if (paramBoolean) {
      return HolderDelayErrors.INSTANCE;
    }
    return HolderNoDelay.INSTANCE;
  }
  
  public static <T> OperatorMerge<T> instance(boolean paramBoolean, int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("maxConcurrent > 0 required but it was " + paramInt);
    }
    if (paramInt == Integer.MAX_VALUE) {
      return instance(paramBoolean);
    }
    return new OperatorMerge(paramBoolean, paramInt);
  }
  
  public Subscriber<Observable<? extends T>> call(Subscriber<? super T> paramSubscriber)
  {
    MergeSubscriber localMergeSubscriber = new MergeSubscriber(paramSubscriber, this.delayErrors, this.maxConcurrent);
    MergeProducer localMergeProducer = new MergeProducer(localMergeSubscriber);
    localMergeSubscriber.producer = localMergeProducer;
    paramSubscriber.add(localMergeSubscriber);
    paramSubscriber.setProducer(localMergeProducer);
    return localMergeSubscriber;
  }
  
  private static final class HolderDelayErrors
  {
    static final OperatorMerge<Object> INSTANCE = new OperatorMerge(true, Integer.MAX_VALUE);
  }
  
  private static final class HolderNoDelay
  {
    static final OperatorMerge<Object> INSTANCE = new OperatorMerge(false, Integer.MAX_VALUE);
  }
  
  static final class InnerSubscriber<T>
    extends Subscriber<T>
  {
    static final int limit = RxRingBuffer.SIZE / 4;
    volatile boolean done;
    final long id;
    int outstanding;
    final OperatorMerge.MergeSubscriber<T> parent;
    volatile RxRingBuffer queue;
    
    public InnerSubscriber(OperatorMerge.MergeSubscriber<T> paramMergeSubscriber, long paramLong)
    {
      this.parent = paramMergeSubscriber;
      this.id = paramLong;
    }
    
    public void onCompleted()
    {
      this.done = true;
      this.parent.emit();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.done = true;
      this.parent.getOrCreateErrorQueue().offer(paramThrowable);
      this.parent.emit();
    }
    
    public void onNext(T paramT)
    {
      this.parent.tryEmit(this, paramT);
    }
    
    public void onStart()
    {
      this.outstanding = RxRingBuffer.SIZE;
      request(RxRingBuffer.SIZE);
    }
    
    public void requestMore(long paramLong)
    {
      int i = this.outstanding - (int)paramLong;
      if (i > limit) {
        this.outstanding = i;
      }
      do
      {
        return;
        this.outstanding = RxRingBuffer.SIZE;
        i = RxRingBuffer.SIZE - i;
      } while (i <= 0);
      request(i);
    }
  }
  
  static final class MergeProducer<T>
    extends AtomicLong
    implements Producer
  {
    private static final long serialVersionUID = -1214379189873595503L;
    final OperatorMerge.MergeSubscriber<T> subscriber;
    
    public MergeProducer(OperatorMerge.MergeSubscriber<T> paramMergeSubscriber)
    {
      this.subscriber = paramMergeSubscriber;
    }
    
    public long produced(int paramInt)
    {
      return addAndGet(-paramInt);
    }
    
    public void request(long paramLong)
    {
      if (paramLong > 0L) {
        if (get() != Long.MAX_VALUE) {}
      }
      while (paramLong >= 0L)
      {
        return;
        BackpressureUtils.getAndAddRequest(this, paramLong);
        this.subscriber.emit();
        return;
      }
      throw new IllegalArgumentException("n >= 0 required");
    }
  }
  
  static final class MergeSubscriber<T>
    extends Subscriber<Observable<? extends T>>
  {
    static final OperatorMerge.InnerSubscriber<?>[] EMPTY = new OperatorMerge.InnerSubscriber[0];
    final Subscriber<? super T> child;
    final boolean delayErrors;
    volatile boolean done;
    boolean emitting;
    volatile ConcurrentLinkedQueue<Throwable> errors;
    final Object innerGuard;
    volatile OperatorMerge.InnerSubscriber<?>[] innerSubscribers;
    long lastId;
    int lastIndex;
    final int maxConcurrent;
    boolean missed;
    final NotificationLite<T> nl;
    OperatorMerge.MergeProducer<T> producer;
    volatile Queue<Object> queue;
    int scalarEmissionCount;
    final int scalarEmissionLimit;
    volatile CompositeSubscription subscriptions;
    long uniqueId;
    
    public MergeSubscriber(Subscriber<? super T> paramSubscriber, boolean paramBoolean, int paramInt)
    {
      this.child = paramSubscriber;
      this.delayErrors = paramBoolean;
      this.maxConcurrent = paramInt;
      this.nl = NotificationLite.instance();
      this.innerGuard = new Object();
      this.innerSubscribers = EMPTY;
      if (paramInt == Integer.MAX_VALUE)
      {
        this.scalarEmissionLimit = Integer.MAX_VALUE;
        request(Long.MAX_VALUE);
        return;
      }
      this.scalarEmissionLimit = Math.max(1, paramInt >> 1);
      request(paramInt);
    }
    
    private void reportError()
    {
      ArrayList localArrayList = new ArrayList(this.errors);
      if (localArrayList.size() == 1)
      {
        this.child.onError((Throwable)localArrayList.get(0));
        return;
      }
      this.child.onError(new CompositeException(localArrayList));
    }
    
    void addInner(OperatorMerge.InnerSubscriber<T> paramInnerSubscriber)
    {
      getOrCreateComposite().add(paramInnerSubscriber);
      synchronized (this.innerGuard)
      {
        OperatorMerge.InnerSubscriber[] arrayOfInnerSubscriber1 = this.innerSubscribers;
        int i = arrayOfInnerSubscriber1.length;
        OperatorMerge.InnerSubscriber[] arrayOfInnerSubscriber2 = new OperatorMerge.InnerSubscriber[i + 1];
        System.arraycopy(arrayOfInnerSubscriber1, 0, arrayOfInnerSubscriber2, 0, i);
        arrayOfInnerSubscriber2[i] = paramInnerSubscriber;
        this.innerSubscribers = arrayOfInnerSubscriber2;
        return;
      }
    }
    
    boolean checkTerminate()
    {
      if (this.child.isUnsubscribed()) {
        return true;
      }
      ConcurrentLinkedQueue localConcurrentLinkedQueue = this.errors;
      if ((!this.delayErrors) && (localConcurrentLinkedQueue != null) && (!localConcurrentLinkedQueue.isEmpty())) {
        try
        {
          reportError();
          return true;
        }
        finally
        {
          unsubscribe();
        }
      }
      return false;
    }
    
    void emit()
    {
      try
      {
        if (this.emitting)
        {
          this.missed = true;
          return;
        }
        this.emitting = true;
        emitLoop();
        return;
      }
      finally {}
    }
    
    void emitLoop()
    {
      i5 = 0;
      i4 = 0;
      j = i4;
      try
      {
        Subscriber localSubscriber = this.child;
        for (;;)
        {
          j = i4;
          boolean bool = checkTerminate();
          if (bool)
          {
            if (1 != 0) {
              break label1145;
            }
            try
            {
              this.emitting = false;
              return;
            }
            finally {}
          }
          j = i4;
          Object localObject20 = this.queue;
          j = i4;
          long l1 = this.producer.get();
          int i1;
          int i;
          label165:
          int k;
          long l2;
          label195:
          int i7;
          if (l1 == Long.MAX_VALUE) {
            i1 = 1;
          } else {
            for (;;)
            {
              if (l1 > 0L)
              {
                j = i4;
                Object localObject2 = ((Queue)localObject20).poll();
                j = i4;
                bool = checkTerminate();
                if (bool)
                {
                  if (1 != 0) {
                    break label1145;
                  }
                  try
                  {
                    this.emitting = false;
                    return;
                  }
                  finally {}
                  i1 = 0;
                  break label1146;
                }
                if (localObject3 != null) {}
              }
              else
              {
                if (i > 0)
                {
                  if (i1 == 0) {
                    break label449;
                  }
                  l1 = Long.MAX_VALUE;
                }
                i = k;
                l2 = l1;
                if (l1 != 0L)
                {
                  i = k;
                  l2 = l1;
                  if (localObject3 != null) {
                    break label1165;
                  }
                  l2 = l1;
                  i = k;
                }
                j = i4;
                bool = this.done;
                j = i4;
                Object localObject4 = this.queue;
                j = i4;
                localObject21 = this.innerSubscribers;
                j = i4;
                i7 = localObject21.length;
                if (!bool) {
                  break label475;
                }
                if (localObject4 != null)
                {
                  j = i4;
                  if (!((Queue)localObject4).isEmpty()) {
                    break label475;
                  }
                }
                if (i7 != 0) {
                  break label475;
                }
                j = i4;
                localObject4 = this.errors;
                if (localObject4 != null)
                {
                  j = i4;
                  if (!((Queue)localObject4).isEmpty()) {
                    break label465;
                  }
                }
                j = i4;
                localSubscriber.onCompleted();
                label293:
                if (1 != 0) {
                  break label1145;
                }
                try
                {
                  this.emitting = false;
                  return;
                }
                finally {}
              }
              j = i4;
              Object localObject21 = this.nl.getValue(localObject5);
              j = i4;
              try
              {
                localSubscriber.onNext(localObject21);
                k += 1;
                i += 1;
                l1 -= 1L;
              }
              catch (Throwable localThrowable2)
              {
                for (;;)
                {
                  j = i4;
                  if (!this.delayErrors)
                  {
                    j = i4;
                    Exceptions.throwIfFatal(localThrowable2);
                    i = 1;
                    j = i;
                    unsubscribe();
                    j = i;
                    localSubscriber.onError(localThrowable2);
                    if (1 != 0) {
                      break;
                    }
                    try
                    {
                      this.emitting = false;
                      return;
                    }
                    finally {}
                  }
                  j = i4;
                  getOrCreateErrorQueue().offer(localThrowable2);
                }
              }
            }
          }
          try
          {
            this.emitting = false;
            throw ((Throwable)localObject7);
            j = i4;
            l1 = this.producer.produced(i);
            break label165;
            j = i4;
            reportError();
            break label293;
            n = 0;
            i6 = 0;
            k = i;
            if (i7 > 0)
            {
              j = i4;
              l1 = this.lastId;
              j = i4;
              m = this.lastIndex;
              if (i7 > m)
              {
                j = i4;
                k = m;
                if (localThrowable2[m].id == l1) {
                  break label1201;
                }
                break label1181;
                for (;;)
                {
                  if (m < i7)
                  {
                    j = i4;
                    if (localThrowable2[k].id != l1) {}
                  }
                  else
                  {
                    m = k;
                    j = i4;
                    this.lastIndex = k;
                    j = i4;
                    this.lastId = localThrowable2[k].id;
                    k = m;
                    break label1201;
                    n = k;
                    i = m;
                    if (i2 >= i7) {
                      break label989;
                    }
                    j = i4;
                    bool = checkTerminate();
                    if (!bool) {
                      break;
                    }
                    if (1 != 0) {
                      break label1145;
                    }
                    try
                    {
                      this.emitting = false;
                      return;
                    }
                    finally {}
                  }
                  j = k + 1;
                  k = j;
                  if (j == i7) {
                    k = 0;
                  }
                  m += 1;
                }
                OperatorMerge.InnerSubscriber localInnerSubscriber = localThrowable2[i3];
                localObject20 = null;
                l2 = l1;
                break label1220;
                for (;;)
                {
                  Object localObject9 = localObject20;
                  Object localObject11;
                  if (l1 > 0L)
                  {
                    j = i4;
                    bool = checkTerminate();
                    if (bool)
                    {
                      if (1 != 0) {
                        break label1145;
                      }
                      try
                      {
                        this.emitting = false;
                        return;
                      }
                      finally {}
                    }
                    j = i4;
                    localObject11 = localInnerSubscriber.queue;
                    if (localObject11 != null) {
                      break label875;
                    }
                    localObject11 = localObject20;
                  }
                  do
                  {
                    if (i <= 0) {
                      break label1229;
                    }
                    if (i1 != 0) {
                      break label1252;
                    }
                    j = i4;
                    l1 = this.producer.produced(i);
                    j = i4;
                    localInnerSubscriber.requestMore(i);
                    break label1229;
                    j = i4;
                    bool = localInnerSubscriber.done;
                    j = i4;
                    localObject11 = localInnerSubscriber.queue;
                    n = k;
                    i = m;
                    if (!bool) {
                      break label1268;
                    }
                    if (localObject11 != null)
                    {
                      j = i4;
                      n = k;
                      i = m;
                      if (!((RxRingBuffer)localObject11).isEmpty()) {
                        break label1268;
                      }
                    }
                    j = i4;
                    removeInner(localInnerSubscriber);
                    j = i4;
                    bool = checkTerminate();
                    if (!bool) {
                      break label1260;
                    }
                    if (1 != 0) {
                      break;
                    }
                    try
                    {
                      this.emitting = false;
                      return;
                    }
                    finally {}
                    j = i4;
                    localObject20 = ((RxRingBuffer)localObject12).poll();
                    localObject13 = localObject20;
                  } while (localObject20 == null);
                  j = i4;
                  Object localObject13 = this.nl.getValue(localObject20);
                  j = i4;
                  try
                  {
                    localSubscriber.onNext(localObject13);
                    l1 -= 1L;
                    i += 1;
                  }
                  catch (Throwable localThrowable1)
                  {
                    i = 1;
                    j = i;
                    Exceptions.throwIfFatal(localThrowable1);
                    try
                    {
                      localSubscriber.onError(localThrowable1);
                      j = i;
                      unsubscribe();
                      if (1 != 0) {
                        break label1145;
                      }
                      try
                      {
                        this.emitting = false;
                        return;
                      }
                      finally {}
                      j = i4;
                    }
                    finally
                    {
                      j = i;
                      unsubscribe();
                      j = i;
                    }
                  }
                }
                this.lastIndex = i3;
                j = i4;
                this.lastId = localThrowable2[i3].id;
                k = i;
              }
            }
            else
            {
              if (k > 0)
              {
                j = i4;
                request(k);
              }
              if (n != 0) {
                continue;
              }
              j = i4;
              j = i5;
              try
              {
                if (!this.missed)
                {
                  i = 1;
                  j = i;
                  this.emitting = false;
                  j = i;
                  if (1 != 0) {
                    break label1145;
                  }
                  try
                  {
                    this.emitting = false;
                    return;
                  }
                  finally {}
                  k = i3 + 1;
                  j = k;
                  if (k == i7) {
                    j = 0;
                  }
                  i2 += 1;
                  k = n;
                  i3 = j;
                  m = i;
                  break label590;
                }
                j = i5;
                this.missed = false;
                j = i5;
              }
              finally {}
            }
          }
          finally
          {
            int m;
            for (;;)
            {
              int i6;
              throw ((Throwable)localObject18);
              return;
              j = 0;
              i = 0;
              l2 = l1;
              if (localObject20 == null) {
                break label195;
              }
              l2 = l1;
              i = j;
              j = 0;
              Object localObject19 = null;
              k = i;
              l1 = l2;
              i = j;
              break;
              j = m;
              if (i7 <= m) {
                j = 0;
              }
              m = 0;
              k = j;
              continue;
              int i3 = k;
              int i2 = 0;
              k = i6;
              m = i;
              l1 = l2;
              continue;
              do
              {
                i = 0;
                l1 = l2;
                break;
                if (l1 == 0L) {
                  break label778;
                }
                localObject20 = localObject19;
                l2 = l1;
              } while (localObject19 != null);
              continue;
              l1 = Long.MAX_VALUE;
            }
            i = m + 1;
            int n = 1;
          }
        }
      }
      finally
      {
        if (j != 0) {}
      }
    }
    
    /* Error */
    protected void emitScalar(T paramT, long paramLong)
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore 5
      //   3: iload 5
      //   5: istore 4
      //   7: aload_0
      //   8: getfield 57	rx/internal/operators/OperatorMerge$MergeSubscriber:child	Lrx/Subscriber;
      //   11: aload_1
      //   12: invokevirtual 190	rx/Subscriber:onNext	(Ljava/lang/Object;)V
      //   15: lload_2
      //   16: ldc2_w 80
      //   19: lcmp
      //   20: ifeq +16 -> 36
      //   23: iload 5
      //   25: istore 4
      //   27: aload_0
      //   28: getfield 168	rx/internal/operators/OperatorMerge$MergeSubscriber:producer	Lrx/internal/operators/OperatorMerge$MergeProducer;
      //   31: iconst_1
      //   32: invokevirtual 207	rx/internal/operators/OperatorMerge$MergeProducer:produced	(I)J
      //   35: pop2
      //   36: iload 5
      //   38: istore 4
      //   40: aload_0
      //   41: getfield 232	rx/internal/operators/OperatorMerge$MergeSubscriber:scalarEmissionCount	I
      //   44: iconst_1
      //   45: iadd
      //   46: istore 6
      //   48: iload 5
      //   50: istore 4
      //   52: iload 6
      //   54: aload_0
      //   55: getfield 79	rx/internal/operators/OperatorMerge$MergeSubscriber:scalarEmissionLimit	I
      //   58: if_icmpne +154 -> 212
      //   61: iload 5
      //   63: istore 4
      //   65: aload_0
      //   66: iconst_0
      //   67: putfield 232	rx/internal/operators/OperatorMerge$MergeSubscriber:scalarEmissionCount	I
      //   70: iload 5
      //   72: istore 4
      //   74: aload_0
      //   75: iload 6
      //   77: i2l
      //   78: invokevirtual 233	rx/internal/operators/OperatorMerge$MergeSubscriber:requestMore	(J)V
      //   81: iload 5
      //   83: istore 4
      //   85: aload_0
      //   86: monitorenter
      //   87: iconst_1
      //   88: istore 4
      //   90: aload_0
      //   91: getfield 159	rx/internal/operators/OperatorMerge$MergeSubscriber:missed	Z
      //   94: ifne +136 -> 230
      //   97: aload_0
      //   98: iconst_0
      //   99: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   102: aload_0
      //   103: monitorexit
      //   104: iconst_1
      //   105: ifne +12 -> 117
      //   108: aload_0
      //   109: monitorenter
      //   110: aload_0
      //   111: iconst_0
      //   112: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   115: aload_0
      //   116: monitorexit
      //   117: return
      //   118: astore_1
      //   119: iload 5
      //   121: istore 4
      //   123: aload_0
      //   124: getfield 59	rx/internal/operators/OperatorMerge$MergeSubscriber:delayErrors	Z
      //   127: ifne +50 -> 177
      //   130: iload 5
      //   132: istore 4
      //   134: aload_1
      //   135: invokestatic 195	rx/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   138: iconst_1
      //   139: istore 5
      //   141: iload 5
      //   143: istore 4
      //   145: aload_0
      //   146: invokevirtual 154	rx/internal/operators/OperatorMerge$MergeSubscriber:unsubscribe	()V
      //   149: iload 5
      //   151: istore 4
      //   153: aload_0
      //   154: aload_1
      //   155: invokevirtual 234	rx/internal/operators/OperatorMerge$MergeSubscriber:onError	(Ljava/lang/Throwable;)V
      //   158: iconst_1
      //   159: ifne -42 -> 117
      //   162: aload_0
      //   163: monitorenter
      //   164: aload_0
      //   165: iconst_0
      //   166: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   169: aload_0
      //   170: monitorexit
      //   171: return
      //   172: astore_1
      //   173: aload_0
      //   174: monitorexit
      //   175: aload_1
      //   176: athrow
      //   177: iload 5
      //   179: istore 4
      //   181: aload_0
      //   182: invokevirtual 199	rx/internal/operators/OperatorMerge$MergeSubscriber:getOrCreateErrorQueue	()Ljava/util/Queue;
      //   185: aload_1
      //   186: invokeinterface 203 2 0
      //   191: pop
      //   192: goto -177 -> 15
      //   195: astore_1
      //   196: iload 4
      //   198: ifne +12 -> 210
      //   201: aload_0
      //   202: monitorenter
      //   203: aload_0
      //   204: iconst_0
      //   205: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   208: aload_0
      //   209: monitorexit
      //   210: aload_1
      //   211: athrow
      //   212: iload 5
      //   214: istore 4
      //   216: aload_0
      //   217: iload 6
      //   219: putfield 232	rx/internal/operators/OperatorMerge$MergeSubscriber:scalarEmissionCount	I
      //   222: goto -141 -> 81
      //   225: astore_1
      //   226: aload_0
      //   227: monitorexit
      //   228: aload_1
      //   229: athrow
      //   230: aload_0
      //   231: iconst_0
      //   232: putfield 159	rx/internal/operators/OperatorMerge$MergeSubscriber:missed	Z
      //   235: aload_0
      //   236: monitorexit
      //   237: iconst_1
      //   238: ifne +12 -> 250
      //   241: aload_0
      //   242: monitorenter
      //   243: aload_0
      //   244: iconst_0
      //   245: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   248: aload_0
      //   249: monitorexit
      //   250: aload_0
      //   251: invokevirtual 162	rx/internal/operators/OperatorMerge$MergeSubscriber:emitLoop	()V
      //   254: return
      //   255: astore_1
      //   256: aload_0
      //   257: monitorexit
      //   258: aload_1
      //   259: athrow
      //   260: astore_1
      //   261: aload_0
      //   262: monitorexit
      //   263: aload_1
      //   264: athrow
      //   265: astore_1
      //   266: aload_0
      //   267: monitorexit
      //   268: aload_1
      //   269: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	270	0	this	MergeSubscriber
      //   0	270	1	paramT	T
      //   0	270	2	paramLong	long
      //   5	210	4	i	int
      //   1	212	5	j	int
      //   46	172	6	k	int
      // Exception table:
      //   from	to	target	type
      //   7	15	118	java/lang/Throwable
      //   164	171	172	finally
      //   173	175	172	finally
      //   7	15	195	finally
      //   27	36	195	finally
      //   40	48	195	finally
      //   52	61	195	finally
      //   65	70	195	finally
      //   74	81	195	finally
      //   85	87	195	finally
      //   123	130	195	finally
      //   134	138	195	finally
      //   145	149	195	finally
      //   153	158	195	finally
      //   181	192	195	finally
      //   216	222	195	finally
      //   258	260	195	finally
      //   110	117	225	finally
      //   226	228	225	finally
      //   90	104	255	finally
      //   230	237	255	finally
      //   256	258	255	finally
      //   243	250	260	finally
      //   261	263	260	finally
      //   203	210	265	finally
      //   266	268	265	finally
    }
    
    /* Error */
    protected void emitScalar(OperatorMerge.InnerSubscriber<T> paramInnerSubscriber, T paramT, long paramLong)
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore 6
      //   3: iload 6
      //   5: istore 5
      //   7: aload_0
      //   8: getfield 57	rx/internal/operators/OperatorMerge$MergeSubscriber:child	Lrx/Subscriber;
      //   11: aload_2
      //   12: invokevirtual 190	rx/Subscriber:onNext	(Ljava/lang/Object;)V
      //   15: lload_3
      //   16: ldc2_w 80
      //   19: lcmp
      //   20: ifeq +16 -> 36
      //   23: iload 6
      //   25: istore 5
      //   27: aload_0
      //   28: getfield 168	rx/internal/operators/OperatorMerge$MergeSubscriber:producer	Lrx/internal/operators/OperatorMerge$MergeProducer;
      //   31: iconst_1
      //   32: invokevirtual 207	rx/internal/operators/OperatorMerge$MergeProducer:produced	(I)J
      //   35: pop2
      //   36: iload 6
      //   38: istore 5
      //   40: aload_1
      //   41: lconst_1
      //   42: invokevirtual 220	rx/internal/operators/OperatorMerge$InnerSubscriber:requestMore	(J)V
      //   45: iload 6
      //   47: istore 5
      //   49: aload_0
      //   50: monitorenter
      //   51: iconst_1
      //   52: istore 5
      //   54: aload_0
      //   55: getfield 159	rx/internal/operators/OperatorMerge$MergeSubscriber:missed	Z
      //   58: ifne +123 -> 181
      //   61: aload_0
      //   62: iconst_0
      //   63: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   66: aload_0
      //   67: monitorexit
      //   68: iconst_1
      //   69: ifne +12 -> 81
      //   72: aload_0
      //   73: monitorenter
      //   74: aload_0
      //   75: iconst_0
      //   76: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   79: aload_0
      //   80: monitorexit
      //   81: return
      //   82: astore_2
      //   83: iload 6
      //   85: istore 5
      //   87: aload_0
      //   88: getfield 59	rx/internal/operators/OperatorMerge$MergeSubscriber:delayErrors	Z
      //   91: ifne +50 -> 141
      //   94: iload 6
      //   96: istore 5
      //   98: aload_2
      //   99: invokestatic 195	rx/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   102: iconst_1
      //   103: istore 6
      //   105: iload 6
      //   107: istore 5
      //   109: aload_1
      //   110: invokevirtual 237	rx/internal/operators/OperatorMerge$InnerSubscriber:unsubscribe	()V
      //   113: iload 6
      //   115: istore 5
      //   117: aload_1
      //   118: aload_2
      //   119: invokevirtual 238	rx/internal/operators/OperatorMerge$InnerSubscriber:onError	(Ljava/lang/Throwable;)V
      //   122: iconst_1
      //   123: ifne -42 -> 81
      //   126: aload_0
      //   127: monitorenter
      //   128: aload_0
      //   129: iconst_0
      //   130: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   133: aload_0
      //   134: monitorexit
      //   135: return
      //   136: astore_1
      //   137: aload_0
      //   138: monitorexit
      //   139: aload_1
      //   140: athrow
      //   141: iload 6
      //   143: istore 5
      //   145: aload_0
      //   146: invokevirtual 199	rx/internal/operators/OperatorMerge$MergeSubscriber:getOrCreateErrorQueue	()Ljava/util/Queue;
      //   149: aload_2
      //   150: invokeinterface 203 2 0
      //   155: pop
      //   156: goto -141 -> 15
      //   159: astore_1
      //   160: iload 5
      //   162: ifne +12 -> 174
      //   165: aload_0
      //   166: monitorenter
      //   167: aload_0
      //   168: iconst_0
      //   169: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   172: aload_0
      //   173: monitorexit
      //   174: aload_1
      //   175: athrow
      //   176: astore_1
      //   177: aload_0
      //   178: monitorexit
      //   179: aload_1
      //   180: athrow
      //   181: aload_0
      //   182: iconst_0
      //   183: putfield 159	rx/internal/operators/OperatorMerge$MergeSubscriber:missed	Z
      //   186: aload_0
      //   187: monitorexit
      //   188: iconst_1
      //   189: ifne +12 -> 201
      //   192: aload_0
      //   193: monitorenter
      //   194: aload_0
      //   195: iconst_0
      //   196: putfield 157	rx/internal/operators/OperatorMerge$MergeSubscriber:emitting	Z
      //   199: aload_0
      //   200: monitorexit
      //   201: aload_0
      //   202: invokevirtual 162	rx/internal/operators/OperatorMerge$MergeSubscriber:emitLoop	()V
      //   205: return
      //   206: astore_1
      //   207: aload_0
      //   208: monitorexit
      //   209: aload_1
      //   210: athrow
      //   211: astore_1
      //   212: aload_0
      //   213: monitorexit
      //   214: aload_1
      //   215: athrow
      //   216: astore_1
      //   217: aload_0
      //   218: monitorexit
      //   219: aload_1
      //   220: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	221	0	this	MergeSubscriber
      //   0	221	1	paramInnerSubscriber	OperatorMerge.InnerSubscriber<T>
      //   0	221	2	paramT	T
      //   0	221	3	paramLong	long
      //   5	156	5	i	int
      //   1	141	6	j	int
      // Exception table:
      //   from	to	target	type
      //   7	15	82	java/lang/Throwable
      //   128	135	136	finally
      //   137	139	136	finally
      //   7	15	159	finally
      //   27	36	159	finally
      //   40	45	159	finally
      //   49	51	159	finally
      //   87	94	159	finally
      //   98	102	159	finally
      //   109	113	159	finally
      //   117	122	159	finally
      //   145	156	159	finally
      //   209	211	159	finally
      //   74	81	176	finally
      //   177	179	176	finally
      //   54	68	206	finally
      //   181	188	206	finally
      //   207	209	206	finally
      //   194	201	211	finally
      //   212	214	211	finally
      //   167	174	216	finally
      //   217	219	216	finally
    }
    
    /* Error */
    CompositeSubscription getOrCreateComposite()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 241	rx/internal/operators/OperatorMerge$MergeSubscriber:subscriptions	Lrx/subscriptions/CompositeSubscription;
      //   4: astore_2
      //   5: aload_2
      //   6: astore_3
      //   7: aload_2
      //   8: ifnonnull +48 -> 56
      //   11: iconst_0
      //   12: istore_1
      //   13: aload_0
      //   14: monitorenter
      //   15: aload_0
      //   16: getfield 241	rx/internal/operators/OperatorMerge$MergeSubscriber:subscriptions	Lrx/subscriptions/CompositeSubscription;
      //   19: astore_3
      //   20: aload_3
      //   21: astore_2
      //   22: aload_3
      //   23: ifnonnull +18 -> 41
      //   26: new 128	rx/subscriptions/CompositeSubscription
      //   29: dup
      //   30: invokespecial 242	rx/subscriptions/CompositeSubscription:<init>	()V
      //   33: astore_2
      //   34: aload_0
      //   35: aload_2
      //   36: putfield 241	rx/internal/operators/OperatorMerge$MergeSubscriber:subscriptions	Lrx/subscriptions/CompositeSubscription;
      //   39: iconst_1
      //   40: istore_1
      //   41: aload_0
      //   42: monitorexit
      //   43: aload_2
      //   44: astore_3
      //   45: iload_1
      //   46: ifeq +10 -> 56
      //   49: aload_0
      //   50: aload_2
      //   51: invokevirtual 243	rx/internal/operators/OperatorMerge$MergeSubscriber:add	(Lrx/Subscription;)V
      //   54: aload_2
      //   55: astore_3
      //   56: aload_3
      //   57: areturn
      //   58: astore_2
      //   59: aload_0
      //   60: monitorexit
      //   61: aload_2
      //   62: athrow
      //   63: astore_2
      //   64: goto -5 -> 59
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	67	0	this	MergeSubscriber
      //   12	34	1	i	int
      //   4	51	2	localObject1	Object
      //   58	4	2	localObject2	Object
      //   63	1	2	localObject3	Object
      //   6	51	3	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   15	20	58	finally
      //   26	34	58	finally
      //   41	43	58	finally
      //   59	61	58	finally
      //   34	39	63	finally
    }
    
    Queue<Throwable> getOrCreateErrorQueue()
    {
      Object localObject1 = this.errors;
      if (localObject1 == null)
      {
        for (;;)
        {
          try
          {
            ConcurrentLinkedQueue localConcurrentLinkedQueue = this.errors;
            localObject1 = localConcurrentLinkedQueue;
            if (localConcurrentLinkedQueue == null) {
              localObject1 = new ConcurrentLinkedQueue();
            }
          }
          finally
          {
            continue;
          }
          try
          {
            this.errors = ((ConcurrentLinkedQueue)localObject1);
            return (Queue<Throwable>)localObject1;
          }
          finally {}
        }
        throw ((Throwable)localObject1);
      }
      return localQueue;
    }
    
    public void onCompleted()
    {
      this.done = true;
      emit();
    }
    
    public void onError(Throwable paramThrowable)
    {
      getOrCreateErrorQueue().offer(paramThrowable);
      this.done = true;
      emit();
    }
    
    public void onNext(Observable<? extends T> paramObservable)
    {
      if (paramObservable == null) {
        return;
      }
      if ((paramObservable instanceof ScalarSynchronousObservable))
      {
        tryEmit(((ScalarSynchronousObservable)paramObservable).get());
        return;
      }
      long l = this.uniqueId;
      this.uniqueId = (1L + l);
      OperatorMerge.InnerSubscriber localInnerSubscriber = new OperatorMerge.InnerSubscriber(this, l);
      addInner(localInnerSubscriber);
      paramObservable.unsafeSubscribe(localInnerSubscriber);
      emit();
    }
    
    protected void queueScalar(T paramT)
    {
      Queue localQueue = this.queue;
      Object localObject = localQueue;
      int i;
      if (localQueue == null)
      {
        i = this.maxConcurrent;
        if (i != Integer.MAX_VALUE) {
          break label78;
        }
        localObject = new SpscUnboundedAtomicArrayQueue(RxRingBuffer.SIZE);
      }
      for (;;)
      {
        this.queue = ((Queue)localObject);
        if (((Queue)localObject).offer(this.nl.next(paramT))) {
          break;
        }
        unsubscribe();
        onError(OnErrorThrowable.addValueAsLastCause(new MissingBackpressureException(), paramT));
        return;
        label78:
        if (Pow2.isPowerOfTwo(i))
        {
          if (UnsafeAccess.isUnsafeAvailable()) {
            localObject = new SpscArrayQueue(i);
          } else {
            localObject = new SpscAtomicArrayQueue(i);
          }
        }
        else {
          localObject = new SpscExactAtomicArrayQueue(i);
        }
      }
      emit();
    }
    
    protected void queueScalar(OperatorMerge.InnerSubscriber<T> paramInnerSubscriber, T paramT)
    {
      RxRingBuffer localRxRingBuffer2 = paramInnerSubscriber.queue;
      RxRingBuffer localRxRingBuffer1 = localRxRingBuffer2;
      if (localRxRingBuffer2 == null)
      {
        localRxRingBuffer1 = RxRingBuffer.getSpscInstance();
        paramInnerSubscriber.add(localRxRingBuffer1);
        paramInnerSubscriber.queue = localRxRingBuffer1;
      }
      try
      {
        localRxRingBuffer1.onNext(this.nl.next(paramT));
        emit();
        return;
      }
      catch (MissingBackpressureException paramT)
      {
        paramInnerSubscriber.unsubscribe();
        paramInnerSubscriber.onError(paramT);
        return;
      }
      catch (IllegalStateException paramT)
      {
        while (paramInnerSubscriber.isUnsubscribed()) {}
        paramInnerSubscriber.unsubscribe();
        paramInnerSubscriber.onError(paramT);
      }
    }
    
    void removeInner(OperatorMerge.InnerSubscriber<T> paramInnerSubscriber)
    {
      ??? = paramInnerSubscriber.queue;
      if (??? != null) {
        ((RxRingBuffer)???).release();
      }
      this.subscriptions.remove(paramInnerSubscriber);
      for (;;)
      {
        OperatorMerge.InnerSubscriber[] arrayOfInnerSubscriber;
        int m;
        int i;
        int j;
        synchronized (this.innerGuard)
        {
          arrayOfInnerSubscriber = this.innerSubscribers;
          m = arrayOfInnerSubscriber.length;
          int k = -1;
          i = 0;
          j = k;
          if (i < m)
          {
            if (!paramInnerSubscriber.equals(arrayOfInnerSubscriber[i])) {
              break label144;
            }
            j = i;
          }
          if (j < 0) {
            return;
          }
          if (m == 1)
          {
            this.innerSubscribers = EMPTY;
            return;
          }
        }
        paramInnerSubscriber = new OperatorMerge.InnerSubscriber[m - 1];
        System.arraycopy(arrayOfInnerSubscriber, 0, paramInnerSubscriber, 0, j);
        System.arraycopy(arrayOfInnerSubscriber, j + 1, paramInnerSubscriber, j, m - j - 1);
        this.innerSubscribers = paramInnerSubscriber;
        return;
        label144:
        i += 1;
      }
    }
    
    public void requestMore(long paramLong)
    {
      request(paramLong);
    }
    
    void tryEmit(T paramT)
    {
      int i = 0;
      int j = 0;
      long l2 = this.producer.get();
      long l1 = l2;
      if (l2 != 0L) {}
      try
      {
        l1 = this.producer.get();
        i = j;
        if (!this.emitting)
        {
          i = j;
          if (l1 != 0L)
          {
            this.emitting = true;
            i = 1;
          }
        }
        if (i != 0)
        {
          emitScalar(paramT, l1);
          return;
        }
      }
      finally {}
      queueScalar(paramT);
    }
    
    void tryEmit(OperatorMerge.InnerSubscriber<T> paramInnerSubscriber, T paramT)
    {
      int i = 0;
      int j = 0;
      long l2 = this.producer.get();
      long l1 = l2;
      if (l2 != 0L) {}
      try
      {
        l1 = this.producer.get();
        i = j;
        if (!this.emitting)
        {
          i = j;
          if (l1 != 0L)
          {
            this.emitting = true;
            i = 1;
          }
        }
        if (i != 0)
        {
          emitScalar(paramInnerSubscriber, paramT, l1);
          return;
        }
      }
      finally {}
      queueScalar(paramInnerSubscriber, paramT);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorMerge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */