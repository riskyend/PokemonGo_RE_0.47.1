package com.upsight.android.analytics.internal.session;

import android.support.annotation.NonNull;
import com.upsight.android.analytics.session.UpsightSessionInfo;

public abstract interface SessionManager
{
  public static final String CONFIGURATION_SUBTYPE = "upsight.configuration.session_manager";
  public static final String DEFAULT_CONFIGURATION = "{\"session_gap\": 120}";
  public static final String SESSION_CAMPAIGN_ID = "campaign_id";
  public static final String SESSION_EXTRA = "session_extra";
  public static final String SESSION_MESSAGE_ID = "message_id";
  
  public abstract Session getBackgroundSession();
  
  public abstract UpsightSessionInfo getLatestSessionInfo();
  
  public abstract Session getSession();
  
  public abstract Session startSession(@NonNull SessionInitializer paramSessionInitializer);
  
  public abstract void stopSession();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/SessionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */