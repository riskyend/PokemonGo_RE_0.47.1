package rx.singles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import rx.Single;
import rx.SingleSubscriber;
import rx.annotations.Experimental;
import rx.internal.operators.BlockingOperatorToFuture;
import rx.internal.util.BlockingUtils;

@Experimental
public class BlockingSingle<T>
{
  private final Single<? extends T> single;
  
  private BlockingSingle(Single<? extends T> paramSingle)
  {
    this.single = paramSingle;
  }
  
  @Experimental
  public static <T> BlockingSingle<T> from(Single<? extends T> paramSingle)
  {
    return new BlockingSingle(paramSingle);
  }
  
  @Experimental
  public Future<T> toFuture()
  {
    return BlockingOperatorToFuture.toFuture(this.single.toObservable());
  }
  
  @Experimental
  public T value()
  {
    final AtomicReference localAtomicReference = new AtomicReference();
    final Object localObject = new AtomicReference();
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    BlockingUtils.awaitForComplete(localCountDownLatch, this.single.subscribe(new SingleSubscriber()
    {
      public void onError(Throwable paramAnonymousThrowable)
      {
        localObject.set(paramAnonymousThrowable);
        localCountDownLatch.countDown();
      }
      
      public void onSuccess(T paramAnonymousT)
      {
        localAtomicReference.set(paramAnonymousT);
        localCountDownLatch.countDown();
      }
    }));
    localObject = (Throwable)((AtomicReference)localObject).get();
    if (localObject != null)
    {
      if ((localObject instanceof RuntimeException)) {
        throw ((RuntimeException)localObject);
      }
      throw new RuntimeException((Throwable)localObject);
    }
    return (T)localAtomicReference.get();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/singles/BlockingSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */