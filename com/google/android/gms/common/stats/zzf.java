package com.google.android.gms.common.stats;

public abstract class zzf
{
  public static int zzahY = 0;
  public static int zzahZ = 1;
  
  public abstract int getEventType();
  
  public abstract long getTimeMillis();
  
  public String toString()
  {
    return getTimeMillis() + "\t" + getEventType() + "\t" + zzqd() + zzqg();
  }
  
  public abstract long zzqd();
  
  public abstract String zzqg();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/common/stats/zzf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */