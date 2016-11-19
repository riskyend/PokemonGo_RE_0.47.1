package com.upsight.android.analytics.internal.session;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;

class ConfigParser
{
  private Gson mGson;
  
  @Inject
  public ConfigParser(@Named("config-gson") Gson paramGson)
  {
    this.mGson = paramGson;
  }
  
  public SessionManagerImpl.Config parseConfig(String paramString)
    throws IOException
  {
    try
    {
      paramString = (ConfigJson)this.mGson.fromJson(paramString, ConfigJson.class);
      return new SessionManagerImpl.Config(paramString.session_gap);
    }
    catch (JsonSyntaxException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public static class ConfigJson
  {
    @Expose
    @SerializedName("session_gap")
    public int session_gap;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/ConfigParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */