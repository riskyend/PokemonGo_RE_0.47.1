package rx.internal.operators;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;

public final class OperatorSingle<T>
  implements Observable.Operator<T, T>
{
  private final T defaultValue;
  private final boolean hasDefaultValue;
  
  OperatorSingle()
  {
    this(false, null);
  }
  
  public OperatorSingle(T paramT)
  {
    this(true, paramT);
  }
  
  private OperatorSingle(boolean paramBoolean, T paramT)
  {
    this.hasDefaultValue = paramBoolean;
    this.defaultValue = paramT;
  }
  
  public static <T> OperatorSingle<T> instance()
  {
    return Holder.INSTANCE;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final ParentSubscriber localParentSubscriber = new ParentSubscriber(paramSubscriber, this.hasDefaultValue, this.defaultValue);
    paramSubscriber.setProducer(new Producer()
    {
      private final AtomicBoolean requestedTwo = new AtomicBoolean(false);
      
      public void request(long paramAnonymousLong)
      {
        if ((paramAnonymousLong > 0L) && (this.requestedTwo.compareAndSet(false, true))) {
          localParentSubscriber.requestMore(2L);
        }
      }
    });
    paramSubscriber.add(localParentSubscriber);
    return localParentSubscriber;
  }
  
  private static class Holder
  {
    static final OperatorSingle<?> INSTANCE = new OperatorSingle();
  }
  
  private static final class ParentSubscriber<T>
    extends Subscriber<T>
  {
    private final Subscriber<? super T> child;
    private final T defaultValue;
    private final boolean hasDefaultValue;
    private boolean hasTooManyElements = false;
    private boolean isNonEmpty = false;
    private T value;
    
    ParentSubscriber(Subscriber<? super T> paramSubscriber, boolean paramBoolean, T paramT)
    {
      this.child = paramSubscriber;
      this.hasDefaultValue = paramBoolean;
      this.defaultValue = paramT;
    }
    
    public void onCompleted()
    {
      if (this.hasTooManyElements) {
        return;
      }
      if (this.isNonEmpty)
      {
        this.child.onNext(this.value);
        this.child.onCompleted();
        return;
      }
      if (this.hasDefaultValue)
      {
        this.child.onNext(this.defaultValue);
        this.child.onCompleted();
        return;
      }
      this.child.onError(new NoSuchElementException("Sequence contains no elements"));
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.child.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.isNonEmpty)
      {
        this.hasTooManyElements = true;
        this.child.onError(new IllegalArgumentException("Sequence contains too many elements"));
        unsubscribe();
        return;
      }
      this.value = paramT;
      this.isNonEmpty = true;
    }
    
    void requestMore(long paramLong)
    {
      request(paramLong);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */