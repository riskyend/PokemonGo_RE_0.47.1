package rx.subjects;

import java.util.concurrent.TimeUnit;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.schedulers.TestScheduler;

public final class TestSubject<T>
  extends Subject<T, T>
{
  private final Scheduler.Worker innerScheduler;
  private final SubjectSubscriptionManager<T> state;
  
  protected TestSubject(Observable.OnSubscribe<T> paramOnSubscribe, SubjectSubscriptionManager<T> paramSubjectSubscriptionManager, TestScheduler paramTestScheduler)
  {
    super(paramOnSubscribe);
    this.state = paramSubjectSubscriptionManager;
    this.innerScheduler = paramTestScheduler.createWorker();
  }
  
  public static <T> TestSubject<T> create(TestScheduler paramTestScheduler)
  {
    SubjectSubscriptionManager localSubjectSubscriptionManager = new SubjectSubscriptionManager();
    localSubjectSubscriptionManager.onAdded = new Action1()
    {
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        paramAnonymousSubjectObserver.emitFirst(this.val$state.getLatest(), this.val$state.nl);
      }
    };
    localSubjectSubscriptionManager.onTerminated = localSubjectSubscriptionManager.onAdded;
    return new TestSubject(localSubjectSubscriptionManager, localSubjectSubscriptionManager, paramTestScheduler);
  }
  
  void _onCompleted()
  {
    if (this.state.active)
    {
      SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.state.terminate(NotificationLite.instance().completed());
      int j = arrayOfSubjectObserver.length;
      int i = 0;
      while (i < j)
      {
        arrayOfSubjectObserver[i].onCompleted();
        i += 1;
      }
    }
  }
  
  void _onError(Throwable paramThrowable)
  {
    if (this.state.active)
    {
      SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.state.terminate(NotificationLite.instance().error(paramThrowable));
      int j = arrayOfSubjectObserver.length;
      int i = 0;
      while (i < j)
      {
        arrayOfSubjectObserver[i].onError(paramThrowable);
        i += 1;
      }
    }
  }
  
  void _onNext(T paramT)
  {
    SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.state.observers();
    int j = arrayOfSubjectObserver.length;
    int i = 0;
    while (i < j)
    {
      arrayOfSubjectObserver[i].onNext(paramT);
      i += 1;
    }
  }
  
  public boolean hasObservers()
  {
    return this.state.observers().length > 0;
  }
  
  public void onCompleted()
  {
    onCompleted(0L);
  }
  
  public void onCompleted(long paramLong)
  {
    this.innerScheduler.schedule(new Action0()
    {
      public void call()
      {
        TestSubject.this._onCompleted();
      }
    }, paramLong, TimeUnit.MILLISECONDS);
  }
  
  public void onError(Throwable paramThrowable)
  {
    onError(paramThrowable, 0L);
  }
  
  public void onError(final Throwable paramThrowable, long paramLong)
  {
    this.innerScheduler.schedule(new Action0()
    {
      public void call()
      {
        TestSubject.this._onError(paramThrowable);
      }
    }, paramLong, TimeUnit.MILLISECONDS);
  }
  
  public void onNext(T paramT)
  {
    onNext(paramT, 0L);
  }
  
  public void onNext(final T paramT, long paramLong)
  {
    this.innerScheduler.schedule(new Action0()
    {
      public void call()
      {
        TestSubject.this._onNext(paramT);
      }
    }, paramLong, TimeUnit.MILLISECONDS);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/subjects/TestSubject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */