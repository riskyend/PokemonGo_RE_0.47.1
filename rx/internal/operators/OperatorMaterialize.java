package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import rx.Notification;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

public final class OperatorMaterialize<T>
  implements Observable.Operator<Notification<T>, T>
{
  public static <T> OperatorMaterialize<T> instance()
  {
    return Holder.INSTANCE;
  }
  
  public Subscriber<? super T> call(Subscriber<? super Notification<T>> paramSubscriber)
  {
    final ParentSubscriber localParentSubscriber = new ParentSubscriber(paramSubscriber);
    paramSubscriber.add(localParentSubscriber);
    paramSubscriber.setProducer(new Producer()
    {
      public void request(long paramAnonymousLong)
      {
        if (paramAnonymousLong > 0L) {
          localParentSubscriber.requestMore(paramAnonymousLong);
        }
      }
    });
    return localParentSubscriber;
  }
  
  private static final class Holder
  {
    static final OperatorMaterialize<Object> INSTANCE = new OperatorMaterialize();
  }
  
  private static class ParentSubscriber<T>
    extends Subscriber<T>
  {
    private boolean busy = false;
    private final Subscriber<? super Notification<T>> child;
    private boolean missed = false;
    private final AtomicLong requested = new AtomicLong();
    private volatile Notification<T> terminalNotification;
    
    ParentSubscriber(Subscriber<? super Notification<T>> paramSubscriber)
    {
      this.child = paramSubscriber;
    }
    
    private void decrementRequested()
    {
      AtomicLong localAtomicLong = this.requested;
      long l;
      do
      {
        l = localAtomicLong.get();
        if (l == Long.MAX_VALUE) {
          return;
        }
      } while (!localAtomicLong.compareAndSet(l, l - 1L));
    }
    
    private void drain()
    {
      for (;;)
      {
        try
        {
          if (this.busy)
          {
            this.missed = true;
            return;
          }
          AtomicLong localAtomicLong = this.requested;
          if (this.child.isUnsubscribed()) {
            break;
          }
          Notification localNotification = this.terminalNotification;
          if ((localNotification != null) && (localAtomicLong.get() > 0L))
          {
            this.terminalNotification = null;
            this.child.onNext(localNotification);
            if (this.child.isUnsubscribed()) {
              break;
            }
            this.child.onCompleted();
            return;
          }
        }
        finally {}
        try
        {
          if (!this.missed)
          {
            this.busy = false;
            return;
          }
        }
        finally {}
      }
    }
    
    public void onCompleted()
    {
      this.terminalNotification = Notification.createOnCompleted();
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.terminalNotification = Notification.createOnError(paramThrowable);
      RxJavaPlugins.getInstance().getErrorHandler().handleError(paramThrowable);
      drain();
    }
    
    public void onNext(T paramT)
    {
      this.child.onNext(Notification.createOnNext(paramT));
      decrementRequested();
    }
    
    public void onStart()
    {
      request(0L);
    }
    
    void requestMore(long paramLong)
    {
      BackpressureUtils.getAndAddRequest(this.requested, paramLong);
      request(paramLong);
      drain();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorMaterialize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */