package com.upsight.android.marketing.internal.billboard;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightMarketingComponent;
import com.upsight.android.marketing.internal.content.MarketingContent;
import com.upsight.android.marketing.internal.content.MarketingContent.PendingDialog;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import java.util.Iterator;
import javax.inject.Inject;

public final class BillboardDialogFragment
  extends DialogFragment
{
  private static final String FRAGMENT_BUNDLE_KEY_BUTTONS = "buttons";
  private static final String FRAGMENT_BUNDLE_KEY_DIALOG_THEME = "dialogTheme";
  private static final String FRAGMENT_BUNDLE_KEY_DISMISS_TRIGGER = "dismissTrigger";
  private static final String FRAGMENT_BUNDLE_KEY_ID = "id";
  private static final String FRAGMENT_BUNDLE_KEY_MESSAGE = "message";
  private static final String FRAGMENT_BUNDLE_KEY_TITLE = "title";
  private static final String LOG_TAG = BillboardDialogFragment.class.getSimpleName();
  @Inject
  MarketingContentStore mContentStore;
  @Inject
  UpsightContext mUpsight;
  
  private void executeActions(String paramString1, String paramString2)
  {
    paramString1 = (MarketingContent)this.mContentStore.get(paramString1);
    if (paramString1 != null) {
      paramString1.executeActions(paramString2);
    }
  }
  
  public static DialogFragment newInstance(MarketingContent.PendingDialog paramPendingDialog)
  {
    BillboardDialogFragment localBillboardDialogFragment = new BillboardDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("id", paramPendingDialog.mId);
    localBundle.putString("title", paramPendingDialog.mTitle);
    localBundle.putString("message", paramPendingDialog.mMessage);
    localBundle.putString("buttons", paramPendingDialog.mButtons);
    localBundle.putString("dismissTrigger", paramPendingDialog.mDismissTrigger);
    localBillboardDialogFragment.setArguments(localBundle);
    return localBillboardDialogFragment;
  }
  
  public static DialogFragment newInstance(MarketingContent.PendingDialog paramPendingDialog, int paramInt)
  {
    BillboardDialogFragment localBillboardDialogFragment = new BillboardDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("id", paramPendingDialog.mId);
    localBundle.putString("title", paramPendingDialog.mTitle);
    localBundle.putString("message", paramPendingDialog.mMessage);
    localBundle.putString("buttons", paramPendingDialog.mButtons);
    localBundle.putString("dismissTrigger", paramPendingDialog.mDismissTrigger);
    localBundle.putInt("dialogTheme", paramInt);
    localBillboardDialogFragment.setArguments(localBundle);
    return localBillboardDialogFragment;
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    paramDialogInterface = getArguments();
    executeActions(paramDialogInterface.getString("id"), paramDialogInterface.getString("dismissTrigger"));
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = (UpsightMarketingExtension)Upsight.createContext(getActivity()).getUpsightExtension("com.upsight.extension.marketing");
    if (paramBundle == null) {
      return null;
    }
    ((UpsightMarketingComponent)paramBundle.getComponent()).inject(this);
    paramBundle = getArguments();
    final String str1 = paramBundle.getString("id");
    localObject1 = paramBundle.getString("title");
    localObject2 = paramBundle.getString("message");
    String str2 = paramBundle.getString("buttons");
    int i;
    if (paramBundle.containsKey("dialogTheme"))
    {
      i = paramBundle.getInt("dialogTheme");
      paramBundle = new AlertDialog.Builder(getActivity(), i);
      paramBundle.setTitle((CharSequence)localObject1).setMessage((CharSequence)localObject2);
      if (!TextUtils.isEmpty(str2)) {
        this.mUpsight.getCoreComponent().gson();
      }
    }
    for (;;)
    {
      try
      {
        localObject1 = this.mUpsight.getCoreComponent().jsonParser().parse(str2);
        if (!((JsonElement)localObject1).isJsonArray()) {
          continue;
        }
        i = ((JsonElement)localObject1).getAsJsonArray().size();
        localObject1 = ((JsonElement)localObject1).getAsJsonArray().iterator();
        switch (i)
        {
        }
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        this.mUpsight.getLogger().e(LOG_TAG, "Failed to parse button due to malformed JSON", new Object[] { localJsonSyntaxException });
        continue;
        if (!((Iterator)localObject1).hasNext()) {
          continue;
        }
        localObject2 = (JsonElement)((Iterator)localObject1).next();
        paramBundle.setNegativeButton(((JsonElement)localObject2).getAsJsonObject().get("text").getAsString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            BillboardDialogFragment.this.executeActions(localJsonSyntaxException, this.val$buttonTrigger);
          }
        });
        if (!((Iterator)localObject1).hasNext()) {
          continue;
        }
        localObject1 = (JsonElement)((Iterator)localObject1).next();
        paramBundle.setPositiveButton(((JsonElement)localObject1).getAsJsonObject().get("text").getAsString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            BillboardDialogFragment.this.executeActions(localJsonSyntaxException, this.val$buttonTrigger);
          }
        });
        continue;
        if (!((Iterator)localObject1).hasNext()) {
          continue;
        }
        localObject2 = (JsonElement)((Iterator)localObject1).next();
        paramBundle.setNegativeButton(((JsonElement)localObject2).getAsJsonObject().get("text").getAsString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            BillboardDialogFragment.this.executeActions(localJsonSyntaxException, this.val$buttonTrigger);
          }
        });
        if (!((Iterator)localObject1).hasNext()) {
          continue;
        }
        localObject2 = (JsonElement)((Iterator)localObject1).next();
        paramBundle.setNeutralButton(((JsonElement)localObject2).getAsJsonObject().get("text").getAsString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            BillboardDialogFragment.this.executeActions(localJsonSyntaxException, this.val$buttonTrigger);
          }
        });
        if (!((Iterator)localObject1).hasNext()) {
          continue;
        }
        localObject1 = (JsonElement)((Iterator)localObject1).next();
        paramBundle.setPositiveButton(((JsonElement)localObject1).getAsJsonObject().get("text").getAsString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            BillboardDialogFragment.this.executeActions(localJsonSyntaxException, this.val$buttonTrigger);
          }
        });
        continue;
        this.mUpsight.getLogger().e(LOG_TAG, "Failed to parse buttons because expected buttons array is missing", new Object[0]);
        continue;
      }
      return paramBundle.create();
      paramBundle = new AlertDialog.Builder(getActivity());
      break;
      if (((Iterator)localObject1).hasNext())
      {
        localObject1 = (JsonElement)((Iterator)localObject1).next();
        paramBundle.setNeutralButton(((JsonElement)localObject1).getAsJsonObject().get("text").getAsString(), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            BillboardDialogFragment.this.executeActions(str1, this.val$buttonTrigger);
          }
        });
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */