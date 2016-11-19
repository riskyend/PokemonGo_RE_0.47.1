package com.upsight.android.analytics.event.milestone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.milestone")
public class UpsightMilestoneEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightMilestoneEvent() {}
  
  protected UpsightMilestoneEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(String paramString)
  {
    return new Builder(paramString);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightMilestoneEvent, UpsightMilestoneEvent.UpsightData>
  {
    private String scope;
    
    protected Builder(String paramString)
    {
      this.scope = paramString;
    }
    
    protected UpsightMilestoneEvent build()
    {
      return new UpsightMilestoneEvent("upsight.milestone", new UpsightMilestoneEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
  }
  
  static class UpsightData
  {
    @Expose
    @SerializedName("scope")
    String scope;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightMilestoneEvent.Builder paramBuilder)
    {
      this.scope = paramBuilder.scope;
    }
    
    public String getScope()
    {
      return this.scope;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/milestone/UpsightMilestoneEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */