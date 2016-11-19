package com.upsight.android.analytics.internal.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract interface GsonHelper
{
  public static class JSONArraySerializer
  {
    private static JsonParser sJsonParser = new JsonParser();
    
    public static JSONArray fromJsonArray(JsonArray paramJsonArray)
    {
      JSONArray localJSONArray = null;
      if (paramJsonArray != null) {}
      try
      {
        localJSONArray = new JSONArray(paramJsonArray.toString());
        return localJSONArray;
      }
      catch (JSONException paramJsonArray) {}
      return null;
    }
    
    public static JsonArray toJsonArray(JSONArray paramJSONArray)
    {
      try
      {
        paramJSONArray = sJsonParser.parse(paramJSONArray.toString()).getAsJsonArray();
        return paramJSONArray;
      }
      catch (JsonParseException paramJSONArray) {}
      return null;
    }
  }
  
  public static class JSONObjectSerializer
  {
    private static JsonParser sJsonParser = new JsonParser();
    
    public static JSONObject fromJsonObject(JsonObject paramJsonObject)
    {
      JSONObject localJSONObject = null;
      if (paramJsonObject != null) {}
      try
      {
        localJSONObject = new JSONObject(paramJsonObject.toString());
        return localJSONObject;
      }
      catch (JSONException paramJsonObject) {}
      return null;
    }
    
    public static JsonObject toJsonObject(JSONObject paramJSONObject)
    {
      JsonObject localJsonObject = null;
      if (paramJSONObject != null) {}
      try
      {
        localJsonObject = sJsonParser.parse(paramJSONObject.toString()).getAsJsonObject();
        return localJsonObject;
      }
      catch (JsonParseException paramJSONObject) {}
      return null;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/util/GsonHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */