package com.upsight.android.managedvariables.type;

import com.upsight.android.UpsightException;
import com.upsight.android.managedvariables.internal.type.ManagedVariable;
import com.upsight.android.persistence.UpsightSubscription;

public abstract class UpsightManagedVariable<T>
  extends ManagedVariable<T>
{
  protected UpsightManagedVariable(String paramString, T paramT1, T paramT2)
  {
    super(paramString, paramT1, paramT2);
  }
  
  public static abstract interface Listener<T>
  {
    public abstract void onFailure(UpsightException paramUpsightException);
    
    public abstract void onSuccess(T paramT);
  }
  
  protected static class NoOpSubscription
    implements UpsightSubscription
  {
    public boolean isSubscribed()
    {
      return false;
    }
    
    public void unsubscribe() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/type/UpsightManagedVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */