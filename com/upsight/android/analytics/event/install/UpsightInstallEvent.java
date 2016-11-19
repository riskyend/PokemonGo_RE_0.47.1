package com.upsight.android.analytics.event.install;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.install")
public class UpsightInstallEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightInstallEvent() {}
  
  protected UpsightInstallEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightInstallEvent, UpsightInstallEvent.UpsightData>
  {
    private String referrer;
    private String sourceId;
    private String streamId;
    private String streamStartTs;
    
    protected UpsightInstallEvent build()
    {
      return new UpsightInstallEvent("upsight.install", new UpsightInstallEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setReferrer(String paramString)
    {
      this.referrer = paramString;
      return this;
    }
    
    public Builder setSourceId(String paramString)
    {
      this.sourceId = paramString;
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
    @SerializedName("source_id")
    String sourceId;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightInstallEvent.Builder paramBuilder)
    {
      this.sourceId = paramBuilder.sourceId;
      this.referrer = paramBuilder.referrer;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.streamId = paramBuilder.streamId;
    }
    
    public String getReferrer()
    {
      return this.referrer;
    }
    
    public String getSourceId()
    {
      return this.sourceId;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/install/UpsightInstallEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */