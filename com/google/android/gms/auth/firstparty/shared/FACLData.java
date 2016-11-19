package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class FACLData
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  final int version;
  FACLConfig zzTD;
  String zzTE;
  boolean zzTF;
  String zzTG;
  
  FACLData(int paramInt, FACLConfig paramFACLConfig, String paramString1, boolean paramBoolean, String paramString2)
  {
    this.version = paramInt;
    this.zzTD = paramFACLConfig;
    this.zzTE = paramString1;
    this.zzTF = paramBoolean;
    this.zzTG = paramString2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/auth/firstparty/shared/FACLData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */