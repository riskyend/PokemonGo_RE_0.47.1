package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.Subscribers;

public final class OperatorToMultimap<T, K, V>
  implements Observable.Operator<Map<K, Collection<V>>, T>
{
  final Func1<? super K, ? extends Collection<V>> collectionFactory;
  final Func1<? super T, ? extends K> keySelector;
  private final Func0<? extends Map<K, Collection<V>>> mapFactory;
  final Func1<? super T, ? extends V> valueSelector;
  
  public OperatorToMultimap(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11)
  {
    this(paramFunc1, paramFunc11, new DefaultToMultimapFactory(), new DefaultMultimapCollectionFactory());
  }
  
  public OperatorToMultimap(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11, Func0<? extends Map<K, Collection<V>>> paramFunc0)
  {
    this(paramFunc1, paramFunc11, paramFunc0, new DefaultMultimapCollectionFactory());
  }
  
  public OperatorToMultimap(Func1<? super T, ? extends K> paramFunc1, Func1<? super T, ? extends V> paramFunc11, Func0<? extends Map<K, Collection<V>>> paramFunc0, Func1<? super K, ? extends Collection<V>> paramFunc12)
  {
    this.keySelector = paramFunc1;
    this.valueSelector = paramFunc11;
    this.mapFactory = paramFunc0;
    this.collectionFactory = paramFunc12;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super Map<K, Collection<V>>> paramSubscriber)
  {
    try
    {
      final Map localMap = (Map)this.mapFactory.call();
      new Subscriber(paramSubscriber)
      {
        private Map<K, Collection<V>> map = localMap;
        
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
            Object localObject1 = OperatorToMultimap.this.keySelector.call(paramAnonymousT);
            Object localObject2 = OperatorToMultimap.this.valueSelector.call(paramAnonymousT);
            Collection localCollection = (Collection)this.map.get(localObject1);
            paramAnonymousT = localCollection;
            if (localCollection == null) {}
            return;
          }
          catch (Throwable paramAnonymousT)
          {
            try
            {
              paramAnonymousT = (Collection)OperatorToMultimap.this.collectionFactory.call(localObject1);
              this.map.put(localObject1, paramAnonymousT);
              paramAnonymousT.add(localObject2);
              return;
            }
            catch (Throwable paramAnonymousT)
            {
              Exceptions.throwOrReport(paramAnonymousT, paramSubscriber);
            }
            paramAnonymousT = paramAnonymousT;
            Exceptions.throwOrReport(paramAnonymousT, paramSubscriber);
            return;
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
      Exceptions.throwIfFatal(localThrowable);
      paramSubscriber.onError(localThrowable);
      paramSubscriber = Subscribers.empty();
      paramSubscriber.unsubscribe();
    }
    return paramSubscriber;
  }
  
  public static final class DefaultMultimapCollectionFactory<K, V>
    implements Func1<K, Collection<V>>
  {
    public Collection<V> call(K paramK)
    {
      return new ArrayList();
    }
  }
  
  public static final class DefaultToMultimapFactory<K, V>
    implements Func0<Map<K, Collection<V>>>
  {
    public Map<K, Collection<V>> call()
    {
      return new HashMap();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorToMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */