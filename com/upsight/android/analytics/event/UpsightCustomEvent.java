package com.upsight.android.analytics.event;

import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("com.upsight.custom")
public final class UpsightCustomEvent
  extends AnalyticsEvent<UpsightPublisherData>
{
  UpsightCustomEvent() {}
  
  UpsightCustomEvent(String paramString, UpsightPublisherData paramUpsightPublisherData1, UpsightPublisherData paramUpsightPublisherData2)
  {
    super(paramString, paramUpsightPublisherData1, paramUpsightPublisherData2);
  }
  
  public static Builder createBuilder(String paramString)
  {
    return new Builder(paramString, null);
  }
  
  public static final class Builder
    extends AnalyticsEvent.Builder<UpsightCustomEvent, UpsightPublisherData>
  {
    private static final String FORMAT = "pub.%s";
    private String type;
    private UpsightPublisherData.Builder upsightDataBuilder = new UpsightPublisherData.Builder();
    
    private Builder(String paramString)
    {
      this.type = String.format("pub.%s", new Object[] { paramString });
    }
    
    protected UpsightCustomEvent build()
    {
      return new UpsightCustomEvent(this.type, this.upsightDataBuilder.build(), this.mPublisherDataBuilder.build());
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/UpsightCustomEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */