package com.upsight.android.analytics.event.install;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.install.attribution")
public class UpsightInstallAttributionEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightInstallAttributionEvent() {}
  
  protected UpsightInstallAttributionEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightInstallAttributionEvent, UpsightInstallAttributionEvent.UpsightData>
  {
    private String attributionCampaign;
    private String attributionCreative;
    private String attributionSource;
    private String streamId;
    private String streamStartTs;
    
    protected UpsightInstallAttributionEvent build()
    {
      return new UpsightInstallAttributionEvent("upsight.install.attribution", new UpsightInstallAttributionEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setAttributionCampaign(String paramString)
    {
      this.attributionCampaign = paramString;
      return this;
    }
    
    public Builder setAttributionCreative(String paramString)
    {
      this.attributionCreative = paramString;
      return this;
    }
    
    public Builder setAttributionSource(String paramString)
    {
      this.attributionSource = paramString;
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
    @SerializedName("attribution_campaign")
    String attributionCampaign;
    @Expose
    @SerializedName("attribution_creative")
    String attributionCreative;
    @Expose
    @SerializedName("attribution_source")
    String attributionSource;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightInstallAttributionEvent.Builder paramBuilder)
    {
      this.attributionCampaign = paramBuilder.attributionCampaign;
      this.attributionCreative = paramBuilder.attributionCreative;
      this.attributionSource = paramBuilder.attributionSource;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.streamId = paramBuilder.streamId;
    }
    
    public String getAttributionCampaign()
    {
      return this.attributionCampaign;
    }
    
    public String getAttributionCreative()
    {
      return this.attributionCreative;
    }
    
    public String getAttributionSource()
    {
      return this.attributionSource;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/install/UpsightInstallAttributionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */