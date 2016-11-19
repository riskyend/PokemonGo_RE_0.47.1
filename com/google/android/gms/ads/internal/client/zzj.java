package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.internal.zzgr;

@zzgr
public final class zzj
  extends zzu.zza
{
  private final AppEventListener zztj;
  
  public zzj(AppEventListener paramAppEventListener)
  {
    this.zztj = paramAppEventListener;
  }
  
  public void onAppEvent(String paramString1, String paramString2)
  {
    this.zztj.onAppEvent(paramString1, paramString2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/client/zzj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */