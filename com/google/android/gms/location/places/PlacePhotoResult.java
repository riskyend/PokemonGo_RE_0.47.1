package com.google.android.gms.location.places;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;

public class PlacePhotoResult
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<PlacePhotoResult> CREATOR = new zzi();
  private final Bitmap mBitmap;
  final int mVersionCode;
  private final Status zzSC;
  final BitmapTeleporter zzaGs;
  
  PlacePhotoResult(int paramInt, Status paramStatus, BitmapTeleporter paramBitmapTeleporter)
  {
    this.mVersionCode = paramInt;
    this.zzSC = paramStatus;
    this.zzaGs = paramBitmapTeleporter;
    if (this.zzaGs != null)
    {
      this.mBitmap = paramBitmapTeleporter.zzos();
      return;
    }
    this.mBitmap = null;
  }
  
  public PlacePhotoResult(Status paramStatus, BitmapTeleporter paramBitmapTeleporter)
  {
    this.mVersionCode = 0;
    this.zzSC = paramStatus;
    this.zzaGs = paramBitmapTeleporter;
    if (this.zzaGs != null)
    {
      this.mBitmap = paramBitmapTeleporter.zzos();
      return;
    }
    this.mBitmap = null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }
  
  public Status getStatus()
  {
    return this.zzSC;
  }
  
  public String toString()
  {
    return zzw.zzv(this).zzg("status", this.zzSC).zzg("bitmap", this.mBitmap).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/PlacePhotoResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */