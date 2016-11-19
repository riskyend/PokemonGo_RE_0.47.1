package rx.internal.util.unsafe;

abstract class SpscUnboundedArrayQueueConsumerColdField<E>
  extends SpscUnboundedArrayQueueL2Pad<E>
{
  protected E[] consumerBuffer;
  protected long consumerMask;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/SpscUnboundedArrayQueueConsumerColdField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */