package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import java.util.Date;
import java.util.Set;

@zzgr
public final class zzes
  implements MediationAdRequest
{
  private final Date zzaT;
  private final Set<String> zzaV;
  private final boolean zzaW;
  private final Location zzaX;
  private final int zzsR;
  private final int zzzI;
  
  public zzes(Date paramDate, int paramInt1, Set<String> paramSet, Location paramLocation, boolean paramBoolean, int paramInt2)
  {
    this.zzaT = paramDate;
    this.zzsR = paramInt1;
    this.zzaV = paramSet;
    this.zzaX = paramLocation;
    this.zzaW = paramBoolean;
    this.zzzI = paramInt2;
  }
  
  public Date getBirthday()
  {
    return this.zzaT;
  }
  
  public int getGender()
  {
    return this.zzsR;
  }
  
  public Set<String> getKeywords()
  {
    return this.zzaV;
  }
  
  public Location getLocation()
  {
    return this.zzaX;
  }
  
  public boolean isTesting()
  {
    return this.zzaW;
  }
  
  public int taggedForChildDirectedTreatment()
  {
    return this.zzzI;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */