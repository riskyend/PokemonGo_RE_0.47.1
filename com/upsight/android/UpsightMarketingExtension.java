package com.upsight.android;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.upsight.android.analytics.dispatcher.EndpointResponse;
import com.upsight.android.analytics.internal.action.ActionMapResponse;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.UpsightMarketingApi;
import com.upsight.android.marketing.UpsightMarketingComponent;
import com.upsight.android.marketing.internal.BaseMarketingModule;
import com.upsight.android.marketing.internal.DaggerMarketingComponent;
import com.upsight.android.marketing.internal.DaggerMarketingComponent.Builder;
import com.upsight.android.marketing.internal.content.DefaultContentMediator;
import com.upsight.android.marketing.internal.content.MarketingContent;
import com.upsight.android.marketing.internal.content.MarketingContentFactory;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.annotation.Created;
import javax.inject.Inject;

public class UpsightMarketingExtension
  extends UpsightExtension<UpsightMarketingComponent, UpsightMarketingApi>
{
  public static final String EXTENSION_NAME = "com.upsight.extension.marketing";
  private static final String UPSIGHT_ACTION_MAP = "upsight.action_map";
  @Inject
  DefaultContentMediator mDefaultContentMediator;
  private Gson mGson;
  private JsonParser mJsonParser;
  private UpsightLogger mLogger;
  @Inject
  UpsightMarketingApi mMarketing;
  @Inject
  MarketingContentFactory mMarketingContentFactory;
  
  public UpsightMarketingApi getApi()
  {
    return this.mMarketing;
  }
  
  protected void onCreate(UpsightContext paramUpsightContext)
  {
    this.mGson = paramUpsightContext.getCoreComponent().gson();
    this.mJsonParser = paramUpsightContext.getCoreComponent().jsonParser();
    this.mLogger = paramUpsightContext.getLogger();
    UpsightContentMediator.register(paramUpsightContext, this.mDefaultContentMediator);
    paramUpsightContext.getDataStore().subscribe(this);
  }
  
  protected UpsightMarketingComponent onResolve(UpsightContext paramUpsightContext)
  {
    return DaggerMarketingComponent.builder().baseMarketingModule(new BaseMarketingModule(paramUpsightContext)).build();
  }
  
  @Created
  public void onResponse(EndpointResponse paramEndpointResponse)
  {
    if (!"upsight.action_map".equals(paramEndpointResponse.getType())) {}
    for (;;)
    {
      return;
      try
      {
        paramEndpointResponse = this.mJsonParser.parse(paramEndpointResponse.getContent());
        paramEndpointResponse = (ActionMapResponse)this.mGson.fromJson(paramEndpointResponse, ActionMapResponse.class);
        if ("marketing_content_factory".equals(paramEndpointResponse.getActionFactory()))
        {
          paramEndpointResponse = this.mMarketingContentFactory.create(paramEndpointResponse);
          if (paramEndpointResponse != null)
          {
            paramEndpointResponse.executeActions("content_received");
            return;
          }
        }
      }
      catch (JsonSyntaxException paramEndpointResponse)
      {
        this.mLogger.w("Upsight", "Unable to parse action map", new Object[] { paramEndpointResponse });
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightMarketingExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */