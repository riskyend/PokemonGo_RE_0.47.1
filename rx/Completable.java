package rx;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.annotations.Experimental;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.operators.CompletableOnSubscribeConcat;
import rx.internal.operators.CompletableOnSubscribeConcatArray;
import rx.internal.operators.CompletableOnSubscribeConcatIterable;
import rx.internal.operators.CompletableOnSubscribeMerge;
import rx.internal.operators.CompletableOnSubscribeMergeArray;
import rx.internal.operators.CompletableOnSubscribeMergeDelayErrorArray;
import rx.internal.operators.CompletableOnSubscribeMergeDelayErrorIterable;
import rx.internal.operators.CompletableOnSubscribeMergeIterable;
import rx.internal.operators.CompletableOnSubscribeTimeout;
import rx.internal.util.SubscriptionList;
import rx.internal.util.UtilityFunctions;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.MultipleAssignmentSubscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

@Experimental
public class Completable
{
  static final Completable COMPLETE = create(new CompletableOnSubscribe()
  {
    public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
    {
      paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
      paramAnonymousCompletableSubscriber.onCompleted();
    }
  });
  static final RxJavaErrorHandler ERROR_HANDLER = RxJavaPlugins.getInstance().getErrorHandler();
  static final Completable NEVER = create(new CompletableOnSubscribe()
  {
    public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
    {
      paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
    }
  });
  private final CompletableOnSubscribe onSubscribe;
  
  protected Completable(CompletableOnSubscribe paramCompletableOnSubscribe)
  {
    this.onSubscribe = paramCompletableOnSubscribe;
  }
  
  public static Completable amb(Iterable<? extends Completable> paramIterable)
  {
    requireNonNull(paramIterable);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
        paramAnonymousCompletableSubscriber.onSubscribe(localCompositeSubscription);
        final AtomicBoolean localAtomicBoolean = new AtomicBoolean();
        Completable.CompletableSubscriber local1 = new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            if (localAtomicBoolean.compareAndSet(false, true))
            {
              localCompositeSubscription.unsubscribe();
              paramAnonymousCompletableSubscriber.onCompleted();
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            if (localAtomicBoolean.compareAndSet(false, true))
            {
              localCompositeSubscription.unsubscribe();
              paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            Completable.ERROR_HANDLER.handleError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            localCompositeSubscription.add(paramAnonymous2Subscription);
          }
        };
        label274:
        label298:
        for (;;)
        {
          Iterator localIterator;
          try
          {
            localIterator = this.val$sources.iterator();
            if (localIterator == null)
            {
              paramAnonymousCompletableSubscriber.onError(new NullPointerException("The iterator returned is null"));
              return;
            }
          }
          catch (Throwable localThrowable1)
          {
            paramAnonymousCompletableSubscriber.onError(localThrowable1);
            return;
          }
          int i = 1;
          for (;;)
          {
            if ((localAtomicBoolean.get()) || (localThrowable1.isUnsubscribed())) {
              break label298;
            }
            try
            {
              boolean bool = localIterator.hasNext();
              if (!bool)
              {
                if (i == 0) {
                  break;
                }
                paramAnonymousCompletableSubscriber.onCompleted();
                return;
              }
            }
            catch (Throwable localThrowable2)
            {
              if (localAtomicBoolean.compareAndSet(false, true))
              {
                localThrowable1.unsubscribe();
                paramAnonymousCompletableSubscriber.onError(localThrowable2);
                return;
              }
              Completable.ERROR_HANDLER.handleError(localThrowable2);
              return;
            }
            i = 0;
            if ((localAtomicBoolean.get()) || (localThrowable1.isUnsubscribed())) {
              break;
            }
            Completable localCompletable;
            try
            {
              localCompletable = (Completable)localIterator.next();
              if (localCompletable != null) {
                break label274;
              }
              NullPointerException localNullPointerException = new NullPointerException("One of the sources is null");
              if (localAtomicBoolean.compareAndSet(false, true))
              {
                localThrowable1.unsubscribe();
                paramAnonymousCompletableSubscriber.onError(localNullPointerException);
                return;
              }
            }
            catch (Throwable localThrowable3)
            {
              if (localAtomicBoolean.compareAndSet(false, true))
              {
                localThrowable1.unsubscribe();
                paramAnonymousCompletableSubscriber.onError(localThrowable3);
                return;
              }
              Completable.ERROR_HANDLER.handleError(localThrowable3);
              return;
            }
            Completable.ERROR_HANDLER.handleError(localThrowable3);
            return;
            if ((localAtomicBoolean.get()) || (localThrowable1.isUnsubscribed())) {
              break;
            }
            localCompletable.subscribe(localThrowable3);
          }
        }
      }
    });
  }
  
  public static Completable amb(Completable... paramVarArgs)
  {
    requireNonNull(paramVarArgs);
    if (paramVarArgs.length == 0) {
      return complete();
    }
    if (paramVarArgs.length == 1) {
      return paramVarArgs[0];
    }
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
        paramAnonymousCompletableSubscriber.onSubscribe(localCompositeSubscription);
        final AtomicBoolean localAtomicBoolean = new AtomicBoolean();
        Object localObject = new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            if (localAtomicBoolean.compareAndSet(false, true))
            {
              localCompositeSubscription.unsubscribe();
              paramAnonymousCompletableSubscriber.onCompleted();
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            if (localAtomicBoolean.compareAndSet(false, true))
            {
              localCompositeSubscription.unsubscribe();
              paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            Completable.ERROR_HANDLER.handleError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            localCompositeSubscription.add(paramAnonymous2Subscription);
          }
        };
        Completable[] arrayOfCompletable = this.val$sources;
        int j = arrayOfCompletable.length;
        int i = 0;
        for (;;)
        {
          Completable localCompletable;
          if (i < j)
          {
            localCompletable = arrayOfCompletable[i];
            if (!localCompositeSubscription.isUnsubscribed()) {
              break label73;
            }
          }
          label73:
          do
          {
            return;
            if (localCompletable == null)
            {
              localObject = new NullPointerException("One of the sources is null");
              if (localAtomicBoolean.compareAndSet(false, true))
              {
                localCompositeSubscription.unsubscribe();
                paramAnonymousCompletableSubscriber.onError((Throwable)localObject);
                return;
              }
              Completable.ERROR_HANDLER.handleError((Throwable)localObject);
              return;
            }
          } while ((localAtomicBoolean.get()) || (localCompositeSubscription.isUnsubscribed()));
          localCompletable.subscribe((Completable.CompletableSubscriber)localObject);
          i += 1;
        }
      }
    });
  }
  
  public static Completable complete()
  {
    return COMPLETE;
  }
  
  public static Completable concat(Iterable<? extends Completable> paramIterable)
  {
    requireNonNull(paramIterable);
    return create(new CompletableOnSubscribeConcatIterable(paramIterable));
  }
  
  public static Completable concat(Observable<? extends Completable> paramObservable)
  {
    return concat(paramObservable, 2);
  }
  
  public static Completable concat(Observable<? extends Completable> paramObservable, int paramInt)
  {
    requireNonNull(paramObservable);
    if (paramInt < 1) {
      throw new IllegalArgumentException("prefetch > 0 required but it was " + paramInt);
    }
    return create(new CompletableOnSubscribeConcat(paramObservable, paramInt));
  }
  
  public static Completable concat(Completable... paramVarArgs)
  {
    requireNonNull(paramVarArgs);
    if (paramVarArgs.length == 0) {
      return complete();
    }
    if (paramVarArgs.length == 1) {
      return paramVarArgs[0];
    }
    return create(new CompletableOnSubscribeConcatArray(paramVarArgs));
  }
  
  public static Completable create(CompletableOnSubscribe paramCompletableOnSubscribe)
  {
    requireNonNull(paramCompletableOnSubscribe);
    try
    {
      paramCompletableOnSubscribe = new Completable(paramCompletableOnSubscribe);
      return paramCompletableOnSubscribe;
    }
    catch (NullPointerException paramCompletableOnSubscribe)
    {
      throw paramCompletableOnSubscribe;
    }
    catch (Throwable paramCompletableOnSubscribe)
    {
      ERROR_HANDLER.handleError(paramCompletableOnSubscribe);
      throw toNpe(paramCompletableOnSubscribe);
    }
  }
  
  public static Completable defer(Func0<? extends Completable> paramFunc0)
  {
    requireNonNull(paramFunc0);
    create(new CompletableOnSubscribe()
    {
      public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        try
        {
          Completable localCompletable = (Completable)this.val$completableFunc0.call();
          if (localCompletable == null)
          {
            paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
            paramAnonymousCompletableSubscriber.onError(new NullPointerException("The completable returned is null"));
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
          paramAnonymousCompletableSubscriber.onError(localThrowable);
          return;
        }
        localThrowable.subscribe(paramAnonymousCompletableSubscriber);
      }
    });
  }
  
  private static void deliverUncaughtException(Throwable paramThrowable)
  {
    Thread localThread = Thread.currentThread();
    localThread.getUncaughtExceptionHandler().uncaughtException(localThread, paramThrowable);
  }
  
  public static Completable error(Throwable paramThrowable)
  {
    requireNonNull(paramThrowable);
    create(new CompletableOnSubscribe()
    {
      public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
        paramAnonymousCompletableSubscriber.onError(this.val$error);
      }
    });
  }
  
  public static Completable error(Func0<? extends Throwable> paramFunc0)
  {
    requireNonNull(paramFunc0);
    create(new CompletableOnSubscribe()
    {
      public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
        try
        {
          Throwable localThrowable1 = (Throwable)this.val$errorFunc0.call();
          Object localObject = localThrowable1;
          if (localThrowable1 == null) {
            localObject = new NullPointerException("The error supplied is null");
          }
          paramAnonymousCompletableSubscriber.onError((Throwable)localObject);
          return;
        }
        catch (Throwable localThrowable2)
        {
          for (;;) {}
        }
      }
    });
  }
  
  public static Completable fromAction(Action0 paramAction0)
  {
    requireNonNull(paramAction0);
    create(new CompletableOnSubscribe()
    {
      public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        BooleanSubscription localBooleanSubscription = new BooleanSubscription();
        paramAnonymousCompletableSubscriber.onSubscribe(localBooleanSubscription);
        try
        {
          this.val$action.call();
          if (!localBooleanSubscription.isUnsubscribed()) {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
          return;
        }
        catch (Throwable localThrowable)
        {
          while (localBooleanSubscription.isUnsubscribed()) {}
          paramAnonymousCompletableSubscriber.onError(localThrowable);
        }
      }
    });
  }
  
  public static Completable fromCallable(Callable<?> paramCallable)
  {
    requireNonNull(paramCallable);
    create(new CompletableOnSubscribe()
    {
      public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        BooleanSubscription localBooleanSubscription = new BooleanSubscription();
        paramAnonymousCompletableSubscriber.onSubscribe(localBooleanSubscription);
        try
        {
          this.val$callable.call();
          if (!localBooleanSubscription.isUnsubscribed()) {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
          return;
        }
        catch (Throwable localThrowable)
        {
          while (localBooleanSubscription.isUnsubscribed()) {}
          paramAnonymousCompletableSubscriber.onError(localThrowable);
        }
      }
    });
  }
  
  public static Completable fromFuture(Future<?> paramFuture)
  {
    requireNonNull(paramFuture);
    return fromObservable(Observable.from(paramFuture));
  }
  
  public static Completable fromObservable(Observable<?> paramObservable)
  {
    requireNonNull(paramObservable);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        Subscriber local1 = new Subscriber()
        {
          public void onCompleted()
          {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onNext(Object paramAnonymous2Object) {}
        };
        paramAnonymousCompletableSubscriber.onSubscribe(local1);
        this.val$flowable.unsafeSubscribe(local1);
      }
    });
  }
  
  public static Completable fromSingle(Single<?> paramSingle)
  {
    requireNonNull(paramSingle);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        SingleSubscriber local1 = new SingleSubscriber()
        {
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSuccess(Object paramAnonymous2Object)
          {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
        };
        paramAnonymousCompletableSubscriber.onSubscribe(local1);
        this.val$single.subscribe(local1);
      }
    });
  }
  
  public static Completable merge(Iterable<? extends Completable> paramIterable)
  {
    requireNonNull(paramIterable);
    return create(new CompletableOnSubscribeMergeIterable(paramIterable));
  }
  
  public static Completable merge(Observable<? extends Completable> paramObservable)
  {
    return merge0(paramObservable, Integer.MAX_VALUE, false);
  }
  
  public static Completable merge(Observable<? extends Completable> paramObservable, int paramInt)
  {
    return merge0(paramObservable, paramInt, false);
  }
  
  public static Completable merge(Completable... paramVarArgs)
  {
    requireNonNull(paramVarArgs);
    if (paramVarArgs.length == 0) {
      return complete();
    }
    if (paramVarArgs.length == 1) {
      return paramVarArgs[0];
    }
    return create(new CompletableOnSubscribeMergeArray(paramVarArgs));
  }
  
  protected static Completable merge0(Observable<? extends Completable> paramObservable, int paramInt, boolean paramBoolean)
  {
    requireNonNull(paramObservable);
    if (paramInt < 1) {
      throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + paramInt);
    }
    return create(new CompletableOnSubscribeMerge(paramObservable, paramInt, paramBoolean));
  }
  
  public static Completable mergeDelayError(Iterable<? extends Completable> paramIterable)
  {
    requireNonNull(paramIterable);
    return create(new CompletableOnSubscribeMergeDelayErrorIterable(paramIterable));
  }
  
  public static Completable mergeDelayError(Observable<? extends Completable> paramObservable)
  {
    return merge0(paramObservable, Integer.MAX_VALUE, true);
  }
  
  public static Completable mergeDelayError(Observable<? extends Completable> paramObservable, int paramInt)
  {
    return merge0(paramObservable, paramInt, true);
  }
  
  public static Completable mergeDelayError(Completable... paramVarArgs)
  {
    requireNonNull(paramVarArgs);
    return create(new CompletableOnSubscribeMergeDelayErrorArray(paramVarArgs));
  }
  
  public static Completable never()
  {
    return NEVER;
  }
  
  static <T> T requireNonNull(T paramT)
  {
    if (paramT == null) {
      throw new NullPointerException();
    }
    return paramT;
  }
  
  public static Completable timer(long paramLong, TimeUnit paramTimeUnit)
  {
    return timer(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  public static Completable timer(final long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    requireNonNull(paramTimeUnit);
    requireNonNull(paramScheduler);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        MultipleAssignmentSubscription localMultipleAssignmentSubscription = new MultipleAssignmentSubscription();
        paramAnonymousCompletableSubscriber.onSubscribe(localMultipleAssignmentSubscription);
        if (!localMultipleAssignmentSubscription.isUnsubscribed())
        {
          final Scheduler.Worker localWorker = this.val$scheduler.createWorker();
          localMultipleAssignmentSubscription.set(localWorker);
          localWorker.schedule(new Action0()
          {
            public void call()
            {
              try
              {
                paramAnonymousCompletableSubscriber.onCompleted();
                return;
              }
              finally
              {
                localWorker.unsubscribe();
              }
            }
          }, paramLong, this.val$unit);
        }
      }
    });
  }
  
  static NullPointerException toNpe(Throwable paramThrowable)
  {
    NullPointerException localNullPointerException = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
    localNullPointerException.initCause(paramThrowable);
    return localNullPointerException;
  }
  
  public static <R> Completable using(Func0<R> paramFunc0, Func1<? super R, ? extends Completable> paramFunc1, Action1<? super R> paramAction1)
  {
    return using(paramFunc0, paramFunc1, paramAction1, true);
  }
  
  public static <R> Completable using(Func0<R> paramFunc0, final Func1<? super R, ? extends Completable> paramFunc1, final Action1<? super R> paramAction1, final boolean paramBoolean)
  {
    requireNonNull(paramFunc0);
    requireNonNull(paramFunc1);
    requireNonNull(paramAction1);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        try
        {
          Object localObject = this.val$resourceFunc0.call();
          Completable localCompletable;
          localThrowable4.subscribe(new Completable.CompletableSubscriber()
          {
            Subscription d;
            
            void dispose()
            {
              this.d.unsubscribe();
              if (this.val$once.compareAndSet(false, true)) {}
              try
              {
                Completable.13.this.val$disposer.call(localThrowable3);
                return;
              }
              catch (Throwable localThrowable)
              {
                Completable.ERROR_HANDLER.handleError(localThrowable);
              }
            }
            
            public void onCompleted()
            {
              if ((Completable.13.this.val$eager) && (this.val$once.compareAndSet(false, true))) {}
              try
              {
                Completable.13.this.val$disposer.call(localThrowable3);
                paramAnonymousCompletableSubscriber.onCompleted();
                if (!Completable.13.this.val$eager) {
                  dispose();
                }
                return;
              }
              catch (Throwable localThrowable)
              {
                paramAnonymousCompletableSubscriber.onError(localThrowable);
              }
            }
            
            public void onError(Throwable paramAnonymous2Throwable)
            {
              Throwable localThrowable1 = paramAnonymous2Throwable;
              if (Completable.13.this.val$eager)
              {
                localThrowable1 = paramAnonymous2Throwable;
                if (!this.val$once.compareAndSet(false, true)) {}
              }
              try
              {
                Completable.13.this.val$disposer.call(localThrowable3);
                localThrowable1 = paramAnonymous2Throwable;
              }
              catch (Throwable localThrowable2)
              {
                for (;;)
                {
                  CompositeException localCompositeException = new CompositeException(Arrays.asList(new Throwable[] { paramAnonymous2Throwable, localThrowable2 }));
                }
              }
              paramAnonymousCompletableSubscriber.onError(localThrowable1);
              if (!Completable.13.this.val$eager) {
                dispose();
              }
            }
            
            public void onSubscribe(Subscription paramAnonymous2Subscription)
            {
              this.d = paramAnonymous2Subscription;
              paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.create(new Action0()
              {
                public void call()
                {
                  Completable.13.1.this.dispose();
                }
              }));
            }
          });
        }
        catch (Throwable localThrowable1)
        {
          try
          {
            localCompletable = (Completable)paramFunc1.call(localObject);
            if (localCompletable != null) {
              break label211;
            }
          }
          catch (Throwable localThrowable4)
          {
            try
            {
              paramAction1.call(localThrowable1);
              Exceptions.throwIfFatal(localThrowable4);
              paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
              paramAnonymousCompletableSubscriber.onError(localThrowable4);
              return;
            }
            catch (Throwable localThrowable2)
            {
              Exceptions.throwIfFatal(localThrowable4);
              Exceptions.throwIfFatal(localThrowable2);
              paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
              paramAnonymousCompletableSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[] { localThrowable4, localThrowable2 })));
              return;
            }
          }
          try
          {
            paramAction1.call(localObject);
            paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
            paramAnonymousCompletableSubscriber.onError(new NullPointerException("The completable supplied is null"));
            return;
          }
          catch (Throwable localThrowable3)
          {
            Exceptions.throwIfFatal(localThrowable3);
            paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
            paramAnonymousCompletableSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[] { new NullPointerException("The completable supplied is null"), localThrowable3 })));
            return;
          }
          localThrowable1 = localThrowable1;
          paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
          paramAnonymousCompletableSubscriber.onError(localThrowable1);
          return;
        }
        label211:
      }
    });
  }
  
  public final Completable ambWith(Completable paramCompletable)
  {
    requireNonNull(paramCompletable);
    return amb(new Completable[] { this, paramCompletable });
  }
  
  public final <T> Observable<T> andThen(Observable<T> paramObservable)
  {
    requireNonNull(paramObservable);
    return paramObservable.delaySubscription(toObservable());
  }
  
  public final <T> Single<T> andThen(Single<T> paramSingle)
  {
    requireNonNull(paramSingle);
    return paramSingle.delaySubscription(toObservable());
  }
  
  public final void await()
  {
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final Throwable[] arrayOfThrowable = new Throwable[1];
    subscribe(new CompletableSubscriber()
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
      
      public void onSubscribe(Subscription paramAnonymousSubscription) {}
    });
    if (localCountDownLatch.getCount() == 0L) {
      if (arrayOfThrowable[0] != null) {
        Exceptions.propagate(arrayOfThrowable[0]);
      }
    }
    for (;;)
    {
      return;
      try
      {
        localCountDownLatch.await();
        if (arrayOfThrowable[0] == null) {
          continue;
        }
        Exceptions.propagate(arrayOfThrowable[0]);
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        throw Exceptions.propagate(localInterruptedException);
      }
    }
  }
  
  public final boolean await(long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool2 = true;
    requireNonNull(paramTimeUnit);
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final Throwable[] arrayOfThrowable = new Throwable[1];
    subscribe(new CompletableSubscriber()
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
      
      public void onSubscribe(Subscription paramAnonymousSubscription) {}
    });
    boolean bool1;
    if (localCountDownLatch.getCount() == 0L)
    {
      bool1 = bool2;
      if (arrayOfThrowable[0] != null)
      {
        Exceptions.propagate(arrayOfThrowable[0]);
        bool1 = bool2;
      }
    }
    for (;;)
    {
      return bool1;
      try
      {
        bool2 = localCountDownLatch.await(paramLong, paramTimeUnit);
        bool1 = bool2;
        if (!bool2) {
          continue;
        }
        bool1 = bool2;
        if (arrayOfThrowable[0] == null) {
          continue;
        }
        Exceptions.propagate(arrayOfThrowable[0]);
        return bool2;
      }
      catch (InterruptedException paramTimeUnit)
      {
        throw Exceptions.propagate(paramTimeUnit);
      }
    }
  }
  
  public final Completable compose(CompletableTransformer paramCompletableTransformer)
  {
    return (Completable)to(paramCompletableTransformer);
  }
  
  public final Completable concatWith(Completable paramCompletable)
  {
    requireNonNull(paramCompletable);
    return concat(new Completable[] { this, paramCompletable });
  }
  
  public final Completable delay(long paramLong, TimeUnit paramTimeUnit)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation(), false);
  }
  
  public final Completable delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return delay(paramLong, paramTimeUnit, paramScheduler, false);
  }
  
  public final Completable delay(final long paramLong, TimeUnit paramTimeUnit, final Scheduler paramScheduler, final boolean paramBoolean)
  {
    requireNonNull(paramTimeUnit);
    requireNonNull(paramScheduler);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
        final Scheduler.Worker localWorker = paramScheduler.createWorker();
        localCompositeSubscription.add(localWorker);
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            localCompositeSubscription.add(localWorker.schedule(new Action0()
            {
              public void call()
              {
                try
                {
                  Completable.16.1.this.val$s.onCompleted();
                  return;
                }
                finally
                {
                  Completable.16.1.this.val$w.unsubscribe();
                }
              }
            }, Completable.16.this.val$delay, Completable.16.this.val$unit));
          }
          
          public void onError(final Throwable paramAnonymous2Throwable)
          {
            if (Completable.16.this.val$delayError)
            {
              localCompositeSubscription.add(localWorker.schedule(new Action0()
              {
                public void call()
                {
                  try
                  {
                    Completable.16.1.this.val$s.onError(paramAnonymous2Throwable);
                    return;
                  }
                  finally
                  {
                    Completable.16.1.this.val$w.unsubscribe();
                  }
                }
              }, Completable.16.this.val$delay, Completable.16.this.val$unit));
              return;
            }
            paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            localCompositeSubscription.add(paramAnonymous2Subscription);
            paramAnonymousCompletableSubscriber.onSubscribe(localCompositeSubscription);
          }
        });
      }
    });
  }
  
  public final Completable doAfterTerminate(Action0 paramAction0)
  {
    return doOnLifecycle(Actions.empty(), Actions.empty(), Actions.empty(), paramAction0, Actions.empty());
  }
  
  @Deprecated
  public final Completable doOnComplete(Action0 paramAction0)
  {
    return doOnCompleted(paramAction0);
  }
  
  public final Completable doOnCompleted(Action0 paramAction0)
  {
    return doOnLifecycle(Actions.empty(), Actions.empty(), paramAction0, Actions.empty(), Actions.empty());
  }
  
  public final Completable doOnError(Action1<? super Throwable> paramAction1)
  {
    return doOnLifecycle(Actions.empty(), paramAction1, Actions.empty(), Actions.empty(), Actions.empty());
  }
  
  protected final Completable doOnLifecycle(final Action1<? super Subscription> paramAction1, final Action1<? super Throwable> paramAction11, final Action0 paramAction01, final Action0 paramAction02, final Action0 paramAction03)
  {
    requireNonNull(paramAction1);
    requireNonNull(paramAction11);
    requireNonNull(paramAction01);
    requireNonNull(paramAction02);
    requireNonNull(paramAction03);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            try
            {
              Completable.17.this.val$onComplete.call();
              paramAnonymousCompletableSubscriber.onCompleted();
              return;
            }
            catch (Throwable localThrowable1)
            {
              try
              {
                Completable.17.this.val$onAfterComplete.call();
                return;
              }
              catch (Throwable localThrowable2)
              {
                Completable.ERROR_HANDLER.handleError(localThrowable2);
              }
              localThrowable1 = localThrowable1;
              paramAnonymousCompletableSubscriber.onError(localThrowable1);
              return;
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              Completable.17.this.val$onError.call(paramAnonymous2Throwable);
              paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            catch (Throwable localThrowable)
            {
              for (;;)
              {
                paramAnonymous2Throwable = new CompositeException(Arrays.asList(new Throwable[] { paramAnonymous2Throwable, localThrowable }));
              }
            }
          }
          
          public void onSubscribe(final Subscription paramAnonymous2Subscription)
          {
            try
            {
              Completable.17.this.val$onSubscribe.call(paramAnonymous2Subscription);
              paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.create(new Action0()
              {
                public void call()
                {
                  try
                  {
                    Completable.17.this.val$onUnsubscribe.call();
                    paramAnonymous2Subscription.unsubscribe();
                    return;
                  }
                  catch (Throwable localThrowable)
                  {
                    for (;;)
                    {
                      Completable.ERROR_HANDLER.handleError(localThrowable);
                    }
                  }
                }
              }));
              return;
            }
            catch (Throwable localThrowable)
            {
              paramAnonymous2Subscription.unsubscribe();
              paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
              paramAnonymousCompletableSubscriber.onError(localThrowable);
            }
          }
        });
      }
    });
  }
  
  public final Completable doOnSubscribe(Action1<? super Subscription> paramAction1)
  {
    return doOnLifecycle(paramAction1, Actions.empty(), Actions.empty(), Actions.empty(), Actions.empty());
  }
  
  public final Completable doOnTerminate(final Action0 paramAction0)
  {
    doOnLifecycle(Actions.empty(), new Action1()
    {
      public void call(Throwable paramAnonymousThrowable)
      {
        paramAction0.call();
      }
    }, paramAction0, Actions.empty(), Actions.empty());
  }
  
  public final Completable doOnUnsubscribe(Action0 paramAction0)
  {
    return doOnLifecycle(Actions.empty(), Actions.empty(), Actions.empty(), Actions.empty(), paramAction0);
  }
  
  public final Completable endWith(Completable paramCompletable)
  {
    return concatWith(paramCompletable);
  }
  
  public final <T> Observable<T> endWith(Observable<T> paramObservable)
  {
    return paramObservable.startWith(toObservable());
  }
  
  public final Throwable get()
  {
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final Throwable[] arrayOfThrowable = new Throwable[1];
    subscribe(new CompletableSubscriber()
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
      
      public void onSubscribe(Subscription paramAnonymousSubscription) {}
    });
    if (localCountDownLatch.getCount() == 0L) {
      return arrayOfThrowable[0];
    }
    try
    {
      localCountDownLatch.await();
      return arrayOfThrowable[0];
    }
    catch (InterruptedException localInterruptedException)
    {
      throw Exceptions.propagate(localInterruptedException);
    }
  }
  
  public final Throwable get(long paramLong, TimeUnit paramTimeUnit)
  {
    requireNonNull(paramTimeUnit);
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final Throwable[] arrayOfThrowable = new Throwable[1];
    subscribe(new CompletableSubscriber()
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
      
      public void onSubscribe(Subscription paramAnonymousSubscription) {}
    });
    if (localCountDownLatch.getCount() == 0L) {
      return arrayOfThrowable[0];
    }
    try
    {
      boolean bool = localCountDownLatch.await(paramLong, paramTimeUnit);
      if (bool) {
        return arrayOfThrowable[0];
      }
    }
    catch (InterruptedException paramTimeUnit)
    {
      throw Exceptions.propagate(paramTimeUnit);
    }
    Exceptions.propagate(new TimeoutException());
    return null;
  }
  
  public final Completable lift(final CompletableOperator paramCompletableOperator)
  {
    requireNonNull(paramCompletableOperator);
    create(new CompletableOnSubscribe()
    {
      public void call(Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        try
        {
          paramAnonymousCompletableSubscriber = (Completable.CompletableSubscriber)paramCompletableOperator.call(paramAnonymousCompletableSubscriber);
          Completable.this.subscribe(paramAnonymousCompletableSubscriber);
          return;
        }
        catch (NullPointerException paramAnonymousCompletableSubscriber)
        {
          throw paramAnonymousCompletableSubscriber;
        }
        catch (Throwable paramAnonymousCompletableSubscriber)
        {
          throw Completable.toNpe(paramAnonymousCompletableSubscriber);
        }
      }
    });
  }
  
  public final Completable mergeWith(Completable paramCompletable)
  {
    requireNonNull(paramCompletable);
    return merge(new Completable[] { this, paramCompletable });
  }
  
  public final Completable observeOn(final Scheduler paramScheduler)
  {
    requireNonNull(paramScheduler);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        final SubscriptionList localSubscriptionList = new SubscriptionList();
        final Scheduler.Worker localWorker = paramScheduler.createWorker();
        localSubscriptionList.add(localWorker);
        paramAnonymousCompletableSubscriber.onSubscribe(localSubscriptionList);
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            localWorker.schedule(new Action0()
            {
              public void call()
              {
                try
                {
                  Completable.22.1.this.val$s.onCompleted();
                  return;
                }
                finally
                {
                  Completable.22.1.this.val$ad.unsubscribe();
                }
              }
            });
          }
          
          public void onError(final Throwable paramAnonymous2Throwable)
          {
            localWorker.schedule(new Action0()
            {
              public void call()
              {
                try
                {
                  Completable.22.1.this.val$s.onError(paramAnonymous2Throwable);
                  return;
                }
                finally
                {
                  Completable.22.1.this.val$ad.unsubscribe();
                }
              }
            });
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            localSubscriptionList.add(paramAnonymous2Subscription);
          }
        });
      }
    });
  }
  
  public final Completable onErrorComplete()
  {
    return onErrorComplete(UtilityFunctions.alwaysTrue());
  }
  
  public final Completable onErrorComplete(final Func1<? super Throwable, Boolean> paramFunc1)
  {
    requireNonNull(paramFunc1);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              boolean bool = ((Boolean)Completable.23.this.val$predicate.call(paramAnonymous2Throwable)).booleanValue();
              if (bool)
              {
                paramAnonymousCompletableSubscriber.onCompleted();
                return;
              }
            }
            catch (Throwable localThrowable)
            {
              new CompositeException(Arrays.asList(new Throwable[] { paramAnonymous2Throwable, localThrowable }));
              return;
            }
            paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            paramAnonymousCompletableSubscriber.onSubscribe(paramAnonymous2Subscription);
          }
        });
      }
    });
  }
  
  public final Completable onErrorResumeNext(final Func1<? super Throwable, ? extends Completable> paramFunc1)
  {
    requireNonNull(paramFunc1);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        final SerialSubscription localSerialSubscription = new SerialSubscription();
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              Completable localCompletable = (Completable)Completable.24.this.val$errorMapper.call(paramAnonymous2Throwable);
              if (localCompletable == null)
              {
                paramAnonymous2Throwable = new CompositeException(Arrays.asList(new Throwable[] { paramAnonymous2Throwable, new NullPointerException("The completable returned is null") }));
                paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
                return;
              }
            }
            catch (Throwable localThrowable)
            {
              paramAnonymous2Throwable = new CompositeException(Arrays.asList(new Throwable[] { paramAnonymous2Throwable, localThrowable }));
              paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            localThrowable.subscribe(new Completable.CompletableSubscriber()
            {
              public void onCompleted()
              {
                Completable.24.1.this.val$s.onCompleted();
              }
              
              public void onError(Throwable paramAnonymous3Throwable)
              {
                Completable.24.1.this.val$s.onError(paramAnonymous3Throwable);
              }
              
              public void onSubscribe(Subscription paramAnonymous3Subscription)
              {
                Completable.24.1.this.val$sd.set(paramAnonymous3Subscription);
              }
            });
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            localSerialSubscription.set(paramAnonymous2Subscription);
          }
        });
      }
    });
  }
  
  public final Completable repeat()
  {
    return fromObservable(toObservable().repeat());
  }
  
  public final Completable repeat(long paramLong)
  {
    return fromObservable(toObservable().repeat(paramLong));
  }
  
  public final Completable repeatWhen(Func1<? super Observable<? extends Void>, ? extends Observable<?>> paramFunc1)
  {
    requireNonNull(paramFunc1);
    return fromObservable(toObservable().repeatWhen(paramFunc1));
  }
  
  public final Completable retry()
  {
    return fromObservable(toObservable().retry());
  }
  
  public final Completable retry(long paramLong)
  {
    return fromObservable(toObservable().retry(paramLong));
  }
  
  public final Completable retry(Func2<Integer, Throwable, Boolean> paramFunc2)
  {
    return fromObservable(toObservable().retry(paramFunc2));
  }
  
  public final Completable retryWhen(Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> paramFunc1)
  {
    return fromObservable(toObservable().retryWhen(paramFunc1));
  }
  
  public final Completable startWith(Completable paramCompletable)
  {
    requireNonNull(paramCompletable);
    return concat(new Completable[] { paramCompletable, this });
  }
  
  public final <T> Observable<T> startWith(Observable<T> paramObservable)
  {
    requireNonNull(paramObservable);
    return toObservable().startWith(paramObservable);
  }
  
  public final Subscription subscribe()
  {
    final MultipleAssignmentSubscription localMultipleAssignmentSubscription = new MultipleAssignmentSubscription();
    subscribe(new CompletableSubscriber()
    {
      public void onCompleted()
      {
        localMultipleAssignmentSubscription.unsubscribe();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        Completable.ERROR_HANDLER.handleError(paramAnonymousThrowable);
        localMultipleAssignmentSubscription.unsubscribe();
        Completable.deliverUncaughtException(paramAnonymousThrowable);
      }
      
      public void onSubscribe(Subscription paramAnonymousSubscription)
      {
        localMultipleAssignmentSubscription.set(paramAnonymousSubscription);
      }
    });
    return localMultipleAssignmentSubscription;
  }
  
  public final Subscription subscribe(final Action0 paramAction0)
  {
    requireNonNull(paramAction0);
    final MultipleAssignmentSubscription localMultipleAssignmentSubscription = new MultipleAssignmentSubscription();
    subscribe(new CompletableSubscriber()
    {
      public void onCompleted()
      {
        try
        {
          paramAction0.call();
          return;
        }
        catch (Throwable localThrowable)
        {
          Completable.ERROR_HANDLER.handleError(localThrowable);
          Completable.deliverUncaughtException(localThrowable);
          return;
        }
        finally
        {
          localMultipleAssignmentSubscription.unsubscribe();
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        Completable.ERROR_HANDLER.handleError(paramAnonymousThrowable);
        localMultipleAssignmentSubscription.unsubscribe();
        Completable.deliverUncaughtException(paramAnonymousThrowable);
      }
      
      public void onSubscribe(Subscription paramAnonymousSubscription)
      {
        localMultipleAssignmentSubscription.set(paramAnonymousSubscription);
      }
    });
    return localMultipleAssignmentSubscription;
  }
  
  public final Subscription subscribe(final Action1<? super Throwable> paramAction1, final Action0 paramAction0)
  {
    requireNonNull(paramAction1);
    requireNonNull(paramAction0);
    final MultipleAssignmentSubscription localMultipleAssignmentSubscription = new MultipleAssignmentSubscription();
    subscribe(new CompletableSubscriber()
    {
      public void onCompleted()
      {
        try
        {
          paramAction0.call();
          localMultipleAssignmentSubscription.unsubscribe();
          return;
        }
        catch (Throwable localThrowable)
        {
          onError(localThrowable);
        }
      }
      
      /* Error */
      public void onError(Throwable paramAnonymousThrowable)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 29	rx/Completable$27:val$onError	Lrx/functions/Action1;
        //   4: aload_1
        //   5: invokeinterface 55 2 0
        //   10: aload_0
        //   11: getfield 27	rx/Completable$27:val$mad	Lrx/subscriptions/MultipleAssignmentSubscription;
        //   14: invokevirtual 46	rx/subscriptions/MultipleAssignmentSubscription:unsubscribe	()V
        //   17: return
        //   18: astore_2
        //   19: new 57	rx/exceptions/CompositeException
        //   22: dup
        //   23: iconst_2
        //   24: anewarray 36	java/lang/Throwable
        //   27: dup
        //   28: iconst_0
        //   29: aload_1
        //   30: aastore
        //   31: dup
        //   32: iconst_1
        //   33: aload_2
        //   34: aastore
        //   35: invokestatic 63	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
        //   38: invokespecial 66	rx/exceptions/CompositeException:<init>	(Ljava/util/Collection;)V
        //   41: astore_1
        //   42: getstatic 70	rx/Completable:ERROR_HANDLER	Lrx/plugins/RxJavaErrorHandler;
        //   45: aload_1
        //   46: invokevirtual 75	rx/plugins/RxJavaErrorHandler:handleError	(Ljava/lang/Throwable;)V
        //   49: aload_1
        //   50: invokestatic 78	rx/Completable:access$000	(Ljava/lang/Throwable;)V
        //   53: aload_0
        //   54: getfield 27	rx/Completable$27:val$mad	Lrx/subscriptions/MultipleAssignmentSubscription;
        //   57: invokevirtual 46	rx/subscriptions/MultipleAssignmentSubscription:unsubscribe	()V
        //   60: return
        //   61: astore_1
        //   62: aload_0
        //   63: getfield 27	rx/Completable$27:val$mad	Lrx/subscriptions/MultipleAssignmentSubscription;
        //   66: invokevirtual 46	rx/subscriptions/MultipleAssignmentSubscription:unsubscribe	()V
        //   69: aload_1
        //   70: athrow
        //   71: astore_1
        //   72: goto -10 -> 62
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	75	0	this	27
        //   0	75	1	paramAnonymousThrowable	Throwable
        //   18	16	2	localThrowable	Throwable
        // Exception table:
        //   from	to	target	type
        //   0	10	18	java/lang/Throwable
        //   0	10	61	finally
        //   19	42	61	finally
        //   42	53	71	finally
      }
      
      public void onSubscribe(Subscription paramAnonymousSubscription)
      {
        localMultipleAssignmentSubscription.set(paramAnonymousSubscription);
      }
    });
    return localMultipleAssignmentSubscription;
  }
  
  public final void subscribe(CompletableSubscriber paramCompletableSubscriber)
  {
    requireNonNull(paramCompletableSubscriber);
    try
    {
      this.onSubscribe.call(paramCompletableSubscriber);
      return;
    }
    catch (NullPointerException paramCompletableSubscriber)
    {
      throw paramCompletableSubscriber;
    }
    catch (Throwable paramCompletableSubscriber)
    {
      ERROR_HANDLER.handleError(paramCompletableSubscriber);
      throw toNpe(paramCompletableSubscriber);
    }
  }
  
  public final <T> void subscribe(final Subscriber<T> paramSubscriber)
  {
    requireNonNull(paramSubscriber);
    if (paramSubscriber == null) {}
    try
    {
      throw new NullPointerException("The RxJavaPlugins.onSubscribe returned a null Subscriber");
    }
    catch (NullPointerException paramSubscriber)
    {
      throw paramSubscriber;
      subscribe(new CompletableSubscriber()
      {
        public void onCompleted()
        {
          paramSubscriber.onCompleted();
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          paramSubscriber.onError(paramAnonymousThrowable);
        }
        
        public void onSubscribe(Subscription paramAnonymousSubscription)
        {
          paramSubscriber.add(paramAnonymousSubscription);
        }
      });
      return;
    }
    catch (Throwable paramSubscriber)
    {
      ERROR_HANDLER.handleError(paramSubscriber);
      throw toNpe(paramSubscriber);
    }
  }
  
  public final Completable subscribeOn(final Scheduler paramScheduler)
  {
    requireNonNull(paramScheduler);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        final Scheduler.Worker localWorker = paramScheduler.createWorker();
        localWorker.schedule(new Action0()
        {
          public void call()
          {
            try
            {
              Completable.this.subscribe(paramAnonymousCompletableSubscriber);
              return;
            }
            finally
            {
              localWorker.unsubscribe();
            }
          }
        });
      }
    });
  }
  
  public final Completable timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return timeout0(paramLong, paramTimeUnit, Schedulers.computation(), null);
  }
  
  public final Completable timeout(long paramLong, TimeUnit paramTimeUnit, Completable paramCompletable)
  {
    requireNonNull(paramCompletable);
    return timeout0(paramLong, paramTimeUnit, Schedulers.computation(), paramCompletable);
  }
  
  public final Completable timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return timeout0(paramLong, paramTimeUnit, paramScheduler, null);
  }
  
  public final Completable timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, Completable paramCompletable)
  {
    requireNonNull(paramCompletable);
    return timeout0(paramLong, paramTimeUnit, paramScheduler, paramCompletable);
  }
  
  public final Completable timeout0(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, Completable paramCompletable)
  {
    requireNonNull(paramTimeUnit);
    requireNonNull(paramScheduler);
    return create(new CompletableOnSubscribeTimeout(this, paramLong, paramTimeUnit, paramScheduler, paramCompletable));
  }
  
  public final <U> U to(Func1<? super Completable, U> paramFunc1)
  {
    return (U)paramFunc1.call(this);
  }
  
  public final <T> Observable<T> toObservable()
  {
    Observable.create(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        Completable.this.subscribe(paramAnonymousSubscriber);
      }
    });
  }
  
  public final <T> Single<T> toSingle(final Func0<? extends T> paramFunc0)
  {
    requireNonNull(paramFunc0);
    Single.create(new Single.OnSubscribe()
    {
      public void call(final SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            try
            {
              Object localObject = Completable.31.this.val$completionValueFunc0.call();
              if (localObject == null)
              {
                paramAnonymousSingleSubscriber.onError(new NullPointerException("The value supplied is null"));
                return;
              }
            }
            catch (Throwable localThrowable)
            {
              paramAnonymousSingleSubscriber.onError(localThrowable);
              return;
            }
            paramAnonymousSingleSubscriber.onSuccess(localThrowable);
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousSingleSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            paramAnonymousSingleSubscriber.add(paramAnonymous2Subscription);
          }
        });
      }
    });
  }
  
  public final <T> Single<T> toSingleDefault(final T paramT)
  {
    requireNonNull(paramT);
    toSingle(new Func0()
    {
      public T call()
      {
        return (T)paramT;
      }
    });
  }
  
  public final Completable unsubscribeOn(final Scheduler paramScheduler)
  {
    requireNonNull(paramScheduler);
    create(new CompletableOnSubscribe()
    {
      public void call(final Completable.CompletableSubscriber paramAnonymousCompletableSubscriber)
      {
        Completable.this.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            paramAnonymousCompletableSubscriber.onCompleted();
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousCompletableSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(final Subscription paramAnonymous2Subscription)
          {
            paramAnonymousCompletableSubscriber.onSubscribe(Subscriptions.create(new Action0()
            {
              public void call()
              {
                final Scheduler.Worker localWorker = Completable.33.this.val$scheduler.createWorker();
                localWorker.schedule(new Action0()
                {
                  public void call()
                  {
                    try
                    {
                      Completable.33.1.1.this.val$d.unsubscribe();
                      return;
                    }
                    finally
                    {
                      localWorker.unsubscribe();
                    }
                  }
                });
              }
            }));
          }
        });
      }
    });
  }
  
  public static abstract interface CompletableOnSubscribe
    extends Action1<Completable.CompletableSubscriber>
  {}
  
  public static abstract interface CompletableOperator
    extends Func1<Completable.CompletableSubscriber, Completable.CompletableSubscriber>
  {}
  
  public static abstract interface CompletableSubscriber
  {
    public abstract void onCompleted();
    
    public abstract void onError(Throwable paramThrowable);
    
    public abstract void onSubscribe(Subscription paramSubscription);
  }
  
  public static abstract interface CompletableTransformer
    extends Func1<Completable, Completable>
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/Completable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */