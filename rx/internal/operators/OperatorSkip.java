package rx.internal.operators;

import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;

public final class OperatorSkip<T>
  implements Observable.Operator<T, T>
{
  final int toSkip;
  
  public OperatorSkip(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("n >= 0 required but it was " + paramInt);
    }
    this.toSkip = paramInt;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super T> paramSubscriber)
  {
    new Subscriber(paramSubscriber)
    {
      int skipped = 0;
      
      public void onCompleted()
      {
        paramSubscriber.onCompleted();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        if (this.skipped >= OperatorSkip.this.toSkip)
        {
          paramSubscriber.onNext(paramAnonymousT);
          return;
        }
        this.skipped += 1;
      }
      
      public void setProducer(Producer paramAnonymousProducer)
      {
        paramSubscriber.setProducer(paramAnonymousProducer);
        paramAnonymousProducer.request(OperatorSkip.this.toSkip);
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorSkip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */