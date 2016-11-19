package com.nianticlabs.pokemongoplus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.nianticlabs.pokemongoplus.ble.Peripheral;
import com.nianticlabs.pokemongoplus.ble.Service;
import com.nianticlabs.pokemongoplus.ble.SfidaConstant.BluetoothError;
import com.nianticlabs.pokemongoplus.ble.SfidaConstant.PeripheralState;
import com.nianticlabs.pokemongoplus.ble.callback.CompletionCallback;
import com.nianticlabs.pokemongoplus.ble.callback.ConnectCallback;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SfidaPeripheral
  extends Peripheral
{
  private static final String TAG = SfidaPeripheral.class.getSimpleName();
  private BluetoothAdapter bluetoothAdapter;
  private final BluetoothDevice bluetoothDevice;
  private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback()
  {
    public void onCharacteristicChanged(final BluetoothGatt paramAnonymousBluetoothGatt, final BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic)
    {
      Log.d(SfidaPeripheral.TAG, "onCharacteristicChanged");
      SfidaPeripheral.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          Log.d(SfidaPeripheral.TAG, "onCharacteristicChanged");
          synchronized (SfidaPeripheral.this.serviceRef)
          {
            Iterator localIterator = SfidaPeripheral.this.serviceRef.iterator();
            if (localIterator.hasNext()) {
              ((SfidaService)localIterator.next()).onCharacteristicChanged(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattCharacteristic);
            }
          }
        }
      });
    }
    
    public void onCharacteristicRead(final BluetoothGatt paramAnonymousBluetoothGatt, final BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, final int paramAnonymousInt)
    {
      Log.d(SfidaPeripheral.TAG, "onCharacteristicRead");
      SfidaPeripheral.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          Log.d(SfidaPeripheral.TAG, "onCharacteristicRead");
          synchronized (SfidaPeripheral.this.serviceRef)
          {
            Iterator localIterator = SfidaPeripheral.this.serviceRef.iterator();
            if (localIterator.hasNext()) {
              ((SfidaService)localIterator.next()).onCharacteristicRead(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattCharacteristic, paramAnonymousInt);
            }
          }
        }
      });
    }
    
    public void onCharacteristicWrite(final BluetoothGatt paramAnonymousBluetoothGatt, final BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, final int paramAnonymousInt)
    {
      Log.d(SfidaPeripheral.TAG, "onCharacteristicWrite");
      SfidaPeripheral.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          Log.d(SfidaPeripheral.TAG, "onCharacteristicWrite");
          synchronized (SfidaPeripheral.this.serviceRef)
          {
            Iterator localIterator = SfidaPeripheral.this.serviceRef.iterator();
            if (localIterator.hasNext()) {
              ((SfidaService)localIterator.next()).onCharacteristicWrite(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattCharacteristic, paramAnonymousInt);
            }
          }
        }
      });
    }
    
    public void onConnectionStateChange(final BluetoothGatt paramAnonymousBluetoothGatt, final int paramAnonymousInt1, final int paramAnonymousInt2)
    {
      Log.d(SfidaPeripheral.TAG, "onConnectionStateChange");
      SfidaPeripheral.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          Log.d(SfidaPeripheral.TAG, "onConnectionStateChange");
          SfidaPeripheral.this.onConnectionStateChange(paramAnonymousBluetoothGatt, paramAnonymousInt1, paramAnonymousInt2);
        }
      });
    }
    
    public void onDescriptorWrite(final BluetoothGatt paramAnonymousBluetoothGatt, final BluetoothGattDescriptor paramAnonymousBluetoothGattDescriptor, final int paramAnonymousInt)
    {
      Log.d(SfidaPeripheral.TAG, "onDescriptorWrite");
      SfidaPeripheral.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          Log.d(SfidaPeripheral.TAG, "onDescriptorWrite");
          synchronized (SfidaPeripheral.this.serviceRef)
          {
            Iterator localIterator = SfidaPeripheral.this.serviceRef.iterator();
            if (localIterator.hasNext()) {
              ((SfidaService)localIterator.next()).onDescriptorWrite(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattDescriptor, paramAnonymousInt);
            }
          }
        }
      });
    }
    
    public void onMtuChanged(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      super.onMtuChanged(paramAnonymousBluetoothGatt, paramAnonymousInt1, paramAnonymousInt2);
      Log.d(SfidaPeripheral.TAG, "onMthChanged mtu " + paramAnonymousInt1 + " status : " + paramAnonymousInt2);
    }
    
    public void onServicesDiscovered(final BluetoothGatt paramAnonymousBluetoothGatt, final int paramAnonymousInt)
    {
      Log.d(SfidaPeripheral.TAG, "onServicesDiscovered");
      SfidaPeripheral.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          Log.d(SfidaPeripheral.TAG, "onServicesDiscovered");
          SfidaPeripheral.this.onServicesDiscovered(paramAnonymousBluetoothGatt, paramAnonymousInt);
        }
      });
    }
  };
  private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver()
  {
    private void onHandleBluetoothIntent(Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      if (str == null)
      {
        Log.d(SfidaPeripheral.TAG, "onReceived() action was null");
        return;
      }
      Log.d(SfidaPeripheral.TAG, "onReceived() action was " + str);
      int i = -1;
      switch (str.hashCode())
      {
      }
      for (;;)
      {
        switch (i)
        {
        default: 
          Log.d(SfidaPeripheral.TAG, "onReceive() : Error unhandled: " + str);
          return;
          if (str.equals("android.bluetooth.device.action.BOND_STATE_CHANGED"))
          {
            i = 0;
            continue;
            if (str.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
              i = 1;
            }
          }
          break;
        }
      }
      Log.d(SfidaPeripheral.TAG, "ACTION_BOND_STATE_CHANGED, state = " + SfidaPeripheral.this.state.toString() + ", bond: " + SfidaPeripheral.this.bondState);
      SfidaPeripheral.this.onBondStateChanged(paramAnonymousIntent);
      return;
      Log.d(SfidaPeripheral.TAG, "ACTION_PAIRING_REQUEST, state = " + SfidaPeripheral.this.state.toString() + ", bond: " + SfidaPeripheral.this.bondState);
      paramAnonymousIntent = (BluetoothDevice)paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
      SfidaPeripheral.this.onPairingRequest(paramAnonymousIntent);
    }
    
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Log.d(SfidaPeripheral.TAG, "bluetoothReceiver onReceive");
      onHandleBluetoothIntent(paramAnonymousIntent);
    }
  };
  private int bondState;
  private ConnectCallback connectCallback;
  private final Context context;
  private ConnectCallback disconnectCallback;
  private CompletionCallback discoverServicesCallback;
  private BluetoothGatt gatt;
  private long nativeHandle;
  private byte[] scanRecord;
  private final HandlerExecutor serialExecutor;
  private final ArrayList<SfidaService> serviceRef = new ArrayList();
  private SfidaConstant.PeripheralState state;
  
  public SfidaPeripheral(HandlerExecutor paramHandlerExecutor, Context paramContext, BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfByte)
  {
    Log.d(TAG, String.format("Tid: %d SfidaPeripheral()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    this.context = paramContext;
    this.bluetoothDevice = paramBluetoothDevice;
    this.state = SfidaConstant.PeripheralState.Disconnected;
    this.serialExecutor = paramHandlerExecutor;
    this.bluetoothAdapter = SfidaUtils.getBluetoothManager(paramContext).getAdapter();
    this.scanRecord = paramArrayOfByte;
    this.bondState = 10;
  }
  
  private void bondingCanceled(BluetoothDevice paramBluetoothDevice)
  {
    Log.d(TAG, "bondingCanceled()");
    SfidaUtils.createBond(paramBluetoothDevice);
  }
  
  private byte[] byteArrayFromHexString(String paramString)
  {
    int j = paramString.length();
    byte[] arrayOfByte = new byte[j / 2];
    int i = 0;
    while (i < j)
    {
      arrayOfByte[(i / 2)] = ((byte)((Character.digit(paramString.charAt(i), 16) << 4) + Character.digit(paramString.charAt(i + 1), 16)));
      i += 2;
    }
    return arrayOfByte;
  }
  
  private void callMonitorDisconnectCallback(final boolean paramBoolean, final SfidaConstant.BluetoothError paramBluetoothError)
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaPeripheral.this.nativeMonitorDisconnectCallback(paramBoolean, paramBluetoothError.getInt());
      }
    });
  }
  
  private void disconnectFromBonding(BluetoothDevice paramBluetoothDevice)
  {
    Log.d(TAG, "disconnectFromBonding()");
    SfidaUtils.createBond(this.bluetoothDevice);
  }
  
  private Boolean isBoundDevice(BluetoothDevice paramBluetoothDevice)
  {
    Object localObject = this.bluetoothAdapter.getBondedDevices();
    if ((localObject != null) && (((Set)localObject).size() != 0))
    {
      localObject = ((Set)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        if (((BluetoothDevice)((Iterator)localObject).next()).getAddress().equals(paramBluetoothDevice.getAddress())) {
          return Boolean.valueOf(true);
        }
      }
    }
    return Boolean.valueOf(false);
  }
  
  private native void nativeConnectCallback(boolean paramBoolean, int paramInt);
  
  private native void nativeDisconnectCallback(boolean paramBoolean, int paramInt);
  
  private native void nativeDiscoverService(SfidaService paramSfidaService);
  
  private native void nativeDiscoverServicesCallback(boolean paramBoolean, int paramInt);
  
  private native void nativeMonitorDisconnectCallback(boolean paramBoolean, int paramInt);
  
  private void onBondStateChanged(Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
    int j = paramIntent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", Integer.MIN_VALUE);
    Log.d(TAG, String.format("Tid: %d onBondStateChanged()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "[BLE] ACTION_BOND_STATE_CHANGED oldState : " + SfidaUtils.getBondStateName(j) + " → newState : " + SfidaUtils.getBondStateName(i) + " local state: " + SfidaUtils.getBondStateName(this.bondState));
    paramIntent = (BluetoothDevice)paramIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
    if (paramIntent != null)
    {
      String str = paramIntent.getAddress();
      if (this.bluetoothDevice.getAddress().equals(str)) {
        break label147;
      }
    }
    label147:
    do
    {
      do
      {
        return;
        this.bondState = i;
        switch (i)
        {
        case 11: 
        default: 
          return;
        case 10: 
          if (j != 12) {
            break label384;
          }
          Log.d(TAG, "BOND_NONE, disconnecting from bonding, state = " + this.state.toString() + ", bond: " + this.bondState);
        }
      } while ((this.state != SfidaConstant.PeripheralState.Connecting) && (this.state != SfidaConstant.PeripheralState.Connected));
      disconnectFromBonding(paramIntent);
      return;
      Log.d(TAG, "BOND_BONDED, state = " + this.state.toString() + ", bond: " + this.bondState);
    } while ((tryCompleteConnect()) || (this.state == SfidaConstant.PeripheralState.Disconnected) || (this.state == SfidaConstant.PeripheralState.Disconnecting));
    Log.d(TAG, "BOND_BONDED, retrying, state = " + this.state.toString() + ", bond: " + this.bondState);
    reconnnectFromBonding(paramIntent);
    return;
    label384:
    if (j == 11)
    {
      bondingCanceled(paramIntent);
      Log.d(TAG, "BOND_NONE, bonding canceled, state = " + this.state.toString() + ", bond: " + this.bondState);
      return;
    }
    Log.d(TAG, "Unhandled oldState : " + j);
  }
  
  private void onPairingRequest(BluetoothDevice paramBluetoothDevice)
  {
    Log.d(TAG, String.format("Tid: %d onPairingRequest()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "onPairingRequest()");
    String str = paramBluetoothDevice.getAddress();
    if (!this.bluetoothDevice.getAddress().equals(str)) {
      return;
    }
    try
    {
      paramBluetoothDevice.getClass().getMethod("setPairingConfirmation", new Class[] { Boolean.TYPE }).invoke(paramBluetoothDevice, new Object[] { Boolean.valueOf(true) });
      paramBluetoothDevice.getClass().getMethod("cancelPairingUserInput", new Class[0]).invoke(paramBluetoothDevice, new Object[0]);
      return;
    }
    catch (IllegalAccessException paramBluetoothDevice)
    {
      paramBluetoothDevice.printStackTrace();
      return;
    }
    catch (InvocationTargetException paramBluetoothDevice)
    {
      paramBluetoothDevice.printStackTrace();
      return;
    }
    catch (NoSuchMethodException paramBluetoothDevice)
    {
      paramBluetoothDevice.printStackTrace();
    }
  }
  
  private void reconnnectFromBonding(BluetoothDevice paramBluetoothDevice)
  {
    Log.d(TAG, "reconnnectFromBonding()");
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaPeripheral.this.retryConnect();
      }
    });
  }
  
  private void releaseServices()
  {
    Log.d(TAG, String.format("Tid: %d releaseServices()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "releaseServices, state = " + this.state.toString() + ", bond: " + this.bondState);
    synchronized (this.serviceRef)
    {
      Iterator localIterator = this.serviceRef.iterator();
      while (localIterator.hasNext())
      {
        Service localService = (Service)localIterator.next();
        if ((localService instanceof SfidaService)) {
          ((SfidaService)localService).onDestroy();
        }
      }
    }
    this.serviceRef.clear();
  }
  
  private void retryConnect()
  {
    this.serialExecutor.maybeAssertOnThread();
    Log.d(TAG, String.format("Tid: %d retryConnect", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    if (isBoundDevice(this.bluetoothDevice).booleanValue())
    {
      this.bondState = 12;
      String str = this.bluetoothDevice.getAddress();
      if ((this.bluetoothAdapter == null) || (str == null))
      {
        Log.w(TAG, "[BLE] BluetoothAdapter not initialized or unspecified address.");
        return;
      }
      if ((str.equals(this.bluetoothDevice.getAddress())) && (this.gatt != null))
      {
        Log.d(TAG, "[BLE] Trying to use an existing bluetoothGatt for connection.");
        this.gatt.connect();
        return;
      }
      this.gatt = this.bluetoothDevice.connectGatt(this.context, true, this.bluetoothGattCallback);
      Log.d(TAG, "Trying to create a new connection.");
      return;
    }
    Log.d(TAG, "Create bond.");
    this.bondState = 10;
    SfidaUtils.createBond(this.bluetoothDevice);
  }
  
  private void unpairDevice()
  {
    Log.d(TAG, String.format("Tid: %d unpairDevice()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "unpairDevice()");
    try
    {
      this.bluetoothDevice.getClass().getMethod("removeBond", (Class[])null).invoke(this.bluetoothDevice, (Object[])null);
      return;
    }
    catch (Exception localException)
    {
      Log.e(TAG, localException.getMessage());
    }
  }
  
  public void closeBluetoothGatt()
  {
    Log.d(TAG, String.format("Tid: %d closeBluetoothGatt()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "closeBluetoothGatt, state = " + this.state.toString() + ", bond: " + this.bondState);
    if (this.gatt == null) {
      return;
    }
    this.gatt.close();
    this.gatt = null;
  }
  
  public void connect()
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        Log.d(SfidaPeripheral.TAG, String.format("Tid: %d connect()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
        Log.d(SfidaPeripheral.TAG, "connect() called, state = " + SfidaPeripheral.this.state.toString() + ", bond: " + SfidaPeripheral.this.bondState);
        SfidaPeripheral.this.connect(new ConnectCallback()
        {
          public void onConnectionStateChanged(boolean paramAnonymous2Boolean, SfidaConstant.BluetoothError paramAnonymous2BluetoothError)
          {
            Log.d(SfidaPeripheral.TAG, String.format("Tid: %d connect() onConnectionStateChanged()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
            if (!paramAnonymous2Boolean) {
              Log.e(SfidaPeripheral.TAG, String.format("connect() FAILED, state = %d, bond: %d, ERROR: %s", new Object[] { SfidaPeripheral.this.state.toString(), Integer.valueOf(SfidaPeripheral.this.bondState), paramAnonymous2BluetoothError.toString() }));
            }
            SfidaPeripheral.this.nativeConnectCallback(paramAnonymous2Boolean, paramAnonymous2BluetoothError.getInt());
          }
        });
      }
    });
  }
  
  public void connect(ConnectCallback paramConnectCallback)
  {
    this.serialExecutor.maybeAssertOnThread();
    this.connectCallback = paramConnectCallback;
    this.state = SfidaConstant.PeripheralState.Connecting;
    Log.d(TAG, String.format("SfidaPeripheral connect. state = %s", new Object[] { this.state.toString() }));
    if (isBoundDevice(this.bluetoothDevice).booleanValue()) {
      unpairDevice();
    }
    retryConnect();
  }
  
  public void disconnect()
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        Log.d(SfidaPeripheral.TAG, String.format("Tid: %d disconnect()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
        Log.d(SfidaPeripheral.TAG, "disconnect() called, state = " + SfidaPeripheral.this.state.toString() + ", bond: " + SfidaPeripheral.this.bondState);
        SfidaPeripheral.this.disconnect(new ConnectCallback()
        {
          public void onConnectionStateChanged(boolean paramAnonymous2Boolean, SfidaConstant.BluetoothError paramAnonymous2BluetoothError)
          {
            Log.d(SfidaPeripheral.TAG, String.format("Tid: %d disconnect() onConnectionStateChanged", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
            SfidaPeripheral.this.nativeDisconnectCallback(paramAnonymous2Boolean, paramAnonymous2BluetoothError.getInt());
          }
        });
      }
    });
  }
  
  public void disconnect(ConnectCallback paramConnectCallback)
  {
    this.serialExecutor.maybeAssertOnThread();
    Log.d(TAG, String.format("Tid: %d disconnect(ConnectCallback)", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    this.state = SfidaConstant.PeripheralState.Disconnecting;
    Log.d(TAG, String.format("SfidaPeripheral disconnect. state = %s", new Object[] { this.state.toString() }));
    if (this.gatt != null)
    {
      this.disconnectCallback = paramConnectCallback;
      this.gatt.disconnect();
    }
    for (;;)
    {
      unpairDevice();
      return;
      Log.w(TAG, "Gatt is null");
      paramConnectCallback.onConnectionStateChanged(true, SfidaConstant.BluetoothError.Unknown);
    }
  }
  
  public void discoverServices()
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaPeripheral.this.discoverServices(new CompletionCallback()
        {
          public void onCompletion(final boolean paramAnonymous2Boolean, final SfidaConstant.BluetoothError paramAnonymous2BluetoothError)
          {
            SfidaPeripheral.this.serialExecutor.execute(new Runnable()
            {
              public void run()
              {
                Log.d(SfidaPeripheral.TAG, String.format("Tid: %d discoverServices() callback", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
                Log.d(SfidaPeripheral.TAG, "discoverServices success:" + paramAnonymous2Boolean + " error:" + paramAnonymous2BluetoothError);
                SfidaPeripheral.this.nativeDiscoverServicesCallback(paramAnonymous2Boolean, paramAnonymous2BluetoothError.getInt());
              }
            });
          }
        });
      }
    });
  }
  
  public void discoverServices(CompletionCallback paramCompletionCallback)
  {
    this.serialExecutor.maybeAssertOnThread();
    Log.d(TAG, String.format("Tid: %d discoverServices()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "discoverServices(" + paramCompletionCallback.toString() + ")");
    try
    {
      Thread.sleep(300L);
      if (this.gatt != null)
      {
        this.discoverServicesCallback = paramCompletionCallback;
        boolean bool = this.gatt.discoverServices();
        Log.d(TAG, "discoverServices:" + bool);
        return;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
      }
      Log.e(TAG, "gatt is null");
    }
  }
  
  public String getAddress()
  {
    return this.bluetoothDevice.getAddress();
  }
  
  public long getAdvertisingServiceDataLongValue(String paramString)
  {
    for (;;)
    {
      int i;
      int j;
      try
      {
        paramString = byteArrayFromHexString(paramString);
        int n = paramString.length;
        i = 0;
        if (i < this.scanRecord.length - n)
        {
          int m = 1;
          j = 0;
          int k = m;
          if (j < n)
          {
            if (this.scanRecord[(i + j)] != paramString[(n - 1 - j)]) {
              k = 0;
            }
          }
          else
          {
            if (k == 0) {
              break label101;
            }
            long l = this.scanRecord[(i + n)];
            return l;
          }
        }
        else
        {
          return 0L;
        }
      }
      finally {}
      j += 1;
      continue;
      label101:
      i += 1;
    }
  }
  
  public String getIdentifier()
  {
    return this.bluetoothDevice.getAddress();
  }
  
  public String getName()
  {
    return this.bluetoothDevice.getName();
  }
  
  public Service getService(int paramInt)
  {
    if (paramInt > getServiceCount() - 1) {
      return null;
    }
    return (Service)this.serviceRef.get(paramInt);
  }
  
  public Service getService(String paramString)
  {
    if (paramString != null)
    {
      int j = getServiceCount();
      int i = 0;
      while (i < j)
      {
        Service localService = getService(i);
        if (paramString.equals(localService.getUuid())) {
          return localService;
        }
        i += 1;
      }
    }
    return null;
  }
  
  public int getServiceCount()
  {
    return this.serviceRef.size();
  }
  
  public SfidaConstant.PeripheralState getState()
  {
    return this.state;
  }
  
  public int getStateInt()
  {
    return getState().getInt();
  }
  
  public void onConnectionStateChange(BluetoothGatt paramBluetoothGatt, int paramInt1, int paramInt2)
  {
    boolean bool = true;
    switch (paramInt2)
    {
    case 1: 
    default: 
      Log.d(TAG, String.format("Tid: %d onConnectionStateChange(default)", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
      Log.d(TAG, "onConnectionStateChange() UnhandledState status : " + paramInt1 + " newState : " + paramInt2);
      return;
    case 2: 
      Log.d(TAG, "Connected with GATT server.");
      Log.d(TAG, String.format("Tid: %d onConnectionStateChange(STATE_CONNECTED)", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
      this.state = SfidaConstant.PeripheralState.Connected;
      Log.d(TAG, String.format("CONNECTED SfidaPeripheral connect GATT callback. state = %s", new Object[] { this.state.toString() }));
      tryCompleteConnect();
      return;
    }
    Log.d(TAG, String.format("Tid: %d onConnectionStateChange(STATE_DISCONNECTED)", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "Disconnected from GATT server., state = " + this.state.toString());
    SfidaUtils.refreshDeviceCache(paramBluetoothGatt);
    this.bondState = 10;
    closeBluetoothGatt();
    releaseServices();
    if (this.disconnectCallback != null)
    {
      this.state = SfidaConstant.PeripheralState.Disconnected;
      Log.d(TAG, "Disconnected from state " + this.state.toString());
      paramBluetoothGatt = this.disconnectCallback;
      if (paramInt1 == 0) {}
      for (;;)
      {
        paramBluetoothGatt.onConnectionStateChanged(bool, SfidaConstant.BluetoothError.Unknown);
        return;
        bool = false;
      }
    }
    if ((this.connectCallback != null) && ((this.state == SfidaConstant.PeripheralState.Connected) || (this.state == SfidaConstant.PeripheralState.Connecting)))
    {
      Log.d(TAG, "Disconnected, retrying from state " + this.state.toString());
      this.state = SfidaConstant.PeripheralState.Connecting;
      retryConnect();
      return;
    }
    Log.d(TAG, "Disconnected after connected, giving up from state " + this.state.toString());
    this.state = SfidaConstant.PeripheralState.Disconnected;
    callMonitorDisconnectCallback(true, SfidaConstant.BluetoothError.PeripheralDisconnected);
  }
  
  public void onCreate()
  {
    Log.d(TAG, String.format("Tid: %d onCreate()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
    localIntentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
    Log.d(TAG, "context.registerReceiver(bluetoothReceiver");
    this.context.registerReceiver(this.bluetoothReceiver, localIntentFilter);
  }
  
  public void onDestroy()
  {
    Log.d(TAG, String.format("Tid: %d onDestroy()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Log.d(TAG, "context.unregisterReceiver(bluetoothReceiver");
    try
    {
      this.context.unregisterReceiver(this.bluetoothReceiver);
      releaseServices();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        Log.d(TAG, "java.lang.IllegalArgumentException: Receiver not registered");
      }
    }
  }
  
  public void onServicesDiscovered(BluetoothGatt paramBluetoothGatt, int paramInt)
  {
    Log.d(TAG, String.format("Tid: %d onServicesDiscovered(default)", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    switch (paramInt)
    {
    default: 
      Log.e(TAG, "DISCONNECTED: BluetoothError, onServicesDiscovered, from state " + this.state.toString());
      this.discoverServicesCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
      Log.e(TAG, "[BLE] onServicesDiscovered received error: " + paramInt);
      return;
    }
    List localList = paramBluetoothGatt.getServices();
    synchronized (this.serviceRef)
    {
      Log.d(TAG, "onServicesDiscovered thread:" + Thread.currentThread().getId());
      this.serviceRef.clear();
      Iterator localIterator = localList.iterator();
      if (localIterator.hasNext())
      {
        Object localObject = (BluetoothGattService)localIterator.next();
        localObject = new SfidaService(this.serialExecutor, (BluetoothGattService)localObject, paramBluetoothGatt);
        nativeDiscoverService((SfidaService)localObject);
        this.serviceRef.add(localObject);
      }
    }
    if (this.discoverServicesCallback != null)
    {
      this.discoverServicesCallback.onCompletion(true, SfidaConstant.BluetoothError.Unknown);
      return;
    }
    Log.e(TAG, String.format("onServicesDiscovered() no callback when discover %d service on device %s", new Object[] { Integer.valueOf(localList.size()), this.bluetoothDevice.getAddress() }));
  }
  
  public void setScanRecord(byte[] paramArrayOfByte)
  {
    try
    {
      this.scanRecord = paramArrayOfByte;
      return;
    }
    finally {}
  }
  
  boolean tryCompleteConnect()
  {
    Log.d(TAG, "tryCompleteConnect, state = " + this.state.toString() + ", bond: " + this.bondState);
    Log.d(TAG, String.format("Tid: %d tryCompleteConnect()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    if ((this.bondState == 12) && (this.state == SfidaConstant.PeripheralState.Connected) && (this.connectCallback != null))
    {
      Log.d(TAG, String.format("calling onConnectionStateChanged. state = %s", new Object[] { this.state.toString() }));
      this.connectCallback.onConnectionStateChanged(true, SfidaConstant.BluetoothError.Unknown);
      this.connectCallback = null;
      return true;
    }
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/SfidaPeripheral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */