package com.nianticlabs.pokemongoplus.ble.callback;

import com.nianticlabs.pokemongoplus.ble.SfidaConstant.BluetoothError;

public abstract interface CompletionCallback
{
  public abstract void onCompletion(boolean paramBoolean, SfidaConstant.BluetoothError paramBluetoothError);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/ble/callback/CompletionCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */