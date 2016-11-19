package com.upsight.mediation.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

public class StringUtil
{
  public static boolean isNullOrEmpty(@Nullable String paramString)
  {
    return (paramString == null) || (paramString.trim().equals("")) || (paramString.length() == 0);
  }
  
  @NonNull
  public static String join(@NonNull List paramList, @NonNull String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int j = paramList.size();
    int i = 0;
    while (i < j)
    {
      localStringBuilder.append(paramList.get(i));
      if (i < j - 1) {
        localStringBuilder.append(paramString);
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  @NonNull
  public static String join(@NonNull int[] paramArrayOfInt, @NonNull String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int j = paramArrayOfInt.length;
    int i = 0;
    while (i < j)
    {
      localStringBuilder.append(paramArrayOfInt[i]);
      if (i < j - 1) {
        localStringBuilder.append(paramString);
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  @NonNull
  public static String join(@NonNull String[] paramArrayOfString, @NonNull String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int j = paramArrayOfString.length;
    int i = 0;
    while (i < j)
    {
      localStringBuilder.append(paramArrayOfString[i]);
      if (i < j - 1) {
        localStringBuilder.append(paramString);
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  @NonNull
  public static int[] toIntArray(@NonNull String paramString1, @NonNull String paramString2)
  {
    paramString1 = paramString1.split(paramString2);
    int j = paramString1.length;
    paramString2 = new int[j];
    int i = 0;
    while (i < j)
    {
      paramString2[i] = Integer.parseInt(paramString1[i]);
      i += 1;
    }
    return paramString2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/util/StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */