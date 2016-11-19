package com.upsight.android.analytics.internal;

import com.upsight.android.analytics.internal.configuration.ConfigurationManager;
import com.upsight.android.analytics.internal.dispatcher.Dispatcher;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class DispatcherService_MembersInjector
  implements MembersInjector<DispatcherService>
{
  private final Provider<ConfigurationManager> mConfigurationManagerProvider;
  private final Provider<Dispatcher> mDispatcherProvider;
  
  static
  {
    if (!DispatcherService_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public DispatcherService_MembersInjector(Provider<ConfigurationManager> paramProvider, Provider<Dispatcher> paramProvider1)
  {
    assert (paramProvider != null);
    this.mConfigurationManagerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mDispatcherProvider = paramProvider1;
  }
  
  public static MembersInjector<DispatcherService> create(Provider<ConfigurationManager> paramProvider, Provider<Dispatcher> paramProvider1)
  {
    return new DispatcherService_MembersInjector(paramProvider, paramProvider1);
  }
  
  public static void injectMConfigurationManager(DispatcherService paramDispatcherService, Provider<ConfigurationManager> paramProvider)
  {
    paramDispatcherService.mConfigurationManager = ((ConfigurationManager)paramProvider.get());
  }
  
  public static void injectMDispatcher(DispatcherService paramDispatcherService, Provider<Dispatcher> paramProvider)
  {
    paramDispatcherService.mDispatcher = ((Dispatcher)paramProvider.get());
  }
  
  public void injectMembers(DispatcherService paramDispatcherService)
  {
    if (paramDispatcherService == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramDispatcherService.mConfigurationManager = ((ConfigurationManager)this.mConfigurationManagerProvider.get());
    paramDispatcherService.mDispatcher = ((Dispatcher)this.mDispatcherProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/DispatcherService_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */