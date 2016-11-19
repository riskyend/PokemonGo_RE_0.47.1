package com.upsight.android.marketing.internal.billboard;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class BillboardModule
{
  @Provides
  @Singleton
  UpsightBillboardManager provideBillboardManager(UpsightContext paramUpsightContext, MarketingContentStore paramMarketingContentStore)
  {
    return new BillboardManagerImpl(paramUpsightContext, paramMarketingContentStore, paramUpsightContext.getCoreComponent().bus());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */