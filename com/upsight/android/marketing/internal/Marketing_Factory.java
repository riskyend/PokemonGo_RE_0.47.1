package com.upsight.android.marketing.internal;

import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import com.upsight.android.marketing.internal.content.MarketingContentMediatorManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class Marketing_Factory
  implements Factory<Marketing>
{
  private final Provider<UpsightBillboardManager> billboardManagerProvider;
  private final Provider<MarketingContentMediatorManager> contentMediatorManagerProvider;
  private final Provider<UpsightMarketingContentStore> marketingContentStoreProvider;
  
  static
  {
    if (!Marketing_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public Marketing_Factory(Provider<UpsightBillboardManager> paramProvider, Provider<UpsightMarketingContentStore> paramProvider1, Provider<MarketingContentMediatorManager> paramProvider2)
  {
    assert (paramProvider != null);
    this.billboardManagerProvider = paramProvider;
    assert (paramProvider1 != null);
    this.marketingContentStoreProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.contentMediatorManagerProvider = paramProvider2;
  }
  
  public static Factory<Marketing> create(Provider<UpsightBillboardManager> paramProvider, Provider<UpsightMarketingContentStore> paramProvider1, Provider<MarketingContentMediatorManager> paramProvider2)
  {
    return new Marketing_Factory(paramProvider, paramProvider1, paramProvider2);
  }
  
  public Marketing get()
  {
    return new Marketing((UpsightBillboardManager)this.billboardManagerProvider.get(), (UpsightMarketingContentStore)this.marketingContentStoreProvider.get(), (MarketingContentMediatorManager)this.contentMediatorManagerProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/Marketing_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */