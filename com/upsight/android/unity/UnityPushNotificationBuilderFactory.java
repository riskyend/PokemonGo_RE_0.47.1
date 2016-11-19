package com.upsight.android.unity;

import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat.Builder;
import com.upsight.android.UpsightContext;
import com.upsight.android.googlepushservices.UpsightPushNotificationBuilderFactory.Default;

public class UnityPushNotificationBuilderFactory
  extends UpsightPushNotificationBuilderFactory.Default
{
  private static final String NOTIFICATION_ICON_RES_NAME = "upsight_notification_icon";
  private static final String NOTIFICATION_ICON_RES_TYPE = "drawable";
  
  public NotificationCompat.Builder getNotificationBuilder(UpsightContext paramUpsightContext, String paramString1, String paramString2, String paramString3)
  {
    String str = paramUpsightContext.getPackageName();
    int j = paramUpsightContext.getResources().getIdentifier("upsight_notification_icon", "drawable", str);
    int i = j;
    if (j == 0) {
      i = paramUpsightContext.getApplicationInfo().icon;
    }
    return super.getNotificationBuilder(paramUpsightContext, paramString1, paramString2, paramString3).setSmallIcon(i);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/UnityPushNotificationBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */