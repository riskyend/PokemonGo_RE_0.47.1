package rx.internal.operators;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.util.UtilityFunctions;

public final class OperatorSequenceEqual
{
  static final Object LOCAL_ONCOMPLETED = new Object();
  
  private OperatorSequenceEqual()
  {
    throw new IllegalStateException("No instances!");
  }
  
  static <T> Observable<Object> materializeLite(Observable<T> paramObservable)
  {
    Observable.concat(paramObservable.map(new Func1()
    {
      public Object call(T paramAnonymousT)
      {
        return paramAnonymousT;
      }
    }), Observable.just(LOCAL_ONCOMPLETED));
  }
  
  public static <T> Observable<Boolean> sequenceEqual(Observable<? extends T> paramObservable1, Observable<? extends T> paramObservable2, Func2<? super T, ? super T, Boolean> paramFunc2)
  {
    Observable.zip(materializeLite(paramObservable1), materializeLite(paramObservable2), new Func2()
    {
      public Boolean call(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i;
        if (paramAnonymousObject1 == OperatorSequenceEqual.LOCAL_ONCOMPLETED)
        {
          i = 1;
          if (paramAnonymousObject2 != OperatorSequenceEqual.LOCAL_ONCOMPLETED) {
            break label38;
          }
        }
        label38:
        for (int j = 1;; j = 0)
        {
          if ((i == 0) || (j == 0)) {
            break label44;
          }
          return Boolean.valueOf(true);
          i = 0;
          break;
        }
        label44:
        if ((i != 0) || (j != 0)) {
          return Boolean.valueOf(false);
        }
        return (Boolean)this.val$equality.call(paramAnonymousObject1, paramAnonymousObject2);
      }
    }).all(UtilityFunctions.identity());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSequenceEqual.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */