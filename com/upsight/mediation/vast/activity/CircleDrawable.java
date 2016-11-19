package com.upsight.mediation.vast.activity;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.upsight.mediation.vast.util.Assets;

public class CircleDrawable
  extends Drawable
{
  private static final float STROKE_WIDTH = 2.0F;
  private RectF oval;
  private Paint paint;
  private float startAngle;
  private float sweepAngle;
  private long totalTime;
  
  public CircleDrawable(float paramFloat1, float paramFloat2)
  {
    this.oval = new RectF(paramFloat1 / 2.0F - paramFloat2, paramFloat1 / 2.0F - paramFloat2, paramFloat1 / 2.0F + paramFloat2, paramFloat1 / 2.0F + paramFloat2);
    this.startAngle = 270.0F;
    this.sweepAngle = 0.0F;
    this.paint = new Paint(1);
    this.paint.setStyle(Paint.Style.STROKE);
    this.paint.setColor(-1);
    this.paint.setAlpha(255);
    this.paint.setStrokeWidth(Assets.convertToDps(2.0F));
  }
  
  public void draw(Canvas paramCanvas)
  {
    paramCanvas.drawArc(this.oval, this.startAngle, this.sweepAngle, false, this.paint);
  }
  
  public int getOpacity()
  {
    return 0;
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
  
  public void setSweepAngle(float paramFloat)
  {
    this.sweepAngle = paramFloat;
  }
  
  public void setTimer(long paramLong)
  {
    this.totalTime = paramLong;
  }
  
  public void update(long paramLong)
  {
    this.sweepAngle = (360.0F - (float)paramLong / (float)this.totalTime * 360.0F);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/activity/CircleDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */