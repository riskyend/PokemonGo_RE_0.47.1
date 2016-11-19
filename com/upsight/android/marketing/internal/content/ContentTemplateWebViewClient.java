package com.upsight.android.marketing.internal.content;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.squareup.otto.Bus;
import com.upsight.android.logger.UpsightLogger;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

class ContentTemplateWebViewClient
  extends WebViewClient
{
  private static final String DISPATCH_CALLBACK = "javascript:PlayHaven.nativeAPI.callback(\"%s\", %s, %s, %s)";
  private static final String DISPATCH_CALLBACK_PROTOCOL = "javascript:window.PlayHavenDispatchProtocolVersion=7";
  private static final String DISPATCH_LOAD_CONTEXT = "ph://loadContext";
  private static final String DISPATCH_PARAM_KEY_CALLBACK_ID = "callback";
  private static final String DISPATCH_PARAM_KEY_CONTEXT = "context";
  private static final String DISPATCH_PARAM_KEY_TRIGGER = "trigger";
  private static final String DISPATCH_PARAM_KEY_VIEW_DATA = "view_data";
  private static final String DISPATCH_SCHEME = "ph://";
  private final Bus mBus;
  private final Gson mGson;
  private boolean mIsTemplateLoaded = false;
  private final JsonParser mJsonParser;
  private final UpsightLogger mLogger;
  private final MarketingContent<MarketingContentModel> mMarketingContent;
  
  public ContentTemplateWebViewClient(MarketingContent<MarketingContentModel> paramMarketingContent, Bus paramBus, Gson paramGson, JsonParser paramJsonParser, UpsightLogger paramUpsightLogger)
  {
    this.mMarketingContent = paramMarketingContent;
    this.mBus = paramBus;
    this.mGson = paramGson;
    this.mJsonParser = paramJsonParser;
    this.mLogger = paramUpsightLogger;
  }
  
  private boolean handleActionDispatch(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.startsWith("ph://"))
      {
        bool2 = true;
        String str = Uri.parse(paramString).getQueryParameter("context");
        bool1 = bool2;
        if (!TextUtils.isEmpty(str))
        {
          for (;;)
          {
            try
            {
              paramString = this.mJsonParser.parse(str);
              if (!paramString.isJsonObject()) {
                break;
              }
              Object localObject1 = paramString.getAsJsonObject();
              paramString = ((JsonObject)localObject1).get("trigger");
              localObject1 = ((JsonObject)localObject1).get("view_data");
              if ((paramString != null) && (paramString.isJsonPrimitive()) && (paramString.getAsJsonPrimitive().isString()))
              {
                this.mMarketingContent.executeActions(paramString.getAsString());
                return true;
              }
              bool1 = bool2;
              if (localObject1 == null) {
                return bool1;
              }
              bool1 = bool2;
              if (!((JsonElement)localObject1).isJsonObject()) {
                return bool1;
              }
              localObject1 = ((JsonElement)localObject1).getAsJsonObject().entrySet().iterator();
              bool1 = bool2;
              if (!((Iterator)localObject1).hasNext()) {
                return bool1;
              }
              Object localObject2 = (Map.Entry)((Iterator)localObject1).next();
              paramString = (JsonElement)((Map.Entry)localObject2).getValue();
              MarketingContent localMarketingContent = this.mMarketingContent;
              localObject2 = (String)((Map.Entry)localObject2).getKey();
              if (paramString != null)
              {
                paramString = paramString.toString();
                localMarketingContent.putExtra((String)localObject2, paramString);
              }
              else
              {
                paramString = null;
              }
            }
            catch (JsonSyntaxException paramString)
            {
              this.mLogger.e(getClass().getSimpleName(), paramString, "Failed to parse context into JsonElement context=" + str, new Object[0]);
              return true;
            }
          }
          this.mLogger.e(getClass().getSimpleName(), "Failed to parse context into JsonObject context=" + str, new Object[0]);
          bool1 = bool2;
        }
      }
    }
    return bool1;
  }
  
  @TargetApi(19)
  private boolean handleLoadContextDispatch(WebView paramWebView, String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.startsWith("ph://loadContext"))
      {
        bool1 = true;
        paramString = String.format("javascript:PlayHaven.nativeAPI.callback(\"%s\", %s, %s, %s)", new Object[] { Uri.parse(paramString).getQueryParameter("callback"), ((MarketingContentModel)this.mMarketingContent.getContentModel()).getContext(), null, null });
        paramWebView.loadUrl("javascript:window.PlayHavenDispatchProtocolVersion=7");
        if (Build.VERSION.SDK_INT < 19) {
          break label92;
        }
        paramWebView.evaluateJavascript(paramString, null);
      }
    }
    return bool1;
    label92:
    paramWebView.loadUrl(paramString);
    return true;
  }
  
  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    if (!this.mIsTemplateLoaded)
    {
      this.mIsTemplateLoaded = true;
      this.mMarketingContent.markLoaded(this.mBus);
    }
  }
  
  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    return (handleLoadContextDispatch(paramWebView, paramString)) || (handleActionDispatch(paramString)) || (super.shouldOverrideUrlLoading(paramWebView, paramString));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentTemplateWebViewClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */