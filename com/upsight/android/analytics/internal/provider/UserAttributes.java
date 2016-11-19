package com.upsight.android.analytics.internal.provider;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.provider.UpsightUserAttributes;
import com.upsight.android.analytics.provider.UpsightUserAttributes.Entry;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class UserAttributes
  extends UpsightUserAttributes
{
  private static final Pattern DATETIME_DEFAULT_VALUE_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}");
  private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
  private static final String TIMEZONE_UTC = "+0000";
  private static final Pattern USER_ATTRIBUTE_PATTERN = Pattern.compile("com\\.upsight\\.user_attribute\\.(string|boolean|integer|float|datetime)\\.([a-zA-Z0-9_]+)");
  private static final Pattern USER_ATTRIBUTE_PATTERN_INFER = Pattern.compile("com\\.upsight\\.user_attribute\\.([a-zA-Z0-9_]+)");
  private UpsightLogger mLogger;
  private UpsightContext mUpsight;
  private Map<String, UpsightUserAttributes.Entry> mUserAttributes = new HashMap();
  private Set<UpsightUserAttributes.Entry> mUserAttributesSet = new HashSet();
  
  @Inject
  UserAttributes(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
    this.mLogger = paramUpsightContext.getLogger();
    loadDefaultAttributes();
  }
  
  private void loadDefaultAttributes()
  {
    try
    {
      Bundle localBundle = this.mUpsight.getPackageManager().getApplicationInfo(this.mUpsight.getPackageName(), 128).metaData;
      if (localBundle != null)
      {
        Iterator localIterator = localBundle.keySet().iterator();
        while (localIterator.hasNext())
        {
          Object localObject = (String)localIterator.next();
          localObject = createEntry((String)localObject, localBundle.get((String)localObject));
          if (localObject != null)
          {
            this.mUserAttributes.put(((UpsightUserAttributes.Entry)localObject).getKey(), localObject);
            this.mUserAttributesSet.add(localObject);
          }
        }
      }
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      this.mLogger.e("Upsight", "Unexpected error: Package name missing!?", new Object[] { localNameNotFoundException });
    }
  }
  
  UpsightUserAttributes.Entry createEntry(String paramString, Object paramObject)
    throws IllegalArgumentException
  {
    Object localObject1 = null;
    Object localObject2 = USER_ATTRIBUTE_PATTERN.matcher(paramString);
    String str;
    if (((Matcher)localObject2).matches())
    {
      str = ((Matcher)localObject2).group(1);
      localObject2 = ((Matcher)localObject2).group(2);
      localObject1 = null;
      if ("string".equals(str))
      {
        localObject1 = String.valueOf(paramObject);
        paramString = new UpsightUserAttributes.Entry((String)localObject2, localObject1);
      }
    }
    do
    {
      return paramString;
      if (("boolean".equals(str)) || ("integer".equals(str)) || ("float".equals(str)))
      {
        localObject1 = paramObject;
        break;
      }
      if (!"datetime".equals(str)) {
        break;
      }
      paramObject = (String)paramObject;
      if (DATETIME_DEFAULT_VALUE_PATTERN.matcher((CharSequence)paramObject).matches())
      {
        localObject1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        try
        {
          localObject1 = ((SimpleDateFormat)localObject1).parse((String)paramObject + "+0000");
        }
        catch (ParseException paramObject)
        {
          paramString = String.format("Failed to parse default value of %sdatetime.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString });
          this.mLogger.e("Upsight", paramString, new Object[] { paramObject });
          throw new IllegalArgumentException(paramString, (Throwable)paramObject);
        }
      }
      paramString = String.format("Invalid format for the default value of %sdatetime.%s in the Android Manifest. It must match %s (e.g. 1970-01-01T00:00:00)", new Object[] { "com.upsight.user_attribute.", paramString, "yyyy-MM-dd'T'HH:mm:ssZ" });
      this.mLogger.e("Upsight", paramString, new Object[0]);
      throw new IllegalArgumentException(paramString);
      localObject2 = USER_ATTRIBUTE_PATTERN_INFER.matcher(paramString);
      paramString = (String)localObject1;
    } while (!((Matcher)localObject2).matches());
    return new UpsightUserAttributes.Entry(((Matcher)localObject2).group(1), paramObject);
  }
  
  public Boolean getBoolean(String paramString)
  {
    if (!this.mUserAttributes.containsKey(paramString))
    {
      this.mLogger.w("Upsight", String.format("No metadata found with android:name %sboolean.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("No metadata found with android:name %sboolean.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
    }
    if (!Boolean.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
    {
      this.mLogger.w("Upsight", String.format("The user attribute %s must be of type boolean", new Object[] { paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("The user attribute %s must be of type boolean", new Object[] { paramString }));
    }
    return Boolean.valueOf(PreferencesHelper.getBoolean(this.mUpsight, "com.upsight.user_attribute." + paramString, ((Boolean)((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getDefaultValue()).booleanValue()));
  }
  
  public Date getDatetime(String paramString)
  {
    if (!this.mUserAttributes.containsKey(paramString))
    {
      this.mLogger.w("Upsight", String.format("No metadata found with android:name %sdatetime.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("No metadata found with android:name %sdatetime.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
    }
    if (!Date.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
    {
      this.mLogger.w("Upsight", String.format("The user attribute %s must be of type datetime", new Object[] { paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("The user attribute %s must be of type datetime", new Object[] { paramString }));
    }
    Date localDate = (Date)((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getDefaultValue();
    long l = TimeUnit.SECONDS.convert(localDate.getTime(), TimeUnit.MILLISECONDS);
    l = PreferencesHelper.getLong(this.mUpsight, "com.upsight.user_attribute." + paramString, l);
    return new Date(TimeUnit.MILLISECONDS.convert(l, TimeUnit.SECONDS));
  }
  
  public Set<UpsightUserAttributes.Entry> getDefault()
  {
    return new HashSet(this.mUserAttributesSet);
  }
  
  public Float getFloat(String paramString)
  {
    if (!this.mUserAttributes.containsKey(paramString))
    {
      this.mLogger.w("Upsight", String.format("No metadata found with android:name %sfloat.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("No metadata found with android:name %sfloat.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
    }
    if (!Float.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
    {
      this.mLogger.w("Upsight", String.format("The user attribute %s must be of type float", new Object[] { paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("The user attribute %s must be of type float", new Object[] { paramString }));
    }
    return Float.valueOf(PreferencesHelper.getFloat(this.mUpsight, "com.upsight.user_attribute." + paramString, ((Float)((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getDefaultValue()).floatValue()));
  }
  
  public Integer getInt(String paramString)
  {
    if (!this.mUserAttributes.containsKey(paramString))
    {
      this.mLogger.w("Upsight", String.format("No metadata found with android:name %sinteger.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("No metadata found with android:name %sinteger.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
    }
    if (!Integer.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
    {
      this.mLogger.w("Upsight", String.format("The user attribute %s must be of type integer", new Object[] { paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("The user attribute %s must be of type integer", new Object[] { paramString }));
    }
    return Integer.valueOf(PreferencesHelper.getInt(this.mUpsight, "com.upsight.user_attribute." + paramString, ((Integer)((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getDefaultValue()).intValue()));
  }
  
  public String getString(String paramString)
  {
    if (!this.mUserAttributes.containsKey(paramString))
    {
      this.mLogger.w("Upsight", String.format("No metadata found with android:name %sstring.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("No metadata found with android:name %sstring.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
    }
    if (!String.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
    {
      this.mLogger.w("Upsight", String.format("The user attribute %s must be of type string", new Object[] { paramString }), new Object[0]);
      throw new IllegalArgumentException(String.format("The user attribute %s must be of type string", new Object[] { paramString }));
    }
    return PreferencesHelper.getString(this.mUpsight, "com.upsight.user_attribute." + paramString, (String)((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getDefaultValue());
  }
  
  public void put(String paramString, Boolean paramBoolean)
  {
    if (paramBoolean == null)
    {
      PreferencesHelper.clear(this.mUpsight, "com.upsight.user_attribute." + paramString);
      return;
    }
    if (this.mUserAttributes.containsKey(paramString))
    {
      if (!Boolean.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
      {
        this.mLogger.w("Upsight", String.format("The user attribute %s must be of type boolean", new Object[] { paramString }), new Object[0]);
        throw new IllegalArgumentException(String.format("The user attribute %s must be of type boolean", new Object[] { paramString }));
      }
      PreferencesHelper.putBoolean(this.mUpsight, "com.upsight.user_attribute." + paramString, paramBoolean.booleanValue());
      return;
    }
    this.mLogger.w("Upsight", String.format("No metadata found with android:name %sboolean.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
    throw new IllegalArgumentException(String.format("No metadata found with android:name %sboolean.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
  }
  
  public void put(String paramString, Float paramFloat)
  {
    if (paramFloat == null)
    {
      PreferencesHelper.clear(this.mUpsight, "com.upsight.user_attribute." + paramString);
      return;
    }
    if (this.mUserAttributes.containsKey(paramString))
    {
      if (!Float.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
      {
        this.mLogger.w("Upsight", String.format("The user attribute %s must be of type float", new Object[] { paramString }), new Object[0]);
        throw new IllegalArgumentException(String.format("The user attribute %s must be of type float", new Object[] { paramString }));
      }
      PreferencesHelper.putFloat(this.mUpsight, "com.upsight.user_attribute." + paramString, paramFloat.floatValue());
      return;
    }
    this.mLogger.w("Upsight", String.format("No metadata found with android:name %sfloat.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
    throw new IllegalArgumentException(String.format("No metadata found with android:name %sfloat.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
  }
  
  public void put(String paramString, Integer paramInteger)
  {
    if (paramInteger == null)
    {
      PreferencesHelper.clear(this.mUpsight, "com.upsight.user_attribute." + paramString);
      return;
    }
    if (this.mUserAttributes.containsKey(paramString))
    {
      if (!Integer.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
      {
        this.mLogger.w("Upsight", String.format("The user attribute %s must be of type integer", new Object[] { paramString }), new Object[0]);
        throw new IllegalArgumentException(String.format("The user attribute %s must be of type integer", new Object[] { paramString }));
      }
      PreferencesHelper.putInt(this.mUpsight, "com.upsight.user_attribute." + paramString, paramInteger.intValue());
      return;
    }
    this.mLogger.w("Upsight", String.format("No metadata found with android:name %sinteger.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
    throw new IllegalArgumentException(String.format("No metadata found with android:name %sinteger.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
  }
  
  public void put(String paramString1, String paramString2)
    throws IllegalArgumentException
  {
    if (paramString2 == null)
    {
      PreferencesHelper.clear(this.mUpsight, "com.upsight.user_attribute." + paramString1);
      return;
    }
    if (this.mUserAttributes.containsKey(paramString1))
    {
      if (!String.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString1)).getType()))
      {
        this.mLogger.w("Upsight", String.format("The user attribute %s must be of type string", new Object[] { paramString1 }), new Object[0]);
        throw new IllegalArgumentException(String.format("The user attribute %s must be of type string", new Object[] { paramString1 }));
      }
      PreferencesHelper.putString(this.mUpsight, "com.upsight.user_attribute." + paramString1, paramString2);
      return;
    }
    this.mLogger.w("Upsight", String.format("No metadata found with android:name %sstring.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString1 }), new Object[0]);
    throw new IllegalArgumentException(String.format("No metadata found with android:name %sstring.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString1 }));
  }
  
  public void put(String paramString, Date paramDate)
  {
    if (paramDate == null)
    {
      PreferencesHelper.clear(this.mUpsight, "com.upsight.user_attribute." + paramString);
      return;
    }
    if (this.mUserAttributes.containsKey(paramString))
    {
      if (!Date.class.equals(((UpsightUserAttributes.Entry)this.mUserAttributes.get(paramString)).getType()))
      {
        this.mLogger.w("Upsight", String.format("The user attribute %s must be of type datetime", new Object[] { paramString }), new Object[0]);
        throw new IllegalArgumentException(String.format("The user attribute %s must be of type datetime", new Object[] { paramString }));
      }
      long l = TimeUnit.SECONDS.convert(paramDate.getTime(), TimeUnit.MILLISECONDS);
      PreferencesHelper.putLong(this.mUpsight, "com.upsight.user_attribute." + paramString, l);
      return;
    }
    this.mLogger.w("Upsight", String.format("No metadata found with android:name %sdatetime.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }), new Object[0]);
    throw new IllegalArgumentException(String.format("No metadata found with android:name %sdatetime.%s in the Android Manifest", new Object[] { "com.upsight.user_attribute.", paramString }));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/UserAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */