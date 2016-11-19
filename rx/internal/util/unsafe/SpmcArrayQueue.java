package rx.internal.util.unsafe;

public final class SpmcArrayQueue<E>
  extends SpmcArrayQueueL3Pad<E>
{
  public SpmcArrayQueue(int paramInt)
  {
    super(paramInt);
  }
  
  public boolean isEmpty()
  {
    return lvConsumerIndex() == lvProducerIndex();
  }
  
  public boolean offer(E paramE)
  {
    if (paramE == null) {
      throw new NullPointerException("Null is not a valid element");
    }
    Object[] arrayOfObject = this.buffer;
    long l1 = this.mask;
    long l2 = lvProducerIndex();
    long l3 = calcElementOffset(l2);
    if (lvElement(arrayOfObject, l3) != null)
    {
      if (l2 - lvConsumerIndex() > l1) {
        return false;
      }
      while (lvElement(arrayOfObject, l3) != null) {}
    }
    spElement(arrayOfObject, l3, paramE);
    soTail(1L + l2);
    return true;
  }
  
  public E peek()
  {
    long l1 = lvProducerIndexCache();
    Object localObject;
    do
    {
      long l2 = lvConsumerIndex();
      if (l2 >= l1)
      {
        long l3 = lvProducerIndex();
        if (l2 >= l3) {
          return null;
        }
        svProducerIndexCache(l3);
      }
      localObject = lvElement(calcElementOffset(l2));
    } while (localObject == null);
    return (E)localObject;
  }
  
  public E poll()
  {
    long l1 = lvProducerIndexCache();
    long l2;
    do
    {
      l2 = lvConsumerIndex();
      if (l2 >= l1)
      {
        long l3 = lvProducerIndex();
        if (l2 >= l3) {
          return null;
        }
        svProducerIndexCache(l3);
      }
    } while (!casHead(l2, 1L + l2));
    l1 = calcElementOffset(l2);
    Object[] arrayOfObject = this.buffer;
    Object localObject = lpElement(arrayOfObject, l1);
    soElement(arrayOfObject, l1, null);
    return (E)localObject;
  }
  
  public int size()
  {
    long l1 = lvConsumerIndex();
    long l2;
    long l4;
    long l3;
    do
    {
      l2 = l1;
      l4 = lvProducerIndex();
      l3 = lvConsumerIndex();
      l1 = l3;
    } while (l2 != l3);
    return (int)(l4 - l3);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/SpmcArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */