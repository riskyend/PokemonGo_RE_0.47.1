package com.upsight.android.analytics.internal.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.application.status")
public class ApplicationStatus
{
  @Expose
  @SerializedName("id")
  @UpsightStorableIdentifier
  String id;
  @Expose
  @SerializedName("state")
  State state;
  
  ApplicationStatus() {}
  
  public ApplicationStatus(State paramState)
  {
    this.state = paramState;
  }
  
  public State getState()
  {
    return this.state;
  }
  
  public static enum State
  {
    BACKGROUND,  FOREGROUND;
    
    private State() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/ApplicationStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */