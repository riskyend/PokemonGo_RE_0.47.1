package com.google.android.gms.location.places;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;
import com.google.android.gms.location.places.internal.zzn;

public class PlaceLikelihoodBuffer
  extends AbstractDataBuffer<PlaceLikelihood>
  implements Result
{
  private final Context mContext;
  private final Status zzSC;
  private final String zzaGk;
  private final int zzaGp;
  
  public PlaceLikelihoodBuffer(DataHolder paramDataHolder, int paramInt, Context paramContext)
  {
    super(paramDataHolder);
    this.mContext = paramContext;
    this.zzSC = PlacesStatusCodes.zzhp(paramDataHolder.getStatusCode());
    this.zzaGp = zza.zzhk(paramInt);
    if ((paramDataHolder != null) && (paramDataHolder.zzor() != null))
    {
      this.zzaGk = paramDataHolder.zzor().getString("com.google.android.gms.location.places.PlaceLikelihoodBuffer.ATTRIBUTIONS_EXTRA_KEY");
      return;
    }
    this.zzaGk = null;
  }
  
  public PlaceLikelihood get(int paramInt)
  {
    return new zzn(this.zzabq, paramInt, this.mContext);
  }
  
  public CharSequence getAttributions()
  {
    return this.zzaGk;
  }
  
  public Status getStatus()
  {
    return this.zzSC;
  }
  
  public String toString()
  {
    return zzw.zzv(this).zzg("status", getStatus()).zzg("attributions", this.zzaGk).toString();
  }
  
  public static class zza
  {
    static int zzhk(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        throw new IllegalArgumentException("invalid source: " + paramInt);
      }
      return paramInt;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/location/places/PlaceLikelihoodBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */