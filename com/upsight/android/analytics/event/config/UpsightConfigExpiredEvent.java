package com.upsight.android.analytics.event.config;

import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.config.expired")
public class UpsightConfigExpiredEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightConfigExpiredEvent() {}
  
  protected UpsightConfigExpiredEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightConfigExpiredEvent, UpsightConfigExpiredEvent.UpsightData>
  {
    protected UpsightConfigExpiredEvent build()
    {
      return new UpsightConfigExpiredEvent("upsight.config.expired", new UpsightConfigExpiredEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
  }
  
  static class UpsightData
  {
    protected UpsightData() {}
    
    protected UpsightData(UpsightConfigExpiredEvent.Builder paramBuilder) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/config/UpsightConfigExpiredEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */