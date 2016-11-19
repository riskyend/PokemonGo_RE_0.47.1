package rx.internal.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import rx.Observable;
import rx.Observable.Operator;
import rx.Observer;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

public final class OperatorWindowWithObservable<T, U>
  implements Observable.Operator<Observable<T>, T>
{
  static final Object NEXT_SUBJECT = new Object();
  static final NotificationLite<Object> nl = NotificationLite.instance();
  final Observable<U> other;
  
  public OperatorWindowWithObservable(Observable<U> paramObservable)
  {
    this.other = paramObservable;
  }
  
  public Subscriber<? super T> call(Subscriber<? super Observable<T>> paramSubscriber)
  {
    SourceSubscriber localSourceSubscriber = new SourceSubscriber(paramSubscriber);
    BoundarySubscriber localBoundarySubscriber = new BoundarySubscriber(paramSubscriber, localSourceSubscriber);
    paramSubscriber.add(localSourceSubscriber);
    paramSubscriber.add(localBoundarySubscriber);
    localSourceSubscriber.replaceWindow();
    this.other.unsafeSubscribe(localBoundarySubscriber);
    return localSourceSubscriber;
  }
  
  static final class BoundarySubscriber<T, U>
    extends Subscriber<U>
  {
    final OperatorWindowWithObservable.SourceSubscriber<T> sub;
    
    public BoundarySubscriber(Subscriber<?> paramSubscriber, OperatorWindowWithObservable.SourceSubscriber<T> paramSourceSubscriber)
    {
      this.sub = paramSourceSubscriber;
    }
    
    public void onCompleted()
    {
      this.sub.onCompleted();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.sub.onError(paramThrowable);
    }
    
    public void onNext(U paramU)
    {
      this.sub.replaceWindow();
    }
    
    public void onStart()
    {
      request(Long.MAX_VALUE);
    }
  }
  
  static final class SourceSubscriber<T>
    extends Subscriber<T>
  {
    final Subscriber<? super Observable<T>> child;
    Observer<T> consumer;
    boolean emitting;
    final Object guard;
    Observable<T> producer;
    List<Object> queue;
    
    public SourceSubscriber(Subscriber<? super Observable<T>> paramSubscriber)
    {
      this.child = new SerializedSubscriber(paramSubscriber);
      this.guard = new Object();
    }
    
    void complete()
    {
      Observer localObserver = this.consumer;
      this.consumer = null;
      this.producer = null;
      if (localObserver != null) {
        localObserver.onCompleted();
      }
      this.child.onCompleted();
      unsubscribe();
    }
    
    void createNewWindow()
    {
      UnicastSubject localUnicastSubject = UnicastSubject.create();
      this.consumer = localUnicastSubject;
      this.producer = localUnicastSubject;
    }
    
    void drain(List<Object> paramList)
    {
      if (paramList == null) {}
      for (;;)
      {
        return;
        paramList = paramList.iterator();
        while (paramList.hasNext())
        {
          Object localObject = paramList.next();
          if (localObject == OperatorWindowWithObservable.NEXT_SUBJECT)
          {
            replaceSubject();
          }
          else
          {
            if (OperatorWindowWithObservable.nl.isError(localObject))
            {
              error(OperatorWindowWithObservable.nl.getError(localObject));
              return;
            }
            if (OperatorWindowWithObservable.nl.isCompleted(localObject))
            {
              complete();
              return;
            }
            emitValue(localObject);
          }
        }
      }
    }
    
    void emitValue(T paramT)
    {
      Observer localObserver = this.consumer;
      if (localObserver != null) {
        localObserver.onNext(paramT);
      }
    }
    
    void error(Throwable paramThrowable)
    {
      Observer localObserver = this.consumer;
      this.consumer = null;
      this.producer = null;
      if (localObserver != null) {
        localObserver.onError(paramThrowable);
      }
      this.child.onError(paramThrowable);
      unsubscribe();
    }
    
    public void onCompleted()
    {
      List localList;
      synchronized (this.guard)
      {
        if (this.emitting)
        {
          if (this.queue == null) {
            this.queue = new ArrayList();
          }
          this.queue.add(OperatorWindowWithObservable.nl.completed());
          return;
        }
        localList = this.queue;
        this.queue = null;
        this.emitting = true;
      }
      try
      {
        drain(localList);
        complete();
        return;
      }
      catch (Throwable localThrowable)
      {
        error(localThrowable);
      }
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
    
    public void onError(Throwable paramThrowable)
    {
      synchronized (this.guard)
      {
        if (this.emitting)
        {
          this.queue = Collections.singletonList(OperatorWindowWithObservable.nl.error(paramThrowable));
          return;
        }
        this.queue = null;
        this.emitting = true;
        error(paramThrowable);
        return;
      }
    }
    
    public void onNext(T arg1)
    {
      for (;;)
      {
        List localList;
        int k;
        int n;
        int m;
        int i;
        synchronized (this.guard)
        {
          if (this.emitting)
          {
            if (this.queue == null) {
              this.queue = new ArrayList();
            }
            this.queue.add(???);
            return;
          }
          localList = this.queue;
          this.queue = null;
          this.emitting = true;
          k = 1;
          n = 0;
          m = 0;
          i = n;
        }
        try
        {
          drain(localList);
          int j = k;
          if (k != 0)
          {
            j = 0;
            i = n;
            emitValue(???);
          }
          i = n;
          ??? = this.guard;
          i = n;
          i = m;
          try
          {
            localList = this.queue;
            i = m;
            this.queue = null;
            if (localList == null)
            {
              i = m;
              this.emitting = false;
              i = 1;
              if (1 == 0)
              {
                synchronized (this.guard)
                {
                  this.emitting = false;
                  return;
                }
                ??? = finally;
                throw ???;
              }
            }
            else
            {
              i = m;
              i = n;
              boolean bool = this.child.isUnsubscribed();
              k = j;
              if (!bool) {
                continue;
              }
              if (0 == 0)
              {
                synchronized (this.guard)
                {
                  this.emitting = false;
                  return;
                }
                localObject3 = finally;
              }
            }
          }
          finally {}
          return;
        }
        finally
        {
          if (i == 0) {}
          synchronized (this.guard)
          {
            this.emitting = false;
            throw ((Throwable)localObject3);
          }
        }
      }
    }
    
    public void onStart()
    {
      request(Long.MAX_VALUE);
    }
    
    void replaceSubject()
    {
      Observer localObserver = this.consumer;
      if (localObserver != null) {
        localObserver.onCompleted();
      }
      createNewWindow();
      this.child.onNext(this.producer);
    }
    
    void replaceWindow()
    {
      for (;;)
      {
        int k;
        int n;
        int m;
        int i;
        synchronized (this.guard)
        {
          if (this.emitting)
          {
            if (this.queue == null) {
              this.queue = new ArrayList();
            }
            this.queue.add(OperatorWindowWithObservable.NEXT_SUBJECT);
            return;
          }
          ??? = this.queue;
          this.queue = null;
          this.emitting = true;
          k = 1;
          n = 0;
          m = 0;
          i = n;
        }
        try
        {
          drain((List)???);
          int j = k;
          if (k != 0)
          {
            j = 0;
            i = n;
            replaceSubject();
          }
          i = n;
          ??? = this.guard;
          i = n;
          i = m;
          try
          {
            ??? = this.queue;
            i = m;
            this.queue = null;
            if (??? == null)
            {
              i = m;
              this.emitting = false;
              i = 1;
              if (1 == 0)
              {
                synchronized (this.guard)
                {
                  this.emitting = false;
                  return;
                }
                localObject2 = finally;
                throw ((Throwable)localObject2);
              }
            }
            else
            {
              i = m;
              i = n;
              boolean bool = this.child.isUnsubscribed();
              k = j;
              if (!bool) {
                continue;
              }
              if (0 == 0)
              {
                synchronized (this.guard)
                {
                  this.emitting = false;
                  return;
                }
                localObject9 = finally;
              }
            }
          }
          finally {}
          return;
        }
        finally
        {
          if (i == 0) {}
          synchronized (this.guard)
          {
            this.emitting = false;
            throw ((Throwable)localObject9);
          }
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorWindowWithObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */