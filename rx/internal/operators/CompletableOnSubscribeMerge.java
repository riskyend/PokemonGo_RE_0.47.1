package rx.internal.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import rx.Completable;
import rx.Completable.CompletableOnSubscribe;
import rx.Completable.CompletableSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMerge
  implements Completable.CompletableOnSubscribe
{
  final boolean delayErrors;
  final int maxConcurrency;
  final Observable<Completable> source;
  
  public CompletableOnSubscribeMerge(Observable<? extends Completable> paramObservable, int paramInt, boolean paramBoolean)
  {
    this.source = paramObservable;
    this.maxConcurrency = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  public static Throwable collectErrors(Queue<Throwable> paramQueue)
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      Throwable localThrowable = (Throwable)paramQueue.poll();
      if (localThrowable == null) {
        break;
      }
      localArrayList.add(localThrowable);
    }
    if (localArrayList.isEmpty()) {
      return null;
    }
    if (localArrayList.size() == 1) {
      return (Throwable)localArrayList.get(0);
    }
    return new CompositeException(localArrayList);
  }
  
  public void call(Completable.CompletableSubscriber paramCompletableSubscriber)
  {
    CompletableMergeSubscriber localCompletableMergeSubscriber = new CompletableMergeSubscriber(paramCompletableSubscriber, this.maxConcurrency, this.delayErrors);
    paramCompletableSubscriber.onSubscribe(localCompletableMergeSubscriber);
    this.source.subscribe(localCompletableMergeSubscriber);
  }
  
  static final class CompletableMergeSubscriber
    extends Subscriber<Completable>
  {
    static final AtomicReferenceFieldUpdater<CompletableMergeSubscriber, Queue> ERRORS = AtomicReferenceFieldUpdater.newUpdater(CompletableMergeSubscriber.class, Queue.class, "errors");
    static final AtomicIntegerFieldUpdater<CompletableMergeSubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(CompletableMergeSubscriber.class, "once");
    final Completable.CompletableSubscriber actual;
    final boolean delayErrors;
    volatile boolean done;
    volatile Queue<Throwable> errors;
    final int maxConcurrency;
    volatile int once;
    final CompositeSubscription set;
    final AtomicInteger wip;
    
    public CompletableMergeSubscriber(Completable.CompletableSubscriber paramCompletableSubscriber, int paramInt, boolean paramBoolean)
    {
      this.actual = paramCompletableSubscriber;
      this.maxConcurrency = paramInt;
      this.delayErrors = paramBoolean;
      this.set = new CompositeSubscription();
      this.wip = new AtomicInteger(1);
      if (paramInt == Integer.MAX_VALUE)
      {
        request(Long.MAX_VALUE);
        return;
      }
      request(paramInt);
    }
    
    Queue<Throwable> getOrCreateErrors()
    {
      Object localObject = this.errors;
      if (localObject != null) {
        return (Queue<Throwable>)localObject;
      }
      localObject = new ConcurrentLinkedQueue();
      if (ERRORS.compareAndSet(this, null, localObject)) {
        return (Queue<Throwable>)localObject;
      }
      return this.errors;
    }
    
    public void onCompleted()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      terminate();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
        return;
      }
      getOrCreateErrors().offer(paramThrowable);
      this.done = true;
      terminate();
    }
    
    public void onNext(Completable paramCompletable)
    {
      if (this.done) {
        return;
      }
      this.wip.getAndIncrement();
      paramCompletable.subscribe(new Completable.CompletableSubscriber()
      {
        Subscription d;
        boolean innerDone;
        
        public void onCompleted()
        {
          if (this.innerDone) {}
          do
          {
            return;
            this.innerDone = true;
            CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.set.remove(this.d);
            CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.terminate();
          } while (CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.done);
          CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.request(1L);
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          if (this.innerDone) {
            RxJavaPlugins.getInstance().getErrorHandler().handleError(paramAnonymousThrowable);
          }
          do
          {
            return;
            this.innerDone = true;
            CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.set.remove(this.d);
            CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.getOrCreateErrors().offer(paramAnonymousThrowable);
            CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.terminate();
          } while ((!CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.delayErrors) || (CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.done));
          CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.request(1L);
        }
        
        public void onSubscribe(Subscription paramAnonymousSubscription)
        {
          this.d = paramAnonymousSubscription;
          CompletableOnSubscribeMerge.CompletableMergeSubscriber.this.set.add(paramAnonymousSubscription);
        }
      });
    }
    
    void terminate()
    {
      if (this.wip.decrementAndGet() == 0)
      {
        localObject = this.errors;
        if ((localObject == null) || (((Queue)localObject).isEmpty())) {
          this.actual.onCompleted();
        }
      }
      do
      {
        do
        {
          return;
          localObject = CompletableOnSubscribeMerge.collectErrors((Queue)localObject);
          if (ONCE.compareAndSet(this, 0, 1))
          {
            this.actual.onError((Throwable)localObject);
            return;
          }
          RxJavaPlugins.getInstance().getErrorHandler().handleError((Throwable)localObject);
          return;
        } while (this.delayErrors);
        localObject = this.errors;
      } while ((localObject == null) || (((Queue)localObject).isEmpty()));
      Object localObject = CompletableOnSubscribeMerge.collectErrors((Queue)localObject);
      if (ONCE.compareAndSet(this, 0, 1))
      {
        this.actual.onError((Throwable)localObject);
        return;
      }
      RxJavaPlugins.getInstance().getErrorHandler().handleError((Throwable)localObject);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CompletableOnSubscribeMerge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */