package com.upsight.android.analytics.internal.action;

import com.squareup.otto.Bus;

public abstract class Actionable
{
  private ActionMap mActionMap;
  private String mId;
  
  private Actionable() {}
  
  protected <T extends Actionable, U extends ActionContext> Actionable(String paramString, ActionMap<T, U> paramActionMap)
  {
    this.mId = paramString;
    this.mActionMap = paramActionMap;
  }
  
  public void executeActions(String paramString)
  {
    this.mActionMap.executeActions(paramString, this);
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public void signalActionCompleted(Bus paramBus)
  {
    if (this.mActionMap.signalActionCompleted()) {
      paramBus.post(new ActionMapFinishedEvent(this.mId, null));
    }
  }
  
  public void signalActionMapCompleted(Bus paramBus)
  {
    if (this.mActionMap.signalActionMapCompleted()) {
      paramBus.post(new ActionMapFinishedEvent(this.mId, null));
    }
  }
  
  public static class ActionMapFinishedEvent
  {
    public final String mId;
    
    private ActionMapFinishedEvent(String paramString)
    {
      this.mId = paramString;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/action/Actionable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */