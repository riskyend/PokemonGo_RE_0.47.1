package com.nianticlabs.pokemongoplus.ble;

import com.nianticlabs.pokemongoplus.ble.callback.CompletionCallback;

public abstract class Service
{
  public abstract void discoverCharacteristics(CompletionCallback paramCompletionCallback);
  
  public abstract int getCharacteristicCount();
  
  public abstract String getUuid();
  
  public abstract boolean isPrimary();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/ble/Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */