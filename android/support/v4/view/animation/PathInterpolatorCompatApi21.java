package android.support.v4.view.animation;

import android.graphics.Path;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

class PathInterpolatorCompatApi21
{
  public static Interpolator create(float paramFloat1, float paramFloat2)
  {
    return new PathInterpolator(paramFloat1, paramFloat2);
  }
  
  public static Interpolator create(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return new PathInterpolator(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public static Interpolator create(Path paramPath)
  {
    return new PathInterpolator(paramPath);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/view/animation/PathInterpolatorCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */