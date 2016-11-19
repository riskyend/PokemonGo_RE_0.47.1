package com.upsight.android.analytics.internal;

import com.upsight.android.analytics.event.UpsightAnalyticsEvent;
import com.upsight.android.analytics.event.UpsightAnalyticsEvent.Builder;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;

public abstract class AnalyticsEvent<U>
  extends UpsightAnalyticsEvent<U, UpsightPublisherData>
{
  protected AnalyticsEvent() {}
  
  protected AnalyticsEvent(String paramString, U paramU, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramU, paramUpsightPublisherData);
  }
  
  public static abstract class Builder<T extends AnalyticsEvent<U>, U>
    extends UpsightAnalyticsEvent.Builder<T, U, UpsightPublisherData>
  {
    protected final UpsightPublisherData.Builder mPublisherDataBuilder = new UpsightPublisherData.Builder();
    
    public Builder<T, U> put(UpsightPublisherData paramUpsightPublisherData)
    {
      this.mPublisherDataBuilder.put(paramUpsightPublisherData);
      return this;
    }
    
    public Builder<T, U> put(String paramString, char paramChar)
    {
      this.mPublisherDataBuilder.put(paramString, paramChar);
      return this;
    }
    
    public Builder<T, U> put(String paramString, double paramDouble)
    {
      this.mPublisherDataBuilder.put(paramString, paramDouble);
      return this;
    }
    
    public Builder<T, U> put(String paramString, float paramFloat)
    {
      this.mPublisherDataBuilder.put(paramString, paramFloat);
      return this;
    }
    
    public Builder<T, U> put(String paramString, int paramInt)
    {
      this.mPublisherDataBuilder.put(paramString, paramInt);
      return this;
    }
    
    public Builder<T, U> put(String paramString, long paramLong)
    {
      this.mPublisherDataBuilder.put(paramString, paramLong);
      return this;
    }
    
    public Builder<T, U> put(String paramString, CharSequence paramCharSequence)
    {
      this.mPublisherDataBuilder.put(paramString, paramCharSequence);
      return this;
    }
    
    public Builder<T, U> put(String paramString, boolean paramBoolean)
    {
      this.mPublisherDataBuilder.put(paramString, paramBoolean);
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/AnalyticsEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */