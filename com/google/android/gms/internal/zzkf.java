package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;

public class zzkf
  extends zzj<zzkg>
{
  public zzkf(Context paramContext, Looper paramLooper, zzf paramzzf, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 92, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  protected zzkg zzap(IBinder paramIBinder)
  {
    return zzkg.zza.zzaq(paramIBinder);
  }
  
  protected String zzfK()
  {
    return "com.google.android.gms.auth.api.consent.START";
  }
  
  protected String zzfL()
  {
    return "com.google.android.gms.auth.api.consent.internal.IConsentService";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzkf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */