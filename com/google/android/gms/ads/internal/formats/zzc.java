package com.google.android.gms.ads.internal.formats;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcm.zza;
import com.google.android.gms.internal.zzgr;

@zzgr
public class zzc
  extends zzcm.zza
{
  private final Uri mUri;
  private final Drawable zzwm;
  private final double zzwn;
  
  public zzc(Drawable paramDrawable, Uri paramUri, double paramDouble)
  {
    this.zzwm = paramDrawable;
    this.mUri = paramUri;
    this.zzwn = paramDouble;
  }
  
  public double getScale()
  {
    return this.zzwn;
  }
  
  public Uri getUri()
    throws RemoteException
  {
    return this.mUri;
  }
  
  public zzd zzdv()
    throws RemoteException
  {
    return zze.zzy(this.zzwm);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/formats/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */