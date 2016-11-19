package com.upsight.android.internal.persistence.subscription;

import com.upsight.android.persistence.annotation.UpsightStorableType;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

class SubscriptionHandlerVisitor
  implements ClassSubscriptionVisitor
{
  private final Set<SubscriptionHandler> mHandlers = new HashSet();
  private final Object mTarget;
  
  SubscriptionHandlerVisitor(Object paramObject)
  {
    this.mTarget = paramObject;
  }
  
  public Set<SubscriptionHandler> getHandlers()
  {
    return new HashSet(this.mHandlers);
  }
  
  public void visitCreatedSubscription(Method paramMethod, Class<?> paramClass)
  {
    paramClass = (UpsightStorableType)paramClass.getAnnotation(UpsightStorableType.class);
    if (paramClass != null) {
      this.mHandlers.add(new SubscriptionHandler(this.mTarget, paramMethod, DataStoreEvent.Action.Created, paramClass.value()));
    }
  }
  
  public void visitRemovedSubscription(Method paramMethod, Class<?> paramClass)
  {
    paramClass = (UpsightStorableType)paramClass.getAnnotation(UpsightStorableType.class);
    if (paramClass != null) {
      this.mHandlers.add(new SubscriptionHandler(this.mTarget, paramMethod, DataStoreEvent.Action.Removed, paramClass.value()));
    }
  }
  
  public void visitUpdatedSubscription(Method paramMethod, Class<?> paramClass)
  {
    paramClass = (UpsightStorableType)paramClass.getAnnotation(UpsightStorableType.class);
    if (paramClass != null) {
      this.mHandlers.add(new SubscriptionHandler(this.mTarget, paramMethod, DataStoreEvent.Action.Updated, paramClass.value()));
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/SubscriptionHandlerVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */