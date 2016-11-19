package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import com.android.volley.RequestQueue;
import java.io.File;

public class Volley
{
  private static final String DEFAULT_CACHE_DIR = "volley";
  
  public static RequestQueue newRequestQueue(Context paramContext)
  {
    return newRequestQueue(paramContext, null);
  }
  
  public static RequestQueue newRequestQueue(Context paramContext, HttpStack paramHttpStack)
  {
    File localFile = new File(paramContext.getCacheDir(), "volley");
    Object localObject = "volley/0";
    try
    {
      String str = paramContext.getPackageName();
      paramContext = paramContext.getPackageManager().getPackageInfo(str, 0);
      str = String.valueOf(String.valueOf(str));
      int i = paramContext.versionCode;
      paramContext = str.length() + 12 + str + "/" + i;
      localObject = paramContext;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      label140:
      for (;;) {}
    }
    paramContext = paramHttpStack;
    if (paramHttpStack == null) {
      if (Build.VERSION.SDK_INT < 9) {
        break label140;
      }
    }
    for (paramContext = new HurlStack();; paramContext = new HttpClientStack(AndroidHttpClient.newInstance((String)localObject)))
    {
      paramContext = new BasicNetwork(paramContext);
      paramContext = new RequestQueue(new DiskBasedCache(localFile), paramContext);
      paramContext.start();
      return paramContext;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/android/volley/toolbox/Volley.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */