package com.upsight.android.analytics.event.comm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.comm.register")
public class UpsightCommRegisterEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightCommRegisterEvent() {}
  
  protected UpsightCommRegisterEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder()
  {
    return new Builder();
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightCommRegisterEvent, UpsightCommRegisterEvent.UpsightData>
  {
    private String token;
    
    protected UpsightCommRegisterEvent build()
    {
      return new UpsightCommRegisterEvent("upsight.comm.register", new UpsightCommRegisterEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setToken(String paramString)
    {
      this.token = paramString;
      return this;
    }
  }
  
  static class UpsightData
  {
    @Expose
    @SerializedName("token")
    String token;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightCommRegisterEvent.Builder paramBuilder)
    {
      this.token = paramBuilder.token;
    }
    
    public String getToken()
    {
      return this.token;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/comm/UpsightCommRegisterEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */