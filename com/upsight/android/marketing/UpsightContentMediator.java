package com.upsight.android.marketing;

import android.app.FragmentManager;
import android.view.View;
import com.google.gson.JsonObject;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.internal.billboard.BillboardFragment;
import com.upsight.android.marketing.internal.content.MarketingContent;
import com.upsight.android.marketing.internal.content.MarketingContentActions.MarketingContentActionContext;
import java.util.HashSet;
import java.util.Set;

public abstract class UpsightContentMediator<T>
{
  public static void register(UpsightContext paramUpsightContext, UpsightContentMediator paramUpsightContentMediator)
  {
    UpsightMarketingExtension localUpsightMarketingExtension = (UpsightMarketingExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.marketing");
    if (localUpsightMarketingExtension != null)
    {
      localUpsightMarketingExtension.getApi().registerContentMediator(paramUpsightContentMediator);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.marketing must be registered in your Android Manifest", new Object[0]);
  }
  
  public abstract T buildContentModel(MarketingContent<T> paramMarketingContent, MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext, JsonObject paramJsonObject);
  
  public abstract View buildContentView(MarketingContent<T> paramMarketingContent, MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext);
  
  public abstract void displayContent(MarketingContent<T> paramMarketingContent, FragmentManager paramFragmentManager, BillboardFragment paramBillboardFragment);
  
  public abstract String getContentProvider();
  
  public Set<UpsightBillboard.Dimensions> getDimensions(MarketingContent<T> paramMarketingContent)
  {
    return new HashSet();
  }
  
  public UpsightBillboard.PresentationStyle getPresentationStyle(MarketingContent<T> paramMarketingContent)
  {
    return UpsightBillboard.PresentationStyle.None;
  }
  
  public abstract void hideContent(MarketingContent<T> paramMarketingContent, FragmentManager paramFragmentManager, BillboardFragment paramBillboardFragment);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/UpsightContentMediator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */