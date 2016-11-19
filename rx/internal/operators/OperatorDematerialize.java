package rx.internal.operators;

import rx.Notification;
import rx.Observable.Operator;
import rx.Subscriber;

public final class OperatorDematerialize<T>
  implements Observable.Operator<T, Notification<T>>
{
  public static OperatorDematerialize instance()
  {
    return Holder.INSTANCE;
  }
  
  public Subscriber<? super Notification<T>> call(final Subscriber<? super T> paramSubscriber)
  {
    new Subscriber(paramSubscriber)
    {
      boolean terminated;
      
      public void onCompleted()
      {
        if (!this.terminated)
        {
          this.terminated = true;
          paramSubscriber.onCompleted();
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        if (!this.terminated)
        {
          this.terminated = true;
          paramSubscriber.onError(paramAnonymousThrowable);
        }
      }
      
      public void onNext(Notification<T> paramAnonymousNotification)
      {
        switch (OperatorDematerialize.2.$SwitchMap$rx$Notification$Kind[paramAnonymousNotification.getKind().ordinal()])
        {
        default: 
        case 1: 
          do
          {
            return;
          } while (this.terminated);
          paramSubscriber.onNext(paramAnonymousNotification.getValue());
          return;
        case 2: 
          onError(paramAnonymousNotification.getThrowable());
          return;
        }
        onCompleted();
      }
    };
  }
  
  private static final class Holder
  {
    static final OperatorDematerialize<Object> INSTANCE = new OperatorDematerialize();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorDematerialize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */