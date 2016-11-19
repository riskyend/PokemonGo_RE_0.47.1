package rx.internal.operators;

import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.observers.SerializedSubscriber;
import rx.observers.Subscribers;
import rx.subjects.PublishSubject;

public final class OperatorDelayWithSelector<T, V>
  implements Observable.Operator<T, T>
{
  final Func1<? super T, ? extends Observable<V>> itemDelay;
  final Observable<? extends T> source;
  
  public OperatorDelayWithSelector(Observable<? extends T> paramObservable, Func1<? super T, ? extends Observable<V>> paramFunc1)
  {
    this.source = paramObservable;
    this.itemDelay = paramFunc1;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramSubscriber);
    final PublishSubject localPublishSubject = PublishSubject.create();
    paramSubscriber.add(Observable.merge(localPublishSubject).unsafeSubscribe(Subscribers.from(localSerializedSubscriber)));
    new Subscriber(paramSubscriber)
    {
      public void onCompleted()
      {
        localPublishSubject.onCompleted();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localSerializedSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(final T paramAnonymousT)
      {
        try
        {
          localPublishSubject.onNext(((Observable)OperatorDelayWithSelector.this.itemDelay.call(paramAnonymousT)).take(1).defaultIfEmpty(null).map(new Func1()
          {
            public T call(V paramAnonymous2V)
            {
              return (T)paramAnonymousT;
            }
          }));
          return;
        }
        catch (Throwable paramAnonymousT)
        {
          Exceptions.throwOrReport(paramAnonymousT, this);
        }
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorDelayWithSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */