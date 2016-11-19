package com.upsight.android.analytics.event.comm;

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

@UpsightStorableType("upsight.comm.send")
public class UpsightCommSendEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightCommSendEvent() {}
  
  protected UpsightCommSendEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(Integer paramInteger, String paramString)
  {
    return new Builder(paramInteger, paramString);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightCommSendEvent, UpsightCommSendEvent.UpsightData>
  {
    private Integer msgCampaignId;
    private Integer msgId;
    private JsonObject payload;
    private String token;
    
    protected Builder(Integer paramInteger, String paramString)
    {
      this.msgId = paramInteger;
      this.token = paramString;
    }
    
    protected UpsightCommSendEvent build()
    {
      return new UpsightCommSendEvent("upsight.comm.send", new UpsightCommSendEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setMsgCampaignId(Integer paramInteger)
    {
      this.msgCampaignId = paramInteger;
      return this;
    }
    
    public Builder setPayload(JSONObject paramJSONObject)
    {
      this.payload = GsonHelper.JSONObjectSerializer.toJsonObject(paramJSONObject);
      return this;
    }
  }
  
  static class UpsightData
  {
    @Expose
    @SerializedName("msg_campaign_id")
    Integer msgCampaignId;
    @Expose
    @SerializedName("msg_id")
    Integer msgId;
    @Expose
    @SerializedName("payload")
    JsonObject payload;
    @Expose
    @SerializedName("token")
    String token;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightCommSendEvent.Builder paramBuilder)
    {
      this.token = paramBuilder.token;
      this.msgId = paramBuilder.msgId;
      this.payload = paramBuilder.payload;
      this.msgCampaignId = paramBuilder.msgCampaignId;
    }
    
    public Integer getMsgCampaignId()
    {
      return this.msgCampaignId;
    }
    
    public Integer getMsgId()
    {
      return this.msgId;
    }
    
    public JSONObject getPayload()
    {
      return GsonHelper.JSONObjectSerializer.fromJsonObject(this.payload);
    }
    
    public String getToken()
    {
      return this.token;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/comm/UpsightCommSendEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */