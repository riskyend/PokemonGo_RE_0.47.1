package com.nianticlabs.nia.location;

import android.location.Location;
import com.nianticlabs.nia.contextservice.ServiceStatus;

public abstract interface Provider
{
  public abstract void onPause();
  
  public abstract void onResume();
  
  public abstract void onStart();
  
  public abstract void onStop();
  
  public abstract void setListener(ProviderListener paramProviderListener);
  
  public static abstract interface ProviderListener
  {
    public abstract void onProviderLocation(Location paramLocation);
    
    public abstract void onProviderStatus(ServiceStatus paramServiceStatus);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/location/Provider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */