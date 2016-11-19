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

@UpsightStorableType("upsight.content.dismiss")
public class UpsightContentDismissEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightContentDismissEvent() {}
  
  protected UpsightContentDismissEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(String paramString1, Integer paramInteger, String paramString2)
  {
    return new Builder(paramString1, paramInteger, paramString2);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightContentDismissEvent, UpsightContentDismissEvent.UpsightData>
  {
    private String action;
    private JsonArray ads;
    private Integer contentId;
    private String impressionId;
    private String scope;
    private String streamId;
    private String streamStartTs;
    private Boolean testDevice;
    
    protected Builder(String paramString1, Integer paramInteger, String paramString2)
    {
      this.streamId = paramString1;
      this.contentId = paramInteger;
      this.action = paramString2;
    }
    
    protected UpsightContentDismissEvent build()
    {
      return new UpsightContentDismissEvent("upsight.content.dismiss", new UpsightContentDismissEvent.UpsightData(this), this.mPublisherDataBuilder.build());
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
    @SerializedName("action")
    String action;
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
    
    protected UpsightData(UpsightContentDismissEvent.Builder paramBuilder)
    {
      this.ads = paramBuilder.ads;
      this.impressionId = paramBuilder.impressionId;
      this.streamId = paramBuilder.streamId;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.scope = paramBuilder.scope;
      this.contentId = paramBuilder.contentId;
      this.action = paramBuilder.action;
      this.testDevice = paramBuilder.testDevice;
    }
    
    public String getAction()
    {
      return this.action;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/content/UpsightContentDismissEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */