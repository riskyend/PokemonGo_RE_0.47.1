package com.upsight.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.upsight.android.internal.util.PreferencesHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class Upsight
{
  private static final String CORE_COMPONENT_FACTORY = "com.upsight.core";
  private static final String EXTENSION_PREFIX = "com.upsight.extension.";
  public static final String LOG_TAG = "Upsight";
  private static final int MIN_ANDROID_API_LEVEL = 14;
  public static final String PREFERENCE_KEY_SDK_OPT_IN = "sdk_opt_in";
  private static final String SDK_OPT_IN_DEFAULT = "com.upsight.sdk_opt_in_default";
  private static UpsightContext sUpsight;
  
  static UpsightContext create(Context paramContext)
  {
    if (Build.VERSION.SDK_INT < 14)
    {
      Log.d("Upsight", UpsightContextCompat.class.getSimpleName() + " created");
      return new UpsightContextCompat(paramContext);
    }
    if (!PreferencesHelper.getBoolean(paramContext, "sdk_opt_in", loadSdkOptInDefault(paramContext)))
    {
      Log.d("Upsight", UpsightContextCompat.class.getSimpleName() + " created");
      return new UpsightContextCompat(paramContext);
    }
    UpsightCoreComponent localUpsightCoreComponent = loadCoreComponent(paramContext);
    paramContext = loadExtensions(paramContext);
    UpsightContext localUpsightContext = localUpsightCoreComponent.upsightContext();
    localUpsightContext.onCreate(localUpsightCoreComponent, paramContext);
    Log.d("Upsight", UpsightContext.class.getSimpleName() + " created");
    return localUpsightContext;
  }
  
  public static UpsightContext createContext(Context paramContext)
  {
    try
    {
      if (sUpsight == null) {
        sUpsight = create(paramContext);
      }
      paramContext = sUpsight;
      return paramContext;
    }
    finally {}
  }
  
  public static boolean isEnabled(Context paramContext)
  {
    return PreferencesHelper.getBoolean(paramContext, "sdk_opt_in", loadSdkOptInDefault(paramContext));
  }
  
  private static UpsightCoreComponent loadCoreComponent(Context paramContext)
  {
    Object localObject = loadMetadataByName(paramContext, "com.upsight.core");
    if (localObject != null) {
      try
      {
        localObject = Class.forName((String)((Pair)localObject).second);
        if (!UpsightCoreComponent.Factory.class.isAssignableFrom((Class)localObject)) {
          throw new IllegalStateException(String.format("Class %s must implement %s", new Object[] { ((Class)localObject).getName(), UpsightCoreComponent.Factory.class.getName() }));
        }
      }
      catch (ClassNotFoundException paramContext)
      {
        throw new IllegalStateException(paramContext.getMessage(), paramContext);
        paramContext = ((UpsightCoreComponent.Factory)((Class)localObject).newInstance()).create(paramContext);
        return paramContext;
      }
      catch (InstantiationException paramContext)
      {
        throw new IllegalStateException(paramContext.getMessage(), paramContext);
      }
      catch (IllegalAccessException paramContext)
      {
        throw new IllegalStateException(paramContext.getMessage(), paramContext);
      }
    }
    return null;
  }
  
  private static Map<String, UpsightExtension> loadExtensions(Context paramContext)
    throws IllegalStateException
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = loadMetadataByPrefix(paramContext, "com.upsight.extension.").entrySet().iterator();
    while (localIterator.hasNext())
    {
      paramContext = (Map.Entry)localIterator.next();
      try
      {
        localClass = Class.forName((String)paramContext.getValue());
        if (!UpsightExtension.class.isAssignableFrom(localClass)) {
          throw new IllegalStateException(String.format("Class %s must implement %s", new Object[] { localClass.getName(), UpsightExtension.class.getName() }));
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Class localClass;
        throw new IllegalStateException("Unable to load extension: " + (String)paramContext.getKey(), localClassNotFoundException);
        localClassNotFoundException.put(paramContext.getKey(), (UpsightExtension)localClass.newInstance());
      }
      catch (InstantiationException localInstantiationException)
      {
        throw new IllegalStateException("Unable to load extension: " + (String)paramContext.getKey(), localInstantiationException);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new IllegalStateException("Unable to load extension: " + (String)paramContext.getKey(), localIllegalAccessException);
      }
    }
    return localIllegalAccessException;
  }
  
  private static Pair<String, String> loadMetadataByName(Context paramContext, String paramString)
  {
    Object localObject = null;
    try
    {
      Bundle localBundle = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData;
      paramContext = (Context)localObject;
      if (localBundle != null)
      {
        Iterator localIterator = localBundle.keySet().iterator();
        String str;
        do
        {
          do
          {
            paramContext = (Context)localObject;
            if (!localIterator.hasNext()) {
              break;
            }
            paramContext = (String)localIterator.next();
          } while ((TextUtils.isEmpty(paramContext)) || (!paramContext.equals(paramString)));
          str = localBundle.getString(paramContext);
        } while (TextUtils.isEmpty(str));
        paramContext = new Pair(paramContext, str);
      }
      return paramContext;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.e("Upsight", "Unexpected error: Package name missing", paramContext);
    }
    return null;
  }
  
  private static Map<String, String> loadMetadataByPrefix(Context paramContext, String paramString)
  {
    localHashMap = new HashMap();
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData;
      if (paramContext != null)
      {
        Iterator localIterator = paramContext.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str1 = (String)localIterator.next();
          if ((!TextUtils.isEmpty(str1)) && (str1.startsWith(paramString)))
          {
            String str2 = paramContext.getString(str1);
            if (!TextUtils.isEmpty(str2)) {
              localHashMap.put(str1, str2);
            }
          }
        }
      }
      return localHashMap;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.e("Upsight", "Unexpected error: Package name missing", paramContext);
    }
  }
  
  private static boolean loadSdkOptInDefault(Context paramContext)
  {
    boolean bool2 = true;
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData;
      boolean bool1 = bool2;
      if (paramContext != null)
      {
        Iterator localIterator = paramContext.keySet().iterator();
        String str;
        do
        {
          bool1 = bool2;
          if (!localIterator.hasNext()) {
            break;
          }
          str = (String)localIterator.next();
        } while ((TextUtils.isEmpty(str)) || (!str.equals("com.upsight.sdk_opt_in_default")));
        bool1 = paramContext.getBoolean(str, true);
      }
      return bool1;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      Log.e("Upsight", "Unexpected error: Package name missing", paramContext);
    }
    return true;
  }
  
  public static void setEnabled(Context paramContext, boolean paramBoolean)
  {
    PreferencesHelper.putBoolean(paramContext, "sdk_opt_in", paramBoolean);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/Upsight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */