package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ResolveAccountRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<ResolveAccountRequest> CREATOR = new zzy();
  final int mVersionCode;
  private final Account zzQd;
  private final int zzagp;
  
  ResolveAccountRequest(int paramInt1, Account paramAccount, int paramInt2)
  {
    this.mVersionCode = paramInt1;
    this.zzQd = paramAccount;
    this.zzagp = paramInt2;
  }
  
  public ResolveAccountRequest(Account paramAccount, int paramInt)
  {
    this(1, paramAccount, paramInt);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Account getAccount()
  {
    return this.zzQd;
  }
  
  public int getSessionId()
  {
    return this.zzagp;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzy.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/common/internal/ResolveAccountRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */