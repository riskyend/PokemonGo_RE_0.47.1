package com.upsight.android.marketing.internal.content;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.event.UpsightDynamicEvent;
import com.upsight.android.analytics.event.UpsightDynamicEvent.Builder;
import com.upsight.android.analytics.event.datacollection.UpsightDataCollectionEvent;
import com.upsight.android.analytics.event.datacollection.UpsightDataCollectionEvent.Builder;
import com.upsight.android.analytics.internal.action.Action;
import com.upsight.android.analytics.internal.action.ActionContext;
import com.upsight.android.analytics.internal.action.ActionFactory;
import com.upsight.android.analytics.internal.association.Association;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.R.id;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.UpsightPurchase;
import com.upsight.android.marketing.UpsightReward;
import com.upsight.android.persistence.UpsightDataStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;

public final class MarketingContentActions
{
  private static final Map<String, InternalFactory> FACTORY_MAP = new HashMap() {};
  
  static class AssociateOnce
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String UPSIGHT_DATA = "upsight_data";
    public static final String UPSIGHT_DATA_FILTER = "upsight_data_filter";
    public static final String WITH = "with";
    
    private AssociateOnce(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      ActionContext localActionContext = getActionContext();
      String str = optParamString("with");
      JsonObject localJsonObject1 = optParamJsonObject("upsight_data_filter");
      JsonObject localJsonObject2 = optParamJsonObject("upsight_data");
      try
      {
        Association localAssociation = Association.from(str, localJsonObject1, localJsonObject2, localActionContext.mGson, localActionContext.mClock);
        localActionContext.mUpsight.getDataStore().store(localAssociation);
        paramMarketingContent.signalActionCompleted(localActionContext.mBus);
        return;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          localActionContext.mLogger.e(getClass().getSimpleName(), localIOException, "Failed to parse Association with=" + str + " upsightDataFilter=" + localJsonObject1 + " upsightData" + localJsonObject2, new Object[0]);
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        for (;;) {}
      }
    }
  }
  
  static class Destroy
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    private Destroy(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      Object localObject = paramMarketingContent.getId();
      MarketingContentActions.MarketingContentActionContext localMarketingContentActionContext = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        localMarketingContentActionContext.mContentStore.remove((String)localObject);
        localMarketingContentActionContext.mBus.post(new MarketingContentActions.DestroyEvent((String)localObject, null));
      }
      localObject = localMarketingContentActionContext.mBus;
      paramMarketingContent.signalActionCompleted((Bus)localObject);
      paramMarketingContent.signalActionMapCompleted((Bus)localObject);
    }
  }
  
  public static class DestroyEvent
  {
    public final String mId;
    
    private DestroyEvent(String paramString)
    {
      this.mId = paramString;
    }
  }
  
  private static abstract interface InternalFactory
  {
    public abstract Action<MarketingContent, MarketingContentActions.MarketingContentActionContext> create(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject);
  }
  
  public static class MarketingContentActionContext
    extends ActionContext
  {
    public final MarketingContentMediatorManager mContentMediatorManager;
    public final MarketingContentStore mContentStore;
    public final ContentTemplateWebViewClientFactory mContentTemplateWebViewClientFactory;
    
    public MarketingContentActionContext(UpsightContext paramUpsightContext, Bus paramBus, Gson paramGson, Clock paramClock, Scheduler.Worker paramWorker, UpsightLogger paramUpsightLogger, MarketingContentMediatorManager paramMarketingContentMediatorManager, MarketingContentStore paramMarketingContentStore, ContentTemplateWebViewClientFactory paramContentTemplateWebViewClientFactory)
    {
      super(paramBus, paramGson, paramClock, paramWorker, paramUpsightLogger);
      this.mContentMediatorManager = paramMarketingContentMediatorManager;
      this.mContentStore = paramMarketingContentStore;
      this.mContentTemplateWebViewClientFactory = paramContentTemplateWebViewClientFactory;
    }
  }
  
  public static class MarketingContentActionFactory
    implements ActionFactory<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String TYPE = "marketing_content_factory";
    
    public Action<MarketingContent, MarketingContentActions.MarketingContentActionContext> create(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, JsonObject paramJsonObject)
      throws UpsightException
    {
      if (paramJsonObject == null) {
        throw new UpsightException("Failed to create Action. JSON is null.", new Object[0]);
      }
      String str = paramJsonObject.get("action_type").getAsString();
      paramJsonObject = paramJsonObject.getAsJsonObject("parameters");
      MarketingContentActions.InternalFactory localInternalFactory = (MarketingContentActions.InternalFactory)MarketingContentActions.FACTORY_MAP.get(str);
      if (localInternalFactory == null) {
        throw new UpsightException("Failed to create Action. Unknown action type.", new Object[0]);
      }
      return localInternalFactory.create(paramMarketingContentActionContext, str, paramJsonObject);
    }
  }
  
  static class NotifyPurchases
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String PURCHASES = "purchases";
    
    private NotifyPurchases(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject = optParamJsonArray("purchases");
      if ((localObject != null) && (((JsonElement)localObject).isJsonArray()))
      {
        ActionContext localActionContext = getActionContext();
        Iterator localIterator = ((JsonElement)localObject).getAsJsonArray().iterator();
        while (localIterator.hasNext())
        {
          localObject = null;
          try
          {
            JsonElement localJsonElement = (JsonElement)localIterator.next();
            localObject = localJsonElement;
            localArrayList.add(Purchase.from(localJsonElement, localActionContext.mGson));
          }
          catch (IOException localIOException)
          {
            localActionContext.mLogger.e(getClass().getSimpleName(), localIOException, "Failed to parse Purchase purchaseJson=" + localObject, new Object[0]);
          }
        }
      }
      localObject = ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus;
      ((Bus)localObject).post(new MarketingContentActions.PurchasesEvent(paramMarketingContent.getId(), localArrayList, null));
      paramMarketingContent.signalActionCompleted((Bus)localObject);
    }
  }
  
  static class NotifyRewards
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String REWARDS = "rewards";
    
    private NotifyRewards(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject = optParamJsonArray("rewards");
      if ((localObject != null) && (((JsonElement)localObject).isJsonArray()))
      {
        ActionContext localActionContext = getActionContext();
        Iterator localIterator = ((JsonElement)localObject).getAsJsonArray().iterator();
        while (localIterator.hasNext())
        {
          localObject = null;
          try
          {
            JsonElement localJsonElement = (JsonElement)localIterator.next();
            localObject = localJsonElement;
            localArrayList.add(Reward.from(localJsonElement, localActionContext.mGson));
          }
          catch (IOException localIOException)
          {
            localActionContext.mLogger.e(getClass().getSimpleName(), localIOException, "Failed to parse Reward rewardJson=" + localObject, new Object[0]);
          }
        }
      }
      localObject = ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus;
      ((Bus)localObject).post(new MarketingContentActions.RewardsEvent(paramMarketingContent.getId(), localArrayList, null));
      paramMarketingContent.signalActionCompleted((Bus)localObject);
    }
  }
  
  static class OpenUrl
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String URL = "url";
    
    private OpenUrl(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      MarketingContentActions.MarketingContentActionContext localMarketingContentActionContext = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      Object localObject = optParamString("url");
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        localObject = new Intent("android.intent.action.VIEW", Uri.parse((String)localObject));
        ((Intent)localObject).setFlags(268435456);
      }
      for (;;)
      {
        try
        {
          localMarketingContentActionContext.mUpsight.startActivity((Intent)localObject);
          paramMarketingContent.signalActionCompleted(localMarketingContentActionContext.mBus);
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), localActivityNotFoundException, "Action execution failed actionType=" + getType() + " intent=" + localObject, new Object[0]);
          continue;
        }
        localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), "Action execution failed actionType=" + getType() + " uri=" + (String)localObject, new Object[0]);
      }
    }
  }
  
  static class PresentCloseButton
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String DELAY_MS = "delay_ms";
    
    private PresentCloseButton(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(final MarketingContent paramMarketingContent)
    {
      long l = optParamInt("delay_ms");
      ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mMainWorker.schedule(new Action0()
      {
        public void call()
        {
          View localView = paramMarketingContent.getContentView();
          if ((localView != null) && (localView.getRootView() != null)) {
            ((ImageView)localView.findViewById(R.id.upsight_marketing_content_view_close_button)).setVisibility(0);
          }
        }
      }, l, TimeUnit.MILLISECONDS);
      paramMarketingContent.signalActionCompleted(((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus);
    }
  }
  
  static class PresentContent
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    private PresentContent(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      Bus localBus = ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus;
      localBus.post(new MarketingContent.SubcontentAvailabilityEvent(paramMarketingContent.getId()));
      paramMarketingContent.signalActionCompleted(localBus);
    }
  }
  
  static class PresentDialog
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String BUTTONS = "buttons";
    public static final String DISMISS_TRIGGER = "dismiss_trigger";
    public static final String MESSAGE = "message";
    public static final String TITLE = "title";
    
    private PresentDialog(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      Object localObject1 = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      String str1 = optParamString("title");
      String str2 = optParamString("message");
      Object localObject2 = optParamJsonArray("buttons");
      String str3 = optParamString("dismiss_trigger");
      localObject1 = null;
      if (localObject2 != null) {
        localObject1 = ((JsonArray)localObject2).toString();
      }
      localObject2 = ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus;
      localObject1 = new MarketingContent.PendingDialog(paramMarketingContent.getId(), str1, str2, (String)localObject1, str3);
      ((Bus)localObject2).post(new MarketingContent.SubdialogAvailabilityEvent(paramMarketingContent.getId(), (MarketingContent.PendingDialog)localObject1));
      paramMarketingContent.signalActionCompleted((Bus)localObject2);
    }
  }
  
  static class PresentScopedContent
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String ID = "id";
    public static final String SCOPE_LIST = "scope_list";
    
    private PresentScopedContent(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      String str = optParamString("id");
      Object localObject = optParamJsonArray("scope_list");
      if ((!TextUtils.isEmpty(str)) && (localObject != null) && (((JsonElement)localObject).isJsonArray()))
      {
        ArrayList localArrayList = new ArrayList();
        localObject = ((JsonElement)localObject).getAsJsonArray().iterator();
        while (((Iterator)localObject).hasNext())
        {
          JsonElement localJsonElement = (JsonElement)((Iterator)localObject).next();
          if ((localJsonElement.isJsonPrimitive()) && (localJsonElement.getAsJsonPrimitive().isString())) {
            localArrayList.add(localJsonElement.getAsString());
          }
        }
        ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mContentStore.presentScopedContent(str, (String[])localArrayList.toArray(new String[localArrayList.size()]));
      }
      paramMarketingContent.signalActionCompleted(((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus);
    }
  }
  
  static class PresentScopedDialog
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String BUTTONS = "buttons";
    public static final String DISMISS_TRIGGER = "dismiss_trigger";
    public static final String MESSAGE = "message";
    public static final String SCOPE_LIST = "scope_list";
    public static final String TITLE = "title";
    
    private PresentScopedDialog(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      MarketingContentActions.MarketingContentActionContext localMarketingContentActionContext = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      Object localObject = optParamJsonArray("scope_list");
      String str1 = optParamString("title");
      String str2 = optParamString("message");
      JsonArray localJsonArray = optParamJsonArray("buttons");
      String str3 = optParamString("dismiss_trigger");
      ArrayList localArrayList = new ArrayList();
      localObject = ((JsonArray)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        JsonElement localJsonElement = (JsonElement)((Iterator)localObject).next();
        if ((localJsonElement.isJsonPrimitive()) && (localJsonElement.getAsJsonPrimitive().isString())) {
          localArrayList.add(localJsonElement.getAsString());
        }
      }
      localObject = null;
      if (localJsonArray != null) {
        localObject = localJsonArray.toString();
      }
      paramMarketingContent.addPendingDialog(new MarketingContent.PendingDialog(paramMarketingContent.getId(), str1, str2, (String)localObject, str3));
      ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mContentStore.presentScopedContent(paramMarketingContent.getId(), (String[])localArrayList.toArray(new String[localArrayList.size()]));
      paramMarketingContent.signalActionCompleted(localMarketingContentActionContext.mBus);
    }
  }
  
  static class PresentScopelessContent
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String NEXT_ID = "next_id";
    public static final String SELF_ID = "self_id";
    
    private PresentScopelessContent(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      String str1 = optParamString("self_id");
      String str2 = optParamString("next_id");
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2))) {
        ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mContentStore.presentScopelessContent(str2, str1);
      }
      paramMarketingContent.signalActionCompleted(((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus);
    }
  }
  
  public static class PurchasesEvent
  {
    public final String mId;
    public final List<UpsightPurchase> mPurchases;
    
    private PurchasesEvent(String paramString, List<UpsightPurchase> paramList)
    {
      this.mId = paramString;
      this.mPurchases = paramList;
    }
  }
  
  public static class RewardsEvent
  {
    public final String mId;
    public final List<UpsightReward> mRewards;
    
    private RewardsEvent(String paramString, List<UpsightReward> paramList)
    {
      this.mId = paramString;
      this.mRewards = paramList;
    }
  }
  
  static class SendEvent
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String EVENT = "event";
    public static final String IDENTIFIERS = "identifiers";
    public static final String PUB_DATA = "pub_data";
    public static final String TYPE = "type";
    public static final String UPSIGHT_DATA = "upsight_data";
    
    private SendEvent(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      MarketingContentActions.MarketingContentActionContext localMarketingContentActionContext = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      JsonObject localJsonObject = optParamJsonObject("event");
      Object localObject;
      if (localJsonObject != null)
      {
        localObject = localJsonObject.get("type");
        if ((((JsonElement)localObject).isJsonPrimitive()) && (((JsonElement)localObject).getAsJsonPrimitive().isString()))
        {
          localObject = UpsightDynamicEvent.createBuilder(((JsonElement)localObject).getAsString()).putUpsightData(localJsonObject.get("upsight_data").getAsJsonObject());
          if (localJsonObject.has("pub_data")) {
            ((UpsightDynamicEvent.Builder)localObject).putPublisherData(localJsonObject.getAsJsonObject("pub_data"));
          }
          if ((localJsonObject.has("identifiers")) && (localJsonObject.get("identifiers").isJsonPrimitive()) && (localJsonObject.get("identifiers").getAsJsonPrimitive().isString())) {
            ((UpsightDynamicEvent.Builder)localObject).setDynamicIdentifiers(localJsonObject.get("identifiers").getAsString());
          }
          ((UpsightDynamicEvent.Builder)localObject).record(localMarketingContentActionContext.mUpsight);
        }
      }
      for (;;)
      {
        paramMarketingContent.signalActionCompleted(localMarketingContentActionContext.mBus);
        return;
        localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), "Action failed actionType=" + getType() + " type=" + localObject, new Object[0]);
        continue;
        localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), "Action failed actionType=" + getType() + " event=" + localJsonObject, new Object[0]);
      }
    }
  }
  
  static class SendFormData
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String DATA_KEY = "data_key";
    public static final String STREAM_ID = "stream_id";
    
    private SendFormData(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      MarketingContentActions.MarketingContentActionContext localMarketingContentActionContext = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      String str2 = optParamString("data_key");
      String str1 = optParamString("stream_id");
      if ((str2 != null) && (str1 != null))
      {
        str2 = paramMarketingContent.getExtra(str2);
        if (str2 != null) {
          UpsightDataCollectionEvent.createBuilder(str2, str1).record(localMarketingContentActionContext.mUpsight);
        }
      }
      for (;;)
      {
        paramMarketingContent.signalActionCompleted(localMarketingContentActionContext.mBus);
        return;
        localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), "Action failed actionType=" + getType() + " dataKey=" + str2, new Object[0]);
      }
    }
  }
  
  static class Trigger
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String TRIGGER = "trigger";
    
    private Trigger(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      String str = optParamString("trigger");
      if (!TextUtils.isEmpty(str)) {
        paramMarketingContent.executeActions(str);
      }
      paramMarketingContent.signalActionCompleted(((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus);
    }
  }
  
  static class TriggerIfContentAvailable
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String CONDITION_PARAMETERS = "condition_parameters";
    public static final String ELSE_TRIGGER = "else_trigger";
    public static final String ID = "id";
    public static final String THEN_TRIGGER = "then_trigger";
    public static final String TIMEOUT_MS = "timeout_ms";
    private boolean isTriggerExecuted = false;
    private String mConditionalContentID;
    private MarketingContent mContent;
    private Subscription mSubscription;
    
    private TriggerIfContentAvailable(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(final MarketingContent paramMarketingContent)
    {
      localObject1 = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      this.mContent = paramMarketingContent;
      l1 = 0L;
      try
      {
        localObject2 = optParamJsonObject("condition_parameters").getAsJsonObject();
        this.mConditionalContentID = ((JsonObject)localObject2).get("id").getAsString();
        long l2 = ((JsonObject)localObject2).get("timeout_ms").getAsLong();
        l1 = l2;
      }
      catch (NullPointerException localNullPointerException)
      {
        for (;;)
        {
          Object localObject2;
          ((MarketingContentActions.MarketingContentActionContext)localObject1).mLogger.e(getClass().getSimpleName(), localNullPointerException, "Action execution failed actionType=" + getType() + " invalid CONDITION_PARAMETERS", new Object[0]);
          continue;
          ((MarketingContentActions.MarketingContentActionContext)localObject1).mBus.register(this);
          this.mSubscription = ((MarketingContentActions.MarketingContentActionContext)localObject1).mMainWorker.schedule(new Action0()
          {
            public void call()
            {
              String str = MarketingContentActions.TriggerIfContentAvailable.this.optParamString("else_trigger");
              if ((!TextUtils.isEmpty(str)) && (!MarketingContentActions.TriggerIfContentAvailable.this.isTriggerExecuted))
              {
                paramMarketingContent.executeActions(str);
                MarketingContentActions.TriggerIfContentAvailable.access$1802(MarketingContentActions.TriggerIfContentAvailable.this, true);
              }
              localObject1.mBus.unregister(this);
            }
          }, l1, TimeUnit.MILLISECONDS);
          continue;
          localObject1 = optParamString("else_trigger");
          if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (!this.isTriggerExecuted))
          {
            paramMarketingContent.executeActions((String)localObject1);
            this.isTriggerExecuted = true;
          }
        }
      }
      if (this.mConditionalContentID != null)
      {
        localObject2 = (MarketingContent)((MarketingContentActions.MarketingContentActionContext)localObject1).mContentStore.get(this.mConditionalContentID);
        if ((localObject2 != null) && (((MarketingContent)localObject2).isLoaded()))
        {
          localObject1 = optParamString("then_trigger");
          if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (!this.isTriggerExecuted))
          {
            paramMarketingContent.executeActions((String)localObject1);
            this.isTriggerExecuted = true;
          }
          paramMarketingContent.signalActionCompleted(((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus);
          return;
        }
      }
    }
    
    @Subscribe
    public void handleAvailabilityEvent(MarketingContent.ContentLoadedEvent paramContentLoadedEvent)
    {
      if (paramContentLoadedEvent.getId().equals(this.mConditionalContentID))
      {
        this.mSubscription.unsubscribe();
        ((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus.unregister(this);
        paramContentLoadedEvent = optParamString("then_trigger");
        if ((!TextUtils.isEmpty(paramContentLoadedEvent)) && (!this.isTriggerExecuted))
        {
          this.mContent.executeActions(paramContentLoadedEvent);
          this.isTriggerExecuted = true;
        }
      }
    }
  }
  
  static class TriggerIfContentBuilt
    extends Action<MarketingContent, MarketingContentActions.MarketingContentActionContext>
  {
    public static final String CONDITION_PARAMETERS = "condition_parameters";
    public static final String CONTENT_MODEL = "content_model";
    public static final String CONTENT_PROVIDER = "content_provider";
    public static final String CONTENT_PROVIDER_NAME = "name";
    public static final String ELSE_TRIGGER = "else_trigger";
    public static final String THEN_TRIGGER = "then_trigger";
    
    private TriggerIfContentBuilt(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, String paramString, JsonObject paramJsonObject)
    {
      super(paramString, paramJsonObject);
    }
    
    public void execute(MarketingContent paramMarketingContent)
    {
      i = 0;
      int j = 0;
      localMarketingContentActionContext = (MarketingContentActions.MarketingContentActionContext)getActionContext();
      Object localObject2 = null;
      Object localObject3 = optParamJsonObject("condition_parameters").get("content_provider");
      localObject1 = localObject2;
      if (localObject3 != null)
      {
        localObject3 = ((JsonElement)localObject3).getAsJsonObject().get("name");
        localObject1 = localObject2;
        if (((JsonElement)localObject3).isJsonPrimitive())
        {
          localObject1 = localObject2;
          if (((JsonElement)localObject3).getAsJsonPrimitive().isString()) {
            localObject1 = localMarketingContentActionContext.mContentMediatorManager.getContentMediator(((JsonElement)localObject3).getAsString());
          }
        }
      }
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = localMarketingContentActionContext.mContentMediatorManager.getDefaultContentMediator();
      }
      paramMarketingContent.setContentMediator((UpsightContentMediator)localObject2);
      localObject1 = null;
      try
      {
        localObject3 = optParamJsonObject("condition_parameters").get("content_model");
        localObject1 = localObject3;
      }
      catch (NullPointerException localNullPointerException)
      {
        for (;;)
        {
          localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), localNullPointerException, "Action execution failed actionType=" + getType() + " invalid CONDITION_PARAMETERS", new Object[0]);
          continue;
          i = 0;
          continue;
          localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), "Action execution failed actionType=" + getType() + " model=" + localObject1, new Object[0]);
          continue;
          localObject1 = optParamString("else_trigger");
          if (!TextUtils.isEmpty((CharSequence)localObject1)) {
            paramMarketingContent.executeActions((String)localObject1);
          }
        }
      }
      if ((localObject1 != null) && (((JsonElement)localObject1).isJsonObject()))
      {
        j = i;
        try
        {
          localMarketingContentActionContext.mContentStore.put(paramMarketingContent.getId(), paramMarketingContent);
          j = i;
          localObject3 = ((UpsightContentMediator)localObject2).buildContentModel(paramMarketingContent, localMarketingContentActionContext, ((JsonElement)localObject1).getAsJsonObject());
          if (localObject3 == null) {
            break label318;
          }
          i = 1;
          j = i;
          paramMarketingContent.setContentModel(localObject3);
          j = i;
          if (i != 0)
          {
            j = i;
            paramMarketingContent.setContentView(((UpsightContentMediator)localObject2).buildContentView(paramMarketingContent, localMarketingContentActionContext));
            j = i;
          }
        }
        catch (Exception localException)
        {
          for (;;)
          {
            localMarketingContentActionContext.mLogger.e(getClass().getSimpleName(), localException, "Action execution failed actionType=" + getType() + " model=" + localObject1, new Object[0]);
          }
        }
        if (j == 0) {
          break label439;
        }
        localObject1 = optParamString("then_trigger");
        if (!TextUtils.isEmpty((CharSequence)localObject1)) {
          paramMarketingContent.executeActions((String)localObject1);
        }
        paramMarketingContent.signalActionCompleted(((MarketingContentActions.MarketingContentActionContext)getActionContext()).mBus);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContentActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */