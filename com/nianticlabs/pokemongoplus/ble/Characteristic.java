package com.nianticlabs.pokemongoplus.ble;

import com.nianticlabs.pokemongoplus.ble.callback.CompletionCallback;

public abstract class Characteristic
{
  public abstract void disableNotify(CompletionCallback paramCompletionCallback);
  
  public abstract void enableNotify(CompletionCallback paramCompletionCallback);
  
  public abstract long getLongValue();
  
  public abstract String getUuid();
  
  public abstract byte[] getValue();
  
  public abstract void readValue(CompletionCallback paramCompletionCallback);
  
  public abstract void writeByteArray(byte[] paramArrayOfByte, CompletionCallback paramCompletionCallback);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/ble/Characteristic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */