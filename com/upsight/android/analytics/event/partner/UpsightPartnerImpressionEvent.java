package com.upsight.android.analytics.event.partner;

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

@UpsightStorableType("upsight.partner.impression")
public class UpsightPartnerImpressionEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightPartnerImpressionEvent() {}
  
  protected UpsightPartnerImpressionEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(Integer paramInteger1, String paramString1, String paramString2, Integer paramInteger2)
  {
    return new Builder(paramInteger1, paramString1, paramString2, paramInteger2);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightPartnerImpressionEvent, UpsightPartnerImpressionEvent.UpsightData>
  {
    private JsonArray ads;
    private Integer contentId;
    private String impressionId;
    private Integer partnerId;
    private String partnerName;
    private String scope;
    private String streamId;
    private String streamStartTs;
    private Boolean testDevice;
    
    protected Builder(Integer paramInteger1, String paramString1, String paramString2, Integer paramInteger2)
    {
      this.partnerId = paramInteger1;
      this.scope = paramString1;
      this.streamId = paramString2;
      this.contentId = paramInteger2;
    }
    
    protected UpsightPartnerImpressionEvent build()
    {
      return new UpsightPartnerImpressionEvent("upsight.partner.impression", new UpsightPartnerImpressionEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setAds(JSONArray paramJSONArray)
    {
      this.ads = GsonHelper.JSONArraySerializer.toJsonArray(paramJSONArray);
      return this;
    }
    
    public Builder setImpressionId(String paramString)
    {
      this.impressionId = paramString;
      return this;
    }
    
    public Builder setPartnerName(String paramString)
    {
      this.partnerName = paramString;
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
    @SerializedName("ads")
    JsonArray ads;
    @Expose
    @SerializedName("content_id")
    Integer contentId;
    @Expose
    @SerializedName("impression_id")
    String impressionId;
    @Expose
    @SerializedName("partner_id")
    Integer partnerId;
    @Expose
    @SerializedName("partner_name")
    String partnerName;
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
    
    protected UpsightData(UpsightPartnerImpressionEvent.Builder paramBuilder)
    {
      this.ads = paramBuilder.ads;
      this.partnerName = paramBuilder.partnerName;
      this.impressionId = paramBuilder.impressionId;
      this.streamId = paramBuilder.streamId;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.scope = paramBuilder.scope;
      this.contentId = paramBuilder.contentId;
      this.partnerId = paramBuilder.partnerId;
      this.testDevice = paramBuilder.testDevice;
    }
    
    public JSONArray getAds()
    {
      return GsonHelper.JSONArraySerializer.fromJsonArray(this.ads);
    }
    
    public Integer getContentId()
    {
      return this.contentId;
    }
    
    public String getImpressionId()
    {
      return this.impressionId;
    }
    
    public Integer getPartnerId()
    {
      return this.partnerId;
    }
    
    public String getPartnerName()
    {
      return this.partnerName;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/partner/UpsightPartnerImpressionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */