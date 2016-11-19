package com.upsight.android.analytics.session;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.provider.UpsightSessionContext;

public abstract interface UpsightSessionCallbacks
{
  public abstract void onResume(UpsightSessionContext paramUpsightSessionContext, UpsightSessionInfo paramUpsightSessionInfo);
  
  public abstract void onResumed(UpsightContext paramUpsightContext);
  
  public abstract void onStart(UpsightSessionContext paramUpsightSessionContext, UpsightSessionInfo paramUpsightSessionInfo);
  
  public abstract void onStarted(UpsightContext paramUpsightContext);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/session/UpsightSessionCallbacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */