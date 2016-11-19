package com.upsight.mediation.mraid.internal;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.List;

public class MRAIDNativeFeatureManager
{
  private static final String TAG = "MRAIDNativeFeatureManager";
  private Context context;
  private List<String> supportedNativeFeatures;
  
  public MRAIDNativeFeatureManager(Context paramContext, List<String> paramList)
  {
    this.context = paramContext;
    this.supportedNativeFeatures = paramList;
  }
  
  public boolean isCalendarSupported()
  {
    if ((this.supportedNativeFeatures.contains("calendar")) && (Build.VERSION.SDK_INT >= 14) && (this.context.checkCallingOrSelfPermission("android.permission.WRITE_CALENDAR") == 0)) {}
    for (boolean bool = true;; bool = false)
    {
      MRAIDLog.v("MRAIDNativeFeatureManager", "isCalendarSupported " + bool);
      return bool;
    }
  }
  
  public boolean isInlineVideoSupported()
  {
    boolean bool = this.supportedNativeFeatures.contains("inlineVideo");
    MRAIDLog.v("MRAIDNativeFeatureManager", "isInlineVideoSupported " + bool);
    return bool;
  }
  
  public boolean isSmsSupported()
  {
    if ((this.supportedNativeFeatures.contains("sms")) && (this.context.checkCallingOrSelfPermission("android.permission.SEND_SMS") == 0)) {}
    for (boolean bool = true;; bool = false)
    {
      MRAIDLog.v("MRAIDNativeFeatureManager", "isSmsSupported " + bool);
      return bool;
    }
  }
  
  public boolean isStorePictureSupported()
  {
    boolean bool = this.supportedNativeFeatures.contains("storePicture");
    MRAIDLog.v("MRAIDNativeFeatureManager", "isStorePictureSupported " + bool);
    return bool;
  }
  
  public boolean isTelSupported()
  {
    if ((this.supportedNativeFeatures.contains("tel")) && (this.context.checkCallingOrSelfPermission("android.permission.CALL_PHONE") == 0)) {}
    for (boolean bool = true;; bool = false)
    {
      MRAIDLog.v("MRAIDNativeFeatureManager", "isTelSupported " + bool);
      return bool;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/internal/MRAIDNativeFeatureManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */