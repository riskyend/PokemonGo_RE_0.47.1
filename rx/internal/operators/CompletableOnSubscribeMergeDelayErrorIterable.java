package rx.internal.operators;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Subscription;
import rx.internal.util.unsafe.MpscLinkedQueue;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeDelayErrorIterable
  implements Completable.CompletableOnSubscribe
{
  final Iterable<? extends Completable> sources;
  
  public CompletableOnSubscribeMergeDelayErrorIterable(Iterable<? extends Completable> paramIterable)
  {
    this.sources = paramIterable;
  }
  
  public void call(final Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    final CompositeSubscription localCompositeSubscription = new CompositeSubscription();
    AtomicInteger localAtomicInteger = new AtomicInteger(1);
    final MpscLinkedQueue localMpscLinkedQueue = new MpscLinkedQueue();
    paramCompletableSubscriber.onSubscribe(localCompositeSubscription);
    label76:
    label100:
    label192:
    do
    {
      for (;;)
      {
        Iterator localIterator;
        try
        {
          localIterator = this.sources.iterator();
          if (localIterator != null) {
            break label100;
          }
          paramCompletableSubscriber.onError(new NullPointerException("The source iterator returned is null"));
          return;
        }
        catch (Throwable localThrowable1)
        {
          paramCompletableSubscriber.onError(localThrowable1);
          return;
        }
        localThrowable1.getAndIncrement();
        Completable localCompletable;
        localCompletable.subscribe(new Completable.CompletableSubscriber()
        {
          public void onCompleted()
          {
            tryTerminate();
          }
          
          public void onError(Throwable paramAnonymousThrowable)
          {
            localMpscLinkedQueue.offer(paramAnonymousThrowable);
            tryTerminate();
          }
          
          public void onSubscribe(Subscription paramAnonymousSubscription)
          {
            localCompositeSubscription.add(paramAnonymousSubscription);
          }
          
          void tryTerminate()
          {
            if (localThrowable1.decrementAndGet() == 0)
            {
              if (localMpscLinkedQueue.isEmpty()) {
                paramCompletableSubscriber.onCompleted();
              }
            }
            else {
              return;
            }
            paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localMpscLinkedQueue));
          }
        });
        if (!localCompositeSubscription.isUnsubscribed())
        {
          try
          {
            boolean bool = localIterator.hasNext();
            if (bool) {
              break label192;
            }
            if (localThrowable1.decrementAndGet() != 0) {
              continue;
            }
            if (!localMpscLinkedQueue.isEmpty()) {
              break label326;
            }
            paramCompletableSubscriber.onCompleted();
            return;
          }
          catch (Throwable localThrowable2)
          {
            localMpscLinkedQueue.offer(localThrowable2);
          }
          if (localThrowable1.decrementAndGet() == 0)
          {
            if (localMpscLinkedQueue.isEmpty())
            {
              paramCompletableSubscriber.onCompleted();
              return;
            }
            paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localMpscLinkedQueue));
            return;
            if (!localThrowable2.isUnsubscribed()) {
              try
              {
                localCompletable = (Completable)localIterator.next();
                if (!localThrowable2.isUnsubscribed())
                {
                  if (localCompletable != null) {
                    break label76;
                  }
                  localMpscLinkedQueue.offer(new NullPointerException("A completable source is null"));
                  if (localThrowable1.decrementAndGet() == 0)
                  {
                    if (!localMpscLinkedQueue.isEmpty()) {
                      break label314;
                    }
                    paramCompletableSubscriber.onCompleted();
                    return;
                  }
                }
              }
              catch (Throwable localThrowable3)
              {
                localMpscLinkedQueue.offer(localThrowable3);
              }
            }
          }
        }
      }
    } while (localThrowable1.decrementAndGet() != 0);
    if (localMpscLinkedQueue.isEmpty())
    {
      paramCompletableSubscriber.onCompleted();
      return;
    }
    paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localMpscLinkedQueue));
    return;
    label314:
    paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localMpscLinkedQueue));
    return;
    label326:
    paramCompletableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(localMpscLinkedQueue));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeMergeDelayErrorIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */