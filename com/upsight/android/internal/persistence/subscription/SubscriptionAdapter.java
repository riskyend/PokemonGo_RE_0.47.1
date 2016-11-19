package com.upsight.android.internal.persistence.subscription;

import com.upsight.android.persistence.UpsightSubscription;
import rx.Subscription;

class SubscriptionAdapter
  implements UpsightSubscription
{
  private final Subscription mRxSubscription;
  
  SubscriptionAdapter(Subscription paramSubscription)
  {
    this.mRxSubscription = paramSubscription;
  }
  
  public boolean isSubscribed()
  {
    return !this.mRxSubscription.isUnsubscribed();
  }
  
  public void unsubscribe()
  {
    this.mRxSubscription.unsubscribe();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/subscription/SubscriptionAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */