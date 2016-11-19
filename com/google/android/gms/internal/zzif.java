package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@zzgr
public class zzif
{
  private final Context mContext;
  private int mState = 0;
  private final float zzAC;
  private String zzII;
  private float zzIJ;
  private float zzIK;
  private float zzIL;
  
  public zzif(Context paramContext)
  {
    this.mContext = paramContext;
    this.zzAC = paramContext.getResources().getDisplayMetrics().density;
  }
  
  public zzif(Context paramContext, String paramString)
  {
    this(paramContext);
    this.zzII = paramString;
  }
  
  private void showDialog()
  {
    if (!(this.mContext instanceof Activity))
    {
      zzb.zzaG("Can not create dialog without Activity Context");
      return;
    }
    final String str = zzaD(this.zzII);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mContext);
    localBuilder.setMessage(str);
    localBuilder.setTitle("Ad Information");
    localBuilder.setPositiveButton("Share", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        zzp.zzbv().zzb(zzif.zza(zzif.this), Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", str), "Share via"));
      }
    });
    localBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
    localBuilder.create().show();
  }
  
  static String zzaD(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      paramString = "No debug information";
    }
    Object localObject;
    do
    {
      return paramString;
      paramString = paramString.replaceAll("\\+", "%20");
      localObject = new Uri.Builder().encodedQuery(paramString).build();
      paramString = new StringBuilder();
      localObject = zzp.zzbv().zze((Uri)localObject);
      Iterator localIterator = ((Map)localObject).keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramString.append(str).append(" = ").append((String)((Map)localObject).get(str)).append("\n\n");
      }
      localObject = paramString.toString().trim();
      paramString = (String)localObject;
    } while (!TextUtils.isEmpty((CharSequence)localObject));
    return "No debug information";
  }
  
  void zza(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 0)
    {
      this.mState = 0;
      this.zzIJ = paramFloat1;
      this.zzIK = paramFloat2;
      this.zzIL = paramFloat2;
    }
    label24:
    label224:
    do
    {
      do
      {
        break label24;
        do
        {
          return;
        } while (this.mState == -1);
        if (paramInt != 2) {
          break;
        }
        if (paramFloat2 > this.zzIK) {
          this.zzIK = paramFloat2;
        }
        while (this.zzIK - this.zzIL > 30.0F * this.zzAC)
        {
          this.mState = -1;
          return;
          if (paramFloat2 < this.zzIL) {
            this.zzIL = paramFloat2;
          }
        }
        if ((this.mState == 0) || (this.mState == 2)) {
          if (paramFloat1 - this.zzIJ >= 50.0F * this.zzAC) {
            this.zzIJ = paramFloat1;
          }
        }
        for (this.mState += 1;; this.mState += 1)
        {
          do
          {
            if ((this.mState != 1) && (this.mState != 3)) {
              break label224;
            }
            if (paramFloat1 <= this.zzIJ) {
              break;
            }
            this.zzIJ = paramFloat1;
            return;
          } while (((this.mState != 1) && (this.mState != 3)) || (paramFloat1 - this.zzIJ > -50.0F * this.zzAC));
          this.zzIJ = paramFloat1;
        }
      } while ((this.mState != 2) || (paramFloat1 >= this.zzIJ));
      this.zzIJ = paramFloat1;
      return;
    } while ((paramInt != 1) || (this.mState != 4));
    showDialog();
  }
  
  public void zzaC(String paramString)
  {
    this.zzII = paramString;
  }
  
  public void zze(MotionEvent paramMotionEvent)
  {
    int j = paramMotionEvent.getHistorySize();
    int i = 0;
    while (i < j)
    {
      zza(paramMotionEvent.getActionMasked(), paramMotionEvent.getHistoricalX(0, i), paramMotionEvent.getHistoricalY(0, i));
      i += 1;
    }
    zza(paramMotionEvent.getActionMasked(), paramMotionEvent.getX(), paramMotionEvent.getY());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/gms/internal/zzif.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */