package com.upsight.android.analytics.internal.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.configuration.UpsightConfiguration;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ConfigurationResponseParser
{
  private Gson mGson;
  private SessionManager mSessionManager;
  
  @Inject
  ConfigurationResponseParser(@Named("config-gson") Gson paramGson, SessionManager paramSessionManager)
  {
    this.mGson = paramGson;
    this.mSessionManager = paramSessionManager;
  }
  
  public Collection<UpsightConfiguration> parse(String paramString)
    throws IOException
  {
    try
    {
      Object localObject1 = (ConfigResponseJson)this.mGson.fromJson(paramString, ConfigResponseJson.class);
      paramString = new LinkedList();
      localObject1 = ((ConfigResponseJson)localObject1).configs;
      int j = localObject1.length;
      int i = 0;
      while (i < j)
      {
        Object localObject2 = localObject1[i];
        paramString.add(UpsightConfiguration.create(((ConfigJson)localObject2).type, ((ConfigJson)localObject2).configuration.toString(), this.mSessionManager.getLatestSessionInfo().sessionNumber));
        i += 1;
      }
      return paramString;
    }
    catch (JsonSyntaxException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public static class ConfigJson
  {
    @Expose
    @SerializedName("configuration")
    public JsonElement configuration;
    @Expose
    @SerializedName("type")
    public String type;
  }
  
  public static class ConfigResponseJson
  {
    @Expose
    @SerializedName("configurationList")
    public ConfigurationResponseParser.ConfigJson[] configs;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/configuration/ConfigurationResponseParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */