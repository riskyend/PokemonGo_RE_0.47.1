package rx.internal.operators;

import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.internal.producers.SingleDelayedProducer;

public final class OperatorAny<T>
  implements Observable.Operator<Boolean, T>
{
  final Func1<? super T, Boolean> predicate;
  final boolean returnOnEmpty;
  
  public OperatorAny(Func1<? super T, Boolean> paramFunc1, boolean paramBoolean)
  {
    this.predicate = paramFunc1;
    this.returnOnEmpty = paramBoolean;
  }
  
  public Subscriber<? super T> call(final Subscriber<? super Boolean> paramSubscriber)
  {
    final SingleDelayedProducer localSingleDelayedProducer = new SingleDelayedProducer(paramSubscriber);
    Subscriber local1 = new Subscriber()
    {
      boolean done;
      boolean hasElements;
      
      public void onCompleted()
      {
        if (!this.done)
        {
          this.done = true;
          if (this.hasElements) {
            localSingleDelayedProducer.setValue(Boolean.valueOf(false));
          }
        }
        else
        {
          return;
        }
        localSingleDelayedProducer.setValue(Boolean.valueOf(OperatorAny.this.returnOnEmpty));
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        paramSubscriber.onError(paramAnonymousThrowable);
      }
      
      public void onNext(T paramAnonymousT)
      {
        this.hasElements = true;
        for (;;)
        {
          try
          {
            bool = ((Boolean)OperatorAny.this.predicate.call(paramAnonymousT)).booleanValue();
            if ((bool) && (!this.done))
            {
              this.done = true;
              paramAnonymousT = localSingleDelayedProducer;
              if (!OperatorAny.this.returnOnEmpty)
              {
                bool = true;
                paramAnonymousT.setValue(Boolean.valueOf(bool));
                unsubscribe();
              }
            }
            else
            {
              return;
            }
          }
          catch (Throwable localThrowable)
          {
            Exceptions.throwOrReport(localThrowable, this, paramAnonymousT);
            return;
          }
          boolean bool = false;
        }
      }
    };
    paramSubscriber.add(local1);
    paramSubscriber.setProducer(localSingleDelayedProducer);
    return local1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorAny.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */