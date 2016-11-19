package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Action0;
import rx.internal.util.atomic.SpscLinkedAtomicQueue;
import rx.internal.util.atomic.SpscUnboundedAtomicArrayQueue;
import rx.internal.util.unsafe.SpscLinkedQueue;
import rx.internal.util.unsafe.SpscUnboundedArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

public final class UnicastSubject<T>
  extends Subject<T, T>
{
  final State<T> state;
  
  private UnicastSubject(State<T> paramState)
  {
    super(paramState);
    this.state = paramState;
  }
  
  public static <T> UnicastSubject<T> create()
  {
    return create(16);
  }
  
  public static <T> UnicastSubject<T> create(int paramInt)
  {
    return new UnicastSubject(new State(paramInt, null));
  }
  
  public static <T> UnicastSubject<T> create(int paramInt, Action0 paramAction0)
  {
    return new UnicastSubject(new State(paramInt, paramAction0));
  }
  
  public boolean hasObservers()
  {
    return this.state.subscriber.get() != null;
  }
  
  public void onCompleted()
  {
    this.state.onCompleted();
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.state.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    this.state.onNext(paramT);
  }
  
  static final class State<T>
    extends AtomicLong
    implements Producer, Observer<T>, Action0, Observable.OnSubscribe<T>
  {
    private static final long serialVersionUID = -9044104859202255786L;
    volatile boolean caughtUp;
    volatile boolean done;
    boolean emitting;
    Throwable error;
    boolean missed;
    final NotificationLite<T> nl = NotificationLite.instance();
    final Queue<Object> queue;
    final AtomicReference<Subscriber<? super T>> subscriber = new AtomicReference();
    final AtomicReference<Action0> terminateOnce;
    
    public State(int paramInt, Action0 paramAction0)
    {
      if (paramAction0 != null)
      {
        paramAction0 = new AtomicReference(paramAction0);
        this.terminateOnce = paramAction0;
        if (paramInt <= 1) {
          break label83;
        }
        if (!UnsafeAccess.isUnsafeAvailable()) {
          break label71;
        }
      }
      label71:
      for (paramAction0 = new SpscUnboundedArrayQueue(paramInt);; paramAction0 = new SpscUnboundedAtomicArrayQueue(paramInt))
      {
        this.queue = paramAction0;
        return;
        paramAction0 = null;
        break;
      }
      label83:
      if (UnsafeAccess.isUnsafeAvailable()) {}
      for (paramAction0 = new SpscLinkedQueue();; paramAction0 = new SpscLinkedAtomicQueue()) {
        break;
      }
    }
    
    public void call()
    {
      doTerminate();
      this.done = true;
      try
      {
        if (this.emitting) {
          return;
        }
        this.emitting = true;
        this.queue.clear();
        return;
      }
      finally {}
    }
    
    public void call(Subscriber<? super T> paramSubscriber)
    {
      if (this.subscriber.compareAndSet(null, paramSubscriber))
      {
        paramSubscriber.add(Subscriptions.create(this));
        paramSubscriber.setProducer(this);
        return;
      }
      paramSubscriber.onError(new IllegalStateException("Only a single subscriber is allowed"));
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super T> paramSubscriber)
    {
      if (paramSubscriber.isUnsubscribed())
      {
        this.queue.clear();
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable = this.error;
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
      return false;
    }
    
    void doTerminate()
    {
      AtomicReference localAtomicReference = this.terminateOnce;
      if (localAtomicReference != null)
      {
        Action0 localAction0 = (Action0)localAtomicReference.get();
        if ((localAction0 != null) && (localAtomicReference.compareAndSet(localAction0, null))) {
          localAction0.call();
        }
      }
    }
    
    public void onCompleted()
    {
      if (!this.done)
      {
        doTerminate();
        this.done = true;
        if (this.caughtUp) {}
      }
      else
      {
        try
        {
          if (!this.caughtUp) {}
          for (int i = 1;; i = 0)
          {
            if (i == 0) {
              break;
            }
            replay();
            return;
          }
          ((Subscriber)this.subscriber.get()).onCompleted();
        }
        finally {}
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.done)
      {
        doTerminate();
        this.error = paramThrowable;
        this.done = true;
        if (this.caughtUp) {}
      }
      else
      {
        try
        {
          if (!this.caughtUp) {}
          for (int i = 1;; i = 0)
          {
            if (i == 0) {
              break;
            }
            replay();
            return;
          }
          ((Subscriber)this.subscriber.get()).onError(paramThrowable);
        }
        finally {}
      }
    }
    
    public void onNext(T paramT)
    {
      int i;
      if (!this.done)
      {
        if (!this.caughtUp) {
          i = 0;
        }
      }
      else {
        try
        {
          if (!this.caughtUp)
          {
            this.queue.offer(this.nl.next(paramT));
            i = 1;
          }
          if (i != 0)
          {
            replay();
            return;
          }
        }
        finally {}
      }
      Subscriber localSubscriber = (Subscriber)this.subscriber.get();
      try
      {
        localSubscriber.onNext(paramT);
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwOrReport(localThrowable, localSubscriber, paramT);
      }
    }
    
    void replay()
    {
      for (;;)
      {
        Subscriber localSubscriber;
        long l2;
        long l1;
        try
        {
          if (this.emitting)
          {
            this.missed = true;
            return;
          }
          this.emitting = true;
          Queue localQueue = this.queue;
          localSubscriber = (Subscriber)this.subscriber.get();
          int j = 0;
          if (localSubscriber != null)
          {
            if (checkTerminated(this.done, localQueue.isEmpty(), localSubscriber)) {
              break;
            }
            l2 = get();
            if (l2 != Long.MAX_VALUE) {
              break label209;
            }
            i = 1;
            l1 = 0L;
            if (l2 != 0L)
            {
              boolean bool2 = this.done;
              localObject3 = localQueue.poll();
              if (localObject3 != null) {
                break label214;
              }
              bool1 = true;
              if (checkTerminated(bool2, bool1, localSubscriber)) {
                break;
              }
              if (!bool1) {
                break label220;
              }
            }
            j = i;
            if (i == 0)
            {
              j = i;
              if (l1 != 0L)
              {
                addAndGet(-l1);
                j = i;
              }
            }
          }
          try
          {
            if (this.missed) {
              break label278;
            }
            if ((j != 0) && (localQueue.isEmpty())) {
              this.caughtUp = true;
            }
            this.emitting = false;
            return;
          }
          finally {}
          int i = 0;
        }
        finally {}
        label209:
        continue;
        label214:
        boolean bool1 = false;
        continue;
        label220:
        Object localObject3 = this.nl.getValue(localObject3);
        try
        {
          localSubscriber.onNext(localObject3);
          l2 -= 1L;
          l1 += 1L;
        }
        catch (Throwable localThrowable)
        {
          ((Queue)localObject2).clear();
          Exceptions.throwIfFatal(localThrowable);
          localSubscriber.onError(OnErrorThrowable.addValueAsLastCause(localThrowable, localObject3));
          return;
        }
        label278:
        this.missed = false;
      }
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= 0 required");
      }
      if (paramLong > 0L)
      {
        BackpressureUtils.getAndAddRequest(this, paramLong);
        replay();
      }
      while (!this.done) {
        return;
      }
      replay();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/UnicastSubject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */