package com.upsight.android.analytics.internal.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.upsight.android.UpsightException;
import com.upsight.android.logger.UpsightLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ActionMap<T extends Actionable, U extends ActionContext>
  extends HashMap<String, List<Action<T, U>>>
{
  private static final String ACTIONS = "actions";
  private static final String LOG_TEMPLATE_ACTION = "  -> %1$s";
  private static final String LOG_TEMPLATE_TRIGGER = "%1$s on %2$s:";
  private static final String TAG = ActionMap.class.getSimpleName();
  private static final String TRIGGER = "trigger";
  private int mActiveActionCount = 0;
  private boolean mIsActionMapCompleted = false;
  private UpsightLogger mLogger;
  
  public ActionMap(ActionFactory<T, U> paramActionFactory, U paramU, JsonArray paramJsonArray)
  {
    this.mLogger = paramU.mLogger;
    if ((paramJsonArray != null) && (paramJsonArray.isJsonArray()))
    {
      Iterator localIterator = paramJsonArray.getAsJsonArray().iterator();
      while (localIterator.hasNext())
      {
        paramJsonArray = ((JsonElement)localIterator.next()).getAsJsonObject();
        JsonElement localJsonElement1 = paramJsonArray.get("trigger");
        JsonElement localJsonElement2 = paramJsonArray.get("actions");
        if ((localJsonElement1 != null) && (localJsonElement1.isJsonPrimitive()) && (localJsonElement1.getAsJsonPrimitive().isString()) && (localJsonElement2 != null) && (localJsonElement2.isJsonArray()))
        {
          int j = localJsonElement2.getAsJsonArray().size();
          if (j > 0)
          {
            ArrayList localArrayList = new ArrayList(j);
            int i = 0;
            for (;;)
            {
              if (i >= j) {
                break label243;
              }
              paramJsonArray = null;
              try
              {
                JsonObject localJsonObject = localJsonElement2.getAsJsonArray().get(i).getAsJsonObject();
                paramJsonArray = localJsonObject;
                localArrayList.add(paramActionFactory.create(paramU, localJsonObject));
              }
              catch (UpsightException localUpsightException)
              {
                for (;;)
                {
                  paramU.mLogger.e(TAG, localUpsightException, "Unable to create action from actionJSON=" + paramJsonArray, new Object[0]);
                }
              }
              i += 1;
            }
            label243:
            if (localArrayList.size() > 0) {
              put(localJsonElement1.getAsString(), localArrayList);
            }
          }
        }
      }
    }
  }
  
  private boolean isFinished()
  {
    return (this.mIsActionMapCompleted) && (this.mActiveActionCount == 0);
  }
  
  public void executeActions(String paramString, T paramT)
  {
    try
    {
      this.mLogger.i(TAG, "%1$s on %2$s:", new Object[] { paramString, paramT.getId() });
      paramString = (List)get(paramString);
      if (paramString != null)
      {
        paramString = paramString.iterator();
        while (paramString.hasNext())
        {
          Action localAction = (Action)paramString.next();
          this.mLogger.i(TAG, "  -> %1$s", new Object[] { localAction.getType() });
          this.mActiveActionCount += 1;
          localAction.execute(paramT);
        }
      }
    }
    finally {}
  }
  
  public boolean signalActionCompleted()
  {
    try
    {
      this.mActiveActionCount -= 1;
      boolean bool = isFinished();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean signalActionMapCompleted()
  {
    try
    {
      this.mIsActionMapCompleted = true;
      boolean bool = isFinished();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/action/ActionMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */