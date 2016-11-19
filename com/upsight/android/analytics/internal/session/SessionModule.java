package com.upsight.android.analytics.internal.session;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class SessionModule
{
  @Provides
  @Singleton
  public SessionManager providesSessionManager(SessionManagerImpl paramSessionManagerImpl)
  {
    return paramSessionManagerImpl;
  }
  
  @Provides
  @Singleton
  public SessionManagerImpl providesSessionManagerImpl(UpsightContext paramUpsightContext, ConfigParser paramConfigParser, Clock paramClock)
  {
    return new SessionManagerImpl(paramUpsightContext.getCoreComponent().applicationContext(), paramUpsightContext, paramUpsightContext.getDataStore(), paramUpsightContext.getLogger(), paramConfigParser, paramClock);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/SessionModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */