package com.google.android.gms.location.places;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.maps.model.LatLngBounds;

public abstract interface GeoDataApi
{
  public abstract PendingResult<PlaceBuffer> addPlace(GoogleApiClient paramGoogleApiClient, AddPlaceRequest paramAddPlaceRequest);
  
  public abstract PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(GoogleApiClient paramGoogleApiClient, String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter);
  
  public abstract PendingResult<PlaceBuffer> getPlaceById(GoogleApiClient paramGoogleApiClient, String... paramVarArgs);
  
  public abstract PendingResult<PlacePhotoMetadataResult> getPlacePhotos(GoogleApiClient paramGoogleApiClient, String paramString);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/GeoDataApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */