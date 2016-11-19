package com.upsight.android.internal.persistence.subscription;

import android.util.Log;
import com.upsight.android.UpsightException;
import java.util.Iterator;
import java.util.Set;
import rx.Subscriber;

class AnnotatedSubscriber
  extends Subscriber<DataStoreEvent>
{
  private final Set<SubscriptionHandler> mHandlers;
  
  AnnotatedSubscriber(Set<SubscriptionHandler> paramSet)
  {
    this.mHandlers = paramSet;
  }
  
  public void onCompleted() {}
  
  public void onError(Throwable paramThrowable) {}
  
  public void onNext(DataStoreEvent paramDataStoreEvent)
  {
    Iterator localIterator = this.mHandlers.iterator();
    while (localIterator.hasNext())
    {
      SubscriptionHandler localSubscriptionHandler = (SubscriptionHandler)localIterator.next();
      if (localSubscriptionHandler.matches(paramDataStoreEvent.action, paramDataStoreEvent.sourceType)) {
        try
        {
          localSubscriptionHandler.handle(paramDataStoreEvent);
        }
        catch (UpsightException localUpsightException)
        {
          Log.e("Upsight", "Failed to handle subscription.", localUpsightException);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/AnnotatedSubscriber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */