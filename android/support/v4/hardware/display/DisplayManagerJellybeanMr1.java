package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;

final class DisplayManagerJellybeanMr1
{
  public static Display getDisplay(Object paramObject, int paramInt)
  {
    return ((DisplayManager)paramObject).getDisplay(paramInt);
  }
  
  public static Object getDisplayManager(Context paramContext)
  {
    return paramContext.getSystemService("display");
  }
  
  public static Display[] getDisplays(Object paramObject)
  {
    return ((DisplayManager)paramObject).getDisplays();
  }
  
  public static Display[] getDisplays(Object paramObject, String paramString)
  {
    return ((DisplayManager)paramObject).getDisplays(paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/hardware/display/DisplayManagerJellybeanMr1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */