package com.upsight.android.analytics.event;

import android.text.TextUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

@JsonAdapter(DefaultTypeAdapter.class)
public class UpsightPublisherData
{
  private final JsonObject mDataMap;
  
  private UpsightPublisherData(Builder paramBuilder)
  {
    this.mDataMap = paramBuilder.mDataMap;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (UpsightPublisherData)paramObject;
      if (this.mDataMap == null) {
        break;
      }
    } while (this.mDataMap.equals(((UpsightPublisherData)paramObject).mDataMap));
    while (((UpsightPublisherData)paramObject).mDataMap != null) {
      return false;
    }
    return true;
  }
  
  public String getData(String paramString)
  {
    return this.mDataMap.get(paramString).toString();
  }
  
  public int hashCode()
  {
    if (this.mDataMap != null) {
      return this.mDataMap.hashCode();
    }
    return 0;
  }
  
  public static class Builder
  {
    private final JsonObject mDataMap;
    
    public Builder()
    {
      this.mDataMap = new JsonObject();
    }
    
    Builder(JsonObject paramJsonObject)
    {
      this.mDataMap = paramJsonObject;
    }
    
    public UpsightPublisherData build()
    {
      return new UpsightPublisherData(this, null);
    }
    
    public Builder put(UpsightPublisherData paramUpsightPublisherData)
    {
      if (paramUpsightPublisherData != null)
      {
        paramUpsightPublisherData = paramUpsightPublisherData.mDataMap.entrySet().iterator();
        while (paramUpsightPublisherData.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)paramUpsightPublisherData.next();
          this.mDataMap.add((String)localEntry.getKey(), (JsonElement)localEntry.getValue());
        }
      }
      return this;
    }
    
    public Builder put(String paramString, char paramChar)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.mDataMap.addProperty(paramString, String.valueOf(paramChar));
      }
      return this;
    }
    
    public Builder put(String paramString, double paramDouble)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.mDataMap.addProperty(paramString, Double.valueOf(paramDouble));
      }
      return this;
    }
    
    public Builder put(String paramString, float paramFloat)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.mDataMap.addProperty(paramString, Float.valueOf(paramFloat));
      }
      return this;
    }
    
    public Builder put(String paramString, int paramInt)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.mDataMap.addProperty(paramString, Integer.valueOf(paramInt));
      }
      return this;
    }
    
    public Builder put(String paramString, long paramLong)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.mDataMap.addProperty(paramString, Long.valueOf(paramLong));
      }
      return this;
    }
    
    public Builder put(String paramString, CharSequence paramCharSequence)
    {
      if ((!TextUtils.isEmpty(paramString)) && (paramCharSequence != null)) {
        this.mDataMap.addProperty(paramString, paramCharSequence.toString());
      }
      return this;
    }
    
    public Builder put(String paramString, boolean paramBoolean)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.mDataMap.addProperty(paramString, Boolean.valueOf(paramBoolean));
      }
      return this;
    }
  }
  
  public static final class DefaultTypeAdapter
    extends TypeAdapter<UpsightPublisherData>
  {
    private static final JsonParser JSON_PARSER = new JsonParser();
    
    public UpsightPublisherData read(JsonReader paramJsonReader)
      throws IOException
    {
      return new UpsightPublisherData.Builder(JSON_PARSER.parse(paramJsonReader).getAsJsonObject()).build();
    }
    
    public void write(JsonWriter paramJsonWriter, UpsightPublisherData paramUpsightPublisherData)
      throws IOException
    {
      Streams.write(paramUpsightPublisherData.mDataMap, paramJsonWriter);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/UpsightPublisherData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */