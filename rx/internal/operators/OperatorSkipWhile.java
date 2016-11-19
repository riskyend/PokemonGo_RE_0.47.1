package rx.internal.operators;

import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.functions.Func2;

public final class OperatorSkipWhile<T>
  implements Observable.Operator<T, T>
{
  final Func2<? super T, Integer, Boolean> predicate;
  
  public OperatorSkipWhile(Func2<? super T, Integer, Boolean> paramFunc2)
  {
    this.predicate = paramFunc2;
  }
  
  public static <T> Func2<T, Integer, Boolean> toPredicate2(Func1<? super T, Boolean> paramFunc1)
  {
    new Func2()
    {
      public Boolean call(T paramAnonymousT, Integer paramAnonymousInteger)
      {
        return (Boolean)this.val$predicate.call(paramAnonymousT);
      }
    };
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    new Subscriber(paramSubscriber)
    {
      int index;
      boolean skipping = true;
      
      public void onCompleted()
      {
        paramSubscriber.onCompleted();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (!this.skipping)
        {
          paramSubscriber.onNext(paramAnonymousT);
          return;
        }
        try
        {
          Func2 localFunc2 = OperatorSkipWhile.this.predicate;
          int i = this.index;
          this.index = (i + 1);
          boolean bool = ((Boolean)localFunc2.call(paramAnonymousT, Integer.valueOf(i))).booleanValue();
          if (!bool)
          {
            this.skipping = false;
            paramSubscriber.onNext(paramAnonymousT);
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwOrReport(localThrowable, paramSubscriber, paramAnonymousT);
          return;
        }
        request(1L);
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSkipWhile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */