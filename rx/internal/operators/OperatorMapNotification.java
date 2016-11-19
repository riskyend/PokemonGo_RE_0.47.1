package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func1;

public final class OperatorMapNotification<T, R>
  implements Observable.Operator<R, T>
{
  final Func0<? extends R> onCompleted;
  final Func1<? super Throwable, ? extends R> onError;
  final Func1<? super T, ? extends R> onNext;
  
  public OperatorMapNotification(Func1<? super T, ? extends R> paramFunc1, Func1<? super Throwable, ? extends R> paramFunc11, Func0<? extends R> paramFunc0)
  {
    this.onNext = paramFunc1;
    this.onError = paramFunc11;
    this.onCompleted = paramFunc0;
  }
  
  public Subscriber<? super T> call(Subscriber<? super R> paramSubscriber)
  {
    final MapNotificationSubscriber localMapNotificationSubscriber = new MapNotificationSubscriber(paramSubscriber, this.onNext, this.onError, this.onCompleted);
    paramSubscriber.add(localMapNotificationSubscriber);
    paramSubscriber.setProducer(new Producer()
    {
      public void request(long paramAnonymousLong)
      {
        localMapNotificationSubscriber.requestInner(paramAnonymousLong);
      }
    });
    return localMapNotificationSubscriber;
  }
  
  static final class MapNotificationSubscriber<T, R>
    extends Subscriber<T>
  {
    static final long COMPLETED_FLAG = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;
    final Subscriber<? super R> actual;
    final AtomicLong missedRequested;
    final Func0<? extends R> onCompleted;
    final Func1<? super Throwable, ? extends R> onError;
    final Func1<? super T, ? extends R> onNext;
    long produced;
    final AtomicReference<Producer> producer;
    final AtomicLong requested;
    R value;
    
    public MapNotificationSubscriber(Subscriber<? super R> paramSubscriber, Func1<? super T, ? extends R> paramFunc1, Func1<? super Throwable, ? extends R> paramFunc11, Func0<? extends R> paramFunc0)
    {
      this.actual = paramSubscriber;
      this.onNext = paramFunc1;
      this.onError = paramFunc11;
      this.onCompleted = paramFunc0;
      this.requested = new AtomicLong();
      this.missedRequested = new AtomicLong();
      this.producer = new AtomicReference();
    }
    
    void accountProduced()
    {
      long l = this.produced;
      if ((l != 0L) && (this.producer.get() != null)) {
        BackpressureUtils.produced(this.requested, l);
      }
    }
    
    public void onCompleted()
    {
      accountProduced();
      try
      {
        this.value = this.onCompleted.call();
        tryEmit();
        return;
      }
      catch (Throwable localThrowable)
      {
        for (;;)
        {
          Exceptions.throwOrReport(localThrowable, this.actual);
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      accountProduced();
      try
      {
        this.value = this.onError.call(paramThrowable);
        tryEmit();
        return;
      }
      catch (Throwable localThrowable)
      {
        for (;;)
        {
          Exceptions.throwOrReport(localThrowable, this.actual, paramThrowable);
        }
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        this.produced += 1L;
        this.actual.onNext(this.onNext.call(paramT));
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwOrReport(localThrowable, this.actual, paramT);
      }
    }
    
    void requestInner(long paramLong)
    {
      if (paramLong < 0L) {
        throw new IllegalArgumentException("n >= 0 required but it was " + paramLong);
      }
      if (paramLong == 0L) {}
      Object localObject;
      do
      {
        do
        {
          return;
          long l1;
          long l2;
          do
          {
            long l3;
            do
            {
              l1 = this.requested.get();
              if ((0x8000000000000000 & l1) == 0L) {
                break;
              }
              l2 = l1 & 0x7FFFFFFFFFFFFFFF;
              l3 = BackpressureUtils.addCap(l2, paramLong);
            } while (!this.requested.compareAndSet(l1, l3 | 0x8000000000000000));
            if (l2 != 0L) {
              break;
            }
            if (!this.actual.isUnsubscribed()) {
              this.actual.onNext(this.value);
            }
            if (this.actual.isUnsubscribed()) {
              break;
            }
            this.actual.onCompleted();
            return;
            l2 = BackpressureUtils.addCap(l1, paramLong);
          } while (!this.requested.compareAndSet(l1, l2));
          localObject = this.producer;
          Producer localProducer = (Producer)((AtomicReference)localObject).get();
          if (localProducer != null)
          {
            localProducer.request(paramLong);
            return;
          }
          BackpressureUtils.getAndAddRequest(this.missedRequested, paramLong);
          localObject = (Producer)((AtomicReference)localObject).get();
        } while (localObject == null);
        paramLong = this.missedRequested.getAndSet(0L);
      } while (paramLong == 0L);
      ((Producer)localObject).request(paramLong);
    }
    
    public void setProducer(Producer paramProducer)
    {
      if (this.producer.compareAndSet(null, paramProducer))
      {
        long l = this.missedRequested.getAndSet(0L);
        if (l != 0L) {
          paramProducer.request(l);
        }
        return;
      }
      throw new IllegalStateException("Producer already set!");
    }
    
    void tryEmit()
    {
      long l = this.requested.get();
      if ((l & 0x8000000000000000) != 0L) {}
      do
      {
        do
        {
          return;
          if (!this.requested.compareAndSet(l, l | 0x8000000000000000)) {
            break;
          }
        } while ((l == 0L) && (this.producer.get() != null));
        if (!this.actual.isUnsubscribed()) {
          this.actual.onNext(this.value);
        }
      } while (this.actual.isUnsubscribed());
      this.actual.onCompleted();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorMapNotification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */