package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;

public final class OperatorElementAt<T>
  implements Observable.Operator<T, T>
{
  final T defaultValue;
  final boolean hasDefault;
  final int index;
  
  public OperatorElementAt(int paramInt)
  {
    this(paramInt, null, false);
  }
  
  public OperatorElementAt(int paramInt, T paramT)
  {
    this(paramInt, paramT, true);
  }
  
  private OperatorElementAt(int paramInt, T paramT, boolean paramBoolean)
  {
    if (paramInt < 0) {
      throw new IndexOutOfBoundsException(paramInt + " is out of bounds");
    }
    this.index = paramInt;
    this.defaultValue = paramT;
    this.hasDefault = paramBoolean;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    Subscriber local1 = new Subscriber()
    {
      private int currentIndex = 0;
      
      public void onCompleted()
      {
        if (this.currentIndex <= OperatorElementAt.this.index)
        {
          if (OperatorElementAt.this.hasDefault)
          {
            paramSubscriber.onNext(OperatorElementAt.this.defaultValue);
            paramSubscriber.onCompleted();
          }
        }
        else {
          return;
        }
        paramSubscriber.onError(new IndexOutOfBoundsException(OperatorElementAt.this.index + " is out of bounds"));
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        int i = this.currentIndex;
        this.currentIndex = (i + 1);
        if (i == OperatorElementAt.this.index)
        {
          paramSubscriber.onNext(paramAnonymousT);
          paramSubscriber.onCompleted();
          unsubscribe();
        }
      }
      
      public void setProducer(Producer paramAnonymousProducer)
      {
        paramSubscriber.setProducer(new OperatorElementAt.InnerProducer(paramAnonymousProducer));
      }
    };
    paramSubscriber.add(local1);
    return local1;
  }
  
  static class InnerProducer
    extends AtomicBoolean
    implements Producer
  {
    private static final long serialVersionUID = 1L;
    final Producer actual;
    
    public InnerProducer(Producer paramProducer)
    {
      this.actual = paramProducer;
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= 0 required");
      }
      if ((paramLong > 0L) && (compareAndSet(false, true))) {
        this.actual.request(Long.MAX_VALUE);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorElementAt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */