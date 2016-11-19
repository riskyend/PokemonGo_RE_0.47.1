package com.upsight.android.googlepushservices.internal;

import android.support.annotation.NonNull;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsComponent;
import com.upsight.android.analytics.internal.session.Session;
import com.upsight.android.analytics.internal.session.SessionInitializer;
import com.upsight.android.analytics.internal.session.SessionManager;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.googlepushservices.UpsightGooglePushServicesApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class PushModule
{
  private final UpsightContext mUpsight;
  
  public PushModule(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  @Provides
  @Singleton
  public UpsightGooglePushServicesApi provideGooglePushServicesApi(GooglePushServices paramGooglePushServices)
  {
    return paramGooglePushServices;
  }
  
  @Provides
  @Singleton
  public PushConfigManager providePushConfigManager(UpsightContext paramUpsightContext)
  {
    return new PushConfigManager(paramUpsightContext);
  }
  
  @Provides
  @Singleton
  SessionManager provideSessionManager(UpsightContext paramUpsightContext)
  {
    paramUpsightContext = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (paramUpsightContext != null) {
      return ((UpsightAnalyticsComponent)paramUpsightContext.getComponent()).sessionManager();
    }
    new SessionManager()
    {
      public Session getBackgroundSession()
      {
        return null;
      }
      
      public UpsightSessionInfo getLatestSessionInfo()
      {
        return UpsightSessionInfo.NONE;
      }
      
      public Session getSession()
      {
        return null;
      }
      
      public Session startSession(@NonNull SessionInitializer paramAnonymousSessionInitializer)
      {
        return null;
      }
      
      public void stopSession() {}
    };
  }
  
  @Provides
  @Singleton
  UpsightContext provideUpsightContext()
  {
    return this.mUpsight;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/PushModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */