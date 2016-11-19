package rx;

import rx.annotations.Beta;
import rx.internal.util.SubscriptionList;

@Beta
public abstract class SingleSubscriber<T>
  implements Subscription
{
  private final SubscriptionList cs = new SubscriptionList();
  
  public final void add(Subscription paramSubscription)
  {
    this.cs.add(paramSubscription);
  }
  
  public final boolean isUnsubscribed()
  {
    return this.cs.isUnsubscribed();
  }
  
  public abstract void onError(Throwable paramThrowable);
  
  public abstract void onSuccess(T paramT);
  
  public final void unsubscribe()
  {
    this.cs.unsubscribe();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/SingleSubscriber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */