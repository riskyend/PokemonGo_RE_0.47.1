package rx.internal.util.unsafe;

abstract class SpscArrayQueueColdField<E>
  extends ConcurrentCircularArrayQueue<E>
{
  private static final Integer MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
  protected final int lookAheadStep;
  
  public SpscArrayQueueColdField(int paramInt)
  {
    super(paramInt);
    this.lookAheadStep = Math.min(paramInt / 4, MAX_LOOK_AHEAD_STEP.intValue());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/util/unsafe/SpscArrayQueueColdField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */