package com.upsight.android.unity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.unity3d.player.UnityPlayer;

public class UnityBridge
{
  protected static final String MANAGER_NAME = "UpsightManager";
  protected static final String TAG = "Upsight";
  
  public static void UnitySendMessage(@NonNull String paramString)
  {
    UnitySendMessage(paramString, "");
  }
  
  public static void UnitySendMessage(@NonNull String paramString1, @Nullable String paramString2)
  {
    String str = paramString2;
    if (paramString2 == null) {
      str = "";
    }
    try
    {
      UnityPlayer.UnitySendMessage("UpsightManager", paramString1, str);
      return;
    }
    catch (Throwable paramString1)
    {
      Log.e("Upsight", "UnityPlayer.UnitySendMessage failed. The NDK library is likely not loaded: " + paramString1.getMessage());
    }
  }
  
  @Nullable
  public static Activity getActivity()
  {
    return UnityPlayer.currentActivity;
  }
  
  public static void runSafelyOnUiThread(@NonNull Runnable paramRunnable)
  {
    Activity localActivity = getActivity();
    if (localActivity != null) {
      localActivity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          try
          {
            this.val$r.run();
            return;
          }
          catch (Exception localException)
          {
            Log.e("Upsight", "Exception running command on UI thread: " + localException.getMessage());
          }
        }
      });
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/UnityBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */