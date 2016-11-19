package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

abstract class zzkl
  extends zzlb.zza<ProxyApi.ProxyResult, zzki>
{
  public zzkl(GoogleApiClient paramGoogleApiClient)
  {
    super(Auth.zzRE, paramGoogleApiClient);
  }
  
  protected abstract void zza(Context paramContext, zzkk paramzzkk)
    throws RemoteException;
  
  protected final void zza(zzki paramzzki)
    throws RemoteException
  {
    zza(paramzzki.getContext(), (zzkk)paramzzki.zzpc());
  }
  
  protected ProxyApi.ProxyResult zzj(Status paramStatus)
  {
    return new zzkn(paramStatus);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzkl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */