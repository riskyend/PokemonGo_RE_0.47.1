package com.upsight.android.marketing.internal.content;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import com.google.gson.JsonObject;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.R.id;
import com.upsight.android.marketing.R.layout;
import com.upsight.android.marketing.UpsightBillboard.Dimensions;
import com.upsight.android.marketing.UpsightBillboard.Dimensions.LayoutOrientation;
import com.upsight.android.marketing.UpsightBillboard.PresentationStyle;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.internal.billboard.BillboardFragment;
import com.upsight.android.marketing.internal.billboard.BillboardFragment.BackPressHandler;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class DefaultContentMediator
  extends UpsightContentMediator<MarketingContentModel>
{
  public static final String CONTENT_PROVIDER = "upsight";
  
  public MarketingContentModel buildContentModel(MarketingContent<MarketingContentModel> paramMarketingContent, MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, JsonObject paramJsonObject)
  {
    try
    {
      paramMarketingContent = MarketingContentModel.from(paramJsonObject, paramMarketingContentActionContext.mGson);
      return paramMarketingContent;
    }
    catch (IOException paramMarketingContent)
    {
      paramMarketingContentActionContext.mLogger.e("Upsight", "Failed to parse content model", new Object[] { paramMarketingContent });
    }
    return null;
  }
  
  @SuppressLint({"SetJavaScriptEnabled"})
  public View buildContentView(final MarketingContent<MarketingContentModel> paramMarketingContent, MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext)
  {
    View localView = LayoutInflater.from(paramMarketingContentActionContext.mUpsight).inflate(R.layout.upsight_marketing_content_view, null);
    ((ImageView)localView.findViewById(R.id.upsight_marketing_content_view_close_button)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramMarketingContent.executeActions("content_dismissed");
      }
    });
    WebView localWebView = (WebView)localView.findViewById(R.id.upsight_marketing_content_view_web_view);
    localWebView.getSettings().setJavaScriptEnabled(true);
    localWebView.setWebViewClient(paramMarketingContentActionContext.mContentTemplateWebViewClientFactory.create(paramMarketingContent));
    localWebView.loadUrl(((MarketingContentModel)paramMarketingContent.getContentModel()).getTemplateUrl());
    return localView;
  }
  
  public void displayContent(final MarketingContent<MarketingContentModel> paramMarketingContent, FragmentManager paramFragmentManager, BillboardFragment paramBillboardFragment)
  {
    ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
    View localView = paramMarketingContent.getContentView();
    paramBillboardFragment.getContentViewContainer().addView(localView, localLayoutParams);
    paramBillboardFragment.setBackPressHandler(new BillboardFragment.BackPressHandler()
    {
      private boolean mIsDismissed = false;
      
      public boolean onBackPress()
      {
        if (!this.mIsDismissed)
        {
          paramMarketingContent.executeActions("content_dismissed");
          this.mIsDismissed = true;
        }
        return true;
      }
    });
    if (!paramBillboardFragment.isAdded()) {
      paramBillboardFragment.show(paramFragmentManager, null);
    }
    paramMarketingContent.executeActions("content_displayed");
  }
  
  public String getContentProvider()
  {
    return "upsight";
  }
  
  public Set<UpsightBillboard.Dimensions> getDimensions(MarketingContent<MarketingContentModel> paramMarketingContent)
  {
    HashSet localHashSet = new HashSet();
    paramMarketingContent = ((MarketingContentModel)paramMarketingContent.getContentModel()).getDialogLayouts();
    if (paramMarketingContent != null)
    {
      if ((paramMarketingContent.portrait != null) && (paramMarketingContent.portrait.w > 0) && (paramMarketingContent.portrait.h > 0)) {
        localHashSet.add(new UpsightBillboard.Dimensions(UpsightBillboard.Dimensions.LayoutOrientation.Portrait, paramMarketingContent.portrait.w, paramMarketingContent.portrait.h));
      }
      if ((paramMarketingContent.landscape != null) && (paramMarketingContent.landscape.w > 0) && (paramMarketingContent.landscape.h > 0)) {
        localHashSet.add(new UpsightBillboard.Dimensions(UpsightBillboard.Dimensions.LayoutOrientation.Landscape, paramMarketingContent.landscape.w, paramMarketingContent.landscape.h));
      }
    }
    return localHashSet;
  }
  
  public UpsightBillboard.PresentationStyle getPresentationStyle(MarketingContent<MarketingContentModel> paramMarketingContent)
  {
    paramMarketingContent = ((MarketingContentModel)paramMarketingContent.getContentModel()).getPresentationStyle();
    if ("dialog".equals(paramMarketingContent)) {
      return UpsightBillboard.PresentationStyle.Dialog;
    }
    if ("fullscreen".equals(paramMarketingContent)) {
      return UpsightBillboard.PresentationStyle.Fullscreen;
    }
    return UpsightBillboard.PresentationStyle.None;
  }
  
  public void hideContent(MarketingContent<MarketingContentModel> paramMarketingContent, FragmentManager paramFragmentManager, BillboardFragment paramBillboardFragment)
  {
    paramBillboardFragment.getContentViewContainer().removeAllViews();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/DefaultContentMediator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */