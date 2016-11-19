package com.upsight.android.analytics.event.comm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.comm.click")
public class UpsightCommClickEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightCommClickEvent() {}
  
  protected UpsightCommClickEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(Integer paramInteger)
  {
    return new Builder(paramInteger);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightCommClickEvent, UpsightCommClickEvent.UpsightData>
  {
    private Integer msgCampaignId;
    private Integer msgId;
    
    protected Builder(Integer paramInteger)
    {
      this.msgId = paramInteger;
    }
    
    protected UpsightCommClickEvent build()
    {
      return new UpsightCommClickEvent("upsight.comm.click", new UpsightCommClickEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setMsgCampaignId(Integer paramInteger)
    {
      this.msgCampaignId = paramInteger;
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
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightCommClickEvent.Builder paramBuilder)
    {
      this.msgId = paramBuilder.msgId;
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
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/comm/UpsightCommClickEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */