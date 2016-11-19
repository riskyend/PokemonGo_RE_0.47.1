package com.google.android.exoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public final class AspectRatioFrameLayout
  extends FrameLayout
{
  private static final float MAX_ASPECT_RATIO_DEFORMATION_FRACTION = 0.01F;
  private float videoAspectRatio;
  
  public AspectRatioFrameLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public AspectRatioFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.videoAspectRatio == 0.0F) {}
    float f;
    do
    {
      return;
      paramInt2 = getMeasuredWidth();
      paramInt1 = getMeasuredHeight();
      f = paramInt2 / paramInt1;
      f = this.videoAspectRatio / f - 1.0F;
    } while (Math.abs(f) <= 0.01F);
    if (f > 0.0F) {
      paramInt1 = (int)(paramInt2 / this.videoAspectRatio);
    }
    for (;;)
    {
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824), View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824));
      return;
      paramInt2 = (int)(paramInt1 * this.videoAspectRatio);
    }
  }
  
  public void setAspectRatio(float paramFloat)
  {
    if (this.videoAspectRatio != paramFloat)
    {
      this.videoAspectRatio = paramFloat;
      requestLayout();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/AspectRatioFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */