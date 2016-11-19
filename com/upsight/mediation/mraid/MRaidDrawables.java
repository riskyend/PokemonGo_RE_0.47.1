package com.upsight.mediation.mraid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.upsight.mediation.log.FuseLog;
import java.io.IOException;
import java.io.InputStream;

public class MRaidDrawables
{
  private static final String TAG = "MRaidDrawables";
  
  public static Drawable getDrawableForImage(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    paramString1 = paramContext.getClass().getResourceAsStream(paramString1);
    if (paramString1 != null) {
      paramContext = new BitmapDrawable(paramContext.getResources(), paramString1);
    }
    try
    {
      paramString1.close();
      return paramContext;
    }
    catch (IOException paramString1) {}
    int i = paramContext.getResources().getIdentifier(paramString2, "drawable", paramContext.getPackageName());
    if (i != 0) {
      return paramContext.getResources().getDrawable(i);
    }
    FuseLog.w("MRaidDrawables", "Could not load button image: " + paramString2);
    return new ColorDrawable(paramInt);
    return paramContext;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/MRaidDrawables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */