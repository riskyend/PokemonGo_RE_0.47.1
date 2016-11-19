package rx.internal.operators;

import java.util.Iterator;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func2;
import rx.observers.Subscribers;

public final class OperatorZipIterable<T1, T2, R>
  implements Observable.Operator<R, T1>
{
  final Iterable<? extends T2> iterable;
  final Func2<? super T1, ? super T2, ? extends R> zipFunction;
  
  public OperatorZipIterable(Iterable<? extends T2> paramIterable, Func2<? super T1, ? super T2, ? extends R> paramFunc2)
  {
    this.iterable = paramIterable;
    this.zipFunction = paramFunc2;
  }
  
  public Subscriber<? super T1> call(final Subscriber<? super R> paramSubscriber)
  {
    Object localObject = this.iterable.iterator();
    try
    {
      if (!((Iterator)localObject).hasNext())
      {
        paramSubscriber.onCompleted();
        localObject = Subscribers.empty();
        return (Subscriber<? super T1>)localObject;
      }
    }
    catch (Throwable localThrowable)
    {
      Exceptions.throwOrReport(localThrowable, paramSubscriber);
      return Subscribers.empty();
    }
    new Subscriber(paramSubscriber)
    {
      boolean done;
      
      public void onCompleted()
      {
        if (this.done) {
          return;
        }
        this.done = true;
        paramSubscriber.onCompleted();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        if (this.done)
        {
          Exceptions.throwIfFatal(paramAnonymousThrowable);
          return;
        }
        this.done = true;
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T1 paramAnonymousT1)
      {
        if (this.done) {}
        for (;;)
        {
          return;
          try
          {
            paramSubscriber.onNext(OperatorZipIterable.this.zipFunction.call(paramAnonymousT1, localThrowable.next()));
            if (!localThrowable.hasNext())
            {
              onCompleted();
              return;
            }
          }
          catch (Throwable paramAnonymousT1)
          {
            Exceptions.throwOrReport(paramAnonymousT1, this);
          }
        }
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorZipIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */