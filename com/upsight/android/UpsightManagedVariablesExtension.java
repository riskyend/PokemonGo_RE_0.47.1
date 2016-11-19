package com.upsight.android;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.upsight.android.analytics.dispatcher.EndpointResponse;
import com.upsight.android.analytics.internal.action.ActionMapResponse;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.managedvariables.UpsightManagedVariablesApi;
import com.upsight.android.managedvariables.UpsightManagedVariablesComponent;
import com.upsight.android.managedvariables.internal.BaseManagedVariablesModule;
import com.upsight.android.managedvariables.internal.DaggerManagedVariablesComponent;
import com.upsight.android.managedvariables.internal.DaggerManagedVariablesComponent.Builder;
import com.upsight.android.managedvariables.internal.type.UxmBlockProvider;
import com.upsight.android.managedvariables.internal.type.UxmContent;
import com.upsight.android.managedvariables.internal.type.UxmContentFactory;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.annotation.Created;
import javax.inject.Inject;

public class UpsightManagedVariablesExtension
  extends UpsightExtension<UpsightManagedVariablesComponent, UpsightManagedVariablesApi>
{
  public static final String EXTENSION_NAME = "com.upsight.extension.managedvariables";
  private static final String UPSIGHT_ACTION_MAP = "upsight.action_map";
  private Gson mGson;
  private JsonParser mJsonParser;
  private UpsightLogger mLogger;
  @Inject
  UpsightManagedVariablesApi mManagedVariables;
  @Inject
  UxmBlockProvider mUxmBlockProvider;
  @Inject
  UxmContentFactory mUxmContentFactory;
  
  public UpsightManagedVariablesApi getApi()
  {
    return this.mManagedVariables;
  }
  
  protected void onCreate(UpsightContext paramUpsightContext)
  {
    this.mGson = paramUpsightContext.getCoreComponent().gson();
    this.mJsonParser = paramUpsightContext.getCoreComponent().jsonParser();
    this.mLogger = paramUpsightContext.getLogger();
    UpsightDataProvider.register(paramUpsightContext, this.mUxmBlockProvider);
    paramUpsightContext.getDataStore().subscribe(this);
  }
  
  protected UpsightManagedVariablesComponent onResolve(UpsightContext paramUpsightContext)
  {
    return DaggerManagedVariablesComponent.builder().baseManagedVariablesModule(new BaseManagedVariablesModule(paramUpsightContext)).build();
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
        if ("datastore_factory".equals(paramEndpointResponse.getActionFactory()))
        {
          paramEndpointResponse = this.mUxmContentFactory.create(paramEndpointResponse);
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightManagedVariablesExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */