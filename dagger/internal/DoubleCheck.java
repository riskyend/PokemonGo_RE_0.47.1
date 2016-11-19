package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class DoubleCheck<T>
  implements Provider<T>, Lazy<T>
{
  private static final Object UNINITIALIZED;
  private volatile Object instance = UNINITIALIZED;
  private volatile Provider<T> provider;
  
  static
  {
    if (!DoubleCheck.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      UNINITIALIZED = new Object();
      return;
    }
  }
  
  private DoubleCheck(Provider<T> paramProvider)
  {
    assert (paramProvider != null);
    this.provider = paramProvider;
  }
  
  public static <T> Lazy<T> lazy(Provider<T> paramProvider)
  {
    if ((paramProvider instanceof Lazy)) {
      return (Lazy)paramProvider;
    }
    return new DoubleCheck((Provider)Preconditions.checkNotNull(paramProvider));
  }
  
  public static <T> Provider<T> provider(Provider<T> paramProvider)
  {
    Preconditions.checkNotNull(paramProvider);
    if ((paramProvider instanceof DoubleCheck)) {
      return paramProvider;
    }
    return new DoubleCheck(paramProvider);
  }
  
  public T get()
  {
    Object localObject1 = this.instance;
    if (localObject1 == UNINITIALIZED) {
      try
      {
        Object localObject2 = this.instance;
        localObject1 = localObject2;
        if (localObject2 == UNINITIALIZED)
        {
          localObject1 = this.provider.get();
          this.instance = localObject1;
          this.provider = null;
        }
        return (T)localObject1;
      }
      finally {}
    }
    return ?;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/DoubleCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */