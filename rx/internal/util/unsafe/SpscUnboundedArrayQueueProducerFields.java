package rx.internal.util.unsafe;

import java.util.AbstractQueue;

abstract class SpscUnboundedArrayQueueProducerFields<E>
  extends AbstractQueue<E>
{
  protected long producerIndex;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/SpscUnboundedArrayQueueProducerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */