package com.upsight.android.internal.logger;

import com.upsight.android.logger.UpsightLogger.Level;

public abstract interface LogWriter
{
  public abstract void write(String paramString1, UpsightLogger.Level paramLevel, String paramString2);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/logger/LogWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */