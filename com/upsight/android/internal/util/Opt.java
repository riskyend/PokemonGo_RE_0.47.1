package com.upsight.android.internal.util;

public class Opt<T>
{
  private T mObject;
  
  private Opt(T paramT)
  {
    this.mObject = paramT;
  }
  
  public static <T> Opt<T> absent()
  {
    return new Opt(null);
  }
  
  public static <T> Opt<T> from(T paramT)
  {
    return new Opt(paramT);
  }
  
  public T get()
  {
    return (T)this.mObject;
  }
  
  public boolean isPresent()
  {
    return this.mObject != null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/util/Opt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */