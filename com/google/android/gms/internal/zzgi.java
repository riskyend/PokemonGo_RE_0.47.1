package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;

@zzgr
public class zzgi
  extends zzgc
  implements zzja.zza
{
  zzgi(Context paramContext, zzhs.zza paramzza, zziz paramzziz, zzgg.zza paramzza1)
  {
    super(paramContext, paramzza, paramzziz, paramzza1);
  }
  
  protected void zzfs()
  {
    if (this.zzDf.errorCode != -2) {
      return;
    }
    this.zzoM.zzhe().zza(this);
    zzfz();
    zzb.v("Loading HTML in WebView.");
    this.zzoM.loadDataWithBaseURL(zzp.zzbv().zzaz(this.zzDf.zzBF), this.zzDf.body, "text/html", "UTF-8", null);
  }
  
  protected void zzfz() {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzgi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */