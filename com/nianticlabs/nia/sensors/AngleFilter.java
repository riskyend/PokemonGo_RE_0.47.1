package com.nianticlabs.nia.sensors;

public class AngleFilter
{
  private static final float FRICTION_COEFFICIENT_1 = 1.0F;
  private static final float FRICTION_COEFFICIENT_2 = 0.5F;
  private static final float MAX_DT = 10.0F;
  private static final float SIGNAL_LEVEL = 10.0F;
  private static final float STEP_SIZE = 0.5F;
  private static final float TIME_NORMALIZATION_MS = 100.0F;
  protected float currentValue;
  private long lastReadingTime = 0L;
  private float speed = 0.0F;
  private final boolean wrap;
  
  public AngleFilter(boolean paramBoolean)
  {
    this.wrap = paramBoolean;
  }
  
  private void step(float paramFloat1, float paramFloat2)
  {
    paramFloat2 = (paramFloat2 - this.currentValue) / 10.0F;
    this.speed += Math.abs(paramFloat2) * paramFloat2 * paramFloat1;
    if (this.speed != 0.0F)
    {
      paramFloat2 = paramFloat2 * 10.0F / this.speed;
      paramFloat2 = (float)(1.0D * Math.exp(-paramFloat2 * paramFloat2) + 0.5D);
      if (paramFloat2 * paramFloat1 >= 1.0F) {
        this.speed = 0.0F;
      }
    }
    else
    {
      return;
    }
    this.speed -= this.speed * paramFloat2 * paramFloat1;
    this.currentValue += this.speed * paramFloat1;
  }
  
  public float filter(long paramLong, float paramFloat)
  {
    if ((this.lastReadingTime == 0L) || (paramLong < this.lastReadingTime)) {
      this.currentValue = paramFloat;
    }
    for (;;)
    {
      this.lastReadingTime = paramLong;
      if (!this.wrap) {
        break label206;
      }
      while (this.currentValue >= 360.0F) {
        this.currentValue -= 360.0F;
      }
      float f1 = paramFloat;
      if (this.wrap)
      {
        f1 = paramFloat;
        if (Math.abs(paramFloat - this.currentValue) * 2.0F > 360.0F) {
          if (paramFloat >= this.currentValue) {
            break label148;
          }
        }
      }
      label148:
      for (f1 = paramFloat + 360.0F;; f1 = paramFloat - 360.0F)
      {
        float f2 = (float)(paramLong - this.lastReadingTime) / 100.0F;
        if (f2 <= 10.0F)
        {
          paramFloat = f2;
          if (f2 >= 0.0F) {
            break label157;
          }
        }
        this.currentValue = f1;
        this.speed = 0.0F;
        break;
      }
      label157:
      while (paramFloat > 0.0F)
      {
        step(Math.min(0.5F, paramFloat), f1);
        paramFloat -= 0.5F;
      }
    }
    while (this.currentValue < 0.0F) {
      this.currentValue += 360.0F;
    }
    label206:
    return this.currentValue;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/sensors/AngleFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */