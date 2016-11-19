package com.upsight.mediation.mraid;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.upsight.mediation.mraid.internal.MRAIDLog;

public class MRAIDInterstitial
  implements MRAIDViewListener
{
  private static final String TAG = "MRAIDInterstitial";
  private Handler handler;
  public boolean isReady;
  private MRAIDInterstitialListener listener;
  public MRAIDView mraidView;
  
  public MRAIDInterstitial(Context paramContext, String paramString1, String paramString2, int paramInt, @NonNull String[] paramArrayOfString, MRAIDInterstitialListener paramMRAIDInterstitialListener, MRAIDNativeFeatureListener paramMRAIDNativeFeatureListener)
  {
    this.listener = paramMRAIDInterstitialListener;
    this.handler = new Handler(Looper.getMainLooper());
    this.mraidView = new MRAIDView(paramContext, paramString1, paramString2, paramInt, paramArrayOfString, this, paramMRAIDNativeFeatureListener, true);
  }
  
  public MRAIDInterstitial(Context paramContext, String paramString1, String paramString2, String[] paramArrayOfString, MRAIDInterstitialListener paramMRAIDInterstitialListener, MRAIDNativeFeatureListener paramMRAIDNativeFeatureListener)
  {
    this(paramContext, paramString1, paramString2, 0, paramArrayOfString, paramMRAIDInterstitialListener, paramMRAIDNativeFeatureListener);
  }
  
  public void injectJavaScript(String paramString)
  {
    this.mraidView.injectJavaScript(paramString);
  }
  
  public void mraidReplayVideoPressed(MRAIDView paramMRAIDView)
  {
    if (this.listener != null) {
      this.listener.mraidInterstitialReplayVideoPressed(this);
    }
  }
  
  public void mraidViewAcceptPressed(MRAIDView paramMRAIDView)
  {
    if (this.listener != null) {
      this.listener.mraidInterstitialAcceptPressed(this);
    }
  }
  
  public void mraidViewClose(MRAIDView paramMRAIDView)
  {
    MRAIDLog.i("MRAIDInterstitial-MRAIDViewListener", "mraidViewClose");
    this.isReady = false;
    if (this.listener != null) {
      this.listener.mraidInterstitialHide(this);
    }
  }
  
  public void mraidViewExpand(MRAIDView paramMRAIDView)
  {
    MRAIDLog.i("MRAIDInterstitial-MRAIDViewListener", "mraidViewExpand");
    if (this.listener != null) {
      this.listener.mraidInterstitialShow(this);
    }
  }
  
  public void mraidViewFailedToLoad(MRAIDView paramMRAIDView)
  {
    this.isReady = false;
    if (this.listener != null) {
      this.listener.mraidInterstitialFailedToLoad(this);
    }
  }
  
  public void mraidViewLoaded(MRAIDView paramMRAIDView)
  {
    MRAIDLog.v("MRAIDInterstitial-MRAIDViewListener", "mraidViewLoaded");
    this.isReady = true;
    this.handler.postDelayed(new Runnable()
    {
      public void run()
      {
        if ((MRAIDInterstitial.this.isReady) && (MRAIDInterstitial.this.listener != null))
        {
          MRAIDInterstitial.this.listener.mraidInterstitialLoaded(MRAIDInterstitial.this);
          return;
        }
        MRAIDLog.i("MRAIDInterstitial", "No longer ready");
      }
    }, 250L);
  }
  
  public void mraidViewRejectPressed(MRAIDView paramMRAIDView)
  {
    if (this.listener != null) {
      this.listener.mraidInterstitialRejectPressed(this);
    }
  }
  
  public boolean mraidViewResize(MRAIDView paramMRAIDView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return true;
  }
  
  public void setOrientationConfig(int paramInt)
  {
    this.mraidView.setOrientationConfig(paramInt);
  }
  
  public boolean show()
  {
    if (!this.isReady)
    {
      MRAIDLog.i("MRAIDInterstitial", "interstitial is not ready to show");
      return false;
    }
    this.mraidView.showAsInterstitial();
    this.isReady = false;
    return true;
  }
  
  public void updateContext(Context paramContext)
  {
    this.mraidView.updateContext(paramContext);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/MRAIDInterstitial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */