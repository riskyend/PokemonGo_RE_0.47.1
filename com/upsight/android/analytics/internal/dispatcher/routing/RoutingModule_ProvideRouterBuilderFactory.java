package com.upsight.android.analytics.internal.dispatcher.routing;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.dispatcher.delivery.QueueBuilder;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class RoutingModule_ProvideRouterBuilderFactory
  implements Factory<RouterBuilder>
{
  private final RoutingModule module;
  private final Provider<QueueBuilder> queueBuilderProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!RoutingModule_ProvideRouterBuilderFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public RoutingModule_ProvideRouterBuilderFactory(RoutingModule paramRoutingModule, Provider<UpsightContext> paramProvider, Provider<QueueBuilder> paramProvider1)
  {
    assert (paramRoutingModule != null);
    this.module = paramRoutingModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.queueBuilderProvider = paramProvider1;
  }
  
  public static Factory<RouterBuilder> create(RoutingModule paramRoutingModule, Provider<UpsightContext> paramProvider, Provider<QueueBuilder> paramProvider1)
  {
    return new RoutingModule_ProvideRouterBuilderFactory(paramRoutingModule, paramProvider, paramProvider1);
  }
  
  public RouterBuilder get()
  {
    return (RouterBuilder)Preconditions.checkNotNull(this.module.provideRouterBuilder((UpsightContext)this.upsightProvider.get(), (QueueBuilder)this.queueBuilderProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/routing/RoutingModule_ProvideRouterBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */