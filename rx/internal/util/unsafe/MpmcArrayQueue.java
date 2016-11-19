package rx.internal.util.unsafe;

public class MpmcArrayQueue<E>
  extends MpmcArrayQueueConsumerField<E>
{
  long p30;
  long p31;
  long p32;
  long p33;
  long p34;
  long p35;
  long p36;
  long p37;
  long p40;
  long p41;
  long p42;
  long p43;
  long p44;
  long p45;
  long p46;
  
  public MpmcArrayQueue(int paramInt)
  {
    super(Math.max(2, paramInt));
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
    long l3 = this.mask + 1L;
    long[] arrayOfLong = this.sequenceBuffer;
    long l1 = Long.MAX_VALUE;
    long l4;
    long l2;
    do
    {
      long l5;
      do
      {
        do
        {
          l4 = lvProducerIndex();
          l2 = calcSequenceOffset(l4);
          l5 = lvSequence(arrayOfLong, l2) - l4;
          if (l5 != 0L) {
            break;
          }
        } while (!casProducerIndex(l4, 1L + l4));
        spElement(calcElementOffset(l4), paramE);
        soSequence(arrayOfLong, l2, 1L + l4);
        return true;
      } while ((l5 >= 0L) || (l4 - l3 > l1));
      l2 = lvConsumerIndex();
      l1 = l2;
    } while (l4 - l3 > l2);
    return false;
  }
  
  public E peek()
  {
    long l;
    Object localObject;
    do
    {
      l = lvConsumerIndex();
      localObject = lpElement(calcElementOffset(l));
    } while ((localObject == null) && (l != lvProducerIndex()));
    return (E)localObject;
  }
  
  public E poll()
  {
    long[] arrayOfLong = this.sequenceBuffer;
    long l1 = -1L;
    long l3;
    long l2;
    do
    {
      long l4;
      do
      {
        do
        {
          l3 = lvConsumerIndex();
          l2 = calcSequenceOffset(l3);
          l4 = lvSequence(arrayOfLong, l2) - (1L + l3);
          if (l4 != 0L) {
            break;
          }
        } while (!casConsumerIndex(l3, 1L + l3));
        l1 = calcElementOffset(l3);
        Object localObject = lpElement(l1);
        spElement(l1, null);
        soSequence(arrayOfLong, l2, this.mask + l3 + 1L);
        return (E)localObject;
      } while ((l4 >= 0L) || (l3 < l1));
      l2 = lvProducerIndex();
      l1 = l2;
    } while (l3 != l2);
    return null;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/MpmcArrayQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */