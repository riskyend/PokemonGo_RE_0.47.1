package rx.observables;

import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.internal.operators.BackpressureUtils;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

@Beta
public abstract class SyncOnSubscribe<S, T>
  implements Observable.OnSubscribe<T>
{
  @Beta
  public static <S, T> SyncOnSubscribe<S, T> createSingleState(Func0<? extends S> paramFunc0, Action2<? super S, ? super Observer<? super T>> paramAction2)
  {
    new SyncOnSubscribeImpl(paramFunc0, new Func2()
    {
      public S call(S paramAnonymousS, Observer<? super T> paramAnonymousObserver)
      {
        this.val$next.call(paramAnonymousS, paramAnonymousObserver);
        return paramAnonymousS;
      }
    });
  }
  
  @Beta
  public static <S, T> SyncOnSubscribe<S, T> createSingleState(Func0<? extends S> paramFunc0, Action2<? super S, ? super Observer<? super T>> paramAction2, Action1<? super S> paramAction1)
  {
    new SyncOnSubscribeImpl(paramFunc0, new Func2()
    {
      public S call(S paramAnonymousS, Observer<? super T> paramAnonymousObserver)
      {
        this.val$next.call(paramAnonymousS, paramAnonymousObserver);
        return paramAnonymousS;
      }
    }, paramAction1);
  }
  
  @Beta
  public static <S, T> SyncOnSubscribe<S, T> createStateful(Func0<? extends S> paramFunc0, Func2<? super S, ? super Observer<? super T>, ? extends S> paramFunc2)
  {
    return new SyncOnSubscribeImpl(paramFunc0, paramFunc2);
  }
  
  @Beta
  public static <S, T> SyncOnSubscribe<S, T> createStateful(Func0<? extends S> paramFunc0, Func2<? super S, ? super Observer<? super T>, ? extends S> paramFunc2, Action1<? super S> paramAction1)
  {
    return new SyncOnSubscribeImpl(paramFunc0, paramFunc2, paramAction1);
  }
  
  @Beta
  public static <T> SyncOnSubscribe<Void, T> createStateless(Action1<? super Observer<? super T>> paramAction1)
  {
    new SyncOnSubscribeImpl(new Func2()
    {
      public Void call(Void paramAnonymousVoid, Observer<? super T> paramAnonymousObserver)
      {
        this.val$next.call(paramAnonymousObserver);
        return paramAnonymousVoid;
      }
    });
  }
  
  @Beta
  public static <T> SyncOnSubscribe<Void, T> createStateless(Action1<? super Observer<? super T>> paramAction1, Action0 paramAction0)
  {
    new SyncOnSubscribeImpl(new Func2()new Action1
    {
      public Void call(Void paramAnonymousVoid, Observer<? super T> paramAnonymousObserver)
      {
        this.val$next.call(paramAnonymousObserver);
        return null;
      }
    }, new Action1()
    {
      public void call(Void paramAnonymousVoid)
      {
        this.val$onUnsubscribe.call();
      }
    });
  }
  
  public final void call(Subscriber<? super T> paramSubscriber)
  {
    try
    {
      Object localObject = generateState();
      localObject = new SubscriptionProducer(paramSubscriber, this, localObject);
      paramSubscriber.add((Subscription)localObject);
      paramSubscriber.setProducer((Producer)localObject);
      return;
    }
    catch (Throwable localThrowable)
    {
      Exceptions.throwIfFatal(localThrowable);
      paramSubscriber.onError(localThrowable);
    }
  }
  
  protected abstract S generateState();
  
  protected abstract S next(S paramS, Observer<? super T> paramObserver);
  
  protected void onUnsubscribe(S paramS) {}
  
  private static class SubscriptionProducer<S, T>
    extends AtomicLong
    implements Producer, Subscription, Observer<T>
  {
    private static final long serialVersionUID = -3736864024352728072L;
    private final Subscriber<? super T> actualSubscriber;
    private boolean hasTerminated;
    private boolean onNextCalled;
    private final SyncOnSubscribe<S, T> parent;
    private S state;
    
    SubscriptionProducer(Subscriber<? super T> paramSubscriber, SyncOnSubscribe<S, T> paramSyncOnSubscribe, S paramS)
    {
      this.actualSubscriber = paramSubscriber;
      this.parent = paramSyncOnSubscribe;
      this.state = paramS;
    }
    
    private void doUnsubscribe()
    {
      try
      {
        this.parent.onUnsubscribe(this.state);
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwIfFatal(localThrowable);
        RxJavaPlugins.getInstance().getErrorHandler().handleError(localThrowable);
      }
    }
    
    private void fastpath()
    {
      SyncOnSubscribe localSyncOnSubscribe = this.parent;
      Subscriber localSubscriber = this.actualSubscriber;
      try
      {
        do
        {
          this.onNextCalled = false;
          nextIteration(localSyncOnSubscribe);
        } while (!tryUnsubscribe());
        return;
      }
      catch (Throwable localThrowable)
      {
        handleThrownError(localSubscriber, localThrowable);
      }
    }
    
    private void handleThrownError(Subscriber<? super T> paramSubscriber, Throwable paramThrowable)
    {
      if (this.hasTerminated)
      {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
        return;
      }
      this.hasTerminated = true;
      paramSubscriber.onError(paramThrowable);
      unsubscribe();
    }
    
    private void nextIteration(SyncOnSubscribe<S, T> paramSyncOnSubscribe)
    {
      this.state = paramSyncOnSubscribe.next(this.state, this);
    }
    
    private void slowPath(long paramLong)
    {
      SyncOnSubscribe localSyncOnSubscribe = this.parent;
      Subscriber localSubscriber = this.actualSubscriber;
      long l1;
      do
      {
        l1 = paramLong;
        long l2;
        do
        {
          try
          {
            this.onNextCalled = false;
            nextIteration(localSyncOnSubscribe);
            if (tryUnsubscribe()) {
              return;
            }
          }
          catch (Throwable localThrowable)
          {
            handleThrownError(localSubscriber, localThrowable);
            return;
          }
          l2 = l1;
          if (this.onNextCalled) {
            l2 = l1 - 1L;
          }
          l1 = l2;
        } while (l2 != 0L);
        l1 = addAndGet(-paramLong);
        paramLong = l1;
      } while (l1 > 0L);
      tryUnsubscribe();
    }
    
    private boolean tryUnsubscribe()
    {
      if ((this.hasTerminated) || (get() < -1L))
      {
        set(-1L);
        doUnsubscribe();
        return true;
      }
      return false;
    }
    
    public boolean isUnsubscribed()
    {
      return get() < 0L;
    }
    
    public void onCompleted()
    {
      if (this.hasTerminated) {
        throw new IllegalStateException("Terminal event already emitted.");
      }
      this.hasTerminated = true;
      if (!this.actualSubscriber.isUnsubscribed()) {
        this.actualSubscriber.onCompleted();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.hasTerminated) {
        throw new IllegalStateException("Terminal event already emitted.");
      }
      this.hasTerminated = true;
      if (!this.actualSubscriber.isUnsubscribed()) {
        this.actualSubscriber.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (this.onNextCalled) {
        throw new IllegalStateException("onNext called multiple times!");
      }
      this.onNextCalled = true;
      this.actualSubscriber.onNext(paramT);
    }
    
    public void request(long paramLong)
    {
      if ((paramLong > 0L) && (BackpressureUtils.getAndAddRequest(this, paramLong) == 0L))
      {
        if (paramLong == Long.MAX_VALUE) {
          fastpath();
        }
      }
      else {
        return;
      }
      slowPath(paramLong);
    }
    
    public void unsubscribe()
    {
      long l;
      do
      {
        l = get();
        if (compareAndSet(0L, -1L))
        {
          doUnsubscribe();
          return;
        }
      } while (!compareAndSet(l, -2L));
    }
  }
  
  private static final class SyncOnSubscribeImpl<S, T>
    extends SyncOnSubscribe<S, T>
  {
    private final Func0<? extends S> generator;
    private final Func2<? super S, ? super Observer<? super T>, ? extends S> next;
    private final Action1<? super S> onUnsubscribe;
    
    public SyncOnSubscribeImpl(Func0<? extends S> paramFunc0, Func2<? super S, ? super Observer<? super T>, ? extends S> paramFunc2)
    {
      this(paramFunc0, paramFunc2, null);
    }
    
    SyncOnSubscribeImpl(Func0<? extends S> paramFunc0, Func2<? super S, ? super Observer<? super T>, ? extends S> paramFunc2, Action1<? super S> paramAction1)
    {
      this.generator = paramFunc0;
      this.next = paramFunc2;
      this.onUnsubscribe = paramAction1;
    }
    
    public SyncOnSubscribeImpl(Func2<S, Observer<? super T>, S> paramFunc2)
    {
      this(null, paramFunc2, null);
    }
    
    public SyncOnSubscribeImpl(Func2<S, Observer<? super T>, S> paramFunc2, Action1<? super S> paramAction1)
    {
      this(null, paramFunc2, paramAction1);
    }
    
    protected S generateState()
    {
      if (this.generator == null) {
        return null;
      }
      return (S)this.generator.call();
    }
    
    protected S next(S paramS, Observer<? super T> paramObserver)
    {
      return (S)this.next.call(paramS, paramObserver);
    }
    
    protected void onUnsubscribe(S paramS)
    {
      if (this.onUnsubscribe != null) {
        this.onUnsubscribe.call(paramS);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/observables/SyncOnSubscribe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */