package com.nianticlabs.pokemongoplus.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Action.Builder;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.RemoteViews;
import com.nianticlabs.pokemongoplus.R.drawable;
import com.nianticlabs.pokemongoplus.R.id;
import com.nianticlabs.pokemongoplus.R.layout;
import com.nianticlabs.pokemongoplus.R.string;
import com.nianticlabs.pokemongoplus.bridge.BackgroundBridge;
import com.nianticlabs.pokemongoplus.bridge.BackgroundBridgeMessage;
import com.nianticlabs.pokemongoplus.bridge.BackgroundBridgeMessage.MessageHandler;
import com.nianticlabs.pokemongoplus.bridge.BridgeConstants.PgpState;
import com.nianticlabs.pokemongoplus.bridge.BridgeConstants.SfidaState;
import com.nianticlabs.pokemongoplus.bridge.ClientBridgeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BackgroundService
  extends Service
{
  public static int PROCESS_LOCAL_VALUE = new Random().nextInt();
  private static final String STOP_FROM_NOTIFICATION = "stopFromNotif";
  private static final String TAG;
  private static final int intentRequestCode = 1000;
  private static final int kCapturedPokemon = 1;
  private static final int kEmptyMessage = 0;
  private static final int kItemInventoryFull = 9;
  private static final int kOutOfPokeballs = 7;
  private static final int kPokemonEscaped = 2;
  private static final int kPokemonInventoryFull = 8;
  private static final int kPokestopCooldown = 6;
  private static final int kPokestopOutOfRange = 5;
  private static final int kRetrievedItems = 4;
  private static final int kRetrievedOneItem = 3;
  private static final int kSessionEnded = 12;
  private static final int kTrackedPokemonFound = 10;
  private static final int kTrackedPokemonLost = 11;
  private static final int notificationId = 2000;
  private static final Map<Integer, Integer> notificationMap = new HashMap();
  private static boolean serviceStopped = false;
  private double batteryLevel;
  private RemoteViews contentView;
  private boolean isScanning;
  private BackgroundBridgeMessage.MessageHandler messageHandler;
  private Messenger messenger;
  private BackgroundBridge pgpBackgroundBridge = null;
  private BridgeConstants.PgpState pluginState;
  private Messenger replyMessenger;
  private BridgeConstants.SfidaState sfidaState;
  
  static
  {
    notificationMap.put(Integer.valueOf(1), Integer.valueOf(R.string.Captured_Pokemon));
    notificationMap.put(Integer.valueOf(2), Integer.valueOf(R.string.Pokemon_Escaped));
    notificationMap.put(Integer.valueOf(3), Integer.valueOf(R.string.Retrieved_an_Item));
    notificationMap.put(Integer.valueOf(4), Integer.valueOf(R.string.Retrieved_Items));
    notificationMap.put(Integer.valueOf(5), Integer.valueOf(R.string.Pokestop_Out_Of_Range));
    notificationMap.put(Integer.valueOf(6), Integer.valueOf(R.string.Pokestop_Cooldown));
    notificationMap.put(Integer.valueOf(7), Integer.valueOf(R.string.Out_Of_Pokeballs));
    notificationMap.put(Integer.valueOf(8), Integer.valueOf(R.string.Pokemon_Inventory_Full));
    notificationMap.put(Integer.valueOf(9), Integer.valueOf(R.string.Item_Inventory_Full));
    notificationMap.put(Integer.valueOf(10), Integer.valueOf(R.string.Tracked_Pokemon_Found));
    notificationMap.put(Integer.valueOf(11), Integer.valueOf(R.string.Tracked_Pokemon_Lost));
    notificationMap.put(Integer.valueOf(12), Integer.valueOf(R.string.Session_Ended));
    TAG = BackgroundService.class.getSimpleName();
  }
  
  private void createNewStylePlayerNotification(String paramString)
  {
    Log.i(TAG, "BackgroundService createNewStylePlayerNotification message = " + paramString);
    Object localObject = createStopSelfPendingIntent();
    localObject = new NotificationCompat.Action.Builder(R.drawable.ic_close_black_24dp, getResources().getString(R.string.Stop_button_label), (PendingIntent)localObject).build();
    finishPlayerNotificationCreation(new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_pgp_white).setContentTitle(getResources().getString(R.string.Pokemon_Go_Plus)).setContentText(paramString).setPriority(2).setVisibility(1).addAction((NotificationCompat.Action)localObject));
  }
  
  private void createOldStylePlayerNotification(String paramString)
  {
    Log.i(TAG, "BackgroundService createOldStylePlayerNotification message = " + paramString);
    PendingIntent localPendingIntent = createStopSelfPendingIntent();
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_pgp_white).setPriority(2).setVisibility(1);
    this.contentView = new RemoteViews(getPackageName(), R.layout.pgp_status_notif);
    this.contentView.setOnClickPendingIntent(R.id.stopPgp, localPendingIntent);
    this.contentView.setTextViewText(R.id.pgpStatusTitle, getResources().getString(R.string.Pokemon_Go_Plus));
    this.contentView.setTextViewText(R.id.pgpStatusDetail, paramString);
    localBuilder.setContent(this.contentView);
    finishPlayerNotificationCreation(localBuilder);
  }
  
  private void createPlayerNotification(String paramString)
  {
    if (Build.VERSION.SDK_INT > 20)
    {
      createNewStylePlayerNotification(paramString);
      return;
    }
    createOldStylePlayerNotification(paramString);
  }
  
  private PendingIntent createStopSelfPendingIntent()
  {
    Intent localIntent = new Intent(this, BackgroundService.class);
    localIntent.setPackage("com.nianticlabs.pokemongoplus.bridge");
    localIntent.putExtra("action", "stopFromNotif");
    return PendingIntent.getService(this, 0, localIntent, 134217728);
  }
  
  private void finishPlayerNotificationCreation(NotificationCompat.Builder paramBuilder)
  {
    paramBuilder.setContentIntent(PendingIntent.getActivity(this, 1000, Intent.makeMainActivity(new ComponentName(this, GetLauncherActivity(this))), 134217728));
    startForeground(2000, paramBuilder.build());
  }
  
  private void forceStopService()
  {
    stopPlayerNotification();
    stopSelf();
  }
  
  private String formatNotification(int paramInt, String paramString)
  {
    Integer localInteger = (Integer)notificationMap.get(Integer.valueOf(paramInt));
    if (localInteger != null) {
      return String.format(getResources().getString(localInteger.intValue()), new Object[] { paramString });
    }
    return "";
  }
  
  private void onHandleIntent(Intent paramIntent)
  {
    if (paramIntent == null)
    {
      Log.w(TAG, "BackgroundService onHandleIntent (intent == null)");
      return;
    }
    paramIntent = paramIntent.getStringExtra("action");
    if (paramIntent == null)
    {
      Log.w(TAG, "BackgroundService onHandleIntent (action == null)");
      return;
    }
    Log.i(TAG, "BackgroundService onHandleBridgedIntent action = " + paramIntent);
    int i = -1;
    switch (paramIntent.hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        Log.e(TAG, String.format("Unknown intent passed to BackgroundService: %s", new Object[] { paramIntent }));
        return;
        if (paramIntent.equals("stopFromNotif")) {
          i = 0;
        }
        break;
      }
    }
    stopBackgroundService();
  }
  
  private static void sendClientIntent(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, ClientService.class);
    localIntent.setPackage("com.nianticlabs.pokemongoplus.bridge");
    localIntent.putExtra("action", paramString);
    paramContext.startService(localIntent);
  }
  
  private void stopBackgroundService()
  {
    if ((this.sfidaState != BridgeConstants.SfidaState.Disconnecting) && (this.sfidaState != BridgeConstants.SfidaState.Disconnected))
    {
      if (this.contentView != null) {
        this.contentView.setBoolean(R.id.stopPgp, "setEnabled", false);
      }
      if (this.pgpBackgroundBridge != null)
      {
        createPlayerNotification(getResources().getString(R.string.Disconnecting_GO_Plus));
        this.pgpBackgroundBridge.stopSession();
        return;
      }
      stopPlayerNotification();
      return;
    }
    stopPlayerNotification();
  }
  
  private void stopPlayerNotification()
  {
    Log.i(TAG, String.format("stopping notification", new Object[0]));
    this.contentView = null;
    stopForeground(true);
  }
  
  private void updateBatteryLevel(double paramDouble)
  {
    this.batteryLevel = paramDouble;
  }
  
  private void updateNotificationForSfidaState(BridgeConstants.SfidaState paramSfidaState1, BridgeConstants.SfidaState paramSfidaState2)
  {
    Log.e(TAG, "New state: " + paramSfidaState1.toString());
    if (paramSfidaState1 != paramSfidaState2) {}
    switch (paramSfidaState1)
    {
    case ???: 
    default: 
    case ???: 
      do
      {
        return;
      } while (paramSfidaState2 != BridgeConstants.SfidaState.Disconnecting);
      stopPlayerNotification();
      return;
    }
    createPlayerNotification("");
  }
  
  public Class<?> GetLauncherActivity(Context paramContext)
  {
    Object localObject = paramContext.getPackageName();
    paramContext = paramContext.getPackageManager().getLaunchIntentForPackage((String)localObject).getComponent().getClassName();
    try
    {
      localObject = Class.forName(paramContext);
      return (Class<?>)localObject;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      Log.e(TAG, "Launcher class not found: " + paramContext);
    }
    return null;
  }
  
  public void initBackgroundBridge()
  {
    Log.i(TAG, "BackgroundService onCreate PROCESS_LOCAL_VALUE = " + PROCESS_LOCAL_VALUE);
    this.pgpBackgroundBridge = BackgroundBridge.createBridge(this, this.messageHandler);
  }
  
  @Nullable
  public IBinder onBind(Intent paramIntent)
  {
    Log.i(TAG, "onBind()");
    return this.messenger.getBinder();
  }
  
  public void onCreate()
  {
    super.onCreate();
    Log.i(TAG, "BackgroundService onCreate() PROCESS_LOCAL_VALUE = " + PROCESS_LOCAL_VALUE);
    System.loadLibrary("pgpplugin");
    this.messenger = new Messenger(new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        ClientBridgeMessage localClientBridgeMessage = new ClientBridgeMessage(paramAnonymousMessage);
        localClientBridgeMessage.getAction();
        if (BackgroundService.this.pgpBackgroundBridge == null)
        {
          BackgroundService.access$102(BackgroundService.this, paramAnonymousMessage.replyTo);
          BackgroundService.this.initBackgroundBridge();
          BackgroundService.access$202(false);
        }
        BackgroundService.this.pgpBackgroundBridge.onClientMessage(localClientBridgeMessage);
      }
    });
    this.messageHandler = new BackgroundBridgeMessage.MessageHandler()
    {
      public void handleMessage(BackgroundBridgeMessage paramAnonymousBackgroundBridgeMessage)
      {
        if (BackgroundService.this.replyMessenger != null) {}
        for (;;)
        {
          try
          {
            BackgroundService.this.replyMessenger.send(paramAnonymousBackgroundBridgeMessage.message);
            BackgroundService.this.onHandleBackgroundMessage(paramAnonymousBackgroundBridgeMessage);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
            continue;
          }
          Log.e(BackgroundService.TAG, "replyMessenger not found");
        }
      }
    };
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    Log.i(TAG, "BackgroundService onDestroy PROCESS_LOCAL_VALUE = " + PROCESS_LOCAL_VALUE);
  }
  
  public void onHandleBackgroundMessage(BackgroundBridgeMessage paramBackgroundBridgeMessage)
  {
    switch (3.$SwitchMap$com$nianticlabs$pokemongoplus$bridge$BackgroundBridgeMessage$Action[paramBackgroundBridgeMessage.getAction().ordinal()])
    {
    default: 
      return;
    case 1: 
      createPlayerNotification(formatNotification(paramBackgroundBridgeMessage.getArg1(), paramBackgroundBridgeMessage.getNotification()));
      return;
    case 2: 
      stopPlayerNotification();
      return;
    case 3: 
      updateBatteryLevel(paramBackgroundBridgeMessage.getBatteryLevel());
      return;
    case 4: 
      paramBackgroundBridgeMessage = BridgeConstants.SfidaState.fromInt(paramBackgroundBridgeMessage.getArg1());
      updateNotificationForSfidaState(paramBackgroundBridgeMessage, this.sfidaState);
      this.sfidaState = paramBackgroundBridgeMessage;
      return;
    }
    this.pluginState = BridgeConstants.PgpState.fromInt(paramBackgroundBridgeMessage.getArg1());
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    onHandleIntent(paramIntent);
    return 2;
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    stopBackgroundService();
    return false;
  }
  
  public void shutdownBackgroundBridge()
  {
    Log.i(TAG, "BackgroundService shutdownBackgroundBridge() PROCESS_LOCAL_VALUE = " + PROCESS_LOCAL_VALUE);
    sendClientIntent(this, "confirmBridgeShutdown");
    if (this.pgpBackgroundBridge != null)
    {
      Log.i(TAG, "BackgroundService destroy the bridge ");
      this.pgpBackgroundBridge.destroyBridge();
      this.pgpBackgroundBridge = null;
    }
    Log.i(TAG, "DONE BackgroundService shutdownBackgroundBridge() PROCESS_LOCAL_VALUE = " + PROCESS_LOCAL_VALUE);
    serviceStopped = true;
    stopSelf();
  }
  
  public void startNotification(int paramInt, String paramString)
  {
    createPlayerNotification(formatNotification(paramInt, paramString));
  }
  
  public void stopNotification()
  {
    stopPlayerNotification();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/service/BackgroundService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */