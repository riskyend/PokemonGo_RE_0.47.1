package com.nianticlabs.pokemongoplus.ble.callback;

import com.nianticlabs.pokemongoplus.ble.SfidaConstant.CentralState;

public abstract interface CentralStateCallback
{
  public abstract void OnStateChanged(SfidaConstant.CentralState paramCentralState);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/ble/callback/CentralStateCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */