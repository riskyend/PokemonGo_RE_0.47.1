package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.json.JSONObject;

@zzgr
public class zzfp
  extends Handler
{
  private final zzfo zzCq;
  
  public zzfp(Context paramContext)
  {
    this(new zzfq(paramContext));
  }
  
  public zzfp(zzfo paramzzfo)
  {
    this.zzCq = paramzzfo;
  }
  
  private void zzc(JSONObject paramJSONObject)
  {
    try
    {
      this.zzCq.zza(paramJSONObject.getString("request_id"), paramJSONObject.getString("base_url"), paramJSONObject.getString("html"));
      return;
    }
    catch (Exception paramJSONObject) {}
  }
  
  public void handleMessage(Message paramMessage)
  {
    try
    {
      paramMessage = paramMessage.getData();
      if (paramMessage == null) {
        return;
      }
      paramMessage = new JSONObject(paramMessage.getString("data"));
      if ("fetch_html".equals(paramMessage.getString("message_name")))
      {
        zzc(paramMessage);
        return;
      }
    }
    catch (Exception paramMessage) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzfp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */