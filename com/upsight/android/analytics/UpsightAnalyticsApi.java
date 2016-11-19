package com.upsight.android.analytics;

import android.app.Activity;
import android.content.Intent;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.event.UpsightAnalyticsEvent;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.analytics.provider.UpsightLocationTracker.Data;
import com.upsight.android.analytics.provider.UpsightUserAttributes.Entry;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import java.util.Date;
import java.util.Set;

public abstract interface UpsightAnalyticsApi
{
  public abstract Boolean getBooleanUserAttribute(String paramString);
  
  public abstract Date getDatetimeUserAttribute(String paramString);
  
  public abstract Set<UpsightUserAttributes.Entry> getDefaultUserAttributes();
  
  public abstract Float getFloatUserAttribute(String paramString);
  
  public abstract Integer getIntUserAttribute(String paramString);
  
  public abstract UpsightSessionInfo getLatestSessionInfo();
  
  public abstract boolean getOptOutStatus();
  
  public abstract String getStringUserAttribute(String paramString);
  
  public abstract void purgeLocation();
  
  public abstract void putUserAttribute(String paramString, Boolean paramBoolean);
  
  public abstract void putUserAttribute(String paramString, Float paramFloat);
  
  public abstract void putUserAttribute(String paramString, Integer paramInteger);
  
  public abstract void putUserAttribute(String paramString1, String paramString2);
  
  public abstract void putUserAttribute(String paramString, Date paramDate);
  
  public abstract void record(UpsightAnalyticsEvent paramUpsightAnalyticsEvent);
  
  public abstract void recordSessionless(UpsightAnalyticsEvent paramUpsightAnalyticsEvent);
  
  public abstract void registerDataProvider(UpsightDataProvider paramUpsightDataProvider);
  
  public abstract void setOptOutStatus(boolean paramBoolean);
  
  public abstract void trackActivity(Activity paramActivity, UpsightLifeCycleTracker.ActivityState paramActivityState);
  
  public abstract void trackLocation(UpsightLocationTracker.Data paramData);
  
  public abstract void trackPurchase(int paramInt, String paramString1, double paramDouble1, double paramDouble2, String paramString2, Intent paramIntent, UpsightPublisherData paramUpsightPublisherData)
    throws UpsightException;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/UpsightAnalyticsApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */