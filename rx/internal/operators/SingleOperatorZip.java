package rx.internal.operators;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.exceptions.Exceptions;
import rx.functions.FuncN;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public class SingleOperatorZip
{
  public static <T, R> Single<R> zip(Single<? extends T>[] paramArrayOfSingle, final FuncN<? extends R> paramFuncN)
  {
    Single.create(new Single.OnSubscribe()
    {
      public void call(final SingleSubscriber<? super R> paramAnonymousSingleSubscriber)
      {
        if (this.val$singles.length == 0) {
          paramAnonymousSingleSubscriber.onError(new NoSuchElementException("Can't zip 0 Singles."));
        }
        label153:
        for (;;)
        {
          return;
          final AtomicInteger localAtomicInteger = new AtomicInteger(this.val$singles.length);
          final AtomicBoolean localAtomicBoolean = new AtomicBoolean();
          final Object[] arrayOfObject = new Object[this.val$singles.length];
          CompositeSubscription localCompositeSubscription = new CompositeSubscription();
          paramAnonymousSingleSubscriber.add(localCompositeSubscription);
          final int i = 0;
          for (;;)
          {
            if ((i >= this.val$singles.length) || (localCompositeSubscription.isUnsubscribed()) || (localAtomicBoolean.get())) {
              break label153;
            }
            SingleSubscriber local1 = new SingleSubscriber()
            {
              public void onError(Throwable paramAnonymous2Throwable)
              {
                if (localAtomicBoolean.compareAndSet(false, true))
                {
                  paramAnonymousSingleSubscriber.onError(paramAnonymous2Throwable);
                  return;
                }
                RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymous2Throwable);
              }
              
              public void onSuccess(T paramAnonymous2T)
              {
                arrayOfObject[i] = paramAnonymous2T;
                if (localAtomicInteger.decrementAndGet() == 0) {}
                try
                {
                  paramAnonymous2T = SingleOperatorZip.1.this.val$zipper.call(arrayOfObject);
                  paramAnonymousSingleSubscriber.onSuccess(paramAnonymous2T);
                  return;
                }
                catch (Throwable paramAnonymous2T)
                {
                  Exceptions.throwIfFatal(paramAnonymous2T);
                  onError(paramAnonymous2T);
                }
              }
            };
            localCompositeSubscription.add(local1);
            if ((localCompositeSubscription.isUnsubscribed()) || (localAtomicBoolean.get())) {
              break;
            }
            this.val$singles[i].subscribe(local1);
            i += 1;
          }
        }
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/SingleOperatorZip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */