package com.upsight.android.analytics.internal.session;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.upsight.android.analytics.UpsightLifeCycleTracker.ActivityState;
import javax.inject.Inject;

@TargetApi(14)
public class ActivityLifecycleTracker
  implements Application.ActivityLifecycleCallbacks
{
  private SessionInitializer mSessionInitializer;
  private ManualTracker mTracker;
  
  @Inject
  public ActivityLifecycleTracker(ManualTracker paramManualTracker)
  {
    this.mTracker = paramManualTracker;
    this.mSessionInitializer = new StandardSessionInitializer();
  }
  
  public void onActivityCreated(Activity paramActivity, Bundle paramBundle)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.CREATED, this.mSessionInitializer);
  }
  
  public void onActivityDestroyed(Activity paramActivity)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.DESTROYED, this.mSessionInitializer);
  }
  
  public void onActivityPaused(Activity paramActivity)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.PAUSED, this.mSessionInitializer);
  }
  
  public void onActivityResumed(Activity paramActivity)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.RESUMED, this.mSessionInitializer);
  }
  
  public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.SAVE_INSTANCE_STATE, this.mSessionInitializer);
  }
  
  public void onActivityStarted(Activity paramActivity)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.STARTED, this.mSessionInitializer);
  }
  
  public void onActivityStopped(Activity paramActivity)
  {
    this.mTracker.track(paramActivity, UpsightLifeCycleTracker.ActivityState.STOPPED, this.mSessionInitializer);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/ActivityLifecycleTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */