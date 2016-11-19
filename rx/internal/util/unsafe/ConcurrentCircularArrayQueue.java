package rx.internal.util.unsafe;

import java.util.Iterator;
import sun.misc.Unsafe;

public abstract class ConcurrentCircularArrayQueue<E>
  extends ConcurrentCircularArrayQueueL0Pad<E>
{
  protected static final int BUFFER_PAD = 32;
  private static final long REF_ARRAY_BASE;
  private static final int REF_ELEMENT_SHIFT;
  protected static final int SPARSE_SHIFT = Integer.getInteger("sparse.shift", 0).intValue();
  protected final E[] buffer;
  protected final long mask;
  
  static
  {
    int i = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
    if (4 == i) {}
    for (REF_ELEMENT_SHIFT = SPARSE_SHIFT + 2;; REF_ELEMENT_SHIFT = SPARSE_SHIFT + 3)
    {
      REF_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class) + (32 << REF_ELEMENT_SHIFT - SPARSE_SHIFT);
      return;
      if (8 != i) {
        break;
      }
    }
    throw new IllegalStateException("Unknown pointer size");
  }
  
  public ConcurrentCircularArrayQueue(int paramInt)
  {
    paramInt = Pow2.roundToPowerOfTwo(paramInt);
    this.mask = (paramInt - 1);
    this.buffer = ((Object[])new Object[(paramInt << SPARSE_SHIFT) + 64]);
  }
  
  protected final long calcElementOffset(long paramLong)
  {
    return calcElementOffset(paramLong, this.mask);
  }
  
  protected final long calcElementOffset(long paramLong1, long paramLong2)
  {
    return REF_ARRAY_BASE + ((paramLong1 & paramLong2) << REF_ELEMENT_SHIFT);
  }
  
  public void clear()
  {
    while ((poll() != null) || (!isEmpty())) {}
  }
  
  public Iterator<E> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  protected final E lpElement(long paramLong)
  {
    return (E)lpElement(this.buffer, paramLong);
  }
  
  protected final E lpElement(E[] paramArrayOfE, long paramLong)
  {
    return (E)UnsafeAccess.UNSAFE.getObject(paramArrayOfE, paramLong);
  }
  
  protected final E lvElement(long paramLong)
  {
    return (E)lvElement(this.buffer, paramLong);
  }
  
  protected final E lvElement(E[] paramArrayOfE, long paramLong)
  {
    return (E)UnsafeAccess.UNSAFE.getObjectVolatile(paramArrayOfE, paramLong);
  }
  
  protected final void soElement(long paramLong, E paramE)
  {
    soElement(this.buffer, paramLong, paramE);
  }
  
  protected final void soElement(E[] paramArrayOfE, long paramLong, E paramE)
  {
    UnsafeAccess.UNSAFE.putOrderedObject(paramArrayOfE, paramLong, paramE);
  }
  
  protected final void spElement(long paramLong, E paramE)
  {
    spElement(this.buffer, paramLong, paramE);
  }
  
  protected final void spElement(E[] paramArrayOfE, long paramLong, E paramE)
  {
    UnsafeAccess.UNSAFE.putObject(paramArrayOfE, paramLong, paramE);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/ConcurrentCircularArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */