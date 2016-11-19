package rx.internal.operators;

import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Func2;
import rx.observers.SerializedSubscriber;

public final class OperatorWithLatestFrom<T, U, R>
  implements Observable.Operator<R, T>
{
  static final Object EMPTY = new Object();
  final Observable<? extends U> other;
  final Func2<? super T, ? super U, ? extends R> resultSelector;
  
  public OperatorWithLatestFrom(Observable<? extends U> paramObservable, Func2<? super T, ? super U, ? extends R> paramFunc2)
  {
    this.other = paramObservable;
    this.resultSelector = paramFunc2;
  }
  
  public Subscriber<? super T> call(Subscriber<? super R> paramSubscriber)
  {
    final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramSubscriber, false);
    paramSubscriber.add(localSerializedSubscriber);
    final Object localObject = new AtomicReference(EMPTY);
    paramSubscriber = new Subscriber(localSerializedSubscriber, true)
    {
      public void onCompleted()
      {
        localSerializedSubscriber.onCompleted();
        localSerializedSubscriber.unsubscribe();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localSerializedSubscriber.onError(paramAnonymousThrowable);
        localSerializedSubscriber.unsubscribe();
      }
      
      public void onNext(T paramAnonymousT)
      {
        Object localObject = localObject.get();
        if (localObject != OperatorWithLatestFrom.EMPTY) {}
        try
        {
          paramAnonymousT = OperatorWithLatestFrom.this.resultSelector.call(paramAnonymousT, localObject);
          localSerializedSubscriber.onNext(paramAnonymousT);
          return;
        }
        catch (Throwable paramAnonymousT)
        {
          Exceptions.throwOrReport(paramAnonymousT, this);
        }
      }
    };
    localObject = new Subscriber()
    {
      public void onCompleted()
      {
        if (localObject.get() == OperatorWithLatestFrom.EMPTY)
        {
          localSerializedSubscriber.onCompleted();
          localSerializedSubscriber.unsubscribe();
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localSerializedSubscriber.onError(paramAnonymousThrowable);
        localSerializedSubscriber.unsubscribe();
      }
      
      public void onNext(U paramAnonymousU)
      {
        localObject.set(paramAnonymousU);
      }
    };
    localSerializedSubscriber.add(paramSubscriber);
    localSerializedSubscriber.add((Subscription)localObject);
    this.other.unsafeSubscribe((Subscriber)localObject);
    return paramSubscriber;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorWithLatestFrom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */