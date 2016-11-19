package rx.internal.util;

import java.util.ArrayList;
import java.util.List;

public class LinkedArrayList
{
  final int capacityHint;
  Object[] head;
  int indexInTail;
  volatile int size;
  Object[] tail;
  
  public LinkedArrayList(int paramInt)
  {
    this.capacityHint = paramInt;
  }
  
  public void add(Object paramObject)
  {
    if (this.size == 0)
    {
      this.head = new Object[this.capacityHint + 1];
      this.tail = this.head;
      this.head[0] = paramObject;
      this.indexInTail = 1;
      this.size = 1;
      return;
    }
    if (this.indexInTail == this.capacityHint)
    {
      Object[] arrayOfObject = new Object[this.capacityHint + 1];
      arrayOfObject[0] = paramObject;
      this.tail[this.capacityHint] = arrayOfObject;
      this.tail = arrayOfObject;
      this.indexInTail = 1;
      this.size += 1;
      return;
    }
    this.tail[this.indexInTail] = paramObject;
    this.indexInTail += 1;
    this.size += 1;
  }
  
  public int capacityHint()
  {
    return this.capacityHint;
  }
  
  public Object[] head()
  {
    return this.head;
  }
  
  public int indexInTail()
  {
    return this.indexInTail;
  }
  
  public int size()
  {
    return this.size;
  }
  
  public Object[] tail()
  {
    return this.tail;
  }
  
  List<Object> toList()
  {
    int n = this.capacityHint;
    int i1 = this.size;
    ArrayList localArrayList = new ArrayList(i1 + 1);
    Object[] arrayOfObject = head();
    int j = 0;
    int i = 0;
    while (j < i1)
    {
      localArrayList.add(arrayOfObject[i]);
      int k = j + 1;
      int m = i + 1;
      j = k;
      i = m;
      if (m == n)
      {
        i = 0;
        arrayOfObject = (Object[])arrayOfObject[n];
        j = k;
      }
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return toList().toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/LinkedArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */