package rx.internal.operators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable.Operator;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.observers.SerializedSubscriber;

public final class OperatorBufferWithTime<T>
  implements Observable.Operator<List<T>, T>
{
  final int count;
  final Scheduler scheduler;
  final long timeshift;
  final long timespan;
  final TimeUnit unit;
  
  public OperatorBufferWithTime(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, int paramInt, Scheduler paramScheduler)
  {
    this.timespan = paramLong1;
    this.timeshift = paramLong2;
    this.unit = paramTimeUnit;
    this.count = paramInt;
    this.scheduler = paramScheduler;
  }
  
  public Subscriber<? super T> call(Subscriber<? super List<T>> paramSubscriber)
  {
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    Object localObject = new SerializedSubscriber(paramSubscriber);
    if (this.timespan == this.timeshift)
    {
      localObject = new ExactSubscriber((Subscriber)localObject, localWorker);
      ((ExactSubscriber)localObject).add(localWorker);
      paramSubscriber.add((Subscription)localObject);
      ((ExactSubscriber)localObject).scheduleExact();
      return (Subscriber<? super T>)localObject;
    }
    localObject = new InexactSubscriber((Subscriber)localObject, localWorker);
    ((InexactSubscriber)localObject).add(localWorker);
    paramSubscriber.add((Subscription)localObject);
    ((InexactSubscriber)localObject).startNewChunk();
    ((InexactSubscriber)localObject).scheduleChunk();
    return (Subscriber<? super T>)localObject;
  }
  
  final class ExactSubscriber
    extends Subscriber<T>
  {
    final Subscriber<? super List<T>> child;
    List<T> chunk;
    boolean done;
    final Scheduler.Worker inner;
    
    public ExactSubscriber(Scheduler.Worker paramWorker)
    {
      this.child = paramWorker;
      Scheduler.Worker localWorker;
      this.inner = localWorker;
      this.chunk = new ArrayList();
    }
    
    /* Error */
    void emit()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 46	rx/internal/operators/OperatorBufferWithTime$ExactSubscriber:done	Z
      //   6: ifeq +6 -> 12
      //   9: aload_0
      //   10: monitorexit
      //   11: return
      //   12: aload_0
      //   13: getfield 38	rx/internal/operators/OperatorBufferWithTime$ExactSubscriber:chunk	Ljava/util/List;
      //   16: astore_1
      //   17: aload_0
      //   18: new 35	java/util/ArrayList
      //   21: dup
      //   22: invokespecial 36	java/util/ArrayList:<init>	()V
      //   25: putfield 38	rx/internal/operators/OperatorBufferWithTime$ExactSubscriber:chunk	Ljava/util/List;
      //   28: aload_0
      //   29: monitorexit
      //   30: aload_0
      //   31: getfield 31	rx/internal/operators/OperatorBufferWithTime$ExactSubscriber:child	Lrx/Subscriber;
      //   34: aload_1
      //   35: invokevirtual 50	rx/Subscriber:onNext	(Ljava/lang/Object;)V
      //   38: return
      //   39: astore_1
      //   40: aload_1
      //   41: aload_0
      //   42: invokestatic 56	rx/exceptions/Exceptions:throwOrReport	(Ljava/lang/Throwable;Lrx/Observer;)V
      //   45: return
      //   46: astore_1
      //   47: aload_0
      //   48: monitorexit
      //   49: aload_1
      //   50: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	51	0	this	ExactSubscriber
      //   16	19	1	localList	List
      //   39	2	1	localThrowable	Throwable
      //   46	4	1	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   30	38	39	java/lang/Throwable
      //   2	11	46	finally
      //   12	30	46	finally
      //   47	49	46	finally
    }
    
    public void onCompleted()
    {
      try
      {
        this.inner.unsubscribe();
        try
        {
          if (this.done) {
            return;
          }
          this.done = true;
          List localList = this.chunk;
          this.chunk = null;
          this.child.onNext(localList);
          this.child.onCompleted();
          unsubscribe();
          return;
        }
        finally {}
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwOrReport(localThrowable, this.child);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        if (this.done) {
          return;
        }
        this.done = true;
        this.chunk = null;
        this.child.onError(paramThrowable);
        unsubscribe();
        return;
      }
      finally {}
    }
    
    public void onNext(T paramT)
    {
      Object localObject = null;
      try
      {
        if (this.done) {
          return;
        }
        this.chunk.add(paramT);
        paramT = (T)localObject;
        if (this.chunk.size() == OperatorBufferWithTime.this.count)
        {
          paramT = this.chunk;
          this.chunk = new ArrayList();
        }
        if (paramT != null)
        {
          this.child.onNext(paramT);
          return;
        }
      }
      finally {}
    }
    
    void scheduleExact()
    {
      this.inner.schedulePeriodically(new Action0()
      {
        public void call()
        {
          OperatorBufferWithTime.ExactSubscriber.this.emit();
        }
      }, OperatorBufferWithTime.this.timespan, OperatorBufferWithTime.this.timespan, OperatorBufferWithTime.this.unit);
    }
  }
  
  final class InexactSubscriber
    extends Subscriber<T>
  {
    final Subscriber<? super List<T>> child;
    final List<List<T>> chunks;
    boolean done;
    final Scheduler.Worker inner;
    
    public InexactSubscriber(Scheduler.Worker paramWorker)
    {
      this.child = paramWorker;
      Scheduler.Worker localWorker;
      this.inner = localWorker;
      this.chunks = new LinkedList();
    }
    
    void emitChunk(List<T> paramList)
    {
      int j = 0;
      try
      {
        if (this.done) {
          return;
        }
        Iterator localIterator = this.chunks.iterator();
        do
        {
          i = j;
          if (!localIterator.hasNext()) {
            break;
          }
        } while ((List)localIterator.next() != paramList);
        localIterator.remove();
        int i = 1;
        if (i != 0) {
          try
          {
            this.child.onNext(paramList);
            return;
          }
          catch (Throwable paramList)
          {
            Exceptions.throwOrReport(paramList, this);
            return;
          }
        }
        return;
      }
      finally {}
    }
    
    /* Error */
    public void onCompleted()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 49	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:done	Z
      //   6: ifeq +6 -> 12
      //   9: aload_0
      //   10: monitorexit
      //   11: return
      //   12: aload_0
      //   13: iconst_1
      //   14: putfield 49	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:done	Z
      //   17: new 37	java/util/LinkedList
      //   20: dup
      //   21: aload_0
      //   22: getfield 40	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:chunks	Ljava/util/List;
      //   25: invokespecial 83	java/util/LinkedList:<init>	(Ljava/util/Collection;)V
      //   28: astore_1
      //   29: aload_0
      //   30: getfield 40	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:chunks	Ljava/util/List;
      //   33: invokeinterface 86 1 0
      //   38: aload_0
      //   39: monitorexit
      //   40: aload_1
      //   41: invokeinterface 55 1 0
      //   46: astore_1
      //   47: aload_1
      //   48: invokeinterface 61 1 0
      //   53: ifeq +39 -> 92
      //   56: aload_1
      //   57: invokeinterface 65 1 0
      //   62: checkcast 51	java/util/List
      //   65: astore_2
      //   66: aload_0
      //   67: getfield 33	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:child	Lrx/Subscriber;
      //   70: aload_2
      //   71: invokevirtual 72	rx/Subscriber:onNext	(Ljava/lang/Object;)V
      //   74: goto -27 -> 47
      //   77: astore_1
      //   78: aload_1
      //   79: aload_0
      //   80: getfield 33	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:child	Lrx/Subscriber;
      //   83: invokestatic 78	rx/exceptions/Exceptions:throwOrReport	(Ljava/lang/Throwable;Lrx/Observer;)V
      //   86: return
      //   87: astore_1
      //   88: aload_0
      //   89: monitorexit
      //   90: aload_1
      //   91: athrow
      //   92: aload_0
      //   93: getfield 33	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:child	Lrx/Subscriber;
      //   96: invokevirtual 88	rx/Subscriber:onCompleted	()V
      //   99: aload_0
      //   100: invokevirtual 91	rx/internal/operators/OperatorBufferWithTime$InexactSubscriber:unsubscribe	()V
      //   103: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	104	0	this	InexactSubscriber
      //   28	29	1	localObject1	Object
      //   77	2	1	localThrowable	Throwable
      //   87	4	1	localObject2	Object
      //   65	6	2	localList	List
      // Exception table:
      //   from	to	target	type
      //   0	2	77	java/lang/Throwable
      //   40	47	77	java/lang/Throwable
      //   47	74	77	java/lang/Throwable
      //   90	92	77	java/lang/Throwable
      //   2	11	87	finally
      //   12	40	87	finally
      //   88	90	87	finally
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        if (this.done) {
          return;
        }
        this.done = true;
        this.chunks.clear();
        this.child.onError(paramThrowable);
        unsubscribe();
        return;
      }
      finally {}
    }
    
    public void onNext(T paramT)
    {
      Object localObject;
      for (;;)
      {
        Iterator localIterator;
        List localList;
        try
        {
          if (this.done) {
            return;
          }
          localIterator = this.chunks.iterator();
          localObject = null;
        }
        finally {}
        try
        {
          if (!localIterator.hasNext()) {
            continue;
          }
          localList = (List)localIterator.next();
          localList.add(paramT);
          if (localList.size() != OperatorBufferWithTime.this.count) {
            break label156;
          }
          localIterator.remove();
          if (localObject != null) {
            break label153;
          }
          localObject = new LinkedList();
        }
        finally
        {
          continue;
          continue;
          continue;
        }
        ((List)localObject).add(localList);
      }
      if (localObject != null)
      {
        paramT = ((List)localObject).iterator();
        while (paramT.hasNext())
        {
          localObject = (List)paramT.next();
          this.child.onNext(localObject);
        }
      }
    }
    
    void scheduleChunk()
    {
      this.inner.schedulePeriodically(new Action0()
      {
        public void call()
        {
          OperatorBufferWithTime.InexactSubscriber.this.startNewChunk();
        }
      }, OperatorBufferWithTime.this.timeshift, OperatorBufferWithTime.this.timeshift, OperatorBufferWithTime.this.unit);
    }
    
    void startNewChunk()
    {
      final ArrayList localArrayList = new ArrayList();
      try
      {
        if (this.done) {
          return;
        }
        this.chunks.add(localArrayList);
        this.inner.schedule(new Action0()
        {
          public void call()
          {
            OperatorBufferWithTime.InexactSubscriber.this.emitChunk(localArrayList);
          }
        }, OperatorBufferWithTime.this.timespan, OperatorBufferWithTime.this.unit);
        return;
      }
      finally {}
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorBufferWithTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */