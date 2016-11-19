package com.upsight.android.marketing;

import org.json.JSONObject;

public abstract interface UpsightReward
{
  public abstract String getProduct();
  
  public abstract int getQuantity();
  
  public abstract JSONObject getSignatureData();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/UpsightReward.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */