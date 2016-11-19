package com.nianticlabs.pokemongoplus.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.nianticlabs.pokemongoplus.bridge.ClientBridge;

public class ClientService
{
  private static final String TAG = ClientService.class.getSimpleName();
  static ClientBridge pgpClientBridge = null;
  
  public static void startClientService(Context paramContext, ClientBridge paramClientBridge)
  {
    pgpClientBridge = paramClientBridge;
    String str = paramContext.getApplicationContext().getPackageName();
    Log.i(TAG, "package: " + str);
    if (paramContext.bindService(new Intent("com.nianticlabs.pokemongoplus.service.BackgroundService").setPackage(str), paramClientBridge, 1))
    {
      Log.i(TAG, "Started BackgroundService");
      return;
    }
    Log.e(TAG, "Failed to start BackgroundService");
  }
  
  public static void stopClientService(Context paramContext)
  {
    paramContext.unbindService(pgpClientBridge);
    pgpClientBridge = null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/service/ClientService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */