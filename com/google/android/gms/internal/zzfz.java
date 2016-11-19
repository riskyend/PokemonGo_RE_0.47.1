package com.google.android.gms.internal;

import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.purchase.InAppPurchaseResult;

@zzgr
public class zzfz
  implements InAppPurchaseResult
{
  private final zzfv zzDc;
  
  public zzfz(zzfv paramzzfv)
  {
    this.zzDc = paramzzfv;
  }
  
  public void finishPurchase()
  {
    try
    {
      this.zzDc.finishPurchase();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.zzd("Could not forward finishPurchase to InAppPurchaseResult", localRemoteException);
    }
  }
  
  public String getProductId()
  {
    try
    {
      String str = this.zzDc.getProductId();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.zzd("Could not forward getProductId to InAppPurchaseResult", localRemoteException);
    }
    return null;
  }
  
  public Intent getPurchaseData()
  {
    try
    {
      Intent localIntent = this.zzDc.getPurchaseData();
      return localIntent;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.zzd("Could not forward getPurchaseData to InAppPurchaseResult", localRemoteException);
    }
    return null;
  }
  
  public int getResultCode()
  {
    try
    {
      int i = this.zzDc.getResultCode();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.zzd("Could not forward getPurchaseData to InAppPurchaseResult", localRemoteException);
    }
    return 0;
  }
  
  public boolean isVerified()
  {
    try
    {
      boolean bool = this.zzDc.isVerified();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      zzb.zzd("Could not forward isVerified to InAppPurchaseResult", localRemoteException);
    }
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzfz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */