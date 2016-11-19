package com.nianticlabs.nia.location;

import android.location.GpsSatellite;

public abstract interface GpsProvider
  extends Provider
{
  public static abstract interface GpsProviderListener
    extends Provider.ProviderListener
  {
    public abstract void onGpsStatusUpdate(int paramInt, GpsSatellite[] paramArrayOfGpsSatellite);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/location/GpsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */