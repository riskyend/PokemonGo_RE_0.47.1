package rx.internal.operators;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.observers.SerializedSubscriber;
import rx.observers.Subscribers;

public final class OperatorBufferWithSingleObservable<T, TClosing>
  implements Observable.Operator<List<T>, T>
{
  final Func0<? extends Observable<? extends TClosing>> bufferClosingSelector;
  final int initialCapacity;
  
  public OperatorBufferWithSingleObservable(final Observable<? extends TClosing> paramObservable, int paramInt)
  {
    this.bufferClosingSelector = new Func0()
    {
      public Observable<? extends TClosing> call()
      {
        return paramObservable;
      }
    };
    this.initialCapacity = paramInt;
  }
  
  public OperatorBufferWithSingleObservable(Func0<? extends Observable<? extends TClosing>> paramFunc0, int paramInt)
  {
    this.bufferClosingSelector = paramFunc0;
    this.initialCapacity = paramInt;
  }
  
  public Subscriber<? super T> call(Subscriber<? super List<T>> paramSubscriber)
  {
    try
    {
      Observable localObservable = (Observable)this.bufferClosingSelector.call();
      final BufferingSubscriber localBufferingSubscriber = new BufferingSubscriber(new SerializedSubscriber(paramSubscriber));
      Subscriber local2 = new Subscriber()
      {
        public void onCompleted()
        {
          localBufferingSubscriber.onCompleted();
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          localBufferingSubscriber.onError(paramAnonymousThrowable);
        }
        
        public void onNext(TClosing paramAnonymousTClosing)
        {
          localBufferingSubscriber.emit();
        }
      };
      paramSubscriber.add(local2);
      paramSubscriber.add(localBufferingSubscriber);
      localObservable.unsafeSubscribe(local2);
      return localBufferingSubscriber;
    }
    catch (Throwable localThrowable)
    {
      Exceptions.throwOrReport(localThrowable, paramSubscriber);
    }
    return Subscribers.empty();
  }
  
  final class BufferingSubscriber
    extends Subscriber<T>
  {
    final Subscriber<? super List<T>> child;
    List<T> chunk;
    boolean done;
    
    public BufferingSubscriber()
    {
      Subscriber localSubscriber;
      this.child = localSubscriber;
      this.chunk = new ArrayList(OperatorBufferWithSingleObservable.this.initialCapacity);
    }
    
    void emit()
    {
      try
      {
        if (this.done) {
          return;
        }
        List localList = this.chunk;
        this.chunk = new ArrayList(OperatorBufferWithSingleObservable.this.initialCapacity);
        try
        {
          this.child.onNext(localList);
          return;
        }
        catch (Throwable localThrowable1)
        {
          unsubscribe();
          try
          {
            if (this.done) {
              return;
            }
          }
          finally {}
        }
        this.done = true;
      }
      finally {}
      Exceptions.throwOrReport(localThrowable2, this.child);
    }
    
    public void onCompleted()
    {
      try
      {
        try
        {
          if (this.done) {
            return;
          }
          this.done = true;
          List localList = this.chunk;
          this.chunk = null;
          this.child.onNext(localList);
          this.child.onCompleted();
          unsubscribe();
          return;
        }
        finally {}
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwOrReport(localThrowable, this.child);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        if (this.done) {
          return;
        }
        this.done = true;
        this.chunk = null;
        this.child.onError(paramThrowable);
        unsubscribe();
        return;
      }
      finally {}
    }
    
    public void onNext(T paramT)
    {
      try
      {
        if (this.done) {
          return;
        }
        this.chunk.add(paramT);
        return;
      }
      finally {}
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorBufferWithSingleObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */