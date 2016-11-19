package rx.schedulers;

public class TimeInterval<T>
{
  private final long intervalInMilliseconds;
  private final T value;
  
  public TimeInterval(long paramLong, T paramT)
  {
    this.value = paramT;
    this.intervalInMilliseconds = paramLong;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        paramObject = (TimeInterval)paramObject;
        if (this.intervalInMilliseconds != ((TimeInterval)paramObject).intervalInMilliseconds) {
          return false;
        }
        if (this.value != null) {
          break;
        }
      } while (((TimeInterval)paramObject).value == null);
      return false;
    } while (this.value.equals(((TimeInterval)paramObject).value));
    return false;
  }
  
  public long getIntervalInMilliseconds()
  {
    return this.intervalInMilliseconds;
  }
  
  public T getValue()
  {
    return (T)this.value;
  }
  
  public int hashCode()
  {
    int j = (int)(this.intervalInMilliseconds ^ this.intervalInMilliseconds >>> 32);
    if (this.value == null) {}
    for (int i = 0;; i = this.value.hashCode()) {
      return (j + 31) * 31 + i;
    }
  }
  
  public String toString()
  {
    return "TimeInterval [intervalInMilliseconds=" + this.intervalInMilliseconds + ", value=" + this.value + "]";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/schedulers/TimeInterval.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */