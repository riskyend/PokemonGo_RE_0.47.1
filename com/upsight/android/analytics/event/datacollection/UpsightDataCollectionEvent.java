package com.upsight.android.analytics.event.datacollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.data_collection")
public class UpsightDataCollectionEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightDataCollectionEvent() {}
  
  protected UpsightDataCollectionEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(String paramString1, String paramString2)
  {
    return new Builder(paramString1, paramString2);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightDataCollectionEvent, UpsightDataCollectionEvent.UpsightData>
  {
    private String dataBundle;
    private String format;
    private String streamId;
    private String streamStartTs;
    
    protected Builder(String paramString1, String paramString2)
    {
      this.dataBundle = paramString1;
      this.streamId = paramString2;
    }
    
    protected UpsightDataCollectionEvent build()
    {
      return new UpsightDataCollectionEvent("upsight.data_collection", new UpsightDataCollectionEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setFormat(String paramString)
    {
      this.format = paramString;
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
    @SerializedName("data_bundle")
    String dataBundle;
    @Expose
    @SerializedName("format")
    String format;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightDataCollectionEvent.Builder paramBuilder)
    {
      this.streamStartTs = paramBuilder.streamStartTs;
      this.streamId = paramBuilder.streamId;
      this.dataBundle = paramBuilder.dataBundle;
      this.format = paramBuilder.format;
    }
    
    public String getDataBundle()
    {
      return this.dataBundle;
    }
    
    public String getFormat()
    {
      return this.format;
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/datacollection/UpsightDataCollectionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */