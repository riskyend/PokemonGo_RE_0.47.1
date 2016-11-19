package com.upsight.android.analytics.internal;

import android.content.ContextWrapper;
import android.content.res.Resources;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.R.raw;
import com.upsight.android.logger.UpsightLogger;
import java.io.IOException;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;

public class AnalyticsContext
  extends ContextWrapper
{
  private static final String LOG_TAG = AnalyticsContext.class.getSimpleName();
  private final UpsightContext mUpsight;
  
  @Inject
  public AnalyticsContext(UpsightContext paramUpsightContext)
  {
    super(paramUpsightContext);
    this.mUpsight = paramUpsightContext;
  }
  
  public String getDefaultDispatcherConfiguration()
  {
    try
    {
      String str = IOUtils.toString(getResources().openRawResource(R.raw.dispatcher_config));
      return str;
    }
    catch (IOException localIOException)
    {
      this.mUpsight.getLogger().e(LOG_TAG, "Could not read default configuration.", new Object[] { localIOException });
    }
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/AnalyticsContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */