package com.nianticlabs.pokemongoplus.bridge;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.nianticlabs.pokemongoplus.service.BackgroundService;
import java.util.ArrayList;
import java.util.List;

public class ClientBridge
  implements ServiceConnection
{
  private static final String TAG = ClientBridge.class.getSimpleName();
  private static Context currentContext;
  private static ClientBridge instance;
  private final List<BackgroundBridgeMessage.MessageHandler> listeners = new ArrayList();
  LoginDelegate loginDelegate;
  private Messenger messenger;
  private long nativeHandle;
  private Messenger replyMessenger;
  SfidaRegisterDelegate sfidaRegisterDelegate;
  
  private ClientBridge()
  {
    Log.i(TAG, "Initialize();");
    initialize();
  }
  
  public static ClientBridge createBridge(Context paramContext)
  {
    currentContext = paramContext;
    return getInstance();
  }
  
  public static ClientBridge getInstance()
  {
    if (instance == null)
    {
      Log.i(TAG, "Create ClientBridge");
      nativeInit();
      instance = new ClientBridge();
    }
    return instance;
  }
  
  private native void initialize();
  
  public static native void nativeInit();
  
  private void onBackgroundMessage(BackgroundBridgeMessage paramBackgroundBridgeMessage)
  {
    switch (paramBackgroundBridgeMessage.getAction())
    {
    default: 
      Log.e(TAG, "Can't handle intent message: " + paramBackgroundBridgeMessage.getAction());
      return;
    case ???: 
      sendUpdateTimestamp(paramBackgroundBridgeMessage.getTimestamp());
      return;
    case ???: 
      sendSfidaState(paramBackgroundBridgeMessage.message.arg1);
      return;
    case ???: 
      sendEncounterId(paramBackgroundBridgeMessage.getEncounterId());
      return;
    case ???: 
      sendPokestopId(paramBackgroundBridgeMessage.getPokestopId());
      return;
    case ???: 
      sendCentralState(paramBackgroundBridgeMessage.message.arg1);
      return;
    case ???: 
      i = paramBackgroundBridgeMessage.message.arg1;
      sendScannedSfida(paramBackgroundBridgeMessage.getDeviceId(), i);
      return;
    case ???: 
      sendPluginState(paramBackgroundBridgeMessage.message.arg1);
      return;
    case ???: 
      sendIsScanning(paramBackgroundBridgeMessage.message.arg1);
      return;
    case ???: 
      sendXpGained(paramBackgroundBridgeMessage.message.arg1);
      return;
    case ???: 
      sendBatteryLevel(paramBackgroundBridgeMessage.getBatteryLevel());
      return;
    case ???: 
      Log.i(TAG, "Confirmed bridge shut down");
      return;
    }
    int i = paramBackgroundBridgeMessage.getArg1();
    Log.i(TAG, String.format("Notification received: %d", new Object[] { Integer.valueOf(i) }));
  }
  
  private static void sendMessage(ClientBridgeMessage paramClientBridgeMessage)
  {
    if (instance.messenger == null)
    {
      Log.e(TAG, String.format("Service is not connected yet. Action failed: %s", new Object[] { paramClientBridgeMessage.getAction() }));
      return;
    }
    new Handler(currentContext.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        try
        {
          Log.i(ClientBridge.TAG, String.format("sendMessage: action:%s thread:%s", new Object[] { this.val$message.getAction(), Thread.currentThread().getName() }));
          this.val$message.message.replyTo = ClientBridge.instance.replyMessenger;
          ClientBridge.instance.messenger.send(this.val$message.message);
          return;
        }
        catch (RemoteException localRemoteException)
        {
          localRemoteException.printStackTrace();
        }
      }
    });
  }
  
  public static void sendPause()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.PAUSE_ACTION));
  }
  
  public static void sendRequestPgpState()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.REQUEST_PGP_STATE));
  }
  
  public static void sendResume()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.RESUME_ACTION));
  }
  
  public static void sendStart()
  {
    Log.i(TAG, "sendStart PROCESS_LOCAL_VALUE = " + BackgroundService.PROCESS_LOCAL_VALUE);
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.START_ACTION));
  }
  
  public static void sendStartScanning()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.START_SCANNING_ACTION));
  }
  
  public static void sendStartSession(String paramString1, String paramString2, byte[] paramArrayOfByte, long paramLong, int paramInt)
  {
    Log.i(TAG, String.format("send startSession intent %s %s %s %d", new Object[] { paramString1, paramString2, paramArrayOfByte, Long.valueOf(paramLong) }));
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.START_SESSION_ACTION).setDeviceId(paramString2).setEncounterId(paramLong).setHostPort(paramString1).setAuthToken(paramArrayOfByte).setNotifications(paramInt));
  }
  
  public static void sendStop()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.STOP_ACTION));
  }
  
  public static void sendStopScanning()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.STOP_SCANNING_ACTION));
  }
  
  public static void sendStopSession()
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.STOP_SESSION_ACTION));
  }
  
  public static void sendUpdateNotifications(int paramInt)
  {
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.UPDATE_NOTIFICATIONS).setNotifications(paramInt));
  }
  
  public static void shutdownBackgroundBridge()
  {
    Log.i(TAG, "shutdown background bridge PROCESS_LOCAL_VALUE = " + BackgroundService.PROCESS_LOCAL_VALUE);
    sendMessage(new ClientBridgeMessage().setAction(ClientBridgeMessage.Action.SHUTDOWN_ACTION));
  }
  
  public void addListener(BackgroundBridgeMessage.MessageHandler paramMessageHandler)
  {
    this.listeners.add(paramMessageHandler);
  }
  
  public native void connectDevice(String paramString, int paramInt);
  
  public native void disconnectDevice();
  
  public native void dispose();
  
  public native void login();
  
  public native void logout();
  
  public void onLogin(boolean paramBoolean)
  {
    if (this.loginDelegate != null)
    {
      this.loginDelegate.onLogin(paramBoolean);
      this.loginDelegate = null;
    }
  }
  
  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    Log.i(TAG, "onServiceConnected");
    this.messenger = new Messenger(paramIBinder);
    this.replyMessenger = new Messenger(new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Log.i(ClientBridge.TAG, "Received a message: what:" + paramAnonymousMessage.what + " tid:" + Thread.currentThread().getName());
        paramAnonymousMessage = new BackgroundBridgeMessage(paramAnonymousMessage);
        ClientBridge.this.onBackgroundMessage(paramAnonymousMessage);
        int i = 0;
        while (i < ClientBridge.this.listeners.size())
        {
          ((BackgroundBridgeMessage.MessageHandler)ClientBridge.this.listeners.get(i)).handleMessage(paramAnonymousMessage);
          i += 1;
        }
      }
    });
  }
  
  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    Log.i(TAG, "onServiceDisconnected");
    this.messenger = null;
    this.replyMessenger = null;
  }
  
  public void onSfidaRegistered(boolean paramBoolean, String paramString)
  {
    if (this.sfidaRegisterDelegate != null)
    {
      this.sfidaRegisterDelegate.onSfidaRegistered(paramBoolean, paramString);
      this.sfidaRegisterDelegate = null;
    }
  }
  
  public native void pausePlugin();
  
  public native void registerDevice(String paramString);
  
  public void removeListener(BackgroundBridgeMessage.MessageHandler paramMessageHandler)
  {
    this.listeners.remove(paramMessageHandler);
  }
  
  public native void requestPgpState();
  
  public native void resumePlugin();
  
  public native void sendBatteryLevel(double paramDouble);
  
  public native void sendCentralState(int paramInt);
  
  public native void sendEncounterId(long paramLong);
  
  public native void sendIsScanning(int paramInt);
  
  public native void sendPluginState(int paramInt);
  
  public native void sendPokestopId(String paramString);
  
  public native void sendScannedSfida(String paramString, int paramInt);
  
  public native void sendSfidaState(int paramInt);
  
  public native void sendUpdateTimestamp(long paramLong);
  
  public native void sendXpGained(int paramInt);
  
  public native void standaloneInit(long paramLong);
  
  public void standaloneLogin(LoginDelegate paramLoginDelegate)
  {
    this.loginDelegate = paramLoginDelegate;
    login();
  }
  
  public void standaloneSfidaRegister(String paramString, SfidaRegisterDelegate paramSfidaRegisterDelegate)
  {
    this.sfidaRegisterDelegate = paramSfidaRegisterDelegate;
    registerDevice(paramString);
  }
  
  public native void standaloneUpdate();
  
  public native void startPlugin();
  
  public native void startScanning();
  
  public native void stopPlugin();
  
  public native void stopScanning();
  
  public static abstract interface LoginDelegate
  {
    public abstract void onLogin(boolean paramBoolean);
  }
  
  public static abstract interface SfidaRegisterDelegate
  {
    public abstract void onSfidaRegistered(boolean paramBoolean, String paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/bridge/ClientBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */