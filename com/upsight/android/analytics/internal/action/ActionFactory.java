package com.upsight.android.analytics.internal.action;

import com.google.gson.JsonObject;
import com.upsight.android.UpsightException;

public abstract interface ActionFactory<T extends Actionable, U extends ActionContext>
{
  public static final String KEY_ACTION_PARAMS = "parameters";
  public static final String KEY_ACTION_TYPE = "action_type";
  
  public abstract Action<T, U> create(U paramU, JsonObject paramJsonObject)
    throws UpsightException;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/action/ActionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */