package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzj;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzgr;

@zzgr
public final class zzd
  extends zzg<zzr>
{
  private static final zzd zzsA = new zzd();
  
  private zzd()
  {
    super("com.google.android.gms.ads.AdLoaderBuilderCreatorImpl");
  }
  
  public static zzq zza(Context paramContext, String paramString, zzel paramzzel)
  {
    Object localObject;
    if (zzl.zzcF().zzR(paramContext))
    {
      zzq localzzq = zzsA.zzb(paramContext, paramString, paramzzel);
      localObject = localzzq;
      if (localzzq != null) {}
    }
    else
    {
      zzb.zzaF("Using AdLoader from the client jar.");
      localObject = new zzj(paramContext, paramString, paramzzel, new VersionInfoParcel(8115000, 8115000, true));
    }
    return (zzq)localObject;
  }
  
  private zzq zzb(Context paramContext, String paramString, zzel paramzzel)
  {
    try
    {
      com.google.android.gms.dynamic.zzd localzzd = zze.zzy(paramContext);
      paramContext = zzq.zza.zzi(((zzr)zzas(paramContext)).zza(localzzd, paramString, paramzzel, 8115000));
      return paramContext;
    }
    catch (RemoteException paramContext)
    {
      zzb.zzd("Could not create remote builder for AdLoader.", paramContext);
      return null;
    }
    catch (zzg.zza paramContext)
    {
      for (;;)
      {
        zzb.zzd("Could not create remote builder for AdLoader.", paramContext);
      }
    }
  }
  
  protected zzr zzc(IBinder paramIBinder)
  {
    return zzr.zza.zzj(paramIBinder);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/client/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */