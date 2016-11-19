package com.upsight.android.analytics.provider;

import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.logger.UpsightLogger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class UpsightUserAttributes
{
  public static final String DATETIME_NULL = "9999-12-31T23:59:59";
  public static final long DATETIME_NULL_S = 253402300799L;
  protected static final String TYPE_BOOLEAN = "boolean";
  protected static final String TYPE_DATETIME = "datetime";
  protected static final String TYPE_FLOAT = "float";
  protected static final String TYPE_INTEGER = "integer";
  protected static final String TYPE_STRING = "string";
  public static final String USER_ATTRIBUTES_PREFIX = "com.upsight.user_attribute.";
  
  public static Boolean getBoolean(UpsightContext paramUpsightContext, String paramString)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getBooleanUserAttribute(paramString);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return null;
  }
  
  public static Boolean getBoolean(UpsightSessionContext paramUpsightSessionContext, String paramString)
  {
    return getBoolean(paramUpsightSessionContext.getUpsightContext(), paramString);
  }
  
  public static Date getDatetime(UpsightContext paramUpsightContext, String paramString)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getDatetimeUserAttribute(paramString);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return null;
  }
  
  public static Date getDatetime(UpsightSessionContext paramUpsightSessionContext, String paramString)
  {
    return getDatetime(paramUpsightSessionContext.getUpsightContext(), paramString);
  }
  
  public static Set<Entry> getDefault(UpsightContext paramUpsightContext)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getDefaultUserAttributes();
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return new HashSet();
  }
  
  public static Set<Entry> getDefault(UpsightSessionContext paramUpsightSessionContext)
  {
    return getDefault(paramUpsightSessionContext.getUpsightContext());
  }
  
  public static Float getFloat(UpsightContext paramUpsightContext, String paramString)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getFloatUserAttribute(paramString);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return null;
  }
  
  public static Float getFloat(UpsightSessionContext paramUpsightSessionContext, String paramString)
  {
    return getFloat(paramUpsightSessionContext.getUpsightContext(), paramString);
  }
  
  public static Integer getInteger(UpsightContext paramUpsightContext, String paramString)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getIntUserAttribute(paramString);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return null;
  }
  
  public static Integer getInteger(UpsightSessionContext paramUpsightSessionContext, String paramString)
  {
    return getInteger(paramUpsightSessionContext.getUpsightContext(), paramString);
  }
  
  public static String getString(UpsightContext paramUpsightContext, String paramString)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null) {
      return localUpsightAnalyticsExtension.getApi().getStringUserAttribute(paramString);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
    return null;
  }
  
  public static String getString(UpsightSessionContext paramUpsightSessionContext, String paramString)
  {
    return getString(paramUpsightSessionContext.getUpsightContext(), paramString);
  }
  
  public static void put(UpsightContext paramUpsightContext, String paramString, Boolean paramBoolean)
    throws IllegalArgumentException
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().putUserAttribute(paramString, paramBoolean);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void put(UpsightContext paramUpsightContext, String paramString, Float paramFloat)
    throws IllegalArgumentException
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().putUserAttribute(paramString, paramFloat);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void put(UpsightContext paramUpsightContext, String paramString, Integer paramInteger)
    throws IllegalArgumentException
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().putUserAttribute(paramString, paramInteger);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void put(UpsightContext paramUpsightContext, String paramString1, String paramString2)
    throws IllegalArgumentException
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().putUserAttribute(paramString1, paramString2);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void put(UpsightContext paramUpsightContext, String paramString, Date paramDate)
    throws IllegalArgumentException
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().putUserAttribute(paramString, paramDate);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void put(UpsightSessionContext paramUpsightSessionContext, String paramString, Boolean paramBoolean)
    throws IllegalArgumentException
  {
    put(paramUpsightSessionContext.getUpsightContext(), paramString, paramBoolean);
  }
  
  public static void put(UpsightSessionContext paramUpsightSessionContext, String paramString, Float paramFloat)
    throws IllegalArgumentException
  {
    put(paramUpsightSessionContext.getUpsightContext(), paramString, paramFloat);
  }
  
  public static void put(UpsightSessionContext paramUpsightSessionContext, String paramString, Integer paramInteger)
    throws IllegalArgumentException
  {
    put(paramUpsightSessionContext.getUpsightContext(), paramString, paramInteger);
  }
  
  public static void put(UpsightSessionContext paramUpsightSessionContext, String paramString1, String paramString2)
    throws IllegalArgumentException
  {
    put(paramUpsightSessionContext.getUpsightContext(), paramString1, paramString2);
  }
  
  public static void put(UpsightSessionContext paramUpsightSessionContext, String paramString, Date paramDate)
    throws IllegalArgumentException
  {
    put(paramUpsightSessionContext.getUpsightContext(), paramString, paramDate);
  }
  
  public abstract Boolean getBoolean(String paramString);
  
  public abstract Date getDatetime(String paramString);
  
  public abstract Set<Entry> getDefault();
  
  public abstract Float getFloat(String paramString);
  
  public abstract Integer getInt(String paramString);
  
  public abstract String getString(String paramString);
  
  public abstract void put(String paramString, Boolean paramBoolean);
  
  public abstract void put(String paramString, Float paramFloat);
  
  public abstract void put(String paramString, Integer paramInteger);
  
  public abstract void put(String paramString1, String paramString2);
  
  public abstract void put(String paramString, Date paramDate);
  
  public static class Entry
  {
    private Object mDefaultValue;
    private String mKey;
    
    public Entry(String paramString, Object paramObject)
    {
      this.mKey = paramString;
      this.mDefaultValue = paramObject;
    }
    
    public Object getDefaultValue()
    {
      return this.mDefaultValue;
    }
    
    public String getKey()
    {
      return this.mKey;
    }
    
    public Class getType()
    {
      return this.mDefaultValue.getClass();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/provider/UpsightUserAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */