package com.upsight.android.analytics.internal.session;

import android.content.Context;

public abstract interface Session
  extends SessionInitializer
{
  public static final String PREFERENCES_KEY_CURRENT_SESSION_DURATION = "current_session_duration";
  public static final String PREFERENCES_KEY_PAST_SESSION_TIME = "past_session_time";
  public static final String PREFERENCES_KEY_SESSION_NUM = "session_num";
  public static final String PREFERENCES_KEY_SESSION_START_TS = "session_start_ts";
  public static final int SESSION_NUM_BASE_OFFSET = 0;
  
  public abstract Long getPreviousTos();
  
  public abstract Integer getSessionNumber();
  
  public abstract Long getStartTimestamp();
  
  public abstract void updateDuration(Context paramContext, long paramLong);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/Session.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */