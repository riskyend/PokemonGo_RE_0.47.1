package rx.internal.util.atomic;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import rx.internal.util.unsafe.Pow2;

abstract class AtomicReferenceArrayQueue<E>
  extends AbstractQueue<E>
{
  protected final AtomicReferenceArray<E> buffer;
  protected final int mask;
  
  public AtomicReferenceArrayQueue(int paramInt)
  {
    paramInt = Pow2.roundToPowerOfTwo(paramInt);
    this.mask = (paramInt - 1);
    this.buffer = new AtomicReferenceArray(paramInt);
  }
  
  protected final int calcElementOffset(long paramLong)
  {
    return (int)paramLong & this.mask;
  }
  
  protected final int calcElementOffset(long paramLong, int paramInt)
  {
    return (int)paramLong & paramInt;
  }
  
  public void clear()
  {
    while ((poll() != null) || (!isEmpty())) {}
  }
  
  public Iterator<E> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  protected final E lpElement(int paramInt)
  {
    return (E)this.buffer.get(paramInt);
  }
  
  protected final E lpElement(AtomicReferenceArray<E> paramAtomicReferenceArray, int paramInt)
  {
    return (E)paramAtomicReferenceArray.get(paramInt);
  }
  
  protected final E lvElement(int paramInt)
  {
    return (E)lvElement(this.buffer, paramInt);
  }
  
  protected final E lvElement(AtomicReferenceArray<E> paramAtomicReferenceArray, int paramInt)
  {
    return (E)paramAtomicReferenceArray.get(paramInt);
  }
  
  protected final void soElement(int paramInt, E paramE)
  {
    this.buffer.lazySet(paramInt, paramE);
  }
  
  protected final void soElement(AtomicReferenceArray<E> paramAtomicReferenceArray, int paramInt, E paramE)
  {
    paramAtomicReferenceArray.lazySet(paramInt, paramE);
  }
  
  protected final void spElement(int paramInt, E paramE)
  {
    this.buffer.lazySet(paramInt, paramE);
  }
  
  protected final void spElement(AtomicReferenceArray<E> paramAtomicReferenceArray, int paramInt, E paramE)
  {
    paramAtomicReferenceArray.lazySet(paramInt, paramE);
  }
  
  protected final void svElement(AtomicReferenceArray<E> paramAtomicReferenceArray, int paramInt, E paramE)
  {
    paramAtomicReferenceArray.set(paramInt, paramE);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/atomic/AtomicReferenceArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */