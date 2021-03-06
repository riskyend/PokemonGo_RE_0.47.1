package rx.internal.util;

import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;
import rx.functions.Func5;
import rx.functions.Func6;
import rx.functions.Func7;
import rx.functions.Func8;
import rx.functions.Func9;
import rx.functions.FuncN;

public final class UtilityFunctions
{
  private static final NullFunction NULL_FUNCTION = new NullFunction();
  
  public static <T> Func1<? super T, Boolean> alwaysFalse()
  {
    return AlwaysFalse.INSTANCE;
  }
  
  public static <T> Func1<? super T, Boolean> alwaysTrue()
  {
    return AlwaysTrue.INSTANCE;
  }
  
  public static <T> Func1<T, T> identity()
  {
    new Func1()
    {
      public T call(T paramAnonymousT)
      {
        return paramAnonymousT;
      }
    };
  }
  
  public static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, R> NullFunction<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, R> returnNull()
  {
    return NULL_FUNCTION;
  }
  
  private static enum AlwaysFalse
    implements Func1<Object, Boolean>
  {
    INSTANCE;
    
    private AlwaysFalse() {}
    
    public Boolean call(Object paramObject)
    {
      return Boolean.valueOf(false);
    }
  }
  
  private static enum AlwaysTrue
    implements Func1<Object, Boolean>
  {
    INSTANCE;
    
    private AlwaysTrue() {}
    
    public Boolean call(Object paramObject)
    {
      return Boolean.valueOf(true);
    }
  }
  
  private static final class NullFunction<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, R>
    implements Func0<R>, Func1<T0, R>, Func2<T0, T1, R>, Func3<T0, T1, T2, R>, Func4<T0, T1, T2, T3, R>, Func5<T0, T1, T2, T3, T4, R>, Func6<T0, T1, T2, T3, T4, T5, R>, Func7<T0, T1, T2, T3, T4, T5, T6, R>, Func8<T0, T1, T2, T3, T4, T5, T6, T7, R>, Func9<T0, T1, T2, T3, T4, T5, T6, T7, T8, R>, FuncN<R>
  {
    public R call()
    {
      return null;
    }
    
    public R call(T0 paramT0)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2, T3 paramT3)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7)
    {
      return null;
    }
    
    public R call(T0 paramT0, T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8)
    {
      return null;
    }
    
    public R call(Object... paramVarArgs)
    {
      return null;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/UtilityFunctions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */