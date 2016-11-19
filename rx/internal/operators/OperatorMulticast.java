package rx.internal.operators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.observables.ConnectableObservable;
import rx.observers.Subscribers;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

public final class OperatorMulticast<T, R>
  extends ConnectableObservable<R>
{
  final AtomicReference<Subject<? super T, ? extends R>> connectedSubject;
  final Object guard;
  Subscription guardedSubscription;
  final Observable<? extends T> source;
  final Func0<? extends Subject<? super T, ? extends R>> subjectFactory;
  Subscriber<T> subscription;
  final List<Subscriber<? super R>> waitingForConnect;
  
  private OperatorMulticast(Object paramObject, final AtomicReference<Subject<? super T, ? extends R>> paramAtomicReference, final List<Subscriber<? super R>> paramList, Observable<? extends T> paramObservable, Func0<? extends Subject<? super T, ? extends R>> paramFunc0)
  {
    super(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super R> paramAnonymousSubscriber)
      {
        synchronized (OperatorMulticast.this)
        {
          if (paramAtomicReference.get() == null)
          {
            paramList.add(paramAnonymousSubscriber);
            return;
          }
          ((Subject)paramAtomicReference.get()).unsafeSubscribe(paramAnonymousSubscriber);
        }
      }
    });
    this.guard = paramObject;
    this.connectedSubject = paramAtomicReference;
    this.waitingForConnect = paramList;
    this.source = paramObservable;
    this.subjectFactory = paramFunc0;
  }
  
  public OperatorMulticast(Observable<? extends T> paramObservable, Func0<? extends Subject<? super T, ? extends R>> paramFunc0)
  {
    this(new Object(), new AtomicReference(), new ArrayList(), paramObservable, paramFunc0);
  }
  
  public void connect(Action1<? super Subscription> arg1)
  {
    Subject localSubject;
    synchronized (this.guard)
    {
      if (this.subscription != null)
      {
        ???.call(this.guardedSubscription);
        return;
      }
      localSubject = (Subject)this.subjectFactory.call();
      this.subscription = Subscribers.from(localSubject);
      final Object localObject3 = new AtomicReference();
      ((AtomicReference)localObject3).set(Subscriptions.create(new Action0()
      {
        public void call()
        {
          synchronized (OperatorMulticast.this.guard)
          {
            if (OperatorMulticast.this.guardedSubscription == localObject3.get())
            {
              Subscriber localSubscriber = OperatorMulticast.this.subscription;
              OperatorMulticast.this.subscription = null;
              OperatorMulticast.this.guardedSubscription = null;
              OperatorMulticast.this.connectedSubject.set(null);
              if (localSubscriber != null) {
                localSubscriber.unsubscribe();
              }
              return;
            }
            return;
          }
        }
      }));
      this.guardedSubscription = ((Subscription)((AtomicReference)localObject3).get());
      localObject3 = this.waitingForConnect.iterator();
      if (((Iterator)localObject3).hasNext())
      {
        final Subscriber localSubscriber = (Subscriber)((Iterator)localObject3).next();
        localSubject.unsafeSubscribe(new Subscriber(localSubscriber)
        {
          public void onCompleted()
          {
            localSubscriber.onCompleted();
          }
          
          public void onError(Throwable paramAnonymousThrowable)
          {
            localSubscriber.onError(paramAnonymousThrowable);
          }
          
          public void onNext(R paramAnonymousR)
          {
            localSubscriber.onNext(paramAnonymousR);
          }
        });
      }
    }
    this.waitingForConnect.clear();
    this.connectedSubject.set(localSubject);
    ???.call(this.guardedSubscription);
    synchronized (this.guard)
    {
      ??? = this.subscription;
      if (??? != null)
      {
        this.source.subscribe((Subscriber)???);
        return;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorMulticast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */