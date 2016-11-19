package com.upsight.android.analytics.internal.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class Action<T extends Actionable, U extends ActionContext>
{
  private U mActionContext;
  private JsonObject mParams;
  private String mType;
  
  protected Action(U paramU, String paramString, JsonObject paramJsonObject)
  {
    this.mActionContext = paramU;
    this.mType = paramString;
    this.mParams = paramJsonObject;
  }
  
  public abstract void execute(T paramT);
  
  public U getActionContext()
  {
    return this.mActionContext;
  }
  
  public String getType()
  {
    return this.mType;
  }
  
  protected int optParamInt(String paramString)
  {
    if (this.mParams != null)
    {
      paramString = this.mParams.get(paramString);
      if ((paramString != null) && (paramString.isJsonPrimitive()) && (paramString.getAsJsonPrimitive().isNumber())) {
        return paramString.getAsInt();
      }
    }
    return 0;
  }
  
  protected JsonArray optParamJsonArray(String paramString)
  {
    if (this.mParams != null)
    {
      paramString = this.mParams.get(paramString);
      if ((paramString != null) && (paramString.isJsonArray())) {
        return paramString.getAsJsonArray();
      }
    }
    return null;
  }
  
  protected JsonObject optParamJsonObject(String paramString)
  {
    if (this.mParams != null)
    {
      paramString = this.mParams.get(paramString);
      if ((paramString != null) && (paramString.isJsonObject())) {
        return paramString.getAsJsonObject();
      }
    }
    return null;
  }
  
  protected String optParamString(String paramString)
  {
    if (this.mParams != null)
    {
      paramString = this.mParams.get(paramString);
      if ((paramString != null) && (paramString.isJsonPrimitive()) && (paramString.getAsJsonPrimitive().isString())) {
        return paramString.getAsString();
      }
    }
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/action/Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */