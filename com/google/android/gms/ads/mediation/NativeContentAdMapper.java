package com.google.android.gms.ads.mediation;

import com.google.android.gms.ads.formats.NativeAd.Image;
import java.util.List;

public abstract class NativeContentAdMapper
  extends NativeAdMapper
{
  private NativeAd.Image zzKK;
  private String zzwo;
  private List<NativeAd.Image> zzwp;
  private String zzwq;
  private String zzws;
  private String zzwz;
  
  public final String getAdvertiser()
  {
    return this.zzwz;
  }
  
  public final String getBody()
  {
    return this.zzwq;
  }
  
  public final String getCallToAction()
  {
    return this.zzws;
  }
  
  public final String getHeadline()
  {
    return this.zzwo;
  }
  
  public final List<NativeAd.Image> getImages()
  {
    return this.zzwp;
  }
  
  public final NativeAd.Image getLogo()
  {
    return this.zzKK;
  }
  
  public final void setAdvertiser(String paramString)
  {
    this.zzwz = paramString;
  }
  
  public final void setBody(String paramString)
  {
    this.zzwq = paramString;
  }
  
  public final void setCallToAction(String paramString)
  {
    this.zzws = paramString;
  }
  
  public final void setHeadline(String paramString)
  {
    this.zzwo = paramString;
  }
  
  public final void setImages(List<NativeAd.Image> paramList)
  {
    this.zzwp = paramList;
  }
  
  public final void setLogo(NativeAd.Image paramImage)
  {
    this.zzKK = paramImage;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/mediation/NativeContentAdMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */