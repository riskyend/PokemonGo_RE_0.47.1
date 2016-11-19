package com.upsight.android.analytics.internal.session;

import android.os.Bundle;

public class PushSessionInitializer
  implements SessionInitializer
{
  private static final int NO_CMP_ID = Integer.MIN_VALUE;
  private static final int NO_MSG_ID = Integer.MIN_VALUE;
  private static final String SESSION_CAMPAIGN_ID = "campaign_id";
  private static final String SESSION_MESSAGE_ID = "message_id";
  private int mCampaignId;
  private int mMessageId;
  
  private PushSessionInitializer(int paramInt1, int paramInt2)
  {
    this.mCampaignId = paramInt1;
    this.mMessageId = paramInt2;
  }
  
  public static SessionInitializer fromExtras(Bundle paramBundle)
  {
    return new PushSessionInitializer(paramBundle.getInt("campaign_id", Integer.MIN_VALUE), paramBundle.getInt("message_id", Integer.MIN_VALUE));
  }
  
  public Integer getCampaignID()
  {
    if (this.mCampaignId == Integer.MIN_VALUE) {
      return null;
    }
    return Integer.valueOf(this.mCampaignId);
  }
  
  public Integer getMessageID()
  {
    if (this.mMessageId == Integer.MIN_VALUE) {
      return null;
    }
    return Integer.valueOf(this.mMessageId);
  }
  
  public SessionInitializer.Type getType()
  {
    return SessionInitializer.Type.PUSH;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/PushSessionInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */