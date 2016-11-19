package com.google.android.exoplayer.util;

public final class VerboseLogUtil
{
  private static volatile boolean enableAllTags;
  private static volatile String[] enabledTags;
  
  public static boolean areAllTagsEnabled()
  {
    return enableAllTags;
  }
  
  public static boolean isTagEnabled(String paramString)
  {
    if (enableAllTags) {
      return true;
    }
    String[] arrayOfString = enabledTags;
    if ((arrayOfString == null) || (arrayOfString.length == 0)) {
      return false;
    }
    int i = 0;
    for (;;)
    {
      if (i >= arrayOfString.length) {
        break label48;
      }
      if (arrayOfString[i].equals(paramString)) {
        break;
      }
      i += 1;
    }
    label48:
    return false;
  }
  
  public static void setEnableAllTags(boolean paramBoolean)
  {
    enableAllTags = paramBoolean;
  }
  
  public static void setEnabledTags(String... paramVarArgs)
  {
    enabledTags = paramVarArgs;
    enableAllTags = false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/util/VerboseLogUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */