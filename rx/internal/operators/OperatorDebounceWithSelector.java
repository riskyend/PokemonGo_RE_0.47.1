package rx.internal.operators;

import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.observers.SerializedSubscriber;
import rx.subscriptions.SerialSubscription;

public final class OperatorDebounceWithSelector<T, U>
  implements Observable.Operator<T, T>
{
  final Func1<? super T, ? extends Observable<U>> selector;
  
  public OperatorDebounceWithSelector(Func1<? super T, ? extends Observable<U>> paramFunc1)
  {
    this.selector = paramFunc1;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final SerializedSubscriber localSerializedSubscriber = new SerializedSubscriber(paramSubscriber);
    final SerialSubscription localSerialSubscription = new SerialSubscription();
    paramSubscriber.add(localSerialSubscription);
    new Subscriber(paramSubscriber)
    {
      final Subscriber<?> self = this;
      final OperatorDebounceWithTime.DebounceState<T> state = new OperatorDebounceWithTime.DebounceState();
      
      public void onCompleted()
      {
        this.state.emitAndComplete(localSerializedSubscriber, this);
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localSerializedSubscriber.onError(paramAnonymousThrowable);
        unsubscribe();
        this.state.clear();
      }
      
      public void onNext(T paramAnonymousT)
      {
        try
        {
          Observable localObservable = (Observable)OperatorDebounceWithSelector.this.selector.call(paramAnonymousT);
          paramAnonymousT = new Subscriber()
          {
            public void onCompleted()
            {
              OperatorDebounceWithSelector.1.this.state.emit(this.val$index, OperatorDebounceWithSelector.1.this.val$s, OperatorDebounceWithSelector.1.this.self);
              unsubscribe();
            }
            
            public void onError(Throwable paramAnonymous2Throwable)
            {
              OperatorDebounceWithSelector.1.this.self.onError(paramAnonymous2Throwable);
            }
            
            public void onNext(U paramAnonymous2U)
            {
              onCompleted();
            }
          };
          localSerialSubscription.set(paramAnonymousT);
          localObservable.unsafeSubscribe(paramAnonymousT);
          return;
        }
        catch (Throwable paramAnonymousT)
        {
          Exceptions.throwOrReport(paramAnonymousT, this);
        }
      }
      
      public void onStart()
      {
        request(Long.MAX_VALUE);
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorDebounceWithSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */