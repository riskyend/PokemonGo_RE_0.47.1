package com.upsight.android.analytics.internal.session;

import android.content.Context;
import com.upsight.android.internal.util.PreferencesHelper;

public class BackgroundSessionImpl
  implements Session
{
  private final Context mContext;
  private final SessionInitializer.Type mType;
  
  private BackgroundSessionImpl(BackgroundSessionInitializer paramBackgroundSessionInitializer, Context paramContext)
  {
    this.mType = paramBackgroundSessionInitializer.getType();
    this.mContext = paramContext;
  }
  
  public static BackgroundSessionImpl create(Context paramContext, BackgroundSessionInitializer paramBackgroundSessionInitializer)
  {
    return new BackgroundSessionImpl(paramBackgroundSessionInitializer, paramContext);
  }
  
  public Integer getCampaignID()
  {
    return null;
  }
  
  public Integer getMessageID()
  {
    return null;
  }
  
  public Long getPreviousTos()
  {
    return Long.valueOf(PreferencesHelper.getLong(this.mContext, "past_session_time", 0L));
  }
  
  public Integer getSessionNumber()
  {
    return null;
  }
  
  public Long getStartTimestamp()
  {
    return null;
  }
  
  public SessionInitializer.Type getType()
  {
    return this.mType;
  }
  
  public void updateDuration(Context paramContext, long paramLong) {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/BackgroundSessionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */