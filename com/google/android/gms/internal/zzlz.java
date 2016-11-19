package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public final class zzlz
  implements zzly
{
  public PendingResult<Status> zzb(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zzb(new zzma.zza(paramGoogleApiClient)
    {
      protected void zza(zzmb paramAnonymouszzmb)
        throws RemoteException
      {
        ((zzmd)paramAnonymouszzmb.zzpc()).zza(new zzlz.zza(this));
      }
    });
  }
  
  private static class zza
    extends zzlw
  {
    private final zzlb.zzb<Status> zzagy;
    
    public zza(zzlb.zzb<Status> paramzzb)
    {
      this.zzagy = paramzzb;
    }
    
    public void zzbN(int paramInt)
      throws RemoteException
    {
      this.zzagy.zzp(new Status(paramInt));
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzlz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */