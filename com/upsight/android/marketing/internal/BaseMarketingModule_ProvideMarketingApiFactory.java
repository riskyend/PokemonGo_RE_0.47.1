package com.upsight.android.marketing.internal;

import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.UpsightMarketingApi;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import com.upsight.android.marketing.internal.content.MarketingContentMediatorManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseMarketingModule_ProvideMarketingApiFactory
  implements Factory<UpsightMarketingApi>
{
  private final Provider<UpsightBillboardManager> billboardManagerProvider;
  private final Provider<MarketingContentMediatorManager> contentMediatorManagerProvider;
  private final Provider<UpsightMarketingContentStore> contentStoreProvider;
  private final BaseMarketingModule module;
  
  static
  {
    if (!BaseMarketingModule_ProvideMarketingApiFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BaseMarketingModule_ProvideMarketingApiFactory(BaseMarketingModule paramBaseMarketingModule, Provider<UpsightBillboardManager> paramProvider, Provider<UpsightMarketingContentStore> paramProvider1, Provider<MarketingContentMediatorManager> paramProvider2)
  {
    assert (paramBaseMarketingModule != null);
    this.module = paramBaseMarketingModule;
    assert (paramProvider != null);
    this.billboardManagerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.contentStoreProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.contentMediatorManagerProvider = paramProvider2;
  }
  
  public static Factory<UpsightMarketingApi> create(BaseMarketingModule paramBaseMarketingModule, Provider<UpsightBillboardManager> paramProvider, Provider<UpsightMarketingContentStore> paramProvider1, Provider<MarketingContentMediatorManager> paramProvider2)
  {
    return new BaseMarketingModule_ProvideMarketingApiFactory(paramBaseMarketingModule, paramProvider, paramProvider1, paramProvider2);
  }
  
  public UpsightMarketingApi get()
  {
    return (UpsightMarketingApi)Preconditions.checkNotNull(this.module.provideMarketingApi((UpsightBillboardManager)this.billboardManagerProvider.get(), (UpsightMarketingContentStore)this.contentStoreProvider.get(), (MarketingContentMediatorManager)this.contentMediatorManagerProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/BaseMarketingModule_ProvideMarketingApiFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */