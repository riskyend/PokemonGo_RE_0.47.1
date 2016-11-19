package com.upsight.android.analytics.internal;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.squareup.otto.Bus;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.UpsightAnalyticsComponent;
import com.upsight.android.analytics.internal.configuration.ConfigurationManager;
import com.upsight.android.analytics.internal.dispatcher.Dispatcher;
import com.upsight.android.analytics.internal.session.ApplicationStatus;
import com.upsight.android.analytics.internal.session.ApplicationStatus.State;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightDataStoreListener;
import com.upsight.android.persistence.UpsightSubscription;
import com.upsight.android.persistence.annotation.Created;
import com.upsight.android.persistence.annotation.Updated;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;

public class DispatcherService
  extends Service
{
  private static final String LOG_TAG = DispatcherService.class.getSimpleName();
  private static final long STATUS_CHECK_INTERVAL = 25000L;
  private static final int STOP_AFTER_DEAD_INTERVALS = 4;
  private Bus mBus;
  @Inject
  ConfigurationManager mConfigurationManager;
  private UpsightSubscription mDataStoreSubscription;
  private int mDeadIntervalsInARow;
  @Inject
  Dispatcher mDispatcher;
  private Handler mHandler;
  private UpsightLogger mLogger;
  private Runnable mSelfStopTask = new Runnable()
  {
    public void run()
    {
      if (!Upsight.isEnabled(DispatcherService.this.getApplicationContext())) {
        DispatcherService.this.stopSelf();
      }
      boolean bool = DispatcherService.this.mDispatcher.hasPendingRecords();
      DispatcherService.this.mLogger.d(DispatcherService.LOG_TAG, "Check for idle hasPendingRecords=" + bool + " mDeadIntervalsInARow=" + DispatcherService.this.mDeadIntervalsInARow, new Object[0]);
      if (bool)
      {
        DispatcherService.access$102(DispatcherService.this, 0);
        DispatcherService.this.mHandler.postDelayed(DispatcherService.this.mSelfStopTask, 25000L);
        return;
      }
      if (DispatcherService.this.mDeadIntervalsInARow == 4)
      {
        DispatcherService.this.mLogger.d(DispatcherService.LOG_TAG, "Request to destroy", new Object[0]);
        DispatcherService.this.stopSelf();
        return;
      }
      DispatcherService.access$108(DispatcherService.this);
      DispatcherService.this.mHandler.postDelayed(DispatcherService.this.mSelfStopTask, 25000L);
    }
  };
  
  private void handle(ApplicationStatus paramApplicationStatus)
  {
    if (paramApplicationStatus.getState() == ApplicationStatus.State.BACKGROUND)
    {
      this.mDeadIntervalsInARow = 0;
      this.mHandler.postDelayed(this.mSelfStopTask, 25000L);
      return;
    }
    this.mHandler.removeCallbacks(this.mSelfStopTask);
  }
  
  @Created
  @Updated
  public void onApplicationStatus(ApplicationStatus paramApplicationStatus)
  {
    handle(paramApplicationStatus);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    if (!Upsight.isEnabled(getApplicationContext())) {
      stopSelf();
    }
    UpsightContext localUpsightContext;
    UpsightAnalyticsExtension localUpsightAnalyticsExtension;
    do
    {
      return;
      localUpsightContext = Upsight.createContext(this);
      localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)localUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    } while (localUpsightAnalyticsExtension == null);
    ((UpsightAnalyticsComponent)localUpsightAnalyticsExtension.getComponent()).inject(this);
    this.mBus = localUpsightContext.getCoreComponent().bus();
    this.mLogger = localUpsightContext.getLogger();
    this.mLogger.d(LOG_TAG, "onCreate()", new Object[0]);
    this.mHandler = new Handler();
    this.mDataStoreSubscription = localUpsightContext.getDataStore().subscribe(this);
    this.mDispatcher.launch();
    this.mConfigurationManager.launch();
    localUpsightContext.getDataStore().fetch(ApplicationStatus.class, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException) {}
      
      public void onSuccess(Set<ApplicationStatus> paramAnonymousSet)
      {
        paramAnonymousSet = paramAnonymousSet.iterator();
        while (paramAnonymousSet.hasNext())
        {
          ApplicationStatus localApplicationStatus = (ApplicationStatus)paramAnonymousSet.next();
          DispatcherService.this.handle(localApplicationStatus);
        }
      }
    });
  }
  
  public void onDestroy()
  {
    if (this.mBus != null) {
      this.mBus.post(new DestroyEvent());
    }
    if ((this.mHandler != null) && (this.mSelfStopTask != null)) {
      this.mHandler.removeCallbacks(this.mSelfStopTask);
    }
    if (this.mDataStoreSubscription != null) {
      this.mDataStoreSubscription.unsubscribe();
    }
    if (this.mLogger != null) {
      this.mLogger.d(LOG_TAG, "onDestroy()", new Object[0]);
    }
    super.onDestroy();
  }
  
  public static final class DestroyEvent {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/DispatcherService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */