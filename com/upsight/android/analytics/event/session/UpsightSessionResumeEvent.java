package com.upsight.android.analytics.event.session;

import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.session.resume")
public class UpsightSessionResumeEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightSessionResumeEvent() {}
  
  protected UpsightSessionResumeEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightSessionResumeEvent, UpsightSessionResumeEvent.UpsightData>
  {
    protected UpsightSessionResumeEvent build()
    {
      return new UpsightSessionResumeEvent("upsight.session.resume", new UpsightSessionResumeEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
  }
  
  static class UpsightData
  {
    protected UpsightData() {}
    
    protected UpsightData(UpsightSessionResumeEvent.Builder paramBuilder) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/session/UpsightSessionResumeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */