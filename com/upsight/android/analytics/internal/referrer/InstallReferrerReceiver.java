package com.upsight.android.analytics.internal.referrer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.text.TextUtils;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.event.install.UpsightInstallAttributionEvent;
import com.upsight.android.analytics.event.install.UpsightInstallAttributionEvent.Builder;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class InstallReferrerReceiver
  extends BroadcastReceiver
{
  private static final String ACTION_INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";
  private static final String CHARSET_UTF8 = "UTF-8";
  private static final String EXTRA_REFERRER = "referrer";
  public static final String REFERRER_PARAM_KEY_CAMPAIGN = "utm_campaign";
  public static final String REFERRER_PARAM_KEY_CONTENT = "utm_content";
  public static final String REFERRER_PARAM_KEY_MEDIUM = "utm_medium";
  public static final String REFERRER_PARAM_KEY_SOURCE = "utm_source";
  public static final String REFERRER_PARAM_KEY_TERM = "utm_term";
  public static final String SHARED_PREFERENCES_KEY_REFERRER = "referrer";
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (!Upsight.isEnabled(paramContext)) {}
    for (;;)
    {
      return;
      UpsightContext localUpsightContext = Upsight.createContext(paramContext);
      if ((!"com.android.vending.INSTALL_REFERRER".equals(paramIntent.getAction())) || (PreferencesHelper.contains(localUpsightContext, "referrer"))) {
        continue;
      }
      paramContext = paramIntent.getStringExtra("referrer");
      try
      {
        Object localObject = parseReferrerParams(paramContext);
        PreferencesHelper.putString(localUpsightContext, "referrer", ((JSONObject)localObject).toString());
        paramContext = ((JSONObject)localObject).optString("utm_source");
        paramIntent = ((JSONObject)localObject).optString("utm_campaign");
        localObject = ((JSONObject)localObject).optString("utm_content");
        if ((TextUtils.isEmpty(paramContext)) && (TextUtils.isEmpty(paramIntent)) && (TextUtils.isEmpty((CharSequence)localObject))) {
          continue;
        }
        UpsightInstallAttributionEvent.createBuilder().setAttributionSource(paramContext).setAttributionCampaign(paramIntent).setAttributionCreative((String)localObject).record(localUpsightContext);
        return;
      }
      catch (UnsupportedEncodingException paramContext)
      {
        localUpsightContext.getLogger().e("Upsight", "Failed to parse referrer parameters from com.android.vending.INSTALL_REFERRER", new Object[] { paramContext });
        return;
      }
      catch (JSONException paramContext)
      {
        for (;;) {}
      }
    }
  }
  
  JSONObject parseReferrerParams(String paramString)
    throws UnsupportedEncodingException, JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    if (!TextUtils.isEmpty(paramString))
    {
      UrlQuerySanitizer localUrlQuerySanitizer = new UrlQuerySanitizer();
      localUrlQuerySanitizer.setAllowUnregisteredParamaters(true);
      localUrlQuerySanitizer.parseQuery(URLDecoder.decode(paramString, "UTF-8").trim());
      paramString = localUrlQuerySanitizer.getParameterSet().iterator();
      while (paramString.hasNext())
      {
        String str = (String)paramString.next();
        localJSONObject.put(str, localUrlQuerySanitizer.getValue(str));
      }
    }
    return localJSONObject;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/referrer/InstallReferrerReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */