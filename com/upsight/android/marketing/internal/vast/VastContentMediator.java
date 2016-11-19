package com.upsight.android.marketing.internal.vast;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.view.View;
import com.google.gson.JsonObject;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.analytics.UpsightLifeCycleTracker.ActivityTrackEvent;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.internal.billboard.BillboardFragment;
import com.upsight.android.marketing.internal.content.MarketingContent;
import com.upsight.android.marketing.internal.content.MarketingContentActions.MarketingContentActionContext;
import com.upsight.mediation.ads.adapters.NetworkWrapper.Listener;
import com.upsight.mediation.ads.adapters.VastAdAdapter;
import com.upsight.mediation.ads.model.AdapterLoadError;
import com.upsight.mediation.data.Offer;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public final class VastContentMediator
  extends UpsightContentMediator<VastContentModel>
{
  private static final String CONTENT_PROVIDER = "vast";
  public static final String LOG_TAG = VastContentMediator.class.getSimpleName();
  private VastAdAdapter mAdapter = null;
  private Bus mBus;
  private WeakReference<Activity> mCurrentActivity = null;
  private UpsightLogger mLogger;
  
  public VastContentMediator(UpsightLogger paramUpsightLogger, Bus paramBus)
  {
    this.mLogger = paramUpsightLogger;
    this.mBus = paramBus;
    this.mBus.register(this);
  }
  
  public VastContentModel buildContentModel(MarketingContent<VastContentModel> paramMarketingContent, MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, JsonObject paramJsonObject)
  {
    try
    {
      paramMarketingContent = VastContentModel.from(paramJsonObject, paramMarketingContentActionContext.mGson);
      return paramMarketingContent;
    }
    catch (IOException paramMarketingContent)
    {
      this.mLogger.e(LOG_TAG, "Failed to parse content model", new Object[] { paramMarketingContent });
    }
    return null;
  }
  
  public View buildContentView(final MarketingContent<VastContentModel> paramMarketingContent, MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext)
  {
    if (this.mCurrentActivity != null)
    {
      paramMarketingContentActionContext = (Activity)this.mCurrentActivity.get();
      if (paramMarketingContentActionContext != null)
      {
        final VastContentModel localVastContentModel = (VastContentModel)paramMarketingContent.getContentModel();
        this.mAdapter = new VastAdAdapter();
        this.mAdapter.init();
        HashMap localHashMap = ((VastContentModel)paramMarketingContent.getContentModel()).getSettings();
        localHashMap.put("maxFileSize", localVastContentModel.getMaxVastFileSize());
        localHashMap.put("isRewarded", Boolean.toString(localVastContentModel.isRewarded().booleanValue()));
        localHashMap.put("shouldValidateSchema", Boolean.toString(localVastContentModel.shouldValidateSchema().booleanValue()));
        this.mAdapter.setListener(new NetworkWrapper.Listener()
        {
          public int getID()
          {
            return localVastContentModel.getAdapterId().intValue();
          }
          
          public void onAdClicked() {}
          
          public void onAdClosed()
          {
            VastContentMediator.this.mLogger.i(VastContentMediator.LOG_TAG, "onAdClosed", new Object[0]);
            if (paramMarketingContent.isRewardGranted()) {}
            for (String str = "content_dismissed_with_reward";; str = "content_dismissed")
            {
              paramMarketingContent.executeActions(str);
              return;
            }
          }
          
          public void onAdCompleted() {}
          
          public void onAdDisplayed()
          {
            VastContentMediator.this.mLogger.i(VastContentMediator.LOG_TAG, "onAdDisplayed", new Object[0]);
            paramMarketingContent.executeActions("content_displayed");
          }
          
          public void onAdFailedToDisplay()
          {
            VastContentMediator.this.mLogger.w(VastContentMediator.LOG_TAG, "Failed to display VAST content", new Object[0]);
          }
          
          public void onAdFailedToLoad(AdapterLoadError paramAnonymousAdapterLoadError)
          {
            VastContentMediator.this.mLogger.w(VastContentMediator.LOG_TAG, "Failed to load VAST content", new Object[0]);
          }
          
          public void onAdLoaded()
          {
            VastContentMediator.this.mLogger.i(VastContentMediator.LOG_TAG, "onAdLoaded", new Object[0]);
            paramMarketingContent.markLoaded(VastContentMediator.this.mBus);
          }
          
          public void onAdSkipped() {}
          
          public void onOfferAccepted() {}
          
          public void onOfferDisplayed(Offer paramAnonymousOffer)
          {
            VastContentMediator.this.mLogger.i(VastContentMediator.LOG_TAG, "onOfferDisplayed", new Object[0]);
            paramMarketingContent.executeActions("content_displayed");
          }
          
          public void onOfferRejected() {}
          
          public void onOpenMRaidUrl(@NonNull String paramAnonymousString) {}
          
          public void onRewardedVideoCompleted()
          {
            VastContentMediator.this.mLogger.i(VastContentMediator.LOG_TAG, "onRewardedVideoCompleted", new Object[0]);
            paramMarketingContent.markRewardGranted();
          }
          
          public void onVastError(int paramAnonymousInt)
          {
            VastContentMediator.this.mLogger.w(VastContentMediator.LOG_TAG, "onVastError i=" + paramAnonymousInt, new Object[0]);
          }
          
          public void onVastProgress(int paramAnonymousInt) {}
          
          public void onVastReplay() {}
          
          public void onVastSkip() {}
          
          public void sendRequestToBeacon(String paramAnonymousString) {}
        });
        this.mAdapter.loadAd(paramMarketingContentActionContext, localHashMap);
      }
    }
    return null;
  }
  
  public void displayContent(MarketingContent<VastContentModel> paramMarketingContent, FragmentManager paramFragmentManager, BillboardFragment paramBillboardFragment)
  {
    this.mAdapter.displayAd();
  }
  
  public String getContentProvider()
  {
    return "vast";
  }
  
  @Subscribe
  public void handleActivityTrackEvent(UpsightLifeCycleTracker.ActivityTrackEvent paramActivityTrackEvent)
  {
    switch (paramActivityTrackEvent.mActivityState)
    {
    }
    do
    {
      return;
      this.mCurrentActivity = new WeakReference(paramActivityTrackEvent.mActivity);
      return;
    } while (this.mCurrentActivity == null);
    this.mCurrentActivity.clear();
    this.mCurrentActivity = null;
  }
  
  public void hideContent(MarketingContent<VastContentModel> paramMarketingContent, FragmentManager paramFragmentManager, BillboardFragment paramBillboardFragment) {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/vast/VastContentMediator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */