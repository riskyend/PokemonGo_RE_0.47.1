package com.upsight.android.analytics.internal;

import android.content.Context;
import com.upsight.android.internal.util.PreferencesHelper;

public final class EventSequenceId
{
  private static final long INITIAL_SEQUENCE_ID = 1L;
  private static final String PREFERENCES_KEY_SEQ_ID = "seq_id";
  
  public static long getAndIncrement(Context paramContext)
  {
    try
    {
      long l = PreferencesHelper.getLong(paramContext, "seq_id", 1L);
      PreferencesHelper.putLong(paramContext, "seq_id", l + 1L);
      return l;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/EventSequenceId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */