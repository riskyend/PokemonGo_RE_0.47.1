package com.upsight.android.analytics.event.session;

import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.session.pause")
public class UpsightSessionPauseEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightSessionPauseEvent() {}
  
  protected UpsightSessionPauseEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightSessionPauseEvent, UpsightSessionPauseEvent.UpsightData>
  {
    protected UpsightSessionPauseEvent build()
    {
      return new UpsightSessionPauseEvent("upsight.session.pause", new UpsightSessionPauseEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
  }
  
  static class UpsightData
  {
    protected UpsightData() {}
    
    protected UpsightData(UpsightSessionPauseEvent.Builder paramBuilder) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/session/UpsightSessionPauseEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */