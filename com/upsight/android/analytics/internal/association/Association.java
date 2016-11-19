package com.upsight.android.analytics.internal.association;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import java.io.IOException;

@UpsightStorableType("upsight.association")
public class Association
{
  @Expose
  @UpsightStorableIdentifier
  String id;
  @Expose
  @SerializedName("timestamp_ms")
  long timestampMs;
  @Expose
  @SerializedName("upsight_data")
  JsonObject upsightData;
  @Expose
  @SerializedName("upsight_data_filter")
  UpsightDataFilter upsightDataFilter;
  @Expose
  @SerializedName("with")
  String with;
  
  Association() {}
  
  private Association(String paramString, UpsightDataFilter paramUpsightDataFilter, JsonObject paramJsonObject, long paramLong)
  {
    this.with = paramString;
    this.upsightDataFilter = paramUpsightDataFilter;
    this.upsightData = paramJsonObject;
    this.timestampMs = paramLong;
  }
  
  public static Association from(String paramString, JsonObject paramJsonObject1, JsonObject paramJsonObject2, Gson paramGson, Clock paramClock)
    throws IllegalArgumentException, IOException
  {
    if ((TextUtils.isEmpty(paramString)) || (paramJsonObject1 == null) || (paramJsonObject2 == null)) {
      throw new IllegalArgumentException("Illegal arguments");
    }
    try
    {
      paramJsonObject1 = (UpsightDataFilter)paramGson.fromJson(paramJsonObject1, UpsightDataFilter.class);
      return new Association(paramString, paramJsonObject1, paramJsonObject2, paramClock.currentTimeMillis());
    }
    catch (JsonSyntaxException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public long getTimestampMs()
  {
    return this.timestampMs;
  }
  
  public JsonObject getUpsightData()
  {
    return this.upsightData;
  }
  
  public UpsightDataFilter getUpsightDataFilter()
  {
    return this.upsightDataFilter;
  }
  
  public String getWith()
  {
    return this.with;
  }
  
  public static class UpsightDataFilter
  {
    @Expose
    @SerializedName("match_key")
    String matchKey;
    @Expose
    @SerializedName("match_values")
    JsonArray matchValues;
    
    public String getMatchKey()
    {
      return this.matchKey;
    }
    
    public JsonArray getMatchValues()
    {
      return this.matchValues;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/association/Association.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */