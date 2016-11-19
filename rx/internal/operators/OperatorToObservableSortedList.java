package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func2;
import rx.internal.producers.SingleDelayedProducer;

public final class OperatorToObservableSortedList<T>
  implements Observable.Operator<List<T>, T>
{
  private static Comparator DEFAULT_SORT_FUNCTION = new DefaultComparableFunction();
  final int initialCapacity;
  final Comparator<? super T> sortFunction;
  
  public OperatorToObservableSortedList(int paramInt)
  {
    this.sortFunction = DEFAULT_SORT_FUNCTION;
    this.initialCapacity = paramInt;
  }
  
  public OperatorToObservableSortedList(final Func2<? super T, ? super T, Integer> paramFunc2, int paramInt)
  {
    this.initialCapacity = paramInt;
    this.sortFunction = new Comparator()
    {
      public int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        return ((Integer)paramFunc2.call(paramAnonymousT1, paramAnonymousT2)).intValue();
      }
    };
  }
  
  public Subscriber<? super T> call(final Subscriber<? super List<T>> paramSubscriber)
  {
    final SingleDelayedProducer localSingleDelayedProducer = new SingleDelayedProducer(paramSubscriber);
    Subscriber local2 = new Subscriber()
    {
      boolean completed;
      List<T> list = new ArrayList(OperatorToObservableSortedList.this.initialCapacity);
      
      public void onCompleted()
      {
        List localList;
        if (!this.completed)
        {
          this.completed = true;
          localList = this.list;
          this.list = null;
        }
        try
        {
          Collections.sort(localList, OperatorToObservableSortedList.this.sortFunction);
          localSingleDelayedProducer.setValue(localList);
          return;
        }
        catch (Throwable localThrowable)
        {
          Exceptions.throwOrReport(localThrowable, this);
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (!this.completed) {
          this.list.add(paramAnonymousT);
        }
      }
      
      public void onStart()
      {
        request(Long.MAX_VALUE);
      }
    };
    paramSubscriber.add(local2);
    paramSubscriber.setProducer(localSingleDelayedProducer);
    return local2;
  }
  
  private static class DefaultComparableFunction
    implements Comparator<Object>
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      return ((Comparable)paramObject1).compareTo((Comparable)paramObject2);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorToObservableSortedList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */