package com.upsight.android.analytics.event.monetization;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.analytics.internal.AnalyticsEvent;
import com.upsight.android.analytics.internal.AnalyticsEvent.Builder;
import com.upsight.android.analytics.internal.util.GsonHelper.JSONObjectSerializer;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import org.json.JSONObject;

@UpsightStorableType("upsight.monetization.iap")
public class UpsightMonetizationIapEvent
  extends AnalyticsEvent<UpsightData>
{
  protected UpsightMonetizationIapEvent() {}
  
  protected UpsightMonetizationIapEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
  {
    super(paramString, paramUpsightData, paramUpsightPublisherData);
  }
  
  public static Builder createBuilder(String paramString1, JSONObject paramJSONObject, Double paramDouble1, Double paramDouble2, Integer paramInteger, String paramString2, String paramString3)
  {
    return new Builder(paramString1, paramJSONObject, paramDouble1, paramDouble2, paramInteger, paramString2, paramString3);
  }
  
  public static class Builder
    extends AnalyticsEvent.Builder<UpsightMonetizationIapEvent, UpsightMonetizationIapEvent.UpsightData>
  {
    private String cookie;
    private String currency;
    private JsonObject iapBundle;
    private Double price;
    private String product;
    private Integer quantity;
    private String resolution;
    private String store;
    private String streamId;
    private String streamStartTs;
    private Double totalPrice;
    
    protected Builder(String paramString1, JSONObject paramJSONObject, Double paramDouble1, Double paramDouble2, Integer paramInteger, String paramString2, String paramString3)
    {
      this.store = paramString1;
      this.iapBundle = GsonHelper.JSONObjectSerializer.toJsonObject(paramJSONObject);
      this.totalPrice = paramDouble1;
      this.price = paramDouble2;
      this.quantity = paramInteger;
      this.currency = paramString2;
      this.product = paramString3;
    }
    
    protected UpsightMonetizationIapEvent build()
    {
      return new UpsightMonetizationIapEvent("upsight.monetization.iap", new UpsightMonetizationIapEvent.UpsightData(this), this.mPublisherDataBuilder.build());
    }
    
    public Builder setCookie(String paramString)
    {
      this.cookie = paramString;
      return this;
    }
    
    public Builder setResolution(String paramString)
    {
      this.resolution = paramString;
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
    @SerializedName("cookie")
    String cookie;
    @Expose
    @SerializedName("currency")
    String currency;
    @Expose
    @SerializedName("iap_bundle")
    JsonObject iapBundle;
    @Expose
    @SerializedName("price")
    Double price;
    @Expose
    @SerializedName("product")
    String product;
    @Expose
    @SerializedName("quantity")
    Integer quantity;
    @Expose
    @SerializedName("resolution")
    String resolution;
    @Expose
    @SerializedName("store")
    String store;
    @Expose
    @SerializedName("stream_id")
    String streamId;
    @Expose
    @SerializedName("stream_start_ts")
    String streamStartTs;
    @Expose
    @SerializedName("total_price")
    Double totalPrice;
    
    protected UpsightData() {}
    
    protected UpsightData(UpsightMonetizationIapEvent.Builder paramBuilder)
    {
      this.product = paramBuilder.product;
      this.totalPrice = paramBuilder.totalPrice;
      this.streamId = paramBuilder.streamId;
      this.price = paramBuilder.price;
      this.currency = paramBuilder.currency;
      this.cookie = paramBuilder.cookie;
      this.iapBundle = paramBuilder.iapBundle;
      this.streamStartTs = paramBuilder.streamStartTs;
      this.resolution = paramBuilder.resolution;
      this.store = paramBuilder.store;
      this.quantity = paramBuilder.quantity;
    }
    
    public String getCookie()
    {
      return this.cookie;
    }
    
    public String getCurrency()
    {
      return this.currency;
    }
    
    public JSONObject getIapBundle()
    {
      return GsonHelper.JSONObjectSerializer.fromJsonObject(this.iapBundle);
    }
    
    public Double getPrice()
    {
      return this.price;
    }
    
    public String getProduct()
    {
      return this.product;
    }
    
    public Integer getQuantity()
    {
      return this.quantity;
    }
    
    public String getResolution()
    {
      return this.resolution;
    }
    
    public String getStore()
    {
      return this.store;
    }
    
    public String getStreamId()
    {
      return this.streamId;
    }
    
    public String getStreamStartTs()
    {
      return this.streamStartTs;
    }
    
    public Double getTotalPrice()
    {
      return this.totalPrice;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/monetization/UpsightMonetizationIapEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */