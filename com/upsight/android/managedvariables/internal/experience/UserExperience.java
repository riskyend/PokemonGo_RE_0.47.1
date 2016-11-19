package com.upsight.android.managedvariables.internal.experience;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.analytics.internal.action.Actionable.ActionMapFinishedEvent;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import com.upsight.android.managedvariables.experience.UpsightUserExperience.Handler;
import com.upsight.android.managedvariables.internal.type.UxmContentActions.ScheduleSyncNotificationEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserExperience
  extends UpsightUserExperience
{
  private static final UpsightUserExperience.Handler DEFAULT_HANDLER = new DefaultHandler(null);
  private Bus mBus;
  private UpsightUserExperience.Handler mHandler = DEFAULT_HANDLER;
  private Map<String, List<String>> mSyncNotifications = new HashMap();
  
  UserExperience(Bus paramBus)
  {
    this.mBus = paramBus;
    this.mBus.register(this);
  }
  
  public UpsightUserExperience.Handler getHandler()
  {
    try
    {
      UpsightUserExperience.Handler localHandler = this.mHandler;
      return localHandler;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Subscribe
  public void handleActionMapFinishedEvent(Actionable.ActionMapFinishedEvent paramActionMapFinishedEvent)
  {
    try
    {
      paramActionMapFinishedEvent = (List)this.mSyncNotifications.remove(paramActionMapFinishedEvent.mId);
      if (paramActionMapFinishedEvent != null) {
        this.mHandler.onSynchronize(paramActionMapFinishedEvent);
      }
      return;
    }
    finally {}
  }
  
  @Subscribe
  public void handleScheduleSyncNotificationEvent(UxmContentActions.ScheduleSyncNotificationEvent paramScheduleSyncNotificationEvent)
  {
    try
    {
      this.mSyncNotifications.put(paramScheduleSyncNotificationEvent.mId, paramScheduleSyncNotificationEvent.mTags);
      return;
    }
    finally
    {
      paramScheduleSyncNotificationEvent = finally;
      throw paramScheduleSyncNotificationEvent;
    }
  }
  
  public void registerHandler(UpsightUserExperience.Handler paramHandler)
  {
    if (paramHandler != null) {}
    for (;;)
    {
      try
      {
        this.mHandler = paramHandler;
        return;
      }
      finally {}
      this.mHandler = DEFAULT_HANDLER;
    }
  }
  
  private static class DefaultHandler
    implements UpsightUserExperience.Handler
  {
    public boolean onReceive()
    {
      return true;
    }
    
    public void onSynchronize(List<String> paramList) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/experience/UserExperience.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */