package com.upsight.android.internal.persistence.subscription;

import com.upsight.android.persistence.annotation.Created;
import com.upsight.android.persistence.annotation.Removed;
import com.upsight.android.persistence.annotation.Updated;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class ClassSubscriptionReader
{
  private final Class<?> mClass;
  
  ClassSubscriptionReader(Class<?> paramClass)
  {
    this.mClass = paramClass;
  }
  
  private boolean isSubscriptionMethod(Method paramMethod)
  {
    if (!paramMethod.getReturnType().equals(Void.TYPE)) {}
    Class[] arrayOfClass;
    do
    {
      return false;
      arrayOfClass = paramMethod.getParameterTypes();
    } while ((arrayOfClass.length != 1) || ((UpsightStorableType)arrayOfClass[0].getAnnotation(UpsightStorableType.class) == null) || (!Modifier.isPublic(paramMethod.getModifiers())));
    return true;
  }
  
  public void accept(ClassSubscriptionVisitor paramClassSubscriptionVisitor)
  {
    Method[] arrayOfMethod = this.mClass.getMethods();
    int j = arrayOfMethod.length;
    int i = 0;
    if (i < j)
    {
      Method localMethod = arrayOfMethod[i];
      if (!isSubscriptionMethod(localMethod)) {}
      for (;;)
      {
        i += 1;
        break;
        Class localClass = localMethod.getParameterTypes()[0];
        if ((Created)localMethod.getAnnotation(Created.class) != null) {
          paramClassSubscriptionVisitor.visitCreatedSubscription(localMethod, localClass);
        }
        if ((Updated)localMethod.getAnnotation(Updated.class) != null) {
          paramClassSubscriptionVisitor.visitUpdatedSubscription(localMethod, localClass);
        }
        if ((Removed)localMethod.getAnnotation(Removed.class) != null) {
          paramClassSubscriptionVisitor.visitRemovedSubscription(localMethod, localClass);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/ClassSubscriptionReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */