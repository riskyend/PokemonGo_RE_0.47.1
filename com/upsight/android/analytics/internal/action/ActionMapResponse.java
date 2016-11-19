package com.upsight.android.analytics.internal.action;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.action_map")
public final class ActionMapResponse
{
  @Expose
  @SerializedName("action_factory")
  String actionFactory;
  @Expose
  @SerializedName("action_map")
  JsonArray actionMap;
  @Expose
  @SerializedName("id")
  String actionMapId;
  @UpsightStorableIdentifier
  String id;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (ActionMapResponse)paramObject;
      if (this.id == null) {
        break;
      }
    } while (this.id.equals(((ActionMapResponse)paramObject).id));
    for (;;)
    {
      return false;
      if (((ActionMapResponse)paramObject).id == null) {
        break;
      }
    }
  }
  
  public String getActionFactory()
  {
    return this.actionFactory;
  }
  
  public JsonArray getActionMap()
  {
    return this.actionMap;
  }
  
  public String getActionMapId()
  {
    return this.actionMapId;
  }
  
  public int hashCode()
  {
    if (this.id != null) {
      return this.id.hashCode();
    }
    return 0;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/action/ActionMapResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */