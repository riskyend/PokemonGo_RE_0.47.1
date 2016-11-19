package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Subscription;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeIterable
  implements Completable.CompletableOnSubscribe
{
  final Iterable<? extends Completable> sources;
  
  public CompletableOnSubscribeMergeIterable(Iterable<? extends Completable> paramIterable)
  {
    this.sources = paramIterable;
  }
  
  public void call(final Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    final Object localObject = new CompositeSubscription();
    final AtomicInteger localAtomicInteger = new AtomicInteger(1);
    AtomicBoolean localAtomicBoolean = new AtomicBoolean();
    paramCompletableSubscriber.onSubscribe((Subscription)localObject);
    for (;;)
    {
      Iterator localIterator;
      try
      {
        localIterator = this.sources.iterator();
        if (localIterator != null) {
          break label101;
        }
        paramCompletableSubscriber.onError(new NullPointerException("The source iterator returned is null"));
        return;
      }
      catch (Throwable localThrowable1)
      {
        paramCompletableSubscriber.onError(localThrowable1);
        return;
      }
      label76:
      localAtomicInteger.getAndIncrement();
      Completable localCompletable;
      localCompletable.subscribe(new Completable.CompletableSubscriber()
      {
        public void onCompleted()
        {
          if ((localAtomicInteger.decrementAndGet() == 0) && (localThrowable1.compareAndSet(false, true))) {
            paramCompletableSubscriber.onCompleted();
          }
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          localObject.unsubscribe();
          if (localThrowable1.compareAndSet(false, true))
          {
            paramCompletableSubscriber.onError(paramAnonymousThrowable);
            return;
          }
          RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymousThrowable);
        }
        
        public void onSubscribe(Subscription paramAnonymousSubscription)
        {
          localObject.add(paramAnonymousSubscription);
        }
      });
      label101:
      if (!((CompositeSubscription)localObject).isUnsubscribed())
      {
        try
        {
          boolean bool = localIterator.hasNext();
          if (!bool)
          {
            if ((localAtomicInteger.decrementAndGet() != 0) || (!localThrowable1.compareAndSet(false, true))) {
              continue;
            }
            paramCompletableSubscriber.onCompleted();
            return;
          }
        }
        catch (Throwable localThrowable2)
        {
          ((CompositeSubscription)localObject).unsubscribe();
          if (localThrowable1.compareAndSet(false, true))
          {
            paramCompletableSubscriber.onError(localThrowable2);
            return;
          }
          RxJavaPlugins.getInstance().getErrorHandler().handleError(localThrowable2);
          return;
        }
        if (!((CompositeSubscription)localObject).isUnsubscribed()) {
          try
          {
            localCompletable = (Completable)localIterator.next();
            if (!((CompositeSubscription)localObject).isUnsubscribed())
            {
              if (localCompletable != null) {
                break label76;
              }
              ((CompositeSubscription)localObject).unsubscribe();
              localObject = new NullPointerException("A completable source is null");
              if (localThrowable1.compareAndSet(false, true))
              {
                paramCompletableSubscriber.onError((Throwable)localObject);
                return;
              }
            }
          }
          catch (Throwable localThrowable3)
          {
            ((CompositeSubscription)localObject).unsubscribe();
            if (localThrowable1.compareAndSet(false, true))
            {
              paramCompletableSubscriber.onError(localThrowable3);
              return;
            }
            RxJavaPlugins.getInstance().getErrorHandler().handleError(localThrowable3);
            return;
          }
        }
      }
    }
    RxJavaPlugins.getInstance().getErrorHandler().handleError((Throwable)localObject);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeMergeIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */