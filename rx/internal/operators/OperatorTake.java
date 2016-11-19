package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;

public final class OperatorTake<T>
  implements Observable.Operator<T, T>
{
  final int limit;
  
  public OperatorTake(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("limit >= 0 required but it was " + paramInt);
    }
    this.limit = paramInt;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    Subscriber local1 = new Subscriber()
    {
      boolean completed;
      int count;
      
      public void onCompleted()
      {
        if (!this.completed)
        {
          this.completed = true;
          paramSubscriber.onCompleted();
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        if (!this.completed) {
          this.completed = true;
        }
        try
        {
          paramSubscriber.onError(paramAnonymousThrowable);
          return;
        }
        finally
        {
          unsubscribe();
        }
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (!isUnsubscribed())
        {
          i = this.count;
          this.count = (i + 1);
          if (i < OperatorTake.this.limit) {
            if (this.count != OperatorTake.this.limit) {
              break label82;
            }
          }
        }
        for (int i = 1;; i = 0)
        {
          paramSubscriber.onNext(paramAnonymousT);
          if ((i != 0) && (!this.completed)) {
            this.completed = true;
          }
          try
          {
            paramSubscriber.onCompleted();
            return;
          }
          finally
          {
            label82:
            unsubscribe();
          }
        }
      }
      
      public void setProducer(final Producer paramAnonymousProducer)
      {
        paramSubscriber.setProducer(new Producer()
        {
          final AtomicLong requested = new AtomicLong(0L);
          
          public void request(long paramAnonymous2Long)
          {
            if ((paramAnonymous2Long > 0L) && (!OperatorTake.1.this.completed)) {}
            long l1;
            long l2;
            do
            {
              l1 = this.requested.get();
              l2 = Math.min(paramAnonymous2Long, OperatorTake.this.limit - l1);
              if (l2 == 0L) {
                return;
              }
            } while (!this.requested.compareAndSet(l1, l1 + l2));
            paramAnonymousProducer.request(l2);
          }
        });
      }
    };
    if (this.limit == 0)
    {
      paramSubscriber.onCompleted();
      local1.unsubscribe();
    }
    paramSubscriber.add(local1);
    return local1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorTake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */