package com.upsight.mediation.mraid.properties;

import java.util.Arrays;
import java.util.List;

public final class MRAIDOrientationProperties
{
  public static final int FORCE_ORIENTATION_LANDSCAPE = 1;
  public static final int FORCE_ORIENTATION_NONE = 2;
  public static final int FORCE_ORIENTATION_PORTRAIT = 0;
  public boolean allowOrientationChange;
  public int forceOrientation;
  
  public MRAIDOrientationProperties()
  {
    this(true, 2);
  }
  
  public MRAIDOrientationProperties(boolean paramBoolean, int paramInt)
  {
    this.allowOrientationChange = paramBoolean;
    this.forceOrientation = paramInt;
  }
  
  public static int forceOrientationFromString(String paramString)
  {
    int i = Arrays.asList(new String[] { "portrait", "landscape", "none" }).indexOf(paramString);
    if (i != -1) {
      return i;
    }
    return 2;
  }
  
  public String forceOrientationString()
  {
    switch (this.forceOrientation)
    {
    default: 
      return "error";
    case 0: 
      return "portrait";
    case 1: 
      return "landscape";
    }
    return "none";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/properties/MRAIDOrientationProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */