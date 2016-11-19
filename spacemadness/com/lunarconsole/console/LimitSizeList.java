package spacemadness.com.lunarconsole.console;

import java.util.Iterator;
import spacemadness.com.lunarconsole.utils.CycleArray;

public class LimitSizeList<T>
  implements Iterable<T>
{
  private final CycleArray<T> internalArray;
  private final int trimSize;
  
  public LimitSizeList(Class<? extends T> paramClass, int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0) {
      throw new IllegalArgumentException("Illegal capacity: " + paramInt1);
    }
    this.internalArray = new CycleArray(paramClass, paramInt1);
    this.trimSize = paramInt2;
  }
  
  public void addObject(T paramT)
  {
    if (willOverflow()) {
      trimHead(this.trimSize);
    }
    this.internalArray.add(paramT);
  }
  
  public int capacity()
  {
    return this.internalArray.getCapacity();
  }
  
  public void clear()
  {
    this.internalArray.clear();
  }
  
  public int count()
  {
    return this.internalArray.realLength();
  }
  
  public int getTrimSize()
  {
    return this.trimSize;
  }
  
  public boolean isOverfloating()
  {
    return (this.internalArray.getHeadIndex() > 0) && (willOverflow());
  }
  
  public boolean isTrimmed()
  {
    return trimmedCount() > 0;
  }
  
  public Iterator<T> iterator()
  {
    return this.internalArray.iterator();
  }
  
  public T objectAtIndex(int paramInt)
  {
    return (T)this.internalArray.get(this.internalArray.getHeadIndex() + paramInt);
  }
  
  public int overflowCount()
  {
    return this.internalArray.getHeadIndex();
  }
  
  public int totalCount()
  {
    return this.internalArray.length();
  }
  
  public void trimHead(int paramInt)
  {
    this.internalArray.trimHeadIndex(paramInt);
  }
  
  public int trimmedCount()
  {
    return totalCount() - count();
  }
  
  public boolean willOverflow()
  {
    return this.internalArray.realLength() == this.internalArray.getCapacity();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/LimitSizeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */