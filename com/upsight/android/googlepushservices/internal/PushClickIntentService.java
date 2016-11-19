package com.upsight.android.googlepushservices.internal;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightGooglePushServicesExtension;
import com.upsight.android.analytics.event.comm.UpsightCommClickEvent;
import com.upsight.android.analytics.event.comm.UpsightCommClickEvent.Builder;
import com.upsight.android.analytics.event.content.UpsightContentUnrenderedEvent;
import com.upsight.android.analytics.event.content.UpsightContentUnrenderedEvent.Builder;
import com.upsight.android.analytics.internal.session.ApplicationStatus;
import com.upsight.android.analytics.internal.session.ApplicationStatus.State;
import com.upsight.android.analytics.internal.session.PushSessionInitializer;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.googlepushservices.UpsightGooglePushServicesComponent;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import javax.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.observables.BlockingObservable;

public class PushClickIntentService
  extends IntentService
{
  private static final String BUNDLE_KEY_MESSAGE_INTENT = "messageIntent";
  private static final String CONTENT_UNRENDERED_CONTENT_PROVIDER_KEY_NAME = "name";
  private static final String CONTENT_UNRENDERED_CONTENT_PROVIDER_KEY_PARAMETERS = "parameters";
  private static final String CONTENT_UNRENDERED_CONTENT_PROVIDER_PARAMETERS_KEY_CONTENT_ID = "content_id";
  private static final String LOG_TAG = PushClickIntentService.class.getSimpleName();
  private static final String PARAM_KEY_IS_DISPATCH_FROM_FOREGROUND = "isDispatchFromForeground";
  private static final String PARAM_KEY_PUSH_CONTENT_ID = "contentId";
  private static final String SERVICE_NAME = "UpsightGcmPushClickIntentService";
  @Inject
  SessionManager mSessionManager;
  
  public PushClickIntentService()
  {
    super("UpsightGcmPushClickIntentService");
  }
  
  protected static Intent appendMessageIntentBundle(Intent paramIntent, boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    Bundle localBundle = new Bundle();
    if (paramInteger1 != null) {
      localBundle.putInt("campaign_id", paramInteger1.intValue());
    }
    if (paramInteger2 != null) {
      localBundle.putInt("message_id", paramInteger2.intValue());
    }
    if (paramInteger3 != null) {
      localBundle.putInt("contentId", paramInteger3.intValue());
    }
    localBundle.putBoolean("isDispatchFromForeground", paramBoolean);
    paramIntent.putExtra("pushMessage", true);
    paramIntent.addFlags(872415232);
    return paramIntent.putExtra("session_extra", localBundle);
  }
  
  static Intent newIntent(Context paramContext, Intent paramIntent, boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    return new Intent(paramContext.getApplicationContext(), PushClickIntentService.class).putExtra("messageIntent", appendMessageIntentBundle(paramIntent, paramBoolean, paramInteger1, paramInteger2, paramInteger3));
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    if (!Upsight.isEnabled(getApplicationContext())) {}
    do
    {
      do
      {
        return;
        localObject1 = Upsight.createContext(this);
        localObject2 = (UpsightGooglePushServicesExtension)((UpsightContext)localObject1).getUpsightExtension("com.upsight.extension.googlepushservices");
      } while (localObject2 == null);
      ((UpsightGooglePushServicesComponent)((UpsightGooglePushServicesExtension)localObject2).getComponent()).inject(this);
      paramIntent = (Intent)paramIntent.getParcelableExtra("messageIntent");
      Object localObject2 = paramIntent.getBundleExtra("session_extra");
      Object localObject3 = this.mSessionManager;
      if (ApplicationStatus.State.BACKGROUND.name().equals(((ApplicationStatus)((UpsightContext)localObject1).getDataStore().fetchObservable(ApplicationStatus.class).toBlocking().first()).getState().name())) {
        ((SessionManager)localObject3).startSession(PushSessionInitializer.fromExtras((Bundle)localObject2));
      }
      if (((Bundle)localObject2).containsKey("message_id"))
      {
        localObject3 = UpsightCommClickEvent.createBuilder(Integer.valueOf(((Bundle)localObject2).getInt("message_id")));
        if (((Bundle)localObject2).containsKey("campaign_id")) {
          ((UpsightCommClickEvent.Builder)localObject3).setMsgCampaignId(Integer.valueOf(((Bundle)localObject2).getInt("campaign_id")));
        }
        ((UpsightCommClickEvent.Builder)localObject3).record((UpsightContext)localObject1);
      }
      if (((Bundle)localObject2).containsKey("contentId")) {
        localObject3 = new JSONObject();
      }
      try
      {
        ((JSONObject)localObject3).put("name", "upsight");
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("content_id", ((Bundle)localObject2).getInt("contentId"));
        ((JSONObject)localObject3).put("parameters", localJSONObject);
        localObject3 = UpsightContentUnrenderedEvent.createBuilder((JSONObject)localObject3).setScope("com_upsight_push_scope");
        if (((Bundle)localObject2).containsKey("campaign_id")) {
          ((UpsightContentUnrenderedEvent.Builder)localObject3).setCampaignId(Integer.valueOf(((Bundle)localObject2).getInt("campaign_id")));
        }
        ((UpsightContentUnrenderedEvent.Builder)localObject3).record((UpsightContext)localObject1);
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          ((UpsightContext)localObject1).getLogger().e(LOG_TAG, localJSONException, "Could not construct \"content_provider\" bundle in \"upsight.content.unrendered\"", new Object[0]);
        }
      }
      if (((Bundle)localObject2).getBoolean("isDispatchFromForeground", false)) {
        break;
      }
      localObject1 = (ApplicationStatus)((UpsightContext)localObject1).getDataStore().fetchObservable(ApplicationStatus.class).toBlocking().first();
    } while ((localObject1 != null) && (!ApplicationStatus.State.BACKGROUND.equals(((ApplicationStatus)localObject1).getState())));
    startActivity(paramIntent);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/PushClickIntentService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */