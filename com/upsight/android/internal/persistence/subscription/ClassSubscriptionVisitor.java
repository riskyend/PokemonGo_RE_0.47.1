package com.upsight.android.internal.persistence.subscription;

import java.lang.reflect.Method;

abstract interface ClassSubscriptionVisitor
{
  public abstract void visitCreatedSubscription(Method paramMethod, Class<?> paramClass);
  
  public abstract void visitRemovedSubscription(Method paramMethod, Class<?> paramClass);
  
  public abstract void visitUpdatedSubscription(Method paramMethod, Class<?> paramClass);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/ClassSubscriptionVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */