package com.upsight.android.analytics.internal.session;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SessionModule_ProvidesSessionManagerFactory
  implements Factory<SessionManager>
{
  private final SessionModule module;
  private final Provider<SessionManagerImpl> sessionManagerProvider;
  
  static
  {
    if (!SessionModule_ProvidesSessionManagerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public SessionModule_ProvidesSessionManagerFactory(SessionModule paramSessionModule, Provider<SessionManagerImpl> paramProvider)
  {
    assert (paramSessionModule != null);
    this.module = paramSessionModule;
    assert (paramProvider != null);
    this.sessionManagerProvider = paramProvider;
  }
  
  public static Factory<SessionManager> create(SessionModule paramSessionModule, Provider<SessionManagerImpl> paramProvider)
  {
    return new SessionModule_ProvidesSessionManagerFactory(paramSessionModule, paramProvider);
  }
  
  public SessionManager get()
  {
    return (SessionManager)Preconditions.checkNotNull(this.module.providesSessionManager((SessionManagerImpl)this.sessionManagerProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/SessionModule_ProvidesSessionManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */