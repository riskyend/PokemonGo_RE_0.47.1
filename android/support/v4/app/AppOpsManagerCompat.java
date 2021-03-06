package android.support.v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public class AppOpsManagerCompat
{
  private static final AppOpsManagerImpl IMPL = new AppOpsManagerImpl(null);
  public static final int MODE_ALLOWED = 0;
  public static final int MODE_DEFAULT = 3;
  public static final int MODE_IGNORED = 1;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      IMPL = new AppOpsManager23(null);
      return;
    }
  }
  
  public static int noteOp(@NonNull Context paramContext, @NonNull String paramString1, int paramInt, @NonNull String paramString2)
  {
    return IMPL.noteOp(paramContext, paramString1, paramInt, paramString2);
  }
  
  public static int noteProxyOp(@NonNull Context paramContext, @NonNull String paramString1, @NonNull String paramString2)
  {
    return IMPL.noteProxyOp(paramContext, paramString1, paramString2);
  }
  
  public static String permissionToOp(@NonNull String paramString)
  {
    return IMPL.permissionToOp(paramString);
  }
  
  private static class AppOpsManager23
    extends AppOpsManagerCompat.AppOpsManagerImpl
  {
    private AppOpsManager23()
    {
      super();
    }
    
    public int noteOp(Context paramContext, String paramString1, int paramInt, String paramString2)
    {
      return AppOpsManagerCompat23.noteOp(paramContext, paramString1, paramInt, paramString2);
    }
    
    public int noteProxyOp(Context paramContext, String paramString1, String paramString2)
    {
      return AppOpsManagerCompat23.noteProxyOp(paramContext, paramString1, paramString2);
    }
    
    public String permissionToOp(String paramString)
    {
      return AppOpsManagerCompat23.permissionToOp(paramString);
    }
  }
  
  private static class AppOpsManagerImpl
  {
    public int noteOp(Context paramContext, String paramString1, int paramInt, String paramString2)
    {
      return 1;
    }
    
    public int noteProxyOp(Context paramContext, String paramString1, String paramString2)
    {
      return 1;
    }
    
    public String permissionToOp(String paramString)
    {
      return null;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/app/AppOpsManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */