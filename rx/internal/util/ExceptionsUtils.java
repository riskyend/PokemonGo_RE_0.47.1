package rx.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import rx.exceptions.CompositeException;

public enum ExceptionsUtils
{
  private static final Throwable TERMINATED = new Throwable("Terminated");
  
  private ExceptionsUtils() {}
  
  public static boolean addThrowable(AtomicReference<Throwable> paramAtomicReference, Throwable paramThrowable)
  {
    Throwable localThrowable = (Throwable)paramAtomicReference.get();
    if (localThrowable == TERMINATED) {
      return false;
    }
    Object localObject;
    if (localThrowable == null) {
      localObject = paramThrowable;
    }
    while (paramAtomicReference.compareAndSet(localThrowable, localObject))
    {
      return true;
      if ((localThrowable instanceof CompositeException))
      {
        localObject = new ArrayList(((CompositeException)localThrowable).getExceptions());
        ((List)localObject).add(paramThrowable);
        localObject = new CompositeException((Collection)localObject);
      }
      else
      {
        localObject = new CompositeException(new Throwable[] { localThrowable, paramThrowable });
      }
    }
  }
  
  public static boolean isTerminated(Throwable paramThrowable)
  {
    return paramThrowable == TERMINATED;
  }
  
  public static boolean isTerminated(AtomicReference<Throwable> paramAtomicReference)
  {
    return isTerminated((Throwable)paramAtomicReference.get());
  }
  
  public static Throwable terminate(AtomicReference<Throwable> paramAtomicReference)
  {
    Throwable localThrowable2 = (Throwable)paramAtomicReference.get();
    Throwable localThrowable1 = localThrowable2;
    if (localThrowable2 != TERMINATED) {
      localThrowable1 = (Throwable)paramAtomicReference.getAndSet(TERMINATED);
    }
    return localThrowable1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/ExceptionsUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */