package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class SingleCheck<T>
  implements Provider<T>, Lazy<T>
{
  private static final Object UNINITIALIZED;
  private volatile Factory<T> factory;
  private volatile Object instance = UNINITIALIZED;
  
  static
  {
    if (!SingleCheck.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      UNINITIALIZED = new Object();
      return;
    }
  }
  
  private SingleCheck(Factory<T> paramFactory)
  {
    assert (paramFactory != null);
    this.factory = paramFactory;
  }
  
  public static <T> Provider<T> provider(Factory<T> paramFactory)
  {
    return new SingleCheck((Factory)Preconditions.checkNotNull(paramFactory));
  }
  
  public T get()
  {
    Factory localFactory = this.factory;
    if (this.instance == UNINITIALIZED)
    {
      this.instance = localFactory.get();
      this.factory = null;
    }
    return (T)this.instance;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/SingleCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */