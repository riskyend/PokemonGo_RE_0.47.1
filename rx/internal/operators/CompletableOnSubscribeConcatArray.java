package rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Subscription;
import rx.subscriptions.SerialSubscription;

public final class CompletableOnSubscribeConcatArray
  implements Completable.CompletableOnSubscribe
{
  final Completable[] sources;
  
  public CompletableOnSubscribeConcatArray(Completable[] paramArrayOfCompletable)
  {
    this.sources = paramArrayOfCompletable;
  }
  
  public void call(Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    ConcatInnerSubscriber localConcatInnerSubscriber = new ConcatInnerSubscriber(paramCompletableSubscriber, this.sources);
    paramCompletableSubscriber.onSubscribe(localConcatInnerSubscriber.sd);
    localConcatInnerSubscriber.next();
  }
  
  static final class ConcatInnerSubscriber
    extends AtomicInteger
    implements Completable.CompletableSubscriber
  {
    private static final long serialVersionUID = -7965400327305809232L;
    final Completable.CompletableSubscriber actual;
    int index;
    final SerialSubscription sd;
    final Completable[] sources;
    
    public ConcatInnerSubscriber(Completable.CompletableSubscriber paramCompletableSubscriber, Completable[] paramArrayOfCompletable)
    {
      this.actual = paramCompletableSubscriber;
      this.sources = paramArrayOfCompletable;
      this.sd = new SerialSubscription();
    }
    
    void next()
    {
      if (this.sd.isUnsubscribed()) {}
      do
      {
        Completable[] arrayOfCompletable;
        do
        {
          return;
          while (getAndIncrement() != 0) {}
          arrayOfCompletable = this.sources;
        } while (this.sd.isUnsubscribed());
        int i = this.index;
        this.index = (i + 1);
        if (i == arrayOfCompletable.length)
        {
          this.actual.onCompleted();
          return;
        }
        arrayOfCompletable[i].subscribe(this);
      } while (decrementAndGet() != 0);
    }
    
    public void onCompleted()
    {
      next();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.actual.onError(paramThrowable);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      this.sd.set(paramSubscription);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeConcatArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */