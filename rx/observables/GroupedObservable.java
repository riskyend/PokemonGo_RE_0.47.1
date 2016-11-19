package rx.observables;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class GroupedObservable<K, T>
  extends Observable<T>
{
  private final K key;
  
  protected GroupedObservable(K paramK, Observable.OnSubscribe<T> paramOnSubscribe)
  {
    super(paramOnSubscribe);
    this.key = paramK;
  }
  
  public static <K, T> GroupedObservable<K, T> create(K paramK, Observable.OnSubscribe<T> paramOnSubscribe)
  {
    return new GroupedObservable(paramK, paramOnSubscribe);
  }
  
  public static <K, T> GroupedObservable<K, T> from(K paramK, Observable<T> paramObservable)
  {
    new GroupedObservable(paramK, new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        this.val$o.unsafeSubscribe(paramAnonymousSubscriber);
      }
    });
  }
  
  public K getKey()
  {
    return (K)this.key;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/observables/GroupedObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */