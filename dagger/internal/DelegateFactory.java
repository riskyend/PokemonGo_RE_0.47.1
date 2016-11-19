package dagger.internal;

import javax.inject.Provider;

public final class DelegateFactory<T>
  implements Factory<T>
{
  private Provider<T> delegate;
  
  public T get()
  {
    if (this.delegate == null) {
      throw new IllegalStateException();
    }
    return (T)this.delegate.get();
  }
  
  public void setDelegatedProvider(Provider<T> paramProvider)
  {
    if (paramProvider == null) {
      throw new IllegalArgumentException();
    }
    if (this.delegate != null) {
      throw new IllegalStateException();
    }
    this.delegate = paramProvider;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/DelegateFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */