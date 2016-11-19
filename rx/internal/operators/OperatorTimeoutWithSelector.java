package rx.internal.operators;

import rx.Observable;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class OperatorTimeoutWithSelector<T, U, V>
  extends OperatorTimeoutBase<T>
{
  public OperatorTimeoutWithSelector(Func0<? extends Observable<U>> paramFunc0, Func1<? super T, ? extends Observable<V>> paramFunc1, Observable<? extends T> paramObservable)
  {
    super(new OperatorTimeoutBase.FirstTimeoutStub()new OperatorTimeoutBase.TimeoutStub
    {
      public Subscription call(final OperatorTimeoutBase.TimeoutSubscriber<T> paramAnonymousTimeoutSubscriber, final Long paramAnonymousLong, Scheduler.Worker paramAnonymousWorker)
      {
        if (OperatorTimeoutWithSelector.this != null) {
          try
          {
            paramAnonymousWorker = (Observable)OperatorTimeoutWithSelector.this.call();
            paramAnonymousWorker.unsafeSubscribe(new Subscriber()
            {
              public void onCompleted()
              {
                paramAnonymousTimeoutSubscriber.onTimeout(paramAnonymousLong.longValue());
              }
              
              public void onError(Throwable paramAnonymous2Throwable)
              {
                paramAnonymousTimeoutSubscriber.onError(paramAnonymous2Throwable);
              }
              
              public void onNext(U paramAnonymous2U)
              {
                paramAnonymousTimeoutSubscriber.onTimeout(paramAnonymousLong.longValue());
              }
            });
          }
          catch (Throwable paramAnonymousLong)
          {
            Exceptions.throwOrReport(paramAnonymousLong, paramAnonymousTimeoutSubscriber);
            return Subscriptions.unsubscribed();
          }
        }
        return Subscriptions.unsubscribed();
      }
    }, new OperatorTimeoutBase.TimeoutStub()
    {
      public Subscription call(final OperatorTimeoutBase.TimeoutSubscriber<T> paramAnonymousTimeoutSubscriber, final Long paramAnonymousLong, T paramAnonymousT, Scheduler.Worker paramAnonymousWorker)
      {
        try
        {
          paramAnonymousT = (Observable)OperatorTimeoutWithSelector.this.call(paramAnonymousT);
          paramAnonymousT.unsafeSubscribe(new Subscriber()
          {
            public void onCompleted()
            {
              paramAnonymousTimeoutSubscriber.onTimeout(paramAnonymousLong.longValue());
            }
            
            public void onError(Throwable paramAnonymous2Throwable)
            {
              paramAnonymousTimeoutSubscriber.onError(paramAnonymous2Throwable);
            }
            
            public void onNext(V paramAnonymous2V)
            {
              paramAnonymousTimeoutSubscriber.onTimeout(paramAnonymousLong.longValue());
            }
          });
        }
        catch (Throwable paramAnonymousLong)
        {
          Exceptions.throwOrReport(paramAnonymousLong, paramAnonymousTimeoutSubscriber);
        }
        return Subscriptions.unsubscribed();
      }
    }, paramObservable, Schedulers.immediate());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorTimeoutWithSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */