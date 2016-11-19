package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@zzgr
public class zzbv
{
  private final Collection<zzbu> zzug = new ArrayList();
  private final Collection<zzbu<String>> zzuh = new ArrayList();
  private final Collection<zzbu<String>> zzui = new ArrayList();
  
  public void zza(zzbu paramzzbu)
  {
    this.zzug.add(paramzzbu);
  }
  
  public void zzb(zzbu<String> paramzzbu)
  {
    this.zzuh.add(paramzzbu);
  }
  
  public void zzc(zzbu<String> paramzzbu)
  {
    this.zzui.add(paramzzbu);
  }
  
  public List<String> zzdf()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.zzuh.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)((zzbu)localIterator.next()).get();
      if (str != null) {
        localArrayList.add(str);
      }
    }
    return localArrayList;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzbv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */