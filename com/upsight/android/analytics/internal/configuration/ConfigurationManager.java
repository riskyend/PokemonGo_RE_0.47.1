package com.upsight.android.analytics.internal.configuration;

import android.content.res.Resources;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.R.raw;
import com.upsight.android.analytics.configuration.UpsightConfiguration;
import com.upsight.android.analytics.dispatcher.EndpointResponse;
import com.upsight.android.analytics.event.config.UpsightConfigExpiredEvent;
import com.upsight.android.analytics.event.config.UpsightConfigExpiredEvent.Builder;
import com.upsight.android.analytics.internal.DispatcherService.DestroyEvent;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightDataStoreListener;
import com.upsight.android.persistence.UpsightSubscription;
import com.upsight.android.persistence.annotation.Created;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;

public final class ConfigurationManager
{
  public static final String CONFIGURATION_RESPONSE_SUBTYPE = "upsight.configuration";
  public static final String CONFIGURATION_SUBTYPE = "upsight.configuration.configurationManager";
  private static final String LOG_TAG = "Configuration";
  private static final int RETRY_BASE = -1;
  private final Bus mBus;
  private int mConfigExpiryRetryCount = -1;
  private final ManagerConfigParser mConfigParser;
  private Config mCurrentConfig;
  private final UpsightDataStore mDataStore;
  private UpsightSubscription mDataStoreSubscription;
  private boolean mIsLaunched = false;
  private final UpsightLogger mLogger;
  private final ConfigurationResponseParser mResponseParser;
  private Action0 mSyncAction = new Action0()
  {
    public void call()
    {
      int i = 0;
      ConfigurationManager.this.mLogger.d("Configuration", "Record config.expired", new Object[0]);
      UpsightConfigExpiredEvent.createBuilder().recordSessionless(ConfigurationManager.this.mUpsight);
      ConfigurationManager.Config localConfig = ConfigurationManager.this.mCurrentConfig;
      ConfigurationManager localConfigurationManager;
      if (localConfig != null)
      {
        ConfigurationManager.access$308(ConfigurationManager.this);
        localConfigurationManager = ConfigurationManager.this;
        if (ConfigurationManager.this.mConfigExpiryRetryCount <= localConfig.retryPowerExponentMax) {
          break label120;
        }
      }
      for (;;)
      {
        ConfigurationManager.access$302(localConfigurationManager, i);
        long l = (localConfig.retryMultiplierMs * Math.pow(localConfig.retryPowerBase, ConfigurationManager.this.mConfigExpiryRetryCount));
        ConfigurationManager.this.scheduleConfigExpiry(l);
        return;
        label120:
        i = ConfigurationManager.this.mConfigExpiryRetryCount;
      }
    }
  };
  private final UpsightContext mUpsight;
  private final Scheduler.Worker mWorker;
  private Subscription mWorkerSubscription;
  
  ConfigurationManager(UpsightContext paramUpsightContext, UpsightDataStore paramUpsightDataStore, ConfigurationResponseParser paramConfigurationResponseParser, ManagerConfigParser paramManagerConfigParser, Scheduler paramScheduler, Bus paramBus, UpsightLogger paramUpsightLogger)
  {
    this.mUpsight = paramUpsightContext;
    this.mDataStore = paramUpsightDataStore;
    this.mResponseParser = paramConfigurationResponseParser;
    this.mConfigParser = paramManagerConfigParser;
    this.mBus = paramBus;
    this.mLogger = paramUpsightLogger;
    this.mWorker = paramScheduler.createWorker();
  }
  
  private boolean applyConfiguration(String paramString)
  {
    bool = true;
    for (;;)
    {
      try
      {
        paramString = this.mConfigParser.parse(paramString);
        if ((paramString == null) || (!paramString.isValid())) {
          continue;
        }
        this.mCurrentConfig = paramString;
        this.mLogger.d("Configuration", "Configurations applied", new Object[0]);
        if (this.mWorkerSubscription != null) {
          continue;
        }
        scheduleConfigExpiry(0L);
      }
      catch (IOException paramString)
      {
        this.mLogger.e("Configuration", "Could not parse configurations", new Object[] { paramString });
        bool = false;
        continue;
        this.mLogger.w("Configuration", "Skipped invalid configurations", new Object[0]);
        bool = false;
        continue;
      }
      finally {}
      return bool;
      this.mConfigExpiryRetryCount = -1;
      scheduleConfigExpiry(paramString.requestIntervalMs);
    }
  }
  
  private void applyDefaultConfiguration()
  {
    try
    {
      String str = IOUtils.toString(this.mUpsight.getResources().openRawResource(R.raw.configurator_config));
      this.mLogger.d("Configuration", "Apply default configurations", new Object[0]);
      applyConfiguration(str);
      return;
    }
    catch (IOException localIOException)
    {
      this.mLogger.e("Configuration", "Could not read default config", new Object[] { localIOException });
    }
  }
  
  private void fetchCurrentConfig()
  {
    this.mDataStore.fetch(UpsightConfiguration.class, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException)
      {
        ConfigurationManager.this.mLogger.e("Configuration", "Could not fetch existing configurations from datastore", new Object[] { paramAnonymousUpsightException });
        if (ConfigurationManager.this.mCurrentConfig == null) {
          ConfigurationManager.this.applyDefaultConfiguration();
        }
      }
      
      public void onSuccess(Set<UpsightConfiguration> paramAnonymousSet)
      {
        if (ConfigurationManager.this.mCurrentConfig != null) {}
        boolean bool;
        do
        {
          return;
          bool = false;
          paramAnonymousSet = paramAnonymousSet.iterator();
          while (paramAnonymousSet.hasNext())
          {
            UpsightConfiguration localUpsightConfiguration = (UpsightConfiguration)paramAnonymousSet.next();
            if ("upsight.configuration.configurationManager".equals(localUpsightConfiguration.getScope()))
            {
              ConfigurationManager.this.mLogger.d("Configuration", "Apply local configurations", new Object[0]);
              bool = ConfigurationManager.this.applyConfiguration(localUpsightConfiguration.getConfiguration());
            }
          }
        } while (bool);
        ConfigurationManager.this.applyDefaultConfiguration();
      }
    });
  }
  
  private void scheduleConfigExpiry(long paramLong)
  {
    try
    {
      if ((this.mWorkerSubscription != null) && (!this.mWorkerSubscription.isUnsubscribed()))
      {
        this.mLogger.d("Configuration", "Stop config.expired recording scheduler", new Object[0]);
        this.mWorkerSubscription.unsubscribe();
      }
      this.mLogger.d("Configuration", "Schedule recording of config.expired in " + paramLong + " ms", new Object[0]);
      this.mWorkerSubscription = this.mWorker.schedule(this.mSyncAction, paramLong, TimeUnit.MILLISECONDS);
      return;
    }
    finally {}
  }
  
  @Subscribe
  public void handle(DispatcherService.DestroyEvent paramDestroyEvent)
  {
    terminate();
  }
  
  /* Error */
  public void launch()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 58	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mIsLaunched	Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: iconst_1
    //   16: putfield 58	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mIsLaunched	Z
    //   19: aload_0
    //   20: aconst_null
    //   21: putfield 94	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mCurrentConfig	Lcom/upsight/android/analytics/internal/configuration/ConfigurationManager$Config;
    //   24: aload_0
    //   25: aload_0
    //   26: getfield 69	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mDataStore	Lcom/upsight/android/persistence/UpsightDataStore;
    //   29: aload_0
    //   30: invokeinterface 241 2 0
    //   35: putfield 243	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mDataStoreSubscription	Lcom/upsight/android/persistence/UpsightSubscription;
    //   38: aload_0
    //   39: aconst_null
    //   40: putfield 139	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mWorkerSubscription	Lrx/Subscription;
    //   43: aload_0
    //   44: iconst_m1
    //   45: putfield 60	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mConfigExpiryRetryCount	I
    //   48: aload_0
    //   49: getfield 75	com/upsight/android/analytics/internal/configuration/ConfigurationManager:mBus	Lcom/squareup/otto/Bus;
    //   52: aload_0
    //   53: invokevirtual 249	com/squareup/otto/Bus:register	(Ljava/lang/Object;)V
    //   56: aload_0
    //   57: invokespecial 251	com/upsight/android/analytics/internal/configuration/ConfigurationManager:fetchCurrentConfig	()V
    //   60: goto -49 -> 11
    //   63: astore_2
    //   64: aload_0
    //   65: monitorexit
    //   66: aload_2
    //   67: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	68	0	this	ConfigurationManager
    //   6	2	1	bool	boolean
    //   63	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	63	finally
    //   14	60	63	finally
  }
  
  @Created
  public void onEndpointResponse(final EndpointResponse paramEndpointResponse)
  {
    if (!"upsight.configuration".equals(paramEndpointResponse.getType())) {
      return;
    }
    try
    {
      paramEndpointResponse = this.mResponseParser.parse(paramEndpointResponse.getContent());
      this.mDataStore.fetch(UpsightConfiguration.class, new UpsightDataStoreListener()
      {
        public void onFailure(UpsightException paramAnonymousUpsightException) {}
        
        public void onSuccess(Set<UpsightConfiguration> paramAnonymousSet)
        {
          paramAnonymousSet = paramAnonymousSet.iterator();
          UpsightConfiguration localUpsightConfiguration;
          while (paramAnonymousSet.hasNext())
          {
            localUpsightConfiguration = (UpsightConfiguration)paramAnonymousSet.next();
            ConfigurationManager.this.mDataStore.remove(localUpsightConfiguration);
          }
          paramAnonymousSet = paramEndpointResponse.iterator();
          while (paramAnonymousSet.hasNext())
          {
            localUpsightConfiguration = (UpsightConfiguration)paramAnonymousSet.next();
            if ("upsight.configuration.configurationManager".equals(localUpsightConfiguration.getScope()))
            {
              ConfigurationManager.this.mLogger.d("Configuration", "Try to apply newly arrived configurations", new Object[0]);
              if (ConfigurationManager.this.applyConfiguration(localUpsightConfiguration.getConfiguration())) {
                ConfigurationManager.this.mDataStore.store(localUpsightConfiguration);
              }
            }
            else
            {
              ConfigurationManager.this.mDataStore.store(localUpsightConfiguration);
            }
          }
        }
      });
      return;
    }
    catch (IOException paramEndpointResponse)
    {
      this.mLogger.e("Configuration", "Could not parse newly arrived configurations", new Object[] { paramEndpointResponse });
    }
  }
  
  public void terminate()
  {
    try
    {
      this.mBus.unregister(this);
      if (this.mDataStoreSubscription != null)
      {
        this.mDataStoreSubscription.unsubscribe();
        this.mDataStoreSubscription = null;
      }
      if (this.mWorkerSubscription != null)
      {
        this.mLogger.d("Configuration", "Stop config.expired recording scheduler", new Object[0]);
        this.mWorkerSubscription.unsubscribe();
        this.mWorkerSubscription = null;
      }
      this.mConfigExpiryRetryCount = -1;
      this.mCurrentConfig = null;
      this.mIsLaunched = false;
      return;
    }
    finally {}
  }
  
  public static final class Config
  {
    public final long requestIntervalMs;
    public final long retryMultiplierMs;
    public final double retryPowerBase;
    public final int retryPowerExponentMax;
    
    Config(long paramLong1, long paramLong2, double paramDouble, int paramInt)
    {
      this.requestIntervalMs = paramLong1;
      this.retryMultiplierMs = paramLong2;
      this.retryPowerBase = paramDouble;
      this.retryPowerExponentMax = paramInt;
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
      } while ((((Config)paramObject).requestIntervalMs == this.requestIntervalMs) && (((Config)paramObject).retryMultiplierMs == this.retryMultiplierMs) && (((Config)paramObject).retryPowerBase == this.retryPowerBase) && (((Config)paramObject).retryPowerExponentMax == this.retryPowerExponentMax));
      return false;
    }
    
    public boolean isValid()
    {
      return (this.requestIntervalMs > 0L) && (this.retryMultiplierMs > 0L) && (this.retryPowerBase > 0.0D) && (this.retryPowerExponentMax > 0);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/configuration/ConfigurationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */