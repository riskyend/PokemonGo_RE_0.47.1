package com.upsight.android.analytics.event.content;

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

@UpsightStorableType("upsight.content.view")
public class UpsightContentViewEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightContentViewEvent() {}
  
  protected UpsightContentViewEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(String paramString, Integer paramInteger)
  {
    return new Builder(paramString, paramInteger);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightContentViewEvent, UpsightContentViewEvent.UpsightData>
  {
    private JsonArray ads;
    private Integer contentId;
    private String impressionId;
    private String scope;
    private String streamId;
    private String streamStartTs;
    private Boolean testDevice;
    
    protected Builder(String paramString, Integer paramInteger)
    {
      this.streamId = paramString;
      this.contentId = paramInteger;
    }
    
    protected UpsightContentViewEvent build()
    {
      return new UpsightContentViewEvent("upsight.content.view", new UpsightContentViewEvent.UpsightData(this), this.mPublisherDataBuilder.build());
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
    @SerializedName("ads")
    JsonArray ads;
    @Expose
    @SerializedName("content_id")
    Integer contentId;
    @Expose
    @SerializedName("impression_id")
    String impressionId;
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
    
    protected UpsightData(UpsightContentViewEvent.Builder paramBuilder)
    {
      this.ads = paramBuilder.ads;
      this.impressionId = paramBuilder.impressionId;
      this.streamId = paramBuilder.streamId;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.scope = paramBuilder.scope;
      this.contentId = paramBuilder.contentId;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/content/UpsightContentViewEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */