package com.voxelbusters.nativeplugins.features.reachability;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.voxelbusters.NativeBinding;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.base.interfaces.IAppLifeCycleListener;
import com.voxelbusters.nativeplugins.utilities.Debug;

public class NetworkReachabilityHandler
  implements IAppLifeCycleListener
{
  private static NetworkReachabilityHandler INSTANCE;
  static boolean isSocketConnected = false;
  static boolean isWifiReachable = false;
  ConnectivityListener connectivityListener;
  Context context;
  HostConnectionPoller socketPoller = new HostConnectionPoller();
  
  public static NetworkReachabilityHandler getInstance()
  {
    if (INSTANCE == null) {
      INSTANCE = new NetworkReachabilityHandler();
    }
    return INSTANCE;
  }
  
  public static void sendSocketConnectionStatus(boolean paramBoolean)
  {
    if (isSocketConnected != paramBoolean)
    {
      isSocketConnected = paramBoolean;
      if (!isSocketConnected) {
        break label27;
      }
    }
    label27:
    for (String str = "true";; str = "false")
    {
      NativePluginHelper.sendMessage("NetworkSocketStatusChange", str);
      return;
    }
  }
  
  public static void sendWifiReachabilityStatus(boolean paramBoolean)
  {
    if (isWifiReachable != paramBoolean)
    {
      isWifiReachable = paramBoolean;
      if (!isWifiReachable) {
        break label27;
      }
    }
    label27:
    for (String str = "true";; str = "false")
    {
      NativePluginHelper.sendMessage("NetworkHardwareStatusChange", str);
      return;
    }
  }
  
  void StartSocketPoller(String paramString, int paramInt1, float paramFloat, int paramInt2, int paramInt3)
  {
    this.socketPoller.setIp(paramString);
    this.socketPoller.setPort(paramInt1);
    this.socketPoller.setConnectionTimeOutPeriod(paramInt2);
    this.socketPoller.setMaxRetryCount(paramInt3);
    this.socketPoller.setTimeGapBetweenPolls(paramFloat);
    this.socketPoller.Start();
  }
  
  void StartTestingNetworkHardware()
  {
    pauseReachability();
    this.connectivityListener = new ConnectivityListener();
    registerBroadcastReceiver(this.connectivityListener);
    this.connectivityListener.updateConnectionStatus(this.context);
  }
  
  public void initialize(String paramString, int paramInt1, float paramFloat, int paramInt2, int paramInt3)
  {
    this.context = NativePluginHelper.getCurrentContext();
    StartTestingNetworkHardware();
    StartSocketPoller(paramString, paramInt1, paramFloat, paramInt2, paramInt3);
    NativeBinding.addAppLifeCycleListener(this);
  }
  
  public void onApplicationPause() {}
  
  public void onApplicationQuit()
  {
    pauseReachability();
    NativeBinding.removeAppLifeCycleListener(this);
  }
  
  public void onApplicationResume() {}
  
  public void pauseReachability()
  {
    try
    {
      this.context.unregisterReceiver(this.connectivityListener);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Debug.warning("NativePlugins.NetworkConnectivity", "Already unregistered!" + localIllegalArgumentException.getMessage());
    }
  }
  
  void registerBroadcastReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    IntentFilter localIntentFilter1 = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    IntentFilter localIntentFilter2 = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
    IntentFilter localIntentFilter3 = new IntentFilter("android.net.wifi.STATE_CHANGE");
    this.context.registerReceiver(paramBroadcastReceiver, localIntentFilter1);
    this.context.registerReceiver(paramBroadcastReceiver, localIntentFilter2);
    this.context.registerReceiver(paramBroadcastReceiver, localIntentFilter3);
  }
  
  public void resumeReachability()
  {
    try
    {
      registerBroadcastReceiver(this.connectivityListener);
      this.connectivityListener.updateConnectionStatus(this.context);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Debug.warning("NativePlugins.NetworkConnectivity", "Already registered! " + localIllegalArgumentException.getMessage());
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/nativeplugins/features/reachability/NetworkReachabilityHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */