package com.upsight.android.googlepushservices.internal;

import com.upsight.android.analytics.internal.session.SessionManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class PushClickIntentService_MembersInjector
  implements MembersInjector<PushClickIntentService>
{
  private final Provider<SessionManager> mSessionManagerProvider;
  
  static
  {
    if (!PushClickIntentService_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public PushClickIntentService_MembersInjector(Provider<SessionManager> paramProvider)
  {
    assert (paramProvider != null);
    this.mSessionManagerProvider = paramProvider;
  }
  
  public static MembersInjector<PushClickIntentService> create(Provider<SessionManager> paramProvider)
  {
    return new PushClickIntentService_MembersInjector(paramProvider);
  }
  
  public static void injectMSessionManager(PushClickIntentService paramPushClickIntentService, Provider<SessionManager> paramProvider)
  {
    paramPushClickIntentService.mSessionManager = ((SessionManager)paramProvider.get());
  }
  
  public void injectMembers(PushClickIntentService paramPushClickIntentService)
  {
    if (paramPushClickIntentService == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramPushClickIntentService.mSessionManager = ((SessionManager)this.mSessionManagerProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/PushClickIntentService_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */