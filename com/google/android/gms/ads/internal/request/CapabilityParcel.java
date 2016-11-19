package com.google.android.gms.ads.internal.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class CapabilityParcel
  implements SafeParcelable
{
  public static final Parcelable.Creator<CapabilityParcel> CREATOR = new zzi();
  public final int versionCode;
  public final boolean zzFa;
  public final boolean zzFb;
  
  CapabilityParcel(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.versionCode = paramInt;
    this.zzFa = paramBoolean1;
    this.zzFb = paramBoolean2;
  }
  
  public CapabilityParcel(boolean paramBoolean1, boolean paramBoolean2)
  {
    this(1, paramBoolean1, paramBoolean2);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Bundle toBundle()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("iap_supported", this.zzFa);
    localBundle.putBoolean("default_iap_supported", this.zzFb);
    return localBundle;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/request/CapabilityParcel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */