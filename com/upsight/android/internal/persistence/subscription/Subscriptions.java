package com.upsight.android.internal.persistence.subscription;

import com.squareup.otto.Bus;
import com.upsight.android.persistence.UpsightSubscription;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class Subscriptions
{
  public static AnnotatedSubscriber create(Object paramObject)
  {
    SubscriptionHandlerVisitor localSubscriptionHandlerVisitor = new SubscriptionHandlerVisitor(paramObject);
    new ClassSubscriptionReader(paramObject.getClass()).accept(localSubscriptionHandlerVisitor);
    return new AnnotatedSubscriber(localSubscriptionHandlerVisitor.getHandlers());
  }
  
  public static UpsightSubscription from(Subscription paramSubscription)
  {
    return new SubscriptionAdapter(paramSubscription);
  }
  
  public static <T> Action1<T> publishCreated(Bus paramBus, final String paramString)
  {
    new Action1()
    {
      public void call(T paramAnonymousT)
      {
        this.val$bus.post(new DataStoreEvent(DataStoreEvent.Action.Created, paramString, paramAnonymousT));
      }
    };
  }
  
  public static <T> Action1<T> publishRemoved(Bus paramBus, final String paramString)
  {
    new Action1()
    {
      public void call(T paramAnonymousT)
      {
        this.val$bus.post(new DataStoreEvent(DataStoreEvent.Action.Removed, paramString, paramAnonymousT));
      }
    };
  }
  
  public static <T> Action1<T> publishUpdated(Bus paramBus, final String paramString)
  {
    new Action1()
    {
      public void call(T paramAnonymousT)
      {
        this.val$bus.post(new DataStoreEvent(DataStoreEvent.Action.Updated, paramString, paramAnonymousT));
      }
    };
  }
  
  public static Observable<DataStoreEvent> toObservable(Bus paramBus)
  {
    return Observable.create(new OnSubscribeBus(paramBus)).onBackpressureBuffer();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/Subscriptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */