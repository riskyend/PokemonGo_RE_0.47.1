package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.functions.Action0;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.atomic.SpscLinkedArrayQueue;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

public final class OperatorSwitch<T>
  implements Observable.Operator<T, Observable<? extends T>>
{
  final boolean delayError;
  
  OperatorSwitch(boolean paramBoolean)
  {
    this.delayError = paramBoolean;
  }
  
  public static <T> OperatorSwitch<T> instance(boolean paramBoolean)
  {
    if (paramBoolean) {
      return HolderDelayError.INSTANCE;
    }
    return Holder.INSTANCE;
  }
  
  public Subscriber<? super Observable<? extends T>> call(Subscriber<? super T> paramSubscriber)
  {
    SwitchSubscriber localSwitchSubscriber = new SwitchSubscriber(paramSubscriber, this.delayError);
    paramSubscriber.add(localSwitchSubscriber);
    localSwitchSubscriber.init();
    return localSwitchSubscriber;
  }
  
  private static final class Holder
  {
    static final OperatorSwitch<Object> INSTANCE = new OperatorSwitch(false);
  }
  
  private static final class HolderDelayError
  {
    static final OperatorSwitch<Object> INSTANCE = new OperatorSwitch(true);
  }
  
  static final class InnerSubscriber<T>
    extends Subscriber<T>
  {
    private final long id;
    private final OperatorSwitch.SwitchSubscriber<T> parent;
    
    InnerSubscriber(long paramLong, OperatorSwitch.SwitchSubscriber<T> paramSwitchSubscriber)
    {
      this.id = paramLong;
      this.parent = paramSwitchSubscriber;
    }
    
    public void onCompleted()
    {
      this.parent.complete(this.id);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.error(paramThrowable, this.id);
    }
    
    public void onNext(T paramT)
    {
      this.parent.emit(paramT, this);
    }
    
    public void setProducer(Producer paramProducer)
    {
      this.parent.innerProducer(paramProducer, this.id);
    }
  }
  
  private static final class SwitchSubscriber<T>
    extends Subscriber<Observable<? extends T>>
  {
    static final Throwable TERMINAL_ERROR = new Throwable("Terminal error");
    final Subscriber<? super T> child;
    final boolean delayError;
    boolean emitting;
    Throwable error;
    final AtomicLong index;
    boolean innerActive;
    volatile boolean mainDone;
    boolean missed;
    final NotificationLite<T> nl;
    Producer producer;
    final SpscLinkedArrayQueue<Object> queue;
    long requested;
    final SerialSubscription ssub;
    
    SwitchSubscriber(Subscriber<? super T> paramSubscriber, boolean paramBoolean)
    {
      this.child = paramSubscriber;
      this.ssub = new SerialSubscription();
      this.delayError = paramBoolean;
      this.index = new AtomicLong();
      this.queue = new SpscLinkedArrayQueue(RxRingBuffer.SIZE);
      this.nl = NotificationLite.instance();
    }
    
    protected boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Throwable paramThrowable, SpscLinkedArrayQueue<Object> paramSpscLinkedArrayQueue, Subscriber<? super T> paramSubscriber, boolean paramBoolean3)
    {
      if (this.delayError)
      {
        if ((paramBoolean1) && (!paramBoolean2) && (paramBoolean3))
        {
          if (paramThrowable != null)
          {
            paramSubscriber.onError(paramThrowable);
            return true;
          }
          paramSubscriber.onCompleted();
          return true;
        }
      }
      else
      {
        if (paramThrowable != null)
        {
          paramSpscLinkedArrayQueue.clear();
          paramSubscriber.onError(paramThrowable);
          return true;
        }
        if ((paramBoolean1) && (!paramBoolean2) && (paramBoolean3))
        {
          paramSubscriber.onCompleted();
          return true;
        }
      }
      return false;
    }
    
    void childRequested(long paramLong)
    {
      try
      {
        Producer localProducer = this.producer;
        this.requested = BackpressureUtils.addCap(this.requested, paramLong);
        if (localProducer != null) {
          localProducer.request(paramLong);
        }
        drain();
        return;
      }
      finally {}
    }
    
    void clearProducer()
    {
      try
      {
        this.producer = null;
        return;
      }
      finally {}
    }
    
    void complete(long paramLong)
    {
      try
      {
        if (this.index.get() != paramLong) {
          return;
        }
        this.innerActive = false;
        this.producer = null;
        drain();
        return;
      }
      finally {}
    }
    
    void drain()
    {
      boolean bool1 = this.mainDone;
      for (;;)
      {
        boolean bool2;
        SpscLinkedArrayQueue localSpscLinkedArrayQueue;
        AtomicLong localAtomicLong;
        Subscriber localSubscriber;
        long l2;
        try
        {
          if (this.emitting)
          {
            this.missed = true;
            return;
          }
          this.emitting = true;
          bool2 = this.innerActive;
          long l1 = this.requested;
          Throwable localThrowable1 = this.error;
          if ((localThrowable1 != null) && (localThrowable1 != TERMINAL_ERROR) && (!this.delayError)) {
            this.error = TERMINAL_ERROR;
          }
          localSpscLinkedArrayQueue = this.queue;
          localAtomicLong = this.index;
          localSubscriber = this.child;
          l2 = 0L;
          if (l2 != l1)
          {
            if (localSubscriber.isUnsubscribed()) {
              break;
            }
            boolean bool3 = localSpscLinkedArrayQueue.isEmpty();
            if (checkTerminated(bool1, bool2, localThrowable1, localSpscLinkedArrayQueue, localSubscriber, bool3)) {
              break;
            }
            if (!bool3) {}
          }
          else
          {
            if ((l2 == l1) && ((localSubscriber.isUnsubscribed()) || (checkTerminated(this.mainDone, bool2, localThrowable1, localSpscLinkedArrayQueue, localSubscriber, localSpscLinkedArrayQueue.isEmpty())))) {
              break;
            }
            try
            {
              long l3 = this.requested;
              l1 = l3;
              if (l3 != Long.MAX_VALUE)
              {
                l1 = l3 - l2;
                this.requested = l1;
              }
              if (this.missed) {
                break label292;
              }
              this.emitting = false;
              return;
            }
            finally {}
          }
          localInnerSubscriber = (OperatorSwitch.InnerSubscriber)localSpscLinkedArrayQueue.poll();
        }
        finally {}
        OperatorSwitch.InnerSubscriber localInnerSubscriber;
        Object localObject3 = this.nl.getValue(localSpscLinkedArrayQueue.poll());
        if (localAtomicLong.get() == localInnerSubscriber.id)
        {
          localSubscriber.onNext(localObject3);
          l2 += 1L;
          continue;
          label292:
          this.missed = false;
          bool1 = this.mainDone;
          bool2 = this.innerActive;
          Throwable localThrowable2 = this.error;
          if ((localThrowable2 != null) && (localThrowable2 != TERMINAL_ERROR) && (!this.delayError)) {
            this.error = TERMINAL_ERROR;
          }
        }
      }
    }
    
    void emit(T paramT, OperatorSwitch.InnerSubscriber<T> paramInnerSubscriber)
    {
      try
      {
        if (this.index.get() != paramInnerSubscriber.id) {
          return;
        }
        this.queue.offer(paramInnerSubscriber, this.nl.next(paramT));
        drain();
        return;
      }
      finally {}
    }
    
    void error(Throwable paramThrowable, long paramLong)
    {
      try
      {
        boolean bool;
        if (this.index.get() == paramLong)
        {
          bool = updateError(paramThrowable);
          this.innerActive = false;
          this.producer = null;
        }
        for (;;)
        {
          if (!bool) {
            break;
          }
          drain();
          return;
          bool = true;
        }
        pluginError(paramThrowable);
      }
      finally {}
    }
    
    void init()
    {
      this.child.add(this.ssub);
      this.child.add(Subscriptions.create(new Action0()
      {
        public void call()
        {
          OperatorSwitch.SwitchSubscriber.this.clearProducer();
        }
      }));
      this.child.setProducer(new Producer()
      {
        public void request(long paramAnonymousLong)
        {
          if (paramAnonymousLong > 0L) {
            OperatorSwitch.SwitchSubscriber.this.childRequested(paramAnonymousLong);
          }
          while (paramAnonymousLong >= 0L) {
            return;
          }
          throw new IllegalArgumentException("n >= 0 expected but it was " + paramAnonymousLong);
        }
      });
    }
    
    void innerProducer(Producer paramProducer, long paramLong)
    {
      try
      {
        if (this.index.get() != paramLong) {
          return;
        }
        paramLong = this.requested;
        this.producer = paramProducer;
        paramProducer.request(paramLong);
        return;
      }
      finally {}
    }
    
    public void onCompleted()
    {
      this.mainDone = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        boolean bool = updateError(paramThrowable);
        if (bool)
        {
          this.mainDone = true;
          drain();
          return;
        }
      }
      finally {}
      pluginError(paramThrowable);
    }
    
    public void onNext(Observable<? extends T> paramObservable)
    {
      long l = this.index.incrementAndGet();
      Object localObject = this.ssub.get();
      if (localObject != null) {
        ((Subscription)localObject).unsubscribe();
      }
      try
      {
        localObject = new OperatorSwitch.InnerSubscriber(l, this);
        this.innerActive = true;
        this.producer = null;
        this.ssub.set((Subscription)localObject);
        paramObservable.unsafeSubscribe((Subscriber)localObject);
        return;
      }
      finally {}
    }
    
    void pluginError(Throwable paramThrowable)
    {
      RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
    }
    
    boolean updateError(Throwable paramThrowable)
    {
      Object localObject = this.error;
      if (localObject == TERMINAL_ERROR) {
        return false;
      }
      if (localObject == null) {
        this.error = paramThrowable;
      }
      for (;;)
      {
        return true;
        if ((localObject instanceof CompositeException))
        {
          localObject = new ArrayList(((CompositeException)localObject).getExceptions());
          ((List)localObject).add(paramThrowable);
          this.error = new CompositeException((Collection)localObject);
        }
        else
        {
          this.error = new CompositeException(new Throwable[] { localObject, paramThrowable });
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSwitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */