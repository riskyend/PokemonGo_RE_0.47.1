package com.upsight.mediation.vast.Postroll;

import android.app.Activity;
import android.view.ViewGroup;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.mraid.MRAIDInterstitial;
import com.upsight.mediation.mraid.MRAIDInterstitialListener;
import com.upsight.mediation.mraid.MRAIDNativeFeatureListener;

public class MRaidPostroll
  implements Postroll, MRAIDInterstitialListener, MRAIDNativeFeatureListener
{
  private static final String BASE_URL = "";
  private static final String TAG = "MRaidPostroll";
  private final Activity mActivity;
  private final String mHtml;
  private MRAIDInterstitial mInterstitial;
  private final Postroll.Listener mListener;
  private boolean mReady = false;
  private int previousOrientation;
  
  public MRaidPostroll(Activity paramActivity, String paramString, Postroll.Listener paramListener)
  {
    this.mActivity = paramActivity;
    this.mHtml = paramString;
    this.mListener = paramListener;
  }
  
  public void hide() {}
  
  public void init()
  {
    if (this.mInterstitial != null)
    {
      FuseLog.w("MRaidPostroll", "Tried to call init on already init'd mraid postroll");
      return;
    }
    this.mInterstitial = new MRAIDInterstitial(this.mActivity, "", this.mHtml, new String[0], this, this);
  }
  
  public boolean isReady()
  {
    return this.mReady;
  }
  
  public void mraidInterstitialAcceptPressed(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidInterstitialFailedToLoad(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidInterstitialHide(MRAIDInterstitial paramMRAIDInterstitial)
  {
    this.mActivity.setRequestedOrientation(this.previousOrientation);
    this.mListener.closeClicked();
  }
  
  public void mraidInterstitialLoaded(MRAIDInterstitial paramMRAIDInterstitial)
  {
    this.mReady = true;
  }
  
  public void mraidInterstitialRejectPressed(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidInterstitialReplayVideoPressed(MRAIDInterstitial paramMRAIDInterstitial)
  {
    this.mActivity.setRequestedOrientation(this.previousOrientation);
    this.mListener.replayedClicked();
  }
  
  public void mraidInterstitialShow(MRAIDInterstitial paramMRAIDInterstitial) {}
  
  public void mraidNativeFeatureCallTel(String paramString) {}
  
  public void mraidNativeFeatureCreateCalendarEvent(String paramString) {}
  
  public void mraidNativeFeatureOpenBrowser(String paramString)
  {
    if (this.mListener != null)
    {
      this.mListener.infoClicked(false);
      this.mListener.onOpenMRaidUrl(paramString);
    }
  }
  
  public void mraidNativeFeatureOpenMarket(String paramString) {}
  
  public void mraidNativeFeaturePlayVideo(String paramString) {}
  
  public void mraidNativeFeatureSendSms(String paramString) {}
  
  public void mraidNativeFeatureStorePicture(String paramString) {}
  
  public void mraidRewardComplete() {}
  
  public void show(ViewGroup paramViewGroup)
  {
    this.previousOrientation = this.mActivity.getRequestedOrientation();
    this.mActivity.setRequestedOrientation(4);
    this.mInterstitial.show();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/Postroll/MRaidPostroll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */