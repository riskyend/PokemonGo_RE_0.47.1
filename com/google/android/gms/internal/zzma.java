package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

abstract class zzma<R extends Result>
  extends zzlb.zza<R, zzmb>
{
  public zzma(GoogleApiClient paramGoogleApiClient)
  {
    super(zzlx.zzRk, paramGoogleApiClient);
  }
  
  static abstract class zza
    extends zzma<Status>
  {
    public zza(GoogleApiClient paramGoogleApiClient)
    {
      super();
    }
    
    public Status zzd(Status paramStatus)
    {
      return paramStatus;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */