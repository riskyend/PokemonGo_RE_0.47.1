package com.upsight.android.analytics.internal.dispatcher.schema;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.internal.util.NetworkHelper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DeviceBlockProvider
  extends UpsightDataProvider
{
  public static final String CARRIER_KEY = "device.carrier";
  public static final String CONNECTION_KEY = "device.connection";
  private static final String DEVICE_TYPE_PHONE = "phone";
  private static final String DEVICE_TYPE_TABLET = "tablet";
  public static final String HARDWARE_KEY = "device.hardware";
  public static final String JAILBROKEN_KEY = "device.jailbroken";
  private static final String KERNEL_BUILD_KEY_TEST = "test-keys";
  public static final String LIMITED_AD_TRACKING_KEY = "device.limit_ad_tracking";
  public static final String MANUFACTURER_KEY = "device.manufacturer";
  private static final String OS_ANDROID = "android";
  public static final String OS_KEY = "device.os";
  public static final String OS_VERSION_KEY = "device.os_version";
  private static final String SPACE = " ";
  public static final String TYPE_KEY = "device.type";
  private final Bus mBus;
  
  DeviceBlockProvider(UpsightContext paramUpsightContext)
  {
    this.mBus = paramUpsightContext.getCoreComponent().bus();
    this.mBus.register(this);
    put("device.carrier", NetworkHelper.getNetworkOperatorName(paramUpsightContext));
    put("device.connection", NetworkHelper.getActiveNetworkType(paramUpsightContext));
    put("device.hardware", Build.MODEL);
    put("device.jailbroken", Boolean.valueOf(isRooted()));
    put("device.manufacturer", Build.MANUFACTURER);
    put("device.os", "android");
    put("device.os_version", Build.VERSION.RELEASE + " " + Build.VERSION.SDK_INT);
    put("device.type", getDeviceType(paramUpsightContext));
  }
  
  private String getDeviceType(Context paramContext)
  {
    String str = "phone";
    if ((paramContext.getResources().getConfiguration().screenLayout & 0xF) >= 3) {
      str = "tablet";
    }
    return str;
  }
  
  private boolean isRooted()
  {
    String str = Build.TAGS;
    return (str != null) && (str.contains("test-keys"));
  }
  
  public Set<String> availableKeys()
  {
    return new HashSet(Arrays.asList(new String[] { "device.os", "device.os_version", "device.type", "device.hardware", "device.manufacturer", "device.carrier", "device.connection", "device.jailbroken" }));
  }
  
  @Subscribe
  public void onNetworkChangeEvent(NetworkChangeEvent paramNetworkChangeEvent)
  {
    put("device.carrier", paramNetworkChangeEvent.networkOperatorName);
    put("device.connection", paramNetworkChangeEvent.activeNetworkType);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/DeviceBlockProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */