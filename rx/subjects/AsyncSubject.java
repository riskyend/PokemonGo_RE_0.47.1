package rx.subjects;

import java.util.ArrayList;
import java.util.List;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.internal.operators.NotificationLite;
import rx.internal.producers.SingleProducer;

public final class AsyncSubject<T>
  extends Subject<T, T>
{
  volatile Object lastValue;
  private final NotificationLite<T> nl = NotificationLite.instance();
  final SubjectSubscriptionManager<T> state;
  
  protected AsyncSubject(Observable.OnSubscribe<T> paramOnSubscribe, SubjectSubscriptionManager<T> paramSubjectSubscriptionManager)
  {
    super(paramOnSubscribe);
    this.state = paramSubjectSubscriptionManager;
  }
  
  public static <T> AsyncSubject<T> create()
  {
    SubjectSubscriptionManager localSubjectSubscriptionManager = new SubjectSubscriptionManager();
    localSubjectSubscriptionManager.onTerminated = new Action1()
    {
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        Object localObject = this.val$state.getLatest();
        NotificationLite localNotificationLite = this.val$state.nl;
        if ((localObject == null) || (localNotificationLite.isCompleted(localObject)))
        {
          paramAnonymousSubjectObserver.onCompleted();
          return;
        }
        if (localNotificationLite.isError(localObject))
        {
          paramAnonymousSubjectObserver.onError(localNotificationLite.getError(localObject));
          return;
        }
        paramAnonymousSubjectObserver.actual.setProducer(new SingleProducer(paramAnonymousSubjectObserver.actual, localNotificationLite.getValue(localObject)));
      }
    };
    return new AsyncSubject(localSubjectSubscriptionManager, localSubjectSubscriptionManager);
  }
  
  @Beta
  public Throwable getThrowable()
  {
    Object localObject = this.state.getLatest();
    if (this.nl.isError(localObject)) {
      return this.nl.getError(localObject);
    }
    return null;
  }
  
  @Beta
  public T getValue()
  {
    Object localObject1 = this.lastValue;
    Object localObject2 = this.state.getLatest();
    if ((!this.nl.isError(localObject2)) && (this.nl.isNext(localObject1))) {
      return (T)this.nl.getValue(localObject1);
    }
    return null;
  }
  
  @Beta
  public boolean hasCompleted()
  {
    Object localObject = this.state.getLatest();
    return (localObject != null) && (!this.nl.isError(localObject));
  }
  
  public boolean hasObservers()
  {
    return this.state.observers().length > 0;
  }
  
  @Beta
  public boolean hasThrowable()
  {
    Object localObject = this.state.getLatest();
    return this.nl.isError(localObject);
  }
  
  @Beta
  public boolean hasValue()
  {
    Object localObject1 = this.lastValue;
    Object localObject2 = this.state.getLatest();
    return (!this.nl.isError(localObject2)) && (this.nl.isNext(localObject1));
  }
  
  public void onCompleted()
  {
    if (this.state.active)
    {
      Object localObject2 = this.lastValue;
      Object localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = this.nl.completed();
      }
      localObject2 = this.state.terminate(localObject1);
      int j = localObject2.length;
      int i = 0;
      if (i < j)
      {
        Object localObject3 = localObject2[i];
        if (localObject1 == this.nl.completed()) {
          ((SubjectSubscriptionManager.SubjectObserver)localObject3).onCompleted();
        }
        for (;;)
        {
          i += 1;
          break;
          ((SubjectSubscriptionManager.SubjectObserver)localObject3).actual.setProducer(new SingleProducer(((SubjectSubscriptionManager.SubjectObserver)localObject3).actual, this.nl.getValue(localObject1)));
        }
      }
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (this.state.active)
    {
      Object localObject2 = this.nl.error(paramThrowable);
      Object localObject1 = null;
      SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.state.terminate(localObject2);
      int j = arrayOfSubjectObserver.length;
      int i = 0;
      for (;;)
      {
        if (i < j)
        {
          localObject2 = arrayOfSubjectObserver[i];
          try
          {
            ((SubjectSubscriptionManager.SubjectObserver)localObject2).onError(paramThrowable);
            i += 1;
          }
          catch (Throwable localThrowable)
          {
            for (;;)
            {
              localObject2 = localObject1;
              if (localObject1 == null) {
                localObject2 = new ArrayList();
              }
              ((List)localObject2).add(localThrowable);
              localObject1 = localObject2;
            }
          }
        }
      }
      Exceptions.throwIfAny((List)localObject1);
    }
  }
  
  public void onNext(T paramT)
  {
    this.lastValue = this.nl.next(paramT);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/subjects/AsyncSubject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */