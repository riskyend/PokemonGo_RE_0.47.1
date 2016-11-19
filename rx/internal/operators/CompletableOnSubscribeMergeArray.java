package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Subscription;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeArray
  implements Completable.CompletableOnSubscribe
{
  final Completable[] sources;
  
  public CompletableOnSubscribeMergeArray(Completable[] paramArrayOfCompletable)
  {
    this.sources = paramArrayOfCompletable;
  }
  
  public void call(final Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
    final AtomicInteger localAtomicInteger = new AtomicInteger(this.sources.length + 1);
    final AtomicBoolean localAtomicBoolean = new AtomicBoolean();
    paramCompletableSubscriber.onSubscribe(localCompositeSubscription);
    Completable[] arrayOfCompletable = this.sources;
    int j = arrayOfCompletable.length;
    int i = 0;
    if (i < j)
    {
      localCompletable = arrayOfCompletable[i];
      if (!localCompositeSubscription.isUnsubscribed()) {}
    }
    while ((localAtomicInteger.decrementAndGet() != 0) || (!localAtomicBoolean.compareAndSet(false, true)))
    {
      Completable localCompletable;
      return;
      if (localCompletable == null)
      {
        localCompositeSubscription.unsubscribe();
        NullPointerException localNullPointerException = new NullPointerException("A completable source is null");
        if (localAtomicBoolean.compareAndSet(false, true))
        {
          paramCompletableSubscriber.onError(localNullPointerException);
          return;
        }
        RxJavaPlugins.getInstance().getErrorHandler().handleError(localNullPointerException);
      }
      localCompletable.subscribe(new Completable.CompletableSubscriber()
      {
        public void onCompleted()
        {
          if ((localAtomicInteger.decrementAndGet() == 0) && (localAtomicBoolean.compareAndSet(false, true))) {
            paramCompletableSubscriber.onCompleted();
          }
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          localCompositeSubscription.unsubscribe();
          if (localAtomicBoolean.compareAndSet(false, true))
          {
            paramCompletableSubscriber.onError(paramAnonymousThrowable);
            return;
          }
          RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymousThrowable);
        }
        
        public void onSubscribe(Subscription paramAnonymousSubscription)
        {
          localCompositeSubscription.add(paramAnonymousSubscription);
        }
      });
      i += 1;
      break;
    }
    paramCompletableSubscriber.onCompleted();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeMergeArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */