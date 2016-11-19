package com.upsight.android.internal.persistence.storable;

import com.upsight.android.UpsightException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class StorableMethodTypeAccessor<T>
  implements StorableTypeAccessor<T>
{
  private final Method mMethod;
  
  public StorableMethodTypeAccessor(Method paramMethod)
  {
    this.mMethod = paramMethod;
  }
  
  public String getType()
    throws UpsightException
  {
    return null;
  }
  
  public String getType(T paramT)
    throws UpsightException
  {
    try
    {
      paramT = (String)this.mMethod.invoke(paramT, new Object[0]);
      return paramT;
    }
    catch (InvocationTargetException paramT)
    {
      throw new UpsightException(paramT);
    }
    catch (IllegalAccessException paramT)
    {
      for (;;) {}
    }
  }
  
  public boolean isDynamic()
  {
    return true;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/StorableMethodTypeAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */