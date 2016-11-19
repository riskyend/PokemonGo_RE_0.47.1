package rx.internal.util.atomic;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import rx.internal.util.unsafe.Pow2;

public final class SpscLinkedArrayQueue<T>
  implements Queue<T>
{
  private static final Object HAS_NEXT = new Object();
  static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
  protected AtomicReferenceArray<Object> consumerBuffer;
  protected final AtomicLong consumerIndex;
  protected int consumerMask;
  protected AtomicReferenceArray<Object> producerBuffer;
  protected final AtomicLong producerIndex;
  protected long producerLookAhead;
  protected int producerLookAheadStep;
  protected int producerMask;
  
  public SpscLinkedArrayQueue(int paramInt)
  {
    paramInt = Pow2.roundToPowerOfTwo(paramInt);
    int i = paramInt - 1;
    AtomicReferenceArray localAtomicReferenceArray = new AtomicReferenceArray(paramInt + 1);
    this.producerBuffer = localAtomicReferenceArray;
    this.producerMask = i;
    adjustLookAheadStep(paramInt);
    this.consumerBuffer = localAtomicReferenceArray;
    this.consumerMask = i;
    this.producerLookAhead = (i - 1);
    this.producerIndex = new AtomicLong();
    this.consumerIndex = new AtomicLong();
  }
  
  private void adjustLookAheadStep(int paramInt)
  {
    this.producerLookAheadStep = Math.min(paramInt / 4, MAX_LOOK_AHEAD_STEP);
  }
  
  private static int calcDirectOffset(int paramInt)
  {
    return paramInt;
  }
  
  private static int calcWrappedOffset(long paramLong, int paramInt)
  {
    return calcDirectOffset((int)paramLong & paramInt);
  }
  
  private long lpConsumerIndex()
  {
    return this.consumerIndex.get();
  }
  
  private long lpProducerIndex()
  {
    return this.producerIndex.get();
  }
  
  private long lvConsumerIndex()
  {
    return this.consumerIndex.get();
  }
  
  private static <E> Object lvElement(AtomicReferenceArray<Object> paramAtomicReferenceArray, int paramInt)
  {
    return paramAtomicReferenceArray.get(paramInt);
  }
  
  private AtomicReferenceArray<Object> lvNext(AtomicReferenceArray<Object> paramAtomicReferenceArray)
  {
    return (AtomicReferenceArray)lvElement(paramAtomicReferenceArray, calcDirectOffset(paramAtomicReferenceArray.length() - 1));
  }
  
  private long lvProducerIndex()
  {
    return this.producerIndex.get();
  }
  
  private T newBufferPeek(AtomicReferenceArray<Object> paramAtomicReferenceArray, long paramLong, int paramInt)
  {
    this.consumerBuffer = paramAtomicReferenceArray;
    return (T)lvElement(paramAtomicReferenceArray, calcWrappedOffset(paramLong, paramInt));
  }
  
  private T newBufferPoll(AtomicReferenceArray<Object> paramAtomicReferenceArray, long paramLong, int paramInt)
  {
    this.consumerBuffer = paramAtomicReferenceArray;
    paramInt = calcWrappedOffset(paramLong, paramInt);
    Object localObject = lvElement(paramAtomicReferenceArray, paramInt);
    if (localObject == null) {
      return null;
    }
    soConsumerIndex(1L + paramLong);
    soElement(paramAtomicReferenceArray, paramInt, null);
    return (T)localObject;
  }
  
  private void resize(AtomicReferenceArray<Object> paramAtomicReferenceArray, long paramLong1, int paramInt, T paramT, long paramLong2)
  {
    AtomicReferenceArray localAtomicReferenceArray = new AtomicReferenceArray(paramAtomicReferenceArray.length());
    this.producerBuffer = localAtomicReferenceArray;
    this.producerLookAhead = (paramLong1 + paramLong2 - 1L);
    soProducerIndex(paramLong1 + 1L);
    soElement(localAtomicReferenceArray, paramInt, paramT);
    soNext(paramAtomicReferenceArray, localAtomicReferenceArray);
    soElement(paramAtomicReferenceArray, paramInt, HAS_NEXT);
  }
  
  private void soConsumerIndex(long paramLong)
  {
    this.consumerIndex.lazySet(paramLong);
  }
  
  private static void soElement(AtomicReferenceArray<Object> paramAtomicReferenceArray, int paramInt, Object paramObject)
  {
    paramAtomicReferenceArray.lazySet(paramInt, paramObject);
  }
  
  private void soNext(AtomicReferenceArray<Object> paramAtomicReferenceArray1, AtomicReferenceArray<Object> paramAtomicReferenceArray2)
  {
    soElement(paramAtomicReferenceArray1, calcDirectOffset(paramAtomicReferenceArray1.length() - 1), paramAtomicReferenceArray2);
  }
  
  private void soProducerIndex(long paramLong)
  {
    this.producerIndex.lazySet(paramLong);
  }
  
  private boolean writeToQueue(AtomicReferenceArray<Object> paramAtomicReferenceArray, T paramT, long paramLong, int paramInt)
  {
    soProducerIndex(1L + paramLong);
    soElement(paramAtomicReferenceArray, paramInt, paramT);
    return true;
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
    return lvProducerIndex() == lvConsumerIndex();
  }
  
  public final Iterator<T> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean offer(T paramT)
  {
    AtomicReferenceArray localAtomicReferenceArray = this.producerBuffer;
    long l = lpProducerIndex();
    int i = this.producerMask;
    int j = calcWrappedOffset(l, i);
    if (l < this.producerLookAhead) {
      return writeToQueue(localAtomicReferenceArray, paramT, l, j);
    }
    int k = this.producerLookAheadStep;
    if (lvElement(localAtomicReferenceArray, calcWrappedOffset(k + l, i)) == null)
    {
      this.producerLookAhead = (k + l - 1L);
      return writeToQueue(localAtomicReferenceArray, paramT, l, j);
    }
    if (lvElement(localAtomicReferenceArray, calcWrappedOffset(1L + l, i)) == null) {
      return writeToQueue(localAtomicReferenceArray, paramT, l, j);
    }
    resize(localAtomicReferenceArray, l, j, paramT, i);
    return true;
  }
  
  public boolean offer(T paramT1, T paramT2)
  {
    AtomicReferenceArray localAtomicReferenceArray1 = this.producerBuffer;
    long l = lvProducerIndex();
    int i = this.producerMask;
    if (lvElement(localAtomicReferenceArray1, calcWrappedOffset(l + 2L, i)) == null)
    {
      i = calcWrappedOffset(l, i);
      soElement(localAtomicReferenceArray1, i + 1, paramT2);
      soProducerIndex(l + 2L);
      soElement(localAtomicReferenceArray1, i, paramT1);
    }
    for (;;)
    {
      return true;
      AtomicReferenceArray localAtomicReferenceArray2 = new AtomicReferenceArray(localAtomicReferenceArray1.length());
      this.producerBuffer = localAtomicReferenceArray2;
      i = calcWrappedOffset(l, i);
      soElement(localAtomicReferenceArray2, i + 1, paramT2);
      soElement(localAtomicReferenceArray2, i, paramT1);
      soNext(localAtomicReferenceArray1, localAtomicReferenceArray2);
      soProducerIndex(l + 2L);
      soElement(localAtomicReferenceArray1, i, HAS_NEXT);
    }
  }
  
  public final T peek()
  {
    AtomicReferenceArray localAtomicReferenceArray = this.consumerBuffer;
    long l = lpConsumerIndex();
    int i = this.consumerMask;
    Object localObject2 = lvElement(localAtomicReferenceArray, calcWrappedOffset(l, i));
    Object localObject1 = localObject2;
    if (localObject2 == HAS_NEXT) {
      localObject1 = newBufferPeek(lvNext(localAtomicReferenceArray), l, i);
    }
    return (T)localObject1;
  }
  
  public final T poll()
  {
    AtomicReferenceArray localAtomicReferenceArray = this.consumerBuffer;
    long l = lpConsumerIndex();
    int j = this.consumerMask;
    int k = calcWrappedOffset(l, j);
    Object localObject = lvElement(localAtomicReferenceArray, k);
    if (localObject == HAS_NEXT) {}
    for (int i = 1; (localObject != null) && (i == 0); i = 0)
    {
      soConsumerIndex(1L + l);
      soElement(localAtomicReferenceArray, k, null);
      return (T)localObject;
    }
    if (i != 0) {
      return (T)newBufferPoll(lvNext(localAtomicReferenceArray), l, j);
    }
    return null;
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
  
  public final int size()
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
  
  public Object[] toArray()
  {
    throw new UnsupportedOperationException();
  }
  
  public <E> E[] toArray(E[] paramArrayOfE)
  {
    throw new UnsupportedOperationException();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/atomic/SpscLinkedArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */