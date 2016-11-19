package rx.internal.operators;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable.Operator;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;

public final class OperatorTakeLastTimed<T>
  implements Observable.Operator<T, T>
{
  final long ageMillis;
  final int count;
  final Scheduler scheduler;
  
  public OperatorTakeLastTimed(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    if (paramInt < 0) {
      throw new IndexOutOfBoundsException("count could not be negative");
    }
    this.ageMillis = paramTimeUnit.toMillis(paramLong);
    this.scheduler = paramScheduler;
    this.count = paramInt;
  }
  
  public OperatorTakeLastTimed(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    this.ageMillis = paramTimeUnit.toMillis(paramLong);
    this.scheduler = paramScheduler;
    this.count = -1;
  }
  
  public Subscriber<? super T> call(Subscriber<? super T> paramSubscriber)
  {
    final TakeLastTimedSubscriber localTakeLastTimedSubscriber = new TakeLastTimedSubscriber(paramSubscriber, this.count, this.ageMillis, this.scheduler);
    paramSubscriber.add(localTakeLastTimedSubscriber);
    paramSubscriber.setProducer(new Producer()
    {
      public void request(long paramAnonymousLong)
      {
        localTakeLastTimedSubscriber.requestMore(paramAnonymousLong);
      }
    });
    return localTakeLastTimedSubscriber;
  }
  
  static final class TakeLastTimedSubscriber<T>
    extends Subscriber<T>
    implements Func1<Object, T>
  {
    final Subscriber<? super T> actual;
    final long ageMillis;
    final int count;
    final NotificationLite<T> nl;
    final ArrayDeque<Object> queue;
    final ArrayDeque<Long> queueTimes;
    final AtomicLong requested;
    final Scheduler scheduler;
    
    public TakeLastTimedSubscriber(Subscriber<? super T> paramSubscriber, int paramInt, long paramLong, Scheduler paramScheduler)
    {
      this.actual = paramSubscriber;
      this.count = paramInt;
      this.ageMillis = paramLong;
      this.scheduler = paramScheduler;
      this.requested = new AtomicLong();
      this.queue = new ArrayDeque();
      this.queueTimes = new ArrayDeque();
      this.nl = NotificationLite.instance();
    }
    
    public T call(Object paramObject)
    {
      return (T)this.nl.getValue(paramObject);
    }
    
    protected void evictOld(long paramLong)
    {
      long l = this.ageMillis;
      for (;;)
      {
        Long localLong = (Long)this.queueTimes.peek();
        if ((localLong == null) || (localLong.longValue() >= paramLong - l)) {
          return;
        }
        this.queue.poll();
        this.queueTimes.poll();
      }
    }
    
    public void onCompleted()
    {
      evictOld(this.scheduler.now());
      this.queueTimes.clear();
      BackpressureUtils.postCompleteDone(this.requested, this.queue, this.actual, this);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.queue.clear();
      this.queueTimes.clear();
      this.actual.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.count != 0)
      {
        long l = this.scheduler.now();
        if (this.queue.size() == this.count)
        {
          this.queue.poll();
          this.queueTimes.poll();
        }
        evictOld(l);
        this.queue.offer(this.nl.next(paramT));
        this.queueTimes.offer(Long.valueOf(l));
      }
    }
    
    void requestMore(long paramLong)
    {
      BackpressureUtils.postCompleteRequest(this.requested, paramLong, this.queue, this.actual, this);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorTakeLastTimed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */