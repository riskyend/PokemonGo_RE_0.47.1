package rx;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import rx.annotations.Beta;
import rx.annotations.Experimental;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;
import rx.functions.Func5;
import rx.functions.Func6;
import rx.functions.Func7;
import rx.functions.Func8;
import rx.functions.Func9;
import rx.functions.FuncN;
import rx.internal.operators.OnSubscribeToObservableFuture;
import rx.internal.operators.OperatorDelay;
import rx.internal.operators.OperatorDoAfterTerminate;
import rx.internal.operators.OperatorDoOnEach;
import rx.internal.operators.OperatorDoOnSubscribe;
import rx.internal.operators.OperatorDoOnUnsubscribe;
import rx.internal.operators.OperatorMap;
import rx.internal.operators.OperatorObserveOn;
import rx.internal.operators.OperatorOnErrorResumeNextViaFunction;
import rx.internal.operators.OperatorTimeout;
import rx.internal.operators.SingleOnSubscribeDelaySubscriptionOther;
import rx.internal.operators.SingleOnSubscribeUsing;
import rx.internal.operators.SingleOperatorOnErrorResumeNext;
import rx.internal.operators.SingleOperatorZip;
import rx.internal.producers.SingleDelayedProducer;
import rx.internal.util.ScalarSynchronousSingle;
import rx.internal.util.UtilityFunctions;
import rx.observers.SafeSubscriber;
import rx.observers.SerializedSubscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSingleExecutionHook;
import rx.schedulers.Schedulers;
import rx.singles.BlockingSingle;
import rx.subscriptions.Subscriptions;

@Beta
public class Single<T>
{
  static RxJavaSingleExecutionHook hook = RxJavaPlugins.getInstance().getSingleExecutionHook();
  final Observable.OnSubscribe<T> onSubscribe;
  
  private Single(Observable.OnSubscribe<T> paramOnSubscribe)
  {
    this.onSubscribe = paramOnSubscribe;
  }
  
  protected Single(final OnSubscribe<T> paramOnSubscribe)
  {
    this.onSubscribe = new Observable.OnSubscribe()
    {
      public void call(final Subscriber<? super T> paramAnonymousSubscriber)
      {
        final Object localObject = new SingleDelayedProducer(paramAnonymousSubscriber);
        paramAnonymousSubscriber.setProducer((Producer)localObject);
        localObject = new SingleSubscriber()
        {
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSuccess(T paramAnonymous2T)
          {
            localObject.setValue(paramAnonymous2T);
          }
        };
        paramAnonymousSubscriber.add((Subscription)localObject);
        paramOnSubscribe.call(localObject);
      }
    };
  }
  
  private static <T> Observable<T> asObservable(Single<T> paramSingle)
  {
    return Observable.create(paramSingle.onSubscribe);
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6, Single<? extends T> paramSingle7)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6), asObservable(paramSingle7));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6, Single<? extends T> paramSingle7, Single<? extends T> paramSingle8)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6), asObservable(paramSingle7), asObservable(paramSingle8));
  }
  
  public static <T> Observable<T> concat(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6, Single<? extends T> paramSingle7, Single<? extends T> paramSingle8, Single<? extends T> paramSingle9)
  {
    return Observable.concat(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6), asObservable(paramSingle7), asObservable(paramSingle8), asObservable(paramSingle9));
  }
  
  public static <T> Single<T> create(OnSubscribe<T> paramOnSubscribe)
  {
    return new Single(hook.onCreate(paramOnSubscribe));
  }
  
  @Experimental
  public static <T> Single<T> defer(Callable<Single<T>> paramCallable)
  {
    create(new OnSubscribe()
    {
      public void call(SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        try
        {
          Single localSingle = (Single)this.val$singleFactory.call();
          localSingle.subscribe(paramAnonymousSingleSubscriber);
          return;
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwIfFatal(localThrowable);
          paramAnonymousSingleSubscriber.onError(localThrowable);
        }
      }
    });
  }
  
  public static <T> Single<T> error(Throwable paramThrowable)
  {
    create(new OnSubscribe()
    {
      public void call(SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        paramAnonymousSingleSubscriber.onError(this.val$exception);
      }
    });
  }
  
  public static <T> Single<T> from(Future<? extends T> paramFuture)
  {
    return new Single(OnSubscribeToObservableFuture.toObservableFuture(paramFuture));
  }
  
  public static <T> Single<T> from(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit)
  {
    return new Single(OnSubscribeToObservableFuture.toObservableFuture(paramFuture, paramLong, paramTimeUnit));
  }
  
  public static <T> Single<T> from(Future<? extends T> paramFuture, Scheduler paramScheduler)
  {
    return new Single(OnSubscribeToObservableFuture.toObservableFuture(paramFuture)).subscribeOn(paramScheduler);
  }
  
  @Beta
  public static <T> Single<T> fromCallable(Callable<? extends T> paramCallable)
  {
    create(new OnSubscribe()
    {
      public void call(SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        try
        {
          Object localObject = this.val$func.call();
          paramAnonymousSingleSubscriber.onSuccess(localObject);
          return;
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwIfFatal(localThrowable);
          paramAnonymousSingleSubscriber.onError(localThrowable);
        }
      }
    });
  }
  
  static <T> Single<? extends T>[] iterableToArray(Iterable<? extends Single<? extends T>> paramIterable)
  {
    if ((paramIterable instanceof Collection))
    {
      paramIterable = (Collection)paramIterable;
      return (Single[])paramIterable.toArray(new Single[paramIterable.size()]);
    }
    Object localObject = new Single[8];
    int i = 0;
    Iterator localIterator = paramIterable.iterator();
    for (paramIterable = (Iterable<? extends Single<? extends T>>)localObject; localIterator.hasNext(); paramIterable = (Iterable<? extends Single<? extends T>>)localObject)
    {
      Single localSingle = (Single)localIterator.next();
      localObject = paramIterable;
      if (i == paramIterable.length)
      {
        localObject = new Single[(i >> 2) + i];
        System.arraycopy(paramIterable, 0, localObject, 0, i);
      }
      localObject[i] = localSingle;
      i += 1;
    }
    if (paramIterable.length == i) {
      return paramIterable;
    }
    localObject = new Single[i];
    System.arraycopy(paramIterable, 0, localObject, 0, i);
    return (Single<? extends T>[])localObject;
  }
  
  public static <T> Single<T> just(T paramT)
  {
    return ScalarSynchronousSingle.create(paramT);
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6, Single<? extends T> paramSingle7)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6), asObservable(paramSingle7));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6, Single<? extends T> paramSingle7, Single<? extends T> paramSingle8)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6), asObservable(paramSingle7), asObservable(paramSingle8));
  }
  
  public static <T> Observable<T> merge(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2, Single<? extends T> paramSingle3, Single<? extends T> paramSingle4, Single<? extends T> paramSingle5, Single<? extends T> paramSingle6, Single<? extends T> paramSingle7, Single<? extends T> paramSingle8, Single<? extends T> paramSingle9)
  {
    return Observable.merge(asObservable(paramSingle1), asObservable(paramSingle2), asObservable(paramSingle3), asObservable(paramSingle4), asObservable(paramSingle5), asObservable(paramSingle6), asObservable(paramSingle7), asObservable(paramSingle8), asObservable(paramSingle9));
  }
  
  public static <T> Single<T> merge(Single<? extends Single<? extends T>> paramSingle)
  {
    if ((paramSingle instanceof ScalarSynchronousSingle)) {
      return ((ScalarSynchronousSingle)paramSingle).scalarFlatMap(UtilityFunctions.identity());
    }
    create(new OnSubscribe()
    {
      public void call(final SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        this.val$source.subscribe(new SingleSubscriber()
        {
          public void onError(Throwable paramAnonymous2Throwable)
          {
            paramAnonymousSingleSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onSuccess(Single<? extends T> paramAnonymous2Single)
          {
            paramAnonymous2Single.subscribe(paramAnonymousSingleSubscriber);
          }
        });
      }
    });
  }
  
  private Single<Observable<T>> nest()
  {
    return just(asObservable(this));
  }
  
  @Experimental
  public static <T, Resource> Single<T> using(Func0<Resource> paramFunc0, Func1<? super Resource, ? extends Single<? extends T>> paramFunc1, Action1<? super Resource> paramAction1)
  {
    return using(paramFunc0, paramFunc1, paramAction1, false);
  }
  
  @Experimental
  public static <T, Resource> Single<T> using(Func0<Resource> paramFunc0, Func1<? super Resource, ? extends Single<? extends T>> paramFunc1, Action1<? super Resource> paramAction1, boolean paramBoolean)
  {
    if (paramFunc0 == null) {
      throw new NullPointerException("resourceFactory is null");
    }
    if (paramFunc1 == null) {
      throw new NullPointerException("singleFactory is null");
    }
    if (paramAction1 == null) {
      throw new NullPointerException("disposeAction is null");
    }
    return create(new SingleOnSubscribeUsing(paramFunc0, paramFunc1, paramAction1, paramBoolean));
  }
  
  public static <R> Single<R> zip(Iterable<? extends Single<?>> paramIterable, FuncN<? extends R> paramFuncN)
  {
    return SingleOperatorZip.zip(iterableToArray(paramIterable), paramFuncN);
  }
  
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Single<? extends T4> paramSingle3, Single<? extends T5> paramSingle4, Single<? extends T6> paramSingle5, Single<? extends T7> paramSingle6, Single<? extends T8> paramSingle7, Single<? extends T9> paramSingle8, Func9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> paramFunc9)
  {
    paramFunc9 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2], paramAnonymousVarArgs[3], paramAnonymousVarArgs[4], paramAnonymousVarArgs[5], paramAnonymousVarArgs[6], paramAnonymousVarArgs[7], paramAnonymousVarArgs[8]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2, paramSingle3, paramSingle4, paramSingle5, paramSingle6, paramSingle7, paramSingle8 }, paramFunc9);
  }
  
  public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Single<? extends T4> paramSingle3, Single<? extends T5> paramSingle4, Single<? extends T6> paramSingle5, Single<? extends T7> paramSingle6, Single<? extends T8> paramSingle7, Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> paramFunc8)
  {
    paramFunc8 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2], paramAnonymousVarArgs[3], paramAnonymousVarArgs[4], paramAnonymousVarArgs[5], paramAnonymousVarArgs[6], paramAnonymousVarArgs[7]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2, paramSingle3, paramSingle4, paramSingle5, paramSingle6, paramSingle7 }, paramFunc8);
  }
  
  public static <T1, T2, T3, T4, T5, T6, T7, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Single<? extends T4> paramSingle3, Single<? extends T5> paramSingle4, Single<? extends T6> paramSingle5, Single<? extends T7> paramSingle6, Func7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> paramFunc7)
  {
    paramFunc7 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2], paramAnonymousVarArgs[3], paramAnonymousVarArgs[4], paramAnonymousVarArgs[5], paramAnonymousVarArgs[6]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2, paramSingle3, paramSingle4, paramSingle5, paramSingle6 }, paramFunc7);
  }
  
  public static <T1, T2, T3, T4, T5, T6, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Single<? extends T4> paramSingle3, Single<? extends T5> paramSingle4, Single<? extends T6> paramSingle5, Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> paramFunc6)
  {
    paramFunc6 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2], paramAnonymousVarArgs[3], paramAnonymousVarArgs[4], paramAnonymousVarArgs[5]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2, paramSingle3, paramSingle4, paramSingle5 }, paramFunc6);
  }
  
  public static <T1, T2, T3, T4, T5, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Single<? extends T4> paramSingle3, Single<? extends T5> paramSingle4, Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> paramFunc5)
  {
    paramFunc5 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2], paramAnonymousVarArgs[3], paramAnonymousVarArgs[4]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2, paramSingle3, paramSingle4 }, paramFunc5);
  }
  
  public static <T1, T2, T3, T4, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Single<? extends T4> paramSingle3, Func4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> paramFunc4)
  {
    paramFunc4 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2], paramAnonymousVarArgs[3]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2, paramSingle3 }, paramFunc4);
  }
  
  public static <T1, T2, T3, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Single<? extends T3> paramSingle2, Func3<? super T1, ? super T2, ? super T3, ? extends R> paramFunc3)
  {
    paramFunc3 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1], paramAnonymousVarArgs[2]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1, paramSingle2 }, paramFunc3);
  }
  
  public static <T1, T2, R> Single<R> zip(Single<? extends T1> paramSingle, Single<? extends T2> paramSingle1, Func2<? super T1, ? super T2, ? extends R> paramFunc2)
  {
    paramFunc2 = new FuncN()
    {
      public R call(Object... paramAnonymousVarArgs)
      {
        return (R)this.val$zipFunction.call(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1]);
      }
    };
    return SingleOperatorZip.zip(new Single[] { paramSingle, paramSingle1 }, paramFunc2);
  }
  
  public <R> Single<R> compose(Transformer<? super T, ? extends R> paramTransformer)
  {
    return (Single)paramTransformer.call(this);
  }
  
  public final Observable<T> concatWith(Single<? extends T> paramSingle)
  {
    return concat(this, paramSingle);
  }
  
  @Experimental
  public final Single<T> delay(long paramLong, TimeUnit paramTimeUnit)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @Experimental
  public final Single<T> delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return lift(new OperatorDelay(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @Experimental
  public final Single<T> delaySubscription(Observable<?> paramObservable)
  {
    if (paramObservable == null) {
      throw new NullPointerException();
    }
    return create(new SingleOnSubscribeDelaySubscriptionOther(this, paramObservable));
  }
  
  @Experimental
  public final Single<T> doAfterTerminate(Action0 paramAction0)
  {
    return lift(new OperatorDoAfterTerminate(paramAction0));
  }
  
  @Experimental
  public final Single<T> doOnError(final Action1<Throwable> paramAction1)
  {
    lift(new OperatorDoOnEach(new Observer()
    {
      public void onCompleted() {}
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramAction1.call(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT) {}
    }));
  }
  
  @Experimental
  public final Single<T> doOnSubscribe(Action0 paramAction0)
  {
    return lift(new OperatorDoOnSubscribe(paramAction0));
  }
  
  @Experimental
  public final Single<T> doOnSuccess(final Action1<? super T> paramAction1)
  {
    lift(new OperatorDoOnEach(new Observer()
    {
      public void onCompleted() {}
      
      public void onError(Throwable paramAnonymousThrowable) {}
      
      public void onNext(T paramAnonymousT)
      {
        paramAction1.call(paramAnonymousT);
      }
    }));
  }
  
  @Experimental
  public final Single<T> doOnUnsubscribe(Action0 paramAction0)
  {
    return lift(new OperatorDoOnUnsubscribe(paramAction0));
  }
  
  public final <R> Single<R> flatMap(Func1<? super T, ? extends Single<? extends R>> paramFunc1)
  {
    if ((this instanceof ScalarSynchronousSingle)) {
      return ((ScalarSynchronousSingle)this).scalarFlatMap(paramFunc1);
    }
    return merge(map(paramFunc1));
  }
  
  public final <R> Observable<R> flatMapObservable(Func1<? super T, ? extends Observable<? extends R>> paramFunc1)
  {
    return Observable.merge(asObservable(map(paramFunc1)));
  }
  
  @Experimental
  public final <R> Single<R> lift(final Observable.Operator<? extends R, ? super T> paramOperator)
  {
    new Single(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super R> paramAnonymousSubscriber)
      {
        try
        {
          Subscriber localSubscriber = (Subscriber)Single.hook.onLift(paramOperator).call(paramAnonymousSubscriber);
          try
          {
            localSubscriber.onStart();
            Single.this.onSubscribe.call(localSubscriber);
            return;
          }
          catch (Throwable localThrowable2)
          {
            Exceptions.throwOrReport(localThrowable2, localSubscriber);
            return;
          }
          return;
        }
        catch (Throwable localThrowable1)
        {
          Exceptions.throwOrReport(localThrowable1, paramAnonymousSubscriber);
        }
      }
    });
  }
  
  public final <R> Single<R> map(Func1<? super T, ? extends R> paramFunc1)
  {
    return lift(new OperatorMap(paramFunc1));
  }
  
  public final Observable<T> mergeWith(Single<? extends T> paramSingle)
  {
    return merge(this, paramSingle);
  }
  
  public final Single<T> observeOn(Scheduler paramScheduler)
  {
    if ((this instanceof ScalarSynchronousSingle)) {
      return ((ScalarSynchronousSingle)this).scalarScheduleOn(paramScheduler);
    }
    return lift(new OperatorObserveOn(paramScheduler, false));
  }
  
  @Experimental
  public final Single<T> onErrorResumeNext(Single<? extends T> paramSingle)
  {
    return new Single(SingleOperatorOnErrorResumeNext.withOther(this, paramSingle));
  }
  
  @Experimental
  public final Single<T> onErrorResumeNext(Func1<Throwable, ? extends Single<? extends T>> paramFunc1)
  {
    return new Single(SingleOperatorOnErrorResumeNext.withFunction(this, paramFunc1));
  }
  
  public final Single<T> onErrorReturn(Func1<Throwable, ? extends T> paramFunc1)
  {
    return lift(OperatorOnErrorResumeNextViaFunction.withSingle(paramFunc1));
  }
  
  public final Single<T> retry()
  {
    return toObservable().retry().toSingle();
  }
  
  public final Single<T> retry(long paramLong)
  {
    return toObservable().retry(paramLong).toSingle();
  }
  
  public final Single<T> retry(Func2<Integer, Throwable, Boolean> paramFunc2)
  {
    return toObservable().retry(paramFunc2).toSingle();
  }
  
  public final Single<T> retryWhen(Func1<Observable<? extends Throwable>, ? extends Observable<?>> paramFunc1)
  {
    return toObservable().retryWhen(paramFunc1).toSingle();
  }
  
  public final Subscription subscribe()
  {
    subscribe(new Subscriber()
    {
      public final void onCompleted() {}
      
      public final void onError(Throwable paramAnonymousThrowable)
      {
        throw new OnErrorNotImplementedException(paramAnonymousThrowable);
      }
      
      public final void onNext(T paramAnonymousT) {}
    });
  }
  
  public final Subscription subscribe(final Observer<? super T> paramObserver)
  {
    if (paramObserver == null) {
      throw new NullPointerException("observer is null");
    }
    subscribe(new SingleSubscriber()
    {
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramObserver.onError(paramAnonymousThrowable);
      }
      
      public void onSuccess(T paramAnonymousT)
      {
        paramObserver.onNext(paramAnonymousT);
        paramObserver.onCompleted();
      }
    });
  }
  
  public final Subscription subscribe(final SingleSubscriber<? super T> paramSingleSubscriber)
  {
    Subscriber local18 = new Subscriber()
    {
      public void onCompleted() {}
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSingleSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        paramSingleSubscriber.onSuccess(paramAnonymousT);
      }
    };
    paramSingleSubscriber.add(local18);
    subscribe(local18);
    return local18;
  }
  
  public final Subscription subscribe(Subscriber<? super T> paramSubscriber)
  {
    if (paramSubscriber == null) {
      throw new IllegalArgumentException("observer can not be null");
    }
    if (this.onSubscribe == null) {
      throw new IllegalStateException("onSubscribe function can not be null.");
    }
    paramSubscriber.onStart();
    Object localObject = paramSubscriber;
    if (!(paramSubscriber instanceof SafeSubscriber)) {
      localObject = new SafeSubscriber(paramSubscriber);
    }
    try
    {
      hook.onSubscribeStart(this, this.onSubscribe).call(localObject);
      paramSubscriber = hook.onSubscribeReturn((Subscription)localObject);
      return paramSubscriber;
    }
    catch (Throwable paramSubscriber)
    {
      Exceptions.throwIfFatal(paramSubscriber);
      try
      {
        ((Subscriber)localObject).onError(hook.onSubscribeError(paramSubscriber));
        return Subscriptions.empty();
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwIfFatal(localThrowable);
        paramSubscriber = new RuntimeException("Error occurred attempting to subscribe [" + paramSubscriber.getMessage() + "] and then again while trying to pass to onError.", localThrowable);
        hook.onSubscribeError(paramSubscriber);
        throw paramSubscriber;
      }
    }
  }
  
  public final Subscription subscribe(final Action1<? super T> paramAction1)
  {
    if (paramAction1 == null) {
      throw new IllegalArgumentException("onSuccess can not be null");
    }
    subscribe(new Subscriber()
    {
      public final void onCompleted() {}
      
      public final void onError(Throwable paramAnonymousThrowable)
      {
        throw new OnErrorNotImplementedException(paramAnonymousThrowable);
      }
      
      public final void onNext(T paramAnonymousT)
      {
        paramAction1.call(paramAnonymousT);
      }
    });
  }
  
  public final Subscription subscribe(final Action1<? super T> paramAction1, final Action1<Throwable> paramAction11)
  {
    if (paramAction1 == null) {
      throw new IllegalArgumentException("onSuccess can not be null");
    }
    if (paramAction11 == null) {
      throw new IllegalArgumentException("onError can not be null");
    }
    subscribe(new Subscriber()
    {
      public final void onCompleted() {}
      
      public final void onError(Throwable paramAnonymousThrowable)
      {
        paramAction11.call(paramAnonymousThrowable);
      }
      
      public final void onNext(T paramAnonymousT)
      {
        paramAction1.call(paramAnonymousT);
      }
    });
  }
  
  public final Single<T> subscribeOn(final Scheduler paramScheduler)
  {
    if ((this instanceof ScalarSynchronousSingle)) {
      return ((ScalarSynchronousSingle)this).scalarScheduleOn(paramScheduler);
    }
    create(new OnSubscribe()
    {
      public void call(final SingleSubscriber<? super T> paramAnonymousSingleSubscriber)
      {
        final Scheduler.Worker localWorker = paramScheduler.createWorker();
        paramAnonymousSingleSubscriber.add(localWorker);
        localWorker.schedule(new Action0()
        {
          public void call()
          {
            SingleSubscriber local1 = new SingleSubscriber()
            {
              public void onError(Throwable paramAnonymous3Throwable)
              {
                try
                {
                  Single.19.1.this.val$t.onError(paramAnonymous3Throwable);
                  return;
                }
                finally
                {
                  Single.19.1.this.val$w.unsubscribe();
                }
              }
              
              public void onSuccess(T paramAnonymous3T)
              {
                try
                {
                  Single.19.1.this.val$t.onSuccess(paramAnonymous3T);
                  return;
                }
                finally
                {
                  Single.19.1.this.val$w.unsubscribe();
                }
              }
            };
            paramAnonymousSingleSubscriber.add(local1);
            Single.this.subscribe(local1);
          }
        });
      }
    });
  }
  
  public final Single<T> takeUntil(final Completable paramCompletable)
  {
    lift(new Observable.Operator()
    {
      public Subscriber<? super T> call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramAnonymousSubscriber, false);
        final Subscriber local1 = new Subscriber(localSerializedSubscriber, false)
        {
          public void onCompleted()
          {
            try
            {
              localSerializedSubscriber.onCompleted();
              return;
            }
            finally
            {
              localSerializedSubscriber.unsubscribe();
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              localSerializedSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            finally
            {
              localSerializedSubscriber.unsubscribe();
            }
          }
          
          public void onNext(T paramAnonymous2T)
          {
            localSerializedSubscriber.onNext(paramAnonymous2T);
          }
        };
        Completable.CompletableSubscriber local2 = new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            onError(new CancellationException("Stream was canceled before emitting a terminal event."));
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            local1.onError(paramAnonymous2Throwable);
          }
          
          public void onSubscribe(Subscription paramAnonymous2Subscription)
          {
            localSerializedSubscriber.add(paramAnonymous2Subscription);
          }
        };
        localSerializedSubscriber.add(local1);
        paramAnonymousSubscriber.add(localSerializedSubscriber);
        paramCompletable.subscribe(local2);
        return local1;
      }
    });
  }
  
  public final <E> Single<T> takeUntil(final Observable<? extends E> paramObservable)
  {
    lift(new Observable.Operator()
    {
      public Subscriber<? super T> call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramAnonymousSubscriber, false);
        final Subscriber local1 = new Subscriber(localSerializedSubscriber, false)
        {
          public void onCompleted()
          {
            try
            {
              localSerializedSubscriber.onCompleted();
              return;
            }
            finally
            {
              localSerializedSubscriber.unsubscribe();
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              localSerializedSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            finally
            {
              localSerializedSubscriber.unsubscribe();
            }
          }
          
          public void onNext(T paramAnonymous2T)
          {
            localSerializedSubscriber.onNext(paramAnonymous2T);
          }
        };
        Subscriber local2 = new Subscriber()
        {
          public void onCompleted()
          {
            onError(new CancellationException("Stream was canceled before emitting a terminal event."));
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            local1.onError(paramAnonymous2Throwable);
          }
          
          public void onNext(E paramAnonymous2E)
          {
            onError(new CancellationException("Stream was canceled before emitting a terminal event."));
          }
        };
        localSerializedSubscriber.add(local1);
        localSerializedSubscriber.add(local2);
        paramAnonymousSubscriber.add(localSerializedSubscriber);
        paramObservable.unsafeSubscribe(local2);
        return local1;
      }
    });
  }
  
  public final <E> Single<T> takeUntil(final Single<? extends E> paramSingle)
  {
    lift(new Observable.Operator()
    {
      public Subscriber<? super T> call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramAnonymousSubscriber, false);
        final Subscriber local1 = new Subscriber(localSerializedSubscriber, false)
        {
          public void onCompleted()
          {
            try
            {
              localSerializedSubscriber.onCompleted();
              return;
            }
            finally
            {
              localSerializedSubscriber.unsubscribe();
            }
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            try
            {
              localSerializedSubscriber.onError(paramAnonymous2Throwable);
              return;
            }
            finally
            {
              localSerializedSubscriber.unsubscribe();
            }
          }
          
          public void onNext(T paramAnonymous2T)
          {
            localSerializedSubscriber.onNext(paramAnonymous2T);
          }
        };
        SingleSubscriber local2 = new SingleSubscriber()
        {
          public void onError(Throwable paramAnonymous2Throwable)
          {
            local1.onError(paramAnonymous2Throwable);
          }
          
          public void onSuccess(E paramAnonymous2E)
          {
            onError(new CancellationException("Stream was canceled before emitting a terminal event."));
          }
        };
        localSerializedSubscriber.add(local1);
        localSerializedSubscriber.add(local2);
        paramAnonymousSubscriber.add(localSerializedSubscriber);
        paramSingle.subscribe(local2);
        return local1;
      }
    });
  }
  
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return timeout(paramLong, paramTimeUnit, null, Schedulers.computation());
  }
  
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return timeout(paramLong, paramTimeUnit, null, paramScheduler);
  }
  
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit, Single<? extends T> paramSingle)
  {
    return timeout(paramLong, paramTimeUnit, paramSingle, Schedulers.computation());
  }
  
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit, Single<? extends T> paramSingle, Scheduler paramScheduler)
  {
    Object localObject = paramSingle;
    if (paramSingle == null) {
      localObject = error(new TimeoutException());
    }
    return lift(new OperatorTimeout(paramLong, paramTimeUnit, asObservable((Single)localObject), paramScheduler));
  }
  
  @Experimental
  public final BlockingSingle<T> toBlocking()
  {
    return BlockingSingle.from(this);
  }
  
  public final Observable<T> toObservable()
  {
    return asObservable(this);
  }
  
  public final Subscription unsafeSubscribe(Subscriber<? super T> paramSubscriber)
  {
    try
    {
      paramSubscriber.onStart();
      hook.onSubscribeStart(this, this.onSubscribe).call(paramSubscriber);
      Subscription localSubscription = hook.onSubscribeReturn(paramSubscriber);
      return localSubscription;
    }
    catch (Throwable localThrowable)
    {
      Exceptions.throwIfFatal(localThrowable);
      try
      {
        paramSubscriber.onError(hook.onSubscribeError(localThrowable));
        return Subscriptions.unsubscribed();
      }
      catch (Throwable paramSubscriber)
      {
        Exceptions.throwIfFatal(paramSubscriber);
        paramSubscriber = new RuntimeException("Error occurred attempting to subscribe [" + localThrowable.getMessage() + "] and then again while trying to pass to onError.", paramSubscriber);
        hook.onSubscribeError(paramSubscriber);
        throw paramSubscriber;
      }
    }
  }
  
  public final <T2, R> Single<R> zipWith(Single<? extends T2> paramSingle, Func2<? super T, ? super T2, ? extends R> paramFunc2)
  {
    return zip(this, paramSingle, paramFunc2);
  }
  
  public static abstract interface OnSubscribe<T>
    extends Action1<SingleSubscriber<? super T>>
  {}
  
  public static abstract interface Transformer<T, R>
    extends Func1<Single<T>, Single<R>>
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/Single.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */