package com.upsight.android.analytics.event;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.analytics.internal.DynamicIdentifiers;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("com.upsight.dynamic")
public final class UpsightDynamicEvent
  extends UpsightAnalyticsEvent<JsonElement, JsonElement>
  implements DynamicIdentifiers
{
  private String identifiers;
  
  UpsightDynamicEvent() {}
  
  UpsightDynamicEvent(String paramString1, String paramString2, JsonElement paramJsonElement1, JsonElement paramJsonElement2)
  {
    super(paramString1, paramJsonElement1, paramJsonElement2);
    this.identifiers = paramString2;
  }
  
  public static Builder createBuilder(String paramString)
  {
    return new Builder(paramString, null);
  }
  
  public String getIdentifiersName()
  {
    return this.identifiers;
  }
  
  public static final class Builder
  {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private String identifiers;
    private JsonObject publisherData = new JsonObject();
    private final String type;
    private JsonObject upsightData = new JsonObject();
    
    private Builder(String paramString)
    {
      this.type = paramString;
    }
    
    private UpsightDynamicEvent build()
    {
      return new UpsightDynamicEvent(this.type, this.identifiers, this.upsightData, this.publisherData);
    }
    
    private JsonObject deepCopy(JsonObject paramJsonObject)
    {
      paramJsonObject = paramJsonObject.toString();
      return JSON_PARSER.parse(paramJsonObject).getAsJsonObject();
    }
    
    public Builder putPublisherData(JsonObject paramJsonObject)
    {
      this.publisherData = deepCopy(paramJsonObject);
      return this;
    }
    
    public Builder putUpsightData(JsonObject paramJsonObject)
    {
      this.upsightData = deepCopy(paramJsonObject);
      return this;
    }
    
    public final UpsightDynamicEvent record(UpsightContext paramUpsightContext)
    {
      UpsightDynamicEvent localUpsightDynamicEvent = build();
      paramUpsightContext = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
      if (paramUpsightContext != null) {
        paramUpsightContext.getApi().record(localUpsightDynamicEvent);
      }
      return localUpsightDynamicEvent;
    }
    
    public Builder setDynamicIdentifiers(String paramString)
    {
      this.identifiers = paramString;
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/event/UpsightDynamicEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */