package crittercism.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class dq
{
  public static boolean a = false;
  
  public static Boolean a(Context paramContext)
  {
    return Boolean.valueOf(paramContext.getSharedPreferences("com.crittercism.usersettings", 0).getBoolean("crashedOnLastLoad", false));
  }
  
  public static void a(Context paramContext, boolean paramBoolean)
  {
    paramContext = paramContext.getSharedPreferences("com.crittercism.usersettings", 0).edit();
    paramContext.putBoolean("crashedOnLastLoad", paramBoolean);
    paramContext.commit();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */