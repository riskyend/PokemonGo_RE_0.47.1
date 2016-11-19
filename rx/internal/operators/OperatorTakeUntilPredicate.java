package rx.internal.operators;

import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

public final class OperatorTakeUntilPredicate<T>
  implements Observable.Operator<T, T>
{
  final Func1<? super T, Boolean> stopPredicate;
  
  public OperatorTakeUntilPredicate(Func1<? super T, Boolean> paramFunc1)
  {
    this.stopPredicate = paramFunc1;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final ParentSubscriber localParentSubscriber = new ParentSubscriber(paramSubscriber);
    paramSubscriber.add(localParentSubscriber);
    paramSubscriber.setProducer(new Producer()
    {
      public void request(long paramAnonymousLong)
      {
        localParentSubscriber.downstreamRequest(paramAnonymousLong);
      }
    });
    return localParentSubscriber;
  }
  
  private final class ParentSubscriber
    extends Subscriber<T>
  {
    private final Subscriber<? super T> child;
    private boolean done = false;
    
    ParentSubscriber()
    {
      Subscriber localSubscriber;
      this.child = localSubscriber;
    }
    
    void downstreamRequest(long paramLong)
    {
      request(paramLong);
    }
    
    public void onCompleted()
    {
      if (!this.done) {
        this.child.onCompleted();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.done) {
        this.child.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.child.onNext(paramT);
      try
      {
        boolean bool = ((Boolean)OperatorTakeUntilPredicate.this.stopPredicate.call(paramT)).booleanValue();
        if (bool)
        {
          this.done = true;
          this.child.onCompleted();
          unsubscribe();
        }
        return;
      }
      catch (Throwable localThrowable)
      {
        this.done = true;
        Exceptions.throwOrReport(localThrowable, this.child, paramT);
        unsubscribe();
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorTakeUntilPredicate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */