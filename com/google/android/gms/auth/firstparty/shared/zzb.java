package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;

public class zzb
  implements Parcelable.Creator<FACLData>
{
  static void zza(FACLData paramFACLData, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramFACLData.version);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramFACLData.zzTD, paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 3, paramFACLData.zzTE, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 4, paramFACLData.zzTF);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 5, paramFACLData.zzTG, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzI(paramParcel, i);
  }
  
  public FACLData zzU(Parcel paramParcel)
  {
    boolean bool = false;
    String str1 = null;
    int j = zza.zzap(paramParcel);
    String str2 = null;
    FACLConfig localFACLConfig = null;
    int i = 0;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzao(paramParcel);
      switch (zza.zzbM(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        localFACLConfig = (FACLConfig)zza.zza(paramParcel, k, FACLConfig.CREATOR);
        break;
      case 3: 
        str2 = zza.zzp(paramParcel, k);
        break;
      case 4: 
        bool = zza.zzc(paramParcel, k);
        break;
      case 5: 
        str1 = zza.zzp(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new FACLData(i, localFACLConfig, str2, bool, str1);
  }
  
  public FACLData[] zzaL(int paramInt)
  {
    return new FACLData[paramInt];
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/auth/firstparty/shared/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */