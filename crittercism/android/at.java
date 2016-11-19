package crittercism.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.crittercism.app.CrittercismConfig;

public final class at
{
  public String a = "1.0";
  public int b = 0;
  
  public at(Context paramContext, CrittercismConfig paramCrittercismConfig)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      this.a = paramContext.versionName;
      this.b = paramContext.versionCode;
      paramContext = paramCrittercismConfig.getCustomVersionName();
      if ((paramContext != null) && (paramContext.length() > 0)) {
        this.a = paramContext;
      }
      if (paramCrittercismConfig.isVersionCodeToBeIncludedInVersionString()) {
        this.a = (this.a + "-" + Integer.toString(this.b));
      }
      return;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/at.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */