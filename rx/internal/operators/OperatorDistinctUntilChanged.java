package rx.internal.operators;

import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.internal.util.UtilityFunctions;

public final class OperatorDistinctUntilChanged<T, U>
  implements Observable.Operator<T, T>
{
  final Func1<? super T, ? extends U> keySelector;
  
  public OperatorDistinctUntilChanged(Func1<? super T, ? extends U> paramFunc1)
  {
    this.keySelector = paramFunc1;
  }
  
  public static <T> OperatorDistinctUntilChanged<T, T> instance()
  {
    return Holder.INSTANCE;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    new Subscriber(paramSubscriber)
    {
      boolean hasPrevious;
      U previousKey;
      
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
        Object localObject1 = this.previousKey;
        try
        {
          Object localObject2 = OperatorDistinctUntilChanged.this.keySelector.call(paramAnonymousT);
          this.previousKey = localObject2;
          if (!this.hasPrevious) {
            break label74;
          }
          if ((localObject1 != localObject2) && ((localObject2 == null) || (!localObject2.equals(localObject1))))
          {
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
        return;
        label74:
        this.hasPrevious = true;
        paramSubscriber.onNext(paramAnonymousT);
      }
    };
  }
  
  private static class Holder
  {
    static final OperatorDistinctUntilChanged<?, ?> INSTANCE = new OperatorDistinctUntilChanged(UtilityFunctions.identity());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorDistinctUntilChanged.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */