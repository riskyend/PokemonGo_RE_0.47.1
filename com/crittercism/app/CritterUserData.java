package com.crittercism.app;

import crittercism.android.dx;
import java.util.Map;

public class CritterUserData
{
  private Map a;
  private final boolean b;
  
  CritterUserData(Map paramMap, boolean paramBoolean)
  {
    this.a = paramMap;
    this.b = paramBoolean;
  }
  
  public boolean crashedOnLastLoad()
  {
    if (!this.a.containsKey("crashedOnLastLoad"))
    {
      if (this.b) {
        dx.c("User has opted out of Crittercism.  Returning false.");
      }
      for (;;)
      {
        return false;
        dx.c("CritterUserData instance has no value for crashedOnLastLoad().  Defaulting to false.");
      }
    }
    return ((Boolean)this.a.get("crashedOnLastLoad")).booleanValue();
  }
  
  public String getRateMyAppMessage()
  {
    if (!this.a.containsKey("message"))
    {
      if (!this.b) {
        break label41;
      }
      dx.c("User has opted out of Crittercism.  Returning null.");
    }
    for (;;)
    {
      return (String)this.a.get("message");
      label41:
      dx.c("CritterUserData instance has no value for getRateMyAppMessage().  Returning null.");
    }
  }
  
  public String getRateMyAppTitle()
  {
    if (!this.a.containsKey("title"))
    {
      if (!this.b) {
        break label41;
      }
      dx.c("User has opted out of Crittercism.  Returning null.");
    }
    for (;;)
    {
      return (String)this.a.get("title");
      label41:
      dx.c("CritterUserData instance has no value for getRateMyAppTitle().  Returning null.");
    }
  }
  
  public String getUserUUID()
  {
    if (!this.a.containsKey("userUUID"))
    {
      if (!this.b) {
        break label41;
      }
      dx.c("User has opted out of Crittercism.  Returning null.");
    }
    for (;;)
    {
      return (String)this.a.get("userUUID");
      label41:
      dx.c("CritterUserData instance has no value for getUserUUID().  Returning null.");
    }
  }
  
  public boolean isOptedOut()
  {
    if (!this.a.containsKey("optOutStatus")) {
      return this.b;
    }
    return ((Boolean)this.a.get("optOutStatus")).booleanValue();
  }
  
  public boolean shouldShowRateMyAppAlert()
  {
    if (!this.a.containsKey("shouldShowRateAppAlert"))
    {
      if (this.b) {
        dx.c("User has opted out of Crittercism.  Returning false.");
      }
      for (;;)
      {
        return false;
        dx.c("CritterUserData instance has no value for shouldShowMyRateAppAlert().  Defaulting to false.");
      }
    }
    return ((Boolean)this.a.get("shouldShowRateAppAlert")).booleanValue();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/crittercism/app/CritterUserData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */