package com.google.android.gms.ads.internal.util.client;

import android.util.Log;
import com.google.android.gms.internal.zzbu;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgr;

@zzgr
public final class zzb
{
  public static void e(String paramString)
  {
    if (zzN(6)) {
      Log.e("Ads", paramString);
    }
  }
  
  public static void v(String paramString)
  {
    if (zzN(2)) {
      Log.v("Ads", paramString);
    }
  }
  
  public static boolean zzN(int paramInt)
  {
    return ((paramInt >= 5) || (Log.isLoggable("Ads", paramInt))) && ((paramInt != 2) || (zzgU()));
  }
  
  public static void zza(String paramString, Throwable paramThrowable)
  {
    if (zzN(3)) {
      Log.d("Ads", paramString, paramThrowable);
    }
  }
  
  public static void zzaF(String paramString)
  {
    if (zzN(3)) {
      Log.d("Ads", paramString);
    }
  }
  
  public static void zzaG(String paramString)
  {
    if (zzN(4)) {
      Log.i("Ads", paramString);
    }
  }
  
  public static void zzaH(String paramString)
  {
    if (zzN(5)) {
      Log.w("Ads", paramString);
    }
  }
  
  public static void zzb(String paramString, Throwable paramThrowable)
  {
    if (zzN(6)) {
      Log.e("Ads", paramString, paramThrowable);
    }
  }
  
  public static void zzc(String paramString, Throwable paramThrowable)
  {
    if (zzN(4)) {
      Log.i("Ads", paramString, paramThrowable);
    }
  }
  
  public static void zzd(String paramString, Throwable paramThrowable)
  {
    if (zzN(5)) {
      Log.w("Ads", paramString, paramThrowable);
    }
  }
  
  public static boolean zzgU()
  {
    return ((Boolean)zzby.zzvl.get()).booleanValue();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/util/client/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */