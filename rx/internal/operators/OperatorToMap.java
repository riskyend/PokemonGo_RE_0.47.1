package rx.internal.operators;

import java.util.HashMap;
import java.util.Map;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.Subscribers;

public final class OperatorToMap<T, K, V>
  implements Observable.Operator<Map<K, V>, T>
{
  final Func1<? super T, ? extends K> keySelector;
  private final Func0<? extends Map<K, V>> mapFactory;
  final Func1<? super T, ? extends V> valueSelector;
  
  public OperatorToMap(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11)
  {
    this(paramFunc1, paramFunc11, new DefaultToMapFactory());
  }
  
  public OperatorToMap(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11, Func0<? extends Map<K, V>> paramFunc0)
  {
    this.keySelector = paramFunc1;
    this.valueSelector = paramFunc11;
    this.mapFactory = paramFunc0;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super Map<K, V>> paramSubscriber)
  {
    try
    {
      final Map localMap = (Map)this.mapFactory.call();
      new Subscriber(paramSubscriber)
      {
        private Map<K, V> map = localMap;
        
        public void onCompleted()
        {
          Map localMap = this.map;
          this.map = null;
          paramSubscriber.onNext(localMap);
          paramSubscriber.onCompleted();
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          this.map = null;
          paramSubscriber.onError(paramAnonymousThrowable);
        }
        
        public void onNext(T paramAnonymousT)
        {
          try
          {
            Object localObject = OperatorToMap.this.keySelector.call(paramAnonymousT);
            paramAnonymousT = OperatorToMap.this.valueSelector.call(paramAnonymousT);
            this.map.put(localObject, paramAnonymousT);
            return;
          }
          catch (Throwable paramAnonymousT)
          {
            Exceptions.throwOrReport(paramAnonymousT, paramSubscriber);
          }
        }
        
        public void onStart()
        {
          request(Long.MAX_VALUE);
        }
      };
    }
    catch (Throwable localThrowable)
    {
      Exceptions.throwOrReport(localThrowable, paramSubscriber);
      paramSubscriber = Subscribers.empty();
      paramSubscriber.unsubscribe();
    }
    return paramSubscriber;
  }
  
  public static final class DefaultToMapFactory<K, V>
    implements Func0<Map<K, V>>
  {
    public Map<K, V> call()
    {
      return new HashMap();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorToMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */