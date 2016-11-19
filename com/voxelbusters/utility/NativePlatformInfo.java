package com.voxelbusters.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.voxelbusters.common.Configuration;

public class NativePlatformInfo
{
  private static PackageInfo getPackageInfo()
  {
    Object localObject = Configuration.getContext().getApplicationContext();
    PackageManager localPackageManager = ((Context)localObject).getPackageManager();
    try
    {
      localObject = localPackageManager.getPackageInfo(((Context)localObject).getPackageName(), 0);
      return (PackageInfo)localObject;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return null;
  }
  
  public static String getPackageName()
  {
    String str = null;
    PackageInfo localPackageInfo = getPackageInfo();
    Log.e("Utility", localPackageInfo.toString());
    if (localPackageInfo != null) {
      str = localPackageInfo.packageName;
    }
    return str;
  }
  
  public static String getPackageVersionName()
  {
    String str = null;
    PackageInfo localPackageInfo = getPackageInfo();
    if (localPackageInfo != null) {
      str = localPackageInfo.versionName;
    }
    return str;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/utility/NativePlatformInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */