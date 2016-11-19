package com.voxelbusters.nativeplugins.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.json.JSONArray;

public class SharedPreferencesUtility
{
  public static JSONArray getJsonArray(String paramString1, int paramInt, Context paramContext, String paramString2)
  {
    return JSONUtility.getJSONArray(getSharedPreferences(paramString1, paramInt, paramContext).getString(paramString2, ""));
  }
  
  public static SharedPreferences getSharedPreferences(String paramString, int paramInt, Context paramContext)
  {
    return paramContext.getSharedPreferences(paramString, paramInt);
  }
  
  public static void setJSONArray(String paramString1, int paramInt, Context paramContext, String paramString2, JSONArray paramJSONArray)
  {
    paramString1 = getSharedPreferences(paramString1, paramInt, paramContext).edit();
    paramString1.putString(paramString2, paramJSONArray.toString());
    paramString1.commit();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/nativeplugins/utilities/SharedPreferencesUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */