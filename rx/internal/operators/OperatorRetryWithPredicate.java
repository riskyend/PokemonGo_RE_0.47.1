package rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import rx.Observable;
import rx.Observable.Operator;
import rx.Producer;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func2;
import rx.internal.producers.ProducerArbiter;
import rx.schedulers.Schedulers;
import rx.subscriptions.SerialSubscription;

public final class OperatorRetryWithPredicate<T>
  implements Observable.Operator<T, Observable<T>>
{
  final Func2<Integer, Throwable, Boolean> predicate;
  
  public OperatorRetryWithPredicate(Func2<Integer, Throwable, Boolean> paramFunc2)
  {
    this.predicate = paramFunc2;
  }
  
  public Subscriber<? super Observable<T>> call(Subscriber<? super T> paramSubscriber)
  {
    Scheduler.Worker localWorker = Schedulers.trampoline().createWorker();
    paramSubscriber.add(localWorker);
    SerialSubscription localSerialSubscription = new SerialSubscription();
    paramSubscriber.add(localSerialSubscription);
    ProducerArbiter localProducerArbiter = new ProducerArbiter();
    paramSubscriber.setProducer(localProducerArbiter);
    return new SourceSubscriber(paramSubscriber, this.predicate, localWorker, localSerialSubscription, localProducerArbiter);
  }
  
  static final class SourceSubscriber<T>
    extends Subscriber<Observable<T>>
  {
    final AtomicInteger attempts = new AtomicInteger();
    final Subscriber<? super T> child;
    final Scheduler.Worker inner;
    final ProducerArbiter pa;
    final Func2<Integer, Throwable, Boolean> predicate;
    final SerialSubscription serialSubscription;
    
    public SourceSubscriber(Subscriber<? super T> paramSubscriber, Func2<Integer, Throwable, Boolean> paramFunc2, Scheduler.Worker paramWorker, SerialSubscription paramSerialSubscription, ProducerArbiter paramProducerArbiter)
    {
      this.child = paramSubscriber;
      this.predicate = paramFunc2;
      this.inner = paramWorker;
      this.serialSubscription = paramSerialSubscription;
      this.pa = paramProducerArbiter;
    }
    
    public void onCompleted() {}
    
    public void onError(Throwable paramThrowable)
    {
      this.child.onError(paramThrowable);
    }
    
    public void onNext(final Observable<T> paramObservable)
    {
      this.inner.schedule(new Action0()
      {
        public void call()
        {
          OperatorRetryWithPredicate.SourceSubscriber.this.attempts.incrementAndGet();
          Subscriber local1 = new Subscriber()
          {
            boolean done;
            
            public void onCompleted()
            {
              if (!this.done)
              {
                this.done = true;
                OperatorRetryWithPredicate.SourceSubscriber.this.child.onCompleted();
              }
            }
            
            public void onError(Throwable paramAnonymous2Throwable)
            {
              if (!this.done)
              {
                this.done = true;
                if ((((Boolean)OperatorRetryWithPredicate.SourceSubscriber.this.predicate.call(Integer.valueOf(OperatorRetryWithPredicate.SourceSubscriber.this.attempts.get()), paramAnonymous2Throwable)).booleanValue()) && (!OperatorRetryWithPredicate.SourceSubscriber.this.inner.isUnsubscribed())) {
                  OperatorRetryWithPredicate.SourceSubscriber.this.inner.schedule(jdField_this);
                }
              }
              else
              {
                return;
              }
              OperatorRetryWithPredicate.SourceSubscriber.this.child.onError(paramAnonymous2Throwable);
            }
            
            public void onNext(T paramAnonymous2T)
            {
              if (!this.done)
              {
                OperatorRetryWithPredicate.SourceSubscriber.this.child.onNext(paramAnonymous2T);
                OperatorRetryWithPredicate.SourceSubscriber.this.pa.produced(1L);
              }
            }
            
            public void setProducer(Producer paramAnonymous2Producer)
            {
              OperatorRetryWithPredicate.SourceSubscriber.this.pa.setProducer(paramAnonymous2Producer);
            }
          };
          OperatorRetryWithPredicate.SourceSubscriber.this.serialSubscription.set(local1);
          paramObservable.unsafeSubscribe(local1);
        }
      });
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorRetryWithPredicate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */