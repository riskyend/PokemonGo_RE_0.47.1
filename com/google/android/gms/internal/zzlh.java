package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class zzlh
  implements zzlj
{
  private final zzli zzabr;
  
  public zzlh(zzli paramzzli)
  {
    this.zzabr = paramzzli;
  }
  
  public void begin()
  {
    this.zzabr.zznZ();
    this.zzabr.zzaci = Collections.emptySet();
  }
  
  public void connect()
  {
    this.zzabr.zzoa();
  }
  
  public void disconnect()
  {
    Iterator localIterator = this.zzabr.zzaca.iterator();
    while (localIterator.hasNext())
    {
      zzli.zzf localzzf = (zzli.zzf)localIterator.next();
      localzzf.zza(null);
      localzzf.cancel();
    }
    this.zzabr.zzaca.clear();
    this.zzabr.zzach.clear();
    this.zzabr.zznY();
  }
  
  public String getName()
  {
    return "DISCONNECTED";
  }
  
  public void onConnected(Bundle paramBundle) {}
  
  public void onConnectionSuspended(int paramInt) {}
  
  public <A extends Api.zzb, R extends Result, T extends zzlb.zza<R, A>> T zza(T paramT)
  {
    this.zzabr.zzaca.add(paramT);
    return paramT;
  }
  
  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt) {}
  
  public <A extends Api.zzb, T extends zzlb.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzlh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */