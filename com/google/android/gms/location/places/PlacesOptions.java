package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;

public final class PlacesOptions
  implements Api.ApiOptions.Optional
{
  public final String zzaGG;
  
  private PlacesOptions(Builder paramBuilder)
  {
    this.zzaGG = Builder.zza(paramBuilder);
  }
  
  public static class Builder
  {
    private String zzaGH;
    
    public PlacesOptions build()
    {
      return new PlacesOptions(this, null);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/PlacesOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */