package rx.schedulers;

public final class Timestamped<T>
{
  private final long timestampMillis;
  private final T value;
  
  public Timestamped(long paramLong, T paramT)
  {
    this.value = paramT;
    this.timestampMillis = paramLong;
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
        if (!(paramObject instanceof Timestamped)) {
          return false;
        }
        paramObject = (Timestamped)paramObject;
        if (this.timestampMillis != ((Timestamped)paramObject).timestampMillis) {
          return false;
        }
        if (this.value != null) {
          break;
        }
      } while (((Timestamped)paramObject).value == null);
      return false;
    } while (this.value.equals(((Timestamped)paramObject).value));
    return false;
  }
  
  public long getTimestampMillis()
  {
    return this.timestampMillis;
  }
  
  public T getValue()
  {
    return (T)this.value;
  }
  
  public int hashCode()
  {
    int j = (int)(this.timestampMillis ^ this.timestampMillis >>> 32);
    if (this.value == null) {}
    for (int i = 0;; i = this.value.hashCode()) {
      return (j + 31) * 31 + i;
    }
  }
  
  public String toString()
  {
    return String.format("Timestamped(timestampMillis = %d, value = %s)", new Object[] { Long.valueOf(this.timestampMillis), this.value.toString() });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/schedulers/Timestamped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */