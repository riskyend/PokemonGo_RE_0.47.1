package rx.internal.operators;

import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.SerializedSubscriber;

public final class OperatorSampleWithObservable<T, U>
  implements Observable.Operator<T, T>
{
  static final Object EMPTY_TOKEN = new Object();
  final Observable<U> sampler;
  
  public OperatorSampleWithObservable(Observable<U> paramObservable)
  {
    this.sampler = paramObservable;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final Object localObject = new SerializedSubscriber(paramSubscriber);
    final AtomicReference localAtomicReference2 = new AtomicReference(EMPTY_TOKEN);
    final AtomicReference localAtomicReference1 = new AtomicReference();
    final Subscriber local1 = new Subscriber()
    {
      public void onCompleted()
      {
        onNext(null);
        localObject.onCompleted();
        ((Subscription)localAtomicReference1.get()).unsubscribe();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localObject.onError(paramAnonymousThrowable);
        ((Subscription)localAtomicReference1.get()).unsubscribe();
      }
      
      public void onNext(U paramAnonymousU)
      {
        paramAnonymousU = localAtomicReference2.getAndSet(OperatorSampleWithObservable.EMPTY_TOKEN);
        if (paramAnonymousU != OperatorSampleWithObservable.EMPTY_TOKEN) {
          localObject.onNext(paramAnonymousU);
        }
      }
    };
    localObject = new Subscriber()
    {
      public void onCompleted()
      {
        local1.onNext(null);
        localObject.onCompleted();
        local1.unsubscribe();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localObject.onError(paramAnonymousThrowable);
        local1.unsubscribe();
      }
      
      public void onNext(T paramAnonymousT)
      {
        localAtomicReference2.set(paramAnonymousT);
      }
    };
    localAtomicReference1.lazySet(localObject);
    paramSubscriber.add((Subscription)localObject);
    paramSubscriber.add(local1);
    this.sampler.unsafeSubscribe(local1);
    return (Subscriber<? super T>)localObject;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSampleWithObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */