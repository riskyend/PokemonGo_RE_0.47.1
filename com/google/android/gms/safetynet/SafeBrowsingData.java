package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class SafeBrowsingData
  implements SafeParcelable
{
  public static final Parcelable.Creator<SafeBrowsingData> CREATOR = new zzb();
  public final int mVersionCode;
  private String zzaUB;
  
  SafeBrowsingData(int paramInt, String paramString)
  {
    this.mVersionCode = paramInt;
    this.zzaUB = paramString;
  }
  
  public SafeBrowsingData(String paramString)
  {
    this(1, paramString);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getMetadata()
  {
    return this.zzaUB;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/safetynet/SafeBrowsingData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */