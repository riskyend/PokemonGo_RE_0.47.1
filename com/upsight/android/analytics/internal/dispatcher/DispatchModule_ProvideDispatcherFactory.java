package com.upsight.android.analytics.internal.dispatcher;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.AnalyticsContext;
import com.upsight.android.analytics.internal.dispatcher.routing.RouterBuilder;
import com.upsight.android.analytics.internal.dispatcher.schema.SchemaSelectorBuilder;
import com.upsight.android.analytics.internal.session.SessionManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DispatchModule_ProvideDispatcherFactory
  implements Factory<Dispatcher>
{
  private final Provider<ConfigParser> configParserProvider;
  private final Provider<AnalyticsContext> contextProvider;
  private final DispatchModule module;
  private final Provider<RouterBuilder> routerBuilderProvider;
  private final Provider<SchemaSelectorBuilder> schemaSelectorBuilderProvider;
  private final Provider<SessionManager> sessionManagerProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!DispatchModule_ProvideDispatcherFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public DispatchModule_ProvideDispatcherFactory(DispatchModule paramDispatchModule, Provider<UpsightContext> paramProvider, Provider<SessionManager> paramProvider1, Provider<AnalyticsContext> paramProvider2, Provider<ConfigParser> paramProvider3, Provider<RouterBuilder> paramProvider4, Provider<SchemaSelectorBuilder> paramProvider5)
  {
    assert (paramDispatchModule != null);
    this.module = paramDispatchModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.sessionManagerProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.contextProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.configParserProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.routerBuilderProvider = paramProvider4;
    assert (paramProvider5 != null);
    this.schemaSelectorBuilderProvider = paramProvider5;
  }
  
  public static Factory<Dispatcher> create(DispatchModule paramDispatchModule, Provider<UpsightContext> paramProvider, Provider<SessionManager> paramProvider1, Provider<AnalyticsContext> paramProvider2, Provider<ConfigParser> paramProvider3, Provider<RouterBuilder> paramProvider4, Provider<SchemaSelectorBuilder> paramProvider5)
  {
    return new DispatchModule_ProvideDispatcherFactory(paramDispatchModule, paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4, paramProvider5);
  }
  
  public Dispatcher get()
  {
    return (Dispatcher)Preconditions.checkNotNull(this.module.provideDispatcher((UpsightContext)this.upsightProvider.get(), (SessionManager)this.sessionManagerProvider.get(), (AnalyticsContext)this.contextProvider.get(), (ConfigParser)this.configParserProvider.get(), (RouterBuilder)this.routerBuilderProvider.get(), (SchemaSelectorBuilder)this.schemaSelectorBuilderProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/DispatchModule_ProvideDispatcherFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */