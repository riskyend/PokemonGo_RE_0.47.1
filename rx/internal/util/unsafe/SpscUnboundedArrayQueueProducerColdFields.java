package rx.internal.util.unsafe;

abstract class SpscUnboundedArrayQueueProducerColdFields<E>
  extends SpscUnboundedArrayQueueProducerFields<E>
{
  protected E[] producerBuffer;
  protected long producerLookAhead;
  protected int producerLookAheadStep;
  protected long producerMask;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/SpscUnboundedArrayQueueProducerColdFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */