package rx.internal.util;

import rx.Subscription;

public class SynchronizedSubscription
  implements Subscription
{
  private final Subscription s;
  
  public SynchronizedSubscription(Subscription paramSubscription)
  {
    this.s = paramSubscription;
  }
  
  public boolean isUnsubscribed()
  {
    try
    {
      boolean bool = this.s.isUnsubscribed();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void unsubscribe()
  {
    try
    {
      this.s.unsubscribe();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/SynchronizedSubscription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */