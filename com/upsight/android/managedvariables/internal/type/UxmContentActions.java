package com.upsight.android.managedvariables.internal.type;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightException;
import com.upsight.android.UpsightManagedVariablesExtension;
import com.upsight.android.analytics.event.uxm.UpsightUxmEnumerateEvent;
import com.upsight.android.analytics.event.uxm.UpsightUxmEnumerateEvent.Builder;
import com.upsight.android.analytics.internal.action.Action;
import com.upsight.android.analytics.internal.action.ActionContext;
import com.upsight.android.analytics.internal.action.ActionFactory;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.managedvariables.UpsightManagedVariablesComponent;
import com.upsight.android.persistence.UpsightDataStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import rx.Observable;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public final class UxmContentActions
{
  private static final Map<String, InternalFactory> FACTORY_MAP = new HashMap() {};
  
  static class Destroy
    extends Action<UxmContent, UxmContentActions.UxmContentActionContext>
  {
    private Destroy(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(UxmContent paramUxmContent)
    {
      Bus localBus = ((UxmContentActions.UxmContentActionContext)getActionContext()).mBus;
      paramUxmContent.signalActionCompleted(localBus);
      paramUxmContent.signalActionMapCompleted(localBus);
    }
  }
  
  private static abstract interface InternalFactory
  {
    public abstract Action<UxmContent, UxmContentActions.UxmContentActionContext> create(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, String paramString, JsonObject paramJsonObject);
  }
  
  static class ModifyValue
    extends Action<UxmContent, UxmContentActions.UxmContentActionContext>
  {
    private static final String MATCH = "match";
    private static final String OPERATOR = "operator";
    private static final String OPERATOR_SET = "set";
    private static final String PROPERTY_NAME = "property_name";
    private static final String PROPERTY_VALUE = "property_value";
    private static final String TYPE = "type";
    private static final String VALUES = "values";
    
    private ModifyValue(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    private <T> void modifyValue(final UxmContent paramUxmContent, final Class<T> paramClass, JsonArray paramJsonArray1, JsonArray paramJsonArray2)
    {
      final UxmContentActions.UxmContentActionContext localUxmContentActionContext = (UxmContentActions.UxmContentActionContext)getActionContext();
      final Gson localGson = localUxmContentActionContext.mGson;
      final UpsightLogger localUpsightLogger = localUxmContentActionContext.mUpsight.getLogger();
      final UpsightDataStore localUpsightDataStore = localUxmContentActionContext.mUpsight.getDataStore();
      final Object localObject1 = localUpsightDataStore.fetchObservable(paramClass).map(new Func1()
      {
        public JsonElement call(T paramAnonymousT)
        {
          return localGson.toJsonTree(paramAnonymousT);
        }
      }).cast(JsonObject.class);
      final Object localObject2 = new JsonObject();
      final Object localObject3 = paramJsonArray1.iterator();
      paramJsonArray1 = (JsonArray)localObject1;
      while (((Iterator)localObject3).hasNext())
      {
        final JsonElement localJsonElement = (JsonElement)((Iterator)localObject3).next();
        localObject1 = localJsonElement.getAsJsonObject().get("property_name").getAsString();
        localJsonElement = localJsonElement.getAsJsonObject().get("property_value");
        paramJsonArray1 = paramJsonArray1.filter(new Func1()
        {
          public Boolean call(JsonObject paramAnonymousJsonObject)
          {
            return Boolean.valueOf(paramAnonymousJsonObject.getAsJsonObject().get(localObject1).equals(localJsonElement));
          }
        });
        ((JsonObject)localObject2).add((String)localObject1, localJsonElement);
      }
      paramJsonArray1 = paramJsonArray1.defaultIfEmpty(localObject2);
      paramJsonArray2 = paramJsonArray2.iterator();
      while (paramJsonArray2.hasNext())
      {
        localObject3 = (JsonElement)paramJsonArray2.next();
        localObject1 = ((JsonElement)localObject3).getAsJsonObject().get("operator").getAsString();
        localObject2 = ((JsonElement)localObject3).getAsJsonObject().get("property_name").getAsString();
        localObject3 = ((JsonElement)localObject3).getAsJsonObject().get("property_value");
        if ("set".equals(localObject1)) {
          paramJsonArray1 = paramJsonArray1.map(new Func1()
          {
            public JsonObject call(JsonObject paramAnonymousJsonObject)
            {
              paramAnonymousJsonObject.add(localObject2, localObject3);
              return paramAnonymousJsonObject;
            }
          });
        }
      }
      paramJsonArray1.subscribeOn(localUxmContentActionContext.mUpsight.getCoreComponent().subscribeOnScheduler()).observeOn(localUxmContentActionContext.mUpsight.getCoreComponent().observeOnScheduler()).subscribe(new Action1()new Action1
      {
        public void call(final JsonObject paramAnonymousJsonObject)
        {
          try
          {
            localUpsightDataStore.storeObservable(localGson.fromJson(paramAnonymousJsonObject, paramClass)).subscribeOn(localUxmContentActionContext.mUpsight.getCoreComponent().subscribeOnScheduler()).observeOn(localUxmContentActionContext.mUpsight.getCoreComponent().observeOnScheduler()).subscribe(new Action1()new Action1
            {
              public void call(T paramAnonymous2T)
              {
                UxmContentActions.ModifyValue.4.this.val$logger.d("Upsight", "Modified managed variable of class " + UxmContentActions.ModifyValue.4.this.val$clazz + " with value " + paramAnonymousJsonObject, new Object[0]);
              }
            }, new Action1()new Action0
            {
              public void call(Throwable paramAnonymous2Throwable)
              {
                UxmContentActions.ModifyValue.4.this.val$logger.e("Upsight", paramAnonymous2Throwable, "Failed to modify managed variable of class " + UxmContentActions.ModifyValue.4.this.val$clazz, new Object[0]);
              }
            }, new Action0()
            {
              public void call()
              {
                UxmContentActions.ModifyValue.4.this.val$content.signalActionCompleted(UxmContentActions.ModifyValue.4.this.val$actionContext.mBus);
              }
            });
            return;
          }
          catch (JsonSyntaxException paramAnonymousJsonObject)
          {
            localUpsightLogger.e("Upsight", paramAnonymousJsonObject, "Failed to parse managed variable of class " + paramClass, new Object[0]);
            paramUxmContent.signalActionCompleted(localUxmContentActionContext.mBus);
          }
        }
      }, new Action1()
      {
        public void call(Throwable paramAnonymousThrowable)
        {
          localUpsightLogger.e("Upsight", paramAnonymousThrowable, "Failed to fetch managed variable of class " + paramClass, new Object[0]);
          paramUxmContent.signalActionCompleted(localUxmContentActionContext.mBus);
        }
      });
    }
    
    public void execute(UxmContent paramUxmContent)
    {
      int j = 1;
      ActionContext localActionContext = getActionContext();
      int i = j;
      String str;
      Class localClass;
      if (paramUxmContent.shouldApplyBundle())
      {
        str = optParamString("type");
        JsonArray localJsonArray1 = optParamJsonArray("match");
        JsonArray localJsonArray2 = optParamJsonArray("values");
        i = j;
        if (!TextUtils.isEmpty(str))
        {
          i = j;
          if (localJsonArray1 != null)
          {
            i = j;
            if (localJsonArray2 != null)
            {
              localClass = null;
              if (!"com.upsight.uxm.string".equals(str)) {
                break label114;
              }
              localClass = ManagedString.Model.class;
              if (localClass == null) {
                break label165;
              }
              modifyValue(paramUxmContent, localClass, localJsonArray1, localJsonArray2);
            }
          }
        }
      }
      for (i = 0;; i = j)
      {
        if (i != 0) {
          paramUxmContent.signalActionCompleted(localActionContext.mBus);
        }
        return;
        label114:
        if ("com.upsight.uxm.boolean".equals(str))
        {
          localClass = ManagedBoolean.Model.class;
          break;
        }
        if ("com.upsight.uxm.integer".equals(str))
        {
          localClass = ManagedInt.Model.class;
          break;
        }
        if (!"com.upsight.uxm.float".equals(str)) {
          break;
        }
        localClass = ManagedFloat.Model.class;
        break;
        label165:
        localActionContext.mLogger.e("Upsight", "Failed to execute action_modify_value due to unknown managed variable type " + str, new Object[0]);
      }
    }
  }
  
  static class NotifyUxmValuesSynchronized
    extends Action<UxmContent, UxmContentActions.UxmContentActionContext>
  {
    private static final String TAGS = "tags";
    
    private NotifyUxmValuesSynchronized(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(UxmContent paramUxmContent)
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject = optParamJsonArray("tags");
      if ((paramUxmContent.shouldApplyBundle()) && (localObject != null))
      {
        localObject = ((JsonArray)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          JsonElement localJsonElement = (JsonElement)((Iterator)localObject).next();
          if ((localJsonElement.isJsonPrimitive()) && (localJsonElement.getAsJsonPrimitive().isString())) {
            localArrayList.add(localJsonElement.getAsString());
          }
        }
      }
      localObject = ((UxmContentActions.UxmContentActionContext)getActionContext()).mBus;
      ((Bus)localObject).post(new UxmContentActions.ScheduleSyncNotificationEvent(paramUxmContent.getId(), localArrayList, null));
      paramUxmContent.signalActionCompleted((Bus)localObject);
    }
  }
  
  public static class ScheduleSyncNotificationEvent
  {
    public final String mId;
    public final List<String> mTags;
    
    private ScheduleSyncNotificationEvent(String paramString, List<String> paramList)
    {
      this.mId = paramString;
      this.mTags = paramList;
    }
  }
  
  static class SetBundleId
    extends Action<UxmContent, UxmContentActions.UxmContentActionContext>
  {
    private static final String BUNDLE_ID = "bundle.id";
    
    private SetBundleId(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(UxmContent paramUxmContent)
    {
      if (paramUxmContent.shouldApplyBundle()) {
        PreferencesHelper.putString(((UxmContentActions.UxmContentActionContext)getActionContext()).mUpsight, "uxmBundleId", optParamString("bundle.id"));
      }
      paramUxmContent.signalActionCompleted(((UxmContentActions.UxmContentActionContext)getActionContext()).mBus);
    }
  }
  
  public static class UxmContentActionContext
    extends ActionContext
  {
    public UxmContentActionContext(UpsightContext paramUpsightContext, Bus paramBus, Gson paramGson, Clock paramClock, Scheduler.Worker paramWorker, UpsightLogger paramUpsightLogger)
    {
      super(paramBus, paramGson, paramClock, paramWorker, paramUpsightLogger);
    }
  }
  
  public static class UxmContentActionFactory
    implements ActionFactory<UxmContent, UxmContentActions.UxmContentActionContext>
  {
    public static final String TYPE = "datastore_factory";
    
    public Action<UxmContent, UxmContentActions.UxmContentActionContext> create(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, JsonObject paramJsonObject)
      throws UpsightException
    {
      if (paramJsonObject == null) {
        throw new UpsightException("Failed to create Action. JSON is null.", new Object[0]);
      }
      String str = paramJsonObject.get("action_type").getAsString();
      paramJsonObject = paramJsonObject.getAsJsonObject("parameters");
      UxmContentActions.InternalFactory localInternalFactory = (UxmContentActions.InternalFactory)UxmContentActions.FACTORY_MAP.get(str);
      if (localInternalFactory == null) {
        throw new UpsightException("Failed to create Action. Unknown action type.", new Object[0]);
      }
      return localInternalFactory.create(paramUxmContentActionContext, str, paramJsonObject);
    }
  }
  
  static class UxmEnumerate
    extends Action<UxmContent, UxmContentActions.UxmContentActionContext>
  {
    private UxmEnumerate(UxmContentActions.UxmContentActionContext paramUxmContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(UxmContent paramUxmContent)
    {
      ActionContext localActionContext = getActionContext();
      Object localObject = (UpsightManagedVariablesExtension)localActionContext.mUpsight.getUpsightExtension("com.upsight.extension.managedvariables");
      if (localObject != null) {
        localObject = ((UpsightManagedVariablesComponent)((UpsightManagedVariablesExtension)localObject).getComponent()).uxmSchema().mSchemaJsonString;
      }
      try
      {
        UpsightUxmEnumerateEvent.createBuilder(new JSONArray((String)localObject)).record(localActionContext.mUpsight);
        paramUxmContent.signalActionCompleted(localActionContext.mBus);
        return;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          localActionContext.mUpsight.getLogger().e("Upsight", localJSONException, "Failed to send UXM enumerate event", new Object[0]);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmContentActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */