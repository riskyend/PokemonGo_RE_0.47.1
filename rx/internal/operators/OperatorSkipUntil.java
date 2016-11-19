package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

public final class OperatorSkipUntil<T, U>
  implements Observable.Operator<T, T>
{
  final Observable<U> other;
  
  public OperatorSkipUntil(Observable<U> paramObservable)
  {
    this.other = paramObservable;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramSubscriber);
    final AtomicBoolean localAtomicBoolean = new AtomicBoolean();
    Subscriber local1 = new Subscriber()
    {
      public void onCompleted()
      {
        unsubscribe();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localSerializedSubscriber.onError(paramAnonymousThrowable);
        localSerializedSubscriber.unsubscribe();
      }
      
      public void onNext(U paramAnonymousU)
      {
        localAtomicBoolean.set(true);
        unsubscribe();
      }
    };
    paramSubscriber.add(local1);
    this.other.unsafeSubscribe(local1);
    new Subscriber(paramSubscriber)
    {
      public void onCompleted()
      {
        localSerializedSubscriber.onCompleted();
        unsubscribe();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localSerializedSubscriber.onError(paramAnonymousThrowable);
        unsubscribe();
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (localAtomicBoolean.get())
        {
          localSerializedSubscriber.onNext(paramAnonymousT);
          return;
        }
        request(1L);
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSkipUntil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */