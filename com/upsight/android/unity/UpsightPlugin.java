package com.upsight.android.unity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.UpsightGooglePlayHelper;
import com.upsight.android.analytics.UpsightLifeCycleTracker;
import com.upsight.android.analytics.UpsightLifeCycleTracker.ActivityState;
import com.upsight.android.analytics.event.UpsightCustomEvent;
import com.upsight.android.analytics.event.UpsightCustomEvent.Builder;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.event.install.UpsightInstallAttributionEvent;
import com.upsight.android.analytics.event.install.UpsightInstallAttributionEvent.Builder;
import com.upsight.android.analytics.event.milestone.UpsightMilestoneEvent;
import com.upsight.android.analytics.event.milestone.UpsightMilestoneEvent.Builder;
import com.upsight.android.analytics.event.monetization.UpsightMonetizationEvent;
import com.upsight.android.analytics.event.monetization.UpsightMonetizationEvent.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightLocationTracker.Data;
import com.upsight.android.analytics.provider.UpsightOptOutStatus;
import com.upsight.android.analytics.provider.UpsightUserAttributes;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.logger.UpsightLogger.Level;
import com.upsight.android.managedvariables.type.UpsightManagedBoolean;
import com.upsight.android.managedvariables.type.UpsightManagedFloat;
import com.upsight.android.managedvariables.type.UpsightManagedInt;
import com.upsight.android.managedvariables.type.UpsightManagedString;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public class UpsightPlugin
{
  protected static final String TAG = "Upsight-Unity";
  @NonNull
  private Set<IUpsightExtensionManager> mExtensions = new HashSet(2);
  protected UpsightContext mUpsight;
  
  public UpsightPlugin()
  {
    try
    {
      final Activity localActivity = UnityBridge.getActivity();
      this.mUpsight = Upsight.createContext(localActivity);
      this.mUpsight.getLogger().setLogLevel("Upsight", EnumSet.of(UpsightLogger.Level.ERROR));
      UnityBridge.runSafelyOnUiThread(new Runnable()
      {
        public void run()
        {
          UpsightLifeCycleTracker.track(UpsightPlugin.this.mUpsight, localActivity, UpsightLifeCycleTracker.ActivityState.STARTED);
          Log.i("Upsight-Unity", "Upsight initialization finished");
        }
      });
      return;
    }
    catch (Exception localException)
    {
      Log.e("Upsight-Unity", "Critical Error: Exception thrown while initializing. Upsight will NOT work!", localException);
      throw localException;
    }
  }
  
  public static boolean isEnabled()
  {
    return Upsight.isEnabled(UnityBridge.getActivity());
  }
  
  @NonNull
  private static UpsightPublisherData publisherDataFromJsonString(@Nullable String paramString)
  {
    UpsightPublisherData.Builder localBuilder = new UpsightPublisherData.Builder();
    if ((paramString != null) && (paramString.length() > 0)) {}
    for (;;)
    {
      Object localObject;
      try
      {
        paramString = new JSONObject(paramString);
        Iterator localIterator = paramString.keys();
        if (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          try
          {
            localObject = paramString.get(str);
            if (!(localObject instanceof String)) {
              break label99;
            }
            localBuilder.put(str, (String)localObject);
          }
          catch (JSONException localJSONException)
          {
            localJSONException.printStackTrace();
          }
          continue;
        }
        return localBuilder.build();
      }
      catch (JSONException paramString)
      {
        paramString.printStackTrace();
      }
      label99:
      if ((localObject instanceof Float)) {
        localBuilder.put(localJSONException, ((Float)localObject).floatValue());
      } else if ((localObject instanceof Double)) {
        localBuilder.put(localJSONException, ((Double)localObject).doubleValue());
      } else if ((localObject instanceof Long)) {
        localBuilder.put(localJSONException, ((Long)localObject).longValue());
      } else if ((localObject instanceof Boolean)) {
        localBuilder.put(localJSONException, ((Boolean)localObject).booleanValue());
      }
    }
  }
  
  public static void setEnabled(boolean paramBoolean)
  {
    Upsight.setEnabled(UnityBridge.getActivity(), paramBoolean);
  }
  
  @NonNull
  public String getAppToken()
  {
    return this.mUpsight.getApplicationToken();
  }
  
  public int getLatestSessionNumber()
  {
    try
    {
      int i = UpsightSessionInfo.getLatest(this.mUpsight).sessionNumber;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public long getLatestSessionStartTimestamp()
  {
    try
    {
      long l = UpsightSessionInfo.getLatest(this.mUpsight).startTimestamp;
      return l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0L;
  }
  
  public boolean getManagedBool(@NonNull String paramString)
  {
    try
    {
      UpsightManagedBoolean localUpsightManagedBoolean = UpsightManagedBoolean.fetch(this.mUpsight, paramString);
      if (localUpsightManagedBoolean != null) {
        return ((Boolean)localUpsightManagedBoolean.get()).booleanValue();
      }
      Log.e("Upsight-Unity", "Unknown tag " + paramString + " for managed bool, please check your UXM schema");
      return false;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public float getManagedFloat(@NonNull String paramString)
  {
    try
    {
      UpsightManagedFloat localUpsightManagedFloat = UpsightManagedFloat.fetch(this.mUpsight, paramString);
      if (localUpsightManagedFloat != null) {
        return ((Float)localUpsightManagedFloat.get()).floatValue();
      }
      Log.e("Upsight-Unity", "Unknown tag " + paramString + " for managed float, please check your UXM schema");
      return 0.0F;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0.0F;
  }
  
  public int getManagedInt(@NonNull String paramString)
  {
    try
    {
      UpsightManagedInt localUpsightManagedInt = UpsightManagedInt.fetch(this.mUpsight, paramString);
      if (localUpsightManagedInt != null) {
        return ((Integer)localUpsightManagedInt.get()).intValue();
      }
      Log.e("Upsight-Unity", "Unknown tag " + paramString + " for managed int, please check your UXM schema");
      return 0;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0;
  }
  
  @Nullable
  public String getManagedString(@NonNull String paramString)
  {
    try
    {
      UpsightManagedString localUpsightManagedString = UpsightManagedString.fetch(this.mUpsight, paramString);
      if (localUpsightManagedString != null) {
        return (String)localUpsightManagedString.get();
      }
      Log.e("Upsight-Unity", "Unknown tag " + paramString + " for managed string, please check your UXM schema");
      return null;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public boolean getOptOutStatus()
  {
    try
    {
      boolean bool = UpsightOptOutStatus.get(this.mUpsight);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  @NonNull
  public String getPluginVersion()
  {
    return this.mUpsight.getSdkPlugin();
  }
  
  @NonNull
  public String getPublicKey()
  {
    return this.mUpsight.getPublicKey();
  }
  
  @NonNull
  public String getSid()
  {
    return this.mUpsight.getSid();
  }
  
  public boolean getUserAttributesBool(@NonNull String paramString)
  {
    try
    {
      paramString = UpsightUserAttributes.getBoolean(this.mUpsight, paramString);
      if (paramString != null)
      {
        boolean bool = paramString.booleanValue();
        return bool;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public long getUserAttributesDatetime(@NonNull String paramString)
  {
    try
    {
      paramString = UpsightUserAttributes.getDatetime(this.mUpsight, paramString);
      if (paramString != null)
      {
        long l = paramString.getTime();
        l = TimeUnit.SECONDS.convert(l, TimeUnit.MILLISECONDS);
        return l;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0L;
  }
  
  public float getUserAttributesFloat(@NonNull String paramString)
  {
    try
    {
      paramString = UpsightUserAttributes.getFloat(this.mUpsight, paramString);
      if (paramString != null)
      {
        float f = paramString.floatValue();
        return f;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0.0F;
  }
  
  public int getUserAttributesInt(@NonNull String paramString)
  {
    try
    {
      paramString = UpsightUserAttributes.getInteger(this.mUpsight, paramString);
      if (paramString != null)
      {
        int i = paramString.intValue();
        return i;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return 0;
  }
  
  @Nullable
  public String getUserAttributesString(@NonNull String paramString)
  {
    try
    {
      paramString = UpsightUserAttributes.getString(this.mUpsight, paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public void onApplicationPaused()
  {
    Iterator localIterator = this.mExtensions.iterator();
    while (localIterator.hasNext()) {
      ((IUpsightExtensionManager)localIterator.next()).onApplicationPaused();
    }
  }
  
  public void onApplicationResumed()
  {
    Iterator localIterator = this.mExtensions.iterator();
    while (localIterator.hasNext()) {
      ((IUpsightExtensionManager)localIterator.next()).onApplicationResumed();
    }
  }
  
  public void purgeLocation()
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightLocationTracker.purge(UpsightPlugin.this.mUpsight);
      }
    });
  }
  
  public void recordAnalyticsEvent(@NonNull final String paramString1, @NonNull final String paramString2)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightCustomEvent.Builder localBuilder = UpsightCustomEvent.createBuilder(paramString1);
        localBuilder.put(UpsightPlugin.publisherDataFromJsonString(paramString2));
        localBuilder.record(UpsightPlugin.this.mUpsight);
      }
    });
  }
  
  public void recordAttributionEvent(@Nullable final String paramString1, @Nullable final String paramString2, @Nullable final String paramString3, @Nullable final String paramString4)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightInstallAttributionEvent.createBuilder().setAttributionCampaign(paramString1).setAttributionCreative(paramString2).setAttributionSource(paramString3).put(UpsightPlugin.publisherDataFromJsonString(paramString4)).record(UpsightPlugin.this.mUpsight);
      }
    });
  }
  
  public void recordGooglePlayPurchase(final int paramInt1, @NonNull final String paramString1, final double paramDouble1, double paramDouble2, @NonNull final String paramString2, final int paramInt2, @NonNull final String paramString3, @NonNull final String paramString4, @NonNull final String paramString5)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightPublisherData.Builder localBuilder = new UpsightPublisherData.Builder();
        localBuilder.put(UpsightPlugin.publisherDataFromJsonString(paramString5));
        try
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("RESPONSE_CODE", paramInt2);
          localIntent.putExtra("INAPP_PURCHASE_DATA", paramString3);
          localIntent.putExtra("INAPP_DATA_SIGNATURE", paramString4);
          UpsightGooglePlayHelper.trackPurchase(UpsightPlugin.this.mUpsight, paramInt1, paramString1, paramDouble1, paramString2, this.val$product, localIntent, localBuilder.build());
          return;
        }
        catch (UpsightException localUpsightException)
        {
          Log.i("Upsight-Unity", "Failed to recordGooglePlayPurchase: " + localUpsightException.getMessage());
          localUpsightException.printStackTrace();
        }
      }
    });
  }
  
  public void recordMilestoneEvent(@NonNull final String paramString1, @NonNull final String paramString2)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightMilestoneEvent.Builder localBuilder = UpsightMilestoneEvent.createBuilder(paramString1);
        localBuilder.put(UpsightPlugin.publisherDataFromJsonString(paramString2));
        localBuilder.record(UpsightPlugin.this.mUpsight);
      }
    });
  }
  
  public void recordMonetizationEvent(final double paramDouble1, @NonNull String paramString1, @Nullable final String paramString2, final double paramDouble2, @Nullable final String paramString3, int paramInt, @Nullable final String paramString4)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightMonetizationEvent.Builder localBuilder = UpsightMonetizationEvent.createBuilder(Double.valueOf(paramDouble1), paramString4);
        localBuilder.put(UpsightPlugin.publisherDataFromJsonString(paramString2));
        if (paramDouble2 != null) {
          localBuilder.setProduct(paramDouble2);
        }
        if (paramString3 >= 0.0D) {
          localBuilder.setPrice(Double.valueOf(paramString3));
        }
        if (this.val$resolution != null) {
          localBuilder.setResolution(this.val$resolution);
        }
        if (this.val$quantity > 0) {
          localBuilder.setQuantity(Integer.valueOf(this.val$quantity));
        }
        localBuilder.record(UpsightPlugin.this.mUpsight);
      }
    });
  }
  
  public void recordSessionlessAnalyticsEvent(@NonNull final String paramString1, @NonNull final String paramString2)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightCustomEvent.Builder localBuilder = UpsightCustomEvent.createBuilder(paramString1);
        localBuilder.put(UpsightPlugin.publisherDataFromJsonString(paramString2));
        localBuilder.recordSessionless(UpsightPlugin.this.mUpsight);
      }
    });
  }
  
  public void registerExtension(IUpsightExtensionManager paramIUpsightExtensionManager)
  {
    if (this.mExtensions.add(paramIUpsightExtensionManager)) {
      paramIUpsightExtensionManager.init(this.mUpsight);
    }
  }
  
  public void setLocation(final double paramDouble1, double paramDouble2)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightLocationTracker.Data localData = UpsightLocationTracker.Data.create(paramDouble1, this.val$lon);
        UpsightLocationTracker.track(UpsightPlugin.this.mUpsight, localData);
      }
    });
  }
  
  public void setLoggerLevel(@NonNull String paramString)
  {
    try
    {
      if (paramString.toLowerCase().equals("verbose"))
      {
        Log.i("Upsight-Unity", "enabling verbose logs");
        this.mUpsight.getLogger().setLogLevel(".*", EnumSet.allOf(UpsightLogger.Level.class));
        return;
      }
      this.mUpsight.getLogger().setLogLevel("Upsight", EnumSet.of(UpsightLogger.Level.valueOf(paramString)));
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void setOptOutStatus(boolean paramBoolean)
  {
    try
    {
      UpsightOptOutStatus.set(this.mUpsight, paramBoolean);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void setUserAttributesBool(@NonNull final String paramString, final boolean paramBoolean)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightUserAttributes.put(UpsightPlugin.this.mUpsight, paramString, Boolean.valueOf(paramBoolean));
      }
    });
  }
  
  public void setUserAttributesDatetime(@NonNull String paramString, final long paramLong)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        long l = TimeUnit.MILLISECONDS.convert(paramLong, TimeUnit.SECONDS);
        UpsightUserAttributes.put(UpsightPlugin.this.mUpsight, this.val$key, new Date(l));
      }
    });
  }
  
  public void setUserAttributesFloat(@NonNull final String paramString, final float paramFloat)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightUserAttributes.put(UpsightPlugin.this.mUpsight, paramString, Float.valueOf(paramFloat));
      }
    });
  }
  
  public void setUserAttributesInt(@NonNull final String paramString, final int paramInt)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightUserAttributes.put(UpsightPlugin.this.mUpsight, paramString, Integer.valueOf(paramInt));
      }
    });
  }
  
  public void setUserAttributesString(@NonNull final String paramString1, @NonNull final String paramString2)
  {
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        UpsightUserAttributes.put(UpsightPlugin.this.mUpsight, paramString1, paramString2);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/UpsightPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */