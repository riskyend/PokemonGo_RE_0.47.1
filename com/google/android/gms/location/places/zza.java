package com.google.android.gms.location.places;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class zza
{
  static <E> List<E> zzf(Collection<E> paramCollection)
  {
    if ((paramCollection == null) || (paramCollection.isEmpty())) {
      return Collections.emptyList();
    }
    return new ArrayList(paramCollection);
  }
  
  static <E> Set<E> zzs(List<E> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty())) {
      return Collections.emptySet();
    }
    return Collections.unmodifiableSet(new HashSet(paramList));
  }
  
  public abstract Set<String> getPlaceIds();
  
  public boolean isRestrictedToPlacesOpenNow()
  {
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */