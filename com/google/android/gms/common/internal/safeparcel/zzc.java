package com.google.android.gms.common.internal.safeparcel;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzx;

public final class zzc
{
  public static <T extends SafeParcelable> T zza(Intent paramIntent, String paramString, Parcelable.Creator<T> paramCreator)
  {
    paramIntent = paramIntent.getByteArrayExtra(paramString);
    if (paramIntent == null) {
      return null;
    }
    return zza(paramIntent, paramCreator);
  }
  
  public static <T extends SafeParcelable> T zza(byte[] paramArrayOfByte, Parcelable.Creator<T> paramCreator)
  {
    zzx.zzw(paramCreator);
    Parcel localParcel = Parcel.obtain();
    localParcel.unmarshall(paramArrayOfByte, 0, paramArrayOfByte.length);
    localParcel.setDataPosition(0);
    paramArrayOfByte = (SafeParcelable)paramCreator.createFromParcel(localParcel);
    localParcel.recycle();
    return paramArrayOfByte;
  }
  
  public static <T extends SafeParcelable> void zza(T paramT, Intent paramIntent, String paramString)
  {
    paramIntent.putExtra(paramString, zza(paramT));
  }
  
  public static <T extends SafeParcelable> byte[] zza(T paramT)
  {
    Parcel localParcel = Parcel.obtain();
    paramT.writeToParcel(localParcel, 0);
    paramT = localParcel.marshall();
    localParcel.recycle();
    return paramT;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/common/internal/safeparcel/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */