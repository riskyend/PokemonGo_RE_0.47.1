package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.regex.Pattern;

public final class zzml
{
  private static Pattern zzaij = null;
  
  public static boolean zzan(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
  }
  
  public static int zzca(int paramInt)
  {
    return paramInt / 1000;
  }
  
  @Deprecated
  public static boolean zzcb(int paramInt)
  {
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzml.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */