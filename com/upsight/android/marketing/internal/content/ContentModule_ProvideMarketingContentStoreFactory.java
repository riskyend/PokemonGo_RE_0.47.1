package com.upsight.android.marketing.internal.content;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ContentModule_ProvideMarketingContentStoreFactory
  implements Factory<MarketingContentStore>
{
  private final Provider<MarketingContentStoreImpl> implProvider;
  private final ContentModule module;
  
  static
  {
    if (!ContentModule_ProvideMarketingContentStoreFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContentModule_ProvideMarketingContentStoreFactory(ContentModule paramContentModule, Provider<MarketingContentStoreImpl> paramProvider)
  {
    assert (paramContentModule != null);
    this.module = paramContentModule;
    assert (paramProvider != null);
    this.implProvider = paramProvider;
  }
  
  public static Factory<MarketingContentStore> create(ContentModule paramContentModule, Provider<MarketingContentStoreImpl> paramProvider)
  {
    return new ContentModule_ProvideMarketingContentStoreFactory(paramContentModule, paramProvider);
  }
  
  public MarketingContentStore get()
  {
    return (MarketingContentStore)Preconditions.checkNotNull(this.module.provideMarketingContentStore((MarketingContentStoreImpl)this.implProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule_ProvideMarketingContentStoreFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */