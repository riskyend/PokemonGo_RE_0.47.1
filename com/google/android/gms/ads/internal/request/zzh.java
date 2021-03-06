package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzh
  implements Parcelable.Creator<AdResponseParcel>
{
  static void zza(AdResponseParcel paramAdResponseParcel, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzaq(paramParcel);
    zzb.zzc(paramParcel, 1, paramAdResponseParcel.versionCode);
    zzb.zza(paramParcel, 2, paramAdResponseParcel.zzBF, false);
    zzb.zza(paramParcel, 3, paramAdResponseParcel.body, false);
    zzb.zzb(paramParcel, 4, paramAdResponseParcel.zzyY, false);
    zzb.zzc(paramParcel, 5, paramAdResponseParcel.errorCode);
    zzb.zzb(paramParcel, 6, paramAdResponseParcel.zzyZ, false);
    zzb.zza(paramParcel, 7, paramAdResponseParcel.zzEJ);
    zzb.zza(paramParcel, 8, paramAdResponseParcel.zzEK);
    zzb.zza(paramParcel, 9, paramAdResponseParcel.zzEL);
    zzb.zzb(paramParcel, 10, paramAdResponseParcel.zzEM, false);
    zzb.zza(paramParcel, 11, paramAdResponseParcel.zzzc);
    zzb.zzc(paramParcel, 12, paramAdResponseParcel.orientation);
    zzb.zza(paramParcel, 13, paramAdResponseParcel.zzEN, false);
    zzb.zza(paramParcel, 14, paramAdResponseParcel.zzEO);
    zzb.zza(paramParcel, 15, paramAdResponseParcel.zzEP, false);
    zzb.zza(paramParcel, 19, paramAdResponseParcel.zzER, false);
    zzb.zza(paramParcel, 18, paramAdResponseParcel.zzEQ);
    zzb.zza(paramParcel, 21, paramAdResponseParcel.zzES, false);
    zzb.zza(paramParcel, 23, paramAdResponseParcel.zzth);
    zzb.zza(paramParcel, 22, paramAdResponseParcel.zzET);
    zzb.zza(paramParcel, 25, paramAdResponseParcel.zzEU);
    zzb.zza(paramParcel, 24, paramAdResponseParcel.zzEv);
    zzb.zzc(paramParcel, 27, paramAdResponseParcel.zzEW);
    zzb.zza(paramParcel, 26, paramAdResponseParcel.zzEV);
    zzb.zza(paramParcel, 29, paramAdResponseParcel.zzEY, false);
    zzb.zza(paramParcel, 28, paramAdResponseParcel.zzEX, paramInt, false);
    zzb.zza(paramParcel, 31, paramAdResponseParcel.zzti);
    zzb.zza(paramParcel, 30, paramAdResponseParcel.zzEZ, false);
    zzb.zzI(paramParcel, i);
  }
  
  public AdResponseParcel[] zzE(int paramInt)
  {
    return new AdResponseParcel[paramInt];
  }
  
  public AdResponseParcel zzj(Parcel paramParcel)
  {
    int n = zza.zzap(paramParcel);
    int m = 0;
    String str8 = null;
    String str7 = null;
    ArrayList localArrayList3 = null;
    int k = 0;
    ArrayList localArrayList2 = null;
    long l4 = 0L;
    boolean bool8 = false;
    long l3 = 0L;
    ArrayList localArrayList1 = null;
    long l2 = 0L;
    int j = 0;
    String str6 = null;
    long l1 = 0L;
    String str5 = null;
    boolean bool7 = false;
    String str4 = null;
    String str3 = null;
    boolean bool6 = false;
    boolean bool5 = false;
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    int i = 0;
    LargeParcelTeleporter localLargeParcelTeleporter = null;
    String str2 = null;
    String str1 = null;
    boolean bool1 = false;
    while (paramParcel.dataPosition() < n)
    {
      int i1 = zza.zzao(paramParcel);
      switch (zza.zzbM(i1))
      {
      case 16: 
      case 17: 
      case 20: 
      default: 
        zza.zzb(paramParcel, i1);
        break;
      case 1: 
        m = zza.zzg(paramParcel, i1);
        break;
      case 2: 
        str8 = zza.zzp(paramParcel, i1);
        break;
      case 3: 
        str7 = zza.zzp(paramParcel, i1);
        break;
      case 4: 
        localArrayList3 = zza.zzD(paramParcel, i1);
        break;
      case 5: 
        k = zza.zzg(paramParcel, i1);
        break;
      case 6: 
        localArrayList2 = zza.zzD(paramParcel, i1);
        break;
      case 7: 
        l4 = zza.zzi(paramParcel, i1);
        break;
      case 8: 
        bool8 = zza.zzc(paramParcel, i1);
        break;
      case 9: 
        l3 = zza.zzi(paramParcel, i1);
        break;
      case 10: 
        localArrayList1 = zza.zzD(paramParcel, i1);
        break;
      case 11: 
        l2 = zza.zzi(paramParcel, i1);
        break;
      case 12: 
        j = zza.zzg(paramParcel, i1);
        break;
      case 13: 
        str6 = zza.zzp(paramParcel, i1);
        break;
      case 14: 
        l1 = zza.zzi(paramParcel, i1);
        break;
      case 15: 
        str5 = zza.zzp(paramParcel, i1);
        break;
      case 19: 
        str4 = zza.zzp(paramParcel, i1);
        break;
      case 18: 
        bool7 = zza.zzc(paramParcel, i1);
        break;
      case 21: 
        str3 = zza.zzp(paramParcel, i1);
        break;
      case 23: 
        bool5 = zza.zzc(paramParcel, i1);
        break;
      case 22: 
        bool6 = zza.zzc(paramParcel, i1);
        break;
      case 25: 
        bool3 = zza.zzc(paramParcel, i1);
        break;
      case 24: 
        bool4 = zza.zzc(paramParcel, i1);
        break;
      case 27: 
        i = zza.zzg(paramParcel, i1);
        break;
      case 26: 
        bool2 = zza.zzc(paramParcel, i1);
        break;
      case 29: 
        str2 = zza.zzp(paramParcel, i1);
        break;
      case 28: 
        localLargeParcelTeleporter = (LargeParcelTeleporter)zza.zza(paramParcel, i1, LargeParcelTeleporter.CREATOR);
        break;
      case 31: 
        bool1 = zza.zzc(paramParcel, i1);
        break;
      case 30: 
        str1 = zza.zzp(paramParcel, i1);
      }
    }
    if (paramParcel.dataPosition() != n) {
      throw new zza.zza("Overread allowed size end=" + n, paramParcel);
    }
    return new AdResponseParcel(m, str8, str7, localArrayList3, k, localArrayList2, l4, bool8, l3, localArrayList1, l2, j, str6, l1, str5, bool7, str4, str3, bool6, bool5, bool4, bool3, bool2, i, localLargeParcelTeleporter, str2, str1, bool1);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/ads/internal/request/zzh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */