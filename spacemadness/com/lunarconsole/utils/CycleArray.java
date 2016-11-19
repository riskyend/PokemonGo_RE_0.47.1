package spacemadness.com.lunarconsole.utils;

import java.lang.reflect.Array;
import java.util.Iterator;

public class CycleArray<E>
  implements Iterable<E>
{
  private final Class<? extends E> componentType;
  private int headIndex;
  private E[] internalArray;
  private int length;
  
  public CycleArray(Class<? extends E> paramClass, int paramInt)
  {
    if (paramClass == null) {
      throw new NullPointerException("Component type is null");
    }
    this.componentType = paramClass;
    this.internalArray = ((Object[])Array.newInstance(paramClass, paramInt));
  }
  
  private int toArrayIndex(E[] paramArrayOfE, int paramInt)
  {
    return paramInt % paramArrayOfE.length;
  }
  
  public E add(E paramE)
  {
    int i = toArrayIndex(this.length);
    Object localObject = this.internalArray[i];
    this.internalArray[i] = paramE;
    this.length += 1;
    if (this.length - this.headIndex > this.internalArray.length)
    {
      this.headIndex += 1;
      return (E)localObject;
    }
    return null;
  }
  
  public void clear()
  {
    int i = 0;
    while (i < this.internalArray.length)
    {
      this.internalArray[i] = null;
      i += 1;
    }
    this.length = 0;
    this.headIndex = 0;
  }
  
  public boolean contains(Object paramObject)
  {
    int i = this.headIndex;
    while (i < this.length)
    {
      int j = toArrayIndex(i);
      if (ObjectUtils.areEqual(this.internalArray[j], paramObject)) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public E get(int paramInt)
  {
    paramInt = toArrayIndex(paramInt);
    return (E)this.internalArray[paramInt];
  }
  
  public int getCapacity()
  {
    return this.internalArray.length;
  }
  
  public int getHeadIndex()
  {
    return this.headIndex;
  }
  
  public E[] internalArray()
  {
    return this.internalArray;
  }
  
  public boolean isValidIndex(int paramInt)
  {
    return (paramInt >= this.headIndex) && (paramInt < this.length);
  }
  
  public Iterator<E> iterator()
  {
    return new CycleIterator();
  }
  
  public int length()
  {
    return this.length;
  }
  
  public int realLength()
  {
    return this.length - this.headIndex;
  }
  
  public void set(int paramInt, E paramE)
  {
    paramInt = toArrayIndex(paramInt);
    this.internalArray[paramInt] = paramE;
  }
  
  public void setCapacity(int paramInt)
  {
    if (paramInt > getCapacity())
    {
      arrayOfObject = (Object[])Array.newInstance(this.componentType, paramInt);
      j = realLength();
      i = toArrayIndex(this.internalArray, this.headIndex);
      for (paramInt = toArrayIndex(arrayOfObject, this.headIndex); j > 0; paramInt = toArrayIndex(arrayOfObject, paramInt + k))
      {
        k = Math.min(j, Math.min(this.internalArray.length - i, arrayOfObject.length - paramInt));
        System.arraycopy(this.internalArray, i, arrayOfObject, paramInt, k);
        j -= k;
        i = toArrayIndex(this.internalArray, i + k);
      }
      this.internalArray = arrayOfObject;
    }
    while (paramInt >= getCapacity())
    {
      Object[] arrayOfObject;
      int j;
      int i;
      int k;
      return;
    }
    throw new NotImplementedException();
  }
  
  public int toArrayIndex(int paramInt)
  {
    return paramInt % this.internalArray.length;
  }
  
  public void trimHeadIndex(int paramInt)
  {
    trimToHeadIndex(this.headIndex + paramInt);
  }
  
  public void trimLength(int paramInt)
  {
    trimToLength(this.length - paramInt);
  }
  
  public void trimToHeadIndex(int paramInt)
  {
    if ((paramInt < this.headIndex) || (paramInt > this.length)) {
      throw new IllegalArgumentException("Trimmed head index " + paramInt + " should be between head index " + this.headIndex + " and length " + this.length);
    }
    this.headIndex = paramInt;
  }
  
  public void trimToLength(int paramInt)
  {
    if ((paramInt < this.headIndex) || (paramInt > this.length)) {
      throw new IllegalArgumentException("Trimmed length " + paramInt + " should be between head index " + this.headIndex + " and length " + this.length);
    }
    this.length = paramInt;
  }
  
  private class CycleIterator
    implements Iterator<E>
  {
    private int index = CycleArray.this.getHeadIndex();
    
    public CycleIterator() {}
    
    public boolean hasNext()
    {
      return this.index < CycleArray.this.length();
    }
    
    public E next()
    {
      CycleArray localCycleArray = CycleArray.this;
      int i = this.index;
      this.index = (i + 1);
      return (E)localCycleArray.get(i);
    }
    
    public void remove()
    {
      throw new NotImplementedException();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/utils/CycleArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */