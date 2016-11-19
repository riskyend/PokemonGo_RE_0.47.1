package com.upsight.android.analytics.provider;

import android.content.Context;
import com.upsight.android.UpsightContext;

public class UpsightSessionContext
{
  private UpsightContext mUpsight;
  
  public UpsightSessionContext(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  public Context getApplicationContext()
  {
    return this.mUpsight.getApplicationContext();
  }
  
  UpsightContext getUpsightContext()
  {
    return this.mUpsight;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/provider/UpsightSessionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */