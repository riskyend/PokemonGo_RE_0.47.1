package rx.internal.operators;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public final class BlockingOperatorToFuture
{
  private BlockingOperatorToFuture()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static <T> Future<T> toFuture(Observable<? extends T> paramObservable)
  {
    CountDownLatch localCountDownLatch = new CountDownLatch(1);
    final AtomicReference localAtomicReference1 = new AtomicReference();
    final AtomicReference localAtomicReference2 = new AtomicReference();
    new Future()
    {
      public void onCompleted()
      {
        this.val$finished.countDown();
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        localAtomicReference2.compareAndSet(null, paramAnonymousThrowable);
        this.val$finished.countDown();
      }
      
      public void onNext(T paramAnonymousT)
      {
        localAtomicReference1.set(paramAnonymousT);
      }
    }
    {
      private volatile boolean cancelled = false;
      
      private T getValue()
        throws ExecutionException
      {
        Throwable localThrowable = (Throwable)localAtomicReference2.get();
        if (localThrowable != null) {
          throw new ExecutionException("Observable onError", localThrowable);
        }
        if (this.cancelled) {
          throw new CancellationException("Subscription unsubscribed");
        }
        return (T)localAtomicReference1.get();
      }
      
      public boolean cancel(boolean paramAnonymousBoolean)
      {
        if (this.val$finished.getCount() > 0L)
        {
          this.cancelled = true;
          this.val$s.unsubscribe();
          this.val$finished.countDown();
          return true;
        }
        return false;
      }
      
      public T get()
        throws InterruptedException, ExecutionException
      {
        this.val$finished.await();
        return (T)getValue();
      }
      
      public T get(long paramAnonymousLong, TimeUnit paramAnonymousTimeUnit)
        throws InterruptedException, ExecutionException, TimeoutException
      {
        if (this.val$finished.await(paramAnonymousLong, paramAnonymousTimeUnit)) {
          return (T)getValue();
        }
        throw new TimeoutException("Timed out after " + paramAnonymousTimeUnit.toMillis(paramAnonymousLong) + "ms waiting for underlying Observable.");
      }
      
      public boolean isCancelled()
      {
        return this.cancelled;
      }
      
      public boolean isDone()
      {
        return this.val$finished.getCount() == 0L;
      }
    };
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/BlockingOperatorToFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */