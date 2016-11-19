package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observable.Operator;
import rx.Observer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.functions.Action0;
import rx.observers.SerializedObserver;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.Subscriptions;

public final class OperatorWindowWithTime<T>
  implements Observable.Operator<Observable<T>, T>
{
  static final Object NEXT_SUBJECT = new Object();
  static final NotificationLite<Object> nl = NotificationLite.instance();
  final Scheduler scheduler;
  final int size;
  final long timeshift;
  final long timespan;
  final TimeUnit unit;
  
  public OperatorWindowWithTime(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, int paramInt, Scheduler paramScheduler)
  {
    this.timespan = paramLong1;
    this.timeshift = paramLong2;
    this.unit = paramTimeUnit;
    this.size = paramInt;
    this.scheduler = paramScheduler;
  }
  
  public Subscriber<? super T> call(Subscriber<? super Observable<T>> paramSubscriber)
  {
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    if (this.timespan == this.timeshift)
    {
      paramSubscriber = new ExactSubscriber(paramSubscriber, localWorker);
      paramSubscriber.add(localWorker);
      paramSubscriber.scheduleExact();
      return paramSubscriber;
    }
    paramSubscriber = new InexactSubscriber(paramSubscriber, localWorker);
    paramSubscriber.add(localWorker);
    paramSubscriber.startNewChunk();
    paramSubscriber.scheduleChunk();
    return paramSubscriber;
  }
  
  static final class CountedSerializedSubject<T>
  {
    final Observer<T> consumer;
    int count;
    final Observable<T> producer;
    
    public CountedSerializedSubject(Observer<T> paramObserver, Observable<T> paramObservable)
    {
      this.consumer = new SerializedObserver(paramObserver);
      this.producer = paramObservable;
    }
  }
  
  final class ExactSubscriber
    extends Subscriber<T>
  {
    final Subscriber<? super Observable<T>> child;
    boolean emitting;
    final Object guard;
    List<Object> queue;
    volatile OperatorWindowWithTime.State<T> state;
    final Scheduler.Worker worker;
    
    public ExactSubscriber(Scheduler.Worker paramWorker)
    {
      this.child = new SerializedSubscriber(paramWorker);
      Scheduler.Worker localWorker;
      this.worker = localWorker;
      this.guard = new Object();
      this.state = OperatorWindowWithTime.State.empty();
      paramWorker.add(Subscriptions.create(new Action0()
      {
        public void call()
        {
          if (OperatorWindowWithTime.ExactSubscriber.this.state.consumer == null) {
            OperatorWindowWithTime.ExactSubscriber.this.unsubscribe();
          }
        }
      }));
    }
    
    void complete()
    {
      Observer localObserver = this.state.consumer;
      this.state = this.state.clear();
      if (localObserver != null) {
        localObserver.onCompleted();
      }
      this.child.onCompleted();
      unsubscribe();
    }
    
    boolean drain(List<Object> paramList)
    {
      if (paramList == null) {}
      Object localObject;
      do
      {
        do
        {
          while (!paramList.hasNext())
          {
            return true;
            paramList = paramList.iterator();
          }
          localObject = paramList.next();
          if (localObject != OperatorWindowWithTime.NEXT_SUBJECT) {
            break;
          }
        } while (replaceSubject());
        return false;
        if (OperatorWindowWithTime.nl.isError(localObject))
        {
          error(OperatorWindowWithTime.nl.getError(localObject));
          return true;
        }
        if (OperatorWindowWithTime.nl.isCompleted(localObject))
        {
          complete();
          return true;
        }
      } while (emitValue(localObject));
      return false;
    }
    
    boolean emitValue(T paramT)
    {
      OperatorWindowWithTime.State localState2 = this.state;
      OperatorWindowWithTime.State localState1 = localState2;
      if (localState2.consumer == null)
      {
        if (!replaceSubject()) {
          return false;
        }
        localState1 = this.state;
      }
      localState1.consumer.onNext(paramT);
      if (localState1.count == OperatorWindowWithTime.this.size - 1) {
        localState1.consumer.onCompleted();
      }
      for (paramT = localState1.clear();; paramT = localState1.next())
      {
        this.state = paramT;
        return true;
      }
    }
    
    void error(Throwable paramThrowable)
    {
      Observer localObserver = this.state.consumer;
      this.state = this.state.clear();
      if (localObserver != null) {
        localObserver.onError(paramThrowable);
      }
      this.child.onError(paramThrowable);
      unsubscribe();
    }
    
    void nextWindow()
    {
      int k;
      int j;
      int i;
      synchronized (this.guard)
      {
        if (this.emitting)
        {
          if (this.queue == null) {
            this.queue = new ArrayList();
          }
          this.queue.add(OperatorWindowWithTime.NEXT_SUBJECT);
          return;
        }
        this.emitting = true;
        k = 0;
        j = 0;
        i = k;
      }
      try
      {
        boolean bool = replaceSubject();
        if (!bool)
        {
          if (0 == 0)
          {
            synchronized (this.guard)
            {
              this.emitting = false;
              return;
            }
            localObject3 = finally;
            throw ((Throwable)localObject3);
          }
        }
        else {
          for (;;)
          {
            i = k;
            ??? = this.guard;
            i = k;
            i = j;
            try
            {
              List localList1 = this.queue;
              if (localList1 == null)
              {
                i = j;
                this.emitting = false;
                i = 1;
                if (1 == 0) {
                  synchronized (this.guard)
                  {
                    this.emitting = false;
                    return;
                  }
                }
              }
              else
              {
                i = j;
                this.queue = null;
                i = j;
                i = k;
                bool = drain(localList2);
                if (!bool) {
                  if (0 == 0)
                  {
                    synchronized (this.guard)
                    {
                      this.emitting = false;
                      return;
                    }
                    localObject6 = finally;
                  }
                }
              }
            }
            finally {}
          }
        }
        return;
      }
      finally
      {
        if (i == 0) {}
        synchronized (this.guard)
        {
          this.emitting = false;
          throw ((Throwable)localObject6);
        }
      }
    }
    
    public void onCompleted()
    {
      List localList;
      synchronized (this.guard)
      {
        if (this.emitting)
        {
          if (this.queue == null) {
            this.queue = new ArrayList();
          }
          this.queue.add(OperatorWindowWithTime.nl.completed());
          return;
        }
        localList = this.queue;
        this.queue = null;
        this.emitting = true;
      }
      try
      {
        drain(localList);
        complete();
        return;
      }
      catch (Throwable localThrowable)
      {
        error(localThrowable);
      }
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
    
    public void onError(Throwable paramThrowable)
    {
      synchronized (this.guard)
      {
        if (this.emitting)
        {
          this.queue = Collections.singletonList(OperatorWindowWithTime.nl.error(paramThrowable));
          return;
        }
        this.queue = null;
        this.emitting = true;
        error(paramThrowable);
        return;
      }
    }
    
    public void onNext(T arg1)
    {
      int k;
      int j;
      int i;
      synchronized (this.guard)
      {
        if (this.emitting)
        {
          if (this.queue == null) {
            this.queue = new ArrayList();
          }
          this.queue.add(???);
          return;
        }
        this.emitting = true;
        k = 0;
        j = 0;
        i = k;
      }
      try
      {
        boolean bool = emitValue(???);
        if (!bool)
        {
          if (0 == 0)
          {
            synchronized (this.guard)
            {
              this.emitting = false;
              return;
            }
            ??? = finally;
            throw ???;
          }
        }
        else {
          for (;;)
          {
            i = k;
            ??? = this.guard;
            i = k;
            i = j;
            try
            {
              List localList1 = this.queue;
              if (localList1 == null)
              {
                i = j;
                this.emitting = false;
                i = 1;
                if (1 == 0) {
                  synchronized (this.guard)
                  {
                    this.emitting = false;
                    return;
                  }
                }
              }
              else
              {
                i = j;
                this.queue = null;
                i = j;
                i = k;
                bool = drain(localList2);
                if (!bool) {
                  if (0 == 0)
                  {
                    synchronized (this.guard)
                    {
                      this.emitting = false;
                      return;
                    }
                    localObject5 = finally;
                  }
                }
              }
            }
            finally {}
          }
        }
        return;
      }
      finally
      {
        if (i == 0) {}
        synchronized (this.guard)
        {
          this.emitting = false;
          throw ((Throwable)localObject5);
        }
      }
    }
    
    public void onStart()
    {
      request(Long.MAX_VALUE);
    }
    
    boolean replaceSubject()
    {
      Object localObject = this.state.consumer;
      if (localObject != null) {
        ((Observer)localObject).onCompleted();
      }
      if (this.child.isUnsubscribed())
      {
        this.state = this.state.clear();
        unsubscribe();
        return false;
      }
      localObject = UnicastSubject.create();
      this.state = this.state.create((Observer)localObject, (Observable)localObject);
      this.child.onNext(localObject);
      return true;
    }
    
    void scheduleExact()
    {
      this.worker.schedulePeriodically(new Action0()
      {
        public void call()
        {
          OperatorWindowWithTime.ExactSubscriber.this.nextWindow();
        }
      }, 0L, OperatorWindowWithTime.this.timespan, OperatorWindowWithTime.this.unit);
    }
  }
  
  final class InexactSubscriber
    extends Subscriber<T>
  {
    final Subscriber<? super Observable<T>> child;
    final List<OperatorWindowWithTime.CountedSerializedSubject<T>> chunks;
    boolean done;
    final Object guard;
    final Scheduler.Worker worker;
    
    public InexactSubscriber(Scheduler.Worker paramWorker)
    {
      super();
      this.child = paramWorker;
      Scheduler.Worker localWorker;
      this.worker = localWorker;
      this.guard = new Object();
      this.chunks = new LinkedList();
    }
    
    OperatorWindowWithTime.CountedSerializedSubject<T> createCountedSerializedSubject()
    {
      UnicastSubject localUnicastSubject = UnicastSubject.create();
      return new OperatorWindowWithTime.CountedSerializedSubject(localUnicastSubject, localUnicastSubject);
    }
    
    public void onCompleted()
    {
      synchronized (this.guard)
      {
        if (this.done) {
          return;
        }
        this.done = true;
        ArrayList localArrayList = new ArrayList(this.chunks);
        this.chunks.clear();
        ??? = localArrayList.iterator();
        if (((Iterator)???).hasNext()) {
          ((OperatorWindowWithTime.CountedSerializedSubject)((Iterator)???).next()).consumer.onCompleted();
        }
      }
      this.child.onCompleted();
    }
    
    public void onError(Throwable paramThrowable)
    {
      synchronized (this.guard)
      {
        if (this.done) {
          return;
        }
        this.done = true;
        ArrayList localArrayList = new ArrayList(this.chunks);
        this.chunks.clear();
        ??? = localArrayList.iterator();
        if (((Iterator)???).hasNext()) {
          ((OperatorWindowWithTime.CountedSerializedSubject)((Iterator)???).next()).consumer.onError(paramThrowable);
        }
      }
      this.child.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      Object localObject2;
      synchronized (this.guard)
      {
        if (this.done) {
          return;
        }
        localObject2 = new ArrayList(this.chunks);
        Iterator localIterator = this.chunks.iterator();
        while (localIterator.hasNext())
        {
          OperatorWindowWithTime.CountedSerializedSubject localCountedSerializedSubject = (OperatorWindowWithTime.CountedSerializedSubject)localIterator.next();
          int i = localCountedSerializedSubject.count + 1;
          localCountedSerializedSubject.count = i;
          if (i == OperatorWindowWithTime.this.size) {
            localIterator.remove();
          }
        }
      }
      ??? = ((List)localObject2).iterator();
      while (((Iterator)???).hasNext())
      {
        localObject2 = (OperatorWindowWithTime.CountedSerializedSubject)((Iterator)???).next();
        ((OperatorWindowWithTime.CountedSerializedSubject)localObject2).consumer.onNext(paramT);
        if (((OperatorWindowWithTime.CountedSerializedSubject)localObject2).count == OperatorWindowWithTime.this.size) {
          ((OperatorWindowWithTime.CountedSerializedSubject)localObject2).consumer.onCompleted();
        }
      }
    }
    
    public void onStart()
    {
      request(Long.MAX_VALUE);
    }
    
    void scheduleChunk()
    {
      this.worker.schedulePeriodically(new Action0()
      {
        public void call()
        {
          OperatorWindowWithTime.InexactSubscriber.this.startNewChunk();
        }
      }, OperatorWindowWithTime.this.timeshift, OperatorWindowWithTime.this.timeshift, OperatorWindowWithTime.this.unit);
    }
    
    void startNewChunk()
    {
      final OperatorWindowWithTime.CountedSerializedSubject localCountedSerializedSubject = createCountedSerializedSubject();
      synchronized (this.guard)
      {
        if (this.done) {
          return;
        }
        this.chunks.add(localCountedSerializedSubject);
      }
    }
    
    void terminateChunk(OperatorWindowWithTime.CountedSerializedSubject<T> paramCountedSerializedSubject)
    {
      int j = 0;
      synchronized (this.guard)
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
        } while ((OperatorWindowWithTime.CountedSerializedSubject)localIterator.next() != paramCountedSerializedSubject);
        int i = 1;
        localIterator.remove();
        if (i != 0)
        {
          paramCountedSerializedSubject.consumer.onCompleted();
          return;
        }
      }
    }
  }
  
  static final class State<T>
  {
    static final State<Object> EMPTY = new State(null, null, 0);
    final Observer<T> consumer;
    final int count;
    final Observable<T> producer;
    
    public State(Observer<T> paramObserver, Observable<T> paramObservable, int paramInt)
    {
      this.consumer = paramObserver;
      this.producer = paramObservable;
      this.count = paramInt;
    }
    
    public static <T> State<T> empty()
    {
      return EMPTY;
    }
    
    public State<T> clear()
    {
      return empty();
    }
    
    public State<T> create(Observer<T> paramObserver, Observable<T> paramObservable)
    {
      return new State(paramObserver, paramObservable, 0);
    }
    
    public State<T> next()
    {
      return new State(this.consumer, this.producer, this.count + 1);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorWindowWithTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */