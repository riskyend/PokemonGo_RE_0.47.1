package spacemadness.com.lunarconsole.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;
import spacemadness.com.lunarconsole.debug.Assert;

public class UIUtils
{
  public static float dpToPx(Context paramContext, float paramFloat)
  {
    return getScreenDensity(paramContext) * paramFloat;
  }
  
  public static FrameLayout getRootLayout(Activity paramActivity)
  {
    paramActivity = getRootViewGroup(paramActivity);
    Assert.IsTrue(paramActivity instanceof FrameLayout);
    return (FrameLayout)ObjectUtils.as(paramActivity, FrameLayout.class);
  }
  
  public static ViewGroup getRootViewGroup(Activity paramActivity)
  {
    if (paramActivity == null) {
      throw new NullPointerException("Activity is null");
    }
    paramActivity = paramActivity.getWindow().findViewById(16908290);
    Assert.IsTrue(paramActivity instanceof ViewGroup);
    return (ViewGroup)ObjectUtils.as(paramActivity, ViewGroup.class);
  }
  
  private static float getScreenDensity(Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().density;
  }
  
  public static float pxToDp(Context paramContext, float paramFloat)
  {
    return paramFloat / getScreenDensity(paramContext);
  }
  
  public static void showToast(Context paramContext, String paramString)
  {
    Assert.IsNotNull(paramContext);
    if (paramContext != null) {
      Toast.makeText(paramContext, paramString, 0).show();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/utils/UIUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */