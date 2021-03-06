package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.auth.api.Auth.AuthCredentialsOptions;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;

public final class zze
  extends zzj<zzh>
{
  private final Auth.AuthCredentialsOptions zzSJ;
  
  public zze(Context paramContext, Looper paramLooper, zzf paramzzf, Auth.AuthCredentialsOptions paramAuthCredentialsOptions, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 68, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
    this.zzSJ = paramAuthCredentialsOptions;
  }
  
  protected zzh zzar(IBinder paramIBinder)
  {
    return zzh.zza.zzat(paramIBinder);
  }
  
  protected String zzfK()
  {
    return "com.google.android.gms.auth.api.credentials.service.START";
  }
  
  protected String zzfL()
  {
    return "com.google.android.gms.auth.api.credentials.internal.ICredentialsService";
  }
  
  protected Bundle zzly()
  {
    if (this.zzSJ == null) {
      return new Bundle();
    }
    return this.zzSJ.zzly();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/auth/api/credentials/internal/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */