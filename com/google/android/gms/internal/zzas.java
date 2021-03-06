package com.google.android.gms.internal;

import java.io.IOException;

class zzas
  implements zzaq
{
  private zzrx zznG;
  private byte[] zznH;
  private final int zznI;
  
  public zzas(int paramInt)
  {
    this.zznI = paramInt;
    reset();
  }
  
  public void reset()
  {
    this.zznH = new byte[this.zznI];
    this.zznG = zzrx.zzC(this.zznH);
  }
  
  public byte[] zzac()
    throws IOException
  {
    int i = this.zznG.zzFD();
    if (i < 0) {
      throw new IOException();
    }
    if (i == 0) {
      return this.zznH;
    }
    byte[] arrayOfByte = new byte[this.zznH.length - i];
    System.arraycopy(this.zznH, 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public void zzb(int paramInt, long paramLong)
    throws IOException
  {
    this.zznG.zzb(paramInt, paramLong);
  }
  
  public void zzb(int paramInt, String paramString)
    throws IOException
  {
    this.zznG.zzb(paramInt, paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */