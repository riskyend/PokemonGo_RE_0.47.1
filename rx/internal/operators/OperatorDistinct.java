package rx.internal.operators;

import java.util.HashSet;
import java.util.Set;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.functions.Func1;
import rx.internal.util.UtilityFunctions;

public final class OperatorDistinct<T, U>
  implements Observable.Operator<T, T>
{
  final Func1<? super T, ? extends U> keySelector;
  
  public OperatorDistinct(Func1<? super T, ? extends U> paramFunc1)
  {
    this.keySelector = paramFunc1;
  }
  
  public static <T> OperatorDistinct<T, T> instance()
  {
    return Holder.INSTANCE;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    new Subscriber(paramSubscriber)
    {
      Set<U> keyMemory = new HashSet();
      
      public void onCompleted()
      {
        this.keyMemory = null;
        paramSubscriber.onCompleted();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        this.keyMemory = null;
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        Object localObject = OperatorDistinct.this.keySelector.call(paramAnonymousT);
        if (this.keyMemory.add(localObject))
        {
          paramSubscriber.onNext(paramAnonymousT);
          return;
        }
        request(1L);
      }
    };
  }
  
  private static class Holder
  {
    static final OperatorDistinct<?, ?> INSTANCE = new OperatorDistinct(UtilityFunctions.identity());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorDistinct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */