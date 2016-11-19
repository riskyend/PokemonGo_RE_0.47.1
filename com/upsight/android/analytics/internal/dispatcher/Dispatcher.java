package com.upsight.android.analytics.internal.dispatcher;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.configuration.UpsightConfiguration;
import com.upsight.android.analytics.dispatcher.AnalyticsEventDeliveryStatus;
import com.upsight.android.analytics.dispatcher.EndpointResponse;
import com.upsight.android.analytics.internal.AnalyticsContext;
import com.upsight.android.analytics.internal.DataStoreRecord;
import com.upsight.android.analytics.internal.DataStoreRecord.Action;
import com.upsight.android.analytics.internal.DispatcherService.DestroyEvent;
import com.upsight.android.analytics.internal.dispatcher.routing.Router;
import com.upsight.android.analytics.internal.dispatcher.routing.RouterBuilder;
import com.upsight.android.analytics.internal.dispatcher.routing.RoutingListener;
import com.upsight.android.analytics.internal.dispatcher.schema.SchemaSelectorBuilder;
import com.upsight.android.analytics.internal.dispatcher.util.Selector;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightDataStoreListener;
import com.upsight.android.persistence.UpsightSubscription;
import com.upsight.android.persistence.annotation.Created;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Dispatcher
  implements RoutingListener
{
  public static final String CONFIGURATION_SUBTYPE = "upsight.configuration.dispatcher";
  static final int DISPATCHER_CONFIGURATION_MAX_SESSIONS = 2;
  private static final String LOG_TAG = "Dispatcher";
  private Bus mBus;
  private ConfigParser mConfigParser;
  private AnalyticsContext mContext;
  private Config mCurrentConfig;
  private volatile Router mCurrentRouter;
  private UpsightSubscription mDataStoreSubscription;
  private Collection<Router> mExpiredRouters;
  private boolean mIsLaunched = false;
  private UpsightLogger mLogger;
  private Set<DataStoreRecord> mPendingRecords;
  private RouterBuilder mRouterBuilder;
  private SchemaSelectorBuilder mSchemaSelectorBuilder;
  private SessionManager mSessionManager;
  private Queue<DataStoreRecord> mUnroutedRecords;
  private UpsightDataStore mUpsightDataStore;
  
  Dispatcher(AnalyticsContext paramAnalyticsContext, SessionManager paramSessionManager, UpsightDataStore paramUpsightDataStore, ConfigParser paramConfigParser, RouterBuilder paramRouterBuilder, SchemaSelectorBuilder paramSchemaSelectorBuilder, Bus paramBus, UpsightLogger paramUpsightLogger)
  {
    this.mContext = paramAnalyticsContext;
    this.mSessionManager = paramSessionManager;
    this.mUpsightDataStore = paramUpsightDataStore;
    this.mConfigParser = paramConfigParser;
    this.mRouterBuilder = paramRouterBuilder;
    this.mSchemaSelectorBuilder = paramSchemaSelectorBuilder;
    this.mBus = paramBus;
    this.mLogger = paramUpsightLogger;
  }
  
  private boolean applyConfiguration(String paramString)
  {
    paramString = parseConfiguration(paramString);
    if (paramString == null) {
      return false;
    }
    if (!paramString.isValid())
    {
      this.mLogger.w("Dispatcher", "Incoming configuration is not valid", new Object[0]);
      return false;
    }
    if (paramString.equals(this.mCurrentConfig)) {
      return true;
    }
    this.mCurrentConfig = paramString;
    Object localObject1 = this.mExpiredRouters;
    Object localObject2 = this.mCurrentRouter;
    if ((localObject1 != null) && (localObject2 != null))
    {
      ((Collection)localObject1).add(localObject2);
      ((Router)localObject2).finishRouting();
    }
    localObject1 = this.mSchemaSelectorBuilder.buildSelectorByName(paramString.getIdentifierConfig());
    localObject2 = this.mSchemaSelectorBuilder.buildSelectorByType(paramString.getIdentifierConfig());
    this.mCurrentRouter = this.mRouterBuilder.build(paramString.getRoutingConfig(), (Selector)localObject1, (Selector)localObject2, this);
    paramString = this.mUnroutedRecords;
    if ((paramString != null) && (this.mCurrentRouter != null)) {
      while (!paramString.isEmpty()) {
        routeRecords((DataStoreRecord)paramString.poll());
      }
    }
    fetchCreatedRecords();
    return true;
  }
  
  private void applyDefaultConfiguration()
  {
    applyConfiguration(this.mContext.getDefaultDispatcherConfiguration());
  }
  
  private void fetchCreatedRecords()
  {
    this.mUpsightDataStore.fetch(DataStoreRecord.class, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException)
      {
        Dispatcher.this.mLogger.e("Dispatcher", "Could not fetch records from store.", new Object[] { paramAnonymousUpsightException });
      }
      
      public void onSuccess(Set<DataStoreRecord> paramAnonymousSet)
      {
        paramAnonymousSet = paramAnonymousSet.iterator();
        while (paramAnonymousSet.hasNext())
        {
          DataStoreRecord localDataStoreRecord = (DataStoreRecord)paramAnonymousSet.next();
          Dispatcher.this.routeRecords(localDataStoreRecord);
        }
      }
    });
  }
  
  private void fetchCurrentConfig()
  {
    this.mUpsightDataStore.fetch(UpsightConfiguration.class, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException)
      {
        Dispatcher.this.mLogger.e("Dispatcher", "Could not fetch config from store.", new Object[] { paramAnonymousUpsightException });
        if (Dispatcher.this.mCurrentConfig == null) {
          Dispatcher.this.applyDefaultConfiguration();
        }
      }
      
      public void onSuccess(Set<UpsightConfiguration> paramAnonymousSet)
      {
        if (Dispatcher.this.mCurrentConfig != null) {}
        boolean bool;
        do
        {
          return;
          bool = false;
          paramAnonymousSet = paramAnonymousSet.iterator();
          while (paramAnonymousSet.hasNext())
          {
            UpsightConfiguration localUpsightConfiguration = (UpsightConfiguration)paramAnonymousSet.next();
            if (("upsight.configuration.dispatcher".equals(localUpsightConfiguration.getScope())) && (Dispatcher.this.isUpsightConfigurationValid(localUpsightConfiguration))) {
              bool = Dispatcher.this.applyConfiguration(localUpsightConfiguration.getConfiguration());
            }
          }
        } while (bool);
        Dispatcher.this.applyDefaultConfiguration();
      }
    });
  }
  
  private boolean isUpsightConfigurationValid(UpsightConfiguration paramUpsightConfiguration)
  {
    return this.mSessionManager.getLatestSessionInfo().sessionNumber - paramUpsightConfiguration.getSessionNumberCreated() <= 2;
  }
  
  private Config parseConfiguration(String paramString)
  {
    try
    {
      paramString = this.mConfigParser.parseConfig(paramString);
      return paramString;
    }
    catch (IOException paramString)
    {
      this.mLogger.e("Dispatcher", "Could not apply incoming config", new Object[] { paramString });
    }
    return null;
  }
  
  private void routeRecords(DataStoreRecord paramDataStoreRecord)
  {
    if (!DataStoreRecord.Action.Created.equals(paramDataStoreRecord.getAction())) {
      this.mUpsightDataStore.remove(paramDataStoreRecord);
    }
    Object localObject;
    Set localSet;
    do
    {
      do
      {
        return;
        localObject = this.mCurrentRouter;
        localSet = this.mPendingRecords;
        if (localObject != null) {
          break;
        }
        localObject = this.mUnroutedRecords;
      } while (((Queue)localObject).contains(paramDataStoreRecord));
      ((Queue)localObject).add(paramDataStoreRecord);
      return;
    } while ((localSet == null) || (localSet.contains(paramDataStoreRecord)) || (!((Router)localObject).routeEvent(paramDataStoreRecord)));
    localSet.add(paramDataStoreRecord);
  }
  
  @Subscribe
  public void handle(DispatcherService.DestroyEvent paramDestroyEvent)
  {
    terminate();
  }
  
  public boolean hasPendingRecords()
  {
    Set localSet = this.mPendingRecords;
    return (localSet == null) || (!localSet.isEmpty());
  }
  
  public void launch()
  {
    if (this.mIsLaunched) {
      return;
    }
    this.mIsLaunched = true;
    this.mCurrentRouter = null;
    this.mExpiredRouters = new HashSet();
    this.mUnroutedRecords = new ConcurrentLinkedQueue();
    this.mPendingRecords = Collections.synchronizedSet(new HashSet());
    this.mCurrentConfig = null;
    this.mDataStoreSubscription = this.mUpsightDataStore.subscribe(this);
    this.mBus.register(this);
    fetchCurrentConfig();
  }
  
  @Created
  public void onConfigurationCreated(UpsightConfiguration paramUpsightConfiguration)
  {
    if (("upsight.configuration.dispatcher".equals(paramUpsightConfiguration.getScope())) && (isUpsightConfigurationValid(paramUpsightConfiguration))) {
      applyConfiguration(paramUpsightConfiguration.getConfiguration());
    }
  }
  
  @Created
  public void onDataStoreRecordCreated(DataStoreRecord paramDataStoreRecord)
  {
    routeRecords(paramDataStoreRecord);
  }
  
  public void onDelivery(DataStoreRecord paramDataStoreRecord, boolean paramBoolean1, boolean paramBoolean2, String paramString)
  {
    if (paramBoolean1) {}
    for (paramString = AnalyticsEventDeliveryStatus.fromSuccess(paramDataStoreRecord.getID());; paramString = AnalyticsEventDeliveryStatus.fromFailure(paramDataStoreRecord.getID(), paramString))
    {
      this.mUpsightDataStore.store(paramString, new UpsightDataStoreListener()
      {
        public void onFailure(UpsightException paramAnonymousUpsightException)
        {
          Dispatcher.this.mLogger.e("Dispatcher", paramAnonymousUpsightException, "Could not store DeliveryStatus.", new Object[0]);
        }
        
        public void onSuccess(AnalyticsEventDeliveryStatus paramAnonymousAnalyticsEventDeliveryStatus)
        {
          Dispatcher.this.mUpsightDataStore.remove(paramAnonymousAnalyticsEventDeliveryStatus);
        }
      });
      if ((paramBoolean1) || (paramBoolean2)) {
        this.mUpsightDataStore.remove(paramDataStoreRecord);
      }
      paramString = this.mPendingRecords;
      if (paramString != null) {
        paramString.remove(paramDataStoreRecord);
      }
      return;
    }
  }
  
  public void onResponse(EndpointResponse paramEndpointResponse)
  {
    this.mUpsightDataStore.store(paramEndpointResponse, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException)
      {
        Dispatcher.this.mLogger.e("Dispatcher", paramAnonymousUpsightException, "Could not store EndpointResponse.", new Object[0]);
      }
      
      public void onSuccess(EndpointResponse paramAnonymousEndpointResponse)
      {
        Dispatcher.this.mUpsightDataStore.remove(paramAnonymousEndpointResponse);
      }
    });
  }
  
  public void onRoutingFinished(Router paramRouter)
  {
    Collection localCollection = this.mExpiredRouters;
    if (localCollection != null) {
      localCollection.remove(paramRouter);
    }
  }
  
  public void terminate()
  {
    this.mBus.unregister(this);
    if (this.mCurrentRouter != null)
    {
      this.mCurrentRouter.finishRouting();
      this.mCurrentRouter = null;
    }
    if (this.mDataStoreSubscription != null)
    {
      this.mDataStoreSubscription.unsubscribe();
      this.mDataStoreSubscription = null;
    }
    this.mCurrentConfig = null;
    this.mPendingRecords = null;
    this.mUnroutedRecords = null;
    this.mExpiredRouters = null;
    this.mCurrentRouter = null;
    this.mIsLaunched = false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/Dispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */