package rx.internal.schedulers;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import rx.Subscription;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.internal.util.SubscriptionList;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class ScheduledAction
  extends AtomicReference<Thread>
  implements Runnable, Subscription
{
  private static final long serialVersionUID = -3962399486978279857L;
  final Action0 action;
  final SubscriptionList cancel;
  
  public ScheduledAction(Action0 paramAction0)
  {
    this.action = paramAction0;
    this.cancel = new SubscriptionList();
  }
  
  public ScheduledAction(Action0 paramAction0, SubscriptionList paramSubscriptionList)
  {
    this.action = paramAction0;
    this.cancel = new SubscriptionList(new Remover2(this, paramSubscriptionList));
  }
  
  public ScheduledAction(Action0 paramAction0, CompositeSubscription paramCompositeSubscription)
  {
    this.action = paramAction0;
    this.cancel = new SubscriptionList(new Remover(this, paramCompositeSubscription));
  }
  
  public void add(Future<?> paramFuture)
  {
    this.cancel.add(new FutureCompleter(paramFuture));
  }
  
  public void add(Subscription paramSubscription)
  {
    this.cancel.add(paramSubscription);
  }
  
  public void addParent(SubscriptionList paramSubscriptionList)
  {
    this.cancel.add(new Remover2(this, paramSubscriptionList));
  }
  
  public void addParent(CompositeSubscription paramCompositeSubscription)
  {
    this.cancel.add(new Remover(this, paramCompositeSubscription));
  }
  
  public boolean isUnsubscribed()
  {
    return this.cancel.isUnsubscribed();
  }
  
  public void run()
  {
    try
    {
      lazySet(Thread.currentThread());
      this.action.call();
      return;
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof OnErrorNotImplementedException)) {}
      for (IllegalStateException localIllegalStateException = new IllegalStateException("Exception thrown on Scheduler.Worker thread. Add `onError` handling.", localThrowable);; localIllegalStateException = new IllegalStateException("Fatal Exception thrown on Scheduler.Worker thread.", localIllegalStateException))
      {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(localIllegalStateException);
        Thread localThread = Thread.currentThread();
        localThread.getUncaughtExceptionHandler().uncaughtException(localThread, localIllegalStateException);
        return;
      }
    }
    finally
    {
      unsubscribe();
    }
  }
  
  public void unsubscribe()
  {
    if (!this.cancel.isUnsubscribed()) {
      this.cancel.unsubscribe();
    }
  }
  
  private final class FutureCompleter
    implements Subscription
  {
    private final Future<?> f;
    
    FutureCompleter()
    {
      Future localFuture;
      this.f = localFuture;
    }
    
    public boolean isUnsubscribed()
    {
      return this.f.isCancelled();
    }
    
    public void unsubscribe()
    {
      if (ScheduledAction.this.get() != Thread.currentThread())
      {
        this.f.cancel(true);
        return;
      }
      this.f.cancel(false);
    }
  }
  
  private static final class Remover
    extends AtomicBoolean
    implements Subscription
  {
    private static final long serialVersionUID = 247232374289553518L;
    final CompositeSubscription parent;
    final ScheduledAction s;
    
    public Remover(ScheduledAction paramScheduledAction, CompositeSubscription paramCompositeSubscription)
    {
      this.s = paramScheduledAction;
      this.parent = paramCompositeSubscription;
    }
    
    public boolean isUnsubscribed()
    {
      return this.s.isUnsubscribed();
    }
    
    public void unsubscribe()
    {
      if (compareAndSet(false, true)) {
        this.parent.remove(this.s);
      }
    }
  }
  
  private static final class Remover2
    extends AtomicBoolean
    implements Subscription
  {
    private static final long serialVersionUID = 247232374289553518L;
    final SubscriptionList parent;
    final ScheduledAction s;
    
    public Remover2(ScheduledAction paramScheduledAction, SubscriptionList paramSubscriptionList)
    {
      this.s = paramScheduledAction;
      this.parent = paramSubscriptionList;
    }
    
    public boolean isUnsubscribed()
    {
      return this.s.isUnsubscribed();
    }
    
    public void unsubscribe()
    {
      if (compareAndSet(false, true)) {
        this.parent.remove(this.s);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/schedulers/ScheduledAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */