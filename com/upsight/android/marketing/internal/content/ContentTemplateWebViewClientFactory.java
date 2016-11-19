package com.upsight.android.marketing.internal.content;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.squareup.otto.Bus;
import com.upsight.android.logger.UpsightLogger;

public class ContentTemplateWebViewClientFactory
{
  protected final Bus mBus;
  protected final Gson mGson;
  protected final JsonParser mJsonParser;
  protected final UpsightLogger mLogger;
  
  public ContentTemplateWebViewClientFactory(Bus paramBus, Gson paramGson, JsonParser paramJsonParser, UpsightLogger paramUpsightLogger)
  {
    this.mBus = paramBus;
    this.mGson = paramGson;
    this.mJsonParser = paramJsonParser;
    this.mLogger = paramUpsightLogger;
  }
  
  public ContentTemplateWebViewClient create(MarketingContent paramMarketingContent)
  {
    return new ContentTemplateWebViewClient(paramMarketingContent, this.mBus, this.mGson, this.mJsonParser, this.mLogger);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentTemplateWebViewClientFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */