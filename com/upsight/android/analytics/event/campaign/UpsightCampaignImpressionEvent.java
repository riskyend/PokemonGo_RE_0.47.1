package com.upsight.android.analytics.event.campaign;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.analytics.internal.util.GsonHelper.JSONArraySerializer;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import org.json.JSONArray;

@UpsightStorableType("upsight.campaign.impression")
public class UpsightCampaignImpressionEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightCampaignImpressionEvent() {}
  
  protected UpsightCampaignImpressionEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    return new Builder(paramString, paramInteger1, paramInteger2, paramInteger3);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightCampaignImpressionEvent, UpsightCampaignImpressionEvent.UpsightData>
  {
    private Integer adGameId;
    private Integer adTypeId;
    private JsonArray ads;
    private Integer campaignId;
    private Integer contentId;
    private Integer contentTypeId;
    private Integer creativeId;
    private String impressionId;
    private Integer ordinal;
    private String scope;
    private String streamId;
    private String streamStartTs;
    private Boolean testDevice;
    
    protected Builder(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
    {
      this.streamId = paramString;
      this.campaignId = paramInteger1;
      this.creativeId = paramInteger2;
      this.contentId = paramInteger3;
    }
    
    protected UpsightCampaignImpressionEvent build()
    {
      return new UpsightCampaignImpressionEvent("upsight.campaign.impression", new UpsightCampaignImpressionEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setAdGameId(Integer paramInteger)
    {
      this.adGameId = paramInteger;
      return this;
    }
    
    public Builder setAdTypeId(Integer paramInteger)
    {
      this.adTypeId = paramInteger;
      return this;
    }
    
    public Builder setAds(JSONArray paramJSONArray)
    {
      this.ads = GsonHelper.JSONArraySerializer.toJsonArray(paramJSONArray);
      return this;
    }
    
    public Builder setContentTypeId(Integer paramInteger)
    {
      this.contentTypeId = paramInteger;
      return this;
    }
    
    public Builder setImpressionId(String paramString)
    {
      this.impressionId = paramString;
      return this;
    }
    
    public Builder setOrdinal(Integer paramInteger)
    {
      this.ordinal = paramInteger;
      return this;
    }
    
    public Builder setScope(String paramString)
    {
      this.scope = paramString;
      return this;
    }
    
    public Builder setStreamStartTs(String paramString)
    {
      this.streamStartTs = paramString;
      return this;
    }
    
    public Builder setTestDevice(Boolean paramBoolean)
    {
      this.testDevice = paramBoolean;
      return this;
    }
  }
  
  static class UpsightData
  {
    @Expose
    @SerializedName("ad_game_id")
    Integer adGameId;
    @Expose
    @SerializedName("ad_type_id")
    Integer adTypeId;
    @Expose
    @SerializedName("ads")
    JsonArray ads;
    @Expose
    @SerializedName("campaign_id")
    Integer campaignId;
    @Expose
    @SerializedName("content_id")
    Integer contentId;
    @Expose
    @SerializedName("content_type_id")
    Integer contentTypeId;
    @Expose
    @SerializedName("creative_id")
    Integer creativeId;
    @Expose
    @SerializedName("impression_id")
    String impressionId;
    @Expose
    @SerializedName("ordinal")
    Integer ordinal;
    @Expose
    @SerializedName("scope")
    String scope;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    @Expose
    @SerializedName("test_device")
    Boolean testDevice;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightCampaignImpressionEvent.Builder paramBuilder)
    {
      this.ordinal = paramBuilder.ordinal;
      this.impressionId = paramBuilder.impressionId;
      this.ads = paramBuilder.ads;
      this.creativeId = paramBuilder.creativeId;
      this.campaignId = paramBuilder.campaignId;
      this.adTypeId = paramBuilder.adTypeId;
      this.streamId = paramBuilder.streamId;
      this.adGameId = paramBuilder.adGameId;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.scope = paramBuilder.scope;
      this.contentId = paramBuilder.contentId;
      this.testDevice = paramBuilder.testDevice;
      this.contentTypeId = paramBuilder.contentTypeId;
    }
    
    public Integer getAdGameId()
    {
      return this.adGameId;
    }
    
    public Integer getAdTypeId()
    {
      return this.adTypeId;
    }
    
    public JSONArray getAds()
    {
      return GsonHelper.JSONArraySerializer.fromJsonArray(this.ads);
    }
    
    public Integer getCampaignId()
    {
      return this.campaignId;
    }
    
    public Integer getContentId()
    {
      return this.contentId;
    }
    
    public Integer getContentTypeId()
    {
      return this.contentTypeId;
    }
    
    public Integer getCreativeId()
    {
      return this.creativeId;
    }
    
    public String getImpressionId()
    {
      return this.impressionId;
    }
    
    public Integer getOrdinal()
    {
      return this.ordinal;
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
    
    public Boolean getTestDevice()
    {
      return this.testDevice;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/campaign/UpsightCampaignImpressionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */