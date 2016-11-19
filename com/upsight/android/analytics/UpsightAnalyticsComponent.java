package com.upsight.android.analytics;

import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightExtension.BaseComponent;
import com.upsight.android.analytics.internal.DispatcherService;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.analytics.internal.session.SessionManager;

public abstract interface UpsightAnalyticsComponent
  extends UpsightExtension.BaseComponent<UpsightAnalyticsExtension>
{
  public abstract Clock clock();
  
  public abstract void inject(DispatcherService paramDispatcherService);
  
  public abstract SessionManager sessionManager();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/UpsightAnalyticsComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */