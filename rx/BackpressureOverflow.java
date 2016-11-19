package rx;

import rx.annotations.Experimental;
import rx.exceptions.MissingBackpressureException;

@Experimental
public final class BackpressureOverflow
{
  public static final Strategy ON_OVERFLOW_DEFAULT = Error.INSTANCE;
  public static final Strategy ON_OVERFLOW_DROP_LATEST = DropLatest.INSTANCE;
  public static final Strategy ON_OVERFLOW_DROP_OLDEST;
  public static final Strategy ON_OVERFLOW_ERROR = Error.INSTANCE;
  
  static
  {
    ON_OVERFLOW_DROP_OLDEST = DropOldest.INSTANCE;
  }
  
  static class DropLatest
    implements BackpressureOverflow.Strategy
  {
    static final DropLatest INSTANCE = new DropLatest();
    
    public boolean mayAttemptDrop()
    {
      return false;
    }
  }
  
  static class DropOldest
    implements BackpressureOverflow.Strategy
  {
    static final DropOldest INSTANCE = new DropOldest();
    
    public boolean mayAttemptDrop()
    {
      return true;
    }
  }
  
  static class Error
    implements BackpressureOverflow.Strategy
  {
    static final Error INSTANCE = new Error();
    
    public boolean mayAttemptDrop()
      throws MissingBackpressureException
    {
      throw new MissingBackpressureException("Overflowed buffer");
    }
  }
  
  public static abstract interface Strategy
  {
    public abstract boolean mayAttemptDrop()
      throws MissingBackpressureException;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/BackpressureOverflow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */