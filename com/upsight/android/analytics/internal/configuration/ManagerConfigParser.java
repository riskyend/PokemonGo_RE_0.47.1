package com.upsight.android.analytics.internal.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ManagerConfigParser
{
  private Gson mGson;
  
  @Inject
  ManagerConfigParser(@Named("config-gson") Gson paramGson)
  {
    this.mGson = paramGson;
  }
  
  public ConfigurationManager.Config parse(String paramString)
    throws IOException
  {
    try
    {
      paramString = (ConfigJson)this.mGson.fromJson(paramString, ConfigJson.class);
      return new ConfigurationManager.Config(TimeUnit.SECONDS.toMillis(paramString.requestInterval), TimeUnit.SECONDS.toMillis(paramString.retryMultiplier), paramString.retryPowerBase, paramString.retryPowerExponentMax);
    }
    catch (JsonSyntaxException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public static class ConfigJson
  {
    @Expose
    @SerializedName("requestInterval")
    public long requestInterval;
    @Expose
    @SerializedName("retryMultiplier")
    public long retryMultiplier;
    @Expose
    @SerializedName("retryPowerBase")
    public double retryPowerBase;
    @Expose
    @SerializedName("retryPowerExponentMax")
    public int retryPowerExponentMax;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/configuration/ManagerConfigParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */