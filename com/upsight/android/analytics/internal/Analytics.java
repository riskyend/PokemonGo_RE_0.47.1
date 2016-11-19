package com.upsight.android.analytics.internal;

import android.app.Activity;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.analytics.UpsightGooglePlayHelper;
import com.upsight.android.analytics.UpsightLifeCycleTracker;
import com.upsight.android.analytics.UpsightLifeCycleTracker.ActivityState;
import com.upsight.android.analytics.event.UpsightAnalyticsEvent;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.internal.association.AssociationManager;
import com.upsight.android.analytics.internal.dispatcher.schema.SchemaSelectorBuilder;
import com.upsight.android.analytics.internal.session.Session;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.analytics.internal.session.StandardSessionInitializer;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightLocationTracker.Data;
import com.upsight.android.analytics.provider.UpsightOptOutStatus;
import com.upsight.android.analytics.provider.UpsightUserAttributes;
import com.upsight.android.analytics.provider.UpsightUserAttributes.Entry;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class Analytics
  implements UpsightAnalyticsApi
{
  private static final String SEQUENCE_ID_FIELD_NAME = "seq_id";
  private static final String USER_ATTRIBUTES_FIELD_NAME = "user_attributes";
  private final AssociationManager mAssociationManager;
  private final UpsightDataStore mDataStore;
  private final Set<UpsightUserAttributes.Entry> mDefaultUserAttributes;
  private final UpsightGooglePlayHelper mGooglePlayHelper;
  private final Gson mGson;
  private final UpsightLifeCycleTracker mLifeCycleTracker;
  private final UpsightLocationTracker mLocationTracker;
  private final UpsightOptOutStatus mOptOutStatus;
  private final SchemaSelectorBuilder mSchemaSelector;
  private final SessionManager mSessionManager;
  private final UpsightContext mUpsight;
  private final UpsightUserAttributes mUserAttributes;
  
  @Inject
  public Analytics(UpsightContext paramUpsightContext, UpsightLifeCycleTracker paramUpsightLifeCycleTracker, SessionManager paramSessionManager, SchemaSelectorBuilder paramSchemaSelectorBuilder, AssociationManager paramAssociationManager, UpsightOptOutStatus paramUpsightOptOutStatus, UpsightLocationTracker paramUpsightLocationTracker, UpsightUserAttributes paramUpsightUserAttributes, UpsightGooglePlayHelper paramUpsightGooglePlayHelper)
  {
    this.mUpsight = paramUpsightContext;
    this.mDataStore = paramUpsightContext.getDataStore();
    this.mLifeCycleTracker = paramUpsightLifeCycleTracker;
    this.mSessionManager = paramSessionManager;
    this.mGson = paramUpsightContext.getCoreComponent().gson();
    this.mSchemaSelector = paramSchemaSelectorBuilder;
    this.mAssociationManager = paramAssociationManager;
    this.mOptOutStatus = paramUpsightOptOutStatus;
    this.mLocationTracker = paramUpsightLocationTracker;
    this.mUserAttributes = paramUpsightUserAttributes;
    this.mDefaultUserAttributes = this.mUserAttributes.getDefault();
    this.mGooglePlayHelper = paramUpsightGooglePlayHelper;
  }
  
  private void appendAssociationData(String paramString, JsonObject paramJsonObject)
  {
    this.mAssociationManager.associate(paramString, paramJsonObject);
  }
  
  private JsonElement getAllAsJsonElement(Set<UpsightUserAttributes.Entry> paramSet)
  {
    JsonObject localJsonObject = new JsonObject();
    paramSet = paramSet.iterator();
    while (paramSet.hasNext())
    {
      UpsightUserAttributes.Entry localEntry = (UpsightUserAttributes.Entry)paramSet.next();
      if (String.class.equals(localEntry.getType()))
      {
        localJsonObject.addProperty(localEntry.getKey(), PreferencesHelper.getString(this.mUpsight, "com.upsight.user_attribute." + localEntry.getKey(), (String)localEntry.getDefaultValue()));
      }
      else if (Integer.class.equals(localEntry.getType()))
      {
        localJsonObject.addProperty(localEntry.getKey(), Integer.valueOf(PreferencesHelper.getInt(this.mUpsight, "com.upsight.user_attribute." + localEntry.getKey(), ((Integer)localEntry.getDefaultValue()).intValue())));
      }
      else if (Boolean.class.equals(localEntry.getType()))
      {
        localJsonObject.addProperty(localEntry.getKey(), Boolean.valueOf(PreferencesHelper.getBoolean(this.mUpsight, "com.upsight.user_attribute." + localEntry.getKey(), ((Boolean)localEntry.getDefaultValue()).booleanValue())));
      }
      else if (Float.class.equals(localEntry.getType()))
      {
        localJsonObject.addProperty(localEntry.getKey(), Float.valueOf(PreferencesHelper.getFloat(this.mUpsight, "com.upsight.user_attribute." + localEntry.getKey(), ((Float)localEntry.getDefaultValue()).floatValue())));
      }
      else if (Date.class.equals(localEntry.getType()))
      {
        Date localDate = (Date)localEntry.getDefaultValue();
        long l = TimeUnit.SECONDS.convert(localDate.getTime(), TimeUnit.MILLISECONDS);
        l = PreferencesHelper.getLong(this.mUpsight, "com.upsight.user_attribute." + localEntry.getKey(), l);
        if (l != 253402300799L) {
          localJsonObject.addProperty(localEntry.getKey(), Long.valueOf(l));
        } else {
          localJsonObject.addProperty(localEntry.getKey(), (Long)null);
        }
      }
    }
    return localJsonObject;
  }
  
  private void record(UpsightAnalyticsEvent paramUpsightAnalyticsEvent, Session paramSession)
  {
    if (!Upsight.isEnabled(this.mUpsight))
    {
      this.mUpsight.getLogger().d("Upsight", "Recording of " + paramUpsightAnalyticsEvent.getType() + " event failed because SDK is disabled", new Object[0]);
      return;
    }
    Long localLong = paramSession.getStartTimestamp();
    Integer localInteger1 = paramSession.getMessageID();
    Integer localInteger2 = paramSession.getCampaignID();
    Integer localInteger3 = paramSession.getSessionNumber();
    paramSession = paramSession.getPreviousTos();
    JsonObject localJsonObject = toJsonElement(paramUpsightAnalyticsEvent);
    appendAssociationData(paramUpsightAnalyticsEvent.getType(), localJsonObject);
    paramSession = DataStoreRecord.create(DataStoreRecord.Action.Created, localLong, localInteger1, localInteger2, localInteger3, paramSession, localJsonObject.toString(), paramUpsightAnalyticsEvent.getType());
    if ((paramUpsightAnalyticsEvent instanceof DynamicIdentifiers)) {
      paramSession.setIdentifiers(((DynamicIdentifiers)paramUpsightAnalyticsEvent).getIdentifiersName());
    }
    this.mDataStore.store(paramSession);
  }
  
  private JsonObject toJsonElement(UpsightAnalyticsEvent paramUpsightAnalyticsEvent)
  {
    paramUpsightAnalyticsEvent = this.mGson.toJsonTree(paramUpsightAnalyticsEvent).getAsJsonObject();
    paramUpsightAnalyticsEvent.addProperty("seq_id", Long.valueOf(EventSequenceId.getAndIncrement(this.mUpsight)));
    paramUpsightAnalyticsEvent.add("user_attributes", getAllAsJsonElement(this.mDefaultUserAttributes));
    return paramUpsightAnalyticsEvent;
  }
  
  public Boolean getBooleanUserAttribute(String paramString)
  {
    return this.mUserAttributes.getBoolean(paramString);
  }
  
  public Date getDatetimeUserAttribute(String paramString)
  {
    return this.mUserAttributes.getDatetime(paramString);
  }
  
  public Set<UpsightUserAttributes.Entry> getDefaultUserAttributes()
  {
    return this.mUserAttributes.getDefault();
  }
  
  public Float getFloatUserAttribute(String paramString)
  {
    return this.mUserAttributes.getFloat(paramString);
  }
  
  public Integer getIntUserAttribute(String paramString)
  {
    return this.mUserAttributes.getInt(paramString);
  }
  
  public UpsightSessionInfo getLatestSessionInfo()
  {
    return this.mSessionManager.getLatestSessionInfo();
  }
  
  public boolean getOptOutStatus()
  {
    return this.mOptOutStatus.get();
  }
  
  public String getStringUserAttribute(String paramString)
  {
    return this.mUserAttributes.getString(paramString);
  }
  
  public void purgeLocation()
  {
    this.mLocationTracker.purge();
  }
  
  public void putUserAttribute(String paramString, Boolean paramBoolean)
  {
    this.mUserAttributes.put(paramString, paramBoolean);
  }
  
  public void putUserAttribute(String paramString, Float paramFloat)
  {
    this.mUserAttributes.put(paramString, paramFloat);
  }
  
  public void putUserAttribute(String paramString, Integer paramInteger)
  {
    this.mUserAttributes.put(paramString, paramInteger);
  }
  
  public void putUserAttribute(String paramString1, String paramString2)
  {
    this.mUserAttributes.put(paramString1, paramString2);
  }
  
  public void putUserAttribute(String paramString, Date paramDate)
  {
    this.mUserAttributes.put(paramString, paramDate);
  }
  
  public void record(UpsightAnalyticsEvent paramUpsightAnalyticsEvent)
  {
    record(paramUpsightAnalyticsEvent, this.mSessionManager.getSession());
  }
  
  public void recordSessionless(UpsightAnalyticsEvent paramUpsightAnalyticsEvent)
  {
    record(paramUpsightAnalyticsEvent, this.mSessionManager.getBackgroundSession());
  }
  
  public void registerDataProvider(UpsightDataProvider paramUpsightDataProvider)
  {
    this.mSchemaSelector.registerDataProvider(paramUpsightDataProvider);
  }
  
  public void setOptOutStatus(boolean paramBoolean)
  {
    this.mOptOutStatus.set(paramBoolean);
  }
  
  public void trackActivity(Activity paramActivity, UpsightLifeCycleTracker.ActivityState paramActivityState)
  {
    this.mLifeCycleTracker.track(paramActivity, paramActivityState, new StandardSessionInitializer());
  }
  
  public void trackLocation(UpsightLocationTracker.Data paramData)
  {
    this.mLocationTracker.track(paramData);
  }
  
  public void trackPurchase(int paramInt, String paramString1, double paramDouble1, double paramDouble2, String paramString2, Intent paramIntent, UpsightPublisherData paramUpsightPublisherData)
    throws UpsightException
  {
    this.mGooglePlayHelper.trackPurchase(paramInt, paramString1, paramDouble1, paramDouble2, paramString2, paramIntent, paramUpsightPublisherData);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/Analytics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */