package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

class ActivityOptionsCompat21
{
  private final ActivityOptions mActivityOptions;
  
  private ActivityOptionsCompat21(ActivityOptions paramActivityOptions)
  {
    this.mActivityOptions = paramActivityOptions;
  }
  
  public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity paramActivity, View paramView, String paramString)
  {
    return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(paramActivity, paramView, paramString));
  }
  
  public static ActivityOptionsCompat21 makeSceneTransitionAnimation(Activity paramActivity, View[] paramArrayOfView, String[] paramArrayOfString)
  {
    Object localObject = null;
    if (paramArrayOfView != null)
    {
      Pair[] arrayOfPair = new Pair[paramArrayOfView.length];
      int i = 0;
      for (;;)
      {
        localObject = arrayOfPair;
        if (i >= arrayOfPair.length) {
          break;
        }
        arrayOfPair[i] = Pair.create(paramArrayOfView[i], paramArrayOfString[i]);
        i += 1;
      }
    }
    return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(paramActivity, (Pair[])localObject));
  }
  
  public Bundle toBundle()
  {
    return this.mActivityOptions.toBundle();
  }
  
  public void update(ActivityOptionsCompat21 paramActivityOptionsCompat21)
  {
    this.mActivityOptions.update(paramActivityOptionsCompat21.mActivityOptions);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/app/ActivityOptionsCompat21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */