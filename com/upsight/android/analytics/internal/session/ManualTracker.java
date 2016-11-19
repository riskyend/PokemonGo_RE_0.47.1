package com.upsight.android.analytics.internal.session;

import android.app.Activity;
import android.content.Intent;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.UpsightLifeCycleTracker;
import com.upsight.android.analytics.UpsightLifeCycleTracker.ActivityState;
import com.upsight.android.analytics.UpsightLifeCycleTracker.ActivityTrackEvent;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightDataStoreListener;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class ManualTracker
  extends UpsightLifeCycleTracker
{
  private static final String LOG_TAG = ManualTracker.class.getSimpleName();
  private Set<WeakReference<Activity>> mActivitySet;
  private Bus mBus;
  private UpsightDataStore mDataStore;
  private boolean mIsTaskRootStopped = false;
  private UpsightLogger mLogger;
  private SessionManager mSessionManager;
  
  @Inject
  public ManualTracker(SessionManager paramSessionManager, UpsightContext paramUpsightContext)
  {
    this.mSessionManager = paramSessionManager;
    this.mDataStore = paramUpsightContext.getDataStore();
    this.mBus = paramUpsightContext.getCoreComponent().bus();
    this.mLogger = paramUpsightContext.getLogger();
    this.mActivitySet = new HashSet();
  }
  
  private static boolean isPurgeable(Activity paramActivity)
  {
    return paramActivity == null;
  }
  
  private static void removeAndPurge(Set<WeakReference<Activity>> paramSet, Activity paramActivity)
  {
    paramSet = paramSet.iterator();
    while (paramSet.hasNext())
    {
      Activity localActivity = (Activity)((WeakReference)paramSet.next()).get();
      if ((localActivity == paramActivity) || (isPurgeable(localActivity))) {
        paramSet.remove();
      }
    }
  }
  
  public void track(Activity paramActivity, UpsightLifeCycleTracker.ActivityState paramActivityState, SessionInitializer paramSessionInitializer)
  {
    if ((paramActivity == null) || (paramActivityState == null)) {
      return;
    }
    switch (paramActivityState)
    {
    }
    for (;;)
    {
      this.mBus.post(new UpsightLifeCycleTracker.ActivityTrackEvent(paramActivity, paramActivityState));
      return;
      this.mLogger.d(LOG_TAG, "Track starting of " + paramActivity + " isTaskRoot=" + paramActivity.isTaskRoot(), new Object[0]);
      if (this.mActivitySet.isEmpty())
      {
        this.mDataStore.fetch(ApplicationStatus.class, new UpsightDataStoreListener()
        {
          public void onFailure(UpsightException paramAnonymousUpsightException) {}
          
          public void onSuccess(Set<ApplicationStatus> paramAnonymousSet)
          {
            if (paramAnonymousSet.isEmpty())
            {
              ManualTracker.this.mDataStore.store(new ApplicationStatus(ApplicationStatus.State.FOREGROUND));
              ManualTracker.this.mLogger.d(ManualTracker.LOG_TAG, "Create application state " + ApplicationStatus.State.FOREGROUND, new Object[0]);
            }
            for (;;)
            {
              return;
              paramAnonymousSet = paramAnonymousSet.iterator();
              int i = 0;
              while (paramAnonymousSet.hasNext())
              {
                ApplicationStatus localApplicationStatus = (ApplicationStatus)paramAnonymousSet.next();
                if (i == 0)
                {
                  localApplicationStatus.state = ApplicationStatus.State.FOREGROUND;
                  ManualTracker.this.mDataStore.store(localApplicationStatus);
                  i = 1;
                  ManualTracker.this.mLogger.d(ManualTracker.LOG_TAG, "Update application state to " + localApplicationStatus.state, new Object[0]);
                }
                else
                {
                  ManualTracker.this.mDataStore.remove(localApplicationStatus);
                  ManualTracker.this.mLogger.w(ManualTracker.LOG_TAG, "Remove duplicate application state " + localApplicationStatus.state, new Object[0]);
                }
              }
            }
          }
        });
        Intent localIntent = paramActivity.getIntent();
        if ((localIntent == null) || (!localIntent.hasExtra("pushMessage")))
        {
          this.mSessionManager.startSession(paramSessionInitializer);
          this.mLogger.d(LOG_TAG, "Request to start new Upsight session", new Object[0]);
        }
      }
      this.mActivitySet.add(new WeakReference(paramActivity));
      continue;
      this.mLogger.d(LOG_TAG, "Track stopping of " + paramActivity, new Object[0]);
      removeAndPurge(this.mActivitySet, paramActivity);
      if (paramActivity.isTaskRoot())
      {
        this.mIsTaskRootStopped = true;
        this.mLogger.d(LOG_TAG, "Clear task root stopped condition with task root Activity " + paramActivity, new Object[0]);
      }
      if ((this.mIsTaskRootStopped) && (!paramActivity.isChangingConfigurations()) && (this.mActivitySet.isEmpty()))
      {
        this.mDataStore.fetch(ApplicationStatus.class, new UpsightDataStoreListener()
        {
          public void onFailure(UpsightException paramAnonymousUpsightException) {}
          
          public void onSuccess(Set<ApplicationStatus> paramAnonymousSet)
          {
            if (paramAnonymousSet.isEmpty())
            {
              ManualTracker.this.mDataStore.store(new ApplicationStatus(ApplicationStatus.State.BACKGROUND));
              ManualTracker.this.mLogger.d(ManualTracker.LOG_TAG, "Create application state " + ApplicationStatus.State.BACKGROUND, new Object[0]);
            }
            for (;;)
            {
              return;
              paramAnonymousSet = paramAnonymousSet.iterator();
              int i = 0;
              while (paramAnonymousSet.hasNext())
              {
                ApplicationStatus localApplicationStatus = (ApplicationStatus)paramAnonymousSet.next();
                if (i == 0)
                {
                  localApplicationStatus.state = ApplicationStatus.State.BACKGROUND;
                  ManualTracker.this.mDataStore.store(localApplicationStatus);
                  i = 1;
                  ManualTracker.this.mLogger.d(ManualTracker.LOG_TAG, "Update application state to " + localApplicationStatus.state, new Object[0]);
                }
                else
                {
                  ManualTracker.this.mDataStore.remove(localApplicationStatus);
                  paramAnonymousSet.remove();
                  ManualTracker.this.mLogger.w(ManualTracker.LOG_TAG, "Remove duplicate application state " + localApplicationStatus.state, new Object[0]);
                }
              }
            }
          }
        });
        this.mSessionManager.stopSession();
        this.mLogger.d(LOG_TAG, "Request to stop current Upsight session", new Object[0]);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/ManualTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */