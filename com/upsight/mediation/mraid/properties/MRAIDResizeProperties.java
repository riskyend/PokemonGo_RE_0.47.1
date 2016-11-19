package com.upsight.mediation.mraid.properties;

import java.util.Arrays;
import java.util.List;

public final class MRAIDResizeProperties
{
  public static final int CUSTOM_CLOSE_POSITION_BOTTOM_CENTER = 5;
  public static final int CUSTOM_CLOSE_POSITION_BOTTOM_LEFT = 4;
  public static final int CUSTOM_CLOSE_POSITION_BOTTOM_RIGHT = 6;
  public static final int CUSTOM_CLOSE_POSITION_CENTER = 3;
  public static final int CUSTOM_CLOSE_POSITION_TOP_CENTER = 1;
  public static final int CUSTOM_CLOSE_POSITION_TOP_LEFT = 0;
  public static final int CUSTOM_CLOSE_POSITION_TOP_RIGHT = 2;
  public boolean allowOffscreen;
  public int customClosePosition;
  public int height;
  public int offsetX;
  public int offsetY;
  public int width;
  
  public MRAIDResizeProperties()
  {
    this(0, 0, 0, 0, 2, true);
  }
  
  public MRAIDResizeProperties(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.offsetX = paramInt3;
    this.offsetY = paramInt4;
    this.customClosePosition = paramInt5;
    this.allowOffscreen = paramBoolean;
  }
  
  public static int customClosePositionFromString(String paramString)
  {
    int i = Arrays.asList(new String[] { "top-left", "top-center", "top-right", "center", "bottom-left", "bottom-center", "bottom-right" }).indexOf(paramString);
    if (i != -1) {
      return i;
    }
    return 2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/properties/MRAIDResizeProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */