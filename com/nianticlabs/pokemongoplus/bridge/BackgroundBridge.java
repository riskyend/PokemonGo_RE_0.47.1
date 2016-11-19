package com.nianticlabs.pokemongoplus.bridge;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class BackgroundBridge
{
  private static final String TAG = BackgroundBridge.class.getSimpleName();
  public static Context currentContext;
  private static BackgroundBridgeMessage.MessageHandler messageHandler;
  private long nativeHandle;
  
  protected BackgroundBridge()
  {
    initialize();
    Log.w(TAG, "Initialize();");
  }
  
  public static BackgroundBridge createBridge(Context paramContext, BackgroundBridgeMessage.MessageHandler paramMessageHandler)
  {
    messageHandler = paramMessageHandler;
    currentContext = paramContext;
    Log.w(TAG, BackgroundBridge.class.toString());
    nativeInit();
    Log.w(TAG, "BackgroundBridge createBridge");
    paramContext = new BackgroundBridge();
    Log.w(TAG, "new BackgroundBridge");
    return paramContext;
  }
  
  private native void initialize();
  
  public static native void nativeInit();
  
  public static void sendBatteryLevel(double paramDouble)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.BATTERY_LEVEL_ACTION).setBatteryLevel(paramDouble));
  }
  
  public static void sendCentralState(int paramInt)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.CENTRAL_STATE_ACTION).setArg1(paramInt));
  }
  
  public static void sendEncounterId(long paramLong)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.ENCOUNTER_ID_ACTION).setEncounterId(paramLong));
  }
  
  public static void sendIsScanning(int paramInt)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.IS_SCANNING_ACTION).setArg1(paramInt));
  }
  
  private static void sendMessage(BackgroundBridgeMessage paramBackgroundBridgeMessage)
  {
    new Handler(currentContext.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        Log.i(BackgroundBridge.TAG, "sendMessage: " + this.val$message.getAction());
        BackgroundBridge.messageHandler.handleMessage(this.val$message);
      }
    });
  }
  
  public static void sendNotification(int paramInt, String paramString)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.SEND_NOTIFICATION_ACTION).setArg1(paramInt).setNotification(paramString));
  }
  
  public static void sendPluginState(int paramInt)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.PLUGIN_STATE_ACTION).setArg1(paramInt));
  }
  
  public static void sendPokestopId(String paramString)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.POKESTOP_ACTION).setPokestopId(paramString));
  }
  
  public static void sendScannedSfida(String paramString, int paramInt)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.SCANNED_SFIDA_ACTION).setArg1(paramInt).setDeviceId(paramString));
  }
  
  public static void sendSfidaState(int paramInt)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.SFIDA_STATE_ACTION).setArg1(paramInt));
  }
  
  public static void sendUpdateTimestamp(long paramLong)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.UPDATE_TIMESTAMP_ACTION).setTimestamp(paramLong));
  }
  
  public static void sendXpGained(int paramInt)
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.XP_GAIN_ACTION).setArg1(paramInt));
  }
  
  public static void stopNotification()
  {
    sendMessage(new BackgroundBridgeMessage().setAction(BackgroundBridgeMessage.Action.STOP_NOTIFICATION_ACTION));
  }
  
  public void destroyBridge()
  {
    dispose();
  }
  
  public native void dispose();
  
  public void onClientMessage(ClientBridgeMessage paramClientBridgeMessage)
  {
    ClientBridgeMessage.Action localAction = paramClientBridgeMessage.getAction();
    Log.i(TAG, "onClientMessage - " + localAction);
    Log.i(TAG, "BackgroundService onClientMessage action = " + localAction);
    switch (localAction)
    {
    default: 
      Log.e(TAG, "Can't handle intent message: " + localAction);
    }
    for (;;)
    {
      Log.i(TAG, "onClientMessage DONE - " + localAction);
      return;
      start();
      continue;
      resume();
      continue;
      pause();
      continue;
      stop();
      continue;
      startScanning();
      continue;
      stopScanning();
      continue;
      String str1 = paramClientBridgeMessage.getHostPort();
      String str2 = paramClientBridgeMessage.getDeviceId();
      byte[] arrayOfByte = paramClientBridgeMessage.getAuthToken();
      long l = paramClientBridgeMessage.getEncounterId();
      int i = paramClientBridgeMessage.getNotifications();
      Log.i(TAG, String.format("Start session: %s %s %d \"%s\"", new Object[] { str1, str2, Long.valueOf(l), "" }));
      startSession(str1, str2, arrayOfByte, l, i);
      continue;
      stopSession();
      continue;
      requestPgpState();
      continue;
      updateNotifications(paramClientBridgeMessage.getNotifications());
    }
  }
  
  public native void pause();
  
  public native void requestPgpState();
  
  public native void resume();
  
  public native void start();
  
  public native void startScanning();
  
  public native void startSession(String paramString1, String paramString2, byte[] paramArrayOfByte, long paramLong, int paramInt);
  
  public native void stop();
  
  public native void stopScanning();
  
  public native void stopSession();
  
  public native void updateNotifications(int paramInt);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/bridge/BackgroundBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */