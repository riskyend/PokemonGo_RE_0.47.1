package com.google.android.exoplayer.util;

import android.annotation.TargetApi;
import android.os.Trace;

public final class TraceUtil
{
  public static void beginSection(String paramString)
  {
    if (Util.SDK_INT >= 18) {
      beginSectionV18(paramString);
    }
  }
  
  @TargetApi(18)
  private static void beginSectionV18(String paramString)
  {
    Trace.beginSection(paramString);
  }
  
  public static void endSection()
  {
    if (Util.SDK_INT >= 18) {
      endSectionV18();
    }
  }
  
  @TargetApi(18)
  private static void endSectionV18() {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/util/TraceUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */