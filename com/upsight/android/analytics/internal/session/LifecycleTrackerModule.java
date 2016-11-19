package com.upsight.android.analytics.internal.session;

import android.app.Application.ActivityLifecycleCallbacks;
import com.upsight.android.analytics.UpsightLifeCycleTracker;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class LifecycleTrackerModule
{
  @Provides
  @Singleton
  public UpsightLifeCycleTracker provideManualTracker(ManualTracker paramManualTracker)
  {
    return paramManualTracker;
  }
  
  @Provides
  @Singleton
  public Application.ActivityLifecycleCallbacks provideUpsightLifeCycleCallbacks(ActivityLifecycleTracker paramActivityLifecycleTracker)
  {
    return paramActivityLifecycleTracker;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/LifecycleTrackerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */