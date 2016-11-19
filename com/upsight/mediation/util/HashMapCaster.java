package com.upsight.mediation.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.upsight.mediation.data.APIVersion;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HashMapCaster
{
  private final HashMap<String, String> map;
  
  public HashMapCaster(HashMap<String, String> paramHashMap)
  {
    this.map = paramHashMap;
  }
  
  @Nullable
  public String get(String paramString)
  {
    return (String)this.map.get(paramString);
  }
  
  @Nullable
  public APIVersion getAPIVersion(String paramString)
  {
    paramString = (String)this.map.get(paramString);
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    return new APIVersion(paramString);
  }
  
  @NonNull
  public ArrayList<APIVersion> getAPIVersions(String paramString)
  {
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    paramString = (String)this.map.get(paramString);
    if (paramString.length() > 0) {}
    for (paramString = paramString.split(",");; paramString = new String[0])
    {
      int j = paramString.length;
      while (i < j)
      {
        localArrayList.add(new APIVersion(paramString[i]));
        i += 1;
      }
    }
    return localArrayList;
  }
  
  public boolean getBool(String paramString)
  {
    paramString = (String)this.map.get(paramString);
    if (paramString != null) {
      return paramString.equals("1");
    }
    return false;
  }
  
  @Nullable
  public Date getDate(String paramString)
  {
    paramString = (String)this.map.get(paramString);
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    return new Date(1000L * Long.parseLong(paramString));
  }
  
  public float getFloat(String paramString)
  {
    return getFloat(paramString, -1.0F);
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    paramString = (String)this.map.get(paramString);
    float f = paramFloat;
    if (paramString != null)
    {
      f = paramFloat;
      if (paramString.length() > 0) {
        f = Float.parseFloat(paramString);
      }
    }
    return f;
  }
  
  public int getInt(String paramString)
  {
    return getInt(paramString, -1);
  }
  
  public int getInt(String paramString, int paramInt)
  {
    String str = (String)this.map.get(paramString);
    int i = paramInt;
    if (str != null)
    {
      i = paramInt;
      if (str.length() > 0) {
        i = Integer.parseInt((String)this.map.get(paramString));
      }
    }
    return i;
  }
  
  public int[] getIntArray(String paramString)
  {
    paramString = (String)this.map.get(paramString);
    if (paramString.length() > 0) {}
    int[] arrayOfInt;
    for (paramString = paramString.split(",");; paramString = new String[0])
    {
      arrayOfInt = new int[paramString.length];
      int i = 0;
      while (i < arrayOfInt.length)
      {
        arrayOfInt[i] = Integer.parseInt(paramString[i]);
        i += 1;
      }
    }
    return arrayOfInt;
  }
  
  public long getLong(String paramString)
  {
    paramString = (String)this.map.get(paramString);
    if ((paramString != null) && (paramString.length() > 0)) {
      return Long.parseLong(paramString);
    }
    return -1L;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/util/HashMapCaster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */