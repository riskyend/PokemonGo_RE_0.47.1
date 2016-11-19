package com.google.android.gms.location.places;

import android.text.style.CharacterStyle;
import com.google.android.gms.common.data.Freezable;
import java.util.List;

public abstract interface AutocompletePrediction
  extends Freezable<AutocompletePrediction>
{
  @Deprecated
  public abstract String getDescription();
  
  public abstract CharSequence getFullText(CharacterStyle paramCharacterStyle);
  
  @Deprecated
  public abstract List<? extends Substring> getMatchedSubstrings();
  
  public abstract String getPlaceId();
  
  public abstract List<Integer> getPlaceTypes();
  
  public abstract CharSequence getPrimaryText(CharacterStyle paramCharacterStyle);
  
  public abstract CharSequence getSecondaryText(CharacterStyle paramCharacterStyle);
  
  @Deprecated
  public static abstract interface Substring
  {
    public abstract int getLength();
    
    public abstract int getOffset();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/AutocompletePrediction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */