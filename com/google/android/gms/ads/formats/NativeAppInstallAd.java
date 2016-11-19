package com.google.android.gms.ads.formats;

import android.os.Bundle;
import java.util.List;

public abstract class NativeAppInstallAd
  extends NativeAd
{
  public abstract CharSequence getBody();
  
  public abstract CharSequence getCallToAction();
  
  public abstract Bundle getExtras();
  
  public abstract CharSequence getHeadline();
  
  public abstract NativeAd.Image getIcon();
  
  public abstract List<NativeAd.Image> getImages();
  
  public abstract CharSequence getPrice();
  
  public abstract Double getStarRating();
  
  public abstract CharSequence getStore();
  
  public static abstract interface OnAppInstallAdLoadedListener
  {
    public abstract void onAppInstallAdLoaded(NativeAppInstallAd paramNativeAppInstallAd);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/formats/NativeAppInstallAd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */