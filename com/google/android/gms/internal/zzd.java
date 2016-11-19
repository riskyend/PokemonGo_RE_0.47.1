package com.google.android.gms.internal;

public class zzd
  implements zzo
{
  private int zzo;
  private int zzp;
  private final int zzq;
  private final float zzr;
  
  public zzd()
  {
    this(2500, 1, 1.0F);
  }
  
  public zzd(int paramInt1, int paramInt2, float paramFloat)
  {
    this.zzo = paramInt1;
    this.zzq = paramInt2;
    this.zzr = paramFloat;
  }
  
  public void zza(zzr paramzzr)
    throws zzr
  {
    this.zzp += 1;
    this.zzo = ((int)(this.zzo + this.zzo * this.zzr));
    if (!zzf()) {
      throw paramzzr;
    }
  }
  
  public int zzd()
  {
    return this.zzo;
  }
  
  public int zze()
  {
    return this.zzp;
  }
  
  protected boolean zzf()
  {
    return this.zzp <= this.zzq;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */