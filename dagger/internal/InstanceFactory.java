package dagger.internal;

public final class InstanceFactory<T>
  implements Factory<T>
{
  private final T instance;
  
  private InstanceFactory(T paramT)
  {
    this.instance = paramT;
  }
  
  public static <T> Factory<T> create(T paramT)
  {
    if (paramT == null) {
      throw new NullPointerException();
    }
    return new InstanceFactory(paramT);
  }
  
  public T get()
  {
    return (T)this.instance;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/InstanceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */