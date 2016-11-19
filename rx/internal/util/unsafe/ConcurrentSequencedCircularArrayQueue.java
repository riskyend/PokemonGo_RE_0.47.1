package rx.internal.util.unsafe;

import sun.misc.Unsafe;

public abstract class ConcurrentSequencedCircularArrayQueue<E>
  extends ConcurrentCircularArrayQueue<E>
{
  private static final long ARRAY_BASE;
  private static final int ELEMENT_SHIFT;
  protected final long[] sequenceBuffer;
  
  static
  {
    if (8 == UnsafeAccess.UNSAFE.arrayIndexScale(long[].class))
    {
      ELEMENT_SHIFT = SPARSE_SHIFT + 3;
      ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(long[].class) + (32 << ELEMENT_SHIFT - SPARSE_SHIFT);
      return;
    }
    throw new IllegalStateException("Unexpected long[] element size");
  }
  
  public ConcurrentSequencedCircularArrayQueue(int paramInt)
  {
    super(paramInt);
    paramInt = (int)(this.mask + 1L);
    this.sequenceBuffer = new long[(paramInt << SPARSE_SHIFT) + 64];
    for (long l = 0L; l < paramInt; l += 1L) {
      soSequence(this.sequenceBuffer, calcSequenceOffset(l), l);
    }
  }
  
  protected final long calcSequenceOffset(long paramLong)
  {
    return ARRAY_BASE + ((this.mask & paramLong) << ELEMENT_SHIFT);
  }
  
  protected final long lvSequence(long[] paramArrayOfLong, long paramLong)
  {
    return UnsafeAccess.UNSAFE.getLongVolatile(paramArrayOfLong, paramLong);
  }
  
  protected final void soSequence(long[] paramArrayOfLong, long paramLong1, long paramLong2)
  {
    UnsafeAccess.UNSAFE.putOrderedLong(paramArrayOfLong, paramLong1, paramLong2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/ConcurrentSequencedCircularArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */