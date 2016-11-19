package com.nianticlabs.pokemongoplus;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.util.Log;
import java.lang.reflect.Method;

public class SfidaUtils
{
  private static final String TAG = SfidaUtils.class.getSimpleName();
  
  public static String byteArrayToBitString(byte[] paramArrayOfByte)
  {
    Object localObject = "";
    int m = paramArrayOfByte.length;
    int i = 0;
    while (i < m)
    {
      int n = paramArrayOfByte[i];
      int j = 0;
      if (j < 8)
      {
        localObject = new StringBuilder().append((String)localObject);
        if ((128 >> j & n) != 0) {}
        for (int k = 1;; k = 0)
        {
          localObject = String.valueOf(k);
          j += 1;
          break;
        }
      }
      localObject = (String)localObject + " ";
      i += 1;
    }
    return (String)localObject;
  }
  
  public static String byteArrayToString(byte[] paramArrayOfByte)
  {
    String str = "";
    int j = paramArrayOfByte.length;
    int i = 0;
    while (i < j)
    {
      int k = paramArrayOfByte[i];
      str = str + String.valueOf(k);
      i += 1;
    }
    return str;
  }
  
  public static boolean checkBluetoothLeSupported(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
  }
  
  @TargetApi(19)
  public static void createBond(BluetoothDevice paramBluetoothDevice)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      Log.d(TAG, "createBond() Start Pairing...");
      paramBluetoothDevice.createBond();
      return;
    }
    try
    {
      Log.d(TAG, "createBond() Start Pairing...");
      paramBluetoothDevice.getClass().getMethod("createBond", (Class[])null).invoke(paramBluetoothDevice, (Object[])null);
      Log.d(TAG, "createBond() Pairing finished.");
      return;
    }
    catch (Exception paramBluetoothDevice)
    {
      Log.e(TAG, paramBluetoothDevice.getMessage());
    }
  }
  
  public static BluetoothManager getBluetoothManager(Context paramContext)
  {
    return (BluetoothManager)paramContext.getSystemService("bluetooth");
  }
  
  public static String getBondStateName(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return String.valueOf(paramInt);
    case 10: 
      return "BOND_NONE";
    case 11: 
      return "BOND_BONDING";
    }
    return "BOND_BONDED";
  }
  
  public static byte[] hexStringToByteArray(String paramString)
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
  
  public static boolean refreshDeviceCache(BluetoothGatt paramBluetoothGatt)
  {
    try
    {
      Method localMethod = paramBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
      if (localMethod != null)
      {
        boolean bool = ((Boolean)localMethod.invoke(paramBluetoothGatt, new Object[0])).booleanValue();
        return bool;
      }
    }
    catch (Exception paramBluetoothGatt)
    {
      Log.e(TAG, "An exception occurred while refreshing device");
    }
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/SfidaUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */