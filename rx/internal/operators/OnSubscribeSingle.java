package rx.internal.operators;

import java.util.NoSuchElementException;
import rx.Observable;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.Subscriber;

public class OnSubscribeSingle<T>
  implements Single.OnSubscribe<T>
{
  private final Observable<T> observable;
  
  public OnSubscribeSingle(Observable<T> paramObservable)
  {
    this.observable = paramObservable;
  }
  
  public static <T> OnSubscribeSingle<T> create(Observable<T> paramObservable)
  {
    return new OnSubscribeSingle(paramObservable);
  }
  
  public void call(final SingleSubscriber<? super T> paramSingleSubscriber)
  {
    Subscriber local1 = new Subscriber()
    {
      private T emission = null;
      private boolean emittedTooMany = false;
      private boolean itemEmitted = false;
      
      public void onCompleted()
      {
        if (this.emittedTooMany) {
          return;
        }
        if (this.itemEmitted)
        {
          paramSingleSubscriber.onSuccess(this.emission);
          return;
        }
        paramSingleSubscriber.onError(new NoSuchElementException("Observable emitted no items"));
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSingleSubscriber.onError(paramAnonymousThrowable);
        unsubscribe();
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (this.itemEmitted)
        {
          this.emittedTooMany = true;
          paramSingleSubscriber.onError(new IllegalArgumentException("Observable emitted too many elements"));
          unsubscribe();
          return;
        }
        this.itemEmitted = true;
        this.emission = paramAnonymousT;
      }
      
      public void onStart()
      {
        request(2L);
      }
    };
    paramSingleSubscriber.add(local1);
    this.observable.unsafeSubscribe(local1);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OnSubscribeSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */