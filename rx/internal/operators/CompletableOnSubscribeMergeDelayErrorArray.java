package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeDelayErrorArray
  implements Completable.CompletableOnSubscribe
{
  final Completable[] sources;
  
  public CompletableOnSubscribeMergeDelayErrorArray(Completable[] paramArrayOfCompletable)
  {
    this.sources = paramArrayOfCompletable;
  }
  
  public void call(final Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
    final AtomicInteger localAtomicInteger = new AtomicInteger(this.sources.length + 1);
    final ConcurrentLinkedQueue localConcurrentLinkedQueue = new ConcurrentLinkedQueue();
    paramCompletableSubscriber.onSubscribe(localCompositeSubscription);
    Completable[] arrayOfCompletable = this.sources;
    int j = arrayOfCompletable.length;
    int i = 0;
    if (i < j)
    {
      localCompletable = arrayOfCompletable[i];
      if (!localCompositeSubscription.isUnsubscribed()) {}
    }
    while (localAtomicInteger.decrementAndGet() != 0)
    {
      Completable localCompletable;
      return;
      if (localCompletable == null)
      {
        localConcurrentLinkedQueue.offer(new NullPointerException("A completable source is null"));
        localAtomicInteger.decrementAndGet();
      }
      for (;;)
      {
        i += 1;
        break;
        localCompletable.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            tryTerminate();
          }
          
          public void onError(Throwable paramAnonymousThrowable)
          {
            localConcurrentLinkedQueue.offer(paramAnonymousThrowable);
            tryTerminate();
          }
          
          public void onSubscribe(Subscription paramAnonymousSubscription)
          {
            localCompositeSubscription.add(paramAnonymousSubscription);
          }
          
          void tryTerminate()
          {
            if (localAtomicInteger.decrementAndGet() == 0)
            {
              if (localConcurrentLinkedQueue.isEmpty()) {
                paramCompletableSubscriber.onCompleted();
              }
            }
            else {
              return;
            }
            paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localConcurrentLinkedQueue));
          }
        });
      }
    }
    if (localConcurrentLinkedQueue.isEmpty())
    {
      paramCompletableSubscriber.onCompleted();
      return;
    }
    paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localConcurrentLinkedQueue));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeMergeDelayErrorArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */