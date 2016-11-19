package rx.internal.util.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscAtomicArrayQueue<E>
  extends AtomicReferenceArrayQueue<E>
{
  private static final Integer MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
  final AtomicLong consumerIndex = new AtomicLong();
  final int lookAheadStep;
  final AtomicLong producerIndex = new AtomicLong();
  protected long producerLookAhead;
  
  public SpscAtomicArrayQueue(int paramInt)
  {
    super(paramInt);
    this.lookAheadStep = Math.min(paramInt / 4, MAX_LOOK_AHEAD_STEP.intValue());
  }
  
  private long lvConsumerIndex()
  {
    return this.consumerIndex.get();
  }
  
  private long lvProducerIndex()
  {
    return this.producerIndex.get();
  }
  
  private void soConsumerIndex(long paramLong)
  {
    this.consumerIndex.lazySet(paramLong);
  }
  
  private void soProducerIndex(long paramLong)
  {
    this.producerIndex.lazySet(paramLong);
  }
  
  public boolean isEmpty()
  {
    return lvProducerIndex() == lvConsumerIndex();
  }
  
  public boolean offer(E paramE)
  {
    if (paramE == null) {
      throw new NullPointerException("Null is not a valid element");
    }
    AtomicReferenceArray localAtomicReferenceArray = this.buffer;
    int i = this.mask;
    long l = this.producerIndex.get();
    int j = calcElementOffset(l, i);
    if (l >= this.producerLookAhead)
    {
      int k = this.lookAheadStep;
      if (lvElement(localAtomicReferenceArray, calcElementOffset(k + l, i)) != null) {
        break label106;
      }
      this.producerLookAhead = (k + l);
    }
    label106:
    while (lvElement(localAtomicReferenceArray, j) == null)
    {
      soProducerIndex(1L + l);
      soElement(localAtomicReferenceArray, j, paramE);
      return true;
    }
    return false;
  }
  
  public E peek()
  {
    return (E)lvElement(calcElementOffset(this.consumerIndex.get()));
  }
  
  public E poll()
  {
    long l = this.consumerIndex.get();
    int i = calcElementOffset(l);
    AtomicReferenceArray localAtomicReferenceArray = this.buffer;
    Object localObject = lvElement(localAtomicReferenceArray, i);
    if (localObject == null) {
      return null;
    }
    soConsumerIndex(1L + l);
    soElement(localAtomicReferenceArray, i, null);
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/atomic/SpscAtomicArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */