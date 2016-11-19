package com.google.android.gms.location.places;

import com.google.android.gms.common.data.Freezable;

public abstract interface PlaceLikelihood
  extends Freezable<PlaceLikelihood>
{
  public abstract float getLikelihood();
  
  public abstract Place getPlace();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/PlaceLikelihood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */