package com.upsight.android.analytics.internal.session;

import android.content.Context;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.internal.util.PreferencesHelper;

public class SessionImpl
  implements Session
{
  private final Integer mCampaignId;
  private final Long mInitialSessionStartTs;
  private final Integer mMessageId;
  private final Long mPastSessionTime;
  private final Integer mSessionNum;
  private final SessionInitializer.Type mType;
  
  private SessionImpl(SessionInitializer paramSessionInitializer, Integer paramInteger, Long paramLong1, Long paramLong2)
  {
    this.mType = paramSessionInitializer.getType();
    this.mCampaignId = paramSessionInitializer.getCampaignID();
    this.mMessageId = paramSessionInitializer.getMessageID();
    this.mSessionNum = paramInteger;
    this.mInitialSessionStartTs = paramLong1;
    this.mPastSessionTime = paramLong2;
  }
  
  public static Session create(Context paramContext, Clock paramClock, SessionInitializer paramSessionInitializer)
  {
    int i = PreferencesHelper.getInt(paramContext, "session_num", Integer.MIN_VALUE);
    long l = PreferencesHelper.getLong(paramContext, "session_start_ts", Long.MIN_VALUE);
    if ((i == Integer.MIN_VALUE) || (l == Long.MIN_VALUE)) {
      return incrementAndCreate(paramContext, paramClock, paramSessionInitializer);
    }
    return new SessionImpl(paramSessionInitializer, Integer.valueOf(i), Long.valueOf(l), Long.valueOf(PreferencesHelper.getLong(paramContext, "past_session_time", 0L)));
  }
  
  public static UpsightSessionInfo getLatestSessionInfo(Context paramContext)
  {
    int i = PreferencesHelper.getInt(paramContext, "session_num", 0);
    long l = PreferencesHelper.getLong(paramContext, "session_start_ts", 0L);
    if ((i > 0) && (l > 0L)) {
      return new UpsightSessionInfo(i, l);
    }
    return UpsightSessionInfo.NONE;
  }
  
  public static Session incrementAndCreate(Context paramContext, Clock paramClock, SessionInitializer paramSessionInitializer)
  {
    int i = PreferencesHelper.getInt(paramContext, "session_num", 0) + 1;
    long l1 = paramClock.currentTimeSeconds();
    PreferencesHelper.putInt(paramContext, "session_num", i);
    PreferencesHelper.putLong(paramContext, "session_start_ts", l1);
    long l2 = PreferencesHelper.getLong(paramContext, "current_session_duration", 0L);
    l2 = PreferencesHelper.getLong(paramContext, "past_session_time", 0L) + l2;
    PreferencesHelper.putLong(paramContext, "current_session_duration", 0L);
    PreferencesHelper.putLong(paramContext, "past_session_time", l2);
    return new SessionImpl(paramSessionInitializer, Integer.valueOf(i), Long.valueOf(l1), Long.valueOf(l2));
  }
  
  public Integer getCampaignID()
  {
    try
    {
      Integer localInteger = this.mCampaignId;
      return localInteger;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Integer getMessageID()
  {
    try
    {
      Integer localInteger = this.mMessageId;
      return localInteger;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Long getPreviousTos()
  {
    return this.mPastSessionTime;
  }
  
  public Integer getSessionNumber()
  {
    try
    {
      Integer localInteger = this.mSessionNum;
      return localInteger;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Long getStartTimestamp()
  {
    try
    {
      Long localLong = this.mInitialSessionStartTs;
      return localLong;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public SessionInitializer.Type getType()
  {
    return this.mType;
  }
  
  public void updateDuration(Context paramContext, long paramLong)
  {
    PreferencesHelper.putLong(paramContext, "current_session_duration", paramLong - this.mInitialSessionStartTs.longValue());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/SessionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */