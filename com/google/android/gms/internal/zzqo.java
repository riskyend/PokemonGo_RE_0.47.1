package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import java.util.List;

public class zzqo
  extends zzj<zzqm>
{
  private final Context mContext;
  
  public zzqo(Context paramContext, Looper paramLooper, zzf paramzzf, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 45, paramzzf, paramConnectionCallbacks, paramOnConnectionFailedListener);
    this.mContext = paramContext;
  }
  
  private String zzmi()
  {
    try
    {
      Object localObject = this.mContext.getPackageManager();
      if (localObject == null) {
        return null;
      }
      localObject = ((PackageManager)localObject).getApplicationInfo(this.mContext.getPackageName(), 128);
      if (localObject == null) {
        return null;
      }
      localObject = ((ApplicationInfo)localObject).metaData;
      if (localObject == null) {
        return null;
      }
      localObject = (String)((Bundle)localObject).get("com.google.android.safetynet.API_KEY");
      return (String)localObject;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public void zza(zzql paramzzql, List<Integer> paramList, int paramInt, String paramString)
    throws RemoteException
  {
    int[] arrayOfInt = new int[paramList.size()];
    int i = 0;
    while (i < paramList.size())
    {
      arrayOfInt[i] = ((Integer)paramList.get(i)).intValue();
      i += 1;
    }
    ((zzqm)zzpc()).zza(paramzzql, zzmi(), arrayOfInt, paramInt, paramString);
  }
  
  public void zza(zzql paramzzql, byte[] paramArrayOfByte)
    throws RemoteException
  {
    ((zzqm)zzpc()).zza(paramzzql, paramArrayOfByte);
  }
  
  protected zzqm zzdI(IBinder paramIBinder)
  {
    return zzqm.zza.zzdH(paramIBinder);
  }
  
  protected String zzfK()
  {
    return "com.google.android.gms.safetynet.service.START";
  }
  
  protected String zzfL()
  {
    return "com.google.android.gms.safetynet.internal.ISafetyNetService";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzqo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */