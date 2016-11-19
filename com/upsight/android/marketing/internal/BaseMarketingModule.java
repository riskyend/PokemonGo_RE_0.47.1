package com.upsight.android.marketing.internal;

import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.UpsightMarketingApi;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import com.upsight.android.marketing.internal.content.MarketingContentMediatorManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public final class BaseMarketingModule
{
  public static final String SCHEDULER_MAIN = "main";
  private final UpsightContext mUpsight;
  
  public BaseMarketingModule(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  @Provides
  @Named("main")
  @Singleton
  Scheduler provideMainScheduler()
  {
    return AndroidSchedulers.mainThread();
  }
  
  @Provides
  @Singleton
  UpsightMarketingApi provideMarketingApi(UpsightBillboardManager paramUpsightBillboardManager, UpsightMarketingContentStore paramUpsightMarketingContentStore, MarketingContentMediatorManager paramMarketingContentMediatorManager)
  {
    return new Marketing(paramUpsightBillboardManager, paramUpsightMarketingContentStore, paramMarketingContentMediatorManager);
  }
  
  @Provides
  @Singleton
  UpsightContext provideUpsightContext()
  {
    return this.mUpsight;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/BaseMarketingModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */