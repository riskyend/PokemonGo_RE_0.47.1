package com.google.android.gms.internal;

import java.io.IOException;

public final class zzsh
{
  public static final double[] zzbiA = new double[0];
  public static final boolean[] zzbiB = new boolean[0];
  public static final String[] zzbiC = new String[0];
  public static final byte[][] zzbiD = new byte[0][];
  public static final byte[] zzbiE = new byte[0];
  public static final int[] zzbix = new int[0];
  public static final long[] zzbiy = new long[0];
  public static final float[] zzbiz = new float[0];
  
  static int zzD(int paramInt1, int paramInt2)
  {
    return paramInt1 << 3 | paramInt2;
  }
  
  public static boolean zzb(zzrw paramzzrw, int paramInt)
    throws IOException
  {
    return paramzzrw.zzlA(paramInt);
  }
  
  public static final int zzc(zzrw paramzzrw, int paramInt)
    throws IOException
  {
    int i = 1;
    int j = paramzzrw.getPosition();
    paramzzrw.zzlA(paramInt);
    while (paramzzrw.zzFo() == paramInt)
    {
      paramzzrw.zzlA(paramInt);
      i += 1;
    }
    paramzzrw.zzlE(j);
    return i;
  }
  
  static int zzlU(int paramInt)
  {
    return paramInt & 0x7;
  }
  
  public static int zzlV(int paramInt)
  {
    return paramInt >>> 3;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzsh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */