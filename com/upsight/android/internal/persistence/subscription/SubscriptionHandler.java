package com.upsight.android.internal.persistence.subscription;

import com.upsight.android.UpsightException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SubscriptionHandler
{
  private final DataStoreEvent.Action mAction;
  private final Method mMethod;
  private final Object mTarget;
  private final String mType;
  
  SubscriptionHandler(Object paramObject, Method paramMethod, DataStoreEvent.Action paramAction, String paramString)
  {
    this.mTarget = paramObject;
    this.mMethod = paramMethod;
    this.mAction = paramAction;
    this.mType = paramString;
  }
  
  public void handle(DataStoreEvent paramDataStoreEvent)
    throws UpsightException
  {
    try
    {
      this.mMethod.invoke(this.mTarget, new Object[] { paramDataStoreEvent.source });
      return;
    }
    catch (InvocationTargetException paramDataStoreEvent)
    {
      throw new UpsightException(paramDataStoreEvent, "Failed to invoke subscription method %s.%s: ", new Object[] { this.mTarget.getClass(), this.mMethod.getName() });
    }
    catch (IllegalAccessException paramDataStoreEvent)
    {
      for (;;) {}
    }
  }
  
  public boolean matches(DataStoreEvent.Action paramAction, String paramString)
  {
    return (this.mAction.equals(paramAction)) && (this.mType.equals(paramString));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/SubscriptionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */