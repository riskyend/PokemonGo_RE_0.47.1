package rx.observables;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.annotations.Experimental;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;
import rx.internal.operators.BlockingOperatorLatest;
import rx.internal.operators.BlockingOperatorMostRecent;
import rx.internal.operators.BlockingOperatorNext;
import rx.internal.operators.BlockingOperatorToFuture;
import rx.internal.operators.BlockingOperatorToIterator;
import rx.internal.operators.NotificationLite;
import rx.internal.util.BlockingUtils;
import rx.internal.util.UtilityFunctions;
import rx.subscriptions.Subscriptions;

public final class BlockingObservable<T>
{
  static final Object ON_START = new Object();
  static final Object SET_PRODUCER = new Object();
  static final Object UNSUBSCRIBE = new Object();
  private final Observable<? extends T> o;
  
  private BlockingObservable(Observable<? extends T> paramObservable)
  {
    this.o = paramObservable;
  }
  
  private T blockForSingle(Observable<? extends T> paramObservable)
  {
    final AtomicReference localAtomicReference1 = new AtomicReference();
    final AtomicReference localAtomicReference2 = new AtomicReference();
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    BlockingUtils.awaitForComplete(localCountDownLatch, paramObservable.subscribe(new Subscriber()
    {
      public void onCompleted()
      {
        localCountDownLatch.countDown();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localAtomicReference2.set(paramAnonymousThrowable);
        localCountDownLatch.countDown();
      }
      
      public void onNext(T paramAnonymousT)
      {
        localAtomicReference1.set(paramAnonymousT);
      }
    }));
    if (localAtomicReference2.get() != null)
    {
      if ((localAtomicReference2.get() instanceof RuntimeException)) {
        throw ((RuntimeException)localAtomicReference2.get());
      }
      throw new RuntimeException((Throwable)localAtomicReference2.get());
    }
    return (T)localAtomicReference1.get();
  }
  
  public static <T> BlockingObservable<T> from(Observable<? extends T> paramObservable)
  {
    return new BlockingObservable(paramObservable);
  }
  
  public T first()
  {
    return (T)blockForSingle(this.o.first());
  }
  
  public T first(Func1<? super T, Boolean> paramFunc1)
  {
    return (T)blockForSingle(this.o.first(paramFunc1));
  }
  
  public T firstOrDefault(T paramT)
  {
    return (T)blockForSingle(this.o.map(UtilityFunctions.identity()).firstOrDefault(paramT));
  }
  
  public T firstOrDefault(T paramT, Func1<? super T, Boolean> paramFunc1)
  {
    return (T)blockForSingle(this.o.filter(paramFunc1).map(UtilityFunctions.identity()).firstOrDefault(paramT));
  }
  
  public void forEach(final Action1<? super T> paramAction1)
  {
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final AtomicReference localAtomicReference = new AtomicReference();
    BlockingUtils.awaitForComplete(localCountDownLatch, this.o.subscribe(new Subscriber()
    {
      public void onCompleted()
      {
        localCountDownLatch.countDown();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localAtomicReference.set(paramAnonymousThrowable);
        localCountDownLatch.countDown();
      }
      
      public void onNext(T paramAnonymousT)
      {
        paramAction1.call(paramAnonymousT);
      }
    }));
    if (localAtomicReference.get() != null)
    {
      if ((localAtomicReference.get() instanceof RuntimeException)) {
        throw ((RuntimeException)localAtomicReference.get());
      }
      throw new RuntimeException((Throwable)localAtomicReference.get());
    }
  }
  
  public Iterator<T> getIterator()
  {
    return BlockingOperatorToIterator.toIterator(this.o);
  }
  
  public T last()
  {
    return (T)blockForSingle(this.o.last());
  }
  
  public T last(Func1<? super T, Boolean> paramFunc1)
  {
    return (T)blockForSingle(this.o.last(paramFunc1));
  }
  
  public T lastOrDefault(T paramT)
  {
    return (T)blockForSingle(this.o.map(UtilityFunctions.identity()).lastOrDefault(paramT));
  }
  
  public T lastOrDefault(T paramT, Func1<? super T, Boolean> paramFunc1)
  {
    return (T)blockForSingle(this.o.filter(paramFunc1).map(UtilityFunctions.identity()).lastOrDefault(paramT));
  }
  
  public Iterable<T> latest()
  {
    return BlockingOperatorLatest.latest(this.o);
  }
  
  public Iterable<T> mostRecent(T paramT)
  {
    return BlockingOperatorMostRecent.mostRecent(this.o, paramT);
  }
  
  public Iterable<T> next()
  {
    return BlockingOperatorNext.next(this.o);
  }
  
  public T single()
  {
    return (T)blockForSingle(this.o.single());
  }
  
  public T single(Func1<? super T, Boolean> paramFunc1)
  {
    return (T)blockForSingle(this.o.single(paramFunc1));
  }
  
  public T singleOrDefault(T paramT)
  {
    return (T)blockForSingle(this.o.map(UtilityFunctions.identity()).singleOrDefault(paramT));
  }
  
  public T singleOrDefault(T paramT, Func1<? super T, Boolean> paramFunc1)
  {
    return (T)blockForSingle(this.o.filter(paramFunc1).map(UtilityFunctions.identity()).singleOrDefault(paramT));
  }
  
  @Experimental
  public void subscribe()
  {
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final Throwable[] arrayOfThrowable = new Throwable[1];
    arrayOfThrowable[0] = null;
    BlockingUtils.awaitForComplete(localCountDownLatch, this.o.subscribe(new Subscriber()
    {
      public void onCompleted()
      {
        localCountDownLatch.countDown();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        arrayOfThrowable[0] = paramAnonymousThrowable;
        localCountDownLatch.countDown();
      }
      
      public void onNext(T paramAnonymousT) {}
    }));
    localCountDownLatch = arrayOfThrowable[0];
    if (localCountDownLatch != null)
    {
      if ((localCountDownLatch instanceof RuntimeException)) {
        throw ((RuntimeException)localCountDownLatch);
      }
      throw new RuntimeException(localCountDownLatch);
    }
  }
  
  @Experimental
  public void subscribe(Observer<? super T> paramObserver)
  {
    final NotificationLite localNotificationLite = NotificationLite.instance();
    final LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
    Subscription localSubscription = this.o.subscribe(new Subscriber()
    {
      public void onCompleted()
      {
        localLinkedBlockingQueue.offer(localNotificationLite.completed());
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localLinkedBlockingQueue.offer(localNotificationLite.error(paramAnonymousThrowable));
      }
      
      public void onNext(T paramAnonymousT)
      {
        localLinkedBlockingQueue.offer(localNotificationLite.next(paramAnonymousT));
      }
    });
    try
    {
      boolean bool;
      do
      {
        Object localObject2 = localLinkedBlockingQueue.poll();
        Object localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = localLinkedBlockingQueue.take();
        }
        bool = localNotificationLite.accept(paramObserver, localObject1);
      } while (!bool);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Thread.currentThread().interrupt();
      paramObserver.onError(localInterruptedException);
      return;
    }
    finally
    {
      localSubscription.unsubscribe();
    }
  }
  
  @Experimental
  public void subscribe(Subscriber<? super T> paramSubscriber)
  {
    final NotificationLite localNotificationLite = NotificationLite.instance();
    final LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
    final Producer[] arrayOfProducer = new Producer[1];
    arrayOfProducer[0] = null;
    Subscriber local6 = new Subscriber()
    {
      public void onCompleted()
      {
        localLinkedBlockingQueue.offer(localNotificationLite.completed());
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localLinkedBlockingQueue.offer(localNotificationLite.error(paramAnonymousThrowable));
      }
      
      public void onNext(T paramAnonymousT)
      {
        localLinkedBlockingQueue.offer(localNotificationLite.next(paramAnonymousT));
      }
      
      public void onStart()
      {
        localLinkedBlockingQueue.offer(BlockingObservable.ON_START);
      }
      
      public void setProducer(Producer paramAnonymousProducer)
      {
        arrayOfProducer[0] = paramAnonymousProducer;
        localLinkedBlockingQueue.offer(BlockingObservable.SET_PRODUCER);
      }
    };
    paramSubscriber.add(local6);
    paramSubscriber.add(Subscriptions.create(new Action0()
    {
      public void call()
      {
        localLinkedBlockingQueue.offer(BlockingObservable.UNSUBSCRIBE);
      }
    }));
    this.o.subscribe(local6);
    boolean bool;
    do
    {
      try
      {
        for (;;)
        {
          bool = paramSubscriber.isUnsubscribed();
          if (bool) {}
          Object localObject1;
          do
          {
            return;
            Object localObject2 = localLinkedBlockingQueue.poll();
            localObject1 = localObject2;
            if (localObject2 == null) {
              localObject1 = localLinkedBlockingQueue.take();
            }
          } while ((paramSubscriber.isUnsubscribed()) || (localObject1 == UNSUBSCRIBE));
          if (localObject1 != ON_START) {
            break;
          }
          paramSubscriber.onStart();
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          Thread.currentThread().interrupt();
          paramSubscriber.onError(localInterruptedException);
          return;
          if (localInterruptedException != SET_PRODUCER) {
            break;
          }
          paramSubscriber.setProducer(arrayOfProducer[0]);
        }
      }
      finally
      {
        local6.unsubscribe();
      }
      bool = localNotificationLite.accept(paramSubscriber, localInterruptedException);
    } while (!bool);
    local6.unsubscribe();
  }
  
  @Experimental
  public void subscribe(Action1<? super T> paramAction1)
  {
    subscribe(paramAction1, new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        throw new OnErrorNotImplementedException(paramAnonymousThrowable);
      }
    }, Actions.empty());
  }
  
  @Experimental
  public void subscribe(Action1<? super T> paramAction1, Action1<? super Throwable> paramAction11)
  {
    subscribe(paramAction1, paramAction11, Actions.empty());
  }
  
  @Experimental
  public void subscribe(final Action1<? super T> paramAction1, final Action1<? super Throwable> paramAction11, final Action0 paramAction0)
  {
    subscribe(new Observer()
    {
      public void onCompleted()
      {
        paramAction0.call();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramAction11.call(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        paramAction1.call(paramAnonymousT);
      }
    });
  }
  
  public Future<T> toFuture()
  {
    return BlockingOperatorToFuture.toFuture(this.o);
  }
  
  public Iterable<T> toIterable()
  {
    new Iterable()
    {
      public Iterator<T> iterator()
      {
        return BlockingObservable.this.getIterator();
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/observables/BlockingObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */