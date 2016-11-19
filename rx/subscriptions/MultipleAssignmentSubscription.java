package rx.subscriptions;

import java.util.concurrent.atomic.AtomicReference;
import rx.Subscription;

public final class MultipleAssignmentSubscription
  implements Subscription
{
  final AtomicReference<State> state = new AtomicReference(new State(false, Subscriptions.empty()));
  
  public Subscription get()
  {
    return ((State)this.state.get()).subscription;
  }
  
  public boolean isUnsubscribed()
  {
    return ((State)this.state.get()).isUnsubscribed;
  }
  
  public void set(Subscription paramSubscription)
  {
    if (paramSubscription == null) {
      throw new IllegalArgumentException("Subscription can not be null");
    }
    AtomicReference localAtomicReference = this.state;
    State localState;
    do
    {
      localState = (State)localAtomicReference.get();
      if (localState.isUnsubscribed)
      {
        paramSubscription.unsubscribe();
        return;
      }
    } while (!localAtomicReference.compareAndSet(localState, localState.set(paramSubscription)));
  }
  
  public void unsubscribe()
  {
    AtomicReference localAtomicReference = this.state;
    State localState;
    do
    {
      localState = (State)localAtomicReference.get();
      if (localState.isUnsubscribed) {
        return;
      }
    } while (!localAtomicReference.compareAndSet(localState, localState.unsubscribe()));
    localState.subscription.unsubscribe();
  }
  
  private static final class State
  {
    final boolean isUnsubscribed;
    final Subscription subscription;
    
    State(boolean paramBoolean, Subscription paramSubscription)
    {
      this.isUnsubscribed = paramBoolean;
      this.subscription = paramSubscription;
    }
    
    State set(Subscription paramSubscription)
    {
      return new State(this.isUnsubscribed, paramSubscription);
    }
    
    State unsubscribe()
    {
      return new State(true, this.subscription);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/subscriptions/MultipleAssignmentSubscription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */