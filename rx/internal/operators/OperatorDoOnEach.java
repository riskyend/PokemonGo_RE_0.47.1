package rx.internal.operators;

import java.util.Arrays;
import rx.Observable.Operator;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;

public class OperatorDoOnEach<T>
  implements Observable.Operator<T, T>
{
  final Observer<? super T> doOnEachObserver;
  
  public OperatorDoOnEach(Observer<? super T> paramObserver)
  {
    this.doOnEachObserver = paramObserver;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    new Subscriber(paramSubscriber)
    {
      private boolean done = false;
      
      public void onCompleted()
      {
        if (this.done) {
          return;
        }
        try
        {
          OperatorDoOnEach.this.doOnEachObserver.onCompleted();
          this.done = true;
          paramSubscriber.onCompleted();
          return;
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwOrReport(localThrowable, this);
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        Exceptions.throwIfFatal(paramAnonymousThrowable);
        if (this.done) {
          return;
        }
        this.done = true;
        try
        {
          OperatorDoOnEach.this.doOnEachObserver.onError(paramAnonymousThrowable);
          paramSubscriber.onError(paramAnonymousThrowable);
          return;
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwIfFatal(localThrowable);
          paramSubscriber.onError(new CompositeException(Arrays.asList(new Throwable[] { paramAnonymousThrowable, localThrowable })));
        }
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (this.done) {
          return;
        }
        try
        {
          OperatorDoOnEach.this.doOnEachObserver.onNext(paramAnonymousT);
          paramSubscriber.onNext(paramAnonymousT);
          return;
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwOrReport(localThrowable, this, paramAnonymousT);
        }
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorDoOnEach.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */