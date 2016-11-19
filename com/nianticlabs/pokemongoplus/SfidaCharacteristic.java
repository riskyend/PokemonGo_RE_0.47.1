package com.nianticlabs.pokemongoplus;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;
import com.nianticlabs.pokemongoplus.ble.Characteristic;
import com.nianticlabs.pokemongoplus.ble.SfidaConstant;
import com.nianticlabs.pokemongoplus.ble.SfidaConstant.BluetoothError;
import com.nianticlabs.pokemongoplus.ble.callback.CompletionCallback;
import com.nianticlabs.pokemongoplus.ble.callback.ValueChangeCallback;
import java.util.ArrayDeque;
import java.util.UUID;

public class SfidaCharacteristic
  extends Characteristic
{
  private static final String TAG = SfidaCharacteristic.class.getSimpleName();
  private final int RETRIES = 7;
  private final long SLEEP_DELAY_MS = 250L;
  private BluetoothGattCharacteristic characteristic;
  private BluetoothGatt gatt;
  private long nativeHandle;
  private CompletionCallback onDisableNotifyCallback;
  private CompletionCallback onEnableNotifyCallback;
  private CompletionCallback onReadCallback;
  private ValueChangeCallback onValueChangedCallback;
  private CompletionCallback onWriteCallback;
  private volatile ArrayDeque<byte[]> queue = new ArrayDeque();
  private final HandlerExecutor serialExecutor;
  
  public SfidaCharacteristic(HandlerExecutor paramHandlerExecutor, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, BluetoothGatt paramBluetoothGatt)
  {
    this.gatt = paramBluetoothGatt;
    this.characteristic = paramBluetoothGattCharacteristic;
    this.serialExecutor = paramHandlerExecutor;
  }
  
  private native void nativeDisableNotifyCallback(boolean paramBoolean, int paramInt);
  
  private native void nativeEnableNotifyCallback(boolean paramBoolean, int paramInt);
  
  private native void nativeReadCompleteCallback(boolean paramBoolean, int paramInt);
  
  private native void nativeSaveValueChangedCallback(byte[] paramArrayOfByte);
  
  private native void nativeValueChangedCallback(boolean paramBoolean1, boolean paramBoolean2, int paramInt);
  
  private native void nativeWriteCompleteCallback(boolean paramBoolean, int paramInt);
  
  public void cancelNotify()
  {
    this.onValueChangedCallback = null;
  }
  
  public void disableNotify()
  {
    disableNotify(new CompletionCallback()
    {
      public void onCompletion(final boolean paramAnonymousBoolean, final SfidaConstant.BluetoothError paramAnonymousBluetoothError)
      {
        SfidaCharacteristic.this.serialExecutor.execute(new Runnable()
        {
          public void run()
          {
            Log.d(SfidaCharacteristic.TAG, String.format("disableNotify callback success: %b error[%d]:%s UUID:%s", new Object[] { Boolean.valueOf(paramAnonymousBoolean), Integer.valueOf(paramAnonymousBluetoothError.getInt()), paramAnonymousBluetoothError.toString(), SfidaCharacteristic.this.getUuid() }));
            SfidaCharacteristic.this.nativeDisableNotifyCallback(paramAnonymousBoolean, paramAnonymousBluetoothError.getInt());
          }
        });
      }
    });
  }
  
  public void disableNotify(final CompletionCallback paramCompletionCallback)
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaCharacteristic.access$802(SfidaCharacteristic.this, null);
        SfidaCharacteristic.access$902(SfidaCharacteristic.this, paramCompletionCallback);
        SfidaCharacteristic.this.gatt.setCharacteristicNotification(SfidaCharacteristic.this.characteristic, false);
        SfidaCharacteristic.this.characteristic.getValue();
        BluetoothGattDescriptor localBluetoothGattDescriptor = SfidaCharacteristic.this.characteristic.getDescriptor(SfidaConstant.UUID_CLIENT_CHARACTERISTIC_CONFIG);
        Log.d(SfidaCharacteristic.TAG, String.format("disableNotify Config characteristic:%s descriptor:%s", new Object[] { SfidaCharacteristic.this.getUuid(), localBluetoothGattDescriptor }));
        boolean bool;
        int i;
        if (localBluetoothGattDescriptor != null)
        {
          localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
          bool = false;
          i = 0;
        }
        for (;;)
        {
          if (i < 7)
          {
            bool = SfidaCharacteristic.this.gatt.writeDescriptor(localBluetoothGattDescriptor);
            Log.d(SfidaCharacteristic.TAG, String.format("disableNotify Write descriptor success: %b", new Object[] { Boolean.valueOf(bool) }));
            if (!bool) {}
          }
          else
          {
            if (!bool)
            {
              SfidaCharacteristic.this.onDisableNotifyCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
              SfidaCharacteristic.access$902(SfidaCharacteristic.this, null);
            }
            return;
          }
          try
          {
            Thread.sleep(250L);
            i += 1;
          }
          catch (InterruptedException localInterruptedException)
          {
            for (;;) {}
          }
        }
      }
    });
  }
  
  public void enableNotify()
  {
    enableNotify(new CompletionCallback()
    {
      public void onCompletion(final boolean paramAnonymousBoolean, final SfidaConstant.BluetoothError paramAnonymousBluetoothError)
      {
        SfidaCharacteristic.this.serialExecutor.execute(new Runnable()
        {
          public void run()
          {
            Log.d(SfidaCharacteristic.TAG, String.format("enableNotify callback success: %b error[%d]:%s UUID:%s", new Object[] { Boolean.valueOf(paramAnonymousBoolean), Integer.valueOf(paramAnonymousBluetoothError.getInt()), paramAnonymousBluetoothError.toString(), SfidaCharacteristic.this.getUuid() }));
            SfidaCharacteristic.this.nativeEnableNotifyCallback(paramAnonymousBoolean, paramAnonymousBluetoothError.getInt());
          }
        });
      }
    });
  }
  
  public void enableNotify(final CompletionCallback paramCompletionCallback)
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaCharacteristic.access$802(SfidaCharacteristic.this, paramCompletionCallback);
        SfidaCharacteristic.access$902(SfidaCharacteristic.this, null);
        int i = 0;
        for (;;)
        {
          boolean bool;
          if (i < 7)
          {
            bool = SfidaCharacteristic.this.gatt.setCharacteristicNotification(SfidaCharacteristic.this.characteristic, true);
            Log.d(SfidaCharacteristic.TAG, String.format("setCharacteristicNotification success: %b", new Object[] { Boolean.valueOf(bool) }));
            if (!bool) {}
          }
          else
          {
            if ((SfidaCharacteristic.this.characteristic.getProperties() & 0x10) == 0) {
              Log.w(SfidaCharacteristic.TAG, "Enable Notify not supported");
            }
            SfidaCharacteristic.this.characteristic.getValue();
            BluetoothGattDescriptor localBluetoothGattDescriptor = SfidaCharacteristic.this.characteristic.getDescriptor(SfidaConstant.UUID_CLIENT_CHARACTERISTIC_CONFIG);
            Log.d(SfidaCharacteristic.TAG, String.format("Config characteristic:%s descriptor:%s", new Object[] { SfidaCharacteristic.this.getUuid(), localBluetoothGattDescriptor }));
            if (localBluetoothGattDescriptor != null)
            {
              localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
              bool = false;
              i = 0;
              label171:
              if (i < 7)
              {
                bool = SfidaCharacteristic.this.gatt.writeDescriptor(localBluetoothGattDescriptor);
                Log.d(SfidaCharacteristic.TAG, String.format("Write descriptor success: %b", new Object[] { Boolean.valueOf(bool) }));
                if (!bool) {
                  break label259;
                }
              }
              if (!bool)
              {
                SfidaCharacteristic.this.onEnableNotifyCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
                SfidaCharacteristic.access$802(SfidaCharacteristic.this, null);
              }
            }
            return;
          }
          try
          {
            Thread.sleep(250L);
            i += 1;
          }
          catch (InterruptedException localInterruptedException1)
          {
            try
            {
              label259:
              Thread.sleep(250L);
              i += 1;
              break label171;
              localInterruptedException1 = localInterruptedException1;
            }
            catch (InterruptedException localInterruptedException2)
            {
              for (;;) {}
            }
          }
        }
      }
    });
  }
  
  public long getLongValue()
  {
    this.serialExecutor.maybeAssertOnThread();
    return 0L;
  }
  
  public String getUuid()
  {
    this.serialExecutor.maybeAssertOnThread();
    return this.characteristic.getUuid().toString();
  }
  
  public byte[] getValue()
  {
    this.serialExecutor.maybeAssertOnThread();
    return (byte[])this.queue.pollFirst();
  }
  
  public void notifyValueChanged()
  {
    this.onValueChangedCallback = new ValueChangeCallback()
    {
      public void OnValueChange(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, SfidaConstant.BluetoothError paramAnonymousBluetoothError)
      {
        SfidaCharacteristic.this.nativeValueChangedCallback(paramAnonymousBoolean1, paramAnonymousBoolean2, paramAnonymousBluetoothError.getInt());
      }
    };
  }
  
  public void onCharacteristicChanged()
  {
    this.serialExecutor.maybeAssertOnThread();
    Log.d(TAG, String.format("onCharacteristicChanged: %s", new Object[] { this.characteristic.getUuid().toString() }));
    byte[] arrayOfByte = this.characteristic.getValue();
    if (this.onValueChangedCallback != null)
    {
      nativeSaveValueChangedCallback(arrayOfByte);
      this.queue.add(arrayOfByte);
      this.onValueChangedCallback.OnValueChange(true, true, SfidaConstant.BluetoothError.Unknown);
    }
  }
  
  public void onCharacteristicRead(int paramInt)
  {
    this.serialExecutor.maybeAssertOnThread();
    if (paramInt == 0)
    {
      Log.d(TAG, String.format("onCharacteristicRead: %s", new Object[] { this.characteristic.getUuid().toString() }));
      nativeSaveValueChangedCallback(this.characteristic.getValue());
      this.onReadCallback.onCompletion(true, SfidaConstant.BluetoothError.Unknown);
      return;
    }
    this.onReadCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
  }
  
  public void onCharacteristicWrite(int paramInt)
  {
    this.serialExecutor.maybeAssertOnThread();
    if (this.onWriteCallback != null)
    {
      if (paramInt == 0) {
        this.onWriteCallback.onCompletion(true, SfidaConstant.BluetoothError.Unknown);
      }
    }
    else {
      return;
    }
    this.onWriteCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
  }
  
  public void onDescriptorWrite(BluetoothGattDescriptor paramBluetoothGattDescriptor, int paramInt)
  {
    boolean bool = true;
    Log.d(TAG, String.format("onDescriptorWrite status:%d", new Object[] { Integer.valueOf(paramInt) }));
    this.serialExecutor.maybeAssertOnThread();
    if (paramInt == 0)
    {
      if (this.onEnableNotifyCallback == null) {
        break label67;
      }
      this.onEnableNotifyCallback.onCompletion(bool, SfidaConstant.BluetoothError.Unknown);
      this.onEnableNotifyCallback = null;
    }
    label67:
    while (this.onDisableNotifyCallback == null)
    {
      return;
      bool = false;
      break;
    }
    this.onDisableNotifyCallback.onCompletion(bool, SfidaConstant.BluetoothError.Unknown);
    this.onDisableNotifyCallback = null;
  }
  
  public void onDestroy()
  {
    this.serialExecutor.maybeAssertOnThread();
    this.onEnableNotifyCallback = null;
    this.onDisableNotifyCallback = null;
  }
  
  public void readValue()
  {
    readValue(new CompletionCallback()
    {
      public void onCompletion(final boolean paramAnonymousBoolean, final SfidaConstant.BluetoothError paramAnonymousBluetoothError)
      {
        SfidaCharacteristic.this.serialExecutor.execute(new Runnable()
        {
          public void run()
          {
            SfidaCharacteristic.this.nativeReadCompleteCallback(paramAnonymousBoolean, paramAnonymousBluetoothError.getInt());
          }
        });
      }
    });
  }
  
  public void readValue(final CompletionCallback paramCompletionCallback)
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaCharacteristic.access$602(SfidaCharacteristic.this, paramCompletionCallback);
        if (!SfidaCharacteristic.this.gatt.readCharacteristic(SfidaCharacteristic.this.characteristic)) {
          SfidaCharacteristic.this.onReadCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
        }
      }
    });
  }
  
  public void writeByteArray(byte[] paramArrayOfByte)
  {
    writeByteArray(paramArrayOfByte, new CompletionCallback()
    {
      public void onCompletion(final boolean paramAnonymousBoolean, final SfidaConstant.BluetoothError paramAnonymousBluetoothError)
      {
        SfidaCharacteristic.this.serialExecutor.execute(new Runnable()
        {
          public void run()
          {
            SfidaCharacteristic.this.nativeWriteCompleteCallback(paramAnonymousBoolean, paramAnonymousBluetoothError.getInt());
          }
        });
      }
    });
  }
  
  public void writeByteArray(final byte[] paramArrayOfByte, final CompletionCallback paramCompletionCallback)
  {
    this.serialExecutor.execute(new Runnable()
    {
      public void run()
      {
        SfidaCharacteristic.access$102(SfidaCharacteristic.this, paramCompletionCallback);
        SfidaCharacteristic.this.characteristic.setValue(paramArrayOfByte);
        boolean bool = false;
        int i = 0;
        for (;;)
        {
          if (i < 7)
          {
            bool = SfidaCharacteristic.this.gatt.writeCharacteristic(SfidaCharacteristic.this.characteristic);
            if (!bool) {}
          }
          else
          {
            if (!bool)
            {
              SfidaCharacteristic.this.onWriteCallback.onCompletion(false, SfidaConstant.BluetoothError.Unknown);
              SfidaCharacteristic.access$102(SfidaCharacteristic.this, null);
            }
            return;
          }
          try
          {
            Thread.sleep(250L);
            i += 1;
          }
          catch (InterruptedException localInterruptedException)
          {
            for (;;) {}
          }
        }
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/SfidaCharacteristic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */