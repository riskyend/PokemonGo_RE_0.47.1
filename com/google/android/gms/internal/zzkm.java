package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.auth.api.proxy.ProxyResponse;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.zzx;

public class zzkm
  implements ProxyApi
{
  public PendingResult<ProxyApi.ProxyResult> performProxyRequest(GoogleApiClient paramGoogleApiClient, final ProxyRequest paramProxyRequest)
  {
    zzx.zzw(paramGoogleApiClient);
    zzx.zzw(paramProxyRequest);
    paramGoogleApiClient.zzb(new zzkl(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, zzkk paramAnonymouszzkk)
        throws RemoteException
      {
        paramAnonymouszzkk.zza(new zzkh()
        {
          public void zza(ProxyResponse paramAnonymous2ProxyResponse)
          {
            zzkm.1.this.zzb(new zzkn(paramAnonymous2ProxyResponse));
          }
        }, paramProxyRequest);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzkm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */