package com.google.android.gms.gcm;

import android.os.Bundle;

public class TaskParams
{
  private final Bundle extras;
  private final String tag;
  
  public TaskParams(String paramString)
  {
    this(paramString, null);
  }
  
  public TaskParams(String paramString, Bundle paramBundle)
  {
    this.tag = paramString;
    this.extras = paramBundle;
  }
  
  public Bundle getExtras()
  {
    return this.extras;
  }
  
  public String getTag()
  {
    return this.tag;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/gcm/TaskParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */