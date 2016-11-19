package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzlb.zzb;

public final class zzc
  implements CredentialsApi
{
  public PendingResult<Status> delete(GoogleApiClient paramGoogleApiClient, final Credential paramCredential)
  {
    paramGoogleApiClient.zzb(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, zzh paramAnonymouszzh)
        throws RemoteException
      {
        paramAnonymouszzh.zza(new zzc.zza(this), new DeleteRequest(paramCredential));
      }
      
      protected Status zzd(Status paramAnonymousStatus)
      {
        return paramAnonymousStatus;
      }
    });
  }
  
  public PendingResult<Status> disableAutoSignIn(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zzb(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, zzh paramAnonymouszzh)
        throws RemoteException
      {
        paramAnonymouszzh.zza(new zzc.zza(this));
      }
      
      protected Status zzd(Status paramAnonymousStatus)
      {
        return paramAnonymousStatus;
      }
    });
  }
  
  public PendingResult<CredentialRequestResult> request(GoogleApiClient paramGoogleApiClient, final CredentialRequest paramCredentialRequest)
  {
    paramGoogleApiClient.zza(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, zzh paramAnonymouszzh)
        throws RemoteException
      {
        paramAnonymouszzh.zza(new zza()
        {
          public void zza(Status paramAnonymous2Status, Credential paramAnonymous2Credential)
          {
            zzc.1.this.zzb(new zzb(paramAnonymous2Status, paramAnonymous2Credential));
          }
          
          public void zzg(Status paramAnonymous2Status)
          {
            zzc.1.this.zzb(zzb.zzh(paramAnonymous2Status));
          }
        }, paramCredentialRequest);
      }
      
      protected CredentialRequestResult zzi(Status paramAnonymousStatus)
      {
        return zzb.zzh(paramAnonymousStatus);
      }
    });
  }
  
  public PendingResult<Status> save(GoogleApiClient paramGoogleApiClient, final Credential paramCredential)
  {
    paramGoogleApiClient.zzb(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, zzh paramAnonymouszzh)
        throws RemoteException
      {
        paramAnonymouszzh.zza(new zzc.zza(this), new SaveRequest(paramCredential));
      }
      
      protected Status zzd(Status paramAnonymousStatus)
      {
        return paramAnonymousStatus;
      }
    });
  }
  
  private static class zza
    extends zza
  {
    private zzlb.zzb<Status> zzSI;
    
    zza(zzlb.zzb<Status> paramzzb)
    {
      this.zzSI = paramzzb;
    }
    
    public void zzg(Status paramStatus)
    {
      this.zzSI.zzp(paramStatus);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/auth/api/credentials/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */