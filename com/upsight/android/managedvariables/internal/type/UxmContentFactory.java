package com.upsight.android.managedvariables.internal.type;

import android.text.TextUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.upsight.android.analytics.internal.action.ActionMap;
import com.upsight.android.analytics.internal.action.ActionMapResponse;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import com.upsight.android.managedvariables.experience.UpsightUserExperience.Handler;
import java.util.Iterator;

public final class UxmContentFactory
{
  private static final String ACTION_MODIFY_VALUE = "action_modify_value";
  private static final String ACTION_SET_BUNDLE_ID = "action_set_bundle_id";
  private static final String KEY_ACTIONS = "actions";
  private static final String KEY_ACTION_TYPE = "action_type";
  private static final UxmContentActions.UxmContentActionFactory sUxmContentActionFactory = new UxmContentActions.UxmContentActionFactory();
  private UxmContentActions.UxmContentActionContext mActionContext;
  private UpsightUserExperience mUserExperience;
  
  public UxmContentFactory(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, UpsightUserExperience paramUpsightUserExperience)
  {
    this.mActionContext = paramUxmContentActionContext;
    this.mUserExperience = paramUpsightUserExperience;
  }
  
  public UxmContent create(ActionMapResponse paramActionMapResponse)
  {
    Object localObject2 = null;
    String str = paramActionMapResponse.getActionMapId();
    Object localObject1 = localObject2;
    if (!TextUtils.isEmpty(str))
    {
      localObject1 = localObject2;
      if ("datastore_factory".equals(paramActionMapResponse.getActionFactory()))
      {
        boolean bool1 = false;
        paramActionMapResponse = paramActionMapResponse.getActionMap();
        localObject1 = localObject2;
        if (paramActionMapResponse != null)
        {
          localObject1 = localObject2;
          if (paramActionMapResponse.isJsonArray())
          {
            localObject1 = paramActionMapResponse.getAsJsonArray().iterator();
            boolean bool2;
            do
            {
              bool2 = bool1;
              if (!((Iterator)localObject1).hasNext()) {
                break;
              }
              localObject2 = ((JsonElement)((Iterator)localObject1).next()).getAsJsonObject().get("actions");
              bool2 = bool1;
              if (localObject2 != null)
              {
                bool2 = bool1;
                if (((JsonElement)localObject2).isJsonArray())
                {
                  localObject2 = ((JsonElement)localObject2).getAsJsonArray().iterator();
                  JsonElement localJsonElement;
                  do
                  {
                    bool2 = bool1;
                    if (!((Iterator)localObject2).hasNext()) {
                      break;
                    }
                    localJsonElement = ((JsonElement)((Iterator)localObject2).next()).getAsJsonObject().get("action_type");
                  } while ((!"action_set_bundle_id".equals(localJsonElement.getAsString())) && (!"action_modify_value".equals(localJsonElement.getAsString())));
                  bool2 = this.mUserExperience.getHandler().onReceive();
                }
              }
              bool1 = bool2;
            } while (!bool2);
            localObject1 = UxmContent.create(str, new ActionMap(sUxmContentActionFactory, this.mActionContext, paramActionMapResponse.getAsJsonArray()), bool2);
          }
        }
      }
    }
    return (UxmContent)localObject1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmContentFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */