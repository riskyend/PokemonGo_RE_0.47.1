package com.upsight.android.analytics.event.uxm;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.analytics.internal.util.GsonHelper.JSONArraySerializer;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import org.json.JSONArray;

@UpsightStorableType("upsight.uxm.enumerate")
public class UpsightUxmEnumerateEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightUxmEnumerateEvent() {}
  
  protected UpsightUxmEnumerateEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(JSONArray paramJSONArray)
  {
    return new Builder(paramJSONArray);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightUxmEnumerateEvent, UpsightUxmEnumerateEvent.UpsightData>
  {
    private JsonArray uxm;
    
    protected Builder(JSONArray paramJSONArray)
    {
      this.uxm = GsonHelper.JSONArraySerializer.toJsonArray(paramJSONArray);
    }
    
    protected UpsightUxmEnumerateEvent build()
    {
      return new UpsightUxmEnumerateEvent("upsight.uxm.enumerate", new UpsightUxmEnumerateEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
  }
  
  static class UpsightData
  {
    @Expose
    @SerializedName("uxm")
    JsonArray uxm;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightUxmEnumerateEvent.Builder paramBuilder)
    {
      this.uxm = paramBuilder.uxm;
    }
    
    public JSONArray getUxm()
    {
      return GsonHelper.JSONArraySerializer.fromJsonArray(this.uxm);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/uxm/UpsightUxmEnumerateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */