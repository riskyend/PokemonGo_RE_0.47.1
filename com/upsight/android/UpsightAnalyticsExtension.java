package com.upsight.android;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.analytics.UpsightAnalyticsComponent;
import com.upsight.android.analytics.event.install.UpsightInstallEvent;
import com.upsight.android.analytics.event.install.UpsightInstallEvent.Builder;
import com.upsight.android.analytics.internal.BaseAnalyticsModule;
import com.upsight.android.analytics.internal.DaggerAnalyticsComponent;
import com.upsight.android.analytics.internal.DaggerAnalyticsComponent.Builder;
import com.upsight.android.analytics.internal.association.AssociationManager;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.internal.util.Opt;
import com.upsight.android.internal.util.PreferencesHelper;
import javax.inject.Inject;
import javax.inject.Named;

public class UpsightAnalyticsExtension
  extends UpsightExtension<UpsightAnalyticsComponent, UpsightAnalyticsApi>
{
  public static final String EXTENSION_NAME = "com.upsight.extension.analytics";
  @Inject
  UpsightAnalyticsApi mAnalytics;
  @Inject
  AssociationManager mAssociationManager;
  @Inject
  Clock mClock;
  @Inject
  @Named("optUncaughtExceptionHandler")
  Opt<Thread.UncaughtExceptionHandler> mUncaughtExceptionHandler;
  @Inject
  Application.ActivityLifecycleCallbacks mUpsightLifeCycleCallbacks;
  
  public UpsightAnalyticsApi getApi()
  {
    return this.mAnalytics;
  }
  
  protected void onCreate(UpsightContext paramUpsightContext)
  {
    if (this.mUncaughtExceptionHandler.isPresent()) {
      Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)this.mUncaughtExceptionHandler.get());
    }
    ((Application)paramUpsightContext.getApplicationContext()).registerActivityLifecycleCallbacks(this.mUpsightLifeCycleCallbacks);
    this.mAssociationManager.launch();
  }
  
  protected void onPostCreate(UpsightContext paramUpsightContext)
  {
    if (!PreferencesHelper.contains(paramUpsightContext, "install_ts"))
    {
      PreferencesHelper.putLong(paramUpsightContext, "install_ts", this.mClock.currentTimeSeconds());
      UpsightInstallEvent.createBuilder().record(paramUpsightContext);
    }
  }
  
  protected UpsightAnalyticsComponent onResolve(UpsightContext paramUpsightContext)
  {
    return DaggerAnalyticsComponent.builder().baseAnalyticsModule(new BaseAnalyticsModule(paramUpsightContext)).build();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightAnalyticsExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */