package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<BitmapTeleporter>
{
  static void zza(BitmapTeleporter paramBitmapTeleporter, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzaq(paramParcel);
    zzb.zzc(paramParcel, 1, paramBitmapTeleporter.mVersionCode);
    zzb.zza(paramParcel, 2, paramBitmapTeleporter.zzFc, paramInt, false);
    zzb.zzc(paramParcel, 3, paramBitmapTeleporter.zzWJ);
    zzb.zzI(paramParcel, i);
  }
  
  public BitmapTeleporter zzaf(Parcel paramParcel)
  {
    int i = 0;
    int k = com.google.android.gms.common.internal.safeparcel.zza.zzap(paramParcel);
    ParcelFileDescriptor localParcelFileDescriptor = null;
    int j = 0;
    if (paramParcel.dataPosition() < k)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzao(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(m))
      {
      default: 
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
      }
      for (;;)
      {
        break;
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        continue;
        localParcelFileDescriptor = (ParcelFileDescriptor)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, m, ParcelFileDescriptor.CREATOR);
        continue;
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != k) {
      throw new zza.zza("Overread allowed size end=" + k, paramParcel);
    }
    return new BitmapTeleporter(j, localParcelFileDescriptor, i);
  }
  
  public BitmapTeleporter[] zzbq(int paramInt)
  {
    return new BitmapTeleporter[paramInt];
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/common/data/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */