package android.support.v4.view;

import android.view.View;
import android.view.ViewPropertyAnimator;

class ViewPropertyAnimatorCompatLollipop
{
  public static void translationZ(View paramView, float paramFloat)
  {
    paramView.animate().translationZ(paramFloat);
  }
  
  public static void translationZBy(View paramView, float paramFloat)
  {
    paramView.animate().translationZBy(paramFloat);
  }
  
  public static void z(View paramView, float paramFloat)
  {
    paramView.animate().z(paramFloat);
  }
  
  public static void zBy(View paramView, float paramFloat)
  {
    paramView.animate().zBy(paramFloat);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/view/ViewPropertyAnimatorCompatLollipop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */