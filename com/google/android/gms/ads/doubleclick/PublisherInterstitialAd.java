package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.internal.client.zzaa;

public final class PublisherInterstitialAd
{
  private final zzaa zznU = new zzaa(paramContext, this);
  
  public PublisherInterstitialAd(Context paramContext) {}
  
  public AdListener getAdListener()
  {
    return this.zznU.getAdListener();
  }
  
  public String getAdUnitId()
  {
    return this.zznU.getAdUnitId();
  }
  
  public AppEventListener getAppEventListener()
  {
    return this.zznU.getAppEventListener();
  }
  
  public String getMediationAdapterClassName()
  {
    return this.zznU.getMediationAdapterClassName();
  }
  
  public OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener()
  {
    return this.zznU.getOnCustomRenderedAdLoadedListener();
  }
  
  public boolean isLoaded()
  {
    return this.zznU.isLoaded();
  }
  
  public void loadAd(PublisherAdRequest paramPublisherAdRequest)
  {
    this.zznU.zza(paramPublisherAdRequest.zzaF());
  }
  
  public void setAdListener(AdListener paramAdListener)
  {
    this.zznU.setAdListener(paramAdListener);
  }
  
  public void setAdUnitId(String paramString)
  {
    this.zznU.setAdUnitId(paramString);
  }
  
  public void setAppEventListener(AppEventListener paramAppEventListener)
  {
    this.zznU.setAppEventListener(paramAppEventListener);
  }
  
  public void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener paramOnCustomRenderedAdLoadedListener)
  {
    this.zznU.setOnCustomRenderedAdLoadedListener(paramOnCustomRenderedAdLoadedListener);
  }
  
  public void show()
  {
    this.zznU.show();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/doubleclick/PublisherInterstitialAd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */