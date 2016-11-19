package com.google.android.gms.internal;

import android.text.TextUtils;

@zzgr
public final class zzbr
{
  private String zzuc;
  private int zzud = -1;
  
  public zzbr()
  {
    this((String)zzby.zzul.zzde(), -1);
  }
  
  public zzbr(String paramString)
  {
    this(paramString, -1);
  }
  
  public zzbr(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString)) {
      paramString = (String)zzby.zzul.zzde();
    }
    for (;;)
    {
      this.zzuc = paramString;
      this.zzud = paramInt;
      return;
    }
  }
  
  public String zzdc()
  {
    return this.zzuc;
  }
  
  public int zzdd()
  {
    return this.zzud;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzbr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */