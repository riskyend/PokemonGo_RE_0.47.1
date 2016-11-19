package rx.internal.util.unsafe;

import java.lang.reflect.Field;
import java.util.Iterator;
import sun.misc.Unsafe;

public class SpscUnboundedArrayQueue<E>
  extends SpscUnboundedArrayQueueConsumerField<E>
  implements QueueProgressIndicators
{
  private static final long C_INDEX_OFFSET;
  private static final Object HAS_NEXT;
  static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
  private static final long P_INDEX_OFFSET;
  private static final long REF_ARRAY_BASE;
  private static final int REF_ELEMENT_SHIFT;
  
  static
  {
    HAS_NEXT = new Object();
    int i = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
    if (4 == i) {}
    for (REF_ELEMENT_SHIFT = 2;; REF_ELEMENT_SHIFT = 3)
    {
      REF_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
      try
      {
        localField = SpscUnboundedArrayQueueProducerFields.class.getDeclaredField("producerIndex");
        P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(localField);
      }
      catch (NoSuchFieldException localNoSuchFieldException1)
      {
        Field localField;
        throw new RuntimeException(localNoSuchFieldException1);
      }
      try
      {
        localField = SpscUnboundedArrayQueueConsumerField.class.getDeclaredField("consumerIndex");
        C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(localField);
        return;
      }
      catch (NoSuchFieldException localNoSuchFieldException2)
      {
        throw new RuntimeException(localNoSuchFieldException2);
      }
      if (8 != i) {
        break;
      }
    }
    throw new IllegalStateException("Unknown pointer size");
  }
  
  public SpscUnboundedArrayQueue(int paramInt)
  {
    paramInt = Pow2.roundToPowerOfTwo(paramInt);
    long l = paramInt - 1;
    Object[] arrayOfObject = (Object[])new Object[paramInt + 1];
    this.producerBuffer = arrayOfObject;
    this.producerMask = l;
    adjustLookAheadStep(paramInt);
    this.consumerBuffer = arrayOfObject;
    this.consumerMask = l;
    this.producerLookAhead = (l - 1L);
    soProducerIndex(0L);
  }
  
  private void adjustLookAheadStep(int paramInt)
  {
    this.producerLookAheadStep = Math.min(paramInt / 4, MAX_LOOK_AHEAD_STEP);
  }
  
  private static long calcDirectOffset(long paramLong)
  {
    return REF_ARRAY_BASE + (paramLong << REF_ELEMENT_SHIFT);
  }
  
  private static long calcWrappedOffset(long paramLong1, long paramLong2)
  {
    return calcDirectOffset(paramLong1 & paramLong2);
  }
  
  private long lvConsumerIndex()
  {
    return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
  }
  
  private static <E> Object lvElement(E[] paramArrayOfE, long paramLong)
  {
    return UnsafeAccess.UNSAFE.getObjectVolatile(paramArrayOfE, paramLong);
  }
  
  private E[] lvNext(E[] paramArrayOfE)
  {
    return (Object[])lvElement(paramArrayOfE, calcDirectOffset(paramArrayOfE.length - 1));
  }
  
  private long lvProducerIndex()
  {
    return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
  }
  
  private E newBufferPeek(E[] paramArrayOfE, long paramLong1, long paramLong2)
  {
    this.consumerBuffer = paramArrayOfE;
    return (E)lvElement(paramArrayOfE, calcWrappedOffset(paramLong1, paramLong2));
  }
  
  private E newBufferPoll(E[] paramArrayOfE, long paramLong1, long paramLong2)
  {
    this.consumerBuffer = paramArrayOfE;
    paramLong2 = calcWrappedOffset(paramLong1, paramLong2);
    Object localObject = lvElement(paramArrayOfE, paramLong2);
    if (localObject == null) {
      return null;
    }
    soConsumerIndex(1L + paramLong1);
    soElement(paramArrayOfE, paramLong2, null);
    return (E)localObject;
  }
  
  private void resize(E[] paramArrayOfE, long paramLong1, long paramLong2, E paramE, long paramLong3)
  {
    Object[] arrayOfObject = (Object[])new Object[paramArrayOfE.length];
    this.producerBuffer = arrayOfObject;
    this.producerLookAhead = (paramLong1 + paramLong3 - 1L);
    soProducerIndex(paramLong1 + 1L);
    soElement(arrayOfObject, paramLong2, paramE);
    soNext(paramArrayOfE, arrayOfObject);
    soElement(paramArrayOfE, paramLong2, HAS_NEXT);
  }
  
  private void soConsumerIndex(long paramLong)
  {
    UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, paramLong);
  }
  
  private static void soElement(Object[] paramArrayOfObject, long paramLong, Object paramObject)
  {
    UnsafeAccess.UNSAFE.putOrderedObject(paramArrayOfObject, paramLong, paramObject);
  }
  
  private void soNext(E[] paramArrayOfE1, E[] paramArrayOfE2)
  {
    soElement(paramArrayOfE1, calcDirectOffset(paramArrayOfE1.length - 1), paramArrayOfE2);
  }
  
  private void soProducerIndex(long paramLong)
  {
    UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, paramLong);
  }
  
  private boolean writeToQueue(E[] paramArrayOfE, E paramE, long paramLong1, long paramLong2)
  {
    soProducerIndex(1L + paramLong1);
    soElement(paramArrayOfE, paramLong2, paramE);
    return true;
  }
  
  public long currentConsumerIndex()
  {
    return lvConsumerIndex();
  }
  
  public long currentProducerIndex()
  {
    return lvProducerIndex();
  }
  
  public final Iterator<E> iterator()
  {
    throw new UnsupportedOperationException();
  }
  
  public final boolean offer(E paramE)
  {
    if (paramE == null) {
      throw new NullPointerException("Null is not a valid element");
    }
    Object[] arrayOfObject = this.producerBuffer;
    long l1 = this.producerIndex;
    long l2 = this.producerMask;
    long l3 = calcWrappedOffset(l1, l2);
    if (l1 < this.producerLookAhead) {
      return writeToQueue(arrayOfObject, paramE, l1, l3);
    }
    int i = this.producerLookAheadStep;
    if (lvElement(arrayOfObject, calcWrappedOffset(i + l1, l2)) == null)
    {
      this.producerLookAhead = (i + l1 - 1L);
      return writeToQueue(arrayOfObject, paramE, l1, l3);
    }
    if (lvElement(arrayOfObject, calcWrappedOffset(1L + l1, l2)) != null) {
      return writeToQueue(arrayOfObject, paramE, l1, l3);
    }
    resize(arrayOfObject, l1, l3, paramE, l2);
    return true;
  }
  
  public final E peek()
  {
    Object[] arrayOfObject = this.consumerBuffer;
    long l1 = this.consumerIndex;
    long l2 = this.consumerMask;
    Object localObject2 = lvElement(arrayOfObject, calcWrappedOffset(l1, l2));
    Object localObject1 = localObject2;
    if (localObject2 == HAS_NEXT) {
      localObject1 = newBufferPeek(lvNext(arrayOfObject), l1, l2);
    }
    return (E)localObject1;
  }
  
  public final E poll()
  {
    Object[] arrayOfObject = this.consumerBuffer;
    long l1 = this.consumerIndex;
    long l2 = this.consumerMask;
    long l3 = calcWrappedOffset(l1, l2);
    Object localObject = lvElement(arrayOfObject, l3);
    if (localObject == HAS_NEXT) {}
    for (int i = 1; (localObject != null) && (i == 0); i = 0)
    {
      soConsumerIndex(1L + l1);
      soElement(arrayOfObject, l3, null);
      return (E)localObject;
    }
    if (i != 0) {
      return (E)newBufferPoll(lvNext(arrayOfObject), l1, l2);
    }
    return null;
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
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/SpscUnboundedArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */