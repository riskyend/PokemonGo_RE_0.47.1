package com.upsight.android.analytics.event.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.session.start")
public class UpsightSessionStartEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightSessionStartEvent() {}
  
  protected UpsightSessionStartEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightSessionStartEvent, UpsightSessionStartEvent.UpsightData>
  {
    private String referrer;
    private String streamId;
    private String streamStartTs;
    
    protected UpsightSessionStartEvent build()
    {
      return new UpsightSessionStartEvent("upsight.session.start", new UpsightSessionStartEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setReferrer(String paramString)
    {
      this.referrer = paramString;
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
    @SerializedName("referrer")
    String referrer;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightSessionStartEvent.Builder paramBuilder)
    {
      this.streamStartTs = paramBuilder.streamStartTs;
      this.referrer = paramBuilder.referrer;
      this.streamId = paramBuilder.streamId;
    }
    
    public String getReferrer()
    {
      return this.referrer;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/session/UpsightSessionStartEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */