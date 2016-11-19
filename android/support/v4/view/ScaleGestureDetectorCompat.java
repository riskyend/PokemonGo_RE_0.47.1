package android.support.v4.view;

import android.os.Build.VERSION;

public class ScaleGestureDetectorCompat
{
  static final ScaleGestureDetectorImpl IMPL = new BaseScaleGestureDetectorImpl(null);
  
  static
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      IMPL = new ScaleGestureDetectorCompatKitKatImpl(null);
      return;
    }
  }
  
  public static boolean isQuickScaleEnabled(Object paramObject)
  {
    return IMPL.isQuickScaleEnabled(paramObject);
  }
  
  public static void setQuickScaleEnabled(Object paramObject, boolean paramBoolean)
  {
    IMPL.setQuickScaleEnabled(paramObject, paramBoolean);
  }
  
  private static class BaseScaleGestureDetectorImpl
    implements ScaleGestureDetectorCompat.ScaleGestureDetectorImpl
  {
    public boolean isQuickScaleEnabled(Object paramObject)
    {
      return false;
    }
    
    public void setQuickScaleEnabled(Object paramObject, boolean paramBoolean) {}
  }
  
  private static class ScaleGestureDetectorCompatKitKatImpl
    implements ScaleGestureDetectorCompat.ScaleGestureDetectorImpl
  {
    public boolean isQuickScaleEnabled(Object paramObject)
    {
      return ScaleGestureDetectorCompatKitKat.isQuickScaleEnabled(paramObject);
    }
    
    public void setQuickScaleEnabled(Object paramObject, boolean paramBoolean)
    {
      ScaleGestureDetectorCompatKitKat.setQuickScaleEnabled(paramObject, paramBoolean);
    }
  }
  
  static abstract interface ScaleGestureDetectorImpl
  {
    public abstract boolean isQuickScaleEnabled(Object paramObject);
    
    public abstract void setQuickScaleEnabled(Object paramObject, boolean paramBoolean);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/view/ScaleGestureDetectorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */