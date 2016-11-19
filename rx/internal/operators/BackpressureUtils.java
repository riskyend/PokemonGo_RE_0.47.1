package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import rx.Subscriber;
import rx.functions.Func1;
import rx.internal.util.UtilityFunctions;

public final class BackpressureUtils
{
  static final long COMPLETED_MASK = Long.MIN_VALUE;
  static final long REQUESTED_MASK = Long.MAX_VALUE;
  
  private BackpressureUtils()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static long addCap(long paramLong1, long paramLong2)
  {
    paramLong2 = paramLong1 + paramLong2;
    paramLong1 = paramLong2;
    if (paramLong2 < 0L) {
      paramLong1 = Long.MAX_VALUE;
    }
    return paramLong1;
  }
  
  public static long getAndAddRequest(AtomicLong paramAtomicLong, long paramLong)
  {
    long l;
    do
    {
      l = paramAtomicLong.get();
    } while (!paramAtomicLong.compareAndSet(l, addCap(l, paramLong)));
    return l;
  }
  
  public static <T> long getAndAddRequest(AtomicLongFieldUpdater<T> paramAtomicLongFieldUpdater, T paramT, long paramLong)
  {
    long l;
    do
    {
      l = paramAtomicLongFieldUpdater.get(paramT);
    } while (!paramAtomicLongFieldUpdater.compareAndSet(paramT, l, addCap(l, paramLong)));
    return l;
  }
  
  public static long multiplyCap(long paramLong1, long paramLong2)
  {
    long l2 = paramLong1 * paramLong2;
    long l1 = l2;
    if ((paramLong1 | paramLong2) >>> 31 != 0L)
    {
      l1 = l2;
      if (paramLong2 != 0L)
      {
        l1 = l2;
        if (l2 / paramLong2 != paramLong1) {
          l1 = Long.MAX_VALUE;
        }
      }
    }
    return l1;
  }
  
  public static <T> void postCompleteDone(AtomicLong paramAtomicLong, Queue<T> paramQueue, Subscriber<? super T> paramSubscriber)
  {
    postCompleteDone(paramAtomicLong, paramQueue, paramSubscriber, UtilityFunctions.identity());
  }
  
  public static <T, R> void postCompleteDone(AtomicLong paramAtomicLong, Queue<T> paramQueue, Subscriber<? super R> paramSubscriber, Func1<? super T, ? extends R> paramFunc1)
  {
    long l = paramAtomicLong.get();
    if ((l & 0x8000000000000000) != 0L) {}
    do
    {
      return;
      if (!paramAtomicLong.compareAndSet(l, l | 0x8000000000000000)) {
        break;
      }
    } while (l == 0L);
    postCompleteDrain(paramAtomicLong, paramQueue, paramSubscriber, paramFunc1);
  }
  
  static <T, R> void postCompleteDrain(AtomicLong paramAtomicLong, Queue<T> paramQueue, Subscriber<? super R> paramSubscriber, Func1<? super T, ? extends R> paramFunc1)
  {
    long l1 = paramAtomicLong.get();
    if (l1 == Long.MAX_VALUE) {
      for (;;)
      {
        if (paramSubscriber.isUnsubscribed()) {
          return;
        }
        paramAtomicLong = paramQueue.poll();
        if (paramAtomicLong == null)
        {
          paramSubscriber.onCompleted();
          return;
        }
        paramSubscriber.onNext(paramFunc1.call(paramAtomicLong));
      }
    }
    long l2 = Long.MIN_VALUE;
    for (;;)
    {
      if (l2 != l1)
      {
        if (paramSubscriber.isUnsubscribed()) {
          break;
        }
        Object localObject = paramQueue.poll();
        if (localObject == null)
        {
          paramSubscriber.onCompleted();
          return;
        }
        paramSubscriber.onNext(paramFunc1.call(localObject));
        l2 += 1L;
        continue;
      }
      if (l2 == l1)
      {
        if (paramSubscriber.isUnsubscribed()) {
          break;
        }
        if (paramQueue.isEmpty())
        {
          paramSubscriber.onCompleted();
          return;
        }
      }
      long l3 = paramAtomicLong.get();
      l1 = l3;
      if (l3 == l2)
      {
        l1 = paramAtomicLong.addAndGet(-(l2 & 0x7FFFFFFFFFFFFFFF));
        if (l1 == Long.MIN_VALUE) {
          break;
        }
        l2 = Long.MIN_VALUE;
      }
    }
  }
  
  public static <T> boolean postCompleteRequest(AtomicLong paramAtomicLong, long paramLong, Queue<T> paramQueue, Subscriber<? super T> paramSubscriber)
  {
    return postCompleteRequest(paramAtomicLong, paramLong, paramQueue, paramSubscriber, UtilityFunctions.identity());
  }
  
  public static <T, R> boolean postCompleteRequest(AtomicLong paramAtomicLong, long paramLong, Queue<T> paramQueue, Subscriber<? super R> paramSubscriber, Func1<? super T, ? extends R> paramFunc1)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("n >= 0 required but it was " + paramLong);
    }
    if (paramLong == 0L) {
      return (paramAtomicLong.get() & 0x8000000000000000) == 0L;
    }
    long l1;
    long l2;
    do
    {
      l1 = paramAtomicLong.get();
      l2 = l1 & 0x8000000000000000;
    } while (!paramAtomicLong.compareAndSet(l1, addCap(l1 & 0x7FFFFFFFFFFFFFFF, paramLong) | l2));
    if (l1 == Long.MIN_VALUE)
    {
      postCompleteDrain(paramAtomicLong, paramQueue, paramSubscriber, paramFunc1);
      return false;
    }
    return l2 == 0L;
  }
  
  public static long produced(AtomicLong paramAtomicLong, long paramLong)
  {
    long l1;
    long l2;
    do
    {
      l1 = paramAtomicLong.get();
      if (l1 == Long.MAX_VALUE) {
        return Long.MAX_VALUE;
      }
      l2 = l1 - paramLong;
      if (l2 < 0L) {
        throw new IllegalStateException("More produced than requested: " + l2);
      }
    } while (!paramAtomicLong.compareAndSet(l1, l2));
    return l2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/BackpressureUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */