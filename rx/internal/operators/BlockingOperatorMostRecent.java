package rx.internal.operators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public final class BlockingOperatorMostRecent
{
  private BlockingOperatorMostRecent()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static <T> Iterable<T> mostRecent(final Observable<? extends T> paramObservable, T paramT)
  {
    new Iterable()
    {
      public Iterator<T> iterator()
      {
        BlockingOperatorMostRecent.MostRecentObserver localMostRecentObserver = new BlockingOperatorMostRecent.MostRecentObserver(this.val$initialValue);
        paramObservable.subscribe(localMostRecentObserver);
        return localMostRecentObserver.getIterable();
      }
    };
  }
  
  private static final class MostRecentObserver<T>
    extends Subscriber<T>
  {
    final NotificationLite<T> nl = NotificationLite.instance();
    volatile Object value = this.nl.next(paramT);
    
    MostRecentObserver(T paramT) {}
    
    public Iterator<T> getIterable()
    {
      new Iterator()
      {
        private Object buf = null;
        
        public boolean hasNext()
        {
          this.buf = BlockingOperatorMostRecent.MostRecentObserver.this.value;
          return !BlockingOperatorMostRecent.MostRecentObserver.this.nl.isCompleted(this.buf);
        }
        
        public T next()
        {
          try
          {
            if (this.buf == null) {
              this.buf = BlockingOperatorMostRecent.MostRecentObserver.this.value;
            }
            if (BlockingOperatorMostRecent.MostRecentObserver.this.nl.isCompleted(this.buf)) {
              throw new NoSuchElementException();
            }
          }
          finally
          {
            this.buf = null;
          }
          if (BlockingOperatorMostRecent.MostRecentObserver.this.nl.isError(this.buf)) {
            throw Exceptions.propagate(BlockingOperatorMostRecent.MostRecentObserver.this.nl.getError(this.buf));
          }
          Object localObject2 = BlockingOperatorMostRecent.MostRecentObserver.this.nl.getValue(this.buf);
          this.buf = null;
          return (T)localObject2;
        }
        
        public void remove()
        {
          throw new UnsupportedOperationException("Read only iterator");
        }
      };
    }
    
    public void onCompleted()
    {
      this.value = this.nl.completed();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.value = this.nl.error(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.value = this.nl.next(paramT);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/BlockingOperatorMostRecent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */