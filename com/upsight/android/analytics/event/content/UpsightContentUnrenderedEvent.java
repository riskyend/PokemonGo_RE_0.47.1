package com.upsight.android.analytics.event.content;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.analytics.internal.util.GsonHelper.JSONObjectSerializer;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import org.json.JSONObject;

@UpsightStorableType("upsight.content.unrendered")
public class UpsightContentUnrenderedEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightContentUnrenderedEvent() {}
  
  protected UpsightContentUnrenderedEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(JSONObject paramJSONObject)
  {
    return new Builder(paramJSONObject);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightContentUnrenderedEvent, UpsightContentUnrenderedEvent.UpsightData>
  {
    private Integer campaignId;
    private JsonObject contentProvider;
    private String id;
    private String scope;
    private String streamId;
    private String streamStartTs;
    
    protected Builder(JSONObject paramJSONObject)
    {
      this.contentProvider = GsonHelper.JSONObjectSerializer.toJsonObject(paramJSONObject);
    }
    
    protected UpsightContentUnrenderedEvent build()
    {
      return new UpsightContentUnrenderedEvent("upsight.content.unrendered", new UpsightContentUnrenderedEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setCampaignId(Integer paramInteger)
    {
      this.campaignId = paramInteger;
      return this;
    }
    
    public Builder setId(String paramString)
    {
      this.id = paramString;
      return this;
    }
    
    public Builder setScope(String paramString)
    {
      this.scope = paramString;
      return this;
    }
    
    public Builder setStreamId(String paramString)
    {
      this.streamId = paramString;
      return this;
    }
    
    public Builder setStreamStartTs(String paramString)
    {
      this.streamStartTs = paramString;
      return this;
    }
  }
  
  static class UpsightData
  {
    @Expose
    @SerializedName("campaign_id")
    Integer campaignId;
    @Expose
    @SerializedName("content_provider")
    JsonObject contentProvider;
    @Expose
    @SerializedName("id")
    String id;
    @Expose
    @SerializedName("scope")
    String scope;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightContentUnrenderedEvent.Builder paramBuilder)
    {
      this.contentProvider = paramBuilder.contentProvider;
      this.campaignId = paramBuilder.campaignId;
      this.streamId = paramBuilder.streamId;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.scope = paramBuilder.scope;
      this.id = paramBuilder.id;
    }
    
    public Integer getCampaignId()
    {
      return this.campaignId;
    }
    
    public JSONObject getContentProvider()
    {
      return GsonHelper.JSONObjectSerializer.fromJsonObject(this.contentProvider);
    }
    
    public String getId()
    {
      return this.id;
    }
    
    public String getScope()
    {
      return this.scope;
    }
    
    public String getStreamId()
    {
      return this.streamId;
    }
    
    public String getStreamStartTs()
    {
      return this.streamStartTs;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/content/UpsightContentUnrenderedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */