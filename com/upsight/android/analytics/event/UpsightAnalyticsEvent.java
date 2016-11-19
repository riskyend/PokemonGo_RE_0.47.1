package com.upsight.android.analytics.event;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.analytics.internal.util.GsonHelper.JSONObjectSerializer;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public abstract class UpsightAnalyticsEvent<U, P>
{
  @UpsightStorableIdentifier
  protected String id;
  @Expose
  @SerializedName("ts")
  protected long mCreationTsMs;
  @Expose
  @SerializedName("pub_data")
  protected P mPublisherData;
  @Expose
  @SerializedName("seq_id")
  protected long mSequenceId;
  @Expose
  @SerializedName("type")
  protected String mType;
  @Expose
  @SerializedName("upsight_data")
  protected U mUpsightData;
  @Expose
  @SerializedName("user_attributes")
  protected JsonObject mUserAttributes;
  
  protected UpsightAnalyticsEvent() {}
  
  protected UpsightAnalyticsEvent(String paramString, U paramU, P paramP)
  {
    this.mCreationTsMs = TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    this.mType = paramString;
    this.mUpsightData = paramU;
    this.mPublisherData = paramP;
  }
  
  public long getCreationTimestampMs()
  {
    return this.mCreationTsMs;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public P getPublisherData()
  {
    return (P)this.mPublisherData;
  }
  
  public long getSequenceId()
  {
    return this.mSequenceId;
  }
  
  public String getType()
  {
    return this.mType;
  }
  
  protected U getUpsightData()
  {
    return (U)this.mUpsightData;
  }
  
  public JSONObject getUserAttributes()
  {
    return GsonHelper.JSONObjectSerializer.fromJsonObject(this.mUserAttributes);
  }
  
  public static abstract class Builder<T extends UpsightAnalyticsEvent<U, P>, U, P>
  {
    protected abstract T build();
    
    public final T record(UpsightContext paramUpsightContext)
    {
      UpsightAnalyticsEvent localUpsightAnalyticsEvent = build();
      UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
      if (localUpsightAnalyticsExtension != null)
      {
        localUpsightAnalyticsExtension.getApi().record(localUpsightAnalyticsEvent);
        return localUpsightAnalyticsEvent;
      }
      paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
      return localUpsightAnalyticsEvent;
    }
    
    public final T recordSessionless(UpsightContext paramUpsightContext)
    {
      UpsightAnalyticsEvent localUpsightAnalyticsEvent = build();
      UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
      if (localUpsightAnalyticsExtension != null)
      {
        localUpsightAnalyticsExtension.getApi().recordSessionless(localUpsightAnalyticsEvent);
        return localUpsightAnalyticsEvent;
      }
      paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
      return localUpsightAnalyticsEvent;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/UpsightAnalyticsEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */