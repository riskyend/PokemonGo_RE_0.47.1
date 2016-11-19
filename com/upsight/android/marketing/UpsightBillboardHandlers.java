package com.upsight.android.marketing;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.List;

public final class UpsightBillboardHandlers
{
  public static UpsightBillboard.Handler forDefault(Activity paramActivity)
  {
    return new DefaultHandler(paramActivity);
  }
  
  public static UpsightBillboard.Handler forDialog(Activity paramActivity)
  {
    return new DialogHandler(paramActivity);
  }
  
  public static UpsightBillboard.Handler forFullscreen(Activity paramActivity)
  {
    return new FullscreenHandler(paramActivity);
  }
  
  public static class DefaultHandler
    extends UpsightBillboardHandlers.SimpleHandler
  {
    public DefaultHandler(Activity paramActivity)
    {
      super();
    }
    
    public UpsightBillboard.AttachParameters onAttach(String paramString)
    {
      paramString = getActivity();
      if ((paramString == null) || (paramString.isFinishing())) {
        return null;
      }
      return new UpsightBillboard.AttachParameters(paramString).putPreferredPresentationStyle(UpsightBillboard.PresentationStyle.None);
    }
  }
  
  public static class DialogHandler
    extends UpsightBillboardHandlers.SimpleHandler
  {
    public DialogHandler(Activity paramActivity)
    {
      super();
    }
    
    public UpsightBillboard.AttachParameters onAttach(String paramString)
    {
      paramString = getActivity();
      if ((paramString == null) || (paramString.isFinishing())) {
        return null;
      }
      return new UpsightBillboard.AttachParameters(paramString).putPreferredPresentationStyle(UpsightBillboard.PresentationStyle.Dialog);
    }
  }
  
  public static class FullscreenHandler
    extends UpsightBillboardHandlers.SimpleHandler
  {
    public FullscreenHandler(Activity paramActivity)
    {
      super();
    }
    
    public UpsightBillboard.AttachParameters onAttach(String paramString)
    {
      paramString = getActivity();
      if ((paramString == null) || (paramString.isFinishing())) {
        return null;
      }
      return new UpsightBillboard.AttachParameters(paramString).putPreferredPresentationStyle(UpsightBillboard.PresentationStyle.Fullscreen);
    }
  }
  
  private static abstract class SimpleHandler
    implements UpsightBillboard.Handler
  {
    private WeakReference<Activity> mActivity;
    
    protected SimpleHandler(Activity paramActivity)
    {
      this.mActivity = new WeakReference(paramActivity);
    }
    
    Activity getActivity()
    {
      return (Activity)this.mActivity.get();
    }
    
    public void onDetach() {}
    
    public void onNextView() {}
    
    public void onPurchases(List<UpsightPurchase> paramList) {}
    
    public void onRewards(List<UpsightReward> paramList) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/UpsightBillboardHandlers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */