package rx.internal.util.atomic;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import rx.internal.util.unsafe.Pow2;

public final class SpscExactAtomicArrayQueue<T>
  extends AtomicReferenceArray<T>
  implements Queue<T>
{
  static final AtomicLongFieldUpdater<SpscExactAtomicArrayQueue> CONSUMER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscExactAtomicArrayQueue.class, "consumerIndex");
  static final AtomicLongFieldUpdater<SpscExactAtomicArrayQueue> PRODUCER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscExactAtomicArrayQueue.class, "producerIndex");
  private static final long serialVersionUID = 6210984603741293445L;
  final int capacitySkip;
  volatile long consumerIndex;
  final int mask;
  volatile long producerIndex;
  
  public SpscExactAtomicArrayQueue(int paramInt)
  {
    super(Pow2.roundToPowerOfTwo(paramInt));
    int i = length();
    this.mask = (i - 1);
    this.capacitySkip = (i - paramInt);
  }
  
  public boolean add(T paramT)
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean addAll(Collection<? extends T> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public void clear()
  {
    while ((poll() != null) || (!isEmpty())) {}
  }
  
  public boolean contains(Object paramObject)
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public T element()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean isEmpty()
  {
    return this.producerIndex == this.consumerIndex;
  }
  
  public Iterator<T> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean offer(T paramT)
  {
    if (paramT == null) {
      throw new NullPointerException();
    }
    long l = this.producerIndex;
    int i = this.mask;
    if (get((int)(this.capacitySkip + l) & i) != null) {
      return false;
    }
    int j = (int)l;
    PRODUCER_INDEX.lazySet(this, 1L + l);
    lazySet(j & i, paramT);
    return true;
  }
  
  public T peek()
  {
    return (T)get((int)this.consumerIndex & this.mask);
  }
  
  public T poll()
  {
    long l = this.consumerIndex;
    int i = (int)l & this.mask;
    Object localObject = get(i);
    if (localObject == null) {
      return null;
    }
    CONSUMER_INDEX.lazySet(this, 1L + l);
    lazySet(i, null);
    return (T)localObject;
  }
  
  public T remove()
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean remove(Object paramObject)
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean removeAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public boolean retainAll(Collection<?> paramCollection)
  {
    throw new UnsupportedOperationException();
  }
  
  public int size()
  {
    long l2;
    for (long l1 = this.consumerIndex;; l1 = l2)
    {
      long l3 = this.producerIndex;
      l2 = this.consumerIndex;
      if (l1 == l2) {
        return (int)(l3 - l2);
      }
    }
  }
  
  public Object[] toArray()
  {
    throw new UnsupportedOperationException();
  }
  
  public <E> E[] toArray(E[] paramArrayOfE)
  {
    throw new UnsupportedOperationException();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/atomic/SpscExactAtomicArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */