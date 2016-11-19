package com.nianticlabs.pokemongoplus.ble;

import com.nianticlabs.pokemongoplus.ble.callback.CompletionCallback;
import com.nianticlabs.pokemongoplus.ble.callback.ConnectCallback;

public abstract class Peripheral
{
  public abstract void connect(ConnectCallback paramConnectCallback);
  
  public abstract void disconnect(ConnectCallback paramConnectCallback);
  
  public abstract void discoverServices(CompletionCallback paramCompletionCallback);
  
  public abstract long getAdvertisingServiceDataLongValue(String paramString);
  
  public abstract String getIdentifier();
  
  public abstract String getName();
  
  public abstract Service getService(int paramInt);
  
  public abstract int getServiceCount();
  
  public abstract SfidaConstant.PeripheralState getState();
  
  public abstract void setScanRecord(byte[] paramArrayOfByte);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/ble/Peripheral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */