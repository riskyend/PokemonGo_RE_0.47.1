package rx.observers;

import rx.Observer;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorThrowable;
import rx.internal.operators.NotificationLite;

public class SerializedObserver<T>
  implements Observer<T>
{
  private static final int MAX_DRAIN_ITERATION = 1024;
  private final Observer<? super T> actual;
  private boolean emitting;
  private final NotificationLite<T> nl = NotificationLite.instance();
  private FastList queue;
  private volatile boolean terminated;
  
  public SerializedObserver(Observer<? super T> paramObserver)
  {
    this.actual = paramObserver;
  }
  
  public void onCompleted()
  {
    if (this.terminated) {
      return;
    }
    try
    {
      if (this.terminated) {
        return;
      }
    }
    finally {}
    this.terminated = true;
    if (this.emitting)
    {
      FastList localFastList = this.queue;
      Object localObject2 = localFastList;
      if (localFastList == null)
      {
        localObject2 = new FastList();
        this.queue = ((FastList)localObject2);
      }
      ((FastList)localObject2).add(this.nl.completed());
      return;
    }
    this.emitting = true;
    this.actual.onCompleted();
  }
  
  public void onError(Throwable paramThrowable)
  {
    Exceptions.throwIfFatal(paramThrowable);
    if (this.terminated) {
      return;
    }
    try
    {
      if (this.terminated) {
        return;
      }
    }
    finally {}
    this.terminated = true;
    if (this.emitting)
    {
      FastList localFastList2 = this.queue;
      FastList localFastList1 = localFastList2;
      if (localFastList2 == null)
      {
        localFastList1 = new FastList();
        this.queue = localFastList1;
      }
      localFastList1.add(this.nl.error(paramThrowable));
      return;
    }
    this.emitting = true;
    this.actual.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    if (this.terminated) {
      return;
    }
    try
    {
      if (this.terminated) {
        return;
      }
    }
    finally {}
    FastList localFastList2;
    FastList localFastList1;
    if (this.emitting)
    {
      localFastList2 = this.queue;
      localFastList1 = localFastList2;
      if (localFastList2 == null)
      {
        localFastList1 = new FastList();
        this.queue = localFastList1;
      }
      localFastList1.add(this.nl.next(paramT));
      return;
    }
    this.emitting = true;
    int i;
    try
    {
      this.actual.onNext(paramT);
      do
      {
        i = 0;
      } while (i >= 1024);
      try
      {
        localFastList1 = this.queue;
        if (localFastList1 == null)
        {
          this.emitting = false;
          return;
        }
      }
      finally {}
      this.queue = null;
    }
    catch (Throwable localThrowable1)
    {
      this.terminated = true;
      Exceptions.throwOrReport(localThrowable1, this.actual, paramT);
      return;
    }
    Object[] arrayOfObject = localThrowable1.array;
    int k = arrayOfObject.length;
    int j = 0;
    for (;;)
    {
      if (j < k)
      {
        localFastList2 = arrayOfObject[j];
        if (localFastList2 != null) {}
      }
      else
      {
        i += 1;
        break;
      }
      try
      {
        if (this.nl.accept(this.actual, localFastList2))
        {
          this.terminated = true;
          return;
        }
      }
      catch (Throwable localThrowable2)
      {
        this.terminated = true;
        Exceptions.throwIfFatal(localThrowable2);
        this.actual.onError(OnErrorThrowable.addValueAsLastCause(localThrowable2, paramT));
        return;
      }
      j += 1;
    }
  }
  
  static final class FastList
  {
    Object[] array;
    int size;
    
    public void add(Object paramObject)
    {
      int i = this.size;
      Object[] arrayOfObject2 = this.array;
      Object[] arrayOfObject1;
      if (arrayOfObject2 == null)
      {
        arrayOfObject1 = new Object[16];
        this.array = arrayOfObject1;
      }
      for (;;)
      {
        arrayOfObject1[i] = paramObject;
        this.size = (i + 1);
        return;
        arrayOfObject1 = arrayOfObject2;
        if (i == arrayOfObject2.length)
        {
          arrayOfObject1 = new Object[(i >> 2) + i];
          System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, i);
          this.array = arrayOfObject1;
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/observers/SerializedObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */