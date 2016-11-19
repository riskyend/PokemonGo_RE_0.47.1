package com.nianticlabs.pokemongoplus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.nianticlabs.pokemongoplus.ble.BluetoothDriver;
import com.nianticlabs.pokemongoplus.ble.Peripheral;
import com.nianticlabs.pokemongoplus.ble.SfidaConstant.CentralState;
import com.nianticlabs.pokemongoplus.ble.SfidaConstant.PeripheralState;
import com.nianticlabs.pokemongoplus.ble.callback.CentralStateCallback;
import com.nianticlabs.pokemongoplus.ble.callback.ScanCallback;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SfidaBluetoothDriver
  extends BluetoothDriver
{
  private static final String TAG = SfidaBluetoothDriver.class.getSimpleName();
  private BluetoothAdapter bluetoothAdapter;
  private Context context;
  private boolean isScanning;
  private long nativeHandle;
  private Peripheral peripheral;
  private Map<String, SfidaPeripheral> peripheralMap = new HashMap();
  private ScanCallback scanCallback;
  private HandlerExecutor serialExecutor = new HandlerExecutor("SfidaBluetoothDriver");
  private SfidaScanCallback sfidaScanCallback;
  
  public SfidaBluetoothDriver(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private void SetIsScanning(boolean paramBoolean)
  {
    try
    {
      this.isScanning = paramBoolean;
      return;
    }
    finally {}
  }
  
  private native void nativeScanCallback(Peripheral paramPeripheral);
  
  private native void nativeStartCallback(int paramInt);
  
  public boolean IsScanning()
  {
    try
    {
      boolean bool = this.isScanning;
      return bool;
    }
    finally {}
  }
  
  public boolean isEnabledBluetoothLe()
  {
    return (this.bluetoothAdapter != null) && (this.bluetoothAdapter.isEnabled());
  }
  
  public void releasePeripherals()
  {
    Log.e(TAG, String.format("Tid: %d releasePeripherals()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.peripheralMap.values().iterator();
    while (localIterator.hasNext())
    {
      SfidaPeripheral localSfidaPeripheral = (SfidaPeripheral)localIterator.next();
      SfidaConstant.PeripheralState localPeripheralState = localSfidaPeripheral.getState();
      if ((localPeripheralState == SfidaConstant.PeripheralState.Disconnected) || (localPeripheralState == SfidaConstant.PeripheralState.Disconnecting)) {
        localSfidaPeripheral.onDestroy();
      } else {
        localHashMap.put(localSfidaPeripheral.getAddress(), localSfidaPeripheral);
      }
    }
    this.peripheralMap.clear();
    this.peripheralMap = localHashMap;
  }
  
  public int start(CentralStateCallback paramCentralStateCallback)
  {
    this.serialExecutor.maybeAssertOnThread();
    if ((this.context == null) || (!this.context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")))
    {
      paramCentralStateCallback.OnStateChanged(SfidaConstant.CentralState.Unsupported);
      return 0;
    }
    Log.e(TAG, String.format("Tid: %d start()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    Object localObject = SfidaUtils.getBluetoothManager(this.context);
    if (localObject != null)
    {
      this.bluetoothAdapter = ((BluetoothManager)localObject).getAdapter();
      if ((this.bluetoothAdapter != null) && (this.bluetoothAdapter.isEnabled())) {}
      for (localObject = SfidaConstant.CentralState.PoweredOn;; localObject = SfidaConstant.CentralState.PoweredOff)
      {
        paramCentralStateCallback.OnStateChanged((SfidaConstant.CentralState)localObject);
        return 0;
      }
    }
    Log.e(TAG, "start(CentralStateCallback): Could not find bluetooth manager.");
    paramCentralStateCallback.OnStateChanged(SfidaConstant.CentralState.Unknown);
    return 0;
  }
  
  public void startDriver()
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        Log.e(SfidaBluetoothDriver.TAG, String.format("Tid: %d startDriver()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
        SfidaBluetoothDriver.this.start(new CentralStateCallback()
        {
          public void OnStateChanged(SfidaConstant.CentralState paramAnonymous2CentralState)
          {
            Log.e(SfidaBluetoothDriver.TAG, String.format("Tid: %d startDriver(), OnStateChanged", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
            SfidaBluetoothDriver.this.serialExecutor.maybeAssertOnThread();
            SfidaBluetoothDriver.this.nativeStartCallback(paramAnonymous2CentralState.getInt());
          }
        });
      }
    });
  }
  
  public void startScanning(String paramString)
  {
    startScanning(paramString, new ScanCallback()
    {
      public void onScan(Peripheral paramAnonymousPeripheral)
      {
        SfidaBluetoothDriver.this.nativeScanCallback(paramAnonymousPeripheral);
      }
    });
  }
  
  public void startScanning(final String paramString, final ScanCallback paramScanCallback)
  {
    if (IsScanning()) {
      return;
    }
    SetIsScanning(true);
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        Log.d(SfidaBluetoothDriver.TAG, String.format("Tid: %d startScanning()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
        SfidaBluetoothDriver.this.releasePeripherals();
        SfidaBluetoothDriver.access$302(SfidaBluetoothDriver.this, paramScanCallback);
        if (SfidaBluetoothDriver.this.isEnabledBluetoothLe())
        {
          SfidaBluetoothDriver.access$402(SfidaBluetoothDriver.this, new SfidaBluetoothDriver.SfidaScanCallback(SfidaBluetoothDriver.this, paramString));
          SfidaBluetoothDriver.this.bluetoothAdapter.startLeScan(SfidaBluetoothDriver.this.sfidaScanCallback);
        }
      }
    });
  }
  
  public void stop(int paramInt)
  {
    Log.e(TAG, String.format("Tid: %d stop()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaBluetoothDriver.this.releasePeripherals();
      }
    });
    this.serialExecutor.stop();
  }
  
  public void stopDriver()
  {
    stop(0);
  }
  
  public void stopScanning(String paramString)
  {
    if (!IsScanning()) {
      return;
    }
    SetIsScanning(false);
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        Log.d(SfidaBluetoothDriver.TAG, String.format("Tid: %d stopScanning()", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
        if (SfidaBluetoothDriver.this.isEnabledBluetoothLe()) {}
        try
        {
          SfidaBluetoothDriver.this.bluetoothAdapter.stopLeScan(SfidaBluetoothDriver.this.sfidaScanCallback);
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          Log.d(SfidaBluetoothDriver.TAG, String.format("-+- SfidaBluetoothDriver stopScanning IllegalArgumentException", new Object[0]));
        }
      }
    });
  }
  
  private class SfidaScanCallback
    implements BluetoothAdapter.LeScanCallback
  {
    private String peripheralName;
    
    public SfidaScanCallback(String paramString)
    {
      this.peripheralName = paramString;
    }
    
    public void onLeScan(final BluetoothDevice paramBluetoothDevice, int paramInt, final byte[] paramArrayOfByte)
    {
      final String str = paramBluetoothDevice.getAddress();
      SfidaBluetoothDriver.this.serialExecutor.execute(new Runnable()
      {
        public void run()
        {
          if (!SfidaBluetoothDriver.this.IsScanning()) {}
          Object localObject;
          do
          {
            return;
            localObject = paramBluetoothDevice.getName();
          } while ((localObject == null) || (!((String)localObject).contains(SfidaBluetoothDriver.SfidaScanCallback.this.peripheralName)) || (SfidaBluetoothDriver.this.scanCallback == null));
          if (!SfidaBluetoothDriver.this.peripheralMap.containsKey(str))
          {
            Log.d(SfidaBluetoothDriver.TAG, String.format("-+- SfidaBluetoothDriver SfidaScanCallback new peripheral: %s", new Object[] { str }));
            localObject = new SfidaPeripheral(SfidaBluetoothDriver.this.serialExecutor, SfidaBluetoothDriver.this.context, paramBluetoothDevice, paramArrayOfByte);
            SfidaBluetoothDriver.this.peripheralMap.put(str, localObject);
          }
          for (;;)
          {
            SfidaBluetoothDriver.this.scanCallback.onScan((Peripheral)localObject);
            return;
            localObject = (SfidaPeripheral)SfidaBluetoothDriver.this.peripheralMap.get(str);
            ((SfidaPeripheral)localObject).setScanRecord(paramArrayOfByte);
          }
        }
      });
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/SfidaBluetoothDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */