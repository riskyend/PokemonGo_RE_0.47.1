package com.upsight.android.marketing.internal.content;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ContentModule_ProvideMarketingContentStoreImplFactory
  implements Factory<MarketingContentStoreImpl>
{
  private final ContentModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!ContentModule_ProvideMarketingContentStoreImplFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContentModule_ProvideMarketingContentStoreImplFactory(ContentModule paramContentModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramContentModule != null);
    this.module = paramContentModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<MarketingContentStoreImpl> create(ContentModule paramContentModule, Provider<UpsightContext> paramProvider)
  {
    return new ContentModule_ProvideMarketingContentStoreImplFactory(paramContentModule, paramProvider);
  }
  
  public MarketingContentStoreImpl get()
  {
    return (MarketingContentStoreImpl)Preconditions.checkNotNull(this.module.provideMarketingContentStoreImpl((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule_ProvideMarketingContentStoreImplFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */