package com.upsight.android.analytics.internal.dispatcher;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.internal.AnalyticsContext;
import com.upsight.android.analytics.internal.dispatcher.routing.RouterBuilder;
import com.upsight.android.analytics.internal.dispatcher.schema.SchemaSelectorBuilder;
import com.upsight.android.analytics.internal.session.SessionManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class DispatchModule
{
  @Provides
  @Singleton
  public Dispatcher provideDispatcher(UpsightContext paramUpsightContext, SessionManager paramSessionManager, AnalyticsContext paramAnalyticsContext, ConfigParser paramConfigParser, RouterBuilder paramRouterBuilder, SchemaSelectorBuilder paramSchemaSelectorBuilder)
  {
    return new Dispatcher(paramAnalyticsContext, paramSessionManager, paramUpsightContext.getCoreComponent().backgroundDataStore(), paramConfigParser, paramRouterBuilder, paramSchemaSelectorBuilder, paramUpsightContext.getCoreComponent().bus(), paramUpsightContext.getLogger());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/DispatchModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */