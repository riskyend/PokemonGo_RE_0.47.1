package com.upsight.android.analytics.internal.association;

import com.google.gson.JsonObject;

public abstract interface AssociationManager
{
  public abstract void associate(String paramString, JsonObject paramJsonObject);
  
  public abstract void launch();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/association/AssociationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */