package com.google.android.exoplayer.text;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public final class SubtitleLayout
  extends View
{
  private static final int ABSOLUTE = 2;
  public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08F;
  public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533F;
  private static final int FRACTIONAL = 0;
  private static final int FRACTIONAL_IGNORE_PADDING = 1;
  private boolean applyEmbeddedStyles = true;
  private float bottomPaddingFraction = 0.08F;
  private List<Cue> cues;
  private final List<CuePainter> painters = new ArrayList();
  private CaptionStyleCompat style = CaptionStyleCompat.DEFAULT;
  private float textSize = 0.0533F;
  private int textSizeType = 0;
  
  public SubtitleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SubtitleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setTextSize(int paramInt, float paramFloat)
  {
    if ((this.textSizeType == paramInt) && (this.textSize == paramFloat)) {
      return;
    }
    this.textSizeType = paramInt;
    this.textSize = paramFloat;
    invalidate();
  }
  
  public void dispatchDraw(Canvas paramCanvas)
  {
    int i;
    int i2;
    int k;
    int m;
    int n;
    int i1;
    if (this.cues == null)
    {
      i = 0;
      j = getTop();
      i2 = getBottom();
      k = getLeft() + getPaddingLeft();
      m = j + getPaddingTop();
      n = getRight() + getPaddingRight();
      i1 = i2 - getPaddingBottom();
      if ((i1 > m) && (n > k)) {
        break label89;
      }
    }
    for (;;)
    {
      return;
      i = this.cues.size();
      break;
      label89:
      if (this.textSizeType != 2) {
        break label179;
      }
      f = this.textSize;
      label102:
      if (f <= 0.0F) {
        break label205;
      }
      j = 0;
      while (j < i)
      {
        ((CuePainter)this.painters.get(j)).draw((Cue)this.cues.get(j), this.applyEmbeddedStyles, this.style, f, this.bottomPaddingFraction, paramCanvas, k, m, n, i1);
        j += 1;
      }
    }
    label179:
    float f = this.textSize;
    if (this.textSizeType == 0) {}
    for (int j = i1 - m;; j = i2 - j)
    {
      f *= j;
      break label102;
      label205:
      break;
    }
  }
  
  public void setApplyEmbeddedStyles(boolean paramBoolean)
  {
    if (this.applyEmbeddedStyles == paramBoolean) {
      return;
    }
    this.applyEmbeddedStyles = paramBoolean;
    invalidate();
  }
  
  public void setBottomPaddingFraction(float paramFloat)
  {
    if (this.bottomPaddingFraction == paramFloat) {
      return;
    }
    this.bottomPaddingFraction = paramFloat;
    invalidate();
  }
  
  public void setCues(List<Cue> paramList)
  {
    if (this.cues == paramList) {
      return;
    }
    this.cues = paramList;
    int i;
    if (paramList == null) {
      i = 0;
    }
    while (this.painters.size() < i)
    {
      this.painters.add(new CuePainter(getContext()));
      continue;
      i = paramList.size();
    }
    invalidate();
  }
  
  public void setFixedTextSize(int paramInt, float paramFloat)
  {
    Object localObject = getContext();
    if (localObject == null) {}
    for (localObject = Resources.getSystem();; localObject = ((Context)localObject).getResources())
    {
      setTextSize(2, TypedValue.applyDimension(paramInt, paramFloat, ((Resources)localObject).getDisplayMetrics()));
      return;
    }
  }
  
  public void setFractionalTextSize(float paramFloat)
  {
    setFractionalTextSize(paramFloat, false);
  }
  
  public void setFractionalTextSize(float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      setTextSize(i, paramFloat);
      return;
    }
  }
  
  public void setStyle(CaptionStyleCompat paramCaptionStyleCompat)
  {
    if (this.style == paramCaptionStyleCompat) {
      return;
    }
    this.style = paramCaptionStyleCompat;
    invalidate();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/SubtitleLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */