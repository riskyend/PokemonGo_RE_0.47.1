package com.nianticlabs.pokemongoplus;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import com.nianticlabs.pokemongoplus.ble.Characteristic;
import com.nianticlabs.pokemongoplus.ble.Service;
import com.nianticlabs.pokemongoplus.ble.callback.CompletionCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SfidaService
  extends Service
{
  private ArrayList<SfidaCharacteristic> characteristicRef = new ArrayList();
  private long nativeHandle;
  private final HandlerExecutor serialExecutor;
  private BluetoothGattService service;
  
  public SfidaService(HandlerExecutor paramHandlerExecutor, BluetoothGattService paramBluetoothGattService, BluetoothGatt paramBluetoothGatt)
  {
    this.service = paramBluetoothGattService;
    this.serialExecutor = paramHandlerExecutor;
    paramHandlerExecutor.maybeAssertOnThread();
    paramBluetoothGattService = paramBluetoothGattService.getCharacteristics().iterator();
    while (paramBluetoothGattService.hasNext())
    {
      SfidaCharacteristic localSfidaCharacteristic = new SfidaCharacteristic(paramHandlerExecutor, (BluetoothGattCharacteristic)paramBluetoothGattService.next(), paramBluetoothGatt);
      this.characteristicRef.add(localSfidaCharacteristic);
    }
  }
  
  public void discoverCharacteristics(CompletionCallback paramCompletionCallback) {}
  
  /* Error */
  public SfidaCharacteristic getCharacteristic(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 74	com/nianticlabs/pokemongoplus/SfidaService:getCharacteristicCount	()I
    //   6: istore_2
    //   7: iload_1
    //   8: iload_2
    //   9: iconst_1
    //   10: isub
    //   11: if_icmple +9 -> 20
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_3
    //   19: areturn
    //   20: aload_0
    //   21: getfield 23	com/nianticlabs/pokemongoplus/SfidaService:characteristicRef	Ljava/util/ArrayList;
    //   24: iload_1
    //   25: invokevirtual 78	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   28: checkcast 52	com/nianticlabs/pokemongoplus/SfidaCharacteristic
    //   31: astore_3
    //   32: goto -16 -> 16
    //   35: astore_3
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_3
    //   39: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	SfidaService
    //   0	40	1	paramInt	int
    //   6	5	2	i	int
    //   15	17	3	localSfidaCharacteristic	SfidaCharacteristic
    //   35	4	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	35	finally
    //   20	32	35	finally
  }
  
  public SfidaCharacteristic getCharacteristic(String paramString)
  {
    this.serialExecutor.maybeAssertOnThread();
    int j = getCharacteristicCount();
    int i = 0;
    while (i < j)
    {
      SfidaCharacteristic localSfidaCharacteristic = (SfidaCharacteristic)this.characteristicRef.get(i);
      if (localSfidaCharacteristic.getUuid().equalsIgnoreCase(paramString)) {
        return localSfidaCharacteristic;
      }
      i += 1;
    }
    return null;
  }
  
  public int getCharacteristicCount()
  {
    try
    {
      int i = this.characteristicRef.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getUuid()
  {
    this.serialExecutor.maybeAssertOnThread();
    return this.service.getUuid().toString();
  }
  
  public boolean isPrimary()
  {
    return false;
  }
  
  public void onCharacteristicChanged(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic)
  {
    try
    {
      this.serialExecutor.maybeAssertOnThread();
      paramBluetoothGatt = paramBluetoothGattCharacteristic.getUuid().toString();
      paramBluetoothGattCharacteristic = this.characteristicRef.iterator();
      while (paramBluetoothGattCharacteristic.hasNext())
      {
        SfidaCharacteristic localSfidaCharacteristic = (SfidaCharacteristic)paramBluetoothGattCharacteristic.next();
        if (localSfidaCharacteristic.getUuid().equals(paramBluetoothGatt)) {
          localSfidaCharacteristic.onCharacteristicChanged();
        }
      }
      return;
    }
    finally {}
  }
  
  public void onCharacteristicRead(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, int paramInt)
  {
    try
    {
      this.serialExecutor.maybeAssertOnThread();
      paramBluetoothGatt = paramBluetoothGattCharacteristic.getUuid().toString();
      paramBluetoothGattCharacteristic = this.characteristicRef.iterator();
      while (paramBluetoothGattCharacteristic.hasNext())
      {
        SfidaCharacteristic localSfidaCharacteristic = (SfidaCharacteristic)paramBluetoothGattCharacteristic.next();
        if (localSfidaCharacteristic.getUuid().equals(paramBluetoothGatt)) {
          localSfidaCharacteristic.onCharacteristicRead(paramInt);
        }
      }
      return;
    }
    finally {}
  }
  
  public void onCharacteristicWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, int paramInt)
  {
    try
    {
      this.serialExecutor.maybeAssertOnThread();
      paramBluetoothGatt = paramBluetoothGattCharacteristic.getUuid().toString();
      paramBluetoothGattCharacteristic = this.characteristicRef.iterator();
      while (paramBluetoothGattCharacteristic.hasNext())
      {
        SfidaCharacteristic localSfidaCharacteristic = (SfidaCharacteristic)paramBluetoothGattCharacteristic.next();
        if (localSfidaCharacteristic.getUuid().equals(paramBluetoothGatt)) {
          localSfidaCharacteristic.onCharacteristicWrite(paramInt);
        }
      }
      return;
    }
    finally {}
  }
  
  public void onDescriptorWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattDescriptor paramBluetoothGattDescriptor, int paramInt)
  {
    try
    {
      this.serialExecutor.maybeAssertOnThread();
      paramBluetoothGatt = paramBluetoothGattDescriptor.getCharacteristic().getUuid().toString();
      Iterator localIterator = this.characteristicRef.iterator();
      while (localIterator.hasNext())
      {
        SfidaCharacteristic localSfidaCharacteristic = (SfidaCharacteristic)localIterator.next();
        if (localSfidaCharacteristic.getUuid().equals(paramBluetoothGatt)) {
          localSfidaCharacteristic.onDescriptorWrite(paramBluetoothGattDescriptor, paramInt);
        }
      }
      return;
    }
    finally {}
  }
  
  public void onDestroy()
  {
    this.serialExecutor.maybeAssertOnThread();
    Iterator localIterator = this.characteristicRef.iterator();
    while (localIterator.hasNext())
    {
      Characteristic localCharacteristic = (Characteristic)localIterator.next();
      if ((localCharacteristic instanceof SfidaCharacteristic)) {
        ((SfidaCharacteristic)localCharacteristic).onDestroy();
      }
    }
    this.characteristicRef.clear();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/SfidaService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */