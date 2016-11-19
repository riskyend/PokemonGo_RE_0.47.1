package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class ProviderOfLazy<T>
  implements Provider<Lazy<T>>
{
  private final Provider<T> provider;
  
  static
  {
    if (!ProviderOfLazy.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private ProviderOfLazy(Provider<T> paramProvider)
  {
    assert (paramProvider != null);
    this.provider = paramProvider;
  }
  
  public static <T> Provider<Lazy<T>> create(Provider<T> paramProvider)
  {
    return new ProviderOfLazy((Provider)Preconditions.checkNotNull(paramProvider));
  }
  
  public Lazy<T> get()
  {
    return DoubleCheck.lazy(this.provider);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/ProviderOfLazy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */