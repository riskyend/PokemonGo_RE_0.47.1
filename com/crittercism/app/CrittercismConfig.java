package com.crittercism.app;

import android.os.Build.VERSION;
import crittercism.android.dx;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;

public class CrittercismConfig
{
  public static final String API_VERSION = "5.0.8";
  protected String a = "com.crittercism/dumps";
  private String b = null;
  private boolean c = false;
  private boolean d = false;
  private boolean e = true;
  private boolean f = false;
  private boolean g = b();
  private String h = "Developer Reply";
  private String i = null;
  private List j = new LinkedList();
  private List k = new LinkedList();
  
  public CrittercismConfig() {}
  
  public CrittercismConfig(CrittercismConfig paramCrittercismConfig)
  {
    this.b = paramCrittercismConfig.b;
    this.c = paramCrittercismConfig.c;
    this.d = paramCrittercismConfig.d;
    this.e = paramCrittercismConfig.e;
    this.f = paramCrittercismConfig.f;
    this.g = paramCrittercismConfig.g;
    this.a = paramCrittercismConfig.a;
    this.h = paramCrittercismConfig.h;
    setURLBlacklistPatterns(paramCrittercismConfig.j);
    setPreserveQueryStringPatterns(paramCrittercismConfig.k);
    this.i = paramCrittercismConfig.i;
  }
  
  @Deprecated
  public CrittercismConfig(JSONObject paramJSONObject)
  {
    this.b = a(paramJSONObject, "customVersionName", this.b);
    this.d = a(paramJSONObject, "includeVersionCode", this.d);
    this.e = a(paramJSONObject, "installNdk", this.e);
    this.c = a(paramJSONObject, "delaySendingAppLoad", this.c);
    this.f = a(paramJSONObject, "shouldCollectLogcat", this.f);
    this.a = a(paramJSONObject, "nativeDumpPath", this.a);
    this.h = a(paramJSONObject, "notificationTitle", this.h);
    this.g = a(paramJSONObject, "installApm", this.g);
  }
  
  private static int a(String paramString)
  {
    int m = 0;
    if (paramString != null) {
      m = paramString.hashCode();
    }
    return m;
  }
  
  private static String a(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    String str = paramString2;
    if (paramJSONObject.has(paramString1)) {}
    try
    {
      str = paramJSONObject.getString(paramString1);
      return str;
    }
    catch (Exception paramJSONObject) {}
    return paramString2;
  }
  
  protected static boolean a(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return paramString2 == null;
    }
    return paramString1.equals(paramString2);
  }
  
  private static boolean a(JSONObject paramJSONObject, String paramString, boolean paramBoolean)
  {
    boolean bool = paramBoolean;
    if (paramJSONObject.has(paramString)) {}
    try
    {
      bool = paramJSONObject.getBoolean(paramString);
      return bool;
    }
    catch (Exception paramJSONObject) {}
    return paramBoolean;
  }
  
  private static final boolean b()
  {
    return (Build.VERSION.SDK_INT >= 10) && (Build.VERSION.SDK_INT <= 21);
  }
  
  public List a()
  {
    return getURLBlacklistPatterns();
  }
  
  public final boolean delaySendingAppLoad()
  {
    return this.c;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof CrittercismConfig)) {}
    do
    {
      return false;
      paramObject = (CrittercismConfig)paramObject;
    } while ((this.c != ((CrittercismConfig)paramObject).c) || (this.f != ((CrittercismConfig)paramObject).f) || (isNdkCrashReportingEnabled() != ((CrittercismConfig)paramObject).isNdkCrashReportingEnabled()) || (isOptmzEnabled() != ((CrittercismConfig)paramObject).isOptmzEnabled()) || (isVersionCodeToBeIncludedInVersionString() != ((CrittercismConfig)paramObject).isVersionCodeToBeIncludedInVersionString()) || (!a(this.b, ((CrittercismConfig)paramObject).b)) || (!a(this.h, ((CrittercismConfig)paramObject).h)) || (!a(this.a, ((CrittercismConfig)paramObject).a)) || (!this.j.equals(((CrittercismConfig)paramObject).j)) || (!this.k.equals(((CrittercismConfig)paramObject).k)) || (!a(this.i, ((CrittercismConfig)paramObject).i)));
    return true;
  }
  
  public final String getCustomVersionName()
  {
    return this.b;
  }
  
  public List getPreserveQueryStringPatterns()
  {
    return new LinkedList(this.k);
  }
  
  public final String getRateMyAppTestTarget()
  {
    return this.i;
  }
  
  public List getURLBlacklistPatterns()
  {
    return new LinkedList(this.j);
  }
  
  public int hashCode()
  {
    int i3 = 1;
    int i4 = a(this.b);
    int i5 = a(this.h);
    int i6 = a(this.a);
    int i7 = a(this.i);
    int i8 = this.j.hashCode();
    int i9 = this.k.hashCode();
    int m;
    int n;
    label79:
    int i1;
    label88:
    int i2;
    if (this.c)
    {
      m = 1;
      if (!this.f) {
        break label176;
      }
      n = 1;
      if (!isNdkCrashReportingEnabled()) {
        break label181;
      }
      i1 = 1;
      if (!isOptmzEnabled()) {
        break label186;
      }
      i2 = 1;
      label98:
      if (!isVersionCodeToBeIncludedInVersionString()) {
        break label192;
      }
    }
    for (;;)
    {
      return Integer.valueOf((i2 + (i1 + (n + (m + 0 << 1) << 1) << 1) << 1) + i3).hashCode() + (i9 + (((((i4 + 0) * 31 + i5) * 31 + i6) * 31 + i7) * 31 + i8) * 31) * 31;
      m = 0;
      break;
      label176:
      n = 0;
      break label79;
      label181:
      i1 = 0;
      break label88;
      label186:
      i2 = 0;
      break label98;
      label192:
      i3 = 0;
    }
  }
  
  public final boolean isLogcatReportingEnabled()
  {
    return this.f;
  }
  
  public final boolean isNdkCrashReportingEnabled()
  {
    return this.e;
  }
  
  @Deprecated
  public final boolean isOptmzEnabled()
  {
    return this.g;
  }
  
  public final boolean isServiceMonitoringEnabled()
  {
    return isOptmzEnabled();
  }
  
  public final boolean isVersionCodeToBeIncludedInVersionString()
  {
    return this.d;
  }
  
  public final void setCustomVersionName(String paramString)
  {
    this.b = paramString;
  }
  
  public final void setDelaySendingAppLoad(boolean paramBoolean)
  {
    this.c = paramBoolean;
  }
  
  public final void setLogcatReportingEnabled(boolean paramBoolean)
  {
    this.f = paramBoolean;
  }
  
  public final void setNdkCrashReportingEnabled(boolean paramBoolean)
  {
    this.e = paramBoolean;
  }
  
  @Deprecated
  public final void setOptmzEnabled(boolean paramBoolean)
  {
    if ((!b()) && (paramBoolean))
    {
      dx.a("OPTMZ is currently only allowed for api levels 10 to 21.  APM will not be installed");
      return;
    }
    this.g = paramBoolean;
  }
  
  public void setPreserveQueryStringPatterns(List paramList)
  {
    this.k.clear();
    if (paramList != null) {
      this.k.addAll(paramList);
    }
  }
  
  public final void setRateMyAppTestTarget(String paramString)
  {
    this.i = paramString;
  }
  
  public final void setServiceMonitoringEnabled(boolean paramBoolean)
  {
    setOptmzEnabled(paramBoolean);
  }
  
  public void setURLBlacklistPatterns(List paramList)
  {
    this.j.clear();
    if (paramList != null) {
      this.j.addAll(paramList);
    }
  }
  
  public final void setVersionCodeToBeIncludedInVersionString(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/crittercism/app/CrittercismConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */