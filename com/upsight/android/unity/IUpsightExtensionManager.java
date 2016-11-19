package com.upsight.android.unity;

import com.upsight.android.UpsightContext;

public abstract interface IUpsightExtensionManager
{
  public abstract void init(UpsightContext paramUpsightContext);
  
  public abstract void onApplicationPaused();
  
  public abstract void onApplicationResumed();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/IUpsightExtensionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */