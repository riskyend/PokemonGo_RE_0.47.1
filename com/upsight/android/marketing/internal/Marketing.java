package com.upsight.android.marketing.internal;

import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.UpsightMarketingApi;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import com.upsight.android.marketing.internal.billboard.Billboard;
import com.upsight.android.marketing.internal.content.MarketingContentMediatorManager;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class Marketing
  implements UpsightMarketingApi
{
  private UpsightBillboardManager mBillboardManager;
  private MarketingContentMediatorManager mContentMediationManager;
  private UpsightMarketingContentStore mMarketingContentStore;
  
  @Inject
  public Marketing(UpsightBillboardManager paramUpsightBillboardManager, UpsightMarketingContentStore paramUpsightMarketingContentStore, MarketingContentMediatorManager paramMarketingContentMediatorManager)
  {
    this.mBillboardManager = paramUpsightBillboardManager;
    this.mMarketingContentStore = paramUpsightMarketingContentStore;
    this.mContentMediationManager = paramMarketingContentMediatorManager;
  }
  
  public boolean isContentReady(String paramString)
  {
    return this.mMarketingContentStore.isContentReady(paramString);
  }
  
  public boolean registerBillboard(Billboard paramBillboard)
  {
    return this.mBillboardManager.registerBillboard(paramBillboard);
  }
  
  public void registerContentMediator(UpsightContentMediator paramUpsightContentMediator)
  {
    this.mContentMediationManager.register(paramUpsightContentMediator);
  }
  
  public boolean unregisterBillboard(Billboard paramBillboard)
  {
    return this.mBillboardManager.unregisterBillboard(paramBillboard);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/Marketing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */