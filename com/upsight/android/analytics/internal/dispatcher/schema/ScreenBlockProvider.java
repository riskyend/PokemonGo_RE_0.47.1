package com.upsight.android.analytics.internal.dispatcher.schema;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.logger.UpsightLogger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ScreenBlockProvider
  extends UpsightDataProvider
{
  private static final float ANDROID_SCREEN_SCALE = 1.0F;
  public static final String DPI_KEY = "screen.dpi";
  public static final String HEIGHT_KEY = "screen.height";
  public static final String SCALE_KEY = "screen.scale";
  public static final String WIDTH_KEY = "screen.width";
  private final UpsightLogger mLogger;
  
  ScreenBlockProvider(UpsightContext paramUpsightContext)
  {
    put("screen.scale", Float.valueOf(1.0F));
    this.mLogger = paramUpsightContext.getLogger();
    paramUpsightContext = getDefaultDisplayMetrics(paramUpsightContext);
    if (paramUpsightContext != null)
    {
      put("screen.dpi", Integer.valueOf(paramUpsightContext.densityDpi));
      put("screen.width", Integer.valueOf(paramUpsightContext.widthPixels));
      put("screen.height", Integer.valueOf(paramUpsightContext.heightPixels));
    }
  }
  
  private DisplayMetrics getDefaultDisplayMetrics(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramContext = (WindowManager)paramContext.getSystemService("window");
    if (paramContext != null)
    {
      paramContext = paramContext.getDefaultDisplay();
      if (paramContext != null) {
        paramContext.getMetrics(localDisplayMetrics);
      }
      return localDisplayMetrics;
    }
    this.mLogger.e("ScreenBlock", "Could not retrieve screen size, windowManager=null", new Object[0]);
    return localDisplayMetrics;
  }
  
  public Set<String> availableKeys()
  {
    return new HashSet(Arrays.asList(new String[] { "screen.width", "screen.height", "screen.scale", "screen.dpi" }));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/ScreenBlockProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */