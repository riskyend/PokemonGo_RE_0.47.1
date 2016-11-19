package com.nianticlabs.pokemongoplus.ble;

import com.nianticlabs.pokemongoplus.ble.callback.CentralStateCallback;
import com.nianticlabs.pokemongoplus.ble.callback.ScanCallback;

public abstract class BluetoothDriver
{
  private SfidaConstant.CentralState currentState = SfidaConstant.CentralState.Unknown;
  
  public abstract boolean IsScanning();
  
  public abstract int start(CentralStateCallback paramCentralStateCallback);
  
  public abstract void startScanning(String paramString, ScanCallback paramScanCallback);
  
  public abstract void stop(int paramInt);
  
  public abstract void stopScanning(String paramString);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/ble/BluetoothDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */