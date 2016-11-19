package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.internal.zzgr;

@zzgr
public final class zzc
  extends zzo.zza
{
  private final AdListener zzsz;
  
  public zzc(AdListener paramAdListener)
  {
    this.zzsz = paramAdListener;
  }
  
  public void onAdClosed()
  {
    this.zzsz.onAdClosed();
  }
  
  public void onAdFailedToLoad(int paramInt)
  {
    this.zzsz.onAdFailedToLoad(paramInt);
  }
  
  public void onAdLeftApplication()
  {
    this.zzsz.onAdLeftApplication();
  }
  
  public void onAdLoaded()
  {
    this.zzsz.onAdLoaded();
  }
  
  public void onAdOpened()
  {
    this.zzsz.onAdOpened();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/client/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */