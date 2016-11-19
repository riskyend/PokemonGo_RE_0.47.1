package rx.subscriptions;

import java.util.concurrent.Future;
import rx.Subscription;
import rx.functions.Action0;

public final class Subscriptions
{
  private static final Unsubscribed UNSUBSCRIBED = new Unsubscribed();
  
  private Subscriptions()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static Subscription create(Action0 paramAction0)
  {
    return BooleanSubscription.create(paramAction0);
  }
  
  public static Subscription empty()
  {
    return BooleanSubscription.create();
  }
  
  public static Subscription from(Future<?> paramFuture)
  {
    return new FutureSubscription(paramFuture);
  }
  
  public static CompositeSubscription from(Subscription... paramVarArgs)
  {
    return new CompositeSubscription(paramVarArgs);
  }
  
  public static Subscription unsubscribed()
  {
    return UNSUBSCRIBED;
  }
  
  private static final class FutureSubscription
    implements Subscription
  {
    final Future<?> f;
    
    public FutureSubscription(Future<?> paramFuture)
    {
      this.f = paramFuture;
    }
    
    public boolean isUnsubscribed()
    {
      return this.f.isCancelled();
    }
    
    public void unsubscribe()
    {
      this.f.cancel(true);
    }
  }
  
  static final class Unsubscribed
    implements Subscription
  {
    public boolean isUnsubscribed()
    {
      return true;
    }
    
    public void unsubscribe() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/subscriptions/Subscriptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */