package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;

@zzgr
public final class zzgb
  extends zzfw.zza
{
  private final PlayStorePurchaseListener zztJ;
  
  public zzgb(PlayStorePurchaseListener paramPlayStorePurchaseListener)
  {
    this.zztJ = paramPlayStorePurchaseListener;
  }
  
  public boolean isValidPurchase(String paramString)
  {
    return this.zztJ.isValidPurchase(paramString);
  }
  
  public void zza(zzfv paramzzfv)
  {
    this.zztJ.onInAppPurchaseFinished(new zzfz(paramzzfv));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzgb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */