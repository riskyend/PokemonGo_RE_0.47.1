package com.upsight.android.googlepushservices.internal;

import android.content.res.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.configuration.UpsightConfiguration;
import com.upsight.android.googlepushservices.R.raw;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import rx.Observable;
import rx.functions.Func1;

public class PushConfigManager
{
  private static final String LOG_TAG = PushConfigManager.class.getSimpleName();
  public static final String PUSH_CONFIGURATION_SUBTYPE = "upsight.configuration.push";
  private UpsightDataStore mDataStore;
  private Gson mGson;
  private UpsightLogger mLogger;
  private UpsightContext mUpsight;
  
  PushConfigManager(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
    this.mDataStore = this.mUpsight.getDataStore();
    this.mLogger = this.mUpsight.getLogger();
    this.mGson = paramUpsightContext.getCoreComponent().gson();
  }
  
  private Config parseConfiguration(String paramString)
  {
    try
    {
      paramString = (Config)this.mGson.fromJson(paramString, Config.class);
      return paramString;
    }
    catch (JsonSyntaxException paramString)
    {
      this.mLogger.e(LOG_TAG, "Could not parse incoming config", new Object[] { paramString });
    }
    return null;
  }
  
  public Observable<Config> fetchCurrentConfigObservable()
    throws IOException
  {
    Config localConfig = parseConfiguration(IOUtils.toString(this.mUpsight.getResources().openRawResource(R.raw.push_config)));
    this.mDataStore.fetchObservable(UpsightConfiguration.class).filter(new Func1()
    {
      public Boolean call(UpsightConfiguration paramAnonymousUpsightConfiguration)
      {
        return Boolean.valueOf("upsight.configuration.push".equals(paramAnonymousUpsightConfiguration.getScope()));
      }
    }).map(new Func1()
    {
      public PushConfigManager.Config call(UpsightConfiguration paramAnonymousUpsightConfiguration)
      {
        return PushConfigManager.this.parseConfiguration(paramAnonymousUpsightConfiguration.getConfiguration());
      }
    }).filter(new Func1()
    {
      public Boolean call(PushConfigManager.Config paramAnonymousConfig)
      {
        if ((paramAnonymousConfig != null) && (PushConfigManager.Config.access$000(paramAnonymousConfig))) {}
        for (boolean bool = true;; bool = false) {
          return Boolean.valueOf(bool);
        }
      }
    }).firstOrDefault(localConfig);
  }
  
  public static final class Config
  {
    @Expose
    @SerializedName("auto_register")
    public boolean autoRegister;
    @Expose
    @SerializedName("push_token_ttl")
    public long pushTokenTtl;
    
    private boolean isValid()
    {
      return this.pushTokenTtl >= 0L;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      do
      {
        return true;
        if ((paramObject == null) || (getClass() != paramObject.getClass())) {
          return false;
        }
        paramObject = (Config)paramObject;
      } while ((((Config)paramObject).pushTokenTtl == this.pushTokenTtl) && (((Config)paramObject).autoRegister == this.autoRegister));
      return false;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/PushConfigManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */